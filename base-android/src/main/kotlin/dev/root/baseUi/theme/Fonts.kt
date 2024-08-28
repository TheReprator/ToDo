package dev.root.baseUi.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import dev.root.baseUi.R

val DmSansFontFamily: FontFamily
  @Composable get() = FontFamily(
    Font(R.font.dm_sans_medium, weight = FontWeight.Medium),
    Font(R.font.dm_sans_regular, weight = FontWeight.Normal),
    Font(R.font.dm_sans_bold, weight = FontWeight.Bold),
  )
