package com.example.data

import com.example.data.model.AppBuildRelease
import com.example.data.model.AppPlatform
import com.example.data.model.CitizenHelpline
import com.example.data.model.CitizenService
import com.example.data.model.CustomerPaymentRecord
import com.example.data.model.PaymentGatewayConfig
import com.example.data.model.PaymentMethod
import com.example.data.model.PaymentVerificationStatus
import com.example.data.model.ServiceCategory

object DefaultServices {
  val helplines = listOf(
    CitizenHelpline("333", "জাতীয় নাগরিক তথ্য সেবা (333)", "National Info Service", "সকল সরকারি সেবা ও সামাজিক ভাতা তথ্য", "তথ্য সেবা"),
    CitizenHelpline("999", "জাতীয় জরুরি সেবা (999)", "National Emergency", "পুলিশ, ফায়ার সার্ভিস ও অ্যাম্বুলেন্স", "জরুরি সেবা"),
    CitizenHelpline("105", "NID ও ভোটার হেল্পলাইন (105)", "NID Helpline", "জাতীয় পরিচয়পত্র সংশোধন ও স্মার্টকার্ড", "NID সেবা"),
    CitizenHelpline("16122", "ভূমি সেবা হটলাইন (16122)", "Land Ministry", "খতিয়ান, নামজারি ও খাজনা সমাধান", "ভূমি সেবা"),
    CitizenHelpline("109", "নারী ও শিশু নির্যাতন হেল্পলাইন (109)", "Women & Child", "তাৎক্ষণিক আইনি ও সামাজিক সুরক্ষা", "সুরক্ষা"),
    CitizenHelpline("106", "দুদক হটলাইন (106)", "Anti-Corruption", "দুর্নীতি ও ঘুষ সংক্রান্ত তথ্য", "অভিযোগ")
  )

  val paymentGateways = listOf(
    PaymentGatewayConfig(
      method = PaymentMethod.BKASH,
      isEnabled = true,
      accountNumber = "01632-042441",
      accountTypeBn = "মার্চেন্ট ও পার্সোনাল (Send Money)",
      instructionsBn = "আপনার বিকাশ অ্যাপ থেকে 01632042441 নম্বরে 'Send Money' বা 'Make Payment' করুন এবং TrxID দিন।"
    ),
    PaymentGatewayConfig(
      method = PaymentMethod.NAGAD,
      isEnabled = true,
      accountNumber = "01632-042441",
      accountTypeBn = "পার্সোনাল একাউন্ট (Send Money)",
      instructionsBn = "নগদ অ্যাপ বা *167# ডায়াল করে 01632042441 নম্বরে Send Money করে TrxID দিন।"
    ),
    PaymentGatewayConfig(
      method = PaymentMethod.ROCKET,
      isEnabled = true,
      accountNumber = "01632-042441-2",
      accountTypeBn = "পার্সোনাল একাউন্ট",
      instructionsBn = "রকেট অ্যাপ থেকে 016320424412 নম্বরে Send Money করে TrxID দিন।"
    ),
    PaymentGatewayConfig(
      method = PaymentMethod.BANK,
      isEnabled = true,
      accountNumber = "Sonali Bank A/C: 1204-5829103",
      accountTypeBn = "কারেন্ট একাউন্ট (ম্যানুয়াল ভেরিফিকেশন)",
      instructionsBn = "সোনালী ব্যাংক একাউন্টে জমা দিয়ে ব্যাংক ডিপোজিট স্লিপের ছবি ও রেফারেন্স নম্বর দিন।"
    ),
    PaymentGatewayConfig(
      method = PaymentMethod.CARD,
      isEnabled = false,
      accountNumber = "Visa / MasterCard / AMEX",
      accountTypeBn = "গেটওয়ে মার্চেন্ট (Configuration Required)",
      instructionsBn = "অনলাইন কার্ড পেমেন্ট গেটওয়ে মার্চেন্ট অ্যাক্টিভেশন পেন্ডিং। অনুগ্রহ করে মোবাইল ব্যাংকিং ব্যবহার করুন।"
    )
  )

