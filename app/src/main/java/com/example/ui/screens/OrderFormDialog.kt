package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Payment
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.CitizenService
import com.example.data.model.PaymentMethod
import com.example.ui.RongdhonuViewModel
import com.example.ui.theme.BkashPink
import com.example.ui.theme.NagadOrange
import com.example.ui.theme.RocketViolet
import com.example.ui.theme.RongdhonuPurplePrimary

@Composable
fun OrderFormDialog(
  service: CitizenService,
  viewModel: RongdhonuViewModel,
  onDismiss: () -> Unit,
  modifier: Modifier = Modifier
) {
  val walletBalance by viewModel.repository.walletBalance.collectAsState()
  var citizenName by remember { mutableStateOf("") }
  var citizenPhone by remember { mutableStateOf("") }
  var identifierNumber by remember { mutableStateOf("") }
  var notes by remember { mutableStateOf("") }
  var selectedMethod by remember { mutableStateOf(PaymentMethod.WALLET) }
  var trxId by remember { mutableStateOf("") }
  var validationError by remember { mutableStateOf<String?>(null) }

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Card(
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      modifier = modifier
        .fillMaxWidth(0.95f)
        .padding(vertical = 20.dp)
    ) {
      Column(
        modifier = Modifier
          .padding(20.dp)
          .verticalScroll(rememberScrollState())
      ) {
        // Header
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "অনলাইন আবেদন ফর্ম",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
              color = RongdhonuPurplePrimary
            )
            Text(
              text = service.titleBn,
              style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
          IconButton(onClick = onDismiss, modifier = Modifier.testTag("btn_close_order_dialog")) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Total fee summary
        Surface(
          color = RongdhonuPurplePrimary.copy(alpha = 0.08f),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "নির্ধারিত মোট সার্ভিস ফি",
              style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
            )
            Text(
              text = "৳ ${service.totalFeeBdt}",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = RongdhonuPurplePrimary
              )
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Input: Citizen Name
        OutlinedTextField(
          value = citizenName,
          onValueChange = { citizenName = it; validationError = null },
          label = { Text("আবেদনকারীর পূর্ণ নাম *") },
          placeholder = { Text("যেমন: মোঃ সাইফুল ইসলাম") },
          singleLine = true,
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_citizen_name")
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Input: Phone Number
        OutlinedTextField(
          value = citizenPhone,
          onValueChange = { citizenPhone = it; validationError = null },
          label = { Text("মোবাইল নম্বর (যোগাযোগের জন্য) *") },
          placeholder = { Text("017XXXXXXXX") },
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
          singleLine = true,
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_citizen_phone")
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Input: Identifier
        OutlinedTextField(
          value = identifierNumber,
          onValueChange = { identifierNumber = it; validationError = null },
          label = { Text("NID / জন্ম নিবন্ধন / রেফারেন্স নম্বর *") },
          placeholder = { Text("১৭ ডিজিটের জন্ম সনদ বা ১০/১৭ ডিজিট NID") },
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
          singleLine = true,
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_identifier_number")
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Input: Notes
        OutlinedTextField(
          value = notes,
          onValueChange = { notes = it },
          label = { Text("বিশেষ নির্দেশনা বা চাহিত সংশোধন (ঐচ্ছিক)") },
          placeholder = { Text("যেমন: পিতার নাম সংশোধন, দ্রুত ডেলিভারি ইত্যাদি") },
          maxLines = 3,
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_order_notes")
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Payment Method Header
        Text(
          text = "পেমেন্ট মাধ্যম নির্বাচন করুন *",
          style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Methods list
        PaymentMethodOption(
          method = PaymentMethod.WALLET,
          title = "ওয়ালেট ব্যালেন্স (বর্তমান: ৳$walletBalance)",
          subtitle = if (walletBalance >= service.totalFeeBdt) "তাৎক্ষণিক এক-ক্লিকে পরিশোধ হবে" else "পর্যাপ্ত ব্যালেন্স নেই (রিচার্জ করুন)",
          isSelected = selectedMethod == PaymentMethod.WALLET,
          accentColor = RongdhonuPurplePrimary,
          onSelect = { selectedMethod = PaymentMethod.WALLET; validationError = null }
        )

        Spacer(modifier = Modifier.height(8.dp))

        PaymentMethodOption(
          method = PaymentMethod.BKASH,
          title = "বিকাশ (bKash Send Money)",
          subtitle = "01632-042441 নম্বরে ৳${service.totalFeeBdt} পাঠিয়ে TrxID দিন",
          isSelected = selectedMethod == PaymentMethod.BKASH,
          accentColor = BkashPink,
          onSelect = { selectedMethod = PaymentMethod.BKASH; validationError = null }
        )

        Spacer(modifier = Modifier.height(8.dp))

        PaymentMethodOption(
          method = PaymentMethod.NAGAD,
          title = "নগদ (Nagad Send Money)",
          subtitle = "01632-042441 নম্বরে ৳${service.totalFeeBdt} পাঠিয়ে TrxID দিন",
          isSelected = selectedMethod == PaymentMethod.NAGAD,
          accentColor = NagadOrange,
          onSelect = { selectedMethod = PaymentMethod.NAGAD; validationError = null }
        )

        Spacer(modifier = Modifier.height(8.dp))

        PaymentMethodOption(
          method = PaymentMethod.ROCKET,
          title = "রকেট (Rocket Send Money)",
          subtitle = "01632-042441-2 নম্বরে ৳${service.totalFeeBdt} পাঠিয়ে TrxID দিন",
          isSelected = selectedMethod == PaymentMethod.ROCKET,
          accentColor = RocketViolet,
          onSelect = { selectedMethod = PaymentMethod.ROCKET; validationError = null }
        )

        // If mobile banking is selected, ask for TrxID
        if (selectedMethod != PaymentMethod.WALLET) {
          Spacer(modifier = Modifier.height(12.dp))
          OutlinedTextField(
            value = trxId,
            onValueChange = { trxId = it; validationError = null },
            label = { Text("${selectedMethod.displayNameBn} ট্রানজেকশন আইডি (TrxID) *") },
            placeholder = { Text("যেমন: BK9X48210") },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("input_order_trx_id")
          )
        }

        if (validationError != null) {
          Spacer(modifier = Modifier.height(10.dp))
          Text(
            text = validationError ?: "",
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.error)
          )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Submit Button
        Button(
          onClick = {
            if (citizenName.isBlank()) {
              validationError = "আবেদনকারীর নাম লিখুন"
              return@Button
            }
            if (citizenPhone.isBlank() || citizenPhone.length < 10) {
              validationError = "সঠিক মোবাইল নম্বর প্রদান করুন"
              return@Button
            }
            if (identifierNumber.isBlank()) {
              validationError = "পরিচয়পত্র বা রেফারেন্স নম্বর প্রদান করুন"
              return@Button
            }
            if (selectedMethod == PaymentMethod.WALLET && walletBalance < service.totalFeeBdt) {
              validationError = "ওয়ালেটে পর্যাপ্ত ব্যালেন্স নেই। বিকাশ/নগদে পরিশোধ করুন অথবা ওয়ালেট রিচার্জ করুন।"
              return@Button
            }
            if (selectedMethod != PaymentMethod.WALLET && trxId.isBlank()) {
              validationError = "পেমেন্টের TrxID প্রদান করা আবশ্যক।"
              return@Button
            }

            viewModel.submitOrder(
              service = service,
              citizenName = citizenName,
              citizenPhone = citizenPhone,
              idNumber = identifierNumber,
              notes = notes,
              paymentMethod = selectedMethod,
              trxId = trxId
            )
          },
          shape = RoundedCornerShape(12.dp),
          colors = ButtonDefaults.buttonColors(containerColor = RongdhonuPurplePrimary),
          modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .testTag("btn_submit_order_form")
        ) {
          Text(
            text = "আবেদন নিশ্চিত করুন ও জমা দিন",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
            color = Color.White
          )
        }
      }
    }
  }
}

@Composable
fun PaymentMethodOption(
  method: PaymentMethod,
  title: String,
  subtitle: String,
  isSelected: Boolean,
  accentColor: Color,
  onSelect: () -> Unit
) {
  Surface(
    shape = RoundedCornerShape(12.dp),
    color = if (isSelected) accentColor.copy(alpha = 0.08f) else Color.White,
    border = androidx.compose.foundation.BorderStroke(
      width = if (isSelected) 2.dp else 1.dp,
      color = if (isSelected) accentColor else Color(0xFFE2DCED)
    ),
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onSelect() }
      .testTag("option_pay_${method.name.lowercase()}")
  ) {
    Row(
      modifier = Modifier.padding(12.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Box(
        modifier = Modifier
          .size(20.dp)
          .border(2.dp, if (isSelected) accentColor else Color.Gray, RoundedCornerShape(10.dp))
          .padding(3.dp),
        contentAlignment = Alignment.Center
      ) {
        if (isSelected) {
          Box(
            modifier = Modifier
              .size(10.dp)
              .background(accentColor, RoundedCornerShape(5.dp))
          )
        }
      }
      Spacer(modifier = Modifier.width(12.dp))
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = title,
          style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
          )
        )
        Text(
          text = subtitle,
          style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    }
  }
}
