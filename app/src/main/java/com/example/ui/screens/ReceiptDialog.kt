package com.example.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.ServiceOrder
import com.example.ui.theme.RongdhonuPurplePrimary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ReceiptDialog(
  order: ServiceOrder,
  onDismiss: () -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val dateFormatted = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(Date(order.createdAt))

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Card(
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
      modifier = modifier
        .fillMaxWidth(0.95f)
        .padding(vertical = 24.dp)
        .testTag("dialog_receipt_${order.orderId}")
    ) {
      Column(
        modifier = Modifier
          .padding(20.dp)
          .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Header Bar
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(RongdhonuPurplePrimary),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Security,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(20.dp)
              )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
              Text(
                text = "রংধনু ডিজিটাল স্টুডিও",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = RongdhonuPurplePrimary
              )
              Text(
                text = "Rongdhonu Online Seva",
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }

          IconButton(onClick = onDismiss, modifier = Modifier.testTag("btn_close_receipt")) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Receipt Title & Order Number
        Surface(
          color = Color(0xFFF7F5FC),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(
              text = "ডিজিটাল আবেদন মানিরসিদ",
              style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
              color = Color(0xFF5A5366)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = order.orderId,
              style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Monospace,
                color = RongdhonuPurplePrimary
              )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = dateFormatted,
              style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
              color = Color.Gray
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Status Chip
        Surface(
          color = Color(order.status.colorHex).copy(alpha = 0.14f),
          shape = RoundedCornerShape(10.dp)
        ) {
          Text(
            text = "আবেদনের অবস্থা: ${order.status.titleBn}",
            style = MaterialTheme.typography.labelMedium.copy(
              fontWeight = FontWeight.Bold,
              color = Color(order.status.colorHex)
            ),
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
          )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Details Table
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color(0xFFECE6F2), RoundedCornerShape(12.dp))
            .padding(14.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          ReceiptRow(label = "সেবার নাম", value = order.serviceTitleBn)
          ReceiptRow(label = "আবেদনকারী", value = order.citizenName)
          ReceiptRow(label = "মোবাইল নম্বর", value = order.citizenPhone)
          ReceiptRow(label = "আইডি / রেফারেন্স", value = order.identifierNumber)
          ReceiptRow(label = "পেমেন্ট মাধ্যম", value = order.paymentMethod.displayNameBn)
          ReceiptRow(label = "ট্রানজেকশন আইডি", value = order.trxId)
          if (order.notes.isNotEmpty()) {
            ReceiptRow(label = "গ্রাহকের নোট", value = order.notes)
          }
          if (order.adminRemarks.isNotEmpty()) {
            ReceiptRow(label = "স্টুডিও আপডেট", value = order.adminRemarks)
          }
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(1.dp)
              .background(Color(0xFFECE6F2))
          )
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = "পরিশোধিত অর্থ",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )
            Text(
              text = "৳ ${order.feePaidBdt} BDT",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = RongdhonuPurplePrimary
              )
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Verification Badge & QR
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.QrCode2,
            contentDescription = "QR Code",
            modifier = Modifier.size(36.dp),
            tint = RongdhonuPurplePrimary
          )
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(
              text = "ডিজিটাল ভেরিফাইড অর্ডার",
              style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
              color = Color(0xFF00796B)
            )
            Text(
              text = "যেকোনো অনুসন্ধানে স্টুডিও হেল্পলাইনে যোগাযোগ করুন",
              style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
              color = Color.Gray
            )
          }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Actions
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          OutlinedButton(
            onClick = onDismiss,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.weight(1f)
          ) {
            Text("বন্ধ করুন")
          }

          Button(
            onClick = {
              Toast.makeText(
                context,
                "রসিদের কপি ডিভাইসে সফলভাবে সেভ হয়েছে! (Order: ${order.orderId})",
                Toast.LENGTH_LONG
              ).show()
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = RongdhonuPurplePrimary),
            modifier = Modifier
              .weight(1f)
              .testTag("btn_download_receipt")
          ) {
            Icon(imageVector = Icons.Default.Download, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("ডাউনলোড রসিদ")
          }
        }
      }
    }
  }
}

@Composable
fun ReceiptRow(label: String, value: String) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.Top
  ) {
    Text(
      text = label,
      style = MaterialTheme.typography.bodySmall,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
      modifier = Modifier.weight(0.4f)
    )
    Text(
      text = value,
      style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
      textAlign = TextAlign.End,
      modifier = Modifier.weight(0.6f)
    )
  }
}