  val initialServices = listOf(
    // Reference Screenshot 1: ভাতা ও পুনর্বাসন
    CitizenService(
      id = "disability_allowance",
      serviceCode = "VA-01",
      titleBn = "প্রতিবন্ধী ভাতা আবেদন",
      titleEn = "Disability Allowance Application",
      category = ServiceCategory.BIRTH_DEATH,
      descriptionBn = "সমাজসেবা অধিদপ্তরের আওতায় প্রতিবন্ধী ভাতার অনলাইন আবেদন ও সহায়তা।",
      officialFeeBdt = 0,
      serviceChargeBdt = 650,
      officialPortalUrl = "https://dss.gov.bd",
      estimatedDays = "১২-২৪ ঘণ্টা",
      timerBadge = "১২-২৪ ঘণ্টা",
      requiredDocuments = listOf("সুবর্ণ কার্ডের কপি", "অনলাইন জন্ম নিবন্ধন বা NID", "ব্যাংক বা মোবাইল ব্যাংকিং একাউন্ট"),
      iconKey = "money_bag",
      isFeatured = true,
      isPopular = true
    ),
    CitizenService(
      id = "disability_stipend",
      serviceCode = "VA-02",
      titleBn = "প্রতিবন্ধী শিক্ষা উপবৃত্তি আবেদন",
      titleEn = "Disability Education Stipend",
      category = ServiceCategory.EDUCATION,
      descriptionBn = "অসচ্ছল প্রতিবন্ধী শিক্ষার্থীদের মাসিক শিক্ষা উপবৃত্তির অনলাইন ফরম পূরণ।",
      officialFeeBdt = 0,
      serviceChargeBdt = 650,
      officialPortalUrl = "https://dss.gov.bd",
      estimatedDays = "২৪ ঘণ্টা",
      timerBadge = "২৪ ঘণ্টা",
      requiredDocuments = listOf("শিক্ষাপ্রতিষ্ঠানের প্রত্যয়নপত্র", "সুবর্ণ নাগরিক সনদ", "অভিভাবকের NID"),
      iconKey = "graduation",
      isFeatured = true,
      isPopular = true
    ),
    CitizenService(
      id = "suborno_card_verify",
      serviceCode = "VA-03",
      titleBn = "সুবর্ণ কার্ড যাচাই ও স্ট্যাটাস চেক",
      titleEn = "Suborno Card Status Check",
      category = ServiceCategory.BIRTH_DEATH,
      descriptionBn = "ডিজিটাল সুবর্ণ কার্ড ডাটাবেজ অনুসন্ধান ও অনলাইন ভেরিফিকেশন।",
      officialFeeBdt = 0,
      serviceChargeBdt = 650,
      officialPortalUrl = "https://dis.dss.gov.bd",
      estimatedDays = "৩০ মিনিট - ২ ঘণ্টা",
      timerBadge = "৩০ মিনিট - ২ ঘণ্টা",
      requiredDocuments = listOf("সুবর্ণ কার্ড নম্বর অথবা আবেদন স্লিপ"),
      iconKey = "search",
      isFeatured = true,
      isPopular = true
    ),
    CitizenService(
      id = "lost_suborno_card",
      serviceCode = "VA-04",
      titleBn = "হারানো/নষ্ট সুবর্ণ কার্ড উত্তোলন",
      titleEn = "Lost Suborno Card Re-issue",
      category = ServiceCategory.BIRTH_DEATH,
      descriptionBn = "হারানো সুবর্ণ প্রতিবন্ধী সনদের অনলাইন ডুপ্লিকেট কপি উত্তোলন ও যাচাই।",
      officialFeeBdt = 0,
      serviceChargeBdt = 650,
      officialPortalUrl = "https://dis.dss.gov.bd",
      estimatedDays = "১২-২৪ ঘণ্টা",
      timerBadge = "১২-২৪ ঘণ্টা",
      requiredDocuments = listOf("থানার জিডি কপি", "জাতীয় পরিচয়পত্র", "ছবি"),
      iconKey = "refresh",
      isFeatured = true,
      isPopular = true
    ),
    CitizenService(
      id = "rehab_assistive",
      serviceCode = "VA-05",
      titleBn = "সহায়ক উপকরণ ও পুনর্বাসন আবেদন",
      titleEn = "Assistive Device & Rehab Application",
      category = ServiceCategory.BIRTH_DEATH,
      descriptionBn = "হুইলচেয়ার, হিয়ারিং এইড ও পুনর্বাসন সহায়তার সরকারি আবেদন ফরম।",
      officialFeeBdt = 0,
      serviceChargeBdt = 650,
      officialPortalUrl = "https://dss.gov.bd",
      estimatedDays = "২৪ ঘণ্টা",
      timerBadge = "২৪ ঘণ্টা",
      requiredDocuments = listOf("ডাক্তারি প্রেসক্রিপশন", "সুবর্ণ কার্ড"),
      iconKey = "wheelchair",
      isFeatured = true,
      isPopular = true
    ),
    CitizenService(
      id = "tin_certificate",
      serviceCode = "TAX-01",
      titleBn = "টিন সার্টিফিকেট",
      titleEn = "e-TIN Certificate Download",
      category = ServiceCategory.TIN_TAX,
      descriptionBn = "জাতীয় রাজস্ব বোর্ডের ডিজিটাল ১২ ডিজিটের ই-টিন সার্টিফিকেট সংগ্রহ।",
      officialFeeBdt = 0,
      serviceChargeBdt = 59,
      officialPortalUrl = "https://secure.incometax.gov.bd",
      estimatedDays = "তাৎক্ষণিক (১৫ মিনিট)",
      iconKey = "document",
      isFeatured = true,
      isPopular = true
    ),

    // Reference Screenshot 3: NID কপি সেবা
    CitizenService(
      id = "nid_server_copy",
      serviceCode = "NID-10",
      titleBn = "সার্ভার কপি",
      titleEn = "NID Server Copy Print",
      category = ServiceCategory.NID_VOTER,
      descriptionBn = "নির্বাচন কমিশনের অফিশিয়াল সার্ভার থেকে ডিজিটাল এনআইডি সার্ভার কপি।",
      officialFeeBdt = 0,
      serviceChargeBdt = 45,
      officialPortalUrl = "https://services.nidw.gov.bd",
      estimatedDays = "১০-৩০ মিনিট",
      timerBadge = "৩০ মিনিট",
      requiredDocuments = listOf("১০ বা ১৭ ডিজিটের NID নম্বর ও জন্মতারিখ"),
      iconKey = "clipboard",
      isFeatured = true,
      isPopular = true
    ),
    CitizenService(
      id = "nid_sign_copy",
      serviceCode = "NID-11",
      titleBn = "সাইন কপি",
      titleEn = "Signed NID Copy",
      category = ServiceCategory.NID_VOTER,
      descriptionBn = "স্বাক্ষরযুক্ত অফিসিয়াল এনআইডি তথ্যের সাইন কপি প্রস্তুত।",
      officialFeeBdt = 0,
      serviceChargeBdt = 85,
      officialPortalUrl = "https://services.nidw.gov.bd",
      estimatedDays = "৩০ মিনিট - ১ ঘণ্টা",
      iconKey = "pen",
      isFeatured = true,
      isPopular = true
    ),
    CitizenService(
      id = "nid_card_pdf",
      serviceCode = "NID-12",
      titleBn = "NID কার্ড PDF",
      titleEn = "NID Card PDF Download",
      category = ServiceCategory.NID_VOTER,
      descriptionBn = "উচ্চ রেজোলিউশন প্রিন্টেবল লেমিনেটিং সাইজ জাতীয় পরিচয়পত্র পিডিএফ।",
      officialFeeBdt = 0,
      serviceChargeBdt = 99,
      officialPortalUrl = "https://services.nidw.gov.bd",
      estimatedDays = "১৫ মিনিট",
      iconKey = "card",
      isFeatured = true,
      isPopular = true
    ),
    CitizenService(
      id = "nid_form_sign",
      serviceCode = "NID-13",
      titleBn = "ফরম নং → সাইন কপি",
      titleEn = "Form No to Sign Copy",
      category = ServiceCategory.NID_VOTER,
      descriptionBn = "নতুন ভোটারের ফরম নম্বর দিয়ে অনলাইন সাইন কপি সংগ্রহ।",
      officialFeeBdt = 0,
      serviceChargeBdt = 23,
      officialPortalUrl = "https://services.nidw.gov.bd",
      estimatedDays = "১০ মিনিট",
      iconKey = "form_pen",
      isFeatured = true,
      isPopular = true
    ),
    CitizenService(
      id = "nid_voter_no_srv",
      serviceCode = "NID-14",
      titleBn = "NID ভোটার নাম্বার দিয়ে সার্ভিস",
      titleEn = "Voter No to NID Service",
      category = ServiceCategory.NID_VOTER,
      descriptionBn = "ভোটার সিরিয়াল নম্বর দিয়ে ভোটার এলাকা ও জাতীয় পরিচয়পত্র অনুসন্ধান।",
      officialFeeBdt = 0,
      serviceChargeBdt = 45,
      officialPortalUrl = "https://services.nidw.gov.bd",
      estimatedDays = "১৫ মিনিট",
      iconKey = "ballot",
      isFeatured = true,
      isPopular = true
    ),
    CitizenService(
      id = "nid_official_server",
      serviceCode = "NID-15",
      titleBn = "অফিসিয়াল সার্ভার কপি",
      titleEn = "Official Server Copy Verified",
      category = ServiceCategory.NID_VOTER,
      descriptionBn = "অফিসিয়াল সিলযুক্ত সম্পূর্ণ ভেরিফায়েড সার্ভার কপি।",
      officialFeeBdt = 0,
      serviceChargeBdt = 59,
      officialPortalUrl = "https://services.nidw.gov.bd",
      estimatedDays = "২০ মিনিট",
      iconKey = "bank",
      isFeatured = true,
      isPopular = true
    ),

    // Reference Screenshot 5: স্মার্ট আইডি ও মোবাইল নম্বর সেবা
    CitizenService(
      id = "smart_id_pdf",
      serviceCode = "NID-20",
      titleBn = "স্মার্ট ID কার্ড PDF",
      titleEn = "Smart NID Card PDF Print",
      category = ServiceCategory.NID_VOTER,
      descriptionBn = "চিপ ও কিউআর কোডযুক্ত স্মার্ট এনআইডি কার্ডের দুই পাশের এইচডি প্রিন্ট ফাইল।",
      officialFeeBdt = 0,
      serviceChargeBdt = 155,
      officialPortalUrl = "https://services.nidw.gov.bd",
      estimatedDays = "৩০ মিনিট",
      iconKey = "smart_card",
      isFeatured = true,
      isPopular = true
    ),
    CitizenService(
      id = "mobile_to_id",
      serviceCode = "NID-21",
      titleBn = "মোবাইল নাম্বার দিয়ে ID কার্ড",
      titleEn = "Mobile No to ID Card Search",
      category = ServiceCategory.NID_VOTER,
      descriptionBn = "বায়োমেট্রিক নিবন্ধিত মোবাইল নম্বর দিয়ে জাতীয় পরিচয়পত্র তথ্য অনুসন্ধান।",
      officialFeeBdt = 0,
      serviceChargeBdt = 115,
      officialPortalUrl = "https://services.nidw.gov.bd",
      estimatedDays = "১-২ ঘণ্টা",
      iconKey = "mobile",
      isFeatured = true,
      isPopular = true
    ),
    CitizenService(
      id = "mobile_to_server",
      serviceCode = "NID-22",
      titleBn = "মোবাইল নাম্বার দিয়ে সার্ভার কপি",
      titleEn = "Mobile No to Server Copy",
      category = ServiceCategory.NID_VOTER,
      descriptionBn = "মোবাইল সিম রেজিস্ট্রেশন ম্যাচিং করে এনআইডি সার্ভার কপি উদ্ধার।",
      officialFeeBdt = 0,
      serviceChargeBdt = 115,
      officialPortalUrl = "https://services.nidw.gov.bd",
      estimatedDays = "১-২ ঘণ্টা",
      iconKey = "mobile_clipboard",
      isFeatured = true,
      isPopular = true
    ),
    CitizenService(
      id = "mobile_to_sign",
      serviceCode = "NID-23",
      titleBn = "মোবাইল নাম্বার দিয়ে সাইন কপি",
      titleEn = "Mobile No to Sign Copy",
      category = ServiceCategory.NID_VOTER,
      descriptionBn = "রেজিস্টার্ড মোবাইল তথ্য মিলিয়ে এনআইডি সাইন কপি উত্তোলন।",
      officialFeeBdt = 0,
      serviceChargeBdt = 115,
      officialPortalUrl = "https://services.nidw.gov.bd",
      estimatedDays = "১-২ ঘণ্টা",
      iconKey = "mobile_pen",
      isFeatured = true,
      isPopular = true
    ),
    CitizenService(
      id = "name_addr_id",
      serviceCode = "NID-24",
      titleBn = "নাম ঠিকানা দিয়ে আইডি কার্ড",
      titleEn = "Name & Address to ID Card",
      category = ServiceCategory.NID_VOTER,
      descriptionBn = "নাম, পিতা-মাতার নাম ও স্থায়ী ঠিকানা দিয়ে ভোটার তালিকা অনুসন্ধান।",
      officialFeeBdt = 0,
      serviceChargeBdt = 185,
      officialPortalUrl = "https://services.nidw.gov.bd",
      estimatedDays = "২-৪ ঘণ্টা",
      iconKey = "card_profile",
      isFeatured = true,
      isPopular = true
    ),
    CitizenService(
      id = "birth_reg_new",
      serviceCode = "BR-01",
      titleBn = "নতুন জন্মনিবন্ধন",
      titleEn = "New Birth Registration",
      category = ServiceCategory.BIRTH_DEATH,
      descriptionBn = "BDRIS পোর্টালে অনলাইন নতুন জন্ম নিবন্ধন আবেদন ও ১৭ ডিজিটের সনদ প্রস্তুতি।",
      officialFeeBdt = 50,
      serviceChargeBdt = 460,
      officialPortalUrl = "https://bdris.gov.bd",
      estimatedDays = "২৪ ঘণ্টা",
      timerBadge = "২৪ ঘণ্টা",
      iconKey = "baby",
      isFeatured = true,
      isPopular = true
    ),

    // Reference Screenshot 4: TIN ও কল লিস্ট
    CitizenService(
      id = "tin_new_reg",
      serviceCode = "TAX-02",
      titleBn = "নতুন TIN রেজিস্ট্রেশন",
      titleEn = "New e-TIN Registration",
      category = ServiceCategory.TIN_TAX,
      descriptionBn = "অনলাইনে নতুন করদাতা নিবন্ধন ও সঙ্গে সঙ্গে সার্টিফিকেট ডাউনলোড।",
      officialFeeBdt = 0,
      serviceChargeBdt = 99,
      officialPortalUrl = "https://secure.incometax.gov.bd",
      estimatedDays = "১৫ মিনিট",
      iconKey = "tax_receipt",
      isFeatured = true,
      isPopular = true
    ),
    CitizenService(
      id = "tax_return",
      serviceCode = "TAX-03",
      titleBn = "আয়কর রিটার্ন",
      titleEn = "Income Tax Return Submission",
      category = ServiceCategory.TIN_TAX,
      descriptionBn = "অনলাইন জিরো রিটার্ন দাখিল ও একনলেজমেন্ট স্লিপ সংগ্রহ।",
      officialFeeBdt = 0,
      serviceChargeBdt = 350,
      officialPortalUrl = "https://etaxnbr.gov.bd",
      estimatedDays = "১ কার্যদিবস",
      iconKey = "briefcase",
      isFeatured = true,
      isPopular = true
    ),
    CitizenService(
      id = "sim_biometric",
      serviceCode = "MOB-01",
      titleBn = "সিম বায়োমেট্রিক",
      titleEn = "SIM Biometric Verification Status",
      category = ServiceCategory.MOBILE_SERVICE,
      descriptionBn = "NID এর বিপরীতে নিবন্ধিত সিম সংখ্যা ও বায়োমেট্রিক তথ্য যাচাই।",
      officialFeeBdt = 0,
      serviceChargeBdt = 49,
      officialPortalUrl = "https://btrc.gov.bd",
      estimatedDays = "তাৎক্ষণিক",
      iconKey = "sim",
      isFeatured = true,
      isPopular = true
    ),
    CitizenService(
      id = "call_list_3m",
      serviceCode = "MOB-02",
      titleBn = "৩ মাস কল লিস্ট",
      titleEn = "3 Months Call History Log",
      category = ServiceCategory.MOBILE_SERVICE,
      descriptionBn = "বৈধ মালিকানার মোবাইল নম্বরের ইনকামিং ও আউটগোয়িং কল রেকর্ড হিস্ট্রি।",
      officialFeeBdt = 0,
      serviceChargeBdt = 620,
      officialPortalUrl = "https://btrc.gov.bd",
      estimatedDays = "২-৪ ঘণ্টা",
      iconKey = "phone_call",
      isFeatured = true,
      isPopular = true
    ),
    CitizenService(
      id = "call_list_6m",
      serviceCode = "MOB-03",
      titleBn = "৬ মাস কল লিস্ট",
      titleEn = "6 Months Call History Log",
      category = ServiceCategory.MOBILE_SERVICE,
      descriptionBn = "গত ৬ মাসের বিস্তারিত কল তালিকা ও সময়কাল তথ্য রিপোর্ট।",
      officialFeeBdt = 0,
      serviceChargeBdt = 950,
      officialPortalUrl = "https://btrc.gov.bd",
      estimatedDays = "২-৪ ঘণ্টা",
      iconKey = "phone_call",
      isFeatured = true,
      isPopular = true
    ),
    CitizenService(
      id = "sms_list_3m",
      serviceCode = "MOB-04",
      titleBn = "৩ মাস SMS লিস্ট",
      titleEn = "3 Months SMS History Log",
      category = ServiceCategory.MOBILE_SERVICE,
      descriptionBn = "আগত ও প্রেরিত এসএমএস এর বিস্তারিত সময় ও নম্বর তালিকা।",
      officialFeeBdt = 0,
      serviceChargeBdt = 800,
      officialPortalUrl = "https://btrc.gov.bd",
      estimatedDays = "২-৪ ঘণ্টা",
      iconKey = "sms",
      isFeatured = true,
      isPopular = true
    ),

    // Reference Screenshot 2: মোবাইল ব্যাংকিং ও লোকেশন
    CitizenService(
      id = "imei_to_num",
      serviceCode = "MOB-05",
      titleBn = "IMEI টু নাম্বার",
      titleEn = "IMEI to Mobile Number Match",
      category = ServiceCategory.MOBILE_SERVICE,
      descriptionBn = "হারানো বা চুরি হওয়া হ্যান্ডসেটের IMEI এর বর্তমান সক্রিয় সিম অনুসন্ধান।",
      officialFeeBdt = 0,
      serviceChargeBdt = 210,
      officialPortalUrl = "https://neir.btrc.gov.bd",
      estimatedDays = "১-২ ঘণ্টা",
      iconKey = "mobile",
      isFeatured = true,
      isPopular = true
    ),
    CitizenService(
      id = "bkash_info",
      serviceCode = "MFS-01",
      titleBn = "বিকাশ তথ্য",
      titleEn = "bKash Account Info Verification",
      category = ServiceCategory.MOBILE_SERVICE,
      descriptionBn = "বিকাশ অ্যাকাউন্টের নাম ও NID তথ্যের সঠিকতা যাচাই ও অনুসন্ধান।",
      officialFeeBdt = 0,
      serviceChargeBdt = 680,
      officialPortalUrl = "https://bkash.com",
      estimatedDays = "৩০ মিনিট",
      iconKey = "money_bag",
      isFeatured = true,
      isPopular = true
    ),
    CitizenService(
      id = "nagad_info",
      serviceCode = "MFS-02",
      titleBn = "নগদ তথ্য",
      titleEn = "Nagad Account Info Verification",
      category = ServiceCategory.MOBILE_SERVICE,
      descriptionBn = "নগদ অ্যাকাউন্টের গ্রাহক নাম ও অ্যাকাউন্ট সক্রিয়তা যাচাই।",
      officialFeeBdt = 0,
      serviceChargeBdt = 680,
      officialPortalUrl = "https://nagad.com.bd",
      estimatedDays = "৩০ মিনিট",
      iconKey = "flying_money",
      isFeatured = true,
      isPopular = true
    ),
    CitizenService(
      id = "rocket_info",
      serviceCode = "MFS-03",
      titleBn = "রকেট তথ্য",
      titleEn = "Rocket Account Info Verification",
      category = ServiceCategory.MOBILE_SERVICE,
      descriptionBn = "ডাচ-বাংলা রকেট অ্যাকাউন্টের গ্রাহক ও কোর ব্যাংকিং তথ্য যাচাই।",
      officialFeeBdt = 0,
      serviceChargeBdt = 680,
      officialPortalUrl = "https://dutchbanglabank.com/rocket",
      estimatedDays = "৩০ মিনিট",
      iconKey = "rocket",
      isFeatured = true,
      isPopular = true
    ),
    CitizenService(
      id = "num_to_location",
      serviceCode = "LOC-01",
      titleBn = "নাম্বার টু লোকেশন",
      titleEn = "Number to BTS Location",
      category = ServiceCategory.MOBILE_SERVICE,
      descriptionBn = "বিটিএস টাওয়ার ভিত্তিক মোবাইল নম্বরের বর্তমান থানা ও জেলা লোকেশন।",
      officialFeeBdt = 0,
      serviceChargeBdt = 170,
      officialPortalUrl = "https://btrc.gov.bd",
      estimatedDays = "৩০ মিনিট",
      iconKey = "pin",
      isFeatured = true,
      isPopular = true
    ),
    CitizenService(
      id = "live_location",
      serviceCode = "LOC-02",
      titleBn = "লাইভ লোকেশন",
      titleEn = "Real-time Live Location Map",
      category = ServiceCategory.MOBILE_SERVICE,
      descriptionBn = "অনুমোদিত নেটওয়ার্ক ট্রায়াঙ্গুলেশন ভিত্তিক তাৎক্ষণিক লাইভ অবস্থান।",
      officialFeeBdt = 0,
      serviceChargeBdt = 250,
      officialPortalUrl = "https://btrc.gov.bd",
      estimatedDays = "১৫ মিনিট",
      iconKey = "map",
      isFeatured = true,
      isPopular = true
    ),
    CitizenService(
      id = "passport_apply",
      serviceCode = "EP-01",
      titleBn = "পাসপোর্ট ও ভিসা আবেদন",
      titleEn = "e-Passport & Visa Application Support",
      category = ServiceCategory.PASSPORT_VISA,
      descriptionBn = "ই-পাসপোর্ট নতুন ও রি-ইস্যু অনলাইন ফরম পূরণ ও চালান জমা।",
      officialFeeBdt = 0,
      serviceChargeBdt = 450,
      officialPortalUrl = "https://epassport.gov.bd",
      estimatedDays = "২৪ ঘণ্টা",
      timerBadge = "২৪ ঘণ্টা",
      iconKey = "flight",
      isFeatured = true,
      isPopular = true
    ),
    CitizenService(
      id = "police_clearance",
      serviceCode = "PC-01",
      titleBn = "পুলিশ ক্লিয়ারেন্স অনলাইন",
      titleEn = "Police Clearance Certificate Online",
      category = ServiceCategory.PASSPORT_VISA,
      descriptionBn = "বিদেশ গমন ও ভিসার জন্য বাংলাদেশ পুলিশের অনলাইন আবেদন।",
      officialFeeBdt = 0,
      serviceChargeBdt = 350,
      officialPortalUrl = "https://pcc.police.gov.bd",
      estimatedDays = "৩-৫ কার্যদিবস",
      iconKey = "police",
      isFeatured = true,
      isPopular = true
    ),
    // Digital Studio & Online Marketplace
    CitizenService(
      id = "cv_biodata",
      serviceCode = "DS-01",
      titleBn = "CV ও বায়োডাটা তৈরি",
      titleEn = "Professional CV & Biodata Design",
      category = ServiceCategory.DIGITAL_STUDIO,
      descriptionBn = "চাকরি ও স্কলারশিপের জন্য আন্তর্জাতিক মানের স্মার্ট বাংলা ও ইংরেজি সিভি।",
      officialFeeBdt = 0,
      serviceChargeBdt = 80,
      officialPortalUrl = "",
      estimatedDays = "৩০ মিনিট",
      timerBadge = "৩০ মিনিট",
      iconKey = "document",
      isFeatured = false,
      isPopular = true
    ),
    CitizenService(
      id = "photo_passport_edit",
      serviceCode = "DS-02",
      titleBn = "পাসপোর্ট সাইজ ছবি ও এডিট",
      titleEn = "Passport Photo & BG Remove",
      category = ServiceCategory.DIGITAL_STUDIO,
      descriptionBn = "অফিসিয়াল নীল বা সাদা ব্যাকগ্রাউন্ডসহ ঝকঝকে পাসপোর্ট ও স্ট্যাম্প সাইজ ছবি প্রস্তুত।",
      officialFeeBdt = 0,
      serviceChargeBdt = 50,
      officialPortalUrl = "",
      estimatedDays = "১৫ মিনিট",
      timerBadge = "১৫ মিনিট",
      iconKey = "card_profile",
      isFeatured = false,
      isPopular = true
    ),
    CitizenService(
      id = "banner_logo_design",
      serviceCode = "DS-03",
      titleBn = "লোগো ও ব্যানার ডিজাইন",
      titleEn = "Creative Logo & Social Banner",
      category = ServiceCategory.DIGITAL_STUDIO,
      descriptionBn = "ফেসবুক পেজ ব্যানার, ইউটিউব থাম্বনেইল ও দোকানের আকর্ষণীয় লোগো ডিজাইন।",
      officialFeeBdt = 0,
      serviceChargeBdt = 350,
      officialPortalUrl = "",
      estimatedDays = "১ দিন",
      timerBadge = "২৪ ঘণ্টা",
      iconKey = "studio",
      isFeatured = false,
      isPopular = false
    ),
    CitizenService(
      id = "mug_tshirt_print",
      serviceCode = "DS-04",
      titleBn = "মগ ও টি-শার্ট কাস্টম প্রিন্ট",
      titleEn = "Custom Mug & T-Shirt Printing",
      category = ServiceCategory.ECOMMERCE_SHOP,
      descriptionBn = "ব্যক্তিগত ছবি ও লোগো সংবলিত সিরামিক মগ এবং প্রিমিয়াম টি-শার্ট প্রিন্ট অর্ডার।",
      officialFeeBdt = 0,
      serviceChargeBdt = 250,
      officialPortalUrl = "",
      estimatedDays = "২-৩ দিন",
      timerBadge = "২ দিন",
      iconKey = "shop",
      isFeatured = false,
      isPopular = false
    )
  )

