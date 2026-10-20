package com.example.ui.screens

import androidx.activity.compose.BackHandler
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.ToggleOff
import androidx.compose.material.icons.filled.ToggleOn
import androidx.compose.material.icons.filled.VerifiedUser
import com.example.data.model.AdminRole
import com.example.data.model.AppBuildRelease
import com.example.data.model.AppPlatform
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.AuditLog
import com.example.data.model.CitizenService
import com.example.data.model.OrderStatus
import com.example.data.model.PaymentGatewayConfig
import com.example.data.model.PaymentMethod
import com.example.data.model.ServiceCategory
import com.example.data.model.ServiceOrder
import com.example.ui.AdminTab
import com.example.ui.RongdhonuViewModel
import com.example.ui.theme.BkashPink
import com.example.ui.theme.NagadOrange
import com.example.ui.theme.RocketViolet
import com.example.ui.theme.RongdhonuAccentOrange
import com.example.ui.theme.RongdhonuPurpleDark
import com.example.ui.theme.RongdhonuPurpleLight
import com.example.ui.theme.RongdhonuPurplePrimary
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminPanelScreen(
  viewModel: RongdhonuViewModel,
  onBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  BackHandler { onBack() }

  val isAuthenticated by viewModel.isAdminAuthenticated.collectAsState()
  val adminTab by viewModel.adminTab.collectAsState()
  val services by viewModel.repository.services.collectAsState()
  val orders by viewModel.repository.orders.collectAsState()
  val gateways by viewModel.repository.gateways.collectAsState()
  val auditLogs by viewModel.repository.auditLogs.collectAsState()
  val editingService by viewModel.editingService.collectAsState()
  val showEditServiceDialog by viewModel.showEditServiceDialog.collectAsState()
  val showCashPaymentDialog by viewModel.showCashPaymentDialog.collectAsState()
  val selectedPaymentForInvoice by viewModel.selectedPaymentForInvoice.collectAsState()
  val actionConfirmation by viewModel.actionConfirmation.collectAsState()

  if (!isAuthenticated) {
    AdminLoginView(
      onLogin = { passcode -> viewModel.loginAdmin(passcode) },
      onBack = onBack
    )
    return
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Column {
            Text(
              text = "রংধনু অ্যাডমিন কন্ট্রোল প্যানেল",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
              color = Color.White
            )
            Text(
              text = "Super Admin • রংধনু ডিজিটাল স্টুডিও",
              style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
              color = Color.White.copy(alpha = 0.85f)
            )
          }
        },
        navigationIcon = {
          IconButton(onClick = onBack) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
          }
        },
        actions = {
          IconButton(onClick = { viewModel.logoutAdmin() }) {
            Icon(imageVector = Icons.Default.Logout, contentDescription = "Logout", tint = Color.White)
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = RongdhonuPurpleDark)
      )
    },
    modifier = modifier
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ) {
      // Horizontal Tab strip
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .background(Color.White)
          .horizontalScroll(rememberScrollState())
          .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        AdminTab.values().forEach { tab ->
          val isSelected = tab == adminTab
          Surface(
            shape = RoundedCornerShape(10.dp),
            color = if (isSelected) RongdhonuPurplePrimary else Color(0xFFF3EEF9),
            modifier = Modifier
              .clickable { viewModel.selectAdminTab(tab) }
              .testTag("admin_tab_${tab.name.lowercase()}")
          ) {
            Text(
              text = tab.titleBn,
              style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
              ),
              color = if (isSelected) Color.White else RongdhonuPurplePrimary,
              modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )
          }
        }
      }

      // Content for selected Admin Tab
      when (adminTab) {
        AdminTab.DASHBOARD -> AdminDashboardView(
          viewModel = viewModel,
          services = services,
          orders = orders,
          gateways = gateways
        )
        AdminTab.AI_ASSISTANT -> AdminAiControlCenterView(viewModel = viewModel)
        AdminTab.ANALYTICS -> AdminLiveAnalyticsView(viewModel = viewModel)
        AdminTab.PAYMENTS -> CustomerPaymentsDashboardView(viewModel = viewModel)
        AdminTab.SERVICES -> AdminServicesManagementView(
          services = services,
          onAddNew = { viewModel.startAddService() },
          onEdit = { viewModel.startEditService(it) },
          onToggle = { viewModel.toggleServiceActive(it) },
          onDelete = { serviceId ->
            viewModel.requestConfirmation(
              titleBn = "সেবা ডিলিট অনুমোদন",
              messageBn = "আপনি কি নিশ্চিতভাবে এই সেবাটি মুছে ফেলতে চান?",
              isDestructive = true,
              onConfirm = { viewModel.deleteService(serviceId) }
            )
          }
        )
        AdminTab.CUSTOMERS -> AdminCustomersManagementView(viewModel = viewModel)
        AdminTab.ORDERS -> AdminOrdersManagementView(
          orders = orders,
          onUpdateStatus = { id, status, remarks ->
            viewModel.updateOrderStatus(id, status, remarks)
          }
        )
        AdminTab.AUTOMATION -> AdminAutomationAndNoticesView(viewModel = viewModel)
        AdminTab.SECURITY -> AdminSecuritySystemView(viewModel = viewModel)
        AdminTab.DOWNLOADS -> AdminAppDownloadsView(viewModel = viewModel)
        AdminTab.GATEWAYS -> AdminGatewaysManagementView(
          gateways = gateways,
          onUpdateGateway = { m, e, a, i ->
            viewModel.updateGateway(m, e, a, i)
          }
        )
        AdminTab.AUDIT_LOGS -> AdminAuditLogsView(logs = auditLogs)
      }
    }
  }

  if (showEditServiceDialog) {
    EditServiceDialog(
      initialService = editingService,
      onDismiss = { viewModel.closeEditServiceDialog() },
      onSave = { viewModel.saveService(it) }
    )
  }

  if (showCashPaymentDialog) {
    OfflineCashPaymentDialog(
      onDismiss = { viewModel.closeCashPaymentDialog() },
      onRecord = { name, phone, srv, price, paid, notes ->
        viewModel.recordCashPayment(name, phone, srv, price, paid, notes)
      }
    )
  }

  if (selectedPaymentForInvoice != null) {
    PaymentInvoiceDialog(
      record = selectedPaymentForInvoice!!,
      onDismiss = { viewModel.closePaymentInvoice() }
    )
  }

  if (actionConfirmation != null) {
    val conf = actionConfirmation!!
    AlertDialog(
      onDismissRequest = { viewModel.dismissConfirmation() },
      title = { Text(conf.titleBn, fontWeight = FontWeight.Bold) },
      text = { Text(conf.messageBn) },
      confirmButton = {
        Button(
          onClick = conf.onConfirm,
          colors = ButtonDefaults.buttonColors(
            containerColor = if (conf.isDestructive) Color(0xFFC62828) else RongdhonuPurplePrimary
          )
        ) {
          Text(conf.confirmButtonBn)
        }
      },
      dismissButton = {
        OutlinedButton(onClick = { viewModel.dismissConfirmation() }) {
          Text("বাতিল")
        }
      }
    )
  }
}

