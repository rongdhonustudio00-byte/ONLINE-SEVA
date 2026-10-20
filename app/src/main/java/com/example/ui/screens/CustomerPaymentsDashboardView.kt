package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.MoneyOff
import androidx.compose.material.icons.filled.Paid
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PriceCheck
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.CustomerPaymentRecord
import com.example.data.model.PaymentMethod
import com.example.data.model.PaymentVerificationStatus
import com.example.ui.RongdhonuViewModel
import com.example.ui.theme.BkashPink
import com.example.ui.theme.NagadOrange
import com.example.ui.theme.RocketViolet
import com.example.ui.theme.RongdhonuAccentOrange
import com.example.ui.theme.RongdhonuGreen
import com.example.ui.theme.RongdhonuPurpleDark
import com.example.ui.theme.RongdhonuPurplePrimary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CustomerPaymentsDashboardView(
  viewModel: RongdhonuViewModel,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val payments by viewModel.repository.paymentRecords.collectAsState()
  val filteredPayments by viewModel.filteredPaymentRecords.collectAsState()
  val searchQuery by viewModel.paymentSearchQuery.collectAsState()
  val statusFilter by viewModel.paymentStatusFilter.collectAsState()
  val methodFilter by viewModel.paymentMethodFilter.collectAsState()
  val selectedForVerification by viewModel.selectedPaymentForVerification.collectAsState()
  val selectedForRefund by viewModel.selectedPaymentForRefund.collectAsState()

  var showExportReportDialog by remember { mutableStateOf(false) }

  // Calculation from real database records
  val totalPaymentVolume = payments.sumOf { it.amountSentBdt }
  val verifiedTotal = payments.filter {
    it.status == PaymentVerificationStatus.VERIFIED ||
      it.status == PaymentVerificationStatus.FULLY_PAID ||
      it.status == PaymentVerificationStatus.PARTIALLY_PAID
  }.sumOf { it.verifiedAmountBdt }

  val pendingAmount = payments.filter {
    it.status == PaymentVerificationStatus.PENDING || it.status == PaymentVerificationStatus.SUBMITTED
  }.sumOf { it.amountSentBdt }

  val totalDues = payments.sumOf { it.dueAmountBdt }
  val totalRefunded = payments.filter { it.status == PaymentVerificationStatus.REFUNDED }.sumOf { it.refundAmountBdt }
  val uniqueCustomersCount = payments.map { it.customerId }.distinct().size

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(Color(0xFFF7F5FC))
      .padding(horizontal = 14.dp),
    contentPadding = PaddingValues(top = 14.dp, bottom = 48.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // 1. Finance Overview Stats Grid
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .background(Color.White, RoundedCornerShape(16.dp))
          .border(1.dp, Color(0xFFEFEAF6), RoundedCornerShape(16.dp))
          .padding(14.dp)
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = "গ্রাহক পেমেন্ট ও লেনদেন ড্যাশবোর্ড",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
              color = Color(0xFF1E1738)
            )
            Text(
              text = "প্রকৃত লেনদেন ও বকেয়া হিসাব (স্বয়ংক্রিয় গণনাকৃত)",
              style = MaterialTheme.typography.bodySmall,
              color = Color(0xFF6B6580)
            )
          }

          Button(
            onClick = { showExportReportDialog = true },
            colors = ButtonDefaults.buttonColors(containerColor = RongdhonuPurplePrimary),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
          ) {
            Icon(imageVector = Icons.Default.FileDownload, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("রিপোর্ট এক্সপোর্ট", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Key Financial Metrics Cards
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          FinanceMetricCard(
            label = "যাচাইকৃত আদায়",
            amount = "৳ $verifiedTotal",
            subtitle = "${payments.count { it.status == PaymentVerificationStatus.FULLY_PAID || it.status == PaymentVerificationStatus.VERIFIED }} টি নিশ্চিত",
            color = Color(0xFF2E7D32),
            modifier = Modifier.weight(1f)
          )
          FinanceMetricCard(
            label = "মোট বকেয়া",
            amount = "৳ $totalDues",
            subtitle = "${payments.count { it.dueAmountBdt > 0 }} জন গ্রাহকের",
            color = Color(0xFFD84315),
            modifier = Modifier.weight(1f)
          )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          FinanceMetricCard(
            label = "অপেক্ষমাণ যাচাই",
            amount = "৳ $pendingAmount",
            subtitle = "${payments.count { it.status == PaymentVerificationStatus.SUBMITTED || it.status == PaymentVerificationStatus.PENDING }} টি পেন্ডিং",
            color = Color(0xFFF57F17),
            modifier = Modifier.weight(1f)
          )
          FinanceMetricCard(
            label = "অনুমোদিত রিফান্ড",
            amount = "৳ $totalRefunded",
            subtitle = "নিট আদায়: ৳ ${verifiedTotal - totalRefunded}",
            color = Color(0xFF6A1B9A),
            modifier = Modifier.weight(1f)
          )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "মোট গ্রাহক সংখ্যা: $uniqueCustomersCount জন • মোট লেনদেন: ${payments.size} টি",
            style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF554D69), fontWeight = FontWeight.Medium)
          )
          Text(
            text = "মোট ভলিউম: ৳ $totalPaymentVolume",
            style = MaterialTheme.typography.labelSmall.copy(color = RongdhonuPurplePrimary, fontWeight = FontWeight.Bold)
          )
        }
      }
    }

    // 2. Search & Filters Bar
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .background(Color.White, RoundedCornerShape(14.dp))
          .border(1.dp, Color(0xFFEFEAF6), RoundedCornerShape(14.dp))
          .padding(12.dp)
      ) {
        OutlinedTextField(
          value = searchQuery,
          onValueChange = { viewModel.setPaymentSearchQuery(it) },
          placeholder = { Text("গ্রাহকের নাম, ফোন, অর্ডার নং বা TrxID খুঁজুন...") },
          leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
          trailingIcon = {
            if (searchQuery.isNotEmpty()) {
              IconButton(onClick = { viewModel.setPaymentSearchQuery("") }) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Clear")
              }
            }
          },
          singleLine = true,
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_payment_search")
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Status Filter Chips
        Text(
          text = "স্ট্যাটাস অনুযায়ী ফিল্টার:",
          style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
          color = Color(0xFF554D69)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Surface(
            shape = RoundedCornerShape(16.dp),
            color = if (statusFilter == null) RongdhonuPurplePrimary else Color(0xFFF3EEF9),
            modifier = Modifier.clickable { viewModel.setPaymentStatusFilter(null) }
          ) {
            Text(
              text = "সকল স্ট্যাটাস",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = if (statusFilter == null) FontWeight.Bold else FontWeight.Normal,
                color = if (statusFilter == null) Color.White else RongdhonuPurplePrimary
              ),
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
            )
          }

          PaymentVerificationStatus.values().forEach { status ->
            val isSelected = statusFilter == status
            Surface(
              shape = RoundedCornerShape(16.dp),
              color = if (isSelected) Color(status.badgeColor) else Color(0xFFF3EEF9),
              modifier = Modifier.clickable { viewModel.setPaymentStatusFilter(status) }
            ) {
              Text(
                text = status.titleBn.substringBefore(" "),
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                  color = if (isSelected) Color.White else Color(status.badgeColor)
                ),
                modifier = Modifier.padding(horizontal = 9.dp, vertical = 6.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Method Filter Chips
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Surface(
            shape = RoundedCornerShape(16.dp),
            color = if (methodFilter == null) RongdhonuPurpleDark else Color(0xFFF0ECF5),
            modifier = Modifier.clickable { viewModel.setPaymentMethodFilter(null) }
          ) {
            Text(
              text = "সকল গেটওয়ে",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = if (methodFilter == null) FontWeight.Bold else FontWeight.Normal,
                color = if (methodFilter == null) Color.White else Color(0xFF3B3250)
              ),
              modifier = Modifier.padding(horizontal = 9.dp, vertical = 5.dp)
            )
          }

          PaymentMethod.values().forEach { method ->
            val isSelected = methodFilter == method
            Surface(
              shape = RoundedCornerShape(16.dp),
              color = if (isSelected) RongdhonuPurplePrimary else Color(0xFFF0ECF5),
              modifier = Modifier.clickable { viewModel.setPaymentMethodFilter(method) }
            ) {
              Text(
                text = method.displayNameBn.substringBefore(" "),
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                  color = if (isSelected) Color.White else Color(0xFF3B3250)
                ),
                modifier = Modifier.padding(horizontal = 9.dp, vertical = 5.dp)
              )
            }
          }
        }
      }
    }

    // Results Counter
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "গ্রাহক লেনদেনের রেকর্ড (${filteredPayments.size} টি পাওয়া গেছে)",
          style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
          color = Color(0xFF1E1738)
        )
      }
    }

    // 3. Customer Payment Records List
    if (filteredPayments.isEmpty()) {
      item {
        Card(
          shape = RoundedCornerShape(14.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier
              .fillMaxWidth()
              .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Icon(
              imageVector = Icons.Default.MoneyOff,
              contentDescription = null,
              tint = Color.Gray,
              modifier = Modifier.size(36.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("কোনো লেনদেন রেকর্ড পাওয়া যায়নি", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
            Text("অনুসন্ধান শব্দ বা ফিল্টার পরিবর্তন করে পুনরায় চেষ্টা করুন।", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
          }
        }
      }
    } else {
      items(filteredPayments) { record ->
        CustomerPaymentItemCard(
          record = record,
          onVerify = { viewModel.openPaymentVerification(record) },
          onRefund = { viewModel.openPaymentRefund(record) },
          onRequestInfo = {
            viewModel.requestMorePaymentInfo(record.paymentId, "অনুগ্রহ করে সঠিক ডিপোজিট স্লিপ বা স্ক্রিনশট প্রদান করুন।")
          },
          onReject = {
            viewModel.rejectPayment(record.paymentId, "ব্যাংক স্টেটমেন্ট বা গেটওয়েতে প্রদত্ত TrxID পাওয়া যায়নি।")
          },
          onWhatsApp = {
            val phoneClean = record.whatsappNumber.replace("-", "").replace(" ", "")
            val target = if (phoneClean.startsWith("0")) "88$phoneClean" else phoneClean
            val url = "https://wa.me/$target?text=${Uri.encode("আসসালামু আলাইকুম ${record.customerName}, রংধনু ডিজিটাল স্টুডিও থেকে আপনার অর্ডার ${record.orderNumber} এর পেমেন্ট সম্পর্কিত বার্তা।")}"
            try {
              context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            } catch (e: Exception) {
              Toast.makeText(context, "WhatsApp ইনস্টল নেই", Toast.LENGTH_SHORT).show()
            }
          },
          onCopyTrx = {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.setPrimaryClip(ClipData.newPlainText("TrxID", record.trxId))
            Toast.makeText(context, "TrxID কপি করা হয়েছে: ${record.trxId}", Toast.LENGTH_SHORT).show()
          }
        )
      }
    }
  }

  // Modals
  if (selectedForVerification != null) {
    PaymentVerificationDialog(
      record = selectedForVerification!!,
      onDismiss = { viewModel.closePaymentVerification() },
      onConfirm = { verifiedAmount, notes ->
        viewModel.verifyPayment(selectedForVerification!!.paymentId, verifiedAmount, notes)
      }
    )
  }

  if (selectedForRefund != null) {
    PaymentRefundDialog(
      record = selectedForRefund!!,
      onDismiss = { viewModel.closePaymentRefund() },
      onConfirm = { refundAmount, refundTrxId, reason ->
        viewModel.refundPayment(selectedForRefund!!.paymentId, refundAmount, refundTrxId, reason)
      }
    )
  }

  if (showExportReportDialog) {
    PaymentReportExportDialog(
      payments = payments,
      verifiedTotal = verifiedTotal,
      totalDues = totalDues,
      totalRefunded = totalRefunded,
      onDismiss = { showExportReportDialog = false }
    )
  }
}

@Composable
fun FinanceMetricCard(
  label: String,
  amount: String,
  subtitle: String,
  color: Color,
  modifier: Modifier = Modifier
) {
  Surface(
    shape = RoundedCornerShape(12.dp),
    color = color.copy(alpha = 0.08f),
    border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.25f)),
    modifier = modifier
  ) {
    Column(modifier = Modifier.padding(10.dp)) {
      Text(text = label, style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp), color = color)
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = amount,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 16.sp),
        color = color
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(text = subtitle, style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), color = Color(0xFF6B6580))
    }
  }
}