  val socialMediaLinks = listOf(
    com.example.data.model.SocialMediaLink(
      id = "sm_whatsapp",
      platform = "WhatsApp",
      displayNameBn = "হোয়াটসঅ্যাপ সাপোর্ট",
      url = "https://wa.me/8801632042441",
      iconKey = "whatsapp",
      isEnabled = true,
      orderIndex = 1
    ),
    com.example.data.model.SocialMediaLink(
      id = "sm_facebook",
      platform = "Facebook",
      displayNameBn = "ফেসবুক অফিশিয়াল পেজ",
      url = "https://facebook.com/rongdhonudigitalstudio",
      iconKey = "facebook",
      isEnabled = true,
      orderIndex = 2
    ),
    com.example.data.model.SocialMediaLink(
      id = "sm_youtube",
      platform = "YouTube",
      displayNameBn = "ইউটিউব চ্যানেল",
      url = "https://youtube.com/@rongdhonustudio",
      iconKey = "youtube",
      isEnabled = true,
      orderIndex = 3
    ),
    com.example.data.model.SocialMediaLink(
      id = "sm_telegram",
      platform = "Telegram",
      displayNameBn = "টেলিগ্রাম হেল্পলাইন",
      url = "https://t.me/rongdhonuseva",
      iconKey = "telegram",
      isEnabled = true,
      orderIndex = 4
    ),
    com.example.data.model.SocialMediaLink(
      id = "sm_tiktok",
      platform = "TikTok",
      displayNameBn = "টিকটক অফিশিয়াল",
      url = "https://tiktok.com/@rongdhonustudio",
      iconKey = "tiktok",
      isEnabled = true,
      orderIndex = 5
    )
  )

