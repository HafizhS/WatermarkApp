package com.example.watermarkapp

import android.graphics.Picture
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.draw
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview
import com.example.watermarkapp.ui.theme.WatermarkAppTheme
import com.example.watermarkapp.utils.saveToDisk
import com.example.watermarkapp.utils.toBitmap

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WatermarkAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background

                ) {
                    MainScreen()
                }
            }
        }
    }
}

// 3 Screen
// Capture -> Cooking -> Result
@Composable
fun MainScreen() {
    val bitmapResult = remember { Picture() }

    Row(
        Modifier
            .fillMaxSize()
            .drawWithCache {
                val width = this.size.width.toInt()
                val height = this.size.height.toInt()
                onDrawWithContent {
                    val bitmapCanvas = androidx.compose.ui.graphics.Canvas(
                        bitmapResult.beginRecording(
                            width,
                            height
                        )
                    )

                    draw(this, this.layoutDirection, bitmapCanvas, this.size) {
                        this@onDrawWithContent.drawContent()
                    }

                    bitmapResult.endRecording()

                    drawIntoCanvas {
                        it.nativeCanvas.drawPicture(bitmapResult)
                    }
                }
            },
        Arrangement.Absolute.Center,
        Alignment.CenterVertically
    ) {
        Watermark(onPrintClicked = {
            bitmapResult.toBitmap().saveToDisk()
        })
    }
}

@Composable
fun Watermark(onPrintClicked: () -> Unit) {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth(0.5f)
                .background(Color.Green), Alignment.Center
        ) {
            Column {
                Text(text = "Hello World")
                Button(onClick = onPrintClicked) {
                    Text(text = "Print")
                }
            }
        }
    }
    
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    WatermarkAppTheme {
        MainScreen()
    }
}

