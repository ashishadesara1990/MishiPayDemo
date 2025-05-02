package com.mishipay.demo.cart.data

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val price: Double,
    val discountedPrice: Double? = null // Nullable for products without a discount
)