  val verifiedProviders = listOf(
    com.example.data.model.ServiceProvider(
      id = "prov_1",
      nameBn = "মোঃ রেজাউল করিম",
      roleBn = "অনলাইন আবেদন বিশেষজ্ঞ",
      specialtyBn = "NID সংশোধন, ই-পাসপোর্ট ও চালান",
      districtBn = "রংপুর সদর",
      phone = "01632-042441",
      rating = 4.9f,
      verified = true,
      completedAssists = 340
    ),
    com.example.data.model.ServiceProvider(
      id = "prov_2",
      nameBn = "রংধনু ডিজিটাল ডেস্ক",
      roleBn = "সার্টিফাইড ভূমি সেবা সহকারী",
      specialtyBn = "ই-নামজারি, খতিয়ান ও মিসকেস",
      districtBn = "ঢাকা ও উত্তরবঙ্গ",
      phone = "01632-042441",
      rating = 4.8f,
      verified = true,
      completedAssists = 512
    ),
    com.example.data.model.ServiceProvider(
      id = "prov_3",
      nameBn = "আরিফুর রহমান",
      roleBn = "ট্যাক্স ও লিগ্যাল কনসালটেন্ট",
      specialtyBn = "ই-টিন, জিরো রিটার্ন ও ট্রেড লাইসেন্স",
      districtBn = "রংপুর / রাজশাহী",
      phone = "01632-042441",
      rating = 4.95f,
      verified = true,
      completedAssists = 275
    )
  )

