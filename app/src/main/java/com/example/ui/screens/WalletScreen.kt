package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.PaymentGatewayConfig
import com.example.data.model.PaymentMethod
import com.example.data.model.TransactionType
import com.example.data.model.WalletTransaction
import com.example.ui.RongdhonuViewModel
import com.example.ui.theme.BkashPink
import com.example.ui.theme.NagadOrange
import com.example.ui.theme.RocketViolet
import com.example.ui.theme.RongdhonuAccentCyan
import com.example.ui.theme.RongdhonuAccentOrange
import com.example.ui.theme.RongdhonuPurpleDark
import com.example.ui.theme.RongdhonuPurpleLight
import com.example.ui.theme.RongdhonuPurplePrimary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun WalletScreen(
  viewModel: RongdhonuViewModel,
  modifier: Modifier = Modifier
) {
  val balance by viewModel.repository.walletBalance.collectAsState()
  val transactions by viewModel.repository.transactions.collectAsState()
  val gateways by viewModel.repository.gateways.collectAsState()
  val showRechargeDialog by viewModel.showRechargeDialog.collectAsState()

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    contentPadding = PaddingValues(top = 12.dp, bottom = 24.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // Wallet Hero Card
    item {
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = Modifier
          .fillMaxWidth()
          .shadow(elevation = 6.dp, shape = RoundedCornerShape(20.dp))
          .background(
            brush = Brush.verticalGradient(
              listOf(RongdhonuPurpleDark, RongdhonuPurplePrimary, RongdhonuPurpleLight)
            ),
            shape = RoundedCornerShape(20.dp)
          )
      ) {
        Column(modifier = Modifier.padding(20.dp)) {
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
                  .background(Color.White.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.AccountBalanceWallet,
                  contentDescription = null,
                  tint = RongdhonuAccentCyan,
                  modifier = Modifier.size(20.dp)
                )
              }
              Spacer(modifier = Modifier.width(10.dp))
              Text(
                text = "রংধনু ডিজিটাল ওয়ালেট",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.White
              )
            }

            Surface(
              color = Color.White.copy(alpha = 0.15f),
              shape = RoundedCornerShape(8.dp)
            ) {
              Text(
                text = "BDT ৳",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                color = Color.White,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(18.dp))

          Text(
            text = "মোট চলতি ব্যালেন্স",
            style = MaterialTheme.typography.bodySmall,
            color = Color.White.copy(alpha = 0.8f)
          )
          Text(
            text = "৳ $balance",
            style = MaterialTheme.typography.headlineLarge.copy(
              fontWeight = FontWeight.ExtraBold,
              fontSize = 34.sp
            ),
            color = Color.White
          )

          Spacer(modifier = Modifier.height(18.dp))

          Button(
            onClick = { viewModel.openRechargeDialog() },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = RongdhonuAccentOrange,
              contentColor = Color.White
            ),
            modifier = Modifier
              .fillMaxWidth()
              .height(46.dp)
              .testTag("btn_wallet_recharge")
          ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              "ব্যালেন্স রিচার্জ করুন (bKash / Nagad / Rocket)",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )
          }
        }
      }
    }

    // Supported Payment Gateways Card
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "স্বীকৃত মোবাইল ব্যাংকিং মাধ্যমসমূহ",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
          )
          Spacer(modifier = Modifier.height(12.dp))

          gateways.filter { it.isEnabled }.forEach { gw ->
            val color = when (gw.method) {
              PaymentMethod.BKASH -> BkashPink
              PaymentMethod.NAGAD -> NagadOrange
              PaymentMethod.ROCKET -> RocketViolet
              else -> RongdhonuPurplePrimary
            }
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                  modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(color)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                  Text(
                    text = gw.method.displayNameBn,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                  )
                  Text(
                    text = gw.accountTypeBn,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                  )
                }
              }

              Surface(
                color = color.copy(alpha = 0.1f),
                shape = RoundedCornerShape(6.dp)
              ) {
                Text(
                  text = gw.accountNumber,
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = color
                  ),
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
              }
            }
          }
        }
      }
    }

    // Transaction Ledger Header
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.History,
            contentDescription = null,
            tint = RongdhonuPurplePrimary,
            modifier = Modifier.size(20.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "লেনদেন হিস্ট্রি ও লেজার",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
          )
        }

        Text(
          text = "${transactions.size} টি এন্ট্রি",
          style = MaterialTheme.typography.labelSmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }

    // Transactions list
    items(transactions) { trx ->
      TransactionRowCard(trx = trx)
    }
  }

  if (showRechargeDialog) {
    RechargeWalletDialog(
      gateways = gateways,
      onDismiss = { viewModel.closeRechargeDialog() },
      onConfirm = { amount, method, trxId ->
        viewModel.rechargeWallet(amount, method, trxId)
      }
    )
  }
}