@Composable
fun AdminLoginView(
  onLogin: (String) -> Boolean,
  onBack: () -> Unit
) {
  var username by remember { mutableStateOf("admin") }
  var passcode by remember { mutableStateOf("") }
  var hasError by remember { mutableStateOf(false) }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF7F5FC))
      .padding(24.dp),
    contentAlignment = Alignment.Center
  ) {
    Card(
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(
        modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Box(
          modifier = Modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(RongdhonuPurplePrimary),
          contentAlignment = Alignment.Center
        ) {
          Icon(imageVector = Icons.Default.Lock, contentDescription = null, tint = Color.White, modifier = Modifier.size(28.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
          text = "অ্যাডমিন স্মার্ট লগইন",
          style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
          color = RongdhonuPurplePrimary
        )
        Text(
          text = "রংধনু ডিজিটাল স্টুডিওর অনুমোদিত ও সুরক্ষিত নিয়ন্ত্রণ কেন্দ্র",
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(18.dp))

        OutlinedTextField(
          value = username,
          onValueChange = { username = it },
          label = { Text("অ্যাডমিন ইউজারনেম") },
          placeholder = { Text("admin") },
          singleLine = true,
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
          value = passcode,
          onValueChange = { passcode = it; hasError = false },
          label = { Text("পাসওয়ার্ড / পাসকোড") },
          placeholder = { Text("123456 বা admin786") },
          visualTransformation = PasswordVisualTransformation(),
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
          singleLine = true,
          shape = RoundedCornerShape(12.dp),
          isError = hasError,
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_admin_passcode")
        )

        if (hasError) {
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = "ভুল তথ্য! সঠিক পাসকোড (যেমন: 123456) ব্যবহার করুন।",
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.error)
          )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
          onClick = {
            if (!onLogin(passcode)) {
              hasError = true
            }
          },
          shape = RoundedCornerShape(12.dp),
          colors = ButtonDefaults.buttonColors(containerColor = RongdhonuPurplePrimary),
          modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .testTag("btn_admin_login_submit")
        ) {
          Text("নিরাপদ লগইন করুন", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
          onClick = onBack,
          colors = ButtonDefaults.outlinedButtonColors()
        ) {
          Text("অ্যাপে ফিরে যান")
        }
      }
    }
  }
}