@Composable
fun CustomerPaymentItemCard(
  record: CustomerPaymentRecord,
  onVerify: () -> Unit,
  onRefund: () -> Unit,
  onRequestInfo: () -> Unit,
  onReject: () -> Unit,
  onWhatsApp: () -> Unit,
  onCopyTrx: () -> Unit
) {
  val dateFormatted = remember(record.timestamp) {
    SimpleDateFormat("dd MMM, hh:mm a", Locale("bn", "BD")).format(Date(record.timestamp))
  }

  val methodColor = when (record.paymentMethod) {
    PaymentMethod.BKASH -> BkashPink
    PaymentMethod.NAGAD -> NagadOrange
    PaymentMethod.ROCKET -> RocketViolet
    PaymentMethod.WALLET -> RongdhonuPurplePrimary
    PaymentMethod.BANK -> Color(0xFF00796B)
    PaymentMethod.CARD -> Color(0xFF1565C0)
  }

  Card(
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = Modifier
      .fillMaxWidth()
      .border(1.dp, Color(0xFFEFEAF6), RoundedCornerShape(14.dp))
      .testTag("payment_record_${record.paymentId}")
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      // Top Bar: Customer Name & Status Badge
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
          Box(
            modifier = Modifier
              .size(32.dp)
              .clip(CircleShape)
              .background(RongdhonuPurplePrimary.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = RongdhonuPurplePrimary, modifier = Modifier.size(18.dp))
          }
          Spacer(modifier = Modifier.width(8.dp))
          Column {
            Text(
              text = record.customerName,
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
              color = Color(0xFF1E1738)
            )
            Text(
              text = "${record.customerPhone} • ID: ${record.customerId}",
              style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.5.sp),
              color = Color(0xFF6B6580)
            )
          }
        }

        Surface(
          color = Color(record.status.badgeColor).copy(alpha = 0.12f),
          shape = RoundedCornerShape(8.dp)
        ) {
          Text(
            text = record.status.titleBn,
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 10.sp,
              color = Color(record.status.badgeColor)
            ),
            modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.5.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))
      HorizontalDivider(color = Color(0xFFF5F2F9))
      Spacer(modifier = Modifier.height(10.dp))

      // Middle: Order Details & Amounts
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(text = "সেবা ও অর্ডার:", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), color = Color(0xFF756F84))
          Text(
            text = record.serviceTitleBn,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
            color = Color(0xFF1E1738)
          )
          Text(
            text = "অর্ডার নং: ${record.orderNumber}",
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.5.sp),
            color = RongdhonuPurplePrimary
          )
        }

        Column(horizontalAlignment = Alignment.End) {
          Text(text = "নির্ধারিত ফি:", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), color = Color(0xFF756F84))
          Text(
            text = "৳ ${record.servicePriceBdt}",
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
            color = Color(0xFF1E1738)
          )
          if (record.dueAmountBdt > 0) {
            Surface(color = Color(0xFFFFEBEE), shape = RoundedCornerShape(4.dp)) {
              Text(
                text = "বকেয়া: ৳ ${record.dueAmountBdt}",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = Color(0xFFC62828)),
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
              )
            }
          } else {
            Text(text = "পরিশোধিত", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32)))
          }
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      // Transaction & Gateway info
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .background(Color(0xFFF9F8FC), RoundedCornerShape(8.dp))
          .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Surface(
            color = methodColor.copy(alpha = 0.12f),
            shape = RoundedCornerShape(6.dp)
          ) {
            Text(
              text = record.paymentMethod.displayNameBn.substringBefore(" "),
              style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, fontSize = 10.sp, color = methodColor),
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
          }
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "TrxID: ${record.trxId}",
            style = MaterialTheme.typography.labelSmall.copy(fontFamily = FontFamily.Monospace, fontSize = 11.sp),
            color = Color(0xFF2E2742)
          )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
          IconButton(onClick = onCopyTrx, modifier = Modifier.size(24.dp)) {
            Icon(imageVector = Icons.Default.ContentCopy, contentDescription = "Copy Trx", tint = Color.Gray, modifier = Modifier.size(14.dp))
          }
          Text(text = dateFormatted, style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), color = Color(0xFF756F84))
        }
      }

      // Notes / Admin remarks
      if (record.notes.isNotEmpty()) {
        Spacer(modifier = Modifier.height(6.dp))
        Text(
          text = "নোট: ${record.notes}",
          style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
          color = Color(0xFF554D69)
        )
      }

      // If refund occurred
      if (record.isRefundProcessed) {
        Spacer(modifier = Modifier.height(6.dp))
        Surface(
          color = Color(0xFFEDE7F6),
          shape = RoundedCornerShape(6.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Text(
            text = "রিফান্ড সম্পন্ন: ৳${record.refundAmountBdt} (Trx: ${record.refundTrxId}) - ${record.refundReason}",
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.5.sp, color = Color(0xFF4A148C), fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Action Buttons
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        if (record.status != PaymentVerificationStatus.FULLY_PAID && record.status != PaymentVerificationStatus.REFUNDED) {
          Button(
            onClick = onVerify,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
            modifier = Modifier.weight(1f)
          ) {
            Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("যাচাই ও অনুমোদন", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
          }
        }

        if (record.status == PaymentVerificationStatus.FULLY_PAID || record.status == PaymentVerificationStatus.VERIFIED) {
          OutlinedButton(
            onClick = onRefund,
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
            modifier = Modifier.weight(1f)
          ) {
            Text("রিফান্ড", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = Color(0xFF6A1B9A)))
          }
        }

        Button(
          onClick = onWhatsApp,
          colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
          shape = RoundedCornerShape(8.dp),
          contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
        ) {
          Text("WhatsApp", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = Color.White))
        }

        if (record.status == PaymentVerificationStatus.SUBMITTED || record.status == PaymentVerificationStatus.PENDING) {
          OutlinedButton(
            onClick = onReject,
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
          ) {
            Text("বাতিল", style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFFC62828)))
          }
        }
      }
    }
  }
}

