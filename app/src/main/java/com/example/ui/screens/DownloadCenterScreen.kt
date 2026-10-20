package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.FolderZip
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.AppBuildRelease
import com.example.data.model.AppPlatform
import com.example.ui.RongdhonuViewModel
import com.example.ui.theme.RongdhonuGreen
import com.example.ui.theme.RongdhonuPurpleDark
import com.example.ui.theme.RongdhonuPurplePrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloadCenterScreen(
  viewModel: RongdhonuViewModel,
  onBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  BackHandler { onBack() }
  val context = LocalContext.current
  val releases by viewModel.repository.appReleases.collectAsState()
  var selectedGuidePlatform by remember { mutableStateOf<AppPlatform?>(null) }

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Column {
            Text(
              text = "অ্যাপ ডাউনলোড সেন্টার",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
              color = Color.White
            )
            Text(
              text = "Android • Windows • Web PWA",
              style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
              color = Color.White.copy(alpha = 0.85f)
            )
          }
        },
        navigationIcon = {
          IconButton(onClick = onBack) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back",
              tint = Color.White
            )
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = RongdhonuPurpleDark)
      )
    },
    modifier = modifier
  ) { paddingValues ->
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFF8F6FD))
        .padding(paddingValues)
        .padding(horizontal = 16.dp),
      contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      // Header Banner
      item {
        Card(
          shape = RoundedCornerShape(18.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                modifier = Modifier
                  .size(44.dp)
                  .clip(CircleShape)
                  .background(RongdhonuPurplePrimary.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
              ) {
                Icon(
                  imageVector = Icons.Default.Download,
                  contentDescription = null,
                  tint = RongdhonuPurplePrimary,
                  modifier = Modifier.size(24.dp)
                )
              }
              Spacer(modifier = Modifier.width(12.dp))
              Column {
                Text(
                  text = "রংধনু অফিশিয়াল অ্যাপ ডাউনলোড",
                  style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                  color = Color(0xFF1E1738)
                )
                Text(
                  text = "পাবলিক স্টোর ছাড়াও সরাসরি ইনস্টল ও ব্যবহারযোগ্য",
                  style = MaterialTheme.typography.bodySmall,
                  color = Color(0xFF6B6580)
                )
              }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color(0xFFF0EBF7))
            Spacer(modifier = Modifier.height(12.dp))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                  imageVector = Icons.Default.Security,
                  contentDescription = null,
                  tint = RongdhonuGreen,
                  modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "SHA-256 সিকিউরড ভেরিফায়েড ফাইল",
                  style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                  color = RongdhonuGreen
                )
              }

              Surface(
                color = Color(0xFFEDE7F6),
                shape = RoundedCornerShape(8.dp)
              ) {
                Text(
                  text = "v2.4.0 সক্রিয়",
                  style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = RongdhonuPurplePrimary
                  ),
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                )
              }
            }
          }
        }
      }

      // App Release Cards
      items(releases) { release ->
        AppReleaseDownloadCard(
          release = release,
          onDownload = {
            viewModel.recordDownload(release.id)
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(release.downloadUrl))
            try {
              context.startActivity(intent)
            } catch (e: Exception) {
              Toast.makeText(context, "${release.platform.titleBn} ডাউনলোড লিঙ্ক কপি করা হয়েছে!", Toast.LENGTH_SHORT).show()
            }
          },
          onShowGuide = { selectedGuidePlatform = release.platform },
          onCopyChecksum = {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.setPrimaryClip(ClipData.newPlainText("SHA256", release.sha256Checksum))
            Toast.makeText(context, "SHA-256 চেকসাম কপি করা হয়েছে!", Toast.LENGTH_SHORT).show()
          }
        )
      }
    }
  }

  // Installation Guide Dialog
  if (selectedGuidePlatform != null) {
    InstallationGuideDialog(
      platform = selectedGuidePlatform!!,
      onDismiss = { selectedGuidePlatform = null }
    )
  }
}