  val initialPaymentRecords = listOf(
    CustomerPaymentRecord(
      paymentId = "PAY-2026-101",
      customerId = "CUST-0842",
      customerName = "মোঃ শরিফুল ইসলাম",
      customerPhone = "01712-345678",
      whatsappNumber = "01712-345678",
      orderNumber = "ORD-94812",
      serviceTitleBn = "জরুরি NID সংশোধন ও প্রিন্ট",
      servicePriceBdt = 350,
      amountSentBdt = 350,
      verifiedAmountBdt = 350,
      paymentMethod = PaymentMethod.BKASH,
      trxId = "BLX9827361",
      timestamp = System.currentTimeMillis() - 7200000L,
      status = PaymentVerificationStatus.FULLY_PAID,
      assignedAdmin = "মোঃ রেজাউল করিম",
      notes = "বিকাশ মার্চেন্ট স্টেটমেন্টে লেনদেন নিশ্চিত করা হয়েছে।"
    ),
    CustomerPaymentRecord(
      paymentId = "PAY-2026-102",
      customerId = "CUST-0915",
      customerName = "তাহমিনা আক্তার",
      customerPhone = "01823-456789",
      whatsappNumber = "01823-456789",
      orderNumber = "ORD-83921",
      serviceTitleBn = "ই-পাসপোর্ট নতুন ও রি-ইস্যু",
      servicePriceBdt = 450,
      amountSentBdt = 300,
      verifiedAmountBdt = 300,
      paymentMethod = PaymentMethod.NAGAD,
      trxId = "NGD7362910",
      timestamp = System.currentTimeMillis() - 14400000L,
      status = PaymentVerificationStatus.PARTIALLY_PAID,
      assignedAdmin = "আরিফুর রহমান",
      notes = "আংশিক পেমেন্ট এসেছে। বাকি ১৫০ টাকা ফরম জমা দেওয়ার আগে প্রদেয়।"
    ),
    CustomerPaymentRecord(
      paymentId = "PAY-2026-103",
      customerId = "CUST-1024",
      customerName = "কবীর হোসেন",
      customerPhone = "01911-223344",
      whatsappNumber = "01911-223344",
      orderNumber = "ORD-71934",
      serviceTitleBn = "ই-নামজারি ও মিসকেস আবেদন",
      servicePriceBdt = 500,
      amountSentBdt = 500,
      verifiedAmountBdt = 0,
      paymentMethod = PaymentMethod.BKASH,
      trxId = "BK99283741",
      timestamp = System.currentTimeMillis() - 1800000L,
      status = PaymentVerificationStatus.SUBMITTED,
      assignedAdmin = "মোঃ রেজাউল করিম",
      notes = "গ্রাহক ট্রানজেকশন আইডি দিয়েছেন, ব্যাংক স্টেটমেন্ট যাচাই বাকি।"
    ),
    CustomerPaymentRecord(
      paymentId = "PAY-2026-104",
      customerId = "CUST-0711",
      customerName = "ফারজানা মিলি",
      customerPhone = "01644-556677",
      whatsappNumber = "01644-556677",
      orderNumber = "ORD-60291",
      serviceTitleBn = "অনলাইন জন্ম নিবন্ধন সংশোধন",
      servicePriceBdt = 200,
      amountSentBdt = 200,
      verifiedAmountBdt = 200,
      paymentMethod = PaymentMethod.ROCKET,
      trxId = "RCK8827104",
      timestamp = System.currentTimeMillis() - 86400000L,
      status = PaymentVerificationStatus.VERIFIED,
      assignedAdmin = "রংধনু ডিজিটাল ডেস্ক",
      notes = "রকেট লেনদেন সফল ও সরকারি চালানে প্রসেসড।"
    ),
    CustomerPaymentRecord(
      paymentId = "PAY-2026-105",
      customerId = "CUST-0532",
      customerName = "মোঃ আলমগীর",
      customerPhone = "01555-667788",
      whatsappNumber = "01555-667788",
      orderNumber = "ORD-51928",
      serviceTitleBn = "ট্রেড লাইসেন্স আবেদন",
      servicePriceBdt = 600,
      amountSentBdt = 600,
      verifiedAmountBdt = 600,
      paymentMethod = PaymentMethod.BKASH,
      trxId = "BK77651029",
      timestamp = System.currentTimeMillis() - 172800000L,
      status = PaymentVerificationStatus.REFUNDED,
      assignedAdmin = "আরিফুর রহমান",
      notes = "পৌরসভার একই ওয়ার্ডে ডুপ্লিকেট থাকায় আবেদন বাতিল।",
      refundTrxId = "BK-REF-30192",
      refundAmountBdt = 600,
      refundReason = "ডুপ্লিকেট ট্রেড লাইসেন্স শনাক্ত হওয়ায় সম্পূর্ণ রিফান্ড প্রদান করা হয়েছে।",
      isRefundProcessed = true
    ),
    CustomerPaymentRecord(
      paymentId = "PAY-2026-106",
      customerId = "CUST-1102",
      customerName = "সাকিব মাহমুদ",
      customerPhone = "01788-990011",
      whatsappNumber = "01788-990011",
      orderNumber = "ORD-41029",
      serviceTitleBn = "CV ও বায়োডাটা তৈরি (স্টুডিও)",
      servicePriceBdt = 80,
      amountSentBdt = 80,
      verifiedAmountBdt = 80,
      paymentMethod = PaymentMethod.WALLET,
      trxId = "WAL-40291",
      timestamp = System.currentTimeMillis() - 3600000L,
      status = PaymentVerificationStatus.FULLY_PAID,
      assignedAdmin = "রংধনু ডিজিটাল ডেস্ক",
      notes = "রংধনু ওয়ালেট থেকে পরিশোধিত।"
    )
  )

