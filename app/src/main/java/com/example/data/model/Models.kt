package com.example.data.model

enum class ServiceCategory(val titleBn: String, val titleEn: String, val iconKey: String) {
  ALL("সকল সেবা", "All Services", "all"),
  NID_VOTER("জাতীয় পরিচয়পত্র ও ভোটার", "NID & Voter", "nid"),
  BIRTH_DEATH("জন্ম ও মৃত্যু নিবন্ধন", "Birth & Death", "birth"),
  PASSPORT_VISA("পাসপোর্ট ও ইমিগ্রেশন", "Passport & Visa", "passport"),
  LAND_MUTATION("ভূমি, খতিয়ান ও নামজারি", "Land & Mutation", "land"),
  TIN_TAX("TIN ও আয়কর রিটার্ন", "TIN & Tax", "tax"),
  EDUCATION("শিক্ষা ও ফলাফল", "Education & Results", "school"),
  MOBILE_SERVICE("মোবাইল সেবা ও ব্যাংকিং", "Mobile Services", "mobile"),
  DIGITAL_STUDIO("ডিজিটাল স্টুডিও ও ডিজাইন", "Digital Studio & Design", "studio"),
  ECOMMERCE_SHOP("অনলাইন শপ ও এক্সেসরিজ", "Shop & Accessories", "shop"),
  TRADE_LICENSE("ট্রেড লাইসেন্স ও ব্যবসা", "Trade License", "business"),
  UTILITY_BILLS("বিদ্যুৎ, গ্যাস ও পানির বিল", "Utility Bills", "utility"),
  GOVT_JOBS("সরকারি চাকরি ও কর্মসংস্থান", "Govt Jobs", "job"),
  DRIVING_BRTA("ড্রাইভিং লাইসেন্স ও BRTA", "Driving & BRTA", "driving"),
  LEGAL_HELPLINE("জরুরি নাগরিক হেল্পলাইন", "Emergency Helplines", "helpline")
}

data class CitizenService(
  val id: String,
  val serviceCode: String,
  val titleBn: String,
  val titleEn: String,
  val category: ServiceCategory,
  val descriptionBn: String,
  val officialFeeBdt: Int = 0,
  val serviceChargeBdt: Int = 100,
  val discountFeeBdt: Int = 0,
  val officialPortalUrl: String,
  val estimatedDays: String = "১-৩ কার্যদিবস",
  val timerBadge: String = "",
  val requiredDocuments: List<String> = emptyList(),
  val eligibility: String = "সকল বাংলাদেশি নাগরিক",
  val applicationSteps: List<String> = emptyList(),
  val termsConditions: String = "সরকারি নিয়মাবলি ও সঠিক তথ্য প্রদান সাপেক্ষে সেবা প্রদান করা হয়।",
  val iconKey: String = "service",
  val isActive: Boolean = true,
  val isFeatured: Boolean = false,
  val isPopular: Boolean = false,
  val updatedDate: String = "২০২৬-১০-১৯",
  val version: Int = 1
) {
  val totalFeeBdt: Int get() = if (discountFeeBdt > 0) discountFeeBdt else (officialFeeBdt + serviceChargeBdt)
}

enum class OrderStatus(val titleBn: String, val colorHex: Long) {
  PENDING("অপেক্ষমান (Pending)", 0xFFF57C00),
  PAYMENT_PENDING("পেমেন্ট বাকি (Unpaid)", 0xFFD32F2F),
  PROCESSING("প্রক্রিয়াধীন (Processing)", 0xFF1976D2),
  AWAITING_INFO("তথ্য প্রয়োজন (Info Needed)", 0xFF7B1FA2),
  COMPLETED("সম্পন্ন হয়েছে (Completed)", 0xFF2E7D32),
  REJECTED("বাতিল (Rejected)", 0xFFC62828)
}

