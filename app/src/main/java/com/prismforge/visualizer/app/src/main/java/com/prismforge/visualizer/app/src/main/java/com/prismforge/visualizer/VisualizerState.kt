package com.prismforge.visualizer

enum class ObjectType {
    CUBE,
    SPHERE,
    TORUS,
    WAVE
}

enum class Preset {
    NEON_ORBIT,
    PULSE_TUNNEL,
    CRYSTAL_WAVE
}

data class VisualizerState(
    val objectType: ObjectType = ObjectType.CUBE,
    val preset: Preset = Preset.NEON_ORBIT,
    val rotation: Float = 0f,
    val glow: Float = 0.7f,
    val depth: Float = 0.6f,
    val audioIntensity: Float = 0.8f,
    val isPlaying: Boolean = false
)