@Composable
fun AdminDashboardView(
  viewModel: RongdhonuViewModel,
  services: List<CitizenService>,
  orders: List<ServiceOrder>,
  gateways: List<PaymentGatewayConfig>
) {
  val totalRevenue = orders.sumOf { it.feePaidBdt }
  val pendingOrders = orders.count { it.status == OrderStatus.PENDING || it.status == OrderStatus.PROCESSING }
  val completedOrders = orders.count { it.status == OrderStatus.COMPLETED }
  val aiSummary by viewModel.aiSummaryText.collectAsState()
  val adminRole by viewModel.adminRole.collectAsState()
  val twoFactorEnabled by viewModel.twoFactorEnabled.collectAsState()
  val payments by viewModel.repository.paymentRecords.collectAsState()

  val verifiedTotal = payments.filter {
    it.status == PaymentVerificationStatus.VERIFIED ||
      it.status == PaymentVerificationStatus.FULLY_PAID ||
      it.status == PaymentVerificationStatus.PARTIALLY_PAID
  }.sumOf { it.verifiedAmountBdt }

  val totalDues = payments.sumOf { it.dueAmountBdt }
  val pendingVerifications = payments.count {
    it.status == PaymentVerificationStatus.SUBMITTED || it.status == PaymentVerificationStatus.PENDING
  }

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // AI Smart Business Intelligence Section
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
          .fillMaxWidth()
          .border(1.dp, RongdhonuPurplePrimary.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
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
                  .size(36.dp)
                  .clip(CircleShape)
                  .background(RongdhonuPurplePrimary.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, tint = RongdhonuPurplePrimary, modifier = Modifier.size(20.dp))
              }
              Spacer(modifier = Modifier.width(10.dp))
              Column {
                Text(
                  text = "AI Smart Control & Analytics",
                  style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                  color = Color(0xFF1E1738)
                )
                Text(
                  text = "স্বয়ংক্রিয় সমস্যা শনাক্তকরণ ও আর্থিক সারাংশ",
                  style = MaterialTheme.typography.labelSmall,
                  color = Color(0xFF6B6580)
                )
              }
            }

            Button(
              onClick = { viewModel.generateAiBusinessSummary() },
              colors = ButtonDefaults.buttonColors(containerColor = RongdhonuPurplePrimary),
              shape = RoundedCornerShape(8.dp),
              contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
            ) {
              Text("AI রিপোর্ট তৈরি", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
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
              Column(modifier = Modifier.padding(12.dp)) {
                Text(
                  text = aiSummary!!,
                  style = MaterialTheme.typography.bodySmall.copy(lineHeight = 20.sp),
                  color = Color(0xFF2A1E4A)
                )
                Spacer(modifier = Modifier.height(8.dp))
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

    // Financial Highlights & Shortcut to Payments Dashboard
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
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
            Column {
              Text(text = "গ্রাহক পেমেন্ট ও লেনদেন সারসংক্ষেপ", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
              Text(text = "আদায়কৃত: ৳$verifiedTotal • বকেয়া: ৳$totalDues", style = MaterialTheme.typography.labelSmall, color = Color(0xFF6B6580))
            }
            Button(
              onClick = { viewModel.selectAdminTab(AdminTab.PAYMENTS) },
              colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
              shape = RoundedCornerShape(8.dp),
              contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
            ) {
              Text("পেমেন্ট ড্যাশবোর্ড →", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            MetricCard(title = "যাচাইকৃত টাকা", value = "৳$verifiedTotal", color = Color(0xFF2E7D32), modifier = Modifier.weight(1f))
            MetricCard(title = "মোট বকেয়া", value = "৳$totalDues", color = Color(0xFFD84315), modifier = Modifier.weight(1f))
            MetricCard(title = "অপেক্ষমাণ যাচাই", value = "$pendingVerifications টি", color = Color(0xFFF57F17), modifier = Modifier.weight(1f))
          }
        }
      }
    }

    // Metrics Row 1
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        MetricCard(title = "মোট সক্রিয় সেবা", value = "${services.size}", color = RongdhonuPurplePrimary, modifier = Modifier.weight(1f))
        MetricCard(title = "মোট রাজস্ব ভলিউম", value = "৳$totalRevenue", color = Color(0xFF008947), modifier = Modifier.weight(1f))
      }
    }

    // Metrics Row 2
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        MetricCard(title = "চলতি আবেদন", value = "$pendingOrders", color = RongdhonuAccentOrange, modifier = Modifier.weight(1f))
        MetricCard(title = "সম্পন্ন আবেদন", value = "$completedOrders", color = Color(0xFF1976D2), modifier = Modifier.weight(1f))
      }
    }

    // Security & Role Control Switchboard
    item {
      Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Text(
            text = "নিরাপত্তা ও ভূমিকা নিয়ন্ত্রণ (Security & Roles)",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
          )
          Spacer(modifier = Modifier.height(10.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(text = "বর্তমান ভূমিকা: ${adminRole.titleBn}", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
              Text(text = adminRole.descriptionBn, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          Row(
            modifier = Modifier
              .fillMaxWidth()
              .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            AdminRole.values().forEach { role ->
              val isSelected = role == adminRole
              Surface(
                shape = RoundedCornerShape(12.dp),
                color = if (isSelected) RongdhonuPurplePrimary else Color(0xFFF3EEF9),
                modifier = Modifier.clickable { viewModel.switchAdminRole(role) }
              ) {
                Text(
                  text = role.titleBn,
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    color = if (isSelected) Color.White else RongdhonuPurplePrimary
                  ),
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(10.dp))
          HorizontalDivider(color = Color(0xFFF0EBF7))
          Spacer(modifier = Modifier.height(10.dp))

          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(text = "টু-ফ্যাক্টর অথেনটিকেশন (2FA)", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
              Text(text = "লগইনে দ্বিতীয় স্তরের অনুমোদন", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
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

    // App Distribution & Builds Shortcut
    item {
      Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(text = "অ্যাপ বিল্ড ও ডাউনলোড সেন্টার", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
              Text(text = "Android APK (23MB) • Windows EXE • PWA", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            }
            Button(
              onClick = { viewModel.selectAdminTab(AdminTab.DOWNLOADS) },
              colors = ButtonDefaults.buttonColors(containerColor = RongdhonuPurpleDark),
              shape = RoundedCornerShape(8.dp),
              contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
            ) {
              Text("রিলিজ পরিচালনা", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
            }
          }
        }
      }
    }

    // Payment Channels Status
    item {
      Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "সচল পেমেন্ট চ্যানেল ও গেটওয়ে",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
          )
          Spacer(modifier = Modifier.height(8.dp))
          gateways.forEach { gw ->
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(gw.method.displayNameBn, style = MaterialTheme.typography.bodyMedium)
              Text(
                if (gw.isEnabled) "সক্রিয় (${gw.accountNumber})" else "কনফিগারেশন বাকি (Manual)",
                style = MaterialTheme.typography.bodyMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = if (gw.isEnabled) Color(0xFF008947) else Color.Gray
                )
              )
            }
          }
        }
      }
    }
  }
}

@Composable
fun AdminAppDownloadsView(
  viewModel: RongdhonuViewModel,
  modifier: Modifier = Modifier
) {
  val releases by viewModel.repository.appReleases.collectAsState()
  var editingRelease by remember { mutableStateOf<AppBuildRelease?>(null) }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .background(Color(0xFFF7F5FC))
      .padding(14.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    item {
      Column {
        Text(
          text = "অ্যাপ ডাউনলোড ও ভার্সন কন্ট্রোল",
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
          color = Color(0xFF1E1738)
        )
        Text(
          text = "Android APK, Windows EXE, ZIP এবং PWA রিলিজ পরিচালনা",
          style = MaterialTheme.typography.bodySmall,
          color = Color(0xFF6B6580)
        )
      }
    }

    items(releases) { release ->
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
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = release.platform.titleBn,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = RongdhonuPurplePrimary
              )
              Text(
                text = "${release.fileName} • ${release.fileSize}",
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
                color = Color(0xFF554D69)
              )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = if (release.isDownloadActive) "সক্রিয়" else "বন্ধ",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = if (release.isDownloadActive) Color(0xFF2E7D32) else Color(0xFFC62828)
                )
              )
              Spacer(modifier = Modifier.width(6.dp))
              Switch(
                checked = release.isDownloadActive,
                onCheckedChange = { viewModel.toggleDownloadActive(release.id) },
                colors = SwitchDefaults.colors(checkedThumbColor = RongdhonuPurplePrimary)
              )
            }
          }

          Spacer(modifier = Modifier.height(8.dp))
          Text(text = "ভার্সন: ${release.version} • মোট ডাউনলোড: ${release.downloadCount} বার", style = MaterialTheme.typography.bodySmall)
          Text(text = "SHA-256: ${release.sha256Checksum}", style = MaterialTheme.typography.labelSmall.copy(fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace, fontSize = 9.sp), color = Color.Gray, maxLines = 1)

          Spacer(modifier = Modifier.height(10.dp))

          OutlinedButton(
            onClick = { editingRelease = release },
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
          ) {
            Icon(imageVector = Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("রিলিজ তথ্য সম্পাদনা", style = MaterialTheme.typography.labelSmall)
          }
        }
      }
    }
  }

  if (editingRelease != null) {
    EditAppReleaseDialog(
      release = editingRelease!!,
      onDismiss = { editingRelease = null },
      onSave = { version, downloadUrl, checksum, notes ->
        viewModel.updateAppRelease(editingRelease!!.id, version, downloadUrl, checksum, notes)
        editingRelease = null
      }
    )
  }
}

