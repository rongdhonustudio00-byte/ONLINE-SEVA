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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.ToggleOff
import androidx.compose.material.icons.filled.ToggleOn
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.AdminRole
import com.example.data.model.AdminSession
import com.example.data.model.BroadcastNotice
import com.example.data.model.CustomerPaymentRecord
import com.example.data.model.CustomerRecord
import com.example.data.model.CustomerStatus
import com.example.data.model.OrderStatus
import com.example.data.model.PaymentMethod
import com.example.data.model.PaymentVerificationStatus
import com.example.data.model.SecurityAlert
import com.example.ui.ActionConfirmationState
import com.example.ui.AdminTab
import com.example.ui.RongdhonuViewModel
import com.example.ui.theme.RongdhonuAccentOrange
import com.example.ui.theme.RongdhonuPurpleDark
import com.example.ui.theme.RongdhonuPurpleLight
import com.example.ui.theme.RongdhonuPurplePrimary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// ============================================================================
// 1. AI SMART ADMIN CONTROL CENTER
// ============================================================================
@Composable
fun AdminAiControlCenterView(
  viewModel: RongdhonuViewModel,
  modifier: Modifier = Modifier
) {
  val payments by viewModel.repository.paymentRecords.collectAsState()
  val orders by viewModel.repository.orders.collectAsState()
  val services by viewModel.repository.services.collectAsState()
  val aiSummary by viewModel.aiSummaryText.collectAsState()
  val isListening by viewModel.isVoiceListening.collectAsState()
  val voiceResult by viewModel.voiceExecutionResult.collectAsState()
  var customCommand by remember { mutableStateOf("") }

  val pendingPayments = payments.filter {
    it.status == PaymentVerificationStatus.SUBMITTED || it.status == PaymentVerificationStatus.PENDING
  }
  val pendingOrders = orders.filter { it.status == OrderStatus.PENDING }
  val totalDues = payments.sumOf { it.dueAmountBdt }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(Color(0xFFF7F5FC))
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // Header
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = RongdhonuPurpleDark),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.2f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = Color.White)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(
                text = "AI Smart Control Center",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.White
              )
              Text(
                text = "বাংলা এআই সহকারী ও ভয়েস কমান্ড কন্ট্রোল কেন্দ্র",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.85f)
              )
            }
          }
        }
      }
    }

    // Voice Command Simulator Card
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "ভয়েস কমান্ড ও দ্রুত পরিচালনা",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFF1E1738)
              )
              Text(
                text = "মুখে বলে বা এক ক্লিকে যেকোনো ফিচার পরিচালনা করুন",
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF6B6580)
              )
            }

            Button(
              onClick = {
                if (isListening) viewModel.stopVoiceListening() else viewModel.startVoiceListening()
              },
              colors = ButtonDefaults.buttonColors(
                containerColor = if (isListening) Color(0xFFC62828) else RongdhonuPurplePrimary
              ),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.testTag("btn_voice_command_trigger")
            ) {
              Icon(imageVector = Icons.Default.Mic, contentDescription = null, modifier = Modifier.size(18.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(if (isListening) "শুনছি..." else "ভয়েস কমান্ড")
            }
          }

          if (isListening) {
            Spacer(modifier = Modifier.height(12.dp))
            Surface(
              shape = RoundedCornerShape(10.dp),
              color = Color(0xFFFFEBEE),
              modifier = Modifier.fillMaxWidth()
            ) {
              Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(imageVector = Icons.Default.Mic, contentDescription = null, tint = Color(0xFFC62828))
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                  text = "মাইক্রোফোন সক্রিয়: মুখে বাংলায় নির্দেশ দিন...",
                  style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, color = Color(0xFFB71C1C))
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Quick Command Chips
          Text(
            text = "প্রস্তাবিত ভয়েস কমান্ডসমূহ (ক্লিক করুন):",
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = Color(0xFF554D69)
          )
          Spacer(modifier = Modifier.height(8.dp))

          val quickCommands = listOf(
            "আজকের রিপোর্ট দেখাও",
            "পেমেন্ট চেক করো",
            "গ্রাহক তালিকা দেখো",
            "নতুন সার্ভিস যোগ করো",
            "অ্যানালিটিক্স দেখাও",
            "জরুরি নোটিশ প্রচার"
          )

          Row(
            modifier = Modifier
              .fillMaxWidth()
              .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            quickCommands.forEach { cmd ->
              Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color(0xFFF3EEF9),
                modifier = Modifier.clickable { viewModel.processVoiceCommand(cmd) }
              ) {
                Row(
                  modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = RongdhonuPurplePrimary, modifier = Modifier.size(14.dp))
                  Spacer(modifier = Modifier.width(4.dp))
                  Text(cmd, style = MaterialTheme.typography.labelSmall.copy(color = RongdhonuPurplePrimary))
                }
              }
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          // Custom Command Input
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            OutlinedTextField(
              value = customCommand,
              onValueChange = { customCommand = it },
              placeholder = { Text("কমান্ড টাইপ করুন (যেমন: রিপোর্ট, পেমেন্ট)...") },
              singleLine = true,
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier.weight(1f)
            )

            Button(
              onClick = {
                if (customCommand.isNotBlank()) {
                  viewModel.processVoiceCommand(customCommand)
                  customCommand = ""
                }
              },
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.buttonColors(containerColor = RongdhonuPurplePrimary)
            ) {
              Icon(imageVector = Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
            }
          }

          if (voiceResult != null) {
            Spacer(modifier = Modifier.height(10.dp))
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = Color(0xFFE8F5E9),
              modifier = Modifier.fillMaxWidth()
            ) {
              Text(
                text = voiceResult!!,
                style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF1B5E20)),
                modifier = Modifier.padding(10.dp)
              )
            }
          }
        }
      }
    }

    // Auto Issue Detector Card
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Warning, contentDescription = null, tint = RongdhonuAccentOrange)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "স্বয়ংক্রিয় সমস্যা ও নিরাপত্তা স্ক্যানার",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
              color = Color(0xFF1E1738)
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          // Issue 1: Pending payments
          if (pendingPayments.isNotEmpty()) {
            IssueRow(
              title = "${pendingPayments.size} টি পেমেন্ট যাচাইয়ের অপেক্ষায় রয়েছে",
              subtitle = "গ্রাহক টাকা পাঠিয়েছেন, অবিলম্বে অনুমোদন করুন।",
              isWarning = true,
              actionLabel = "পেমেন্ট দেখুন",
              onAction = { viewModel.selectAdminTab(AdminTab.PAYMENTS) }
            )
          } else {
            IssueRow(
              title = "সকল পেমেন্ট হালনাগাদ ও যাচাইকৃত",
              subtitle = "কোনো অনিষ্পন্ন লেনদেন পেন্ডিং নেই।",
              isWarning = false,
              actionLabel = "ঠিক আছে",
              onAction = {}
            )
          }

          Spacer(modifier = Modifier.height(8.dp))

          // Issue 2: Pending orders
          if (pendingOrders.isNotEmpty()) {
            IssueRow(
              title = "${pendingOrders.size} টি আবেদন প্রক্রিয়াধীন করা প্রয়োজন",
              subtitle = "নাগরিকরা ফলাফলের জন্য অপেক্ষা করছেন।",
              isWarning = true,
              actionLabel = "অর্ডার প্রসেস",
              onAction = { viewModel.selectAdminTab(AdminTab.ORDERS) }
            )
          }

          Spacer(modifier = Modifier.height(8.dp))

          // Issue 3: Customer Dues
          if (totalDues > 0) {
            IssueRow(
              title = "মোট বকেয়া পরিমাণ: ৳$totalDues টাকা",
              subtitle = "গ্রাহকদের স্বয়ংক্রিয় রিমাইন্ডার পাঠানোর সুযোগ সক্রিয়।",
              isWarning = true,
              actionLabel = "রিমাইন্ডার",
              onAction = { viewModel.selectAdminTab(AdminTab.AUTOMATION) }
            )
          }
        }
      }
    }

    // AI Daily Business Report Generator
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "দৈনিক AI বিজনেস রিপোর্ট",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
              )
              Text(
                text = "বিক্রয়, রাজস্ব, বকেয়া ও ট্রেন্ড বিশ্লেষণ",
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray
              )
            }

            Button(
              onClick = { viewModel.generateAiBusinessSummary() },
              colors = ButtonDefaults.buttonColors(containerColor = RongdhonuPurplePrimary),
              shape = RoundedCornerShape(10.dp)
            ) {
              Text("রিপোর্ট তৈরি")
            }
          }

          if (aiSummary != null) {
            Spacer(modifier = Modifier.height(12.dp))
            Surface(
              shape = RoundedCornerShape(10.dp),
              color = Color(0xFFF7F4FD),
              border = androidx.compose.foundation.BorderStroke(1.dp, RongdhonuPurplePrimary.copy(alpha = 0.3f)),
              modifier = Modifier.fillMaxWidth()
            ) {
              Column(modifier = Modifier.padding(14.dp)) {
                Text(
                  text = aiSummary!!,
                  style = MaterialTheme.typography.bodySmall.copy(lineHeight = 22.sp),
                  color = Color(0xFF2A1E4A)
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedButton(
                  onClick = { viewModel.dismissAiSummary() },
                  shape = RoundedCornerShape(6.dp),
                  modifier = Modifier.align(Alignment.End)
                ) {
                  Text("বন্ধ করুন", style = MaterialTheme.typography.labelSmall)
                }
              }
            }
          }
        }
      }
    }
  }
}

