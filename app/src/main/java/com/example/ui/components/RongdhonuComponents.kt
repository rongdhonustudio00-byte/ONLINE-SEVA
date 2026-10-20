package com.example.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CitizenService
import com.example.data.model.OrderStatus
import com.example.data.model.ServiceCategory
import com.example.data.model.ServiceOrder
import com.example.ui.BottomNavTab
import com.example.ui.theme.BkashPink
import com.example.ui.theme.NagadOrange
import com.example.ui.theme.RongdhonuAccentCyan
import com.example.ui.theme.RongdhonuAccentOrange
import com.example.ui.theme.RongdhonuPurpleDark
import com.example.ui.theme.RongdhonuPurpleLight
import com.example.ui.theme.RongdhonuPurplePrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RongdhonuTopAppBar(
  onOpenAdmin: () -> Unit,
  onOpenWhatsApp: () -> Unit,
  onOpenDownloads: () -> Unit = {},
  modifier: Modifier = Modifier
) {
  TopAppBar(
    title = {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
          modifier = Modifier
            .size(38.dp)
            .clip(CircleShape)
            .background(
              Brush.linearGradient(
                colors = listOf(RongdhonuPurpleLight, RongdhonuAccentCyan)
              )
            ),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Security,
            contentDescription = "রংধনু লোগো",
            tint = Color.White,
            modifier = Modifier.size(22.dp)
          )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column {
          Text(
            text = "রংধনু অনলাইন সেবা",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 17.sp
            ),
            color = Color.White
          )
          Text(
            text = "রংধনু ডিজিটাল স্টুডিও • Citizen Services",
            style = MaterialTheme.typography.labelSmall.copy(
              fontSize = 11.sp
            ),
            color = Color.White.copy(alpha = 0.85f)
          )
        }
      }
    },
    actions = {
      // Download App Center Shortcut
      IconButton(
        onClick = onOpenDownloads,
        modifier = Modifier.testTag("btn_top_download_app")
      ) {
        Box(
          modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.18f)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Download,
            contentDescription = "ডাউনলোড অ্যাপ",
            tint = Color.White,
            modifier = Modifier.size(20.dp)
          )
        }
      }
      // WhatsApp Direct
      IconButton(
        onClick = onOpenWhatsApp,
        modifier = Modifier.testTag("btn_whatsapp_support")
      ) {
        Box(
          modifier = Modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(Color(0xFF25D366)),
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = Icons.Default.Phone,
            contentDescription = "WhatsApp 01632-042441",
            tint = Color.White,
            modifier = Modifier.size(20.dp)
          )
        }
      }
      // Admin Portal Entry
      IconButton(
        onClick = onOpenAdmin,
        modifier = Modifier.testTag("btn_admin_portal_entry")
      ) {
        Icon(
          imageVector = Icons.Default.AdminPanelSettings,
          contentDescription = "Admin Panel",
          tint = Color.White.copy(alpha = 0.9f)
        )
      }
    },
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = RongdhonuPurplePrimary
    ),
    modifier = modifier
  )
}

@Composable
fun BroadcastNoticeBanner(
  notice: com.example.data.model.BroadcastNotice,
  modifier: Modifier = Modifier
) {
  val bgColor = if (notice.isUrgent) Color(0xFFFFF3E0) else Color(0xFFEDE7F6)
  val borderColor = if (notice.isUrgent) Color(0xFFFF9800) else RongdhonuPurplePrimary
  val iconTint = if (notice.isUrgent) Color(0xFFE65100) else RongdhonuPurplePrimary

  Surface(
    shape = RoundedCornerShape(14.dp),
    color = bgColor,
    border = androidx.compose.foundation.BorderStroke(1.dp, borderColor.copy(alpha = 0.5f)),
    modifier = modifier.fillMaxWidth()
  ) {
    Row(
      modifier = Modifier.padding(12.dp),
      verticalAlignment = Alignment.Top
    ) {
      Box(
        modifier = Modifier
          .size(32.dp)
          .clip(CircleShape)
          .background(iconTint.copy(alpha = 0.15f)),
        contentAlignment = Alignment.Center
      ) {
        Icon(
          imageVector = if (notice.isUrgent) Icons.Default.Warning else Icons.Default.Campaign,
          contentDescription = null,
          tint = iconTint,
          modifier = Modifier.size(18.dp)
        )
      }

      Spacer(modifier = Modifier.width(10.dp))

      Column(modifier = Modifier.weight(1f)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = notice.titleBn,
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
            color = if (notice.isUrgent) Color(0xFFB71C1C) else RongdhonuPurpleDark
          )
          if (notice.isUrgent) {
            Surface(
              shape = RoundedCornerShape(4.dp),
              color = Color(0xFFC62828)
            ) {
              Text(
                text = "জরুরি নোটিশ",
                style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, color = Color.White, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
              )
            }
          }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
          text = notice.messageBn,
          style = MaterialTheme.typography.bodySmall.copy(lineHeight = 18.sp),
          color = Color(0xFF37474F)
        )
      }
    }
  }
}


