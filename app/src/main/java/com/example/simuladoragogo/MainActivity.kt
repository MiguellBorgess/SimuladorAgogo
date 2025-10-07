// Define o "endereço" do nosso código dentro do projeto. É como o CEP de um arquivo.
package com.example.simuladoragogo

// --- IMPORTAÇÕES ---
// Aqui, nós dizemos ao nosso arquivo quais "ferramentas" de outras bibliotecas vamos usar.
// Ferramentas para tocar sons.
import android.media.AudioAttributes
import android.media.SoundPool
// Ferramentas básicas do sistema Android.
import android.os.Bundle
// Ferramentas para criar a tela e atividades do app.
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
// Ferramentas visuais do Jetpack Compose para construir a interface.
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
// Componentes de interface do Material Design 3 (o estilo visual do Google).
import androidx.compose.material3.*
// Ferramentas do Compose para gerenciar o "estado" (dados que mudam na tela).
import androidx.compose.runtime.*
// Ferramentas para alinhar e posicionar elementos.
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
// Ferramentas para cortar e rotacionar elementos.
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
// Ferramentas para trabalhar com formas e geometria.
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
// Ferramentas para controlar como as imagens são exibidas.
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
// Ferramenta para pré-visualizar a interface no Android Studio.
import androidx.compose.ui.tooling.preview.Preview
// Ferramentas para lidar com densidade de pixels e direção do layout.
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

// --- CLASSE PRINCIPAL DA TELA ---
// Esta é a classe que representa a tela principal do nosso aplicativo.
// Ela herda de `ComponentActivity`, que é a base para apps que usam Jetpack Compose.
class MainActivity : ComponentActivity() {

    // --- VARIÁVEIS PARA O SOM ---
    // `private` significa que só esta classe pode acessar essas variáveis.
    // `lateinit` é uma promessa de que vamos inicializar a variável antes de usá-la.
    // `SoundPool` é um objeto do Android otimizado para tocar efeitos sonoros curtos e rápidos.
    private lateinit var soundPool: SoundPool

    // Estas variáveis vão guardar os "IDs" (números de identificação) de cada som que carregarmos.
    // Começamos com 0 como um valor inicial padrão.
    private var sound1: Int = 0
    private var sound2: Int = 0
    private var sound3: Int = 0
    private var sound4: Int = 0