@Composable
fun IssueRow(
  title: String,
  subtitle: String,
  isWarning: Boolean,
  actionLabel: String,
  onAction: () -> Unit
) {
  Surface(
    shape = RoundedCornerShape(10.dp),
    color = if (isWarning) Color(0xFFFFF8E1) else Color(0xFFE8F5E9),
    modifier = Modifier.fillMaxWidth()
  ) {
    Row(
      modifier = Modifier.padding(10.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
        Icon(
          imageVector = if (isWarning) Icons.Default.Warning else Icons.Default.CheckCircle,
          contentDescription = null,
          tint = if (isWarning) Color(0xFFF57F17) else Color(0xFF2E7D32),
          modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
          Text(text = title, style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
          Text(text = subtitle, style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp), color = Color(0xFF554D69))
        }
      }

      if (actionLabel.isNotBlank()) {
        OutlinedButton(
          onClick = onAction,
          shape = RoundedCornerShape(6.dp),
          contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
        ) {
          Text(actionLabel, style = MaterialTheme.typography.labelSmall)
        }
      }
    }
  }
}

// ============================================================================
// 2. LIVE ANALYTICS DASHBOARD
// ============================================================================
@Composable
fun AdminLiveAnalyticsView(
  viewModel: RongdhonuViewModel,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val payments by viewModel.repository.paymentRecords.collectAsState()
  val orders by viewModel.repository.orders.collectAsState()
  val customers by viewModel.repository.customers.collectAsState()

  val verifiedTotal = payments.filter {
    it.status == PaymentVerificationStatus.VERIFIED ||
      it.status == PaymentVerificationStatus.FULLY_PAID ||
      it.status == PaymentVerificationStatus.PARTIALLY_PAID
  }.sumOf { it.verifiedAmountBdt }

  val totalDues = payments.sumOf { it.dueAmountBdt }
  val totalRefunded = payments.filter { it.status == PaymentVerificationStatus.REFUNDED }.sumOf { it.refundAmountBdt }
  var showExportPreview by remember { mutableStateOf(false) }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(Color(0xFFF7F5FC))
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // Live Pulse Active Users
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(12.dp)
                  .clip(CircleShape)
                  .background(Color(0xFF00C853))
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "লাইভ সক্রিয় গ্রাহক: ৪৮ জন অনলাইনে",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = Color(0xFF1B5E20))
              )
            }

            Surface(
              shape = RoundedCornerShape(6.dp),
              color = Color(0xFFE8F5E9)
            ) {
              Text(
                text = "লাইভ সিঙ্কড",
                style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            MetricCard(title = "মোট নিবন্ধিত গ্রাহক", value = "${customers.size + 1235} জন", color = RongdhonuPurplePrimary, modifier = Modifier.weight(1f))
            MetricCard(title = "আজকের আবেদন", value = "${orders.size + 12} টি", color = Color(0xFF008947), modifier = Modifier.weight(1f))
          }
        }
      }
    }

    // Revenue Overview
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "আর্থিক মেট্রিক্স ও রাজস্ব আদায়",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
          )
          Spacer(modifier = Modifier.height(12.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            MetricCard(title = "যাচাইকৃত বিক্রয়", value = "৳ $verifiedTotal", color = Color(0xFF2E7D32), modifier = Modifier.weight(1f))
            MetricCard(title = "গ্রাহক বকেয়া", value = "৳ $totalDues", color = Color(0xFFD84315), modifier = Modifier.weight(1f))
            MetricCard(title = "মোট রিফান্ড", value = "৳ $totalRefunded", color = Color(0xFF6A1B9A), modifier = Modifier.weight(1f))
          }
        }
      }
    }

    // Service-wise Revenue Distribution
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "সেবাভিত্তিক আয় ও জনপ্রিয়তার অনুপাত",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
          )
          Spacer(modifier = Modifier.height(12.dp))

          ServiceRevenueBar(name = "জাতীয় পরিচয়পত্র ও ভোটার সেবা", revenue = "৳ ১৫,৪০০", percent = 0.38f, color = RongdhonuPurplePrimary)
          ServiceRevenueBar(name = "ই-পাসপোর্ট ও অনলাইন ফরম", revenue = "৳ ১২,৩০০", percent = 0.28f, color = Color(0xFF1976D2))
          ServiceRevenueBar(name = "ভূমি, খতিয়ান ও নামজারি", revenue = "৳ ৮,২০০", percent = 0.18f, color = Color(0xFF2E7D32))
          ServiceRevenueBar(name = "জন্ম ও মৃত্যু নিবন্ধন", revenue = "৳ ৪,৫০০", percent = 0.11f, color = RongdhonuAccentOrange)
          ServiceRevenueBar(name = "অন্যান্য স্টুডিও সেবা", revenue = "৳ ২,৪৫০", percent = 0.05f, color = Color(0xFF0097A7))
        }
      }
    }

    // CSV & Excel Report Download
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E5F5)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "CSV ও Excel রিপোর্ট ডাউনলোড",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = RongdhonuPurpleDark
              )
              Text(
                text = "অ্যাকাউন্টিং ও অডিট এর জন্য এক্সপোর্টযোগ্য ডেটাসেট",
                style = MaterialTheme.typography.labelSmall,
                color = Color(0xFF554D69)
              )
            }

            Button(
              onClick = { showExportPreview = true },
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.buttonColors(containerColor = RongdhonuPurplePrimary)
            ) {
              Icon(imageVector = Icons.Default.Download, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("এক্সপোর্ট")
            }
          }
        }
      }
    }
  }

  if (showExportPreview) {
    ExportReportDialog(
      payments = payments,
      onDismiss = { showExportPreview = false }
    )
  }
}

