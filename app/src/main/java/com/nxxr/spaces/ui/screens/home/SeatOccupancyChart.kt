package com.nxxr.spaces.ui.screens.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OccupancyGauge(
    occupancySeatCount: Int,
    modifier: Modifier = Modifier,
    backgroundColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.surfaceVariant,
    filledColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.primary
) {
    val occupancyPercentage = occupancySeatCount.toFloat() / 38

    Box(
        modifier = modifier
            .aspectRatio(2f)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val sweep = 180f
            val strokeWidth = size.height / 3
            val rect = Rect(0f, 0f, size.width, size.height * 2)

            drawArc(
                color = backgroundColor,
                startAngle = 180f,
                sweepAngle = sweep,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                topLeft = Offset.Zero,
                size = rect.size
            )

            drawArc(
                color = filledColor,
                startAngle = 180f,
                sweepAngle = sweep * occupancyPercentage,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                topLeft = Offset.Zero,
                size = rect.size
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 12.dp)
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = "${(occupancyPercentage * 100).toInt()}%",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Occupied",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview
@Composable
fun ChartPreview() {
    OccupancyGauge(occupancySeatCount = 16)
}
