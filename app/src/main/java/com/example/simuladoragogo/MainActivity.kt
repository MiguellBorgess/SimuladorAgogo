package com.example.simuladoragogo

import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

// Classe principal da tela
class MainActivity : ComponentActivity() {

    // Variáveis do reprodutor de som
    private lateinit var soundPool: SoundPool
    private var sound1: Int = 0
    private var sound2: Int = 0
    private var sound3: Int = 0
    private var sound4: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configura os atributos de áudio
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        // Cria o SoundPool
        soundPool = SoundPool.Builder()
            .setMaxStreams(4)
            .setAudioAttributes(audioAttributes)
            .build()

        // Carrega os sons da pasta res/raw
        sound1 = soundPool.load(this, R.raw.sound1, 1)
        sound2 = soundPool.load(this, R.raw.sound2, 1)
        sound3 = soundPool.load(this, R.raw.sound3, 1)
        sound4 = soundPool.load(this, R.raw.sound4, 1)

        // Define a interface da tela
        setContent {
            var usarImagem by remember { mutableStateOf(true) }
            var darkMode by remember { mutableStateOf(false) }

            MaterialTheme(colorScheme = if (darkMode) darkColorScheme() else lightColorScheme()) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Linha no topo da tela para os toggles
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Toggle da esquerda (imagem ↔ botões)
                            Switch(
                                checked = usarImagem,
                                onCheckedChange = { usarImagem = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                                    uncheckedThumbColor = MaterialTheme.colorScheme.secondary
                                )
                            )

                            // Toggle da direita (modo claro ↔ escuro)
                            Switch(
                                checked = darkMode,
                                onCheckedChange = { darkMode = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                                    uncheckedThumbColor = MaterialTheme.colorScheme.secondary
                                )
                            )
                        }

                        // Decide qual tela mostrar
                        if (usarImagem) {
                            TelaComImagem(
                                onBoca1 = { tocar(sound1) },
                                onBoca2 = { tocar(sound2) },
                                onBoca3 = { tocar(sound3) },
                                onBoca4 = { tocar(sound4) }
                            )
                        } else {
                            TelaComBotoes(
                                onBoca1 = { tocar(sound1) },
                                onBoca2 = { tocar(sound2) },
                                onBoca3 = { tocar(sound3) },
                                onBoca4 = { tocar(sound4) }
                            )
                        }
                    }
                }
            }
        }
    }

    private fun tocar(soundId: Int) {
        soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }
}

// --- Tela com BOTÕES ---
@Composable
fun TelaComBotoes(
    onBoca1: () -> Unit, onBoca2: () -> Unit, onBoca3: () -> Unit, onBoca4: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = onBoca4, modifier = Modifier.size(180.dp)) { Text("4") }
            Button(onClick = onBoca3, modifier = Modifier.size(180.dp)) { Text("3") }
            Button(onClick = onBoca2, modifier = Modifier.size(180.dp)) { Text("2") }
            Button(onClick = onBoca1, modifier = Modifier.size(180.dp)) { Text("1") }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}


// --- NOVA SHAPE CONFIGURÁVEL ---
/**
 * Cria uma Shape de trapézio onde você pode configurar as proporções.
 * @param topWidthRatio Proporção da largura do topo (ex: 0.8f significa 80% da largura total).
 * @param bottomWidthRatio Proporção da largura da base (ex: 1.0f significa 100% da largura total).
 */
class ConfigurableTrapezoidShape(
    private val topWidthRatio: Float,
    private val bottomWidthRatio: Float
) : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val topWidth = size.width * topWidthRatio
        val bottomWidth = size.width * bottomWidthRatio

        // Calcula os pontos X para centralizar as linhas
        val topStartX = (size.width - topWidth) / 2
        val bottomStartX = (size.width - bottomWidth) / 2

        val path = Path().apply {
            moveTo(topStartX, 0f) // Canto superior esquerdo
            lineTo(topStartX + topWidth, 0f) // Canto superior direito
            lineTo(bottomStartX + bottomWidth, size.height) // Canto inferior direito
            lineTo(bottomStartX, size.height) // Canto inferior esquerdo
            close()
        }
        return Outline.Generic(path)
    }
}


// --- TELA COM IMAGEM (VERSÃO COM SHAPES EDITÁVEIS) ---
// --- TELA COM IMAGEM (VERSÃO COM PREVISÕES AJUSTADAS) ---
@Composable
fun TelaComImagem(
    onBoca1: () -> Unit, onBoca2: () -> Unit, onBoca3: () -> Unit, onBoca4: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center // Mantém o centro como referência
    ) {
        Image(
            painter = painterResource(id = R.drawable.agogonobg),
            contentDescription = "Agogô",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        // Boca 4 (verde) – PREVISÃO
        Box(
            modifier = Modifier
                .offset(x = (-210).dp, y = (50).dp)
                .rotate(120f)
                .size(width = 190.dp, height = 350.dp)
                .clip(ConfigurableTrapezoidShape(topWidthRatio = 0.3f, bottomWidthRatio = 1.0f))
                .background(Color.Green.copy(alpha = 0.4f))
                .clickable { onBoca4() }
        )

        // Boca 3 (vermelha) – PREVISÃO
        Box(
            modifier = Modifier
                .offset(x = (-40).dp, y = (-30).dp)
                .rotate(157f)
                .size(width = 230.dp, height = 280.dp)
                .clip(ConfigurableTrapezoidShape(topWidthRatio = 0.3f, bottomWidthRatio = 1.0f))
                .background(Color.Red.copy(alpha = 0.4f))
                .clickable { onBoca3() }
        )

        // Boca 2 (azul) – PREVISÃO
        Box(
            modifier = Modifier
                .offset(x = (160).dp, y = (-10).dp)
                .rotate(-155f)
                .size(width = 190.dp, height = 260.dp)
                .clip(ConfigurableTrapezoidShape(topWidthRatio = 0.3f, bottomWidthRatio = 1.0f))
                .background(Color.Blue.copy(alpha = 0.4f))
                .clickable { onBoca2() }
        )

        // Boca 1 (amarela) – SEUS VALORES IDEAIS
        Box(
            modifier = Modifier
                .offset(x = 270.dp, y = (95).dp)
                .rotate(-118f)
                .size(width = 140.dp, height = 260.dp)
                .clip(ConfigurableTrapezoidShape(topWidthRatio = 0.3f, bottomWidthRatio = 1.0f))
                .background(Color.Yellow.copy(alpha = 0.4f))
                .clickable { onBoca1() }
        )
    }
}


// Preview (só aparece no Android Studio, não no app real)
@Preview(showBackground = true)
@Composable
fun TelaPreview() {
    MaterialTheme {
        Surface {
            TelaComBotoes({}, {}, {}, {})
        }
    }
}