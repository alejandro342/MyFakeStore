package com.alexdev.myfakestoreale.domain.usecase

import java.util.Calendar
import javax.inject.Inject

class GetGreetingUseCase @Inject constructor() {

    operator fun invoke(): String {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)

        return when (hour) {
            in 5..11 -> "Buenos días"
            in 12..19 -> "Buenas tardes"
            else -> "Buenas noches"
        }
    }
}