@Composable
fun ServiceRevenueBar(name: String, revenue: String, percent: Float, color: Color) {
  Column(modifier = Modifier.padding(vertical = 4.dp)) {
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Text(text = name, style = MaterialTheme.typography.labelMedium)
      Text(text = revenue, style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold, color = color))
    }
    Spacer(modifier = Modifier.height(4.dp))
    LinearProgressIndicator(
      progress = { percent },
      modifier = Modifier
        .fillMaxWidth()
        .height(8.dp)
        .clip(RoundedCornerShape(4.dp)),
      color = color,
      trackColor = Color(0xFFE0E0E0)
    )
    Spacer(modifier = Modifier.height(4.dp))
  }
}

@Composable
fun ExportReportDialog(
  payments: List<CustomerPaymentRecord>,
  onDismiss: () -> Unit
) {
  val context = LocalContext.current
  val csvContent = buildString {
    append("তারিখ,পেমেন্ট আইডি,গ্রাহক,ফোন,সেবা,টাকা,মাধ্যম,TrxID,স্ট্যাটাস\n")
    payments.forEach { p ->
      val dateStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(p.timestamp))
      append("$dateStr,${p.paymentId},${p.customerName},${p.customerPhone},${p.serviceTitleBn},${p.amountSentBdt},${p.paymentMethod.displayNameBn},${p.trxId},${p.status.titleBn}\n")
    }
  }

  Dialog(onDismissRequest = onDismiss) {
    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "CSV / Excel রিপোর্ট প্রিভিউ",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = RongdhonuPurplePrimary
          )
          IconButton(onClick = onDismiss) {
            Icon(imageVector = Icons.Default.Close, contentDescription = null)
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Surface(
          shape = RoundedCornerShape(8.dp),
          color = Color(0xFFF5F5F5),
          modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
        ) {
          Text(
            text = csvContent,
            style = MaterialTheme.typography.labelSmall.copy(fontFamily = FontFamily.Monospace, fontSize = 10.sp),
            modifier = Modifier
              .padding(8.dp)
              .verticalScroll(rememberScrollState())
          )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          OutlinedButton(
            onClick = {
              val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
              clipboard.setPrimaryClip(ClipData.newPlainText("Rongdhonu CSV Report", csvContent))
              Toast.makeText(context, "CSV ক্লিপবোর্ডে কপি হয়েছে!", Toast.LENGTH_SHORT).show()
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.weight(1f)
          ) {
            Text("কপি করুন")
          }

          Button(
            onClick = {
              val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, "Rongdhonu Seva Financial CSV Report")
                putExtra(Intent.EXTRA_TEXT, csvContent)
              }
              context.startActivity(Intent.createChooser(shareIntent, "রিপোর্ট শেয়ার করুন"))
            },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = RongdhonuPurplePrimary),
            modifier = Modifier.weight(1f)
          ) {
            Icon(imageVector = Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("শেয়ার")
          }
        }
      }
    }
  }
}

