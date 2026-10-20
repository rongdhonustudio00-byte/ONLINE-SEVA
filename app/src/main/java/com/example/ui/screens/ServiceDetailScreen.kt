package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CitizenService
import com.example.ui.components.getCategoryIcon
import com.example.ui.theme.RongdhonuAccentOrange
import com.example.ui.theme.RongdhonuPurpleLight
import com.example.ui.theme.RongdhonuPurplePrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceDetailScreen(
  service: CitizenService,
  onBack: () -> Unit,
  onApply: () -> Unit,
  modifier: Modifier = Modifier
) {
  BackHandler { onBack() }
  val context = LocalContext.current

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = "সেবার বিবরণ ও আবেদন",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = Color.White
          )
        },
        navigationIcon = {
          IconButton(onClick = onBack, modifier = Modifier.testTag("btn_detail_back")) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.ArrowBack,
              contentDescription = "Back",
              tint = Color.White
            )
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = RongdhonuPurplePrimary)
      )
    },
    bottomBar = {
      Surface(
        color = Color.White,
        tonalElevation = 8.dp,
        shadowElevation = 8.dp,
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Column {
            Text(
              text = "মোট পরিশোধযোগ্য ফি",
              style = MaterialTheme.typography.labelSmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
              text = "৳ ${service.totalFeeBdt}",
              style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = RongdhonuPurplePrimary
              )
            )
          }

          Button(
            onClick = onApply,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = RongdhonuPurplePrimary),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
            modifier = Modifier.testTag("btn_detail_start_order")
          ) {
            Text(
              text = "আবেদন ফরম পূরণ",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
              color = Color.White
            )
          }
        }
      }
    },
    modifier = modifier
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
        .verticalScroll(rememberScrollState())
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      // Main Service Header Card
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
            Surface(
              color = RongdhonuPurplePrimary.copy(alpha = 0.1f),
              shape = RoundedCornerShape(8.dp)
            ) {
              Text(
                text = "${service.serviceCode} • ${service.category.titleBn}",
                style = MaterialTheme.typography.labelSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = RongdhonuPurplePrimary
                ),
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
              )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.AccessTime,
                contentDescription = null,
                tint = RongdhonuAccentOrange,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = service.estimatedDays,
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
                color = RongdhonuAccentOrange
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          Row(verticalAlignment = Alignment.Top) {
            Box(
              modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(RongdhonuPurplePrimary.copy(alpha = 0.12f)),
              contentAlignment = Alignment.Center
            ) {
              Icon(
                imageVector = getCategoryIcon(service.category),
                contentDescription = null,
                tint = RongdhonuPurplePrimary,
                modifier = Modifier.size(28.dp)
              )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(
                text = service.titleBn,
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Bold,
                  fontSize = 17.sp
                )
              )
              Spacer(modifier = Modifier.height(2.dp))
              Text(
                text = service.titleEn,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          Text(
            text = service.descriptionBn,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )

          if (service.officialPortalUrl.isNotEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))
            Surface(
              color = Color(0xFFE8F5E9),
              shape = RoundedCornerShape(10.dp),
              modifier = Modifier
                .fillMaxWidth()
                .clickable {
                  val intent = Intent(Intent.ACTION_VIEW, Uri.parse(service.officialPortalUrl))
                  try { context.startActivity(intent) } catch (e: Exception) {}
                }
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
              ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Icon(
                    imageVector = Icons.Default.Shield,
                    contentDescription = null,
                    tint = Color(0xFF1B5E20),
                    modifier = Modifier.size(18.dp)
                  )
                  Spacer(modifier = Modifier.width(8.dp))
                  Text(
                    text = "অফিসিয়াল সরকারি লিংক: ${service.officialPortalUrl}",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFF1B5E20)
                  )
                }
                Icon(
                  imageVector = Icons.Default.OpenInNew,
                  contentDescription = "Open Web",
                  tint = Color(0xFF1B5E20),
                  modifier = Modifier.size(16.dp)
                )
              }
            }
          }
        }
      }

      // Fee Breakdown Card
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = "ফি এবং চার্জের বিস্তারিত",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
          )
          Spacer(modifier = Modifier.height(10.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("সরকারি নির্ধারিত ফি", style = MaterialTheme.typography.bodyMedium)
            Text("৳ ${service.officialFeeBdt}", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
          }
          Spacer(modifier = Modifier.height(6.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text("স্টুডিও অনলাইন আবেদন চার্জ", style = MaterialTheme.typography.bodyMedium)
            Text("৳ ${service.serviceChargeBdt}", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
          }
          Spacer(modifier = Modifier.height(8.dp))
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(1.dp)
              .background(Color(0xFFE2DCED))
          )
          Spacer(modifier = Modifier.height(8.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              "সর্বমোট খরচ",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
              color = RongdhonuPurplePrimary
            )
            Text(
              "৳ ${service.totalFeeBdt}",
              style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
              color = RongdhonuPurplePrimary
            )
          }
        }
      }

      // Required Documents Card
      if (service.requiredDocuments.isNotEmpty()) {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Text(
              text = "প্রয়োজনীয় কাগজপত্র",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(10.dp))
            service.requiredDocuments.forEach { doc ->
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  imageVector = Icons.Default.CheckCircleOutline,
                  contentDescription = null,
                  tint = RongdhonuPurplePrimary,
                  modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = doc,
                  style = MaterialTheme.typography.bodyMedium
                )
              }
            }
          }
        }
      }

      // Application Steps Card
      if (service.applicationSteps.isNotEmpty()) {
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(16.dp)) {
            Text(
              text = "আবেদন প্রক্রিয়া ও ধাপসমূহ",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(10.dp))
            service.applicationSteps.forEachIndexed { index, step ->
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 4.dp),
                verticalAlignment = Alignment.Top
              ) {
                Box(
                  modifier = Modifier
                    .size(22.dp)
                    .clip(CircleShape)
                    .background(RongdhonuPurplePrimary),
                  contentAlignment = Alignment.Center
                ) {
                  Text(
                    text = "${index + 1}",
                    style = MaterialTheme.typography.labelSmall.copy(
                      fontWeight = FontWeight.Bold,
                      color = Color.White,
                      fontSize = 11.sp
                    )
                  )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                  text = step,
                  style = MaterialTheme.typography.bodyMedium,
                  modifier = Modifier.weight(1f)
                )
              }
            }
          }
        }
      }

      // Studio Quality Commitment
      Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3E5F5)),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier.padding(12.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.Info,
            contentDescription = null,
            tint = RongdhonuPurplePrimary,
            modifier = Modifier.size(24.dp)
          )
          Spacer(modifier = Modifier.width(10.dp))
          Text(
            text = "রংধনু ডিজিটাল স্টুডিও সরাসরি অফিসিয়াল সরকারি সিস্টেমে নির্ভুলভাবে আবেদন করে ট্র্যাকিং স্লিপ ও রসিদ সরবরাহ করে। কোনো ভুয়া ডকুমেন্ট তৈরি করা হয় না।",
            style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
            color = RongdhonuPurplePrimary
          )
        }
      }
    }
  }
}
