package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.RongdhonuRepository
import com.example.data.model.OrderStatus
import com.example.data.model.PaymentMethod
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Rongdhonu Seva", appName)
  }

  @Test
  fun `repository loads initial citizen services and helplines`() {
    val repo = RongdhonuRepository()
    val services = repo.services.value
    assertTrue("Services should not be empty", services.isNotEmpty())
    val nidService = services.find { it.id == "nid_server_copy" }
    assertNotNull("NID server copy service should be preloaded", nidService)
  }

  @Test
  fun `submitting order with wallet balance debits wallet successfully`() {
    val repo = RongdhonuRepository()
    val initialBalance = repo.walletBalance.value
    val service = repo.services.value.first()
    val totalFee = service.totalFeeBdt

    val result = repo.submitOrder(
      service = service,
      citizenName = "টেস্ট নাগরিক",
      citizenPhone = "01700000000",
      identifier = "1990123456789",
      notes = "টেস্ট আবেদন",
      paymentMethod = PaymentMethod.WALLET,
      trxId = ""
    )

    assertTrue("Order submission should succeed", result.isSuccess)
    val newBalance = repo.walletBalance.value
    assertEquals(initialBalance - totalFee, newBalance)
    val createdOrder = repo.orders.value.first()
    assertEquals(service.id, createdOrder.serviceId)
    assertEquals(OrderStatus.PENDING, createdOrder.status)
  }

  @Test
  fun `wallet recharge updates balance`() {
    val repo = RongdhonuRepository()
    val initialBalance = repo.walletBalance.value
    val result = repo.requestWalletRecharge(
      amountBdt = 500,
      method = PaymentMethod.BKASH,
      trxId = "BKTEST99"
    )
    assertTrue("Recharge request should succeed", result.isSuccess)
    assertEquals(initialBalance + 500, repo.walletBalance.value)
  }
}
