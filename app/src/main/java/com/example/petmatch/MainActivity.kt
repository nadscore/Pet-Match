package com.example.petmatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.petmatch.navigation.NavGraph
import com.example.petmatch.ui.feature.login.LoginScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavGraph()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    // Aqui a gente cria uma visualização "falsa" só para ver o layout
    // O ViewModel vai tentar iniciar, então o preview pode reclamar de rede,
    // mas o desenho deve aparecer.
    MaterialTheme {
        LoginScreen(
            onNavigateToHome = {},
            onNavigateToRegister = {},
            onNavigateToForgotPassword = {}
        )
    }
}