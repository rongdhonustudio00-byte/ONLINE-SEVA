package com.example.data

import com.example.data.model.AdminOrderNotification
import com.example.data.model.AdminRole
import com.example.data.model.AdminSession
import com.example.data.model.AdminUser
import com.example.data.model.AiChatMessage
import com.example.data.model.AppBuildRelease
import com.example.data.model.AppPlatform
import com.example.data.model.AuditLog
import com.example.data.model.BroadcastNotice
import com.example.data.model.CitizenService
import com.example.data.model.CustomerPaymentRecord
import com.example.data.model.CustomerProfile
import com.example.data.model.CustomerRecord
import com.example.data.model.CustomerStatus
import com.example.data.model.DatabaseBackupRecord
import com.example.data.model.PaymentVerificationStatus
import com.example.data.model.OrderStatus
import com.example.data.model.PaymentGatewayConfig
import com.example.data.model.PaymentMethod
import com.example.data.model.SecurityAlert
import com.example.data.model.ServiceOrder
import com.example.data.model.ServiceProvider
import com.example.data.model.SocialMediaLink
import com.example.data.model.SupportTicket
import com.example.data.model.SystemHealthMetrics
import com.example.data.model.TransactionStatus
import com.example.data.model.TransactionType
import com.example.data.model.WalletTransaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class RongdhonuRepository {

  // Core Service Data
  private val _services = MutableStateFlow<List<CitizenService>>(DefaultServices.initialServices)
  val services: StateFlow<List<CitizenService>> = _services.asStateFlow()

  // Draft Services for Admin Preview & Publish Workflow
  private val _draftServices = MutableStateFlow<List<CitizenService>>(DefaultServices.initialServices)
  val draftServices: StateFlow<List<CitizenService>> = _draftServices.asStateFlow()

  private val _walletBalance = MutableStateFlow(650)
  val walletBalance: StateFlow<Int> = _walletBalance.asStateFlow()

  private val _orders = MutableStateFlow<List<ServiceOrder>>(
    listOf(
      ServiceOrder(
        orderId = "RDS-2026-1049",
        serviceId = "birth_reg_new",
        serviceTitleBn = "অনলাইন জন্ম নিবন্ধন আবেদন ও যাচাই",
        citizenName = "মোঃ সাইফুল ইসলাম",
        citizenPhone = "01712-345678",
        identifierNumber = "20241912345678901",
        notes = "সন্তানের বয়স ৩ মাস, দ্রুত প্রিন্ট কপি দরকার।",
        feePaidBdt = 510,
        paymentMethod = PaymentMethod.BKASH,
        trxId = "BK9X4728L1",
        status = OrderStatus.COMPLETED,
        createdAt = System.currentTimeMillis() - 86400000L,
        updatedAt = System.currentTimeMillis() - 43200000L,
        adminRemarks = "BDRIS থেকে সফলভাবে যাচাইকৃত অনলাইন কপি প্রিন্ট দেওয়া হয়েছে।"
      ),
      ServiceOrder(
        orderId = "RDS-2026-1052",
        serviceId = "nid_server_copy",
        serviceTitleBn = "সার্ভার কপি",
        citizenName = "মোছাঃ ফাতেমা আক্তার",
        citizenPhone = "01823-456789",
        identifierNumber = "19942691234567890",
        notes = "মায়ের নাম সংশোধন কপি।",
        feePaidBdt = 45,
        paymentMethod = PaymentMethod.WALLET,
        trxId = "WAL-99410",
        status = OrderStatus.PROCESSING,
        createdAt = System.currentTimeMillis() - 14400000L,
        updatedAt = System.currentTimeMillis() - 7200000L,
        adminRemarks = "নির্বাচন কমিশনের সার্ভারে তথ্য ডাউনলোড ও ভেরিফিকেশন প্রস্তুত।"
      )
    )
  )
  val orders: StateFlow<List<ServiceOrder>> = _orders.asStateFlow()

  private val _transactions = MutableStateFlow<List<WalletTransaction>>(
    listOf(
      WalletTransaction(
        id = "TRX-INIT",
        type = TransactionType.RECHARGE,
        amountBdt = 1050,
        method = PaymentMethod.BKASH,
        trxId = "BK88219901",
        timestamp = System.currentTimeMillis() - 172800000L,
        status = TransactionStatus.SUCCESS,
        note = "স্বাগতম রিচার্জ বোনাস ও প্রাথমিক ব্যালেন্স"
      ),
      WalletTransaction(
        id = "TRX-DEB-1",
        type = TransactionType.SERVICE_FEE,
        amountBdt = 400,
        method = PaymentMethod.WALLET,
        trxId = "WAL-99410",
        timestamp = System.currentTimeMillis() - 14400000L,
        status = TransactionStatus.SUCCESS,
        note = "NID তথ্য সংশোধন সেবার ফি প্রদান"
      )
    )
  )
  val transactions: StateFlow<List<WalletTransaction>> = _transactions.asStateFlow()

  private val _gateways = MutableStateFlow<List<PaymentGatewayConfig>>(DefaultServices.paymentGateways)
  val gateways: StateFlow<List<PaymentGatewayConfig>> = _gateways.asStateFlow()

  // Customer Payment & Transactions Dashboard
  private val _paymentRecords = MutableStateFlow<List<CustomerPaymentRecord>>(DefaultServices.initialPaymentRecords)
  val paymentRecords: StateFlow<List<CustomerPaymentRecord>> = _paymentRecords.asStateFlow()

  // App Builds & Version Distribution
  private val _appReleases = MutableStateFlow<List<AppBuildRelease>>(DefaultServices.initialAppReleases)
  val appReleases: StateFlow<List<AppBuildRelease>> = _appReleases.asStateFlow()

  // Customer Management Records
  private val _customers = MutableStateFlow<List<CustomerRecord>>(DefaultServices.initialCustomers)
  val customers: StateFlow<List<CustomerRecord>> = _customers.asStateFlow()

  // Broadcast Notice Board
  private val _broadcastNotices = MutableStateFlow<List<BroadcastNotice>>(DefaultServices.initialBroadcastNotices)
  val broadcastNotices: StateFlow<List<BroadcastNotice>> = _broadcastNotices.asStateFlow()

  // Security Sessions & Alerts
  private val _adminSessions = MutableStateFlow<List<AdminSession>>(DefaultServices.initialAdminSessions)
  val adminSessions: StateFlow<List<AdminSession>> = _adminSessions.asStateFlow()

  private val _securityAlerts = MutableStateFlow<List<SecurityAlert>>(DefaultServices.initialSecurityAlerts)
  val securityAlerts: StateFlow<List<SecurityAlert>> = _securityAlerts.asStateFlow()

  private val _supportTickets = MutableStateFlow<List<SupportTicket>>(
    listOf(
      SupportTicket(
        id = "TCK-101",
        subject = "পাসপোর্ট অ্যাপয়েন্টমেন্ট তারিখ পরিবর্তন",
        message = "আমার ই-পাসপোর্টের জন্য আগামী রোববারের স্লট দেওয়া যাবে কি?",
        citizenPhone = "01632-042441",
        status = "Resolved",
        createdAt = System.currentTimeMillis() - 120000000L
      )
    )
  )
  val supportTickets: StateFlow<List<SupportTicket>> = _supportTickets.asStateFlow()

  private val _auditLogs = MutableStateFlow<List<AuditLog>>(
    listOf(
      AuditLog(
        id = "LOG-1",
        action = "System Boot",
        target = "রংধনু অনলাইন সেবা নেক্সট-জেন প্ল্যাটফর্ম সচল হয়েছে",
        performedBy = "System",
        timestamp = System.currentTimeMillis() - 3600000L
      )
    )
  )
  val auditLogs: StateFlow<List<AuditLog>> = _auditLogs.asStateFlow()

  // Social Media Management
  private val _socialMediaLinks = MutableStateFlow<List<SocialMediaLink>>(DefaultServices.socialMediaLinks)
  val socialMediaLinks: StateFlow<List<SocialMediaLink>> = _socialMediaLinks.asStateFlow()

  // Admin Order Notifications (Real-time in-app feed)
  private val _orderNotifications = MutableStateFlow<List<AdminOrderNotification>>(
    listOf(
      AdminOrderNotification(
        id = "NOTIF-1",
        orderId = "RDS-2026-1052",
        citizenName = "মোছাঃ ফাতেমা আক্তার",
        citizenPhone = "01823-456789",
        serviceTitle = "সার্ভার কপি",
        amountBdt = 45,
        status = OrderStatus.PROCESSING,
        timestamp = System.currentTimeMillis() - 14400000L,
        isRead = false
      )
    )
  )
  val orderNotifications: StateFlow<List<AdminOrderNotification>> = _orderNotifications.asStateFlow()

  // Verified Service Providers Directory
  private val _serviceProviders = MutableStateFlow<List<ServiceProvider>>(DefaultServices.verifiedProviders)
  val serviceProviders: StateFlow<List<ServiceProvider>> = _serviceProviders.asStateFlow()

  // Customer Profile
  private val _customerProfile = MutableStateFlow(
    CustomerProfile(
      id = "CUST-9921",
      name = "সম্মানিত নাগরিক",
      whatsappPhone = "01632-042441",
      email = "rongdhonustudio00@gmail.com",
      address = "রংপুর, বাংলাদেশ",
      isPhoneVerified = true
    )
  )
  val customerProfile: StateFlow<CustomerProfile> = _customerProfile.asStateFlow()

  // System Health & Telemetry
  private val _systemHealth = MutableStateFlow(SystemHealthMetrics())
  val systemHealth: StateFlow<SystemHealthMetrics> = _systemHealth.asStateFlow()

  // Database Backup History
  private val _backups = MutableStateFlow<List<DatabaseBackupRecord>>(
    listOf(
      DatabaseBackupRecord(
        id = "BKP-INIT-1",
        fileName = "rongdhonu_vault_auto_backup.json",
        backupSizeKb = 148,
        createdAt = System.currentTimeMillis() - 172800000L,
        servicesCount = DefaultServices.initialServices.size,
        ordersCount = 2,
        verifiedBy = "Super Admin"
      )
    )
  )
  val backups: StateFlow<List<DatabaseBackupRecord>> = _backups.asStateFlow()

  // AI Seva Mitra Smart Assistant Messages
  private val _aiChatMessages = MutableStateFlow<List<AiChatMessage>>(
    listOf(
      AiChatMessage(
        id = "msg-0",
        isUser = false,
        textBn = "আসসালামু আলাইকুম! আমি রংধনু এআই সেবা মিত্র। যেকোনো সরকারি সেবা, প্রয়োজনীয় কাগজপত্র, NID, জন্মনিবন্ধন বা অর্ডারের বিষয়ে আমাকে প্রশ্ন করতে পারেন।"
      )
    )
  )
  val aiChatMessages: StateFlow<List<AiChatMessage>> = _aiChatMessages.asStateFlow()

  // Active Admin Session
  private val _activeAdminUser = MutableStateFlow<AdminUser?>(null)
  val activeAdminUser: StateFlow<AdminUser?> = _activeAdminUser.asStateFlow()

  // Order Submission with Instant Admin Notification & Ledger Tracking
  fun submitOrder(
    service: CitizenService,
    citizenName: String,
    citizenPhone: String,
    identifier: String,
    notes: String,
    paymentMethod: PaymentMethod,
    trxId: String
  ): Result<ServiceOrder> {
    val totalFee = service.totalFeeBdt

    if (paymentMethod == PaymentMethod.WALLET) {
      if (_walletBalance.value < totalFee) {
        return Result.failure(Exception("আপনার ওয়ালেটে পর্যাপ্ত ব্যালেন্স নেই (প্রয়োজন ৳$totalFee, বর্তমান ৳${_walletBalance.value})। দয়া করে ব্যালেন্স রিচার্জ করুন।"))
      }
      _walletBalance.update { it - totalFee }
      val debitTrx = WalletTransaction(
        id = "TRX-${System.currentTimeMillis().toString().takeLast(6)}",
        type = TransactionType.SERVICE_FEE,
        amountBdt = totalFee,
        method = PaymentMethod.WALLET,
        trxId = "WAL-${UUID.randomUUID().toString().take(6).uppercase()}",
        status = TransactionStatus.SUCCESS,
        note = "${service.titleBn} সেবার ফি পরিশোধ"
      )
      _transactions.update { listOf(debitTrx) + it }
    } else {
      if (trxId.isBlank()) {
        return Result.failure(Exception("${paymentMethod.displayNameBn} পেমেন্টের ট্রানজেকশন আইডি (TrxID) প্রদান করা আবশ্যক।"))
      }
    }

    val generatedOrderId = "RDS-2026-${(1000..9999).random()}"
    val newOrder = ServiceOrder(
      orderId = generatedOrderId,
      serviceId = service.id,
      serviceTitleBn = service.titleBn,
      citizenName = citizenName.trim(),
      citizenPhone = citizenPhone.trim(),
      identifierNumber = identifier.trim(),
      notes = notes.trim(),
      feePaidBdt = totalFee,
      paymentMethod = paymentMethod,
      trxId = if (paymentMethod == PaymentMethod.WALLET) "WAL-AUTO" else trxId.trim().uppercase(),
      status = OrderStatus.PENDING,
      createdAt = System.currentTimeMillis(),
      updatedAt = System.currentTimeMillis(),
      adminRemarks = "আবেদন সফলভাবে গ্রহণ করা হয়েছে। স্টুডিও থেকে যাচাই চলছে।"
    )

    _orders.update { listOf(newOrder) + it }

    // Instant Customer Payment Record Creation for Finance Dashboard
    val newPaymentRecord = CustomerPaymentRecord(
      paymentId = "PAY-${System.currentTimeMillis().toString().takeLast(6)}",
      customerId = "CUST-${(1000..9999).random()}",
      customerName = citizenName.trim(),
      customerPhone = citizenPhone.trim(),
      whatsappNumber = citizenPhone.trim(),
      orderNumber = generatedOrderId,
      serviceTitleBn = service.titleBn,
      servicePriceBdt = totalFee,
      amountSentBdt = totalFee,
      verifiedAmountBdt = if (paymentMethod == PaymentMethod.WALLET) totalFee else 0,
      paymentMethod = paymentMethod,
      trxId = if (paymentMethod == PaymentMethod.WALLET) "WAL-AUTO" else trxId.trim().uppercase(),
      timestamp = System.currentTimeMillis(),
      status = if (paymentMethod == PaymentMethod.WALLET) PaymentVerificationStatus.FULLY_PAID else PaymentVerificationStatus.SUBMITTED,
      assignedAdmin = "মোঃ রেজাউল করিম",
      notes = notes.trim()
    )
    _paymentRecords.update { listOf(newPaymentRecord) + it }

    // Instant Admin Notification
    val adminNotif = AdminOrderNotification(
      id = "NOTIF-${System.currentTimeMillis()}",
      orderId = generatedOrderId,
      citizenName = citizenName.trim(),
      citizenPhone = citizenPhone.trim(),
      serviceTitle = service.titleBn,
      amountBdt = totalFee,
      status = OrderStatus.PENDING,
      timestamp = System.currentTimeMillis(),
      isRead = false
    )
    _orderNotifications.update { listOf(adminNotif) + it }

    addAuditLog("New Order Submitted", "Order $generatedOrderId - ${service.titleBn} by ${citizenName.trim()}")
    return Result.success(newOrder)
  }

  fun requestWalletRecharge(
    amountBdt: Int,
    method: PaymentMethod,
    trxId: String
  ): Result<WalletTransaction> {
    if (amountBdt < 20) {
      return Result.failure(Exception("সর্বনিম্ন রিচার্জ পরিমাণ ৳২০"))
    }
    if (trxId.isBlank()) {
      return Result.failure(Exception("ট্রানজেকশন আইডি (TrxID) লিখুন"))
    }

    val newTrx = WalletTransaction(
      id = "TRX-${System.currentTimeMillis().toString().takeLast(6)}",
      type = TransactionType.RECHARGE,
      amountBdt = amountBdt,
      method = method,
      trxId = trxId.trim().uppercase(),
      status = TransactionStatus.SUCCESS,
      note = "${method.displayNameBn} মাধ্যমে ওয়ালেট রিচার্জ"
    )

    _walletBalance.update { it + amountBdt }
    _transactions.update { listOf(newTrx) + it }
    addAuditLog("Wallet Recharge", "Amount ৳$amountBdt via ${method.name} TrxID: ${trxId.trim()}")
    return Result.success(newTrx)
  }

  fun createSupportTicket(subject: String, message: String, phone: String) {
    val ticket = SupportTicket(
      id = "TCK-${(100..999).random()}",
      subject = subject.trim(),
      message = message.trim(),
      citizenPhone = phone.trim()
    )
    _supportTickets.update { listOf(ticket) + it }
    addAuditLog("Support Ticket Created", "Ticket ${ticket.id}: $subject")
  }

  // Admin Notification Actions
  fun markNotificationRead(id: String) {
    _orderNotifications.update { list ->
      list.map { if (it.id == id) it.copy(isRead = true) else it }
    }
  }

  fun clearAllNotifications() {
    _orderNotifications.update { list ->
      list.map { it.copy(isRead = true) }
    }
  }

  // Social Media Management
  fun updateSocialLink(id: String, newUrl: String, isEnabled: Boolean) {
    _socialMediaLinks.update { list ->
      list.map {
        if (it.id == id) it.copy(url = newUrl.trim(), isEnabled = isEnabled) else it
      }
    }
    addAuditLog("Social Media Link Updated", "ID: $id, Enabled: $isEnabled")
  }

  fun addSocialLink(link: SocialMediaLink) {
    _socialMediaLinks.update { it + link }
    addAuditLog("Social Media Link Added", link.displayNameBn)
  }

  // Customer Profile & Phone OTP Verification
  fun updateCustomerProfile(name: String, phone: String, email: String, address: String) {
    _customerProfile.update {
      it.copy(
        name = name.trim(),
        whatsappPhone = phone.trim(),
        email = email.trim(),
        address = address.trim()
      )
    }
    addAuditLog("Customer Profile Updated", name.trim())
  }

  fun verifyPhoneOtp(otp: String): Boolean {
    // Verified if 4 or 6 digits
    val success = otp.trim().length in 4..6
    if (success) {
      _customerProfile.update { it.copy(isPhoneVerified = true) }
      addAuditLog("WhatsApp Phone Verified", _customerProfile.value.whatsappPhone)
    }
    return success
  }

  // AI Seva Mitra Smart Assistant Logic
  fun sendAiAssistantQuery(query: String) {
    val q = query.trim()
    if (q.isEmpty()) return

    val userMsg = AiChatMessage(
      id = "msg-${System.currentTimeMillis()}-u",
      isUser = true,
      textBn = q
    )
    _aiChatMessages.update { it + userMsg }

    // Smart contextual Bangladesh Citizen Service logic
    val lower = q.lowercase()
    val replyText: String
    var matchedServiceId: String? = null

    when {
      lower.contains("জন্ম") || lower.contains("birth") -> {
        replyText = "জন্ম নিবন্ধনের জন্য প্রয়োজন: হাসপাতালের ডিসচার্জ সার্টিফিকেট/টিকার কার্ড, পিতা-মাতার ডিজিটাল জন্ম সনদ ও NID নম্বর। সরকারি ফি ৳৫০ এবং রংধনু স্টুডিও চার্জ মিলিয়ে মোট ফি ৳৫১০।"
        matchedServiceId = "birth_reg_new"
      }
      lower.contains("ভাতা") || lower.contains("প্রতিবন্ধী") || lower.contains("সুবর্ণ") -> {
        replyText = "প্রতিবন্ধী ভাতা ও সুবর্ণ কার্ডের জন্য সমাজসেবা অধিদপ্তরের পোর্টালে অনলাইন আবেদন করা যায়। সুবর্ণ কার্ড নম্বর ও জন্ম সনদ দিয়ে আমরা ১২-২৪ ঘণ্টার মধ্যে আবেদন সম্পন্ন করে দিই।"
        matchedServiceId = "disability_allowance"
      }
      lower.contains("সার্ভার") || lower.contains("server") -> {
        replyText = "এনআইডি সার্ভার কপি সংগ্রহের জন্য ১০ বা ১৭ সংখ্যার NID নম্বর এবং জন্মতারিখ লাগবে। মাত্র ১০-১৫ মিনিটের মধ্যে অফিসিয়াল সার্ভার কপি প্রিন্ট পাবেন। ফি মাত্র ৳৪৫।"
        matchedServiceId = "nid_server_copy"
      }
      lower.contains("পাসপোর্ট") || lower.contains("passport") -> {
        replyText = "ই-পাসপোর্ট আবেদনের জন্য স্মার্ট NID বা ১৭ সংখ্যার জন্ম সনদ দরকার। আমাদের মাধ্যমে ফরম পূরণ, এ-চালান ফি জমা এবং অ্যাপয়েন্টমেন্ট তারিখ সহজে নির্ধারণ করতে পারবেন।"
        matchedServiceId = "passport_apply"
      }
      lower.contains("টিন") || lower.contains("tin") || lower.contains("ট্যাক্স") -> {
        replyText = "ই-টিন সার্টিফিকেট মাত্র ১৫ মিনিটে তৈরি করা সম্ভব। এনআইডি নম্বর ও সচল মোবাইল থাকলে তাৎক্ষণিক ১২ সংখ্যার অফিসিয়াল টিন সনদপত্র পাবেন। ফি মাত্র ৳৫৯।"
        matchedServiceId = "tin_certificate"
      }
      lower.contains("কল লিস্ট") || lower.contains("call") -> {
        replyText = "৩ মাস বা ৬ মাসের কল লিস্টের জন্য মোবাইল সিমটি যে জাতীয় পরিচয়পত্র দিয়ে নিবন্ধিত তার তথ্য প্রদান করতে হবে। এটি ২-৪ ঘণ্টার মধ্যে সরবরাহ করা হয়।"
        matchedServiceId = "call_list_3m"
      }
      lower.contains("অর্ডার") || lower.contains("স্ট্যাটাস") || lower.contains("rds") -> {
        val lastOrder = _orders.value.firstOrNull()
        replyText = if (lastOrder != null) {
          "আপনার সর্বশেষ অর্ডার নম্বর ${lastOrder.orderId} (${lastOrder.serviceTitleBn})। বর্তমান অবস্থা: ${lastOrder.status.titleBn}।"
        } else {
          "বর্তমানে আপনার কোনো অর্ডার পাওয়া যায়নি। 'আবেদন সমূহ' পেজে গিয়ে আপনি যেকোনো অর্ডারের বিস্তারিত দেখতে পারেন।"
        }
      }
      else -> {
        replyText = "আপনার জিজ্ঞাসার জন্য ধন্যবাদ। আপনি যেকোনো সরকারি আবেদন, NID কপি, জন্ম নিবন্ধন, পাসপোর্ট বা মোবাইল সংক্রান্ত সেবা হোমপেজ থেকে সরাসরি অর্ডার করতে পারেন। জরুরি সহায়তায় কল করুন 01632-042441 নম্বরে।"
      }
    }

    val botMsg = AiChatMessage(
      id = "msg-${System.currentTimeMillis()}-b",
      isUser = false,
      textBn = replyText,
      suggestedServiceId = matchedServiceId
    )
    _aiChatMessages.update { it + botMsg }
  }

  // Backup & Restore
  fun createDatabaseBackup(): DatabaseBackupRecord {
    val backup = DatabaseBackupRecord(
      id = "BKP-${System.currentTimeMillis().toString().takeLast(6)}",
      fileName = "rongdhonu_db_backup_${SimpleDateFormat("yyyyMMdd_HHmm", Locale.getDefault()).format(Date())}.json",
      backupSizeKb = (120..240).random(),
      createdAt = System.currentTimeMillis(),
      servicesCount = _services.value.size,
      ordersCount = _orders.value.size,
      verifiedBy = _activeAdminUser.value?.fullName ?: "Super Admin"
    )
    _backups.update { listOf(backup) + it }
    addAuditLog("Database Backup Created", backup.fileName)
    return backup
  }

  fun restoreDatabaseFromBackup(backupId: String): Boolean {
    addAuditLog("Database Restored", "Backup ID: $backupId")
    return true
  }

  fun toggleMaintenanceMode(): Boolean {
    val newMode = !_systemHealth.value.maintenanceMode
    _systemHealth.update { it.copy(maintenanceMode = newMode) }
    addAuditLog("Maintenance Mode Changed", if (newMode) "Enabled" else "Disabled")
    return newMode
  }

  // Role-Based Secret Admin Login
  fun adminLoginWithRole(username: String, role: AdminRole, passcode: String): Boolean {
    if (passcode == "123456" || passcode == "admin786" || passcode == "rongdhonu2026") {
      val user = AdminUser(
        id = "ADM-${System.currentTimeMillis().toString().takeLast(4)}",
        username = username.ifBlank { "superadmin" },
        role = role,
        fullName = if (username.isNotBlank()) username else "রংধনু অ্যাডমিন",
        lastLogin = System.currentTimeMillis()
      )
      _activeAdminUser.value = user
      addAuditLog("Admin Login Success", "${user.fullName} (${role.titleBn})")
      return true
    } else {
      addAuditLog("Admin Login Failed", "Attempt with passcode: $passcode")
      return false
    }
  }

  fun adminLogout() {
    val name = _activeAdminUser.value?.fullName ?: "Admin"
    _activeAdminUser.value = null
    addAuditLog("Admin Logout", name)
  }

  // Export Analytics CSV
  fun exportAnalyticsReport(): String {
    val totalRevenue = _orders.value.sumOf { it.feePaidBdt }
    val completedCount = _orders.value.count { it.status == OrderStatus.COMPLETED }
    val processingCount = _orders.value.count { it.status == OrderStatus.PROCESSING }

    val sb = StringBuilder()
    sb.append("Rongdhonu Online Seva - Business Analytics Report\n")
    sb.append("Generated At: ${SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())}\n")
    sb.append("Total Services: ${_services.value.size}\n")
    sb.append("Total Orders: ${_orders.value.size}\n")
    sb.append("Completed Orders: $completedCount\n")
    sb.append("Processing Orders: $processingCount\n")
    sb.append("Total Revenue BDT: ৳$totalRevenue\n\n")
    sb.append("Order ID,Service Title,Citizen Name,Phone,Fee (BDT),Status,Date\n")
    _orders.value.forEach { order ->
      val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(order.createdAt))
      sb.append("${order.orderId},\"${order.serviceTitleBn}\",\"${order.citizenName}\",${order.citizenPhone},${order.feePaidBdt},${order.status.name},$date\n")
    }
    return sb.toString()
  }

  // Core Service Update & State Management
  fun updateOrderStatus(orderId: String, newStatus: OrderStatus, remarks: String) {
    _orders.update { current ->
      current.map { order ->
        if (order.orderId == orderId) {
          order.copy(
            status = newStatus,
            adminRemarks = remarks.ifBlank { order.adminRemarks },
            updatedAt = System.currentTimeMillis()
          )
        } else {
          order
        }
      }
    }
    addAuditLog("Order Status Updated", "Order $orderId changed to ${newStatus.titleBn}")
  }

  fun saveService(service: CitizenService) {
    _services.update { list ->
      val existing = list.indexOfFirst { it.id == service.id }
      if (existing != -1) {
        list.toMutableList().apply { set(existing, service) }
      } else {
        listOf(service) + list
      }
    }
    _draftServices.update { _services.value }
    addAuditLog("Service Saved", "${service.serviceCode} - ${service.titleBn} (৳${service.totalFeeBdt})")
  }

  fun toggleServiceActive(serviceId: String) {
    _services.update { list ->
      list.map {
        if (it.id == serviceId) it.copy(isActive = !it.isActive) else it
      }
    }
    _draftServices.update { _services.value }
    addAuditLog("Service Toggled", "Service ID: $serviceId")
  }

  fun deleteService(serviceId: String) {
    _services.update { list -> list.filterNot { it.id == serviceId } }
    _draftServices.update { _services.value }
    addAuditLog("Service Deleted", "Service ID: $serviceId")
  }

  fun updateGateway(method: PaymentMethod, isEnabled: Boolean, account: String, instructions: String) {
    _gateways.update { list ->
      list.map {
        if (it.method == method) {
          it.copy(isEnabled = isEnabled, accountNumber = account, instructionsBn = instructions)
        } else it
      }
    }
    addAuditLog("Gateway Updated", "Method: ${method.name}, Active: $isEnabled")
  }

  // --- Customer Payment & Transactions Management ---
  fun verifyCustomerPayment(paymentId: String, verifiedAmount: Int, adminName: String, notes: String) {
    _paymentRecords.update { list ->
      list.map { payment ->
        if (payment.paymentId == paymentId) {
          val newStatus = if (verifiedAmount >= payment.servicePriceBdt) {
            PaymentVerificationStatus.FULLY_PAID
          } else if (verifiedAmount > 0) {
            PaymentVerificationStatus.PARTIALLY_PAID
          } else {
            PaymentVerificationStatus.VERIFIED
          }
          payment.copy(
            verifiedAmountBdt = verifiedAmount,
            status = newStatus,
            assignedAdmin = adminName.ifBlank { payment.assignedAdmin },
            notes = if (notes.isNotBlank()) notes else payment.notes
          )
        } else payment
      }
    }
    // Also update order status if linked
    val record = _paymentRecords.value.find { it.paymentId == paymentId }
    if (record != null) {
      _orders.update { orders ->
        orders.map { order ->
          if (order.orderId == record.orderNumber) {
            order.copy(
              status = if (record.status == PaymentVerificationStatus.FULLY_PAID) OrderStatus.PROCESSING else order.status,
              adminRemarks = "পেমেন্ট যাচাই সম্পন্ন: ৳$verifiedAmount ($adminName)"
            )
          } else order
        }
      }
    }
    addAuditLog("Payment Verified", "$paymentId - ৳$verifiedAmount verified by $adminName")
  }

  fun rejectCustomerPayment(paymentId: String, reason: String, adminName: String) {
    _paymentRecords.update { list ->
      list.map {
        if (it.paymentId == paymentId) {
          it.copy(
            status = PaymentVerificationStatus.REJECTED,
            assignedAdmin = adminName.ifBlank { it.assignedAdmin },
            notes = "বাতিলের কারণ: $reason"
          )
        } else it
      }
    }
    addAuditLog("Payment Rejected", "$paymentId - Reason: $reason by $adminName")
  }

  fun requestMorePaymentInfo(paymentId: String, remarks: String, adminName: String) {
    _paymentRecords.update { list ->
      list.map {
        if (it.paymentId == paymentId) {
          it.copy(
            status = PaymentVerificationStatus.PENDING,
            assignedAdmin = adminName.ifBlank { it.assignedAdmin },
            notes = "অতিরিক্ত তথ্য চাওয়া হয়েছে: $remarks"
          )
        } else it
      }
    }
    addAuditLog("Payment Info Requested", "$paymentId - $remarks by $adminName")
  }

  fun processCustomerRefund(paymentId: String, refundAmount: Int, refundTrxId: String, refundReason: String, adminName: String) {
    _paymentRecords.update { list ->
      list.map {
        if (it.paymentId == paymentId) {
          it.copy(
            status = PaymentVerificationStatus.REFUNDED,
            refundAmountBdt = refundAmount,
            refundTrxId = refundTrxId,
            refundReason = refundReason,
            isRefundProcessed = true,
            assignedAdmin = adminName.ifBlank { it.assignedAdmin },
            notes = "রিফান্ড সম্পন্ন (Trx: $refundTrxId, পরিমাণ: ৳$refundAmount)"
          )
        } else it
      }
    }
    addAuditLog("Payment Refunded", "$paymentId - Refund ৳$refundAmount (Trx: $refundTrxId) by $adminName")
  }

  // --- App Builds & Distribution Management ---
  fun updateAppRelease(id: String, version: String, downloadUrl: String, checksum: String, notes: String) {
    _appReleases.update { list ->
      list.map {
        if (it.id == id) {
          it.copy(
            version = version.ifBlank { it.version },
            downloadUrl = downloadUrl.ifBlank { it.downloadUrl },
            sha256Checksum = checksum.ifBlank { it.sha256Checksum },
            releaseNotesBn = notes.ifBlank { it.releaseNotesBn }
          )
        } else it
      }
    }
    addAuditLog("App Release Updated", "Release $id version $version")
  }

  fun toggleDownloadActive(id: String) {
    _appReleases.update { list ->
      list.map {
        if (it.id == id) it.copy(isDownloadActive = !it.isDownloadActive) else it
      }
    }
    addAuditLog("App Download Toggled", "Release $id")
  }

  // Customer Management Operations
  fun updateCustomerStatus(customerId: String, newStatus: CustomerStatus) {
    _customers.update { list ->
      list.map {
        if (it.id == customerId) it.copy(status = newStatus) else it
      }
    }
    addAuditLog("Customer Status Changed", "Customer $customerId -> ${newStatus.titleBn}")
  }

  fun adjustCustomerWallet(customerId: String, amount: Int, isAdd: Boolean, reason: String) {
    _customers.update { list ->
      list.map { customer ->
        if (customer.id == customerId) {
          val newBalance = if (isAdd) customer.walletBalanceBdt + amount else (customer.walletBalanceBdt - amount).coerceAtLeast(0)
          customer.copy(walletBalanceBdt = newBalance)
        } else customer
      }
    }
    val action = if (isAdd) "Wallet Money Added (+৳$amount)" else "Wallet Money Deducted (-৳$amount)"
    addAuditLog(action, "Customer $customerId: $reason")
  }

  fun setCustomerDiscount(customerId: String, percent: Int) {
    _customers.update { list ->
      list.map {
        if (it.id == customerId) it.copy(specialDiscountPercent = percent) else it
      }
    }
    addAuditLog("Customer Discount Set", "Customer $customerId -> $percent%")
  }

  fun updateCustomerNotes(customerId: String, notes: String) {
    _customers.update { list ->
      list.map {
        if (it.id == customerId) it.copy(adminNotes = notes) else it
      }
    }
    addAuditLog("Customer Notes Updated", "Customer $customerId")
  }

  // Offline Counter Cash Payment Recording
  fun recordOfflineCashPayment(
    customerName: String,
    customerPhone: String,
    serviceTitleBn: String,
    servicePriceBdt: Int,
    amountPaidBdt: Int,
    notes: String
  ): CustomerPaymentRecord {
    val paymentId = "PAY-${System.currentTimeMillis().toString().takeLast(6)}"
    val orderNumber = "ORD-CSH-${(1000..9999).random()}"
    val trxId = "CASH-${UUID.randomUUID().toString().take(6).uppercase()}"

    val record = CustomerPaymentRecord(
      paymentId = paymentId,
      customerId = "CUST-${(1000..9999).random()}",
      customerName = customerName.ifBlank { "কাউন্টার সেবাগ্রহীতা" },
      customerPhone = customerPhone.ifBlank { "01632-042441" },
      whatsappNumber = customerPhone.ifBlank { "01632-042441" },
      orderNumber = orderNumber,
      serviceTitleBn = serviceTitleBn,
      servicePriceBdt = servicePriceBdt,
      amountSentBdt = amountPaidBdt,
      verifiedAmountBdt = amountPaidBdt,
      paymentMethod = PaymentMethod.WALLET, // or cash counter
      trxId = trxId,
      timestamp = System.currentTimeMillis(),
      status = if (amountPaidBdt >= servicePriceBdt) PaymentVerificationStatus.FULLY_PAID else PaymentVerificationStatus.PARTIALLY_PAID,
      assignedAdmin = _activeAdminUser.value?.fullName ?: "রংধনু কাউন্টার ডেস্ক",
      notes = "কাউন্টারে সরাসরি নগদ ক্যাশ গ্রহণ। $notes"
    )

    _paymentRecords.update { listOf(record) + it }
    addAuditLog("Counter Cash Payment Recorded", "$paymentId: ৳$amountPaidBdt ($serviceTitleBn)")
    return record
  }

  // Broadcast Notice Operations
  fun addBroadcastNotice(titleBn: String, messageBn: String, isUrgent: Boolean) {
    val newNotice = BroadcastNotice(
      id = "NOTC-${(100..999).random()}",
      titleBn = titleBn.trim(),
      messageBn = messageBn.trim(),
      date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
      isUrgent = isUrgent,
      isActive = true
    )
    _broadcastNotices.update { listOf(newNotice) + it }
    addAuditLog("Broadcast Notice Published", titleBn)
  }

  fun toggleBroadcastNotice(id: String) {
    _broadcastNotices.update { list ->
      list.map {
        if (it.id == id) it.copy(isActive = !it.isActive) else it
      }
    }
    addAuditLog("Broadcast Notice Toggled", "Notice $id")
  }

  fun deleteBroadcastNotice(id: String) {
    _broadcastNotices.update { list -> list.filterNot { it.id == id } }
    addAuditLog("Broadcast Notice Deleted", "Notice $id")
  }

  // Session & Security Management
  fun terminateSession(sessionId: String) {
    _adminSessions.update { list -> list.filterNot { it.id == sessionId } }
    addAuditLog("Admin Session Terminated", "Session $sessionId revoked")
  }

  fun dismissSecurityAlert(alertId: String) {
    _securityAlerts.update { list -> list.filterNot { it.id == alertId } }
  }


  private fun addAuditLog(action: String, target: String) {
    val log = AuditLog(
      id = "LOG-${System.currentTimeMillis().toString().takeLast(5)}",
      action = action,
      target = target,
      performedBy = _activeAdminUser.value?.fullName ?: "রংধনু অ্যাডমিন",
      timestamp = System.currentTimeMillis()
    )
    _auditLogs.update { listOf(log) + it.take(50) }
  }
}
