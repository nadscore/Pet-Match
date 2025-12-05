package com.example.petmatch.ui.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val OrangeLogo = Color(0xFFD99227)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // --- 1. LOGO ---
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("PetM", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = OrangeLogo)
            Icon(
                imageVector = Icons.Default.Pets,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(24.dp).padding(top = 6.dp)
            )
            Text("tch", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = OrangeLogo)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- 2. CONTEÚDO CENTRAL (Card ou Vazio) ---
        if (uiState.isLoading) {
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = OrangeLogo)
            }
        } else if (uiState.pets.isNotEmpty()) {
            // Se tem pets, pega sempre o primeiro da lista (o índice 0)
            // pois o ViewModel vai removendo eles da lista
            val currentPet = uiState.pets[0]

            PetCard(
                pet = currentPet,
                modifier = Modifier.weight(1f)
            )
        } else {
            // --- TELA DE "ACABOU" ---
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Pets, null, tint = Color.LightGray, modifier = Modifier.size(64.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Não há mais pets disponíveis no momento.", color = Color.Gray, textAlign = TextAlign.Center)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- 3. BOTÕES (Só mostra se tiver pets) ---
        if (uiState.pets.isNotEmpty()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Botão DISLIKE (Vermelho)
                ActionButton(
                    icon = Icons.Default.Close,
                    baseColor = Color(0xFFE1BEE7),
                    activeColor = Color(0xFFFFCDD2),
                    isActive = uiState.currentAction == SwipeAction.DISLIKE,
                    onClick = { viewModel.onDislike() }
                )

                // Botão LIKE (Verde)
                ActionButton(
                    icon = Icons.Default.Check,
                    baseColor = Color(0xFFE1BEE7),
                    activeColor = Color(0xFFC8E6C9),
                    isActive = uiState.currentAction == SwipeAction.LIKE,
                    onClick = { viewModel.onLike() }
                )
            }
        } else {
            // Espaço vazio pra manter o layout se não tiver botões
            Spacer(modifier = Modifier.height(60.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- 4. ÍCONE RODAPÉ ---
        Icon(
            imageVector = Icons.Default.Pets,
            contentDescription = "Menu",
            tint = OrangeLogo,
            modifier = Modifier.size(48.dp)
        )
    }

    // --- DIALOG ---
    if (uiState.showMatchDialog) {
        MatchDialog(onDismiss = { viewModel.onDismissDialog() })
    }
}

@Composable
fun PetCard(pet: com.example.petmatch.model.Pet, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Cabeçalho do Card
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFFFCC80)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Pets, contentDescription = null, tint = Color.White)
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(text = pet.nome, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(text = "Online", color = Color.Green, fontSize = 12.sp)
                }

                Icon(Icons.Default.MoreVert, contentDescription = "Mais", tint = Color.Gray)
            }

            Spacer(modifier = Modifier.height(12.dp))

            AsyncImage(
                model = pet.image_uri,
                contentDescription = "Foto do Pet",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Detalhes
            Text(
                text = "${pet.nome}, ${pet.idade}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = "Cachorro", fontSize = 14.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = pet.descricao, fontSize = 14.sp, color = Color.DarkGray)
        }
    }
}

@Composable
fun ActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    baseColor: Color,
    activeColor: Color,
    isActive: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isActive) activeColor else baseColor
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.size(width = 120.dp, height = 60.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun MatchDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = { },
        containerColor = Color.White,
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("☺", fontSize = 32.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Parabéns! \uD83C\uDF89", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
        },
        text = {
            Text(
                text = "Seu pedido de adoção foi enviado.\nNossa equipe vai te procurar em até 1 dia útil.",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF3E5F5)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Concluir", color = Color(0xFF6A1B9A))
            }
        }
    )
}