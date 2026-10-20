package com.example.ui.screens

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.RongdhonuViewModel
import com.example.ui.theme.RongdhonuPurpleLight
import com.example.ui.theme.RongdhonuPurplePrimary

@Composable
fun ProfileScreen(
  viewModel: RongdhonuViewModel,
  onOpenAdmin: () -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  var showTicketDialog by remember { mutableStateOf(false) }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    contentPadding = PaddingValues(top = 12.dp, bottom = 24.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // User Profile Header Card
    item {
      Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier.padding(16.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            modifier = Modifier
              .size(54.dp)
              .clip(CircleShape)
              .background(RongdhonuPurplePrimary),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Person,
              contentDescription = null,
              tint = Color.White,
              modifier = Modifier.size(30.dp)
            )
          }

          Spacer(modifier = Modifier.width(14.dp))

          Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = "সম্মানিত সেবাগ্রহীতা",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Icon(
                imageVector = Icons.Default.Verified,
                contentDescription = null,
                tint = Color(0xFF008947),
                modifier = Modifier.size(16.dp)
              )
            }
            Text(
              text = "রংধনু অনলাইন সেবা অ্যাকাউন্ট",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Surface(
              color = Color(0xFFE8F5E9),
              shape = RoundedCornerShape(6.dp),
              modifier = Modifier.padding(top = 4.dp)
            ) {
              Text(
                text = "সক্রিয় অ্যাকাউন্ট (Active Citizen)",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color(0xFF1B5E20)
                ),
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
          }
        }
      }
    }

    // Studio Official Contact Information Card
    item {
      Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "রংধনু ডিজিটাল স্টুডিওর তথ্য",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
          )
          Spacer(modifier = Modifier.height(12.dp))

          ContactInfoRow(
            icon = Icons.Default.Phone,
            iconColor = Color(0xFF25D366),
            title = "হোয়াটসঅ্যাপ / মোবাইল",
            detail = "01632-042441",
            actionLabel = "মেসেজ / কল",
            onAction = {
              val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/8801632042441"))
              try { context.startActivity(intent) } catch (e: Exception) {}
            }
          )

          ContactInfoRow(
            icon = Icons.Default.Email,
            iconColor = Color(0xFFEA4335),
            title = "অফিসিয়াল ইমেইল",
            detail = "rongdhonustudio00@gmail.com",
            actionLabel = "মেইল পাঠান",
            onAction = {
              val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:rongdhonustudio00@gmail.com"))
              try { context.startActivity(intent) } catch (e: Exception) {}
            }
          )

          ContactInfoRow(
            icon = Icons.Default.LocationOn,
            iconColor = RongdhonuPurplePrimary,
            title = "ঠিকানা ও সেবা কেন্দ্র",
            detail = "রংধনু ডিজিটাল স্টুডিও, বাংলাদেশ",
            actionLabel = "অনলাইন পোর্টাল",
            onAction = {}
          )
        }
      }
    }

    // Support Ticket & Help
    item {
      Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "গ্রাহক সহায়তা ও অভিযোগ",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
          )
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = "যেকোনো সেবা সম্পর্কে প্রশ্ন বা সহায়তা পেতে সরাসরি সাপোর্ট টিকেট খুলুন।",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
          Spacer(modifier = Modifier.height(12.dp))
          Button(
            onClick = { showTicketDialog = true },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = RongdhonuPurplePrimary),
            modifier = Modifier.fillMaxWidth().testTag("btn_open_support_ticket")
          ) {
            Icon(imageVector = Icons.Default.SupportAgent, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text("সহায়তা বার্তা / অভিযোগ পাঠান")
          }
        }
      }
    }

    // App Download Center Card
    item {
      Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
          .fillMaxWidth()
          .clickable { viewModel.openDownloadCenter() }
          .testTag("btn_profile_download_center")
      ) {
        Row(
          modifier = Modifier.padding(16.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
            Box(
              modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(Color(0xFFE8F5E9)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.Download,
                contentDescription = null,
                tint = Color(0xFF2E7D32),
                modifier = Modifier.size(24.dp)
              )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(
                text = "অ্যাপ ডাউনলোড সেন্টার",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFF1E1738)
              )
              Text(
                text = "Android APK ও Windows কম্পিউটার অ্যাপ",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = Color(0xFF2E7D32)
          )
        }
      }
    }

    // Admin Control Panel Shortcut
    item {
      Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E5F5)),
        modifier = Modifier
          .fillMaxWidth()
          .clickable { onOpenAdmin() }
          .testTag("btn_profile_admin_portal")
      ) {
        Row(
          modifier = Modifier.padding(16.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .background(RongdhonuPurplePrimary),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = Icons.Default.AdminPanelSettings,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
              )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(
                text = "অ্যাডমিন কন্ট্রোল প্যানেল",
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = RongdhonuPurplePrimary
              )
              Text(
                text = "সেবা পরিচালনা, অর্ডার ও গেটওয়ে নিয়ন্ত্রণ",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = RongdhonuPurplePrimary
          )
        }
      }
    }

    // App Version & Trust Notice
    item {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Text(
          text = "রংধনু অনলাইন সেবা • সংস্করণ ২.৪.০",
          style = MaterialTheme.typography.labelSmall,
          color = Color.Gray
        )
        Text(
          text = "নাগরিক সেবা ও নিরাপদ ডিজিটাল পেমেন্ট প্ল্যাটফর্ম",
          style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp),
          color = Color.Gray
        )
      }
    }
  }

  if (showTicketDialog) {
    SupportTicketDialog(
      onDismiss = { showTicketDialog = false },
      onSubmit = { subject, message, phone ->
        viewModel.submitSupportTicket(subject, message, phone)
        showTicketDialog = false
      }
    )
  }
}

