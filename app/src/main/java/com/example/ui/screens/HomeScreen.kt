package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.DefaultServices
import com.example.data.model.CitizenHelpline
import com.example.data.model.CitizenService
import com.example.data.model.ServiceCategory
import com.example.ui.BottomNavTab
import com.example.ui.RongdhonuViewModel
import com.example.ui.components.BroadcastNoticeBanner
import com.example.ui.components.CategoryChipsRow
import com.example.ui.components.CitizenServiceCard
import com.example.ui.components.OrderItemCard
import com.example.ui.components.RongdhonuHeroBanner
import com.example.ui.components.RongdhonuSearchBar
import com.example.ui.theme.RongdhonuAccentOrange
import com.example.ui.theme.RongdhonuPurpleLight
import com.example.ui.theme.RongdhonuPurplePrimary

@Composable
fun HomeScreen(
  viewModel: RongdhonuViewModel,
  onNavigateToServices: () -> Unit,
  onNavigateToOrders: () -> Unit,
  modifier: Modifier = Modifier
) {
  val context = LocalContext.current
  val walletBalance by viewModel.repository.walletBalance.collectAsState()
  val services by viewModel.repository.services.collectAsState()
  val orders by viewModel.repository.orders.collectAsState()
  val notices by viewModel.repository.broadcastNotices.collectAsState()
  val selectedCategory by viewModel.selectedCategory.collectAsState()
  val searchQuery by viewModel.searchQuery.collectAsState()

  val featuredServices = services.filter { it.isFeatured || it.isPopular }.take(12)
  val recentOrders = orders.take(2)

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    contentPadding = PaddingValues(top = 12.dp, bottom = 24.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // Broadcast Urgent Notice if present
    val activeNotice = notices.firstOrNull { it.isActive }
    if (activeNotice != null) {
      item {
        BroadcastNoticeBanner(notice = activeNotice)
      }
    }

    // Hero Banner
    item {
      RongdhonuHeroBanner(
        walletBalance = walletBalance,
        onAddBalance = { viewModel.openRechargeDialog() },
        onOpenSupport = {
          val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/8801632042441"))
          try { context.startActivity(intent) } catch (e: Exception) {}
        }
      )
    }

    // Download Android APK & Windows PC App Banner
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
          .fillMaxWidth()
          .clickable { viewModel.openDownloadCenter() }
          .testTag("card_download_app_promo")
      ) {
        Row(
          modifier = Modifier.padding(14.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
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
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                  text = "অ্যাপ ডাউনলোড সেন্টার",
                  style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                  color = Color(0xFF1E1738)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Surface(
                  shape = RoundedCornerShape(4.dp),
                  color = Color(0xFFE8F5E9)
                ) {
                  Text(
                    text = "v2.4.0",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, color = Color(0xFF2E7D32), fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                  )
                }
              }
              Text(
                text = "Android APK (23MB) ও Windows PC সফটওয়্যার ডাউনলোড করুন",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                color = Color(0xFF6B6580)
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

    // Search Bar
    item {
      RongdhonuSearchBar(
        query = searchQuery,
        onQueryChange = {
          viewModel.setSearchQuery(it)
          if (it.isNotEmpty()) {
            onNavigateToServices()
          }
        }
      )
    }

    // Category Selector
    item {
      Column {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "সেবা ক্যাটাগরি",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
          )
          Text(
            text = "সব দেখুন",
            style = MaterialTheme.typography.labelMedium.copy(
              color = RongdhonuPurplePrimary,
              fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
              .clickable { onNavigateToServices() }
              .padding(4.dp)
          )
        }
        Spacer(modifier = Modifier.height(10.dp))
        CategoryChipsRow(
          selectedCategory = selectedCategory,
          onSelectCategory = { cat ->
            viewModel.selectCategory(cat)
            onNavigateToServices()
          }
        )
      }
    }

    // Featured Services Header
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = RongdhonuAccentOrange,
            modifier = Modifier.size(20.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "জনপ্রিয় ও জরুরি নাগরিক সেবা",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
          )
        }
        Text(
          text = "সকল ${services.size}টি সেবা",
          style = MaterialTheme.typography.labelMedium.copy(
            color = RongdhonuPurplePrimary,
            fontWeight = FontWeight.Bold
          ),
          modifier = Modifier
            .clickable { onNavigateToServices() }
            .padding(4.dp)
        )
      }
    }

    // Featured Services 2-Column Grid (Compact)
    val featuredPairs = featuredServices.chunked(2)
    item {
      Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        featuredPairs.forEach { pair ->
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Box(modifier = Modifier.weight(1f)) {
              CitizenServiceCard(
                service = pair[0],
                onClick = { viewModel.openServiceDetail(pair[0]) },
                onApplyDirect = { viewModel.openOrderForm(pair[0]) }
              )
            }
            if (pair.size > 1) {
              Box(modifier = Modifier.weight(1f)) {
                CitizenServiceCard(
                  service = pair[1],
                  onClick = { viewModel.openServiceDetail(pair[1]) },
                  onApplyDirect = { viewModel.openOrderForm(pair[1]) }
                )
              }
            } else {
              Spacer(modifier = Modifier.weight(1f))
            }
          }
        }
      }
    }

    // Emergency Helpline Horizontal Carousel
    item {
      Column {
        Text(
          text = "জরুরি সরকারি হেল্পলাইন (টোল ফ্রি)",
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
          color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(10.dp))
        LazyRow(
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          items(DefaultServices.helplines) { helpline ->
            HelplineCard(
              helpline = helpline,
              onCall = {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${helpline.number}"))
                try { context.startActivity(intent) } catch (e: Exception) {}
              }
            )
          }
        }
      }
    }

    // Recent Orders section if exists
    if (recentOrders.isNotEmpty()) {
      item {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "আপনার সাম্প্রতিক আবেদনসমূহ",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface
          )
          Text(
            text = "সব ট্র্যাক করুন",
            style = MaterialTheme.typography.labelMedium.copy(
              color = RongdhonuPurplePrimary,
              fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
              .clickable { onNavigateToOrders() }
              .padding(4.dp)
          )
        }
      }
      items(recentOrders) { order ->
        OrderItemCard(
          order = order,
          onViewReceipt = { viewModel.openReceipt(order) }
        )
      }
    }

    // Studio Direct Assistance Card
    item {
      Card(
        shape = RoundedCornerShape(16.dp),
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
              .size(46.dp)
              .clip(CircleShape)
              .background(Color(0xFF25D366)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Phone,
              contentDescription = null,
              tint = Color.White,
              modifier = Modifier.size(24.dp)
            )
          }
          Spacer(modifier = Modifier.width(14.dp))
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = "সরাসরি স্টুডিও সহায়তা",
              style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )
            Text(
              text = "WhatsApp / Call: 01632-042441",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
              text = "রংধনু ডিজিটাল স্টুডিও • সকাল ৯টা - রাত ১০টা",
              style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
              color = RongdhonuPurplePrimary
            )
          }
          Button(
            onClick = {
              val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/8801632042441"))
              try { context.startActivity(intent) } catch (e: Exception) {}
            },
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
          ) {
            Text("মেসেজ", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
          }
        }
      }
    }
  }
}

@Composable
fun HelplineCard(
  helpline: CitizenHelpline,
  onCall: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    shape = RoundedCornerShape(14.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    modifier = modifier
      .width(200.dp)
      .clickable { onCall() }
  ) {
    Column(modifier = Modifier.padding(12.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Surface(
          color = RongdhonuPurpleLight.copy(alpha = 0.15f),
          shape = RoundedCornerShape(8.dp)
        ) {
          Text(
            text = helpline.number,
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = RongdhonuPurplePrimary
            ),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
          )
        }
        Icon(
          imageVector = Icons.Default.Call,
          contentDescription = "Call",
          tint = Color(0xFF008947),
          modifier = Modifier.size(18.dp)
        )
      }
      Spacer(modifier = Modifier.height(6.dp))
      Text(
        text = helpline.titleBn,
        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
        maxLines = 1
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = helpline.descBn,
        style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        maxLines = 2
      )
    }
  }
}
