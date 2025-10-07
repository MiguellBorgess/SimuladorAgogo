package com.example.simuladoragogo // pacote do app

// Importa as bibliotecas necessárias
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
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
            .setUsage(AudioAttributes.USAGE_MEDIA) // uso de mídia
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION) // efeito curto
            .build()

        // Cria o SoundPool (permite vários sons ao mesmo tempo)
        soundPool = SoundPool.Builder()
            .setMaxStreams(4) // até 4 sons simultâneos
            .setAudioAttributes(audioAttributes) // usa a configuração acima
            .build()

        // Carrega os sons da pasta res/raw
        sound1 = soundPool.load(this, R.raw.sound1, 1)
        sound2 = soundPool.load(this, R.raw.sound2, 1)
        sound3 = soundPool.load(this, R.raw.sound3, 1)
        sound4 = soundPool.load(this, R.raw.sound4, 1)

        // Define a interface da tela
        setContent {
            // Estados: variáveis que mudam a UI
            var usarImagem by remember { mutableStateOf(true) } // controla imagem/botões
            var darkMode by remember { mutableStateOf(false) }  // controla tema claro/escuro

            // Aplica o tema (claro ou escuro)
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
                            horizontalArrangement = Arrangement.SpaceBetween, // um à esquerda e outro à direita
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Toggle da esquerda (imagem ↔ botões)
                            Switch(
                                checked = usarImagem, // valor atual
                                onCheckedChange = { usarImagem = it }, // troca valor quando clicado
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = MaterialTheme.colorScheme.primary,   // cor ligada
                                    uncheckedThumbColor = MaterialTheme.colorScheme.secondary // cor desligada
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
                            // Mostra imagem do agogô
                            TelaComImagem(
                                onBoca1 = { tocar(sound1) },
                                onBoca2 = { tocar(sound2) },
                                onBoca3 = { tocar(sound3) },
                                onBoca4 = { tocar(sound4) }
                            )
                        } else {
                            // Mostra botões
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

    // Função que toca o som
    private fun tocar(soundId: Int) {
        // play(id, volumeEsq, volumeDir, prioridade, loop, velocidade)
        soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
    }

    // Quando a tela fecha, libera os sons da memória
    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }
}

// --- Tela com BOTÕES ---
@Composable
fun TelaComBotoes(
    onBoca1: () -> Unit,
    onBoca2: () -> Unit,
    onBoca3: () -> Unit,
    onBoca4: () -> Unit
) {
    // Container centralizado
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Primeira linha com dois botões
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = onBoca4,
                modifier = Modifier.size(180.dp)
            ) { Text("4") }

            Button(
                onClick = onBoca3,
                modifier = Modifier.size(180.dp)
            ) { Text("3") }

            Button(
                onClick = onBoca2,
                modifier = Modifier.size(180.dp)
            ) { Text("2") }

            Button(
                onClick = onBoca1,
                modifier = Modifier.size(180.dp) // quadrado
            ) { Text("1") }
        }

        Spacer(modifier = Modifier.height(16.dp)) // espaçamento entre linhas
    }
}


// --- Tela com IMAGEM ---
@Composable
fun TelaComImagem(
    onBoca1: () -> Unit,
    onBoca2: () -> Unit,
    onBoca3: () -> Unit,
    onBoca4: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Imagem do agogô
        Image(
            painter = painterResource(id = R.drawable.agogonobg),
            contentDescription = "Agogô",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        // Boca 1 (amarela) – menor, direita
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(x = 275.dp, y = 190.dp)
                .size(100.dp)
                .background(Color.Yellow.copy(alpha = 0.5f))
                .clickable { onBoca1() }
        )

        // Boca 2 (azul)
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(x = 150.dp, y = 85.dp)
                .size(120.dp)
                .background(Color.Blue.copy(alpha = 0.5f))
                .clickable { onBoca2() }
        )

        // Boca 3 (vermelha)
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(x = -40.dp, y = 55.dp)
                .size(120.dp)
                .background(Color.Red.copy(alpha = 0.5f))
                .clickable { onBoca3() }
        )

        // Boca 4 (verde) – maior, esquerda
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(x = -255.dp, y = 100.dp)
                .size(120.dp)
                .background(Color.Green.copy(alpha = 0.5f))
                .clickable { onBoca4() }
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
