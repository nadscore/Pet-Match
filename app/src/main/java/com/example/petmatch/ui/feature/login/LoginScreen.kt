package com.example.petmatch.ui.feature.login

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
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onNavigateToHome: (String) -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateToForgotPassword: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    val context = LocalContext.current
    val OrangeLogo = Color(0xFFD99227)
    val PurpleText = Color(0xFF6A1B9A)

    LaunchedEffect(uiState.loginSuccess) {
        if (uiState.loginSuccess) {
            val userEmail = uiState.usuario?.email ?: ""
            onNavigateToHome(userEmail)
        }
    }

    LaunchedEffect(uiState.errorMessage) {
        if (uiState.errorMessage != null) {
            Toast.makeText(context, uiState.errorMessage, Toast.LENGTH_SHORT).show()
            viewModel.clearError()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 24.dp), // Apenas vertical
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        // --- LOGO ---
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(bottom = 0.dp)
        ) {
            Text(
                text = "PetM",
                fontSize = 64.sp,
                fontWeight = FontWeight.Bold,
                color = OrangeLogo
            )
            Icon(
                imageVector = Icons.Default.Pets,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier
                    .size(48.dp)
                    .padding(top = 14.dp, start = 2.dp, end = 2.dp)
            )
            Text(
                text = "tch",
                fontSize = 64.sp,
                fontWeight = FontWeight.Bold,
                color = OrangeLogo
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- CAMPO EMAIL ---
        PetMatchInput(
            value = email,
            onValueChange = { email = it },
            label = "E-mail",
            keyboardType = KeyboardType.Email,
            modifier = Modifier.padding(horizontal = 48.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- CAMPO SENHA ---
        PetMatchInput(
            value = senha,
            onValueChange = { senha = it },
            label = "Senha",
            isPassword = true,
            helperText = "Informa sua Senha",
            modifier = Modifier.padding(horizontal = 48.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- BOTÃO ---
        PetMatchButton(
            text = "Entrar",
            onClick = { viewModel.login(email, senha) },
            isLoading = uiState.isLoading,
            modifier = Modifier.width(200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Esqueci minha senha",
            color = PurpleText,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.clickable {onNavigateToForgotPassword()}
        )

        Spacer(modifier = Modifier.weight(1f))

        // --- RODAPÉ ---
        Text(
            text = "Ainda não possui uma conta? Cadastre-se",
            color = PurpleText,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 32.dp)
                .clickable { onNavigateToRegister() }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen(onNavigateToHome = {}, onNavigateToRegister = {}, onNavigateToForgotPassword = {})
    }
}