@Composable
fun EditAppReleaseDialog(
  release: AppBuildRelease,
  onDismiss: () -> Unit,
  onSave: (version: String, downloadUrl: String, checksum: String, notes: String) -> Unit
) {
  var version by remember { mutableStateOf(release.version) }
  var downloadUrl by remember { mutableStateOf(release.downloadUrl) }
  var checksum by remember { mutableStateOf(release.sha256Checksum) }
  var notes by remember { mutableStateOf(release.releaseNotesBn) }

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
          text = "${release.platform.titleBn} সম্পাদনা",
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
          color = RongdhonuPurplePrimary
        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
          value = version,
          onValueChange = { version = it },
          label = { Text("ভার্সন নম্বর") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
          value = downloadUrl,
          onValueChange = { downloadUrl = it },
          label = { Text("ডাউনলোড URL / ফাইল পাথ") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
          value = checksum,
          onValueChange = { checksum = it },
          label = { Text("SHA-256 চেকসাম") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
          value = notes,
          onValueChange = { notes = it },
          label = { Text("রিলিজ নোটস (বাংলা)") },
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
            onClick = { onSave(version, downloadUrl, checksum, notes) },
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

@Composable
fun MetricCard(title: String, value: String, color: Color, modifier: Modifier = Modifier) {
  Card(
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = modifier
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Text(text = title, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = value,
        style = MaterialTheme.typography.titleLarge.copy(
          fontWeight = FontWeight.Bold,
          color = color
        )
      )
    }
  }
}

@Composable
fun AdminServicesManagementView(
  services: List<CitizenService>,
  onAddNew: () -> Unit,
  onEdit: (CitizenService) -> Unit,
  onToggle: (String) -> Unit,
  onDelete: (String) -> Unit
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(12.dp)
  ) {
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "সকল নাগরিক সেবা (${services.size})",
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        Button(
          onClick = onAddNew,
          shape = RoundedCornerShape(8.dp),
          colors = ButtonDefaults.buttonColors(containerColor = RongdhonuPurplePrimary),
          contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
          modifier = Modifier.testTag("btn_admin_add_service")
        ) {
          Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("নতুন সেবা")
        }
      }
    }

    items(services) { service ->
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
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "${service.serviceCode}: ${service.titleBn}",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )
            Text(
              text = "ক্যাটাগরি: ${service.category.titleBn} • ফি: ৳${service.totalFeeBdt}",
              style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
              color = Color.Gray
            )
          }

          Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { onToggle(service.id) }) {
              Icon(
                imageVector = if (service.isActive) Icons.Default.ToggleOn else Icons.Default.ToggleOff,
                contentDescription = "Toggle",
                tint = if (service.isActive) Color(0xFF008947) else Color.Gray,
                modifier = Modifier.size(28.dp)
              )
            }
            IconButton(onClick = { onEdit(service) }) {
              Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit", tint = RongdhonuPurplePrimary)
            }
            IconButton(onClick = { onDelete(service.id) }) {
              Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFC62828))
            }
          }
        }
      }
    }
  }
}

