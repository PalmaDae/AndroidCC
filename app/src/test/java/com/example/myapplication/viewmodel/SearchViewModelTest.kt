package com.example.myapplication.viewmodel

import com.example.myapplication.data.model.City
import com.example.myapplication.data.model.DonorPointDto
import com.example.myapplication.data.model.DonorResponse
import com.example.myapplication.data.network.RetrofitHelper
import com.example.myapplication.utils.AnalyticsLogger
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val analyticsLogger = mockk<AnalyticsLogger>(relaxed = true)
    private lateinit var viewModel: SearchViewModel

    private val mockCity = City(title = "Казань")
    private val mockDto = DonorPointDto(
        id = 42,
        title = "Республиканский центр крови",
        address = "ул. Проспект Победы, 85",
        city = mockCity
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockkObject(RetrofitHelper)
        viewModel = SearchViewModel(analyticsLogger)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkObject(RetrofitHelper)
    }


    @Test
    fun `GetPointsByCityUseCase returns empty list immediately if city query is blank`() = runTest(testDispatcher) {
        val useCase = GetPointsByCityUseCase()
        val result = useCase("   ")

        assertTrue(result.isEmpty())
        coVerify(exactly = 0) { RetrofitHelper.api.getAllPoints(any()) }
    }

    @Test
    fun `GetPointDetailUseCase returns null if pointId is invalid`() = runTest(testDispatcher) {
        val useCase = GetPointDetailUseCase()
        val result = useCase(0)

        assertNull(result)
        coVerify(exactly = 0) { RetrofitHelper.api.getDetail(any()) }
    }


    @Test
    fun `load method successfully updates state with mapped model list`() = runTest(testDispatcher) {
        val fakeResponse = DonorResponse(count = 1, results = listOf(mockDto))
        coEvery { RetrofitHelper.api.getAllPoints("kazan") } returns fakeResponse

        viewModel.load("kazan")

        val expectedModel = mockDto.toModel()

        assertEquals(1, viewModel.uiState.value.points.size)
        assertEquals(expectedModel.id, viewModel.uiState.value.points.first().id)
        assertEquals(expectedModel.titleOfPoint, viewModel.uiState.value.points.first().titleOfPoint)
        assertFalse(viewModel.uiState.value.isLoading)
        assertNull(viewModel.uiState.value.error)

        coVerify(exactly = 1) { RetrofitHelper.api.getAllPoints("kazan") }
    }

    @Test
    fun `load method updates state with error text when server response is empty`() = runTest(testDispatcher) {
        val emptyResponse = DonorResponse(count = 0, results = emptyList())
        coEvery { RetrofitHelper.api.getAllPoints("emptycity") } returns emptyResponse

        viewModel.load("emptycity")

        assertTrue(viewModel.uiState.value.points.isEmpty())
        assertEquals("Пункты не найдены", viewModel.uiState.value.error)
        assertFalse(viewModel.uiState.value.isLoading)
    }
}