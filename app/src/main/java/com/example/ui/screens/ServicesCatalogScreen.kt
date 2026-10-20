package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ServiceCategory
import com.example.ui.RongdhonuViewModel
import com.example.ui.components.CategoryChipsRow
import com.example.ui.components.CitizenServiceCard
import com.example.ui.components.RongdhonuSearchBar
import com.example.ui.theme.RongdhonuPurplePrimary

@Composable
fun ServicesCatalogScreen(
  viewModel: RongdhonuViewModel,
  modifier: Modifier = Modifier
) {
  val services by viewModel.filteredServices.collectAsState()
  val selectedCategory by viewModel.selectedCategory.collectAsState()
  val searchQuery by viewModel.searchQuery.collectAsState()

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    contentPadding = PaddingValues(top = 12.dp, bottom = 24.dp),
    verticalArrangement = Arrangement.spacedBy(14.dp)
  ) {
    // Search Bar
    item {
      RongdhonuSearchBar(
        query = searchQuery,
        onQueryChange = { viewModel.setSearchQuery(it) }
      )
    }

    // Category Chips
    item {
      CategoryChipsRow(
        selectedCategory = selectedCategory,
        onSelectCategory = { viewModel.selectCategory(it) }
      )
    }

    // Results Header
    item {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = if (selectedCategory == ServiceCategory.ALL) "সকল নাগরিক সেবা" else selectedCategory.titleBn,
          style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
          color = MaterialTheme.colorScheme.onSurface
        )

        Surface(
          color = RongdhonuPurplePrimary.copy(alpha = 0.1f),
          shape = MaterialTheme.shapes.small
        ) {
          Text(
            text = "${services.size} টি সেবা",
            style = MaterialTheme.typography.labelSmall.copy(
              fontWeight = FontWeight.Bold,
              color = RongdhonuPurplePrimary
            ),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
          )
        }
      }
    }

    if (services.isEmpty()) {
      item {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 48.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Icon(
            imageVector = Icons.Default.SearchOff,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.padding(bottom = 8.dp)
          )
          Text(
            text = "কোনো সেবা পাওয়া যায়নি",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "অন্য কোনো কীওয়ার্ড দিয়ে খুঁজুন অথবা অন্য ক্যাটাগরি নির্বাচন করুন।",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }
    } else {
      val servicePairs = services.chunked(2)
      item {
        Column(
          modifier = Modifier.fillMaxWidth(),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          servicePairs.forEach { pair ->
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
              androidx.compose.foundation.layout.Box(modifier = Modifier.weight(1f)) {
                CitizenServiceCard(
                  service = pair[0],
                  onClick = { viewModel.openServiceDetail(pair[0]) },
                  onApplyDirect = { viewModel.openOrderForm(pair[0]) }
                )
              }
              if (pair.size > 1) {
                androidx.compose.foundation.layout.Box(modifier = Modifier.weight(1f)) {
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
    }
  }
}