@Composable
fun AdminOrdersManagementView(
  orders: List<ServiceOrder>,
  onUpdateStatus: (String, OrderStatus, String) -> Unit
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item {
      Text(
        text = "আবেদন ও অর্ডার নিয়ন্ত্রণ (${orders.size})",
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
      )
    }

    items(orders) { order ->
      var remarksText by remember { mutableStateOf(order.adminRemarks) }

      Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = order.orderId,
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, color = RongdhonuPurplePrimary)
            )
            Surface(
              color = Color(order.status.colorHex).copy(alpha = 0.15f),
              shape = RoundedCornerShape(6.dp)
            ) {
              Text(
                text = order.status.titleBn,
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = Color(order.status.colorHex)
                ),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(6.dp))
          Text(text = order.serviceTitleBn, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
          Text(text = "গ্রাহক: ${order.citizenName} (${order.citizenPhone})", style = MaterialTheme.typography.bodySmall)
          Text(text = "আইডি নম্বর: ${order.identifierNumber}", style = MaterialTheme.typography.bodySmall)
          Text(text = "পেমেন্ট: ${order.paymentMethod.displayNameBn} (TrxID: ${order.trxId}, ৳${order.feePaidBdt})", style = MaterialTheme.typography.bodySmall)

          Spacer(modifier = Modifier.height(8.dp))

          OutlinedTextField(
            value = remarksText,
            onValueChange = { remarksText = it },
            label = { Text("অ্যাডমিন রিমার্কস / স্টুডিও আপডেট") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
          )

          Spacer(modifier = Modifier.height(10.dp))

          // Status Change buttons
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
          ) {
            Button(
              onClick = { onUpdateStatus(order.orderId, OrderStatus.PROCESSING, remarksText) },
              colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.weight(1f),
              contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp)
            ) {
              Text("প্রক্রিয়াধীন", style = MaterialTheme.typography.labelSmall)
            }

            Button(
              onClick = { onUpdateStatus(order.orderId, OrderStatus.COMPLETED, remarksText) },
              colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.weight(1f),
              contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp)
            ) {
              Text("সম্পন্ন", style = MaterialTheme.typography.labelSmall)
            }

            Button(
              onClick = { onUpdateStatus(order.orderId, OrderStatus.REJECTED, remarksText) },
              colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC62828)),
              shape = RoundedCornerShape(8.dp),
              modifier = Modifier.weight(1f),
              contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp)
            ) {
              Text("বাতিল", style = MaterialTheme.typography.labelSmall)
            }
          }
        }
      }
    }
  }
}

