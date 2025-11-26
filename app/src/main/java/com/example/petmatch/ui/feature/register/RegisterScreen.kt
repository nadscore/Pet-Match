package com.example.petmatch.ui.feature.register

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.petmatch.ui.components.CpfVisualTransformation
import com.example.petmatch.ui.components.PetMatchButton
import com.example.petmatch.ui.components.PetMatchInput

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = viewModel(),
    onNavigateToLogin: () -> Unit, // Volta para o Login ao terminar ou clicar no link
    onRegisterSuccess: () -> Unit  // Vai para Login
) {
    val uiState by viewModel.uiState.collectAsState()

    var nome by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    val context = LocalContext.current
    val OrangeLogo = Color(0xFFD99227)
    val PurpleText = Color(0xFF6A1B9A)

    LaunchedEffect(uiState.registerSuccess) {
        if (uiState.registerSuccess) {
            Toast.makeText(context, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show()
            onRegisterSuccess()
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
            .padding(vertical = 24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(48.dp))

        // --- LOGO ---
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(bottom = 0.dp)
        ) {
            Text("PetM", fontSize = 64.sp, fontWeight = FontWeight.Bold, color = OrangeLogo)
            Icon(
                imageVector = Icons.Default.Pets,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(48.dp).padding(top = 14.dp, start = 2.dp, end = 2.dp)
            )
            Text("tch", fontSize = 64.sp, fontWeight = FontWeight.Bold, color = OrangeLogo)
        }

        Spacer(modifier = Modifier.height(32.dp))

        // --- CAMPOS DE CADASTRO ---

        // Nome Completo
        PetMatchInput(
            value = nome,
            onValueChange = { nome = it },
            label = "Nome Completo",
            modifier = Modifier.padding(horizontal = 48.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // CPF
        PetMatchInput(
            value = cpf,
            onValueChange = { novoValor ->
                if (novoValor.length <= 11 && novoValor.all { it.isDigit() }) {
                    cpf = novoValor
                }
            },
            label = "CPF",
            keyboardType = KeyboardType.Number,
            visualTransformation = CpfVisualTransformation(),
            modifier = Modifier.padding(horizontal = 48.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // E-mail
        PetMatchInput(
            value = email,
            onValueChange = { email = it },
            label = "E-mail",
            keyboardType = KeyboardType.Email,
            modifier = Modifier.padding(horizontal = 48.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Senha
        PetMatchInput(
            value = senha,
            onValueChange = { senha = it },
            label = "Senha",
            isPassword = true,
            modifier = Modifier.padding(horizontal = 48.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- BOTÃO REGISTRAR ---
        PetMatchButton(
            text = "Registrar",
            onClick = { viewModel.register(nome, cpf, email, senha) },
            isLoading = uiState.isLoading,
            modifier = Modifier.width(200.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        // --- RODAPÉ ---
        Text(
            text = "Já possui uma conta? Faça login",
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
fun RegisterScreenPreview() {
    MaterialTheme {
        RegisterScreen(onNavigateToLogin = {}, onRegisterSuccess = {})
    }
}