@Composable
fun RongdhonuHeroBanner(
  walletBalance: Int,
  onAddBalance: () -> Unit,
  onOpenSupport: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(containerColor = Color.Transparent),
    modifier = modifier
      .fillMaxWidth()
      .shadow(elevation = 8.dp, shape = RoundedCornerShape(20.dp))
      .background(
        brush = Brush.verticalGradient(
          colors = listOf(RongdhonuPurpleDark, RongdhonuPurplePrimary, RongdhonuPurpleLight)
        ),
        shape = RoundedCornerShape(20.dp)
      )
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(18.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
      ) {
        Column(modifier = Modifier.weight(1f)) {
          Surface(
            color = RongdhonuAccentOrange.copy(alpha = 0.25f),
            shape = RoundedCornerShape(12.dp)
          ) {
            Text(
              text = "ডিজিটাল বাংলাদেশ সিটিজেন পোর্টাল",
              style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.SemiBold),
              color = Color(0xFFFFD180),
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
          }
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "আসসালামু আলাইকুম,",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White.copy(alpha = 0.9f)
          )
          Text(
            text = "রংধনু ডিজিটাল স্টুডিওতে স্বাগতম",
            style = MaterialTheme.typography.titleLarge.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 20.sp
            ),
            color = Color.White
          )
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Wallet Bar inside Hero
      Surface(
        color = Color.White.copy(alpha = 0.12f),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 10.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.AccountBalanceWallet,
              contentDescription = null,
              tint = RongdhonuAccentCyan,
              modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = "বর্তমান ব্যালেন্স",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.8f)
              )
              Text(
                text = "৳ $walletBalance BDT",
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Bold,
                  fontSize = 18.sp
                ),
                color = Color.White
              )
            }
          }

          Button(
            onClick = onAddBalance,
            colors = ButtonDefaults.buttonColors(
              containerColor = RongdhonuAccentOrange,
              contentColor = Color.White
            ),
            shape = RoundedCornerShape(10.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
            modifier = Modifier.testTag("btn_hero_add_balance")
          ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("রিচার্জ করুন", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "হেল্পলাইন: 01632-042441 (WhatsApp / Call)",
          style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
          color = Color.White.copy(alpha = 0.85f)
        )
        Text(
          text = "সহায়তা নিন",
          style = MaterialTheme.typography.labelSmall.copy(
            color = RongdhonuAccentCyan,
            fontWeight = FontWeight.Bold
          ),
          modifier = Modifier
            .clickable { onOpenSupport() }
            .padding(4.dp)
        )
      }
    }
  }
}

@Composable
fun RongdhonuSearchBar(
  query: String,
  onQueryChange: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  OutlinedTextField(
    value = query,
    onValueChange = onQueryChange,
    placeholder = {
      Text(
        "বাংলা বা ইংরেজিতে সেবা খুঁজুন (যেমন: NID, জন্ম নিবন্ধন, পাসপোর্ট)...",
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
      )
    },
    leadingIcon = {
      Icon(
        imageVector = Icons.Default.Search,
        contentDescription = "Search",
        tint = RongdhonuPurplePrimary
      )
    },
    trailingIcon = {
      if (query.isNotEmpty()) {
        IconButton(onClick = { onQueryChange("") }) {
          Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear search")
        }
      }
    },
    shape = RoundedCornerShape(14.dp),
    colors = OutlinedTextFieldDefaults.colors(
      focusedContainerColor = Color.White,
      unfocusedContainerColor = Color.White,
      focusedBorderColor = RongdhonuPurplePrimary,
      unfocusedBorderColor = MaterialTheme.colorScheme.outline
    ),
    singleLine = true,
    modifier = modifier
      .fillMaxWidth()
      .testTag("input_service_search")
  )
}

