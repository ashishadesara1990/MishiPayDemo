package com.mishipay.demo.cart.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mishipay.demo.cart.data.CartItem
import com.mishipay.demo.cart.data.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.collections.plus

class CartViewModel : ViewModel() {

    // StateFlow to hold the list of cart items.
    // MutableStateFlow allows us to update the state.
    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    // Expose an immutable StateFlow for the UI to observe.
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    // In a real app, this would be a repository or data source to fetch product data.
    // For this demo, we'll use a simple fake data source.
    private val fakeProductDataSource = FakeProductDataSource()



    /**
     * Adds a product to the cart based on its ID.
     * If the product is already in the cart, increments the quantity.
     */
    fun addProductToCart(productId: String) {
        viewModelScope.launch {
            // Look up the product by ID in your data source
            val product = fakeProductDataSource.getProductById(productId)

            product?.let {
                // Check if the product is already in the cart
                val existingItem = _cartItems.value.find { it.product.id == productId }

                if (existingItem != null) {
                    // If exists, increment the quantity of the existing item
                    // This directly updates the observable quantity
                    existingItem.quantity++
                    // Although the quantity is now observable,
                    // explicitly updating the list ensures Compose
                    // is aware of the change within the list structure.
                    _cartItems.value = _cartItems.value.toList() // Create a new list instance to trigger list update

                } else {
                    // If not exists, add a new cart item to the list
                    _cartItems.value = _cartItems.value + CartItem(
                        product = it,
                        initialQuantity = 1
                    )
                }
            }
        }
    }

    /**
     * Removes a product from the cart.
     * (You might want to add options to decrease quantity or remove completely later)
     */
    fun removeProductFromCart(productId: String) {
        viewModelScope.launch {
            _cartItems.value = _cartItems.value.filter { it.product.id != productId }
        }
    }

    fun increaseQuantity(productId: String) {
        viewModelScope.launch {
            val existingItem = _cartItems.value.find { it.product.id == productId }
            existingItem?.let {
                it.quantity++ // Directly update the observable quantity
                // Trigger list update to help Compose detect changes in the list content
                _cartItems.value = _cartItems.value.toList() // Create a new list instance
            }
        }
    }

    fun decreaseQuantity(productId: String) {
        viewModelScope.launch {
            val existingItem = _cartItems.value.find { it.product.id == productId }
            existingItem?.let {
                if (it.quantity > 1) {
                    it.quantity-- // Directly update the observable quantity
                    // Trigger list update
                    _cartItems.value = _cartItems.value.toList()
                } else {
                    // If quantity is 1, remove the item instead
                    _cartItems.value = _cartItems.value.filter { item -> item.product.id != productId }
                }
            }
        }
    }
    /**
     * Calculates the total price of items in the cart, considering discounted prices.
     */
    fun calculateTotal(): Double {
        val total = _cartItems.value.sumOf { item ->
            item.quantity * (item.product.discountedPrice ?: item.product.price)
        }
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.DOWN
        return df.format(total).toDouble()
    }
}

// Simple fake data source for products (replace with your JSON parsing logic later)
class FakeProductDataSource {
    private val products = listOf(
        Product(
            id = "12345",
            name = "Sample Product 1",
            description = "This is the first sample product.",
            imageUrl = "https://picsum.photos/300/300?random=1", // Replace with actual image URLs
            price = 19.99,
            discountedPrice = 15.99
        ),
        Product(
            id = "67890",
            name = "Another Product",
            description = "A great second product.",
            imageUrl = "https://picsum.photos/300/300?random=2", // Replace with actual image URLs
            price = 29.99,
            discountedPrice = null
        ),
        Product(
            id = "11223",
            name = "Third Item",
            description = "A nice third item.",
            imageUrl = "https://picsum.photos/300/300?random=3", // Replace with actual image URLs
            price = 9.50,
            discountedPrice = 8.00
        ),
        Product(
            id = "56789",
            name = "Forth Item",
            description = "A nice Forth item.",
            imageUrl = "https://picsum.photos/300/300?random=3", // Replace with actual image URLs
            price = 11.50,
            discountedPrice = 9.00
        ),
        Product(
            id = "11120",
            name = "Fifth Item",
            description = "A nice Fifth item.",
            imageUrl = "https://picsum.photos/300/300?random=3", // Replace with actual image URLs
            price = 13.50,
            discountedPrice = 11.00
        )
    )

    fun getProductById(productId: String): Product? {
        return products.find { it.id == productId }
    }
}