@Composable
fun AppReleaseDownloadCard(
  release: AppBuildRelease,
  onDownload: () -> Unit,
  onShowGuide: () -> Unit,
  onCopyChecksum: () -> Unit
) {
  val (icon, badgeColor) = when (release.platform) {
    AppPlatform.ANDROID_APK -> Pair(Icons.Default.Android, Color(0xFF2E7D32))
    AppPlatform.WINDOWS_EXE -> Pair(Icons.Default.Computer, Color(0xFF0277BD))
    AppPlatform.WINDOWS_ZIP -> Pair(Icons.Default.FolderZip, Color(0xFFE65100))
    AppPlatform.PWA_WEB -> Pair(Icons.Default.Language, Color(0xFF6A1B9A))
  }

  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = Modifier
      .fillMaxWidth()
      .border(1.dp, Color(0xFFECE6F5), RoundedCornerShape(16.dp))
      .testTag("download_card_${release.platform.name.lowercase()}")
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      // Header: Platform Icon + Title + Version
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
      ) {
        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(42.dp)
              .clip(RoundedCornerShape(12.dp))
              .background(badgeColor.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = icon,
              contentDescription = null,
              tint = badgeColor,
              modifier = Modifier.size(24.dp)
            )
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(
              text = release.platform.titleBn,
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
              color = Color(0xFF1E1738)
            )
            Text(
              text = "${release.fileName} • ${release.fileSize}",
              style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
              color = Color(0xFF6B6580)
            )
          }
        }

        Surface(
          color = if (release.isDownloadActive) Color(0xFFE8F5E9) else Color(0xFFFFEBEE),
          shape = RoundedCornerShape(8.dp)
        ) {
          Text(
            text = if (release.isDownloadActive) "সক্রিয় রিলিজ" else "সাময়িক বন্ধ",
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 10.sp,
              color = if (release.isDownloadActive) Color(0xFF2E7D32) else Color(0xFFC62828)
            ),
            modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Release Notes
      Text(
        text = release.releaseNotesBn,
        style = MaterialTheme.typography.bodySmall.copy(lineHeight = 18.sp),
        color = Color(0xFF423B58)
      )

      Spacer(modifier = Modifier.height(10.dp))

      // Technical Details Pill Row
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Surface(color = Color(0xFFF4F0FA), shape = RoundedCornerShape(6.dp)) {
          Text(
            text = "ভার্সন: ${release.version}",
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
            color = Color(0xFF5B239C),
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.5.dp)
          )
        }

        Surface(color = Color(0xFFF4F0FA), shape = RoundedCornerShape(6.dp)) {
          Text(
            text = release.minOsRequirement,
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
            color = Color(0xFF5B239C),
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.5.dp)
          )
        }

        Surface(color = Color(0xFFF4F0FA), shape = RoundedCornerShape(6.dp)) {
          Text(
            text = "${release.downloadCount} বার ডাউনলোড",
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
            color = Color(0xFF5B239C),
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.5.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Checksum Box with Copy
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .background(Color(0xFFF8F7FC), RoundedCornerShape(8.dp))
          .border(1.dp, Color(0xFFEDE8F5), RoundedCornerShape(8.dp))
          .padding(horizontal = 10.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Text(
            text = "SHA-256 সিকিউরিটি চেকসাম:",
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp, fontWeight = FontWeight.Bold),
            color = Color(0xFF756F84)
          )
          Text(
            text = release.sha256Checksum,
            style = MaterialTheme.typography.labelSmall.copy(
              fontFamily = FontFamily.Monospace,
              fontSize = 9.5.sp
            ),
            color = Color(0xFF2C2442),
            maxLines = 1
          )
        }

        IconButton(
          onClick = onCopyChecksum,
          modifier = Modifier.size(28.dp)
        ) {
          Icon(
            imageVector = Icons.Default.ContentCopy,
            contentDescription = "Copy SHA-256",
            tint = RongdhonuPurplePrimary,
            modifier = Modifier.size(16.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Action Buttons: Download + Installation Guide
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        Button(
          onClick = onDownload,
          enabled = release.isDownloadActive,
          shape = RoundedCornerShape(10.dp),
          colors = ButtonDefaults.buttonColors(containerColor = RongdhonuPurplePrimary),
          modifier = Modifier.weight(1f)
        ) {
          Icon(
            imageVector = if (release.platform == AppPlatform.PWA_WEB) Icons.Default.OpenInBrowser else Icons.Default.Download,
            contentDescription = null,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = if (release.platform == AppPlatform.PWA_WEB) "ওয়েব অ্যাপ খুলুন" else "ডাউনলোড করুন",
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
          )
        }

        OutlinedButton(
          onClick = onShowGuide,
          shape = RoundedCornerShape(10.dp),
          colors = ButtonDefaults.outlinedButtonColors(contentColor = RongdhonuPurplePrimary)
        ) {
          Icon(
            imageVector = Icons.Default.HelpOutline,
            contentDescription = null,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(text = "ইনস্টল গাইড", style = MaterialTheme.typography.labelMedium)
        }
      }
    }
  }
}

@Composable
fun InstallationGuideDialog(
  platform: AppPlatform,
  onDismiss: () -> Unit
) {
  Dialog(onDismissRequest = onDismiss) {
    Card(
      shape = RoundedCornerShape(20.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
    ) {
      Column(modifier = Modifier.padding(20.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "${platform.titleBn} ইনস্টলেশন গাইড",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = RongdhonuPurplePrimary
          )
          IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Close")
          }
        }

        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider(color = Color(0xFFF0EBF7))
        Spacer(modifier = Modifier.height(12.dp))

        when (platform) {
          AppPlatform.ANDROID_APK -> {
            GuideStepItem(
              stepNumber = "১",
              title = "APK ফাইল ডাউনলোড",
              detail = "'ডাউনলোড করুন' বাটনে চাপ দিয়ে Rongdhonu-Online-Shop.apk ফাইলটি ডিভাইসে সেভ করুন।"
            )
            GuideStepItem(
              stepNumber = "২",
              title = "Unknown Sources অনুমতি",
              detail = "ডাউনলোড শেষে ফাইলে ক্লিক করুন। সেটিংস থেকে 'Install unknown apps' বা 'অপরিচিত উৎস থেকে ইনস্টল' চালু করুন।"
            )
            GuideStepItem(
              stepNumber = "৩",
              title = "Google Play Protect বার্তা",
              detail = "যদি 'Blocked by Play Protect' দেখা যায়, তবে 'More details' বা 'বিস্তারিত' এ ক্লিক করে 'Install anyway' সিলেক্ট করুন।"
            )
            GuideStepItem(
              stepNumber = "৪",
              title = "অ্যাপ চালু ও ব্যবহার",
              detail = "ইনস্টলেশন সফল হলে 'Open' বা হোম স্ক্রিন থেকে সরাসরি 'রংধনু সেবা' আইকনে ক্লিক করে উপভোগ করুন।"
            )
          }

          AppPlatform.WINDOWS_EXE, AppPlatform.WINDOWS_ZIP -> {
            GuideStepItem(
              stepNumber = "১",
              title = "ইনস্টলার / জিপ ডাউনলোড",
              detail = "কম্পিউটারে Setup.exe বা Portable.zip ফাইলটি ডাউনলোড করুন।"
            )
            GuideStepItem(
              stepNumber = "২",
              title = "Windows SmartScreen নোটিশ",
              detail = "যদি 'Windows protected your PC' স্ক্রিন আসে, তবে 'More info' লিংকে ক্লিক করে 'Run anyway' বাটনে চাপুন।"
            )
            GuideStepItem(
              stepNumber = "৩",
              title = "ডেস্কটপ শর্টকাট",
              detail = "ইনস্টলারটি স্বয়ংক্রিয়ভাবে ডেস্কটপ ও স্টার্ট মেনুতে Rongdhonu Online Shop শর্টকাট তৈরি করবে।"
            )
          }

          AppPlatform.PWA_WEB -> {
            GuideStepItem(
              stepNumber = "১",
              title = "ব্রাউজারে ওপেন",
              detail = "Chrome, Edge বা Safari দিয়ে ক্লাউড লিংকটিতে প্রবেশ করুন।"
            )
            GuideStepItem(
              stepNumber = "২",
              title = "হোম স্ক্রিনে ইনস্টল",
              detail = "ব্রাউজারের ৩-ডট মেনু চাপুন এবং 'Install App' বা 'Add to Home Screen' নির্বাচন করুন।"
            )
            GuideStepItem(
              stepNumber = "৩",
              title = "নেটিভ ফিল",
              detail = "কোনো ডাউনলোড বা মেগাবাইট খরচ ছাড়া ফোন ও পিসিতে সরাসরি সাধারণ অ্যাপের মতোই চলবে।"
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
          onClick = onDismiss,
          shape = RoundedCornerShape(10.dp),
          colors = ButtonDefaults.buttonColors(containerColor = RongdhonuPurplePrimary),
          modifier = Modifier.fillMaxWidth()
        ) {
          Text("বুঝেছি, ধন্যবাদ", style = MaterialTheme.typography.labelLarge)
        }
      }
    }
  }
}

@Composable
fun GuideStepItem(
  stepNumber: String,
  title: String,
  detail: String
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 6.dp),
    verticalAlignment = Alignment.Top
  ) {
    Box(
      modifier = Modifier
        .size(24.dp)
        .clip(CircleShape)
        .background(RongdhonuPurplePrimary),
      contentAlignment = Alignment.Center
    ) {
      Text(
        text = stepNumber,
        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = Color.White)
      )
    }
    Spacer(modifier = Modifier.width(10.dp))
    Column(modifier = Modifier.weight(1f)) {
      Text(
        text = title,
        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
        color = Color(0xFF1E1738)
      )
      Text(
        text = detail,
        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.5.sp),
        color = Color(0xFF6B6580)
      )
    }
  }
}
