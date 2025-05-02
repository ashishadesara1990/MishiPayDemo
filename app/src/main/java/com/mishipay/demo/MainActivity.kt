package com.mishipay.demo


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mishipay.demo.cart.presentation.CartScreen
import com.mishipay.demo.cart.presentation.CartViewModel
import com.mishipay.demo.common.Screen
import com.mishipay.demo.invoice.InvoiceScreen
import com.mishipay.demo.ui.theme.MishiPayDemoTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {

            val cartViewModel: CartViewModel = viewModel()

            MishiPayDemoTheme {
                // Create a NavController
                val navController = rememberNavController()

                // Define your navigation graph
                NavHost(navController = navController, startDestination = Screen.SPLASH) {
                    composable(Screen.SPLASH) {
                        SplashScreen(navController = navController)
                    }
                    composable(Screen.CART) {
                        CartScreen(
                            navController = navController,
                            cartViewModel
                        ) // Create this composable for your Cart screen
                    }
                    composable(Screen.INVOICE) {
                        InvoiceScreen(navController = navController,cartViewModel) // Create this composable for your Cart screen
                    }
                    // Add other screens here (e.g., invoice, product_details)
                }
            }
        }
    }
}