@Composable
fun TransactionRowCard(trx: WalletTransaction) {
  val dateFormatted = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault()).format(Date(trx.timestamp))
  val isCredit = trx.type == TransactionType.RECHARGE || trx.type == TransactionType.REFUND

  Card(
    shape = RoundedCornerShape(12.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    modifier = Modifier.fillMaxWidth()
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
          modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(if (isCredit) Color(0xFFE8F5E9) else Color(0xFFFFEBEE)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = if (isCredit) Icons.Default.ArrowDownward else Icons.Default.ArrowUpward,
            contentDescription = null,
            tint = if (isCredit) Color(0xFF2E7D32) else Color(0xFFC62828),
            modifier = Modifier.size(18.dp)
          )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column {
          Text(
            text = trx.type.titleBn,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
          )
          Text(
            text = "TrxID: ${trx.trxId} • $dateFormatted",
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }

      Column(horizontalAlignment = Alignment.End) {
        Text(
          text = "${if (isCredit) "+" else "-"} ৳${trx.amountBdt}",
          style = MaterialTheme.typography.titleSmall.copy(
            fontWeight = FontWeight.Bold,
            color = if (isCredit) Color(0xFF2E7D32) else Color(0xFFC62828)
          )
        )
        Text(
          text = trx.method.displayNameBn.split(" ").first(),
          style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
          color = Color.Gray
        )
      }
    }
  }
}

@Composable
fun RechargeWalletDialog(
  gateways: List<PaymentGatewayConfig>,
  onDismiss: () -> Unit,
  onConfirm: (Int, PaymentMethod, String) -> Unit
) {
  var amountText by remember { mutableStateOf("500") }
  var selectedMethod by remember { mutableStateOf(PaymentMethod.BKASH) }
  var trxId by remember { mutableStateOf("") }
  var errorMsg by remember { mutableStateOf<String?>(null) }

  val presetAmounts = listOf(100, 200, 500, 1000)

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Card(
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      modifier = Modifier
        .fillMaxWidth(0.92f)
        .padding(16.dp)
    ) {
      Column(modifier = Modifier.padding(20.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "ওয়ালেট ব্যালেন্স রিচার্জ",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = RongdhonuPurplePrimary
          )
          IconButton(onClick = onDismiss) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
          text = "রিচার্জের পরিমাণ (BDT)",
          style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(6.dp))

        // Preset chips
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          presetAmounts.forEach { preset ->
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = if (amountText == preset.toString()) RongdhonuPurplePrimary else Color(0xFFF3EFF8),
              modifier = Modifier
                .weight(1f)
                .clickable { amountText = preset.toString() }
            ) {
              Text(
                text = "৳$preset",
                style = MaterialTheme.typography.labelMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = if (amountText == preset.toString()) Color.White else RongdhonuPurplePrimary
                ),
                modifier = Modifier.padding(vertical = 8.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
          value = amountText,
          onValueChange = { amountText = it },
          label = { Text("অন্য পরিমাণ লিখুন (টাকা)") },
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
          singleLine = true,
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_recharge_amount")
        )

        Spacer(modifier = Modifier.height(14.dp))

        Text(
          text = "পেমেন্ট মাধ্যম",
          style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(6.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          listOf(PaymentMethod.BKASH, PaymentMethod.NAGAD, PaymentMethod.ROCKET).forEach { method ->
            val isSelected = selectedMethod == method
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = if (isSelected) RongdhonuPurplePrimary else Color(0xFFF3EFF8),
              modifier = Modifier
                .weight(1f)
                .clickable { selectedMethod = method }
            ) {
              Text(
                text = method.displayNameBn.split(" ").first(),
                style = MaterialTheme.typography.labelMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = if (isSelected) Color.White else Color.Black
                ),
                modifier = Modifier.padding(vertical = 8.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Selected Gateway instructions
        val activeGw = gateways.firstOrNull { it.method == selectedMethod }
        if (activeGw != null) {
          Surface(
            color = Color(0xFFF5F3FA),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(10.dp)) {
              Text(
                text = "নম্বর: ${activeGw.accountNumber}",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
              )
              Text(
                text = activeGw.instructionsBn,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
          value = trxId,
          onValueChange = { trxId = it; errorMsg = null },
          label = { Text("ট্রানজেকশন আইডি (TrxID) *") },
          placeholder = { Text("যেমন: BK9284102") },
          singleLine = true,
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_recharge_trx_id")
        )

        if (errorMsg != null) {
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = errorMsg ?: "",
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.error)
          )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Button(
          onClick = {
            val amt = amountText.toIntOrNull()
            if (amt == null || amt < 20) {
              errorMsg = "সর্বনিম্ন ২০ টাকা রিচার্জ করুন"
              return@Button
            }
            if (trxId.isBlank()) {
              errorMsg = "TrxID প্রদান করা আবশ্যক"
              return@Button
            }
            onConfirm(amt, selectedMethod, trxId)
          },
          shape = RoundedCornerShape(10.dp),
          colors = ButtonDefaults.buttonColors(containerColor = RongdhonuPurplePrimary),
          modifier = Modifier
            .fillMaxWidth()
            .height(46.dp)
            .testTag("btn_confirm_recharge")
        ) {
          Text(
            text = "রিচার্জ নিশ্চিত করুন",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
          )
        }
      }
    }
  }
}
