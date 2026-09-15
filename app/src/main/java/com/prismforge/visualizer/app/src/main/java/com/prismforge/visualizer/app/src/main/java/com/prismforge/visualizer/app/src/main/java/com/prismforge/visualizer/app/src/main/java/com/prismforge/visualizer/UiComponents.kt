package com.prismforge.visualizer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ObjectSelector(
    selected: ObjectType,
    onSelected: (ObjectType) -> Unit
) {
    Row(
        modifier = androidx.compose.ui.Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ObjectType.entries.forEach { type ->
            FilterChip(
                selected = selected == type,
                onClick = { onSelected(type) },
                label = { Text(type.name) }
            )
        }
    }
}

@Composable
fun PresetSelector(
    selected: Preset,
    onSelected: (Preset) -> Unit
) {
    Row(
        modifier = androidx.compose.ui.Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Preset.entries.forEach { preset ->
            FilterChip(
                selected = selected == preset,
                onClick = { onSelected(preset) },
                label = {
                    Text(
                        preset.name.replace('_', ' ')
                    )
                }
            )
        }
    }
}
