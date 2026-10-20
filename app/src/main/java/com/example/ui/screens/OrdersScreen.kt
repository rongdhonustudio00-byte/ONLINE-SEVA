package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.OrderStatus
import com.example.ui.BottomNavTab
import com.example.ui.RongdhonuViewModel
import com.example.ui.components.OrderItemCard
import com.example.ui.theme.RongdhonuPurplePrimary

@Composable
fun OrdersScreen(
  viewModel: RongdhonuViewModel,
  onNavigateToServices: () -> Unit,
  modifier: Modifier = Modifier
) {
  val orders by viewModel.repository.orders.collectAsState()
  var filterStatus by remember { mutableStateOf<OrderStatus?>(null) }

  val filtered = if (filterStatus == null) orders else orders.filter { it.status == filterStatus }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    contentPadding = PaddingValues(top = 12.dp, bottom = 24.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // Header & Filter Row
    item {
      Column {
        Text(
          text = "আবেদন ও অর্ডারের অবস্থা",
          style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
          color = MaterialTheme.colorScheme.onSurface
        )
        Text(
          text = "আপনার সকল জমা দেওয়া আবেদনের লাইভ ট্র্যাকিং ও মানিরসিদ",
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Status Filter Chips
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          StatusFilterChip(
            label = "সকল (${orders.size})",
            isSelected = filterStatus == null,
            onClick = { filterStatus = null }
          )
          StatusFilterChip(
            label = "প্রক্রিয়াধীন",
            isSelected = filterStatus == OrderStatus.PROCESSING,
            onClick = { filterStatus = OrderStatus.PROCESSING }
          )
          StatusFilterChip(
            label = "সম্পন্ন",
            isSelected = filterStatus == OrderStatus.COMPLETED,
            onClick = { filterStatus = OrderStatus.COMPLETED }
          )
          StatusFilterChip(
            label = "অপেক্ষমান",
            isSelected = filterStatus == OrderStatus.PENDING,
            onClick = { filterStatus = OrderStatus.PENDING }
          )
        }
      }
    }

    if (filtered.isEmpty()) {
      item {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 60.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Box(
            modifier = Modifier
              .size(64.dp)
              .clip(CircleShape)
              .background(RongdhonuPurplePrimary.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Assignment,
              contentDescription = null,
              tint = RongdhonuPurplePrimary,
              modifier = Modifier.size(32.dp)
            )
          }
          Spacer(modifier = Modifier.height(14.dp))
          Text(
            text = "কোনো আবেদন পাওয়া যায়নি",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "আপনি এখনও কোনো সেবার জন্য আবেদন করেননি।",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
          Spacer(modifier = Modifier.height(16.dp))
          Button(
            onClick = onNavigateToServices,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = RongdhonuPurplePrimary),
            modifier = Modifier.testTag("btn_empty_start_service")
          ) {
            Text("নাগরিক সেবা নির্বাচন করুন")
          }
        }
      }
    } else {
      items(filtered) { order ->
        OrderItemCard(
          order = order,
          onViewReceipt = { viewModel.openReceipt(order) }
        )
      }
    }
  }
}

@Composable
fun StatusFilterChip(
  label: String,
  isSelected: Boolean,
  onClick: () -> Unit
) {
  Surface(
    shape = RoundedCornerShape(16.dp),
    color = if (isSelected) RongdhonuPurplePrimary else Color.White,
    border = if (isSelected) null else androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2DCED)),
    modifier = Modifier.clickable { onClick() }
  ) {
    Text(
      text = label,
      style = MaterialTheme.typography.labelMedium.copy(
        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
        fontSize = 11.sp
      ),
      color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
      modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
    )
  }
}
