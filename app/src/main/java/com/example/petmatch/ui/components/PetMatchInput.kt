package com.example.petmatch.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PetMatchInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String = "Input",
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    helperText: String? = null,
    modifier: Modifier = Modifier
) {
    var senhaVisivel by remember { mutableStateOf(false) }

    val LightPurpleBg = Color(0xFFF3E5F5)

    androidx.compose.foundation.layout.Column(modifier = modifier.fillMaxWidth()) {
        TextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            placeholder = { Text(placeholder) },
            modifier = Modifier.fillMaxWidth(),
            // Lógica: Se for senha, usa a lógica de senha.
            // Se não for senha, usa a transformation (CPF)
            visualTransformation = if (isPassword) {
                if (senhaVisivel) VisualTransformation.None else PasswordVisualTransformation()
            } else {
                visualTransformation
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = LightPurpleBg,
                unfocusedContainerColor = LightPurpleBg,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            trailingIcon = if (isPassword) {
                {
                    IconButton(onClick = { senhaVisivel = !senhaVisivel }) {
                        Icon(
                            imageVector = if (senhaVisivel) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = "Ver senha"
                        )
                    }
                }
            } else null
        )
        Text(
            text = helperText ?: "Informe seu $label",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 4.dp, top = 4.dp)
        )
    }
}