@Composable
fun PaymentVerificationDialog(
  record: CustomerPaymentRecord,
  onDismiss: () -> Unit,
  onConfirm: (verifiedAmount: Int, notes: String) -> Unit
) {
  var verifiedAmountText by remember { mutableStateOf(record.servicePriceBdt.toString()) }
  var adminNotes by remember { mutableStateOf("গেটওয়ে স্টেটমেন্টে প্রাপ্ত অর্থ নিশ্চিত করা হয়েছে।") }

  Dialog(onDismissRequest = onDismiss) {
    Card(
      shape = RoundedCornerShape(18.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
    ) {
      Column(modifier = Modifier.padding(18.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "পেমেন্ট যাচাই ও অনুমোদন",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = RongdhonuPurplePrimary
          )
          IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
          }
        }

        Spacer(modifier = Modifier.height(10.dp))
        HorizontalDivider(color = Color(0xFFF0EBF7))
        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "গ্রাহক: ${record.customerName} (${record.customerPhone})", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
        Text(text = "সেবা: ${record.serviceTitleBn}", style = MaterialTheme.typography.bodySmall)
        Text(text = "নির্ধারিত ফি: ৳ ${record.servicePriceBdt} | মাধ্যম: ${record.paymentMethod.displayNameBn}", style = MaterialTheme.typography.bodySmall)
        Text(text = "প্রদত্ত TrxID: ${record.trxId}", style = MaterialTheme.typography.bodySmall.copy(fontFamily = FontFamily.Monospace, color = RongdhonuPurplePrimary))

        Spacer(modifier = Modifier.height(14.dp))

        OutlinedTextField(
          value = verifiedAmountText,
          onValueChange = { verifiedAmountText = it.filter { ch -> ch.isDigit() } },
          label = { Text("যাচাইকৃত প্রাপ্ত টাকার পরিমাণ (৳)") },
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
          singleLine = true,
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
          value = adminNotes,
          onValueChange = { adminNotes = it },
          label = { Text("অ্যাডমিন মন্তব্য ও নোট") },
          singleLine = true,
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          OutlinedButton(
            onClick = onDismiss,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.weight(1f)
          ) {
            Text("বাতিল")
          }

          Button(
            onClick = {
              val amount = verifiedAmountText.toIntOrNull() ?: record.servicePriceBdt
              onConfirm(amount, adminNotes)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.weight(1f)
          ) {
            Text("অনুমোদন নিশ্চিত", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
          }
        }
      }
    }
  }
}

@Composable
fun PaymentRefundDialog(
  record: CustomerPaymentRecord,
  onDismiss: () -> Unit,
  onConfirm: (refundAmount: Int, refundTrxId: String, reason: String) -> Unit
) {
  var refundAmountText by remember { mutableStateOf(record.verifiedAmountBdt.toString()) }
  var refundTrxId by remember { mutableStateOf("REF-${(10000..99999).random()}") }
  var refundReason by remember { mutableStateOf("গ্রাহকের অনুরোধে সার্ভিস ক্যানসেলেশন ও রিফান্ড।") }

  Dialog(onDismissRequest = onDismiss) {
    Card(
      shape = RoundedCornerShape(18.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
    ) {
      Column(modifier = Modifier.padding(18.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "রিফান্ড ম্যানেজমেন্ট",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = Color(0xFF6A1B9A)
          )
          IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
          }
        }

        Spacer(modifier = Modifier.height(10.dp))
        HorizontalDivider(color = Color(0xFFF0EBF7))
        Spacer(modifier = Modifier.height(10.dp))

        Text(text = "গ্রাহক: ${record.customerName}", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
        Text(text = "আসল পেমেন্ট: ৳ ${record.verifiedAmountBdt} (${record.paymentMethod.displayNameBn})", style = MaterialTheme.typography.bodySmall)

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
          value = refundAmountText,
          onValueChange = { refundAmountText = it.filter { ch -> ch.isDigit() } },
          label = { Text("রিফান্ডের পরিমাণ (৳)") },
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
          singleLine = true,
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
          value = refundTrxId,
          onValueChange = { refundTrxId = it },
          label = { Text("রিফান্ড Transaction ID (TrxID)") },
          singleLine = true,
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
          value = refundReason,
          onValueChange = { refundReason = it },
          label = { Text("রিফান্ডের কারণ") },
          shape = RoundedCornerShape(10.dp),
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          OutlinedButton(
            onClick = onDismiss,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.weight(1f)
          ) {
            Text("বাতিল")
          }

          Button(
            onClick = {
              val amount = refundAmountText.toIntOrNull() ?: record.verifiedAmountBdt
              onConfirm(amount, refundTrxId, refundReason)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A1B9A)),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.weight(1f)
          ) {
            Text("রিফান্ড নিশ্চিত", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
          }
        }
      }
    }
  }
}

