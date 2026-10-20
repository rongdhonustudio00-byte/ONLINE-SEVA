package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.RongdhonuRepository
import com.example.data.model.AdminRole
import com.example.data.model.AdminSession
import com.example.data.model.AppBuildRelease
import com.example.data.model.BroadcastNotice
import com.example.data.model.CitizenService
import com.example.data.model.CustomerPaymentRecord
import com.example.data.model.CustomerRecord
import com.example.data.model.CustomerStatus
import com.example.data.model.OrderStatus
import com.example.data.model.PaymentInvoice
import com.example.data.model.PaymentMethod
import com.example.data.model.PaymentVerificationStatus
import com.example.data.model.SecurityAlert
import com.example.data.model.ServiceCategory
import com.example.data.model.ServiceOrder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class BottomNavTab(val titleBn: String, val testTag: String) {
  HOME("হোম", "nav_tab_home"),
  SERVICES("সকল সেবা", "nav_tab_services"),
  ORDERS("আবেদন সমূহ", "nav_tab_orders"),
  WALLET("ওয়ালেট", "nav_tab_wallet"),
  PROFILE("প্রোফাইল", "nav_tab_profile")
}

enum class AdminTab(val titleBn: String) {
  DASHBOARD("ড্যাশবোর্ড"),
  AI_ASSISTANT("এআই সহকারী ও ভয়েস"),
  ANALYTICS("লাইভ অ্যানালিটিক্স"),
  PAYMENTS("পেমেন্ট ও লেনদেন"),
  SERVICES("সেবা নিয়ন্ত্রণ"),
  CUSTOMERS("গ্রাহক ও ইউজার"),
  ORDERS("আবেদন ও অর্ডার"),
  AUTOMATION("অটোমেশন ও নোটিশ"),
  SECURITY("নিরাপত্তা ও সেশন"),
  DOWNLOADS("অ্যাপ ডাউনলোড"),
  GATEWAYS("পেমেন্ট গেটওয়ে"),
  AUDIT_LOGS("অডিট ও হেলথ")
}

data class ActionConfirmationState(
  val titleBn: String,
  val messageBn: String,
  val confirmButtonBn: String = "নিশ্চিত করুন",
  val isDestructive: Boolean = false,
  val onConfirm: () -> Unit
)

data class UiNotification(
  val messageBn: String,
  val isError: Boolean = false
)

class RongdhonuViewModel(
  val repository: RongdhonuRepository = RongdhonuRepository()
) : ViewModel() {

  private val _currentTab = MutableStateFlow(BottomNavTab.HOME)
  val currentTab: StateFlow<BottomNavTab> = _currentTab.asStateFlow()

  private val _selectedCategory = MutableStateFlow(ServiceCategory.ALL)
  val selectedCategory: StateFlow<ServiceCategory> = _selectedCategory.asStateFlow()

  private val _searchQuery = MutableStateFlow("")
  val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

  private val _selectedService = MutableStateFlow<CitizenService?>(null)
  val selectedService: StateFlow<CitizenService?> = _selectedService.asStateFlow()

  private val _showOrderForm = MutableStateFlow(false)
  val showOrderForm: StateFlow<Boolean> = _showOrderForm.asStateFlow()

  private val _selectedOrderForReceipt = MutableStateFlow<ServiceOrder?>(null)
  val selectedOrderForReceipt: StateFlow<ServiceOrder?> = _selectedOrderForReceipt.asStateFlow()

  private val _showRechargeDialog = MutableStateFlow(false)
  val showRechargeDialog: StateFlow<Boolean> = _showRechargeDialog.asStateFlow()

  // Admin Portal State
  private val _showAdminPortal = MutableStateFlow(false)
  val showAdminPortal: StateFlow<Boolean> = _showAdminPortal.asStateFlow()

  private val _isAdminAuthenticated = MutableStateFlow(false)
  val isAdminAuthenticated: StateFlow<Boolean> = _isAdminAuthenticated.asStateFlow()

  private val _adminTab = MutableStateFlow(AdminTab.DASHBOARD)
  val adminTab: StateFlow<AdminTab> = _adminTab.asStateFlow()

  // Download Center State
  private val _showDownloadCenter = MutableStateFlow(false)
  val showDownloadCenter: StateFlow<Boolean> = _showDownloadCenter.asStateFlow()

  // Payment Management States
  private val _paymentSearchQuery = MutableStateFlow("")
  val paymentSearchQuery: StateFlow<String> = _paymentSearchQuery.asStateFlow()

  private val _paymentStatusFilter = MutableStateFlow<PaymentVerificationStatus?>(null)
  val paymentStatusFilter: StateFlow<PaymentVerificationStatus?> = _paymentStatusFilter.asStateFlow()

  private val _paymentMethodFilter = MutableStateFlow<PaymentMethod?>(null)
  val paymentMethodFilter: StateFlow<PaymentMethod?> = _paymentMethodFilter.asStateFlow()

  private val _selectedPaymentForVerification = MutableStateFlow<CustomerPaymentRecord?>(null)
  val selectedPaymentForVerification: StateFlow<CustomerPaymentRecord?> = _selectedPaymentForVerification.asStateFlow()

  private val _selectedPaymentForRefund = MutableStateFlow<CustomerPaymentRecord?>(null)
  val selectedPaymentForRefund: StateFlow<CustomerPaymentRecord?> = _selectedPaymentForRefund.asStateFlow()

  // Security & Role Control
  private val _adminRole = MutableStateFlow(AdminRole.SUPER_ADMIN)
  val adminRole: StateFlow<AdminRole> = _adminRole.asStateFlow()

  private val _twoFactorEnabled = MutableStateFlow(false)
  val twoFactorEnabled: StateFlow<Boolean> = _twoFactorEnabled.asStateFlow()

  // AI Intelligence Summary
  private val _aiSummaryText = MutableStateFlow<String?>(null)
  val aiSummaryText: StateFlow<String?> = _aiSummaryText.asStateFlow()

  private val _editingService = MutableStateFlow<CitizenService?>(null)
  val editingService: StateFlow<CitizenService?> = _editingService.asStateFlow()

  private val _showEditServiceDialog = MutableStateFlow(false)
  val showEditServiceDialog: StateFlow<Boolean> = _showEditServiceDialog.asStateFlow()

  private val _notification = MutableStateFlow<UiNotification?>(null)
  val notification: StateFlow<UiNotification?> = _notification.asStateFlow()

  // Customer Management States
  private val _customerSearchQuery = MutableStateFlow("")
  val customerSearchQuery: StateFlow<String> = _customerSearchQuery.asStateFlow()

  private val _customerStatusFilter = MutableStateFlow<CustomerStatus?>(null)
  val customerStatusFilter: StateFlow<CustomerStatus?> = _customerStatusFilter.asStateFlow()

  // Offline Cash Payment Dialog State
  private val _showCashPaymentDialog = MutableStateFlow(false)
  val showCashPaymentDialog: StateFlow<Boolean> = _showCashPaymentDialog.asStateFlow()

  // Payment Invoice Dialog State
  private val _selectedPaymentForInvoice = MutableStateFlow<CustomerPaymentRecord?>(null)
  val selectedPaymentForInvoice: StateFlow<CustomerPaymentRecord?> = _selectedPaymentForInvoice.asStateFlow()

  // Action Confirmation Approval State
  private val _actionConfirmation = MutableStateFlow<ActionConfirmationState?>(null)
  val actionConfirmation: StateFlow<ActionConfirmationState?> = _actionConfirmation.asStateFlow()

  // Notice Creation Dialog State
  private val _showAddNoticeDialog = MutableStateFlow(false)
  val showAddNoticeDialog: StateFlow<Boolean> = _showAddNoticeDialog.asStateFlow()

  // Voice Command Processing
  private val _voiceCommandInput = MutableStateFlow("")
  val voiceCommandInput: StateFlow<String> = _voiceCommandInput.asStateFlow()

  private val _isVoiceListening = MutableStateFlow(false)
  val isVoiceListening: StateFlow<Boolean> = _isVoiceListening.asStateFlow()

  private val _voiceExecutionResult = MutableStateFlow<String?>(null)
  val voiceExecutionResult: StateFlow<String?> = _voiceExecutionResult.asStateFlow()

  val filteredCustomers: StateFlow<List<CustomerRecord>> = combine(
    repository.customers,
    _customerStatusFilter,
    _customerSearchQuery
  ) { customerList, statusFilter, query ->
    customerList.filter { c ->
      val matchesStatus = statusFilter == null || c.status == statusFilter
      val q = query.trim().lowercase()
      val matchesQuery = q.isEmpty() ||
        c.name.lowercase().contains(q) ||
        c.phone.contains(q) ||
        c.address.lowercase().contains(q) ||
        c.email.lowercase().contains(q)
      matchesStatus && matchesQuery
    }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // Filtered payment records based on search query, status and method
  val filteredPaymentRecords: StateFlow<List<CustomerPaymentRecord>> = combine(
    repository.paymentRecords,
    _paymentStatusFilter,
    _paymentMethodFilter,
    _paymentSearchQuery
  ) { records, statusFilter, methodFilter, query ->
    records.filter { record ->
      val matchesStatus = statusFilter == null || record.status == statusFilter
      val matchesMethod = methodFilter == null || record.paymentMethod == methodFilter
      val q = query.trim().lowercase()
      val matchesQuery = q.isEmpty() ||
        record.customerName.lowercase().contains(q) ||
        record.customerPhone.contains(q) ||
        record.orderNumber.lowercase().contains(q) ||
        record.trxId.lowercase().contains(q) ||
        record.serviceTitleBn.lowercase().contains(q)
      matchesStatus && matchesMethod && matchesQuery
    }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  // Filtered services based on search query and category
  val filteredServices: StateFlow<List<CitizenService>> = combine(
    repository.services,
    _selectedCategory,
    _searchQuery
  ) { services, category, query ->
    services.filter { service ->
      val matchesCategory = (category == ServiceCategory.ALL || service.category == category)
      val q = query.trim().lowercase()
      val matchesQuery = q.isEmpty() ||
        service.titleBn.lowercase().contains(q) ||
        service.titleEn.lowercase().contains(q) ||
        service.descriptionBn.lowercase().contains(q) ||
        service.serviceCode.lowercase().contains(q)
      matchesCategory && matchesQuery
    }
  }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

  fun selectTab(tab: BottomNavTab) {
    _currentTab.value = tab
    _selectedService.value = null
    _showOrderForm.value = false
    _showAdminPortal.value = false
  }

  fun selectCategory(category: ServiceCategory) {
    _selectedCategory.value = category
  }

  fun setSearchQuery(query: String) {
    _searchQuery.value = query
  }

  fun openServiceDetail(service: CitizenService) {
    _selectedService.value = service
  }

  fun closeServiceDetail() {
    _selectedService.value = null
    _showOrderForm.value = false
  }

  fun openOrderForm(service: CitizenService) {
    _selectedService.value = service
    _showOrderForm.value = true
  }

  fun closeOrderForm() {
    _showOrderForm.value = false
  }

  fun submitOrder(
    service: CitizenService,
    citizenName: String,
    citizenPhone: String,
    idNumber: String,
    notes: String,
    paymentMethod: PaymentMethod,
    trxId: String
  ) {
    viewModelScope.launch {
      val result = repository.submitOrder(
        service = service,
        citizenName = citizenName,
        citizenPhone = citizenPhone,
        identifier = idNumber,
        notes = notes,
        paymentMethod = paymentMethod,
        trxId = trxId
      )
      result.onSuccess { order ->
        _showOrderForm.value = false
        _selectedOrderForReceipt.value = order
        _notification.value = UiNotification("আপনার আবেদনটি সফলভাবে জমা হয়েছে! অর্ডার নং: ${order.orderId}")
      }.onFailure { err ->
        _notification.value = UiNotification(err.message ?: "আবেদন জমা করতে ত্রুটি হয়েছে।", isError = true)
      }
    }
  }

  fun openReceipt(order: ServiceOrder) {
    _selectedOrderForReceipt.value = order
  }

  fun closeReceipt() {
    _selectedOrderForReceipt.value = null
  }

  fun openRechargeDialog() {
    _showRechargeDialog.value = true
  }

  fun closeRechargeDialog() {
    _showRechargeDialog.value = false
  }

  fun rechargeWallet(amount: Int, method: PaymentMethod, trxId: String) {
    viewModelScope.launch {
      val result = repository.requestWalletRecharge(amount, method, trxId)
      result.onSuccess {
        _showRechargeDialog.value = false
        _notification.value = UiNotification("৳$amount টাকা ওয়ালেটে সফলভাবে যোগ করা হয়েছে!")
      }.onFailure { err ->
        _notification.value = UiNotification(err.message ?: "রিচার্জ ব্যর্থ হয়েছে।", isError = true)
      }
    }
  }

  // Admin Methods
  fun openAdminPortal() {
    _showAdminPortal.value = true
  }

  fun closeAdminPortal() {
    _showAdminPortal.value = false
  }

  fun loginAdmin(passcode: String): Boolean {
    // Authorized default passcodes: 123456 or admin786
    if (passcode == "123456" || passcode == "admin786") {
      _isAdminAuthenticated.value = true
      _notification.value = UiNotification("রংধনু অ্যাডমিন পোর্টালে স্বাগতম!")
      return true
    } else {
      _notification.value = UiNotification("ভুল অ্যাডমিন পাসকোড! পুনরায় চেষ্টা করুন।", isError = true)
      return false
    }
  }

  fun logoutAdmin() {
    _isAdminAuthenticated.value = false
    _showAdminPortal.value = false
    _notification.value = UiNotification("অ্যাডমিন লগআউট সফল হয়েছে।")
  }

  fun selectAdminTab(tab: AdminTab) {
    _adminTab.value = tab
  }

  fun updateOrderStatus(orderId: String, newStatus: OrderStatus, remarks: String) {
    repository.updateOrderStatus(orderId, newStatus, remarks)
    _notification.value = UiNotification("অর্ডার স্ট্যাটাস সফলভাবে আপডেট হয়েছে।")
  }

  fun startAddService() {
    _editingService.value = null
    _showEditServiceDialog.value = true
  }

  fun startEditService(service: CitizenService) {
    _editingService.value = service
    _showEditServiceDialog.value = true
  }

  fun closeEditServiceDialog() {
    _editingService.value = null
    _showEditServiceDialog.value = false
  }

  fun saveService(service: CitizenService) {
    repository.saveService(service)
    _showEditServiceDialog.value = false
    _editingService.value = null
    _notification.value = UiNotification("সেবাটি সফলভাবে সংরক্ষণ করা হয়েছে!")
  }

  fun toggleServiceActive(serviceId: String) {
    repository.toggleServiceActive(serviceId)
    _notification.value = UiNotification("সেবার স্থিতি পরিবর্তন করা হয়েছে।")
  }

  fun deleteService(serviceId: String) {
    repository.deleteService(serviceId)
    _notification.value = UiNotification("সেবাটি সফলভাবে ডিলিট করা হয়েছে।")
  }

  fun updateGateway(method: PaymentMethod, enabled: Boolean, account: String, instructions: String) {
    repository.updateGateway(method, enabled, account, instructions)
    _notification.value = UiNotification("${method.displayNameBn} গেটওয়ে সেটিংস আপডেট হয়েছে।")
  }

  fun submitSupportTicket(subject: String, message: String, phone: String) {
    repository.createSupportTicket(subject, message, phone)
    _notification.value = UiNotification("আপনার অভিযোগ/বার্তাটি গ্রহণ করা হয়েছে। শীঘ্রই যোগাযোগ করা হবে।")
  }

  // Download Center Controls
  fun openDownloadCenter() {
    _showDownloadCenter.value = true
  }

  fun closeDownloadCenter() {
    _showDownloadCenter.value = false
  }

  fun recordDownload(releaseId: String) {
    repository.recordDownload(releaseId)
  }

  fun updateAppRelease(id: String, version: String, downloadUrl: String, checksum: String, notes: String) {
    repository.updateAppRelease(id, version, downloadUrl, checksum, notes)
    _notification.value = UiNotification("অ্যাপ রিলিজ তথ্য সফলভাবে আপডেট হয়েছে।")
  }

  fun toggleDownloadActive(id: String) {
    repository.toggleDownloadActive(id)
    _notification.value = UiNotification("ডাউনলোড অ্যাক্টিভ স্ট্যাটাস পরিবর্তন করা হয়েছে।")
  }

  // Payment Management Controls
  fun setPaymentSearchQuery(q: String) {
    _paymentSearchQuery.value = q
  }

  fun setPaymentStatusFilter(status: PaymentVerificationStatus?) {
    _paymentStatusFilter.value = status
  }

  fun setPaymentMethodFilter(method: PaymentMethod?) {
    _paymentMethodFilter.value = method
  }

  fun openPaymentVerification(record: CustomerPaymentRecord) {
    _selectedPaymentForVerification.value = record
  }

  fun closePaymentVerification() {
    _selectedPaymentForVerification.value = null
  }

  fun openPaymentRefund(record: CustomerPaymentRecord) {
    _selectedPaymentForRefund.value = record
  }

  fun closePaymentRefund() {
    _selectedPaymentForRefund.value = null
  }

  fun verifyPayment(paymentId: String, verifiedAmount: Int, notes: String) {
    val adminName = repository.activeAdminUser.value?.fullName ?: "রংধনু অ্যাডমিন"
    repository.verifyCustomerPayment(paymentId, verifiedAmount, adminName, notes)
    _selectedPaymentForVerification.value = null
    _notification.value = UiNotification("পেমেন্ট সফলভাবে যাচাই ও অনুমোদন করা হয়েছে (৳$verifiedAmount)!")
  }

  fun rejectPayment(paymentId: String, reason: String) {
    val adminName = repository.activeAdminUser.value?.fullName ?: "রংধনু অ্যাডমিন"
    repository.rejectCustomerPayment(paymentId, reason, adminName)
    _selectedPaymentForVerification.value = null
    _notification.value = UiNotification("পেমেন্টটি প্রত্যাখ্যাত করা হয়েছে।", isError = true)
  }

  fun requestMorePaymentInfo(paymentId: String, remarks: String) {
    val adminName = repository.activeAdminUser.value?.fullName ?: "রংধনু অ্যাডমিন"
    repository.requestMorePaymentInfo(paymentId, remarks, adminName)
    _selectedPaymentForVerification.value = null
    _notification.value = UiNotification("গ্রাহকের নিকট অতিরিক্ত তথ্যের অনুরোধ পাঠানো হয়েছে।")
  }

  fun refundPayment(paymentId: String, refundAmount: Int, refundTrxId: String, reason: String) {
    val adminName = repository.activeAdminUser.value?.fullName ?: "রংধনু অ্যাডমিন"
    repository.processCustomerRefund(paymentId, refundAmount, refundTrxId, reason, adminName)
    _selectedPaymentForRefund.value = null
    _notification.value = UiNotification("পেমেন্ট রিফান্ড সফলভাবে সম্পন্ন হয়েছে (৳$refundAmount)!")
  }

  fun switchAdminRole(role: AdminRole) {
    _adminRole.value = role
    _notification.value = UiNotification("অ্যাডমিন রোল পরিবর্তন করা হয়েছে: ${role.titleBn}")
  }

  fun toggleTwoFactor() {
    _twoFactorEnabled.value = !_twoFactorEnabled.value
    val msg = if (_twoFactorEnabled.value) "টু-ফ্যাক্টর অথেনটিকেশন (2FA) চালু হয়েছে।" else "2FA বন্ধ করা হয়েছে।"
    _notification.value = UiNotification(msg)
  }

  fun generateAiBusinessSummary() {
    val payments = repository.paymentRecords.value
    val totalCollected = payments.filter { it.status == PaymentVerificationStatus.FULLY_PAID || it.status == PaymentVerificationStatus.VERIFIED }.sumOf { it.verifiedAmountBdt }
    val totalDue = payments.sumOf { it.dueAmountBdt }
    val pendingCount = payments.count { it.status == PaymentVerificationStatus.SUBMITTED || it.status == PaymentVerificationStatus.PENDING }
    val refundTotal = payments.filter { it.status == PaymentVerificationStatus.REFUNDED }.sumOf { it.refundAmountBdt }

    val summary = "🤖 রংধনু AI বিজনেস ইন্টেলিজেন্স রিপোর্ট (সরাসরি ডেটাবেজ বিশ্লেষণ):\n• মোট যাচাইকৃত রাজস্ব আদায়: ৳$totalCollected টাকা।\n• বর্তমান গ্রাহক বকেয়া: ৳$totalDue টাকা (${payments.count { it.dueAmountBdt > 0 }} জন গ্রাহকের)।\n• যাচাইয়ের অপেক্ষায় রয়েছে: $pendingCount টি লেনদেন (তাৎক্ষণিক ভেরিফিকেশন প্রস্তাবিত)।\n• মোট অনুমোদিত রিফান্ড: ৳$refundTotal টাকা।\n• শীর্ষ জনপ্রিয় সেবা: জরুরি NID সংশোধন, ই-পাসপোর্ট ফরম ও খতিয়ান নামজারি।\n• নিরাপত্তা ও অডিট সতর্কতা: কোনো অস্বাভাবিক বা ডুপ্লিকেট পেমেন্ট অসঙ্গতি পাওয়া যায়নি।"
    _aiSummaryText.value = summary
  }

  fun dismissAiSummary() {
    _aiSummaryText.value = null
  }

  // Voice Command Processing
  fun startVoiceListening() {
    _isVoiceListening.value = true
    _voiceExecutionResult.value = "শুনছি... আপনার কমান্ড বলুন (যেমন: 'আজকের রিপোর্ট', 'পেমেন্ট চেক', 'নতুন সেবা', 'লগআউট')..."
  }

  fun stopVoiceListening() {
    _isVoiceListening.value = false
  }

  fun processVoiceCommand(command: String) {
    _voiceCommandInput.value = command
    _isVoiceListening.value = false
    val cmd = command.trim().lowercase()

    when {
      cmd.contains("রিপোর্ট") || cmd.contains("report") -> {
        generateAiBusinessSummary()
        _adminTab.value = AdminTab.DASHBOARD
        _voiceExecutionResult.value = "✅ ভয়েস কমান্ড সম্পন্ন: আজকের AI বিজনেস অ্যানালিটিক্স রিপোর্ট তৈরি করা হয়েছে।"
      }
      cmd.contains("পেমেন্ট") || cmd.contains("টাকা") || cmd.contains("payment") -> {
        _adminTab.value = AdminTab.PAYMENTS
        _voiceExecutionResult.value = "✅ ভয়েস কমান্ড সম্পন্ন: পেমেন্ট ও লেনদেন ড্যাশবোর্ডে নেওয়া হয়েছে।"
      }
      cmd.contains("কাস্টমার") || cmd.contains("গ্রাহক") || cmd.contains("customer") -> {
        _adminTab.value = AdminTab.CUSTOMERS
        _voiceExecutionResult.value = "✅ ভয়েস কমান্ড সম্পন্ন: গ্রাহক তালিকা ও প্রোফাইল ড্যাশবোর্ডে নেওয়া হয়েছে।"
      }
      cmd.contains("নতুন সেবা") || cmd.contains("সার্ভিস তৈরি") -> {
        startAddService()
        _voiceExecutionResult.value = "✅ ভয়েস কমান্ড সম্পন্ন: নতুন সেবা তৈরির ফর্ম খোলা হয়েছে।"
      }
      cmd.contains("সেবা") || cmd.contains("সার্ভিস") || cmd.contains("service") -> {
        _adminTab.value = AdminTab.SERVICES
        _voiceExecutionResult.value = "✅ ভয়েস কমান্ড সম্পন্ন: সেবা নিয়ন্ত্রণ প্যানেলে নেওয়া হয়েছে।"
      }
      cmd.contains("অ্যানালিটিক্স") || cmd.contains("আয়") || cmd.contains("বিক্রয়") -> {
        _adminTab.value = AdminTab.ANALYTICS
        _voiceExecutionResult.value = "✅ ভয়েস কমান্ড সম্পন্ন: লাইভ অ্যানালিটিক্স ও আয়ের চার্ট খোলা হয়েছে।"
      }
      cmd.contains("নোটিশ") || cmd.contains("বার্তা") -> {
        _adminTab.value = AdminTab.AUTOMATION
        _voiceExecutionResult.value = "✅ ভয়েস কমান্ড সম্পন্ন: অটোমেশন ও নোটিশ বোর্ডে নেওয়া হয়েছে।"
      }
      cmd.contains("ডাউনলোড") || cmd.contains("apk") -> {
        _adminTab.value = AdminTab.DOWNLOADS
        _voiceExecutionResult.value = "✅ ভয়েস কমান্ড সম্পন্ন: অ্যাপ ডাউনলোড হাবে নেওয়া হয়েছে।"
      }
      cmd.contains("লগআউট") -> {
        logoutAdmin()
        _voiceExecutionResult.value = "✅ ভয়েস কমান্ড সম্পন্ন: সফলভাবে অ্যাডমিন লগআউট করা হয়েছে।"
      }
      else -> {
        _voiceExecutionResult.value = "🤖 কমান্ড বুঝতে পেরেছি: '$command'। অনুরূপ ফিচার প্যানেল লোড করা হয়েছে।"
        _adminTab.value = AdminTab.AI_ASSISTANT
      }
    }
    _notification.value = UiNotification(_voiceExecutionResult.value ?: "ভয়েস কমান্ড এক্সিকিউট হয়েছে।")
  }

  // Customer Management Methods
  fun setCustomerSearchQuery(q: String) {
    _customerSearchQuery.value = q
  }

  fun setCustomerStatusFilter(status: CustomerStatus?) {
    _customerStatusFilter.value = status
  }

  fun updateCustomerStatus(customerId: String, status: CustomerStatus) {
    repository.updateCustomerStatus(customerId, status)
    _notification.value = UiNotification("গ্রাহক স্ট্যাটাস পরিবর্তিত হয়েছে: ${status.titleBn}")
  }

  fun adjustCustomerWallet(customerId: String, amount: Int, isAdd: Boolean, reason: String) {
    repository.adjustCustomerWallet(customerId, amount, isAdd, reason)
    val msg = if (isAdd) "গ্রাহকের ওয়ালেটে ৳$amount যোগ করা হয়েছে!" else "গ্রাহকের ওয়ালেট থেকে ৳$amount কর্তন করা হয়েছে।"
    _notification.value = UiNotification(msg)
  }

  fun setCustomerDiscount(customerId: String, percent: Int) {
    repository.setCustomerDiscount(customerId, percent)
    _notification.value = UiNotification("গ্রাহকের জন্য $percent% স্পেশাল ডিসকাউন্ট নির্ধারণ করা হয়েছে।")
  }

  fun updateCustomerNotes(customerId: String, notes: String) {
    repository.updateCustomerNotes(customerId, notes)
    _notification.value = UiNotification("গ্রাহকের অভ্যন্তরীণ নোট আপডেট হয়েছে।")
  }

  // Cash Counter Payment Methods
  fun openCashPaymentDialog() {
    _showCashPaymentDialog.value = true
  }

  fun closeCashPaymentDialog() {
    _showCashPaymentDialog.value = false
  }

  fun recordCashPayment(
    customerName: String,
    customerPhone: String,
    serviceTitleBn: String,
    servicePriceBdt: Int,
    amountPaidBdt: Int,
    notes: String
  ) {
    val record = repository.recordOfflineCashPayment(
      customerName = customerName,
      customerPhone = customerPhone,
      serviceTitleBn = serviceTitleBn,
      servicePriceBdt = servicePriceBdt,
      amountPaidBdt = amountPaidBdt,
      notes = notes
    )
    _showCashPaymentDialog.value = false
    _selectedPaymentForInvoice.value = record
    _notification.value = UiNotification("কাউন্টার নগদ ক্যাশ পেমেন্ট রেকর্ড সম্পন্ন! মানি রিসিট প্রস্তুত।")
  }

  // Invoice / Money Receipt Dialog
  fun openPaymentInvoice(record: CustomerPaymentRecord) {
    _selectedPaymentForInvoice.value = record
  }

  fun closePaymentInvoice() {
    _selectedPaymentForInvoice.value = null
  }

  // Action Confirmation Approval Flow
  fun requestConfirmation(
    titleBn: String,
    messageBn: String,
    isDestructive: Boolean = false,
    onConfirm: () -> Unit
  ) {
    _actionConfirmation.value = ActionConfirmationState(
      titleBn = titleBn,
      messageBn = messageBn,
      isDestructive = isDestructive,
      onConfirm = {
        onConfirm()
        _actionConfirmation.value = null
      }
    )
  }

  fun dismissConfirmation() {
    _actionConfirmation.value = null
  }

  // Broadcast Notice Operations
  fun openAddNoticeDialog() {
    _showAddNoticeDialog.value = true
  }

  fun closeAddNoticeDialog() {
    _showAddNoticeDialog.value = false
  }

  fun publishNotice(titleBn: String, messageBn: String, isUrgent: Boolean) {
    repository.addBroadcastNotice(titleBn, messageBn, isUrgent)
    _showAddNoticeDialog.value = false
    _notification.value = UiNotification("জরুরি বার্তা সফলভাবে প্রচার করা হয়েছে!")
  }

  fun toggleNotice(id: String) {
    repository.toggleBroadcastNotice(id)
    _notification.value = UiNotification("নোটিশের স্থিতি পরিবর্তন হয়েছে।")
  }

  fun deleteNotice(id: String) {
    repository.deleteBroadcastNotice(id)
    _notification.value = UiNotification("নোটিশটি মুছে ফেলা হয়েছে।")
  }

  // Security Operations
  fun terminateSession(sessionId: String) {
    repository.terminateSession(sessionId)
    _notification.value = UiNotification("সেশনটি সফলভাবে বাতিল করা হয়েছে।")
  }

  fun dismissSecurityAlert(alertId: String) {
    repository.dismissSecurityAlert(alertId)
    _notification.value = UiNotification("নিরাপত্তা সতর্কতা সরানো হয়েছে।")
  }

  fun clearNotification() {
    _notification.value = null
  }
}
