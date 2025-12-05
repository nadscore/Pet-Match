package com.example.petmatch.ui.feature.forgotpassword

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.petmatch.ui.components.PetMatchButton
import com.example.petmatch.ui.components.PetMatchInput

@Composable
fun NewPasswordScreen(
    viewModel: NewPasswordViewModel = viewModel(),
    onResetSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    var senha by remember { mutableStateOf("") }
    var confirmarSenha by remember { mutableStateOf("") }

    val context = LocalContext.current
    val OrangeLogo = Color(0xFFD99227)

    LaunchedEffect(uiState.isResetComplete) {
        if (uiState.isResetComplete) {
            Toast.makeText(context, "Senha alterada! Entrando...", Toast.LENGTH_SHORT).show()
            onResetSuccess()
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

        // --- ÍCONE ---
        Icon(
            imageVector = Icons.Default.Pets,
            contentDescription = "Logo",
            tint = OrangeLogo,
            modifier = Modifier.size(180.dp)
        )

        Spacer(modifier = Modifier.height(48.dp))

        // --- CAMPO NOVA SENHA ---
        PetMatchInput(
            value = senha,
            onValueChange = { senha = it },
            label = "Nova Senha",
            helperText = "Digite uma Nova Senha",
            isPassword = true,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- CAMPO CONFIRMAR SENHA ---
        PetMatchInput(
            value = confirmarSenha,
            onValueChange = { confirmarSenha = it },
            label = "Confirme sua Senha",
            helperText = "Confirme a Nova Senha",
            isPassword = true,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // --- BOTÃO ENTRAR ---
        PetMatchButton(
            text = "Entrar",
            onClick = { viewModel.resetPassword(senha, confirmarSenha) },
            isLoading = uiState.isLoading,
            modifier = Modifier.width(200.dp)
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
fun NewPasswordPreview() {
    MaterialTheme {
        NewPasswordScreen(onResetSuccess = {})
    }
}