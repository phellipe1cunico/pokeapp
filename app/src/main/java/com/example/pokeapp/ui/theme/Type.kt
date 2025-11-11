package com.example.pokeapp.ui.theme // 1. Pacote corrigido

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * Define os estilos de tipografia para o aplicativo.
 * Esta variável 'Typography' é usada pelo 'Theme.kt'.
 */
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Outros estilos de texto (titleLarge, labelSmall, etc.) podem ser
       adicionados aqui se você quiser personalizá-los. */
)