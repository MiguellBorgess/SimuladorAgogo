package com.example.simuladoragogo.ui.shape

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

/**
 * Cria uma Shape de trapézio definindo a posição horizontal de cada um dos 4 cantos.
 * Os valores de ratio são de 0.0 (extrema esquerda) a 1.0 (extrema direita).
 */
class FourPointTrapezoidShape(
    private val topLeftXRatio: Float,
    private val topRightXRatio: Float,
    private val bottomLeftXRatio: Float,
    private val bottomRightXRatio: Float
) : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val path = Path().apply {
            moveTo(size.width * topLeftXRatio, 0f)
            lineTo(size.width * topRightXRatio, 0f)
            lineTo(size.width * bottomRightXRatio, size.height)
            lineTo(size.width * bottomLeftXRatio, size.height)
            close()
        }
        return Outline.Generic(path)
    }
}