enum class PaymentMethod(val displayNameBn: String, val defaultAccount: String) {
  WALLET("ওয়ালেট ব্যালেন্স", "Rongdhonu Wallet"),
  BKASH("বিকাশ (bKash)", "01632-042441 (Personal/Merchant)"),
  NAGAD("নগদ (Nagad)", "01632-042441 (Personal/Merchant)"),
  ROCKET("রকেট (Rocket)", "01632-042441-2 (Personal)"),
  BANK("ব্যাংক ট্রান্সফার (Bank)", "Sonali Bank A/C: 1204-5829103"),
  CARD("ডেবিট/ক্রেডিট কার্ড (Card)", "SSLCommerz / Shurjopay Gateway")
}

enum class PaymentVerificationStatus(val titleBn: String, val badgeColor: Long) {
  PENDING("অপেক্ষমাণ (Pending)", 0xFFE65100),
  SUBMITTED("তথ্য জমা (Submitted)", 0xFF0288D1),
  VERIFIED("যাচাইকৃত (Verified)", 0xFF2E7D32),
  PARTIALLY_PAID("আংশিক পরিশোধ (Partial)", 0xFFF57C00),
  FULLY_PAID("সম্পূর্ণ পরিশোধ (Paid)", 0xFF1B5E20),
  FAILED("ব্যর্থ (Failed)", 0xFFD32F2F),
  REJECTED("প্রত্যাখ্যাত (Rejected)", 0xFFC62828),
  REFUND_PENDING("রিফান্ড প্রক্রিয়াধীন (Refund Pending)", 0xFF7B1FA2),
  REFUNDED("রিফান্ড সম্পন্ন (Refunded)", 0xFF4A148C)
}

data class CustomerPaymentRecord(
  val paymentId: String,
  val customerId: String,
  val customerName: String,
  val customerPhone: String,
  val whatsappNumber: String = customerPhone,
  val orderNumber: String,
  val serviceTitleBn: String,
  val servicePriceBdt: Int,
  val amountSentBdt: Int,
  val verifiedAmountBdt: Int,
  val paymentMethod: PaymentMethod,
  val trxId: String,
  val timestamp: Long = System.currentTimeMillis(),
  val status: PaymentVerificationStatus = PaymentVerificationStatus.PENDING,
  val assignedAdmin: String = "মোঃ রেজাউল করিম",
  val notes: String = "",
  val refundTrxId: String = "",
  val refundAmountBdt: Int = 0,
  val refundReason: String = "",
  val isRefundProcessed: Boolean = false
) {
  val dueAmountBdt: Int get() = (servicePriceBdt - verifiedAmountBdt).coerceAtLeast(0)
}

enum class AppPlatform(val titleBn: String, val fileName: String, val iconKey: String) {
  ANDROID_APK("অ্যান্ড্রয়েড মোবাইল অ্যাপ", "Rongdhonu-Online-Shop.apk", "android"),
  WINDOWS_EXE("উইন্ডোজ কম্পিউটার অ্যাপ (Setup)", "Rongdhonu-Online-Shop-Setup.exe", "windows"),
  WINDOWS_ZIP("উইন্ডোজ পোর্টেবল সংস্করণ (ZIP)", "Rongdhonu-Online-Shop-Portable.zip", "zip"),
  PWA_WEB("ওয়েব অ্যাপ / PWA (Browser)", "Rongdhonu Web App", "web")
}

data class AppBuildRelease(
  val id: String,
  val platform: AppPlatform,
  val version: String,
  val buildNumber: Int,
  val fileName: String,
  val fileSize: String,
  val releaseDate: String,
  val minOsRequirement: String,
  val sha256Checksum: String,
  val releaseNotesBn: String,
  val downloadUrl: String,
  val isDownloadActive: Boolean = true,
  val downloadCount: Int = 142
)


