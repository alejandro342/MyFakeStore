package com.alexdev.myfakestoreale.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexdev.myfakestoreale.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    private val _effect = Channel<HomeSideEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        fetchProducts()
    }


    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.OnProductClicked -> {
                sendEffect(HomeSideEffect.NavigateToDetail(event.productId))
            }

            is HomeUiEvent.OnCategoryClicked -> {
                _state.update { it.copy(selectedCategory = event.category) }

            }

            is HomeUiEvent.OnRefresh -> {
                fetchProducts()
            }
        }
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            val result = getProductsUseCase()

            if (result.isSuccess) {
                val productsList = result.getOrNull() ?: emptyList()
                _state.update {
                    it.copy(
                        isLoading = false,
                        products = productsList
                    )
                }
            } else {
                val errorMsg = result.exceptionOrNull()?.message ?: "Error desconocido"
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = errorMsg
                    )
                }
                sendEffect(HomeSideEffect.ShowToast(errorMsg))
            }
        }
    }

    private fun sendEffect(effect: HomeSideEffect) {
        viewModelScope.launch {
            _effect.send(effect)
        }
    }
}