@Composable
fun AdminGatewaysManagementView(
  gateways: List<PaymentGatewayConfig>,
  onUpdateGateway: (PaymentMethod, Boolean, String, String) -> Unit
) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    item {
      Text(
        text = "পেমেন্ট গেটওয়ে ও মোবাইল ব্যাংকিং কনফিগারেশন",
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
      )
    }

    items(gateways) { gw ->
      var enabled by remember { mutableStateOf(gw.isEnabled) }
      var account by remember { mutableStateOf(gw.accountNumber) }
      var instructions by remember { mutableStateOf(gw.instructionsBn) }

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
            Text(
              text = gw.method.displayNameBn,
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )
            Switch(
              checked = enabled,
              onCheckedChange = {
                enabled = it
                onUpdateGateway(gw.method, enabled, account, instructions)
              },
              colors = SwitchDefaults.colors(checkedThumbColor = RongdhonuPurplePrimary)
            )
          }

          Spacer(modifier = Modifier.height(8.dp))

          OutlinedTextField(
            value = account,
            onValueChange = { account = it },
            label = { Text("মার্চেন্ট / একাউন্ট নম্বর") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
          )

          Spacer(modifier = Modifier.height(8.dp))

          OutlinedTextField(
            value = instructions,
            onValueChange = { instructions = it },
            label = { Text("গ্রাহকদের জন্য নির্দেশনা") },
            maxLines = 2,
            modifier = Modifier.fillMaxWidth()
          )

          Spacer(modifier = Modifier.height(10.dp))

          Button(
            onClick = { onUpdateGateway(gw.method, enabled, account, instructions) },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = RongdhonuPurplePrimary),
            modifier = Modifier.align(Alignment.End)
          ) {
            Text("সংরক্ষণ করুন")
          }
        }
      }
    }
  }
}