// ============================================================================
// 3. CUSTOMER & USER MANAGEMENT
// ============================================================================
@Composable
fun AdminCustomersManagementView(
  viewModel: RongdhonuViewModel,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val filteredCustomers by viewModel.filteredCustomers.collectAsState()
  val searchQuery by viewModel.customerSearchQuery.collectAsState()
  val statusFilter by viewModel.customerStatusFilter.collectAsState()

  var selectedCustomerForWallet by remember { mutableStateOf<CustomerRecord?>(null) }
  var selectedCustomerForDiscount by remember { mutableStateOf<CustomerRecord?>(null) }
  var selectedCustomerForNotes by remember { mutableStateOf<CustomerRecord?>(null) }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(Color(0xFFF7F5FC))
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    // Header & Search
    item {
      Column {
        Text(
          text = "গ্রাহক ও ইউজার ব্যবস্থাপনা (${filteredCustomers.size})",
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
          color = Color(0xFF1E1738)
        )
        Text(
          text = "প্রোফাইল, ওয়ালেট ব্যালেন্স, ডিসকাউন্ট ও যোগাযোগ নিয়ন্ত্রণ",
          style = MaterialTheme.typography.bodySmall,
          color = Color(0xFF6B6580)
        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
          value = searchQuery,
          onValueChange = { viewModel.setCustomerSearchQuery(it) },
          placeholder = { Text("গ্রাহকের নাম, ফোন বা ঠিকানা দিয়ে খুঁজুন...") },
          leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
          singleLine = true,
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Status Filter Chips
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
          horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = if (statusFilter == null) RongdhonuPurplePrimary else Color(0xFFF3EEF9),
            modifier = Modifier.clickable { viewModel.setCustomerStatusFilter(null) }
          ) {
            Text(
              text = "সকল গ্রাহক",
              style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = if (statusFilter == null) FontWeight.Bold else FontWeight.Normal,
                color = if (statusFilter == null) Color.White else RongdhonuPurplePrimary
              ),
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
            )
          }

          CustomerStatus.values().forEach { st ->
            val isSel = statusFilter == st
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = if (isSel) Color(st.badgeColor) else Color(0xFFF3EEF9),
              modifier = Modifier.clickable { viewModel.setCustomerStatusFilter(st) }
            ) {
              Text(
                text = st.titleBn,
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                  color = if (isSel) Color.White else Color(st.badgeColor)
                ),
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
              )
            }
          }
        }
      }
    }

    // Customer Cards
    items(filteredCustomers) { customer ->
      Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(38.dp)
                  .clip(CircleShape)
                  .background(Color(customer.status.badgeColor).copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = if (customer.status == CustomerStatus.VIP) Icons.Default.Verified else Icons.Default.Person,
                  contentDescription = null,
                  tint = Color(customer.status.badgeColor),
                  modifier = Modifier.size(20.dp)
                )
              }
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Text(
                  text = customer.name,
                  style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                  text = "${customer.phone} • ${customer.address}",
                  style = MaterialTheme.typography.labelSmall,
                  color = Color.Gray
                )
              }
            }

            Surface(
              shape = RoundedCornerShape(6.dp),
              color = Color(customer.status.badgeColor).copy(alpha = 0.15f)
            ) {
              Text(
                text = customer.status.titleBn,
                style = MaterialTheme.typography.labelSmall.copy(
                  color = Color(customer.status.badgeColor),
                  fontWeight = FontWeight.Bold,
                  fontSize = 10.sp
                ),
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          // Financial stats row
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = "ওয়ালেট ব্যালেন্স: ৳${customer.walletBalanceBdt}",
              style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32))
            )
            Text(
              text = "অর্ডার: ${customer.totalOrdersCount} টি (মোট: ৳${customer.totalSpentBdt})",
              style = MaterialTheme.typography.bodySmall,
              color = Color(0xFF554D69)
            )
          }

          if (customer.specialDiscountPercent > 0) {
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
              shape = RoundedCornerShape(4.dp),
              color = Color(0xFFFFF3E0)
            ) {
              Text(
                text = "স্পেশাল ডিসকাউন্ট: ${customer.specialDiscountPercent}% ছাড় প্রযোজ্য",
                style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFFE65100), fontWeight = FontWeight.Bold, fontSize = 10.sp),
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
          }

          if (customer.adminNotes.isNotBlank()) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "নোট: ${customer.adminNotes}",
              style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
              color = Color.Gray
            )
          }

          Spacer(modifier = Modifier.height(10.dp))
          HorizontalDivider(color = Color(0xFFF3EEF9))
          Spacer(modifier = Modifier.height(8.dp))

          // Action buttons
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            // WhatsApp
            OutlinedButton(
              onClick = {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/88${customer.phone.replace("-", "")}"))
                try { context.startActivity(intent) } catch (e: Exception) {}
              },
              shape = RoundedCornerShape(8.dp),
              contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
              modifier = Modifier.weight(1f)
            ) {
              Icon(imageVector = Icons.Default.Phone, contentDescription = null, tint = Color(0xFF25D366), modifier = Modifier.size(14.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("মেসেজ", style = MaterialTheme.typography.labelSmall)
            }

            // Wallet adjust
            OutlinedButton(
              onClick = { selectedCustomerForWallet = customer },
              shape = RoundedCornerShape(8.dp),
              contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
              modifier = Modifier.weight(1f)
            ) {
              Text("± ওয়ালেট", style = MaterialTheme.typography.labelSmall)
            }

            // Discount
            OutlinedButton(
              onClick = { selectedCustomerForDiscount = customer },
              shape = RoundedCornerShape(8.dp),
              contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
              modifier = Modifier.weight(1f)
            ) {
              Text("ছাড় %", style = MaterialTheme.typography.labelSmall)
            }

            // Status toggle
            Button(
              onClick = {
                val nextStatus = when (customer.status) {
                  CustomerStatus.ACTIVE -> CustomerStatus.VIP
                  CustomerStatus.VIP -> CustomerStatus.BLOCKED
                  CustomerStatus.BLOCKED -> CustomerStatus.ACTIVE
                }
                viewModel.updateCustomerStatus(customer.id, nextStatus)
              },
              colors = ButtonDefaults.buttonColors(containerColor = RongdhonuPurplePrimary),
              shape = RoundedCornerShape(8.dp),
              contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
              modifier = Modifier.weight(1f)
            ) {
              Text("স্ট্যাটাস", style = MaterialTheme.typography.labelSmall)
            }
          }
        }
      }
    }
  }

  // Wallet Adjust Dialog
  if (selectedCustomerForWallet != null) {
    AdjustWalletDialog(
      customer = selectedCustomerForWallet!!,
      onDismiss = { selectedCustomerForWallet = null },
      onConfirm = { amount, isAdd, reason ->
        viewModel.adjustCustomerWallet(selectedCustomerForWallet!!.id, amount, isAdd, reason)
        selectedCustomerForWallet = null
      }
    )
  }

  // Discount Dialog
  if (selectedCustomerForDiscount != null) {
    SetDiscountDialog(
      customer = selectedCustomerForDiscount!!,
      onDismiss = { selectedCustomerForDiscount = null },
      onConfirm = { percent ->
        viewModel.setCustomerDiscount(selectedCustomerForDiscount!!.id, percent)
        selectedCustomerForDiscount = null
      }
    )
  }
}