  val initialAppReleases = listOf(
    AppBuildRelease(
      id = "rel_android_apk",
      platform = AppPlatform.ANDROID_APK,
      version = "v2.4.0 (Build 240)",
      buildNumber = 240,
      fileName = "Rongdhonu-Online-Shop.apk",
      fileSize = "23.0 MB",
      releaseDate = "২০২৬-১০-২০",
      minOsRequirement = "Android 7.0+ (API 24+) Nougat",
      sha256Checksum = "7b8b1d3182f08742363702ede99dd731332554aa4f681a9ee23388502c6e503c",
      releaseNotesBn = "রংধনু অনলাইন শপ ও সেবা v2.4.0: গ্রাহক পেমেন্ট ড্যাশবোর্ড, ইনস্ট্যান্ট ভেরিফিকেশন, অফলাইন ক্যাশিং, সম্পূর্ণ নতুন কমপ্যাক্ট সার্ভিস টাইলস এবং নিরাপদ অ্যাডমিন কন্ট্রোল সিস্টেম।",
      downloadUrl = "app/build/outputs/apk/debug/app-debug.apk",
      isDownloadActive = true,
      downloadCount = 318
    ),
    AppBuildRelease(
      id = "rel_windows_exe",
      platform = AppPlatform.WINDOWS_EXE,
      version = "v2.4.0 (Windows Setup)",
      buildNumber = 240,
      fileName = "Rongdhonu-Online-Shop-Setup.exe",
      fileSize = "48.5 MB",
      releaseDate = "২০২৬-১০-২০",
      minOsRequirement = "Windows 10 / 11 64-bit (x64 / ARM64)",
      sha256Checksum = "4f7a93c8b9d0e21a8f3b1452a97c6d4821f0e9b3a726198c25e83610471295db",
      releaseNotesBn = "উইন্ডোজ ডেস্কটপ ইনস্টলার: সরাসরি ডেস্কটপ শর্টকাট, ব্যাকগ্রাউন্ড নোটিফিকেশন এবং প্রিন্ট সার্ভিসেস সমৃদ্ধ সংস্করণ।",
      downloadUrl = "https://ais-pre-cc4ga5ryj6zcgrjdyj6wkv-909641969027.asia-southeast1.run.app/downloads/Rongdhonu-Online-Shop-Setup.exe",
      isDownloadActive = true,
      downloadCount = 184
    ),
    AppBuildRelease(
      id = "rel_windows_zip",
      platform = AppPlatform.WINDOWS_ZIP,
      version = "v2.4.0 (Portable)",
      buildNumber = 240,
      fileName = "Rongdhonu-Online-Shop-Portable.zip",
      fileSize = "44.2 MB",
      releaseDate = "২০২৬-১০-২০",
      minOsRequirement = "Windows 10 / 11 64-bit (Portable - No install)",
      sha256Checksum = "9a2e6f4b81c03d775ea32b509f6e812d4a5b6c7e8f90123456789abcdef01234",
      releaseNotesBn = "পোর্টেবল জিপ সংস্করণ: পেনড্রাইভ বা যেকোনো ফোল্ডার থেকে সরাসরি আনজিপ করে তাৎক্ষণিক ব্যবহারযোগ্য।",
      downloadUrl = "https://ais-pre-cc4ga5ryj6zcgrjdyj6wkv-909641969027.asia-southeast1.run.app/downloads/Rongdhonu-Online-Shop-Portable.zip",
      isDownloadActive = true,
      downloadCount = 97
    ),
    AppBuildRelease(
      id = "rel_pwa_web",
      platform = AppPlatform.PWA_WEB,
      version = "v2.4.0 (Cloud PWA)",
      buildNumber = 240,
      fileName = "Rongdhonu Web App (Cloud PWA)",
      fileSize = "ক্লাউড ইনস্টলেবল (Cloud-hosted)",
      releaseDate = "২০২৬-১০-২০",
      minOsRequirement = "যেকোনো আধুনিক ব্রাউজার (Chrome, Edge, Safari)",
      sha256Checksum = "SSL TLS 1.3 Certified SHA-256",
      releaseNotesBn = "ক্লাউড পিডব্লিউএ: কোনো ডাউনলোড ছাড়া যেকোনো মোবাইল বা কম্পিউটারের ব্রাউজারে সম্পূর্ণ প্ল্যাটফর্ম পরিচালনা।",
      downloadUrl = "https://ais-pre-cc4ga5ryj6zcgrjdyj6wkv-909641969027.asia-southeast1.run.app",
      isDownloadActive = true,
      downloadCount = 840
    )
  )

