package com.prismforge.visualizer

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun VisualizerCanvas(
    state: VisualizerState,
    modifier: Modifier = Modifier
) {
    var animation by remember { mutableFloatStateOf(0f) }

    Canvas(
        modifier = modifier.fillMaxSize()
    ) {
        val cx = size.width / 2f
        val cy = size.height / 2f
        val radius = minOf(size.width, size.height) * 0.22f

        animation += 0.025f

        val rotation = state.rotation + animation * 40f
        val pulse = 1f + sin(animation * 4f) * 0.08f * state.audioIntensity
        val r = radius * pulse

        when (state.objectType) {
            ObjectType.CUBE -> {
                drawCube(
                    center = Offset(cx, cy),
                    radius = r,
                    rotation = rotation,
                    glow = state.glow
                )
            }

            ObjectType.SPHERE -> {
                drawSphere(
                    center = Offset(cx, cy),
                    radius = r,
                    glow = state.glow
                )
            }

            ObjectType.TORUS -> {
                drawTorus(
                    center = Offset(cx, cy),
                    radius = r,
                    glow = state.glow
                )
            }

            ObjectType.WAVE -> {
                drawWave(
                    centerY = cy,
                    radius = r,
                    intensity = state.audioIntensity
                )
            }
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawCube(
    center: Offset,
    radius: Float,
    rotation: Float,
    glow: Float
) {
    val a = Math.toRadians(rotation.toDouble())
    val c = cos(a).toFloat()
    val s = sin(a).toFloat()

    fun rotate(x: Float, y: Float): Offset {
        return Offset(
            center.x + x * c - y * s,
            center.y + x * s + y * c
        )
    }

    val d = radius * 0.65f

    val front = listOf(
        rotate(-d, -d),
        rotate(d, -d),
        rotate(d, d),
        rotate(-d, d)
    )

    val back = listOf(
        rotate(-d * 0.65f, -d * 0.65f),
        rotate(d * 0.65f, -d * 0.65f),
        rotate(d * 0.65f, d * 0.65f),
        rotate(-d * 0.65f, d * 0.65f)
    )

    val alpha = 0.45f + glow * 0.55f
    val color = Color(0xFF66E6FF).copy(alpha = alpha)

    for (i in 0..3) {
        drawLine(
            color,
            front[i],
            front[(i + 1) % 4],
            strokeWidth = 5f
        )

        drawLine(
            color,
            back[i],
            back[(i + 1) % 4],
            strokeWidth = 3f
        )

        drawLine(
            color,
            front[i],
            back[i],
            strokeWidth = 3f
        )
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawSphere(
    center: Offset,
    radius: Float,
    glow: Float
) {
    val color = Color(0xFFB66DFF).copy(alpha = 0.55f + glow * 0.45f)

    drawCircle(
        color = color,
        radius = radius,
        center = center,
        style = Stroke(width = 4f)
    )

    for (i in 1..5) {
        val scale = i / 6f
        drawOval(
            color = color.copy(alpha = 0.35f),
            topLeft = Offset(
                center.x - radius * scale,
                center.y - radius
            ),
            size = androidx.compose.ui.geometry.Size(
                radius * 2f * scale,
                radius * 2f
            ),
            style = Stroke(width = 2f)
        )
    }

    drawLine(
        color,
        Offset(center.x - radius, center.y),
        Offset(center.x + radius, center.y),
        strokeWidth = 2f
    )
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawTorus(
    center: Offset,
    radius: Float,
    glow: Float
) {
    val color = Color(0xFFFF5FB7).copy(alpha = 0.55f + glow * 0.45f)

    drawCircle(
        color = color,
        radius = radius,
        center = center,
        style = Stroke(width = radius * 0.22f)
    )

    drawCircle(
        color = color.copy(alpha = 0.45f),
        radius = radius * 0.48f,
        center = center,
        style = Stroke(width = 2f)
    )
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawWave(
    centerY: Float,
    radius: Float,
    intensity: Float
) {
    val color = Color(0xFF72FF9B)
    val left = size.width * 0.08f
    val right = size.width * 0.92f
    val points = 120

    var previous = Offset(left, centerY)

    for (i in 1..points) {
        val x = left + (right - left) * i / points
        val t = i / points.toFloat()
        val y = centerY +
                sin(t * Math.PI * 8.0).toFloat() *
                radius * 0.35f *
                (0.5f + intensity)

        val current = Offset(x, y)

        drawLine(
            color = color,
            start = previous,
            end = current,
            strokeWidth = 4f,
            cap = StrokeCap.Round
        )

        previous = current
    }
}
