package com.prismforge.visualizer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PrismForgeApp() {
    var state by remember { mutableStateOf(VisualizerState()) }
    val audioEngine = remember { AudioEngine() }

    DisposableEffect(Unit) {
        onDispose {
            audioEngine.stop()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF08090D))
            .padding(12.dp)
    ) {
        Text(
            text = "PRISM FORGE",
            color = Color.White
        )

        Spacer(Modifier.height(8.dp))

        VisualizerCanvas(
            state = state,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )

        ObjectSelector(
            selected = state.objectType,
            onSelected = {
                state = state.copy(objectType = it)
            }
        )

        Spacer(Modifier.height(8.dp))

        PresetSelector(
            selected = state.preset,
            onSelected = {
                state = state.copy(preset = it)
            }
        )

        Spacer(Modifier.height(8.dp))

        Text("GLOW", color = Color.White)
        Slider(
            value = state.glow,
            onValueChange = {
                state = state.copy(glow = it)
            }
        )

        Text("DEPTH", color = Color.White)
        Slider(
            value = state.depth,
            onValueChange = {
                state = state.copy(depth = it)
            }
        )

        Text("AUDIO", color = Color.White)
        Slider(
            value = state.audioIntensity,
            onValueChange = {
                state = state.copy(audioIntensity = it)
            }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    if (state.isPlaying) {
                        audioEngine.stop()
                    } else {
                        audioEngine.start()
                    }

                    state = state.copy(
                        isPlaying = !state.isPlaying
                    )
                }
            ) {
                Text(
                    if (state.isPlaying) "STOP" else "PLAY"
                )
            }
        }
    }
}
