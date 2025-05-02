package com.mishipay.demo.cart.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import androidx.compose.ui.platform.LocalContext // Import LocalContext
import com.google.mlkit.vision.barcode.common.Barcode
import com.mishipay.demo.common.Constants.CART_ADD_PRODUCT_SCAN_BARCODE
import com.mishipay.demo.common.Constants.CART_CHECKOUT_BUTTON
import com.mishipay.demo.common.Constants.CART_EMPTY_MESSAGE
import com.mishipay.demo.common.Constants.CART_SCREEN_TITLE
import com.mishipay.demo.common.Constants.CART_TOTAL_AMOUNT
import com.mishipay.demo.common.Screen

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CartScreen(
    navController: NavController,
    cartViewModel: CartViewModel // Get ViewModel using viewModel()
) {


    val cartItems by cartViewModel.cartItems.collectAsState()
    val context = LocalContext.current // Get the current context

    Scaffold( topBar = {
        TopAppBar(
            title = { Text(CART_SCREEN_TITLE) }
        )
    },modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (cartItems.isEmpty()) {
                // Display empty cart message
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(CART_EMPTY_MESSAGE)
                }
            } else {
                // Display cart items in a list
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(cartItems) { item ->
                        CartItemRow(
                            item = item,
                            onQuantityIncrease = { productId -> cartViewModel.increaseQuantity(productId) },
                            onQuantityDecrease = { productId -> cartViewModel.decreaseQuantity(productId) },
                            onRemoveItem = { productId -> cartViewModel.removeProductFromCart(productId) }
                        )
                    }
                }

                // Display total price and checkout button
                Text(CART_TOTAL_AMOUNT+"${cartViewModel.calculateTotal()}")

                Button(onClick = { navController.navigate(Screen.INVOICE) }) {
                    Text(CART_CHECKOUT_BUTTON)
                }
            }

            // Button to trigger barcode scan
            Button(onClick = {
                // Configure and launch the Google Code Scanner
                val options = GmsBarcodeScannerOptions.Builder()
                    .setBarcodeFormats(
                        Barcode.FORMAT_ALL_FORMATS // Scan all supported formats
                    )
                    .build()
                // Use the context obtained from LocalContext.current
                val scanner = GmsBarcodeScanning.getClient(context, options) // Get scanner client

                // Start the barcode scanner activity
                scanner.startScan()
                    .addOnSuccessListener { barcode ->
                        // Task completed successfully
                        barcode.rawValue?.let { barcodeValue ->
                            // Use the scanned barcode value (product ID) to add to the cart
                            cartViewModel.addProductToCart(barcodeValue)
                        }
                    }
                    .addOnCanceledListener {
                        // Task canceled
                        // Handle cancellation if needed
                    }
                    .addOnFailureListener { e ->
                        // Task failed with an exception
                        // Handle the error, e.g., show a Toast
                    }
            }) {
                Text(CART_ADD_PRODUCT_SCAN_BARCODE)
            }
        }
    }
}