@Composable
fun PaymentReportExportDialog(
  payments: List<CustomerPaymentRecord>,
  verifiedTotal: Int,
  totalDues: Int,
  totalRefunded: Int,
  onDismiss: () -> Unit
) {
  val context = LocalContext.current

  val reportCsv = remember(payments) {
    buildString {
      appendLine("Rongdhonu Online Seva - Financial Payment Report")
      appendLine("Generated on: ${SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(Date())}")
      appendLine("Total Verified Collection: BDT $verifiedTotal")
      appendLine("Total Outstanding Due: BDT $totalDues")
      appendLine("Total Refunded: BDT $totalRefunded")
      appendLine("Net Revenue: BDT ${verifiedTotal - totalRefunded}")
      appendLine()
      appendLine("PaymentID,CustomerID,CustomerName,Phone,OrderNumber,ServiceTitle,PriceBDT,SentBDT,VerifiedBDT,DueBDT,Method,TrxID,Status")
      payments.forEach { p ->
        appendLine("${p.paymentId},${p.customerId},\"${p.customerName}\",${p.customerPhone},${p.orderNumber},\"${p.serviceTitleBn}\",${p.servicePriceBdt},${p.amountSentBdt},${p.verifiedAmountBdt},${p.dueAmountBdt},${p.paymentMethod.name},${p.trxId},${p.status.name}")
      }
    }
  }

  Dialog(onDismissRequest = onDismiss) {
    Card(
      shape = RoundedCornerShape(18.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
    ) {
      Column(modifier = Modifier.padding(18.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "আর্থিক রিপোর্ট এক্সপোর্ট (CSV / Excel)",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = RongdhonuPurplePrimary
          )
          IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
          }
        }

        Spacer(modifier = Modifier.height(10.dp))
        HorizontalDivider(color = Color(0xFFF0EBF7))
        Spacer(modifier = Modifier.height(10.dp))

        Text(
          text = "নিচে স্বয়ংক্রিয়ভাবে প্রস্তুতকৃত CSV রিপোর্ট দেখতে পারেন। এটি কপি করে সরাসরি Excel বা Google Sheets-এ পেস্ট করা যাবে।",
          style = MaterialTheme.typography.bodySmall,
          color = Color(0xFF6B6580)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color(0xFFF7F5FC), RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFFEDE8F5), RoundedCornerShape(8.dp))
            .padding(10.dp)
        ) {
          Text(
            text = reportCsv,
            style = MaterialTheme.typography.labelSmall.copy(fontFamily = FontFamily.Monospace, fontSize = 9.sp),
            color = Color(0xFF2C2442)
          )
        }

        Spacer(modifier = Modifier.height(16.dp))

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
              val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
              clipboard.setPrimaryClip(ClipData.newPlainText("PaymentReportCSV", reportCsv))
              Toast.makeText(context, "CSV রিপোর্ট ক্লিপবোর্ডে কপি করা হয়েছে!", Toast.LENGTH_SHORT).show()
            },
            colors = ButtonDefaults.buttonColors(containerColor = RongdhonuPurplePrimary),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.weight(1f)
          ) {
            Icon(imageVector = Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("কপি করুন", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
          }
        }
      }
    }
  }
}