@Composable
fun ContactInfoRow(
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  iconColor: Color,
  title: String,
  detail: String,
  actionLabel: String,
  onAction: () -> Unit
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 8.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
      Box(
        modifier = Modifier
          .size(36.dp)
          .clip(CircleShape)
          .background(iconColor.copy(alpha = 0.15f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(imageVector = icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(18.dp))
      }
      Spacer(modifier = Modifier.width(10.dp))
      Column {
        Text(text = title, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        Text(text = detail, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold))
      }
    }

    Surface(
      shape = RoundedCornerShape(8.dp),
      color = RongdhonuPurplePrimary.copy(alpha = 0.08f),
      modifier = Modifier.clickable { onAction() }
    ) {
      Text(
        text = actionLabel,
        style = MaterialTheme.typography.labelSmall.copy(
          fontWeight = FontWeight.Bold,
          color = RongdhonuPurplePrimary
        ),
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
      )
    }
  }
}

@Composable
fun SupportTicketDialog(
  onDismiss: () -> Unit,
  onSubmit: (String, String, String) -> Unit
) {
  var subject by remember { mutableStateOf("") }
  var phone by remember { mutableStateOf("") }
  var message by remember { mutableStateOf("") }

  Dialog(onDismissRequest = onDismiss) {
    Card(
      shape = RoundedCornerShape(16.dp),
      colors = CardDefaults.cardColors(containerColor = Color.White),
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        Text(
          text = "নতুন সহায়তা বার্তা পাঠান",
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
          color = RongdhonuPurplePrimary
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
          value = subject,
          onValueChange = { subject = it },
          label = { Text("বিষয় *") },
          placeholder = { Text("যেমন: জন্ম নিবন্ধন নিয়ে প্রশ্ন") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth().testTag("input_ticket_subject")
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
          value = phone,
          onValueChange = { phone = it },
          label = { Text("মোবাইল নম্বর *") },
          singleLine = true,
          modifier = Modifier.fillMaxWidth().testTag("input_ticket_phone")
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
          value = message,
          onValueChange = { message = it },
          label = { Text("বিস্তারিত বার্তা *") },
          maxLines = 4,
          modifier = Modifier.fillMaxWidth().testTag("input_ticket_message")
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.End
        ) {
          Button(
            onClick = onDismiss,
            colors = ButtonDefaults.outlinedButtonColors()
          ) {
            Text("বাতিল")
          }
          Spacer(modifier = Modifier.width(8.dp))
          Button(
            onClick = {
              if (subject.isNotBlank() && message.isNotBlank()) {
                onSubmit(subject, message, phone)
              }
            },
            colors = ButtonDefaults.buttonColors(containerColor = RongdhonuPurplePrimary),
            modifier = Modifier.testTag("btn_submit_ticket")
          ) {
            Text("জমা দিন")
          }
        }
      }
    }
  }
}