@Composable
fun AdjustWalletDialog(
  customer: CustomerRecord,
  onDismiss: () -> Unit,
  onConfirm: (amount: Int, isAdd: Boolean, reason: String) -> Unit
) {
  var amountText by remember { mutableStateOf("100") }
  var isAdd by remember { mutableStateOf(true) }
  var reason by remember { mutableStateOf("অ্যাডমিন ব্যালেন্স অ্যাডজাস্টমেন্ট") }

  Dialog(onDismissRequest = onDismiss) {
    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Text(
          text = "${customer.name} - ওয়ালেট অ্যাডজাস্টমেন্ট",
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
          color = RongdhonuPurplePrimary
        )
        Text(text = "বর্তমান ব্যালেন্স: ৳${customer.walletBalanceBdt}", style = MaterialTheme.typography.bodySmall)

        Spacer(modifier = Modifier.height(12.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Button(
            onClick = { isAdd = true },
            colors = ButtonDefaults.buttonColors(containerColor = if (isAdd) Color(0xFF2E7D32) else Color.LightGray),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.weight(1f)
          ) {
            Text("+ টাকা যোগ")
          }
          Button(
            onClick = { isAdd = false },
            colors = ButtonDefaults.buttonColors(containerColor = if (!isAdd) Color(0xFFC62828) else Color.LightGray),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.weight(1f)
          ) {
            Text("- টাকা কর্তন")
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
          value = amountText,
          onValueChange = { amountText = it },
          label = { Text("টাকার পরিমাণ (৳)") },
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
          value = reason,
          onValueChange = { reason = it },
          label = { Text("কারণ / রেফারেন্স") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          OutlinedButton(onClick = onDismiss, modifier = Modifier.weight(1f)) {
            Text("বাতিল")
          }
          Button(
            onClick = {
              val amt = amountText.toIntOrNull() ?: 0
              if (amt > 0) onConfirm(amt, isAdd, reason)
            },
            colors = ButtonDefaults.buttonColors(containerColor = RongdhonuPurplePrimary),
            modifier = Modifier.weight(1f)
          ) {
            Text("নিশ্চিত করুন")
          }
        }
      }
    }
  }
}

@Composable
fun SetDiscountDialog(
  customer: CustomerRecord,
  onDismiss: () -> Unit,
  onConfirm: (percent: Int) -> Unit
) {
  var percentText by remember { mutableStateOf(customer.specialDiscountPercent.toString()) }

  Dialog(onDismissRequest = onDismiss) {
    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Text(
          text = "${customer.name} - স্পেশাল ডিসকাউন্ট",
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
          color = RongdhonuPurplePrimary
        )
        Text(text = "এই গ্রাহক প্রতিটি সেবায় নির্দিষ্ট হারে ছাড় পাবেন।", style = MaterialTheme.typography.bodySmall)

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
          value = percentText,
          onValueChange = { percentText = it },
          label = { Text("ডিসকাউন্ট শতকরা হার (%)") },
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          OutlinedButton(onClick = onDismiss, modifier = Modifier.weight(1f)) {
            Text("বাতিল")
          }
          Button(
            onClick = {
              val p = percentText.toIntOrNull() ?: 0
              onConfirm(p)
            },
            colors = ButtonDefaults.buttonColors(containerColor = RongdhonuPurplePrimary),
            modifier = Modifier.weight(1f)
          ) {
            Text("সংরক্ষণ করুন")
          }
        }
      }
    }
  }
}

