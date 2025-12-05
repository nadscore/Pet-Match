package com.example.petmatch.ui.feature.petregister

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.petmatch.ui.components.PetMatchButton
import com.example.petmatch.ui.components.PetMatchInput
import com.example.petmatch.util.ImageUtils

@Composable
fun PetRegisterScreen(
    viewModel: PetRegisterViewModel = viewModel(),
    onLogout: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    var nome by remember { mutableStateOf("") }
    var idade by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var imagem_uri by remember { mutableStateOf<Uri?>(null) }

    // Configurador da Galeria
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> imagem_uri = uri } // Atualiza a variável certa
    )

    LaunchedEffect(uiState.successMessage) {
        if (uiState.successMessage != null) {
            Toast.makeText(context, uiState.successMessage, Toast.LENGTH_LONG).show()
            nome = ""; idade = ""; descricao = ""; imagem_uri = null
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
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Área Administrativa", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFFD99227))
        Text("Cadastrar Novo Pet", fontSize = 16.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(32.dp))

        PetMatchInput(value = nome, onValueChange = { nome = it }, label = "Nome do Pet", helperText = "Informe o Nome do Pet")
        Spacer(modifier = Modifier.height(16.dp))

        PetMatchInput(value = idade, onValueChange = { idade = it }, label = "Idade", keyboardType = KeyboardType.Number, helperText = "Informe a Idade do Pet")
        Spacer(modifier = Modifier.height(16.dp))

        PetMatchInput(value = descricao, onValueChange = { descricao = it }, label = "Descrição", helperText = "Informe um pouco sobre o Pet")

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFF3E5F5))
                .border(1.dp, Color(0xFF6A1B9A), RoundedCornerShape(8.dp))
                .clickable {
                    // Abre a galeria
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            if (imagem_uri != null) {
                AsyncImage(
                    model = imagem_uri,
                    contentDescription = "Foto selecionada",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.AddPhotoAlternate,
                        contentDescription = "Adicionar Foto",
                        tint = Color(0xFF6A1B9A),
                        modifier = Modifier.size(48.dp)
                    )
                    Text("Toque para adicionar foto", color = Color(0xFF6A1B9A))
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // --- BOTÃO SALVAR ---
        PetMatchButton(
            text = "Salvar Pet",
            onClick = {
                if (imagem_uri != null) {
                    // CONVERSÃO: Uri -> Base64
                    val base64Image = ImageUtils.uriToBase64(context, imagem_uri!!)

                    if (base64Image != null) {
                        viewModel.savePet(nome, idade, descricao, base64Image)
                    } else {
                        Toast.makeText(context, "Erro ao processar imagem", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Selecione uma foto!", Toast.LENGTH_SHORT).show()
                }
            },
            isLoading = uiState.isLoading,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onLogout,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB71C1C)),
            modifier = Modifier.fillMaxWidth()) {
            Text("Sair / Logout")
        }

        Spacer(modifier = Modifier.height(48.dp))
    }
}