@Composable
fun AdminAuditLogsView(logs: List<AuditLog>) {
  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(10.dp)
  ) {
    item {
      Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier.padding(12.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF1B5E20))
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            text = "সিস্টেম স্ট্যাটাস: 100% সচল • Latency: 22ms • RLS Security: সক্রিয়",
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = Color(0xFF1B5E20))
          )
        }
      }
    }

    item {
      Text(
        text = "অডিট লগ ও পরিবর্তন ইতিহাস",
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
      )
    }

    items(logs) { log ->
      val time = SimpleDateFormat("dd MMM, hh:mm:ss a", Locale.getDefault()).format(Date(log.timestamp))
      Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(10.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(log.action, style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
            Text(time, style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), color = Color.Gray)
          }
          Text(log.target, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
          Text("দ্বারা: ${log.performedBy}", style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), color = Color.Gray)
        }
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditServiceDialog(
  initialService: CitizenService?,
  onDismiss: () -> Unit,
  onSave: (CitizenService) -> Unit
) {
  var serviceCode by remember { mutableStateOf(initialService?.serviceCode ?: "SRV-${(10..99).random()}") }
  var titleBn by remember { mutableStateOf(initialService?.titleBn ?: "") }
  var titleEn by remember { mutableStateOf(initialService?.titleEn ?: "") }
  var category by remember { mutableStateOf(initialService?.category ?: ServiceCategory.NID_VOTER) }
  var descriptionBn by remember { mutableStateOf(initialService?.descriptionBn ?: "") }
  var officialFee by remember { mutableStateOf(initialService?.officialFeeBdt?.toString() ?: "0") }
  var serviceCharge by remember { mutableStateOf(initialService?.serviceChargeBdt?.toString() ?: "100") }
  var portalUrl by remember { mutableStateOf(initialService?.officialPortalUrl ?: "https://") }
  var days by remember { mutableStateOf(initialService?.estimatedDays ?: "১-৩ কার্যদিবস") }

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Card(
      shape = RoundedCornerShape(18.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      modifier = Modifier
        .fillMaxWidth(0.95f)
        .padding(16.dp)
    ) {
      Column(
        modifier = Modifier
          .padding(18.dp)
          .verticalScroll(rememberScrollState())
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = if (initialService == null) "নতুন সেবা যোগ করুন" else "সেবা সম্পাদনা",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = RongdhonuPurplePrimary
          )
          IconButton(onClick = onDismiss) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
          value = serviceCode,
          onValueChange = { serviceCode = it },
          label = { Text("সার্ভিস কোড (যেমন: NID-03)") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
          value = titleBn,
          onValueChange = { titleBn = it },
          label = { Text("সেবার নাম (বাংলা) *") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth().testTag("input_admin_service_title_bn")
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
          value = titleEn,
          onValueChange = { titleEn = it },
          label = { Text("সেবার নাম (ইংরেজি)") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
          value = descriptionBn,
          onValueChange = { descriptionBn = it },
          label = { Text("সংক্ষিপ্ত বিবরণ *") },
          maxLines = 3,
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          OutlinedTextField(
            value = officialFee,
            onValueChange = { officialFee = it },
            label = { Text("সরকারি ফি (৳)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.weight(1f)
          )

          OutlinedTextField(
            value = serviceCharge,
            onValueChange = { serviceCharge = it },
            label = { Text("স্টুডিও চার্জ (৳)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.weight(1f)
          )
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
          value = portalUrl,
          onValueChange = { portalUrl = it },
          label = { Text("অফিসিয়াল সরকারি লিংক (URL)") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
          value = days,
          onValueChange = { days = it },
          label = { Text("আনুমানিক সময়সীমা") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
          onClick = {
            if (titleBn.isNotBlank()) {
              val srv = CitizenService(
                id = initialService?.id ?: "srv_${System.currentTimeMillis()}",
                serviceCode = serviceCode,
                titleBn = titleBn.trim(),
                titleEn = titleEn.trim(),
                category = category,
                descriptionBn = descriptionBn.trim(),
                officialFeeBdt = officialFee.toIntOrNull() ?: 0,
                serviceChargeBdt = serviceCharge.toIntOrNull() ?: 100,
                officialPortalUrl = portalUrl.trim(),
                estimatedDays = days.trim(),
                requiredDocuments = initialService?.requiredDocuments ?: listOf("NID / জন্ম নিবন্ধন", "ছবি"),
                eligibility = "বাংলাদেশি নাগরিক",
                applicationSteps = initialService?.applicationSteps ?: listOf("অনলাইন আবেদন", "ডকুমেন্ট যাচাই")
              )
              onSave(srv)
            }
          },
          shape = RoundedCornerShape(10.dp),
          colors = ButtonDefaults.buttonColors(containerColor = RongdhonuPurplePrimary),
          modifier = Modifier.fillMaxWidth().height(46.dp).testTag("btn_save_service_submit")
        ) {
          Text("সংরক্ষণ করুন ও প্রকাশ করুন")
        }
      }
    }
  }
}
