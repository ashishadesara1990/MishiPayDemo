package com.mishipay.demo.invoice

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.mishipay.demo.cart.data.CartItem
import com.mishipay.demo.cart.presentation.CartViewModel
import com.mishipay.demo.common.Constants.INVOICE_NUMBER_PREFIX
import com.mishipay.demo.common.Constants.INVOICE_ORDER_CANCEL_BUTTON
import com.mishipay.demo.common.Constants.INVOICE_ORDER_CLOSE
import com.mishipay.demo.common.Constants.INVOICE_ORDER_CONFIRM_BUTTON
import com.mishipay.demo.common.Constants.INVOICE_ORDER_CONFIRM_MESSAGE
import com.mishipay.demo.common.Constants.INVOICE_ORDER_CONFIRM_TITLE
import com.mishipay.demo.common.Constants.INVOICE_ORDER_SUCCESS
import com.mishipay.demo.common.Constants.INVOICE_PLACE_ORDER
import com.mishipay.demo.common.Constants.INVOICE_PURCHASED_PRODUCTS
import com.mishipay.demo.common.Constants.INVOICE_SCREEN_TITLE
import com.mishipay.demo.common.Constants.INVOICE_TOTAL_AMOUNT
import com.mishipay.demo.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvoiceScreen(
    navController: NavController,
    cartViewModel: CartViewModel
) {

    val cartItems by cartViewModel.cartItems.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(INVOICE_SCREEN_TITLE) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            InvoiceContent(
                invoiceNumber = (100000..999999).random(),
                products = cartItems,
                totalAmount = cartViewModel.calculateTotal()
            )
        }

    }
}

@Composable
fun InvoiceContent(
    invoiceNumber: Int,
    products: List<CartItem>,
    totalAmount: Double
) {

    var showDialog by remember { mutableStateOf(false) }
    var orderPlaceStatus by remember { mutableStateOf(false) }

    if (showDialog) {
        OrderConfirmationDialog(
            onDismiss = { showDialog = false },
            onConfirm = {
                // Perform order placement logic here
                showDialog = false
                orderPlaceStatus = true
            }
        )
    }

    if (orderPlaceStatus) {
        OrderPlacedSuccessfullyDialog(
            onDismiss = { orderPlaceStatus = false },

            )
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = INVOICE_NUMBER_PREFIX + "$invoiceNumber",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = INVOICE_PURCHASED_PRODUCTS,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        products.forEach { product ->
            ProductCard(product = product)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = INVOICE_TOTAL_AMOUNT + "$totalAmount",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(32.dp))


        Button(onClick = { showDialog = true }) {
            Text(text = INVOICE_PLACE_ORDER)
        }
    }

}

@Composable
fun OrderConfirmationDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(INVOICE_ORDER_CONFIRM_TITLE) },
        text = { Text(INVOICE_ORDER_CONFIRM_MESSAGE) },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text(INVOICE_ORDER_CONFIRM_BUTTON)
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(INVOICE_ORDER_CANCEL_BUTTON)
            }
        }
    )
}

@Composable
fun OrderPlacedSuccessfullyDialog(onDismiss: () -> Unit) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.order_succesfull),
    )

    var isPlaying by remember { mutableStateOf(true) }

    val progress by animateLottieCompositionAsState(
        composition = composition,
        isPlaying = isPlaying,
        iterations = 1
    )

    if (progress == 1f) {
        isPlaying = false
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LottieAnimation(
                    modifier = Modifier
                        .size(200.dp),
                    composition = composition,
                    progress = { progress },
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = INVOICE_ORDER_SUCCESS,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(text = INVOICE_ORDER_CLOSE)
                    }
                }
            }
        }
    }
}






