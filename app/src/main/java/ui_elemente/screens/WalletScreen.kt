package ui_elemente.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ui_elemente.navigation.Topbar
import ui_elemente.viewModel.ProfileViewModel
import com.example.carsharing_app.data.TripViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ButtonDefaults
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.Row

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletScreen(
    navController: NavHostController,
    profileViewModel: ProfileViewModel? = null,
    tripViewModel: TripViewModel? = null
) {
    if (LocalInspectionMode.current) {
        WalletScreenContent(
            navController = navController,
            balance = 100.0,
            onAddFunds = { _, _ -> }
        )
    } else {
        val pvm: ProfileViewModel = profileViewModel ?: viewModel()
        val tvm: TripViewModel = tripViewModel ?: viewModel()
        val balance by pvm.walletBalance.collectAsState()

        WalletScreenContent(
            navController = navController,
            balance = balance,
            onAddFunds = { amount, onResult ->
                tvm.rechargeWallet(amount, onResult)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletScreenContent(
    navController: NavHostController,
    balance: Double,
    onAddFunds: (Double, (Boolean) -> Unit) -> Unit
) {
    val context = LocalContext.current
    var amountText by remember { mutableStateOf("") }
    var selectedPaymentMethod by remember { mutableStateOf("Credit or Mastercard") }

    Scaffold(
        topBar = {
            Topbar("Wallet", navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                border = BorderStroke(1.dp, Color.LightGray),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Current Balance",
                        fontSize = 18.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "€${"%.2f".format(balance)}",
                        fontSize = 60.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Amount of funds you want to add",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )
            OutlinedTextField(
                value = amountText,
                onValueChange = { amountText = it.filter { char -> char.isDigit() || char == '.' } },
                label = { Text("Amount to add (€)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(50
                .dp))

            Text(
                text = "Select Payment Method",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Credit or Mastercard", "Google Pay", "Voucher").forEach { method ->
                    Button(
                        onClick = { selectedPaymentMethod = method },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedPaymentMethod == method) MaterialTheme.colorScheme.primary else Color(0xFFF5F5F5),
                            contentColor = if (selectedPaymentMethod == method) Color.White else Color.Black
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(text = method)
                    }
                }
            }

            Spacer(modifier = Modifier.height(50.dp))

            Button(
                onClick = {
                    val amount = amountText.toDoubleOrNull()
                    if (amount != null && amount > 0) {
                        onAddFunds(amount) { success ->
                            if (success) {
                                Toast.makeText(context, "€${"%.2f".format(amount)} added via $selectedPaymentMethod", Toast.LENGTH_SHORT).show()
                                amountText = ""
                            } else {
                                Toast.makeText(context, "Failed to add funds", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(context, "Please enter a valid amount", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Add Funds", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}



@Preview(name = "wallet", showBackground = true)
@Composable
fun WalletScreenPreview() {
    val navController = rememberNavController()
    WalletScreen(navController = navController)
}