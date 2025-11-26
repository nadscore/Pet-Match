package com.example.petmatch.ui.feature.forgotpassword

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.petmatch.ui.components.PetMatchButton
import com.example.petmatch.ui.components.PetMatchInput

@Composable
fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel = viewModel(),
    onNavigateToLogin: () -> Unit,
    onNavigateToVerificationCode: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var email by remember { mutableStateOf("") }
    val context = LocalContext.current

    val OrangeLogo = Color(0xFFD99227)
    val PurpleText = Color(0xFF6A1B9A)

    LaunchedEffect(uiState.successMessage) {
        if (uiState.successMessage != null) {
            Toast.makeText(context, uiState.successMessage, Toast.LENGTH_LONG).show()
            viewModel.clearMessages()
            onNavigateToVerificationCode()
        }
    }

    LaunchedEffect(uiState.errorMessage) {
        if (uiState.errorMessage != null) {
            Toast.makeText(context, uiState.errorMessage, Toast.LENGTH_SHORT).show()
            viewModel.clearMessages()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        // --- ÍCONE DA PATINHA ---
        Icon(
            imageVector = Icons.Default.Pets,
            contentDescription = "Logo PetMatch",
            tint = OrangeLogo,
            modifier = Modifier.size(180.dp)
        )

        Spacer(modifier = Modifier.height(48.dp))

        // --- INPUT EMAIL ---
        PetMatchInput(
            value = email,
            onValueChange = { email = it },
            label = "E-mail",
            keyboardType = KeyboardType.Email,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // --- BOTÃO ENVIAR ---
        PetMatchButton(
            text = "Enviar Código",
            onClick = { viewModel.sendResetCode(email) },
            isLoading = uiState.isLoading,
            modifier = Modifier.width(200.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        // --- RODAPÉ ---
        Text(
            text = "Já possui uma Conta? Faça Login",
            color = PurpleText,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 32.dp)
                .clickable { onNavigateToLogin() }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ForgotPasswordPreview() {
    MaterialTheme {
        ForgotPasswordScreen(onNavigateToLogin = {}, onNavigateToVerificationCode = {})
    }
}