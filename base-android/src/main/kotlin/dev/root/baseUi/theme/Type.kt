package dev.root.baseUi.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val AppTypography: Typography
    @Composable get() {
        // Eugh, this is gross but there is no defaultFontFamily property in M3
        val default = Typography()
        val fontFamily = DmSansFontFamily
        return Typography(
            displayLarge = default.displayLarge.copy(fontFamily = fontFamily, letterSpacing = 0.sp),
            displayMedium = default.displayMedium.copy(fontFamily = fontFamily, letterSpacing = 0.sp),
            displaySmall = default.displaySmall.copy(fontFamily = fontFamily, letterSpacing = 0.sp),
            headlineLarge = default.headlineLarge.copy(fontFamily = fontFamily, letterSpacing = 0.sp),
            headlineMedium = default.headlineMedium.copy(fontFamily = fontFamily, letterSpacing = 0.sp),
            headlineSmall = default.headlineSmall.copy(fontFamily = fontFamily, letterSpacing = 0.sp),
            titleLarge = default.titleLarge.copy(fontFamily = fontFamily, letterSpacing = 0.sp),
            titleMedium = default.titleMedium.copy(fontFamily = fontFamily, letterSpacing = 0.sp),
            titleSmall = default.titleSmall.copy(fontFamily = fontFamily, letterSpacing = 0.sp),
            bodyLarge = default.bodyLarge.copy(fontFamily = fontFamily, letterSpacing = 0.sp),
            bodyMedium = default.bodyMedium.copy(fontFamily = fontFamily, letterSpacing = 0.sp),
            bodySmall = default.bodySmall.copy(fontFamily = fontFamily, letterSpacing = 0.sp),
            labelLarge = default.labelLarge.copy(fontFamily = fontFamily, letterSpacing = 0.sp),
            labelMedium = default.labelMedium.copy(fontFamily = fontFamily, letterSpacing = 0.sp),
            labelSmall = default.labelSmall.copy(fontFamily = fontFamily, letterSpacing = 0.sp),
        )
    }