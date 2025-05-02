package com.mishipay.demo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.mishipay.demo.common.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Text("MishiPay", style = MaterialTheme.typography.headlineMedium)
    }

    LaunchedEffect(key1 = true) {
        delay(2000) // Delay for 2 seconds
        navController.navigate(Screen.CART) {
            popUpTo(Screen.SPLASH) { inclusive = true }
        }
    }
}