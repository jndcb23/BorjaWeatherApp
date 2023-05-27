package com.example.borjaweatherapp.utils

import org.junit.Test
import com.google.common.truth.Truth.*

class CommonHelperTest {

    @Test
    fun `conversion from Kelvin to Celsius`() {
        val kelvinValue = 300.0
        val celsiusValue = 27
        assertThat(CommonHelper.kelvinToCelsius(kelvinValue) == celsiusValue).isTrue()
    }
}