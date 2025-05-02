package com.mishipay.demo


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mishipay.demo.cart.data.CartItem
import com.mishipay.demo.cart.data.Product
import com.mishipay.demo.cart.presentation.CartViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*


@OptIn(ExperimentalCoroutinesApi::class)
class CartViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CartViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = CartViewModel()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `addProductToCart adds new product`() = runTest {
        viewModel.addProductToCart("12345")
        testDispatcher.scheduler.advanceUntilIdle()
        val cartItems = viewModel.cartItems.value
        assertEquals(1, cartItems.size)
        assertEquals("12345", cartItems[0].product.id)
        assertEquals(1, cartItems[0].quantity)
    }

    @Test
    fun `addProductToCart increments quantity if already exists`() = runTest {
        viewModel.addProductToCart("12345")
        viewModel.addProductToCart("12345")
        testDispatcher.scheduler.advanceUntilIdle()
        val cartItems = viewModel.cartItems.value
        assertEquals(1, cartItems.size)
        assertEquals(2, cartItems[0].quantity)
    }

    @Test
    fun `removeProductFromCart removes the product`() = runTest {
        viewModel.addProductToCart("12345")
        testDispatcher.scheduler.advanceUntilIdle()
        viewModel.removeProductFromCart("12345")
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(0, viewModel.cartItems.value.size)
    }

    @Test
    fun `increaseQuantity increases quantity`() = runTest {
        viewModel.addProductToCart("12345")
        testDispatcher.scheduler.advanceUntilIdle()
        viewModel.increaseQuantity("12345")
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(2, viewModel.cartItems.value[0].quantity)
    }

    @Test
    fun `decreaseQuantity decreases quantity`() = runTest {
        viewModel.addProductToCart("12345")
        viewModel.increaseQuantity("12345") // make quantity 2
        testDispatcher.scheduler.advanceUntilIdle()
        viewModel.decreaseQuantity("12345")
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(1, viewModel.cartItems.value[0].quantity)
    }

    @Test
    fun `decreaseQuantity removes item if quantity is 1`() = runTest {
        viewModel.addProductToCart("12345")
        testDispatcher.scheduler.advanceUntilIdle()
        viewModel.decreaseQuantity("12345")
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(0, viewModel.cartItems.value.size)
    }

    @Test
    fun `calculateTotal returns correct total price`() = runTest {
        viewModel.addProductToCart("12345") // discounted: 15.99
        viewModel.addProductToCart("67890") // price: 29.99
        testDispatcher.scheduler.advanceUntilIdle()
        val total = viewModel.calculateTotal()
        // 15.99 + 29.99 = 45.98, rounded down to 45.98
        assertEquals(45.98, total, 0.001)
    }
}
