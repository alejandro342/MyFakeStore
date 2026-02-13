package com.alexdev.myfakestoreale.domain.usecase

import com.alexdev.myfakestoreale.data.local.StoreManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val storeManager: StoreManager
) {
    operator fun invoke(): Flow<String?> {
        return storeManager.getUser()
    }
}