package com.mishipay.demo.invoice

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.mishipay.demo.cart.data.CartItem


@Composable
fun ProductCard(
    product: CartItem,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = product.product.imageUrl,
                contentDescription = product.product.name,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 8.dp),
                contentScale = ContentScale.Crop // Crop the image to fit
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = product.product.name,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = product.product.description,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "$${product.product.discountedPrice ?: product.product.price} x ${product.quantity} = $${(product.product.discountedPrice ?: product.product.price) * product.quantity}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}