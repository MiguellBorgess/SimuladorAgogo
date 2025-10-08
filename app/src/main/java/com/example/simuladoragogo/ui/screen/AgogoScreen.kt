package com.example.simuladoragogo.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simuladoragogo.R
import com.example.simuladoragogo.ui.shape.FourPointTrapezoidShape

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