  val initialCustomers = listOf(
    com.example.data.model.CustomerRecord(
      id = "CUST-0842",
      name = "মোঃ শরিফুল ইসলাম",
      phone = "01712-345678",
      email = "shariful.islam@gmail.com",
      address = "পায়রা চত্বর, রংপুর সদর",
      walletBalanceBdt = 850,
      totalOrdersCount = 4,
      totalSpentBdt = 2450,
      status = com.example.data.model.CustomerStatus.VIP,
      specialDiscountPercent = 10,
      adminNotes = "স্টুডিওর নিয়মিত VIP গ্রাহক। জরুরি NID ও পাসপোর্ট সার্ভিস গ্রহণকারী।",
      registeredDate = "২০২৫-১১-১২"
    ),
    com.example.data.model.CustomerRecord(
      id = "CUST-0915",
      name = "তাহমিনা আক্তার",
      phone = "01823-456789",
      email = "tahmina.ak@gmail.com",
      address = "মডার্ন মোড়, রংপুর",
      walletBalanceBdt = 400,
      totalOrdersCount = 2,
      totalSpentBdt = 1200,
      status = com.example.data.model.CustomerStatus.ACTIVE,
      specialDiscountPercent = 5,
      adminNotes = "ই-পাসপোর্ট নতুন ও জন্ম নিবন্ধন সংশোধন সম্পন্ন।",
      registeredDate = "২০২৬-০১-০৫"
    ),
    com.example.data.model.CustomerRecord(
      id = "CUST-1024",
      name = "কবীর হোসেন",
      phone = "01911-223344",
      email = "kabir.hossain@yahoo.com",
      address = "কাচারী বাজার, রংপুর",
      walletBalanceBdt = 120,
      totalOrdersCount = 3,
      totalSpentBdt = 1750,
      status = com.example.data.model.CustomerStatus.ACTIVE,
      specialDiscountPercent = 0,
      adminNotes = "ভূমি নামজারি ও মিসকেস ক্লায়েন্ট।",
      registeredDate = "২০২৬-০২-১৮"
    ),
    com.example.data.model.CustomerRecord(
      id = "CUST-0711",
      name = "ফারজানা মিলি",
      phone = "01644-556677",
      email = "farzana.mili@gmail.com",
      address = "ধাপ, রংপুর",
      walletBalanceBdt = 650,
      totalOrdersCount = 5,
      totalSpentBdt = 3200,
      status = com.example.data.model.CustomerStatus.VIP,
      specialDiscountPercent = 15,
      adminNotes = "পরিবারের সবার জন্ম সনদ ও অনলাইন ভেরিফিকেশন সম্পন্ন।",
      registeredDate = "২০২৫-০৮-২০"
    ),
    com.example.data.model.CustomerRecord(
      id = "CUST-1102",
      name = "সাকিব মাহমুদ",
      phone = "01788-990011",
      email = "sakib.mah@outlook.com",
      address = "জাহাজ কোম্পানি মোড়, রংপুর",
      walletBalanceBdt = 50,
      totalOrdersCount = 1,
      totalSpentBdt = 80,
      status = com.example.data.model.CustomerStatus.ACTIVE,
      specialDiscountPercent = 0,
      adminNotes = "CV ও অনলাইন আবেদন সম্পন্ন।",
      registeredDate = "২০২৬-০৩-১০"
    )
  )