data class ServiceOrder(
  val orderId: String,
  val serviceId: String,
  val serviceTitleBn: String,
  val citizenName: String,
  val citizenPhone: String,
  val identifierNumber: String,
  val notes: String = "",
  val feePaidBdt: Int,
  val paymentMethod: PaymentMethod,
  val trxId: String,
  val status: OrderStatus = OrderStatus.PENDING,
  val createdAt: Long = System.currentTimeMillis(),
  val updatedAt: Long = System.currentTimeMillis(),
  val adminRemarks: String = ""
)

enum class TransactionType(val titleBn: String) {
  RECHARGE("ওয়ালেট রিচার্জ"),
  SERVICE_FEE("সেবা ফি প্রদান"),
  REFUND("ফেরত (Refund)")
}

enum class TransactionStatus(val titleBn: String) {
  SUCCESS("সফল (Success)"),
  PENDING_APPROVAL("যাচাই চলছে (Pending)"),
  FAILED("ব্যর্থ (Failed)")
}

data class WalletTransaction(
  val id: String,
  val type: TransactionType,
  val amountBdt: Int,
  val method: PaymentMethod,
  val trxId: String,
  val timestamp: Long = System.currentTimeMillis(),
  val status: TransactionStatus = TransactionStatus.SUCCESS,
  val note: String = ""
)

data class PaymentGatewayConfig(
  val method: PaymentMethod,
  val isEnabled: Boolean = true,
  val accountNumber: String,
  val accountTypeBn: String = "পার্সোনাল / মার্চেন্ট",
  val instructionsBn: String
)

data class CitizenHelpline(
  val number: String,
  val titleBn: String,
  val titleEn: String,
  val descBn: String,
  val categoryBn: String
)

data class SupportTicket(
  val id: String,
  val subject: String,
  val message: String,
  val citizenPhone: String,
  val status: String = "Open",
  val createdAt: Long = System.currentTimeMillis()
)

data class AuditLog(
  val id: String,
  val action: String,
  val target: String,
  val performedBy: String = "Admin (রংধনু)",
  val timestamp: Long = System.currentTimeMillis()
)

// NEW: Social Media Link Model
data class SocialMediaLink(
  val id: String,
  val platform: String,
  val displayNameBn: String,
  val url: String,
  val iconKey: String,
  val isEnabled: Boolean = true,
  val orderIndex: Int = 0
)

// NEW: Admin Role & User
enum class AdminRole(val titleBn: String, val descriptionBn: String) {
  SUPER_ADMIN("সুপার অ্যাডমিন", "সকল ক্ষমতা ও সেটিংস নিয়ন্ত্রণ"),
  ADMIN("অ্যাডমিন", "সার্ভিস, অর্ডার ও ব্যালেন্স নিয়ন্ত্রণ"),
  SERVICE_MANAGER("সার্ভিস ম্যানেজার", "সার্ভিস তৈরি ও মূল্য সম্পাদনা"),
  ORDER_MANAGER("অর্ডার ম্যানেজার", "আবেদন ট্র্যাকিং ও প্রসেসিং"),
  CUSTOMER_SUPPORT("কাস্টমার সাপোর্ট", "টিকিট ও হেল্পলাইন"),
  READ_ONLY_STAFF("রিড-অনলি স্টাফ", "শুধুমাত্র রিপোর্ট পর্যবেক্ষণ")
}

data class AdminUser(
  val id: String,
  val username: String,
  val role: AdminRole,
  val fullName: String,
  val lastLogin: Long = System.currentTimeMillis()
)

// NEW: Order Notification for Admin
data class AdminOrderNotification(
  val id: String,
  val orderId: String,
  val citizenName: String,
  val citizenPhone: String,
  val serviceTitle: String,
  val amountBdt: Int,
  val status: OrderStatus,
  val timestamp: Long = System.currentTimeMillis(),
  val isRead: Boolean = false
)