    // --- MÉTODO `onCreate` ---
    // Este método é o ponto de partida da nossa tela. É o primeiro código a ser executado
    // quando a tela é criada. É aqui que fazemos todas as configurações iniciais.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // --- CONFIGURAÇÃO DO ÁUDIO ---
        // Aqui, criamos as "regras" de como nosso som deve se comportar no sistema Android.
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA) // Diz ao Android que o som é tipo "mídia".
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION) // Diz que são efeitos sonoros de interface.
            .build() // Constrói o objeto de configuração.

        // --- CRIAÇÃO DO REPRODUTOR DE SOM (SoundPool) ---
        // Agora, criamos o SoundPool usando as regras que definimos acima.
        soundPool = SoundPool.Builder()
            .setMaxStreams(4) // Permite que até 4 sons toquem ao mesmo tempo.
            .setAudioAttributes(audioAttributes) // Aplica nossas regras de áudio.
            .build() // Constrói o SoundPool.

        // --- CARREGAMENTO DOS ARQUIVOS DE SOM ---
        // Carregamos os arquivos de som (.wav, .mp3, etc.) da pasta de recursos do app (res/raw).
        // O método `load` retorna um ID numérico para cada som, que guardamos em nossas variáveis.
        sound1 = soundPool.load(this, R.raw.sound1, 1)
        sound2 = soundPool.load(this, R.raw.sound2, 1)
        sound3 = soundPool.load(this, R.raw.sound3, 1)
        sound4 = soundPool.load(this, R.raw.sound4, 1)

        // --- DEFINIÇÃO DA INTERFACE COM JETPACK COMPOSE ---
        // `setContent` é a porta de entrada para o Compose. Todo o código da nossa interface
        // visual vai dentro destas chaves.
        setContent {
            // --- VARIÁVEIS DE ESTADO ---
            // "Estado" é qualquer valor que pode mudar e que afeta a interface.
            // `mutableStateOf` cria uma "caixa" observável que guarda um valor.
            // `remember` diz ao Compose para "lembrar" o valor nesta caixa mesmo quando a tela redesenha.
            // Quando um estado muda, o Compose automaticamente reconstrói as partes da UI que dependem dele.
            var usarImagem by remember { mutableStateOf(true) } // Controla se mostramos a imagem ou os botões.
            var darkMode by remember { mutableStateOf(false) }   // Controla se usamos o tema claro ou escuro.

            // `MaterialTheme` aplica um estilo visual consistente (cores, fontes, etc.) para todo o app.
            // Usamos o estado `darkMode` para decidir qual paleta de cores usar.
            MaterialTheme(colorScheme = if (darkMode) darkColorScheme() else lightColorScheme()) {
                // `Surface` é um container básico que desenha um fundo com a cor do tema.
                Surface(modifier = Modifier.fillMaxSize()) {
                    // `Column` é um container que empilha seus filhos verticalmente, um abaixo do outro.
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Top, // Alinha o conteúdo no topo.
                        horizontalAlignment = Alignment.CenterHorizontally // Centraliza o conteúdo horizontalmente.
                    ) {
                        // `Row` é um container que empilha seus filhos horizontalmente, um ao lado do outro.
                        Row(
                            modifier = Modifier
                                .fillMaxWidth() // Faz a linha ocupar toda a largura da tela.
                                .padding(8.dp), // Adiciona um respiro (margem) de 8dp em volta.
                            horizontalArrangement = Arrangement.SpaceBetween, // Coloca um item na esquerda e outro na direita.
                            verticalAlignment = Alignment.CenterVertically // Alinha os itens no centro verticalmente.
                        ) {
                            // `Switch` é o componente de botão de alternância (liga/desliga).
                            Switch(
                                checked = usarImagem, // O estado do switch (ligado/desligado) é controlado pela nossa variável de estado.
                                onCheckedChange = { usarImagem = it }, // Quando o usuário clica, atualizamos a variável de estado.
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                                    uncheckedThumbColor = MaterialTheme.colorScheme.secondary
                                )
                            )

                            // Outro Switch para o modo escuro.
                            Switch(
                                checked = darkMode,
                                onCheckedChange = { darkMode = it },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = MaterialTheme.colorScheme.primary,
                                    uncheckedThumbColor = MaterialTheme.colorScheme.secondary
                                )
                            )
                        }

                        // --- LÓGICA DE EXIBIÇÃO ---
                        // Aqui, usamos a variável de estado `usarImagem` para decidir qual tela mostrar.
                        // Se `usarImagem` for `true`, mostramos a tela com a imagem do agogô.
                        if (usarImagem) {
                            // Chamamos nossa função que desenha a tela com a imagem.
                            // Passamos para ela as "ações" (o que fazer quando cada boca for clicada).
                            TelaComImagem(
                                onBoca1 = { tocar(sound1) }, // Se clicar na boca 1, toca o som 1.
                                onBoca2 = { tocar(sound2) },
                                onBoca3 = { tocar(sound3) },
                                onBoca4 = { tocar(sound4) }
                            )
                        } else {
                            // Se `usarImagem` for `false`, mostramos a tela com os botões.
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

    // --- FUNÇÃO PARA TOCAR SOM ---
    // Criamos uma função separada para organizar o código que toca o som.
    private fun tocar(soundId: Int) {
        // O método `play` do SoundPool executa o som.
        // Parâmetros: (ID do som, volume esquerdo, volume direito, prioridade, loop, velocidade)
        // `loop = 0` significa que não vai repetir.
        soundPool.play(soundId, 1f, 1f, 0, 0, 1f)
    }

    // --- MÉTODO `onDestroy` ---
    // Este método é chamado quando a tela está prestes a ser destruída (ex: o usuário fecha o app).
    override fun onDestroy() {
        super.onDestroy()
        // `release()` libera os recursos de som da memória. É uma boa prática para evitar vazamentos de memória.
        soundPool.release()
    }
}


// --- TELA COM BOTÕES ---
// A anotação `@Composable` marca esta função como um componente de UI do Jetpack Compose.
@Composable
fun TelaComBotoes(
    // A função recebe 4 "ações" como parâmetros. `() -> Unit` define uma ação que não recebe
    // nada e não retorna nada.
    onBoca1: () -> Unit, onBoca2: () -> Unit, onBoca3: () -> Unit, onBoca4: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center, // Centraliza a coluna inteira na tela.
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) { // Adiciona espaço entre os botões.
            // `Button` é um componente de botão clicável.
            // `onClick` define a ação que acontece quando o botão é pressionado.
            Button(onClick = onBoca4, modifier = Modifier.size(180.dp)) { Text("4") }
            Button(onClick = onBoca3, modifier = Modifier.size(180.dp)) { Text("3") }
            Button(onClick = onBoca2, modifier = Modifier.size(180.dp)) { Text("2") }
            Button(onClick = onBoca1, modifier = Modifier.size(180.dp)) { Text("1") }
        }
        Spacer(modifier = Modifier.height(16.dp)) // Apenas um espaço vertical.
    }
}


// --- NOSSA CLASSE DE FORMA CUSTOMIZADA ---
/**
 * Cria uma Shape de trapézio definindo a posição horizontal de cada um dos 4 cantos.
 * Os valores de ratio são de 0.0 (extrema esquerda) a 1.0 (extrema direita).
 */