  val initialBroadcastNotices = listOf(
    com.example.data.model.BroadcastNotice(
      id = "NOTC-101",
      titleBn = "জরুরি NID সার্ভার রক্ষণাবেক্ষণ নোটিশ",
      messageBn = "আগামীকাল রাত ১২টা থেকে সকাল ৬টা পর্যন্ত জাতীয় সার্ভারে রুটিন আপগ্রেড চলবে। সকল জরুরি আবেদন আজই সম্পন্ন করার অনুরোধ করা হচ্ছে।",
      date = "২০২৬-১০-২০",
      isUrgent = true,
      isActive = true
    ),
    com.example.data.model.BroadcastNotice(
      id = "NOTC-102",
      titleBn = "রংধনু অনলাইন শপ ও পিসি অ্যাপের নতুন রিলিজ v2.4.0",
      messageBn = "এখন আপনার অ্যান্ড্রয়েড মোবাইল ও উইন্ডোজ কম্পিউটারে সরাসরি অ্যাপ ডাউনলোড করে অতি দ্রুত সব সেবা গ্রহণ করতে পারবেন।",
      date = "২০২৬-১০-১৯",
      isUrgent = false,
      isActive = true
    )
  )

  val initialAdminSessions = listOf(
    com.example.data.model.AdminSession(
      id = "SES-01",
      deviceName = "Admin Android Studio / Streaming Emulator",
      ipAddress = "103.145.118.24",
      location = "ঢাকা / রংপুর, বাংলাদেশ",
      loginTime = System.currentTimeMillis() - 1800000L,
      isCurrent = true
    ),
    com.example.data.model.AdminSession(
      id = "SES-02",
      deviceName = "Studio Desktop Workstation (Windows 11 PC)",
      ipAddress = "103.145.118.25",
      location = "রংপুর স্টুডিও হেডকোয়ার্টার",
      loginTime = System.currentTimeMillis() - 86400000L,
      isCurrent = false
    )
  )

  val initialSecurityAlerts = listOf(
    com.example.data.model.SecurityAlert(
      id = "ALT-01",
      titleBn = "নতুন ডিভাইস থেকে সফল অ্যাডমিন লগইন",
      detailBn = "IP 103.145.118.24 থেকে সফলভাবে পাসকোড দিয়ে অ্যাডমিন প্যানেলে প্রবেশ করা হয়েছে।",
      severity = "Info",
      timestamp = System.currentTimeMillis() - 1800000L
    ),
    com.example.data.model.SecurityAlert(
      id = "ALT-02",
      titleBn = "2FA সিকিউরিটি গার্ড সচল রয়েছে",
      detailBn = "অ্যাডমিন প্যানেল টু-ফ্যাক্টর অথেনটিকেশন ও অ্যাক্টিভিটি অডিট সিস্টেম সক্রিয়।",
      severity = "Medium",
      timestamp = System.currentTimeMillis() - 86400000L
    )
  )
}

