package com.passexample.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.passexample.data.Pass
import com.passexample.data.PassType
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*


class MainViewModelTest {

    @get:Rule
    val instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        viewModel = MainViewModel()
    }

    @Test
    fun adjustExpirationDate_plusFourHour_postResult() {
        // Arrange
        mockkStatic(Calendar::class)
        val mockCalendar = mockk<Calendar>(relaxed = true)

        every {
            Calendar.getInstance()
        } returns mockCalendar

        // Act
        viewModel.adjustExpirationDate(Pass(PassType.HOUR, 4, "", true))
        // Assert
        verify {
            mockCalendar.add(Calendar.HOUR, 4)
        }
        viewModel.expiredDate.observeForTesting {
            assertEquals(viewModel.expiredDate.value, Calendar.getInstance().time)
        }
    }

    @Test
    fun adjustExpirationDate_plusOneDay_postResult() {
        // Arrange
        mockkStatic(Calendar::class)
        val mockCalendar = mockk<Calendar>(relaxed = true)

        every {
            Calendar.getInstance()
        } returns mockCalendar

        // Act
        viewModel.adjustExpirationDate(Pass(PassType.DAY, 1, "", true))
        // Assert
        verify {
            mockCalendar.add(Calendar.DATE, 1)
            mockCalendar[Calendar.SECOND] = 59
            mockCalendar[Calendar.MINUTE] = 59
            mockCalendar[Calendar.HOUR_OF_DAY] = 23
        }
        viewModel.expiredDate.observeForTesting {
            assertEquals(viewModel.expiredDate.value, Calendar.getInstance().time)
        }
    }
}