// Esta é uma classe que criamos para definir uma forma geométrica customizada.
// Ela implementa a interface `Shape`, que obriga a ter o método `createOutline`.
class FourPointTrapezoidShape(
    private val topLeftXRatio: Float,
    private val topRightXRatio: Float,
    private val bottomLeftXRatio: Float,
    private val bottomRightXRatio: Float
) : Shape {
    // Este método é onde a "mágica" acontece. Ele descreve o contorno da nossa forma.
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        // `Path` é como um objeto de "desenho vetorial". Vamos usá-lo para desenhar as linhas da nossa forma.
        val path = Path().apply {
            // É como desenhar com uma caneta:
            moveTo(size.width * topLeftXRatio, 0f)              // 1. Levanta a caneta e move para o canto superior esquerdo.
            lineTo(size.width * topRightXRatio, 0f)             // 2. Abaixa a caneta e desenha uma linha até o canto superior direito.
            lineTo(size.width * bottomRightXRatio, size.height) // 3. Desenha uma linha até o canto inferior direito.
            lineTo(size.width * bottomLeftXRatio, size.height)  // 4. Desenha uma linha até o canto inferior esquerdo.
            close()                                             // 5. Desenha uma linha final de volta ao ponto de partida para fechar a forma.
        }
        // Retornamos o contorno da forma que acabamos de desenhar.
        return Outline.Generic(path)
    }
}


// --- TELA COM IMAGEM ATUALIZADA ---
@Composable
fun TelaComImagem(
    onBoca1: () -> Unit, onBoca2: () -> Unit, onBoca3: () -> Unit, onBoca4: () -> Unit
) {
    // `Box` é um container que permite empilhar elementos uns sobre os outros.
    // É perfeito para colocar as áreas de clique sobre uma imagem de fundo.
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center // Define o ponto (0,0) como o centro, facilitando o `offset`.
    ) {
        // A imagem de fundo do agogô.
        Image(
            painter = painterResource(id = R.drawable.agogonobg),
            contentDescription = "Agogô", // Texto para acessibilidade (leitores de tela).
            modifier = Modifier.fillMaxSize(),
            // Estica a imagem para preencher toda a tela, mesmo que distorça um pouco.
            contentScale = ContentScale.FillBounds
        )

        // --- ÁREA DE CLIQUE PARA A BOCA 4 (VERDE) ---
        // Cada boca é um `Box` invisível que será cortado no formato de trapézio.
        Box(
            // `Modifier` é uma cadeia de "instruções" que modificam o `Box`. A ordem importa!
            modifier = Modifier
                .offset(x = (-210).dp, y = (50).dp) // 1. Desloca o Box a partir do centro.
                .rotate(120f)                       // 2. Rotaciona o Box.
                .size(width = 190.dp, height = 350.dp) // 3. Define o tamanho da "tela de pintura" do Box.
                .clip(FourPointTrapezoidShape(      // 4. CORTA o Box usando nossa forma customizada.
                    topLeftXRatio = 0.35f,
                    topRightXRatio = 0.65f,
                    bottomLeftXRatio = 0.0f,
                    bottomRightXRatio = 1.0f
                ))
                .background(Color.Green.copy(alpha = 0.4f)) // 5. Pinta o fundo da área visível (o trapézio).
                .clickable { onBoca4() }                   // 6. Faz com que a área visível seja clicável.
        )

        // As outras bocas seguem exatamente a mesma lógica, apenas com valores diferentes
        // de posição, rotação, tamanho e, se necessário, formato do trapézio.

        // Boca 3 (vermelha)
        Box(
            modifier = Modifier
                .offset(x = (-40).dp, y = (-30).dp)
                .rotate(157f)
                .size(width = 230.dp, height = 280.dp)
                .clip(FourPointTrapezoidShape(
                    topLeftXRatio = 0.35f,
                    topRightXRatio = 0.65f,
                    bottomLeftXRatio = 0.0f,
                    bottomRightXRatio = 1.0f
                ))
                .background(Color.Red.copy(alpha = 0.4f))
                .clickable { onBoca3() }
        )

        // Boca 2 (azul)
        Box(
            modifier = Modifier
                .offset(x = (160).dp, y = (-10).dp)
                .rotate(-155f)
                .size(width = 190.dp, height = 260.dp)
                .clip(FourPointTrapezoidShape(
                    topLeftXRatio = 0.35f,
                    topRightXRatio = 0.65f,
                    bottomLeftXRatio = 0.0f,
                    bottomRightXRatio = 1.0f
                ))
                .background(Color.Blue.copy(alpha = 0.4f))
                .clickable { onBoca2() }
        )

        // Boca 1 (amarela)
        Box(
            modifier = Modifier
                .offset(x = 270.dp, y = (95).dp)
                .rotate(-118f)
                .size(width = 140.dp, height = 260.dp)
                .clip(FourPointTrapezoidShape(
                    topLeftXRatio = 0.35f,
                    topRightXRatio = 0.65f,
                    bottomLeftXRatio = 0.0f,
                    bottomRightXRatio = 1.0f
                ))
                .background(Color.Yellow.copy(alpha = 0.4f))
                .clickable { onBoca1() }
        )
    }
}


// --- PREVIEW ---
// A anotação `@Preview` é uma ferramenta do Android Studio.
// Ela permite que você veja como seu componente de UI (`TelaComBotoes`, neste caso)
// se parece, sem precisar rodar o aplicativo inteiro no emulador.
@Preview(showBackground = true)
@Composable
fun TelaPreview() {
    MaterialTheme {
        Surface {
            // Chamamos a função com ações vazias, pois só queremos ver a aparência.
            TelaComBotoes({}, {}, {}, {})
        }
    }
}