// NEW: Verified Service Provider Directory
data class ServiceProvider(
  val id: String,
  val nameBn: String,
  val roleBn: String,
  val specialtyBn: String,
  val districtBn: String,
  val phone: String,
  val rating: Float,
  val verified: Boolean = true,
  val completedAssists: Int = 120
)

// NEW: Customer Profile
data class CustomerProfile(
  val id: String,
  val name: String,
  val whatsappPhone: String,
  val email: String = "rongdhonustudio00@gmail.com",
  val nidNumber: String = "",
  val address: String = "রংপুর, বাংলাদেশ",
  val isPhoneVerified: Boolean = true,
  val registeredAt: Long = System.currentTimeMillis() - 864000000L
)

// NEW: System Health Status
data class SystemHealthMetrics(
  val databaseStatus: String = "অনলাইন ও সিঙ্কড (SQLite + StateFlow)",
  val apiGatewayLatencyMs: Int = 24,
  val authServiceOnline: Boolean = true,
  val notificationsActive: Boolean = true,
  val paymentGatewaysOnline: Boolean = true,
  val maintenanceMode: Boolean = false,
  val activeUsersCount: Int = 42,
  val totalVisitsToday: Int = 589
)

// NEW: Database Backup Record
data class DatabaseBackupRecord(
  val id: String,
  val fileName: String,
  val backupSizeKb: Int,
  val createdAt: Long = System.currentTimeMillis(),
  val servicesCount: Int,
  val ordersCount: Int,
  val verifiedBy: String = "Super Admin"
)

// NEW: AI Seva Mitra Message
data class AiChatMessage(
  val id: String,
  val isUser: Boolean,
  val textBn: String,
  val timestamp: Long = System.currentTimeMillis(),
  val suggestedServiceId: String? = null
)

// Customer Management Models
enum class CustomerStatus(val titleBn: String, val badgeColor: Long) {
  ACTIVE("সক্রিয় (Active)", 0xFF2E7D32),
  VIP("ভিআইপি গ্রাহক (VIP)", 0xFF7B1FA2),
  BLOCKED("স্থগিত / ব্লকড (Blocked)", 0xFFC62828)
}

data class CustomerRecord(
  val id: String,
  val name: String,
  val phone: String,
  val email: String = "rongdhonustudio00@gmail.com",
  val address: String = "রংপুর, বাংলাদেশ",
  val walletBalanceBdt: Int = 650,
  val totalOrdersCount: Int = 3,
  val totalSpentBdt: Int = 1850,
  val status: CustomerStatus = CustomerStatus.ACTIVE,
  val specialDiscountPercent: Int = 0,
  val adminNotes: String = "নিয়মিত বিশ্বস্ত সেবাগ্রহীতা",
  val registeredDate: String = "২০২৬-০১-১৫"
)

// Automation & Notice Board
data class BroadcastNotice(
  val id: String,
  val titleBn: String,
  val messageBn: String,
  val date: String,
  val isUrgent: Boolean = false,
  val isActive: Boolean = true
)

// Advanced Security System Models
data class AdminSession(
  val id: String,
  val deviceName: String,
  val ipAddress: String,
  val location: String,
  val loginTime: Long = System.currentTimeMillis(),
  val isCurrent: Boolean = false
)

data class SecurityAlert(
  val id: String,
  val titleBn: String,
  val detailBn: String,
  val severity: String = "Medium", // High, Medium, Info
  val timestamp: Long = System.currentTimeMillis()
)

// Invoice & Receipt Data
data class PaymentInvoice(
  val invoiceNumber: String,
  val paymentId: String,
  val customerName: String,
  val customerPhone: String,
  val serviceTitleBn: String,
  val totalAmountBdt: Int,
  val paidAmountBdt: Int,
  val dueAmountBdt: Int,
  val paymentMethod: PaymentMethod,
  val trxId: String,
  val timestamp: Long = System.currentTimeMillis(),
  val issuedBy: String = "রংধনু ডিজিটাল স্টুডিও"
)