@Composable
fun CategoryChipsRow(
  selectedCategory: ServiceCategory,
  onSelectCategory: (ServiceCategory) -> Unit,
  modifier: Modifier = Modifier
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .horizontalScroll(rememberScrollState()),
    horizontalArrangement = Arrangement.spacedBy(8.dp)
  ) {
    ServiceCategory.values().forEach { category ->
      val isSelected = category == selectedCategory
      Surface(
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) RongdhonuPurplePrimary else Color.White,
        border = if (isSelected) null else androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2DCED)),
        shadowElevation = if (isSelected) 3.dp else 1.dp,
        modifier = Modifier
          .clickable { onSelectCategory(category) }
          .testTag("chip_category_${category.name.lowercase()}")
      ) {
        Row(
          modifier = Modifier.padding(horizontal = 14.dp, vertical = 9.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = getCategoryIcon(category),
            contentDescription = null,
            tint = if (isSelected) Color.White else RongdhonuPurplePrimary,
            modifier = Modifier.size(16.dp)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = category.titleBn,
            style = MaterialTheme.typography.labelMedium.copy(
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
            ),
            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
          )
        }
      }
    }
  }
}

@Composable
fun CitizenServiceCard(
  service: CitizenService,
  onClick: () -> Unit,
  onApplyDirect: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp),
    modifier = modifier
      .fillMaxWidth()
      .border(1.dp, Color(0xFFECE6F5), RoundedCornerShape(16.dp))
      .clickable { onClick() }
      .testTag("card_service_${service.id}")
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 10.dp, vertical = 9.dp)
    ) {
      // Top row: Micro icon + Micro Timer Badge
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        ServiceExpressiveIcon(
          iconKey = service.iconKey,
          category = service.category,
          isCompact = true
        )

        if (service.timerBadge.isNotEmpty()) {
          Surface(
            color = Color(0xFFFFF4D8),
            shape = RoundedCornerShape(6.dp)
          ) {
            Text(
              text = "⏱ ${service.timerBadge}",
              style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF8A5A00)
              ),
              modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
            )
          }
        } else {
          Surface(
            color = Color(0xFFE8F5E9),
            shape = RoundedCornerShape(6.dp)
          ) {
            Text(
              text = "সক্রিয়",
              style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E7D32)
              ),
              modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(7.dp))

      // Service Title in Bengali (Compact, 11.5sp, 2 lines max)
      Text(
        text = service.titleBn,
        style = MaterialTheme.typography.titleSmall.copy(
          fontWeight = FontWeight.Bold,
          fontSize = 12.sp,
          lineHeight = 15.sp,
          textAlign = androidx.compose.ui.text.style.TextAlign.Start
        ),
        color = Color(0xFF1E1738),
        maxLines = 2,
        minLines = 2,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.fillMaxWidth()
      )

      Spacer(modifier = Modifier.height(6.dp))

      // Bottom Row: Price tag + Quick Apply
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Surface(
          color = Color(0xFFF3ECFA),
          shape = RoundedCornerShape(6.dp)
        ) {
          Text(
            text = "৳ ${service.totalFeeBdt}",
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.ExtraBold,
              fontSize = 11.5.sp,
              color = Color(0xFF5B239C)
            ),
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
          )
        }

        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier
            .clickable { onApplyDirect() }
            .padding(2.dp)
        ) {
          Text(
            text = "আবেদন",
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 10.5.sp,
              color = Color(0xFF008947)
            )
          )
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = null,
            tint = Color(0xFF008947),
            modifier = Modifier.size(11.dp)
          )
        }
      }
    }
  }
}

@Composable
fun ServiceExpressiveIcon(
  iconKey: String,
  category: ServiceCategory,
  isCompact: Boolean = false
) {
  val (bgColor, iconColor, icon) = when (iconKey) {
    "smart_card", "card", "card_profile" -> Triple(Color(0xFF6E3CBC), Color(0xFF80D8FF), Icons.Default.Badge)
    "mobile", "mobile_clipboard", "mobile_pen", "sim" -> Triple(Color(0xFF6E3CBC), Color(0xFFFFD54F), Icons.Default.PhoneAndroid)
    "clipboard", "document", "tax_receipt" -> Triple(Color(0xFFE3F2FD), Color(0xFF1976D2), Icons.Default.ReceiptLong)
    "pen", "form_pen" -> Triple(Color(0xFF6E3CBC), Color(0xFF80DEEA), Icons.Default.ReceiptLong)
    "money_bag", "flying_money" -> Triple(Color(0xFFFFF9C4), Color(0xFFF57F17), Icons.Default.AccountBalanceWallet)
    "graduation" -> Triple(Color(0xFF6E3CBC), Color(0xFFFFD54F), Icons.Default.School)
    "search" -> Triple(Color(0xFFE1F5FE), Color(0xFF0288D1), Icons.Default.Search)
    "refresh" -> Triple(Color(0xFFE0F2F1), Color(0xFF00796B), Icons.Default.Security)
    "wheelchair" -> Triple(Color(0xFFEDE7F6), Color(0xFF6A1B9A), Icons.Default.Person)
    "bank" -> Triple(Color(0xFFECEFF1), Color(0xFF455A64), Icons.Default.Storefront)
    "ballot" -> Triple(Color(0xFFE8EAF6), Color(0xFF3949AB), Icons.Default.Badge)
    "phone_call" -> Triple(Color(0xFFF5F5F5), Color(0xFF424242), Icons.Default.Phone)
    "sms" -> Triple(Color(0xFFFFF3E0), Color(0xFFE65100), Icons.Default.SupportAgent)
    "rocket" -> Triple(Color(0xFFFCE4EC), Color(0xFFD81B60), Icons.Default.ElectricBolt)
    "pin", "map" -> Triple(Color(0xFFE0F7FA), Color(0xFF00838F), Icons.Default.Map)
    "flight" -> Triple(Color(0xFFE1F5FE), Color(0xFF0277BD), Icons.Default.FlightTakeoff)
    "police" -> Triple(Color(0xFFEDE7F6), Color(0xFF4527A0), Icons.Default.Security)
    "baby" -> Triple(Color(0xFFFFF8E1), Color(0xFFFFA000), Icons.Default.PersonAdd)
    "studio", "camera" -> Triple(Color(0xFFEDE7F6), Color(0xFF6A1B9A), Icons.Default.PhotoCamera)
    "shop", "cart" -> Triple(Color(0xFFE8F5E9), Color(0xFF2E7D32), Icons.Default.ShoppingBag)
    else -> Triple(Color(0xFF6E3CBC), Color.White, getCategoryIcon(category))
  }

  val boxSize = if (isCompact) 34.dp else 46.dp
  val iconSize = if (isCompact) 18.dp else 26.dp
  val cornerRadius = if (isCompact) 10.dp else 14.dp

  Box(
    modifier = Modifier
      .size(boxSize)
      .clip(RoundedCornerShape(cornerRadius))
      .background(bgColor),
    contentAlignment = Alignment.Center
  ) {
    Icon(
      imageVector = icon,
      contentDescription = null,
      tint = iconColor,
      modifier = Modifier.size(iconSize)
    )
  }
}

@Composable
fun OrderItemCard(
  order: ServiceOrder,
  onViewReceipt: () -> Unit,
  modifier: Modifier = Modifier
) {
  Card(
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = modifier
      .fillMaxWidth()
      .border(1.dp, Color(0xFFF0ECF6), RoundedCornerShape(16.dp))
      .clickable { onViewReceipt() }
      .testTag("card_order_${order.orderId}")
  ) {
    Column(modifier = Modifier.padding(14.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = order.orderId,
          style = MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.Bold,
            color = RongdhonuPurplePrimary
          )
        )

        Surface(
          color = Color(order.status.colorHex).copy(alpha = 0.14f),
          shape = RoundedCornerShape(8.dp)
        ) {
          Text(
            text = order.status.titleBn,
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.Bold,
              fontSize = 11.sp
            ),
            color = Color(order.status.colorHex),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      Text(
        text = order.serviceTitleBn,
        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.onSurface
      )

      Spacer(modifier = Modifier.height(4.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Text(
          text = "আবেদনকারী: ${order.citizenName}",
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
          text = "ফি: ৳${order.feePaidBdt}",
          style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
          color = RongdhonuPurplePrimary
        )
      }

      if (order.adminRemarks.isNotEmpty()) {
        Spacer(modifier = Modifier.height(6.dp))
        Surface(
          color = Color(0xFFF9F7FD),
          shape = RoundedCornerShape(6.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Text(
            text = "স্টুডিও নোট: ${order.adminRemarks}",
            style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp),
            color = RongdhonuPurpleDark,
            modifier = Modifier.padding(6.dp)
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
      ) {
        Button(
          onClick = onViewReceipt,
          shape = RoundedCornerShape(8.dp),
          colors = ButtonDefaults.outlinedButtonColors(),
          contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
          modifier = Modifier.testTag("btn_receipt_${order.orderId}")
        ) {
          Icon(
            imageVector = Icons.Default.ReceiptLong,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = RongdhonuPurplePrimary
          )
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            "রসিদ ও কপি",
            style = MaterialTheme.typography.labelMedium,
            color = RongdhonuPurplePrimary
          )
        }
      }
    }
  }
}

@Composable
fun RongdhonuBottomNavigation(
  currentTab: BottomNavTab,
  onTabSelected: (BottomNavTab) -> Unit,
  modifier: Modifier = Modifier
) {
  NavigationBar(
    containerColor = Color.White,
    tonalElevation = 8.dp,
    modifier = modifier
  ) {
    BottomNavTab.values().forEach { tab ->
      val isSelected = tab == currentTab
      val icon = when (tab) {
        BottomNavTab.HOME -> Icons.Default.Storefront
        BottomNavTab.SERVICES -> Icons.Default.Badge
        BottomNavTab.ORDERS -> Icons.Default.ReceiptLong
        BottomNavTab.WALLET -> Icons.Default.AccountBalanceWallet
        BottomNavTab.PROFILE -> Icons.Default.Person
      }
      NavigationBarItem(
        selected = isSelected,
        onClick = { onTabSelected(tab) },
        icon = {
          Icon(
            imageVector = icon,
            contentDescription = tab.titleBn
          )
        },
        label = {
          Text(
            text = tab.titleBn,
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
              fontSize = 11.sp
            )
          )
        },
        colors = NavigationBarItemDefaults.colors(
          selectedIconColor = RongdhonuPurplePrimary,
          selectedTextColor = RongdhonuPurplePrimary,
          indicatorColor = RongdhonuPurplePrimary.copy(alpha = 0.14f),
          unselectedIconColor = Color(0xFF756F84),
          unselectedTextColor = Color(0xFF756F84)
        ),
        modifier = Modifier.testTag(tab.testTag)
      )
    }
  }
}

fun getCategoryIcon(category: ServiceCategory): ImageVector {
  return when (category) {
    ServiceCategory.ALL -> Icons.Default.Storefront
    ServiceCategory.NID_VOTER -> Icons.Default.Badge
    ServiceCategory.BIRTH_DEATH -> Icons.Default.PersonAdd
    ServiceCategory.PASSPORT_VISA -> Icons.Default.FlightTakeoff
    ServiceCategory.LAND_MUTATION -> Icons.Default.Map
    ServiceCategory.TIN_TAX -> Icons.Default.ReceiptLong
    ServiceCategory.EDUCATION -> Icons.Default.School
    ServiceCategory.MOBILE_SERVICE -> Icons.Default.PhoneAndroid
    ServiceCategory.DIGITAL_STUDIO -> Icons.Default.PhotoCamera
    ServiceCategory.ECOMMERCE_SHOP -> Icons.Default.ShoppingBag
    ServiceCategory.TRADE_LICENSE -> Icons.Default.Storefront
    ServiceCategory.UTILITY_BILLS -> Icons.Default.ElectricBolt
    ServiceCategory.GOVT_JOBS -> Icons.Default.Work
    ServiceCategory.DRIVING_BRTA -> Icons.Default.DirectionsCar
    ServiceCategory.LEGAL_HELPLINE -> Icons.Default.SupportAgent
  }
}
