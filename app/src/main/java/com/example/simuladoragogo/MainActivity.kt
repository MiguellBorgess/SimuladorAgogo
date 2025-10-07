package com.example.simuladoragogo

import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {

    private lateinit var soundPool: SoundPool
    private var sound1: Int = 0
    private var sound2: Int = 0
    private var sound3: Int = 0
    private var sound4: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configurar SoundPool (bom para efeitos curtos)
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(4)
            .setAudioAttributes(audioAttributes)
            .build()

        // CARREGAR SONS de res/raw (garanta que os arquivos estejam renomeados conforme instrução)
        // Ex.: app/src/main/res/raw/sound1.wav  -> R.raw.sound1
        sound1 = soundPool.load(this, R.raw.sound1, 1)
        sound2 = soundPool.load(this, R.raw.sound2, 1)
        sound3 = soundPool.load(this, R.raw.sound3, 1)
        sound4 = soundPool.load(this, R.raw.sound4, 1)

        setContent {
            // Evitamos usar um tema customizado que possa não existir (isso evita o "Unresolved reference 'ui'").
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TelaPrincipal(
                        onBoca1 = { tocar(sound1) },
                        onBoca2 = { tocar(sound2) },
                        onBoca3 = { tocar(sound3) },
                        onBoca4 = { tocar(sound4) }
                    )
                }
            }
        }
    }

    private fun tocar(soundId: Int) {
        // volume left, right = 1f, priority 0, loop 0 (não repetir), rate 1f (normal)
        soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }
}

@Composable
fun TelaPrincipal(
    onBoca1: () -> Unit,
    onBoca2: () -> Unit,
    onBoca3: () -> Unit,
    onBoca4: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = onBoca1, modifier = Modifier.fillMaxWidth()) { Text("Boca 1") }
        Button(onClick = onBoca2, modifier = Modifier.fillMaxWidth()) { Text("Boca 2") }
        Button(onClick = onBoca3, modifier = Modifier.fillMaxWidth()) { Text("Boca 3") }
        Button(onClick = onBoca4, modifier = Modifier.fillMaxWidth()) { Text("Boca 4") }
    }
}

@Preview(showBackground = true)
@Composable
fun TelaPreview() {
    MaterialTheme {
        Surface {
            TelaPrincipal({}, {}, {}, {})
        }
    }
}
