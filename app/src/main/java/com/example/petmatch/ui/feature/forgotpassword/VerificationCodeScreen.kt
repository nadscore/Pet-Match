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
fun VerificationCodeScreen(
    viewModel: VerificationCodeViewModel = viewModel(),
    onCodeVerified: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var code by remember { mutableStateOf("") }
    val context = LocalContext.current

    val OrangeLogo = Color(0xFFD99227)
    val PurpleText = Color(0xFF6A1B9A)

    LaunchedEffect(uiState.isCodeVerified) {
        if (uiState.isCodeVerified) {
            Toast.makeText(context, "Código aceito!", Toast.LENGTH_SHORT).show()
            onCodeVerified()
        }
    }

    LaunchedEffect(uiState.successMessage) {
        if (uiState.successMessage != null && !uiState.isCodeVerified) {
            Toast.makeText(context, uiState.successMessage, Toast.LENGTH_SHORT).show()
            viewModel.clearMessages()
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

        // --- ÍCONE GRANDE ---
        Icon(
            imageVector = Icons.Default.Pets,
            contentDescription = "Logo PetMatch",
            tint = OrangeLogo,
            modifier = Modifier.size(180.dp)
        )

        Spacer(modifier = Modifier.height(48.dp))

        // --- INPUT CÓDIGO ---
        PetMatchInput(
            value = code,
            onValueChange = { code = it },
            label = "Código de Verificação",
            keyboardType = KeyboardType.Number,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // --- BOTÃO ---
        PetMatchButton(
            text = "Redefinir Senha",
            onClick = { viewModel.validateCode(code) },
            isLoading = uiState.isLoading,
            modifier = Modifier.width(200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- LINK REENVIAR ---
        Text(
            text = "Reenviar Código",
            color = PurpleText,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.clickable { viewModel.resendCode() }
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
fun VerificationCodePreview() {
    MaterialTheme {
        VerificationCodeScreen(onCodeVerified = {})
    }
}