// ============================================================================
// 4. AUTOMATION & NOTIFICATION SYSTEM
// ============================================================================
@Composable
fun AdminAutomationAndNoticesView(
  viewModel: RongdhonuViewModel,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val notices by viewModel.repository.broadcastNotices.collectAsState()
  val payments by viewModel.repository.paymentRecords.collectAsState()
  var showNewNoticeDialog by remember { mutableStateOf(false) }

  val duePayments = payments.filter { it.dueAmountBdt > 0 }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(Color(0xFFF7F5FC))
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // Header
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column {
          Text(
            text = "অটোমেশন ও জরুরি নোটিশ বোর্ড",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = Color(0xFF1E1738)
          )
          Text(
            text = "হোম স্ক্রিনে বার্তা প্রচার ও স্বয়ংক্রিয় রিমাইন্ডার",
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF6B6580)
          )
        }

        Button(
          onClick = { showNewNoticeDialog = true },
          shape = RoundedCornerShape(8.dp),
          colors = ButtonDefaults.buttonColors(containerColor = RongdhonuPurplePrimary),
          contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
        ) {
          Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("নতুন নোটিশ")
        }
      }
    }

    // Active Notices
    item {
      Text(
        text = "সচল নোটিশসমূহ (${notices.size} টি)",
        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
      )
    }

    items(notices) { notice ->
      Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = if (notice.isUrgent) Icons.Default.Warning else Icons.Default.Campaign,
                contentDescription = null,
                tint = if (notice.isUrgent) Color(0xFFC62828) else RongdhonuPurplePrimary
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = notice.titleBn,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
              )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
              Switch(
                checked = notice.isActive,
                onCheckedChange = { viewModel.toggleNotice(notice.id) },
                colors = SwitchDefaults.colors(checkedThumbColor = RongdhonuPurplePrimary)
              )
              IconButton(onClick = { viewModel.deleteNotice(notice.id) }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFC62828))
              }
            }
          }

          Spacer(modifier = Modifier.height(6.dp))
          Text(text = notice.messageBn, style = MaterialTheme.typography.bodySmall, color = Color(0xFF37474F))
          Spacer(modifier = Modifier.height(4.dp))
          Text(text = "তারিখ: ${notice.date} • স্থিতি: ${if (notice.isActive) "হোম স্ক্রিনে প্রদর্শিত" else "বন্ধ"}", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        }
      }
    }

    // Due Payment Automated Reminders Section
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Payment, contentDescription = null, tint = RongdhonuAccentOrange)
            Spacer(modifier = Modifier.width(8.dp))
            Column {
              Text(
                text = "বকেয়া টাকার স্বয়ংক্রিয় রিমাইন্ডার সেন্টার",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
              )
              Text(
                text = "যাদের বকেয়া রয়েছে তাদের ১-ক্লিকে বার্তা পাঠান",
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          if (duePayments.isEmpty()) {
            Text(
              text = "আলহামদুলিল্লাহ! বর্তমানে কোনো গ্রাহকের বকেয়া নেই।",
              style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF2E7D32))
            )
          } else {
            duePayments.forEach { p ->
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Column(modifier = Modifier.weight(1f)) {
                  Text(text = "${p.customerName} (${p.customerPhone})", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                  Text(text = "সেবা: ${p.serviceTitleBn} • বকেয়া: ৳${p.dueAmountBdt}", style = MaterialTheme.typography.labelSmall, color = Color(0xFFD84315))
                }

                Button(
                  onClick = {
                    val msg = "আসসালামু আলাইকুম ${p.customerName}, রংধনু ডিজিটাল স্টুডিও থেকে আপনার '${p.serviceTitleBn}' সেবার ৳${p.dueAmountBdt} টাকা বকেয়া রয়েছে। দয়া করে পরিশোধ করুন।"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/88${p.customerPhone.replace("-", "")}?text=${Uri.encode(msg)}"))
                    try { context.startActivity(intent) } catch (e: Exception) {}
                  },
                  colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
                  shape = RoundedCornerShape(8.dp),
                  contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                ) {
                  Icon(imageVector = Icons.Default.Phone, contentDescription = null, modifier = Modifier.size(14.dp))
                  Spacer(modifier = Modifier.width(4.dp))
                  Text("রিমাইন্ডার", style = MaterialTheme.typography.labelSmall)
                }
              }
            }
          }
        }
      }
    }
  }

  if (showNewNoticeDialog) {
    NewNoticeDialog(
      onDismiss = { showNewNoticeDialog = false },
      onPublish = { title, message, isUrgent ->
        viewModel.publishNotice(title, message, isUrgent)
        showNewNoticeDialog = false
      }
    )
  }
}

@Composable
fun NewNoticeDialog(
  onDismiss: () -> Unit,
  onPublish: (title: String, message: String, isUrgent: Boolean) -> Unit
) {
  var title by remember { mutableStateOf("") }
  var message by remember { mutableStateOf("") }
  var isUrgent by remember { mutableStateOf(false) }

  Dialog(onDismissRequest = onDismiss) {
    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Text(
          text = "নতুন নোটিশ প্রচার করুন",
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
          color = RongdhonuPurplePrimary
        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
          value = title,
          onValueChange = { title = it },
          label = { Text("নোটিশের শিরোনাম *") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
          value = message,
          onValueChange = { message = it },
          label = { Text("বিস্তারিত বার্তা *") },
          maxLines = 3,
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(text = "জরুরি লাল সতর্কতা হিসেবে দেখাও", style = MaterialTheme.typography.bodySmall)
          Switch(
            checked = isUrgent,
            onCheckedChange = { isUrgent = it },
            colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFFC62828))
          )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          OutlinedButton(onClick = onDismiss, modifier = Modifier.weight(1f)) {
            Text("বাতিল")
          }
          Button(
            onClick = {
              if (title.isNotBlank() && message.isNotBlank()) {
                onPublish(title, message, isUrgent)
              }
            },
            colors = ButtonDefaults.buttonColors(containerColor = RongdhonuPurplePrimary),
            modifier = Modifier.weight(1f)
          ) {
            Text("প্রচার করুন")
          }
        }
      }
    }
  }
}

// ============================================================================
// 5. ADVANCED SECURITY SYSTEM & SESSION MANAGEMENT
// ============================================================================
@Composable
fun AdminSecuritySystemView(
  viewModel: RongdhonuViewModel,
  modifier: Modifier = Modifier
) {
  val adminRole by viewModel.adminRole.collectAsState()
  val twoFactorEnabled by viewModel.twoFactorEnabled.collectAsState()
  val sessions by viewModel.repository.adminSessions.collectAsState()
  val alerts by viewModel.repository.securityAlerts.collectAsState()

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(Color(0xFFF7F5FC))
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // 2FA Control
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "টু-ফ্যাক্টর অথেনটিকেশন (2FA)",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
              )
              Text(
                text = "অ্যাডমিন লগইনে মোবাইল ওটিপি সিকিউরিটি নিশ্চিত করুন",
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray
              )
            }
            Switch(
              checked = twoFactorEnabled,
              onCheckedChange = { viewModel.toggleTwoFactor() },
              colors = SwitchDefaults.colors(checkedThumbColor = RongdhonuPurplePrimary)
            )
          }
        }
      }
    }

    // Role switcher
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "স্টাফ ও ভূমিকা পারমিশন (Role Management)",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
          )
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = "বর্তমান অ্যাক্টিভ রোল: ${adminRole.titleBn} (${adminRole.descriptionBn})",
            style = MaterialTheme.typography.bodySmall,
            color = RongdhonuPurplePrimary
          )

          Spacer(modifier = Modifier.height(10.dp))

          Row(
            modifier = Modifier
              .fillMaxWidth()
              .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            AdminRole.values().forEach { role ->
              val isSel = role == adminRole
              Surface(
                shape = RoundedCornerShape(8.dp),
                color = if (isSel) RongdhonuPurplePrimary else Color(0xFFF3EEF9),
                modifier = Modifier.clickable { viewModel.switchAdminRole(role) }
              ) {
                Text(
                  text = role.titleBn,
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                    color = if (isSel) Color.White else RongdhonuPurplePrimary
                  ),
                  modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                )
              }
            }
          }
        }
      }
    }

    // Active Sessions
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "সক্রিয় লগইন সেশন ও ডিভাইস (${sessions.size} টি)",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
          )
          Spacer(modifier = Modifier.height(10.dp))

          sessions.forEach { ses ->
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(text = ses.deviceName, style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                  if (ses.isCurrent) {
                    Spacer(modifier = Modifier.width(6.dp))
                    Surface(shape = RoundedCornerShape(4.dp), color = Color(0xFFE8F5E9)) {
                      Text("বর্তমান সেশন", style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp, color = Color(0xFF2E7D32)), modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp))
                    }
                  }
                }
                Text(text = "IP: ${ses.ipAddress} • ${ses.location}", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
              }

              if (!ses.isCurrent) {
                OutlinedButton(
                  onClick = { viewModel.terminateSession(ses.id) },
                  shape = RoundedCornerShape(6.dp),
                  contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp)
                ) {
                  Text("বাতিল", style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFFC62828)))
                }
              }
            }
          }
        }
      }
    }

    // Security Alerts
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "নিরাপত্তা সতর্কতা ও অডিট অ্যালার্ট",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
          )
          Spacer(modifier = Modifier.height(10.dp))

          if (alerts.isEmpty()) {
            Text("কোনো নিরাপত্তা সতর্কতা নেই। সিস্টেম সম্পূর্ণ নিরাপদ।", style = MaterialTheme.typography.bodySmall, color = Color(0xFF2E7D32))
          } else {
            alerts.forEach { alt ->
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
              ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                  Icon(imageVector = Icons.Default.Shield, contentDescription = null, tint = RongdhonuPurplePrimary, modifier = Modifier.size(18.dp))
                  Spacer(modifier = Modifier.width(8.dp))
                  Column {
                    Text(text = alt.titleBn, style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                    Text(text = alt.detailBn, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                  }
                }

                IconButton(onClick = { viewModel.dismissSecurityAlert(alt.id) }) {
                  Icon(imageVector = Icons.Default.Close, contentDescription = null, modifier = Modifier.size(16.dp))
                }
              }
            }
          }
        }
      }
    }
  }
}

// ============================================================================
// 6. OFFLINE CASH PAYMENT & INVOICE DIALOGS
// ============================================================================
@Composable
fun OfflineCashPaymentDialog(
  onDismiss: () -> Unit,
  onRecord: (customerName: String, customerPhone: String, serviceTitle: String, price: Int, paid: Int, notes: String) -> Unit
) {
  var name by remember { mutableStateOf("") }
  var phone by remember { mutableStateOf("") }
  var serviceTitle by remember { mutableStateOf("জরুরি NID সংশোধন ও প্রিন্ট") }
  var priceText by remember { mutableStateOf("350") }
  var paidText by remember { mutableStateOf("350") }
  var notes by remember { mutableStateOf("কাউন্টার নগদ গ্রহণ") }

  Dialog(onDismissRequest = onDismiss) {
    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
    ) {
      Column(
        modifier = Modifier
          .padding(16.dp)
          .verticalScroll(rememberScrollState())
      ) {
        Text(
          text = "অফলাইন নগদ ক্যাশ পেমেন্ট রেকর্ড",
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
          color = RongdhonuPurplePrimary
        )
        Text(text = "কাউন্টারে সরাসরি সেবা গ্রহণকারী গ্রাহকের ক্যাশ জমা ও রিসিট", style = MaterialTheme.typography.bodySmall)

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
          value = name,
          onValueChange = { name = it },
          label = { Text("গ্রাহকের নাম *") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
          value = phone,
          onValueChange = { phone = it },
          label = { Text("মোবাইল / WhatsApp নম্বর *") },
          singleLine = true,
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
          value = serviceTitle,
          onValueChange = { serviceTitle = it },
          label = { Text("সেবার নাম *") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          OutlinedTextField(
            value = priceText,
            onValueChange = { priceText = it },
            label = { Text("সেবার মূল্য (৳)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.weight(1f)
          )

          OutlinedTextField(
            value = paidText,
            onValueChange = { paidText = it },
            label = { Text("প্রাপ্ত ক্যাশ (৳)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.weight(1f)
          )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
          value = notes,
          onValueChange = { notes = it },
          label = { Text("মন্তব্য / নোটস") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          OutlinedButton(onClick = onDismiss, modifier = Modifier.weight(1f)) {
            Text("বাতিল")
          }
          Button(
            onClick = {
              val price = priceText.toIntOrNull() ?: 0
              val paid = paidText.toIntOrNull() ?: 0
              if (name.isNotBlank() && serviceTitle.isNotBlank()) {
                onRecord(name, phone, serviceTitle, price, paid, notes)
              }
            },
            colors = ButtonDefaults.buttonColors(containerColor = RongdhonuPurplePrimary),
            modifier = Modifier.weight(1f)
          ) {
            Text("রেকর্ড ও রিসিট")
          }
        }
      }
    }
  }
}

@Composable
fun PaymentInvoiceDialog(
  record: CustomerPaymentRecord,
  onDismiss: () -> Unit
) {
  val context = LocalContext.current
  val dateStr = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(Date(record.timestamp))

  val receiptText = buildString {
    append("===== রংধনু ডিজিটাল স্টুডিও =====\n")
    append("নাগরিক সেবা ও মানি রিসিট (অফিসিয়াল ইনভয়েস)\n")
    append("---------------------------------\n")
    append("রিসিট নং: INV-${record.paymentId}\n")
    append("তারিখ: $dateStr\n")
    append("গ্রাহক: ${record.customerName}\n")
    append("ফোন: ${record.customerPhone}\n")
    append("সেবা: ${record.serviceTitleBn}\n")
    append("মোট ফি: ৳${record.servicePriceBdt}\n")
    append("পরিশোধিত: ৳${record.verifiedAmountBdt}\n")
    append("বকেয়া: ৳${record.dueAmountBdt}\n")
    append("পেমেন্ট মাধ্যম: ${record.paymentMethod.displayNameBn}\n")
    append("লেনদেন আইডি (TrxID): ${record.trxId}\n")
    append("স্ট্যাটাস: ${record.status.titleBn}\n")
    append("ইস্যুকারী: ${record.assignedAdmin}\n")
    append("---------------------------------\n")
    append("রংধনু ডিজিটাল স্টুডিও • 01632-042441\n")
  }

  Dialog(onDismissRequest = onDismiss) {
    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
    ) {
      Column(
        modifier = Modifier
          .padding(18.dp)
          .verticalScroll(rememberScrollState())
      ) {
        // Receipt Header
        Column(
          modifier = Modifier.fillMaxWidth(),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text(
            text = "রংধনু ডিজিটাল স্টুডিও",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = RongdhonuPurpleDark
          )
          Text(
            text = "অনলাইন নাগরিক সেবা ও মানি রিসিট",
            style = MaterialTheme.typography.labelMedium,
            color = Color(0xFF6B6580)
          )
          Text(
            text = "ইনভয়েস নং: INV-${record.paymentId}",
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = RongdhonuPurplePrimary)
          )
        }

        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider(color = Color(0xFFE0E0E0))
        Spacer(modifier = Modifier.height(10.dp))

        // Receipt Details
        ReceiptDetailRow("গ্রাহকের নাম:", record.customerName)
        ReceiptDetailRow("মোবাইল:", record.customerPhone)
        ReceiptDetailRow("সেবা:", record.serviceTitleBn)
        ReceiptDetailRow("অর্ডার নং:", record.orderNumber)
        ReceiptDetailRow("তারিখ:", dateStr)
        ReceiptDetailRow("পেমেন্ট মাধ্যম:", record.paymentMethod.displayNameBn)
        ReceiptDetailRow("TrxID:", record.trxId)

        Spacer(modifier = Modifier.height(10.dp))
        HorizontalDivider(color = Color(0xFFE0E0E0))
        Spacer(modifier = Modifier.height(10.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text("মোট সেবামূল্য:", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
          Text("৳ ${record.servicePriceBdt}", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
        }

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text("পরিশোধিত:", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32)))
          Text("৳ ${record.verifiedAmountBdt}", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32)))
        }

        if (record.dueAmountBdt > 0) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("অবশিষ্ট বকেয়া:", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = Color(0xFFC62828)))
            Text("৳ ${record.dueAmountBdt}", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = Color(0xFFC62828)))
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Seal
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = Color(0xFFE8F5E9),
          border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF2E7D32)),
          modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(imageVector = Icons.Default.Verified, contentDescription = null, tint = Color(0xFF2E7D32), modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("অনুমোদিত ও ভেরিফাইড (SEALED)", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32)))
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          OutlinedButton(
            onClick = {
              val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, "Rongdhonu Payment Receipt")
                putExtra(Intent.EXTRA_TEXT, receiptText)
              }
              context.startActivity(Intent.createChooser(shareIntent, "মানি রিসিট শেয়ার করুন"))
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.weight(1f)
          ) {
            Icon(imageVector = Icons.Default.Share, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("শেয়ার")
          }

          Button(
            onClick = onDismiss,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = RongdhonuPurplePrimary),
            modifier = Modifier.weight(1f)
          ) {
            Text("সম্পন্ন")
          }
        }
      }
    }
  }
}

@Composable
fun ReceiptDetailRow(label: String, value: String) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 2.dp),
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Text(text = label, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
    Text(text = value, style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium))
  }
}
