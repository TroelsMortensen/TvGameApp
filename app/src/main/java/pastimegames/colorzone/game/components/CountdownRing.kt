package pastimegames.colorzone.game.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CountdownRing(
    size: Dp,
    sectorCount: Int,
    visibleCount: Int,
    color: Color,
    modifier: Modifier = Modifier,
    ringThickness: Dp = 12.dp,
    gapDegrees: Float = 2f,
) {
    Canvas(modifier = modifier.size(size)) {
        val canvasSize = this.size.minDimension
        val center = Offset(canvasSize / 2f, canvasSize / 2f)
        val outerRadius = canvasSize / 2f
        val innerRadius = outerRadius - ringThickness.toPx()
        val sweepPerSector = 360f / sectorCount
        val drawSweep = sweepPerSector - gapDegrees

        for (index in 0 until visibleCount) {
            val startAngle = -90f + index * sweepPerSector + gapDegrees / 2f
            val outerRect = Rect(
                left = center.x - outerRadius,
                top = center.y - outerRadius,
                right = center.x + outerRadius,
                bottom = center.y + outerRadius,
            )
            val innerRect = Rect(
                left = center.x - innerRadius,
                top = center.y - innerRadius,
                right = center.x + innerRadius,
                bottom = center.y + innerRadius,
            )

            val path = Path().apply {
                arcTo(
                    rect = outerRect,
                    startAngleDegrees = startAngle,
                    sweepAngleDegrees = drawSweep,
                    forceMoveTo = false,
                )
                arcTo(
                    rect = innerRect,
                    startAngleDegrees = startAngle + drawSweep,
                    sweepAngleDegrees = -drawSweep,
                    forceMoveTo = false,
                )
                close()
            }
            drawPath(path, color)
        }
    }
}
