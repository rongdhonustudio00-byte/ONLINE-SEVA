package com.example

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.BottomNavTab
import com.example.ui.RongdhonuViewModel
import com.example.ui.components.RongdhonuBottomNavigation
import com.example.ui.components.RongdhonuTopAppBar
import com.example.ui.screens.AdminPanelScreen
import com.example.ui.screens.DownloadCenterScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.OrderFormDialog
import com.example.ui.screens.OrdersScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.ReceiptDialog
import com.example.ui.screens.ServiceDetailScreen
import com.example.ui.screens.ServicesCatalogScreen
import com.example.ui.screens.WalletScreen
import com.example.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        RongdhonuAppRoot()
      }
    }
  }
}

@Composable
fun RongdhonuAppRoot(
  viewModel: RongdhonuViewModel = viewModel()
) {
  val context = LocalContext.current
  val currentTab by viewModel.currentTab.collectAsState()
  val selectedService by viewModel.selectedService.collectAsState()
  val showOrderForm by viewModel.showOrderForm.collectAsState()
  val selectedOrderForReceipt by viewModel.selectedOrderForReceipt.collectAsState()
  val showAdminPortal by viewModel.showAdminPortal.collectAsState()
  val showDownloadCenter by viewModel.showDownloadCenter.collectAsState()
  val notification by viewModel.notification.collectAsState()

  // Auto clear notification after 4.5 seconds
  LaunchedEffect(notification) {
    if (notification != null) {
      delay(4500)
      viewModel.clearNotification()
    }
  }

  Box(modifier = Modifier.fillMaxSize()) {
    if (showDownloadCenter) {
      DownloadCenterScreen(
        viewModel = viewModel,
        onBack = { viewModel.closeDownloadCenter() },
        modifier = Modifier.fillMaxSize()
      )
    } else if (showAdminPortal) {
      AdminPanelScreen(
        viewModel = viewModel,
        onBack = { viewModel.closeAdminPortal() },
        modifier = Modifier.fillMaxSize()
      )
    } else if (selectedService != null && !showOrderForm) {
      ServiceDetailScreen(
        service = selectedService!!,
        onBack = { viewModel.closeServiceDetail() },
        onApply = { viewModel.openOrderForm(selectedService!!) },
        modifier = Modifier.fillMaxSize()
      )
    } else {
      Scaffold(
        topBar = {
          RongdhonuTopAppBar(
            onOpenAdmin = { viewModel.openAdminPortal() },
            onOpenWhatsApp = {
              val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/8801632042441"))
              try { context.startActivity(intent) } catch (e: Exception) {}
            },
            onOpenDownloads = { viewModel.openDownloadCenter() }
          )
        },
        bottomBar = {
          RongdhonuBottomNavigation(
            currentTab = currentTab,
            onTabSelected = { tab -> viewModel.selectTab(tab) },
            modifier = Modifier.testTag("main_bottom_nav")
          )
        },
        modifier = Modifier.fillMaxSize()
      ) { innerPadding ->
        Box(
          modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
        ) {
          when (currentTab) {
            BottomNavTab.HOME -> HomeScreen(
              viewModel = viewModel,
              onNavigateToServices = { viewModel.selectTab(BottomNavTab.SERVICES) },
              onNavigateToOrders = { viewModel.selectTab(BottomNavTab.ORDERS) }
            )
            BottomNavTab.SERVICES -> ServicesCatalogScreen(
              viewModel = viewModel
            )
            BottomNavTab.ORDERS -> OrdersScreen(
              viewModel = viewModel,
              onNavigateToServices = { viewModel.selectTab(BottomNavTab.SERVICES) }
            )
            BottomNavTab.WALLET -> WalletScreen(
              viewModel = viewModel
            )
            BottomNavTab.PROFILE -> ProfileScreen(
              viewModel = viewModel,
              onOpenAdmin = { viewModel.openAdminPortal() }
            )
          }
        }
      }
    }

    // Modal dialogs
    if (showOrderForm && selectedService != null) {
      OrderFormDialog(
        service = selectedService!!,
        viewModel = viewModel,
        onDismiss = { viewModel.closeOrderForm() }
      )
    }

    if (selectedOrderForReceipt != null) {
      ReceiptDialog(
        order = selectedOrderForReceipt!!,
        onDismiss = { viewModel.closeReceipt() }
      )
    }

    // Top In-App Floating Toast / Notification Banner
    AnimatedVisibility(
      visible = notification != null,
      enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
      exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
      modifier = Modifier
        .align(Alignment.TopCenter)
        .padding(top = 48.dp, start = 16.dp, end = 16.dp)
    ) {
      notification?.let { notif ->
        Surface(
          shape = RoundedCornerShape(12.dp),
          color = if (notif.isError) Color(0xFFC62828) else Color(0xFF1B5E20),
          shadowElevation = 8.dp,
          modifier = Modifier
            .fillMaxWidth()
            .clickable { viewModel.clearNotification() }
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = if (notif.isError) Icons.Default.Error else Icons.Default.CheckCircle,
              contentDescription = null,
              tint = Color.White
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
              text = notif.messageBn,
              style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp
              ),
              modifier = Modifier.weight(1f)
            )
            IconButton(
              onClick = { viewModel.clearNotification() }
            ) {
              Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Dismiss",
                tint = Color.White
              )
            }
          }
        }
      }
    }
  }
}
