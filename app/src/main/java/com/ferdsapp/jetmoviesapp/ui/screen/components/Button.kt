import android.view.MotionEvent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextPainter.paint
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize

@Composable
fun PixelButton(
    text: String,
    modifier: Modifier = Modifier,
    pixelSize: Dp = 6.dp,
    bgColor: Color = Color(0xFF7FDBFF),
    borderColor: Color = Color(0xFF1A9BE6),
    highlightColor: Color = Color.White,
    shadowColor: Color = Color(0xFF185A7A),
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        isPressed = true
                        true
                    }

                    MotionEvent.ACTION_UP -> {
                        isPressed = false
                        onClick()
                        true
                    }

                    else -> false
                }
            }
    ) {

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            val px = pixelSize.toPx()
            val cols = (size.width / px).toInt()
            val rows = (size.height / px).toInt()

            for (r in 0 until rows) {
                for (c in 0 until cols) {

                    val left = c * px
                    val top = r * px

                    val color = when {
                        // Outer border
                        r == 0 || r == rows - 1 || c == 0 || c == cols - 1 ->
                            borderColor

                        // Inner border
                        r == 1 || r == rows - 2 || c == 1 || c == cols - 2 ->
                            highlightColor

                        // Center
                        else -> bgColor
                    }

                    val finalColor = if (isPressed) color.copy(alpha = 0.75f) else color

                    drawRect(
                        color = finalColor,
                        topLeft = Offset(left, top),
                        size = Size(px, px)
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


// helper extension: konversi Compose Color -> android.graphics.Paint via argb int
private fun androidx.compose.ui.graphics.Color.toArgbColor(): android.graphics.Paint {
    val paint = android.graphics.Paint().apply {
        isAntiAlias = false // supaya benar-benar blocky / pixel-y
        style = android.graphics.Paint.Style.FILL
        color = android.graphics.Color.argb(
            (alpha * 255).toInt(),
            (red * 255).toInt(),
            (green * 255).toInt(),
            (blue * 255).toInt()
        )
    }
    return paint
}

@Preview(showBackground = true, widthDp = 360)
@Composable
fun PixelButtonPreview() {
    Column(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        PixelButton(
            text = "PLAY",
            pixelSize = 6.dp,
            onClick = { /* aksi */ }
        )
        PixelButton(
            text = "START",
            pixelSize = 4.dp,
            bgColor = Color(0xFFFFE07A),
            borderColor = Color(0xFFD88B00),
            highlightColor = Color.White,
            shadowColor = Color(0xFF8B5E00),
            onClick = { /* aksi */ }
        )
    }
}
