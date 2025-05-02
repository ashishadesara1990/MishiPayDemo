package com.mishipay.demo.cart.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.mishipay.demo.cart.data.CartItem
import com.mishipay.demo.common.Constants.CART_DECREASE_QUANTITY
import com.mishipay.demo.common.Constants.CART_INCREASE_QUANTITY
import com.mishipay.demo.common.Constants.CART_REMOVE_ITEM
import com.mishipay.demo.R

@Composable
fun CartItemRow(
    item: CartItem,
    onQuantityIncrease: (String) -> Unit, // Callback for increasing quantity
    onQuantityDecrease: (String) -> Unit, // Callback for decreasing quantity
    onRemoveItem: (String) -> Unit // Callback for removing the item
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Image (using Coil)
            AsyncImage(
                model = item.product.imageUrl,
                contentDescription = item.product.name,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 8.dp),
                contentScale = ContentScale.Crop // Crop the image to fit
            )

            Column(
                modifier = Modifier.weight(1f) // Take up remaining space
            ) {
                // Product Name
                Text(
                    text = item.product.name,
                    style = MaterialTheme.typography.titleMedium
                )

                // Product Description
                Text(
                    text = item.product.description,
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Price Display
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (item.product.discountedPrice != null) {
                        // Show discounted price and strikethrough original price
                        Text(
                            text = "$${item.product.discountedPrice}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary // Highlight discounted price
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "$${item.product.price}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                textDecoration = TextDecoration.LineThrough,
                                color = Color.Gray // Strikethrough original price
                            )
                        )
                    } else {
                        // Show regular price
                        Text(
                            text = "$${item.product.price}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            // Quantity Controls and Remove Button
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Remove Button
                IconButton(onClick = { onRemoveItem(item.product.id) }) {
                    Icon(
                        painter = painterResource(R.drawable.delete),
                        contentDescription = CART_REMOVE_ITEM,
                        tint = Color.Red // Use a red tint for removal
                    )
                }

                // Quantity and controls
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Decrease Quantity Button
                    IconButton(
                        onClick = { onQuantityDecrease(item.product.id) },
                        enabled = item.quantity > 1 // Disable if quantity is 1
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.remove_circle),
                            contentDescription = CART_DECREASE_QUANTITY
                        )
                    }

                    // Quantity Text
                    Text(
                        text = item.quantity.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )

                    // Increase Quantity Button
                    IconButton(onClick = { onQuantityIncrease(item.product.id) }) {
                        Icon(
                            painter = painterResource(R.drawable.add_circle),
                            contentDescription = CART_INCREASE_QUANTITY
                        )
                    }
                }
            }
        }
    }
}