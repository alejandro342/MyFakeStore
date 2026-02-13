package com.alexdev.myfakestoreale.presentation.home

import com.alexdev.myfakestoreale.domain.model.Product

data class HomeUiState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedCategory: String = "Todos",
    val categories: List<String> = listOf(
        "Todos",
        "electronics",
        "jewelery",
        "men's clothing",
        "women's clothing"
    ),
    val greeting: String = "",
    val userName: String = ""
)

sealed class HomeUiEvent {
    data class OnProductClicked(val productId: Int) : HomeUiEvent()
    data class OnCategoryClicked(val category: String) : HomeUiEvent()
    object OnRefresh : HomeUiEvent()
}

sealed class HomeSideEffect {
    data class NavigateToDetail(val productId: Int) : HomeSideEffect()
    data class ShowToast(val message: String) : HomeSideEffect()
}