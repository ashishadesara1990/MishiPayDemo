package com.mishipay.demo.cart.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import com.mishipay.demo.cart.data.Product

data class CartItem(
    val product: Product,
    val initialQuantity: Int = 0 // Receive the initial quantity as a constructor parameter
) {
    // Declare quantity as a mutable property within the class body
    // and make it observable using mutableStateOf
    var quantity: Int by mutableIntStateOf(initialQuantity)
}