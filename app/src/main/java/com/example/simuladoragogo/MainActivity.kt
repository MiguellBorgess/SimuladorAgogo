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
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
// Componentes de interface do Material Design 3 (o estilo visual do Google).
import androidx.compose.material3.*
// Ferramentas do Compose para gerenciar o "estado" (dados que mudam na tela).
import androidx.compose.runtime.*
// Ferramentas para alinhar e posicionar elementos.
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
// Ferramentas para lidar com densidade de pixels e direção do layout.
import androidx.compose.ui.unit.dp
import com.example.simuladoragogo.ui.screen.TelaComBotoes
import com.example.simuladoragogo.ui.screen.TelaComImagem
import com.example.simuladoragogo.ui.viewmodel.AgogoViewModel

// --- CLASSE PRINCIPAL DA TELA ---
// Esta é a classe que representa a tela principal do nosso aplicativo.
// Ela herda de `ComponentActivity`, que é a base para apps que usam Jetpack Compose.
class MainActivity : ComponentActivity() {

    private val viewModel: AgogoViewModel by viewModels()

    // --- MÉTODO `onCreate` ---
    // Este método é o ponto de partida da nossa tela. É o primeiro código a ser executado
    // quando a tela é criada. É aqui que fazemos todas as configurações iniciais.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                                onBoca1 = { viewModel.playSound(1) }, 
                                onBoca2 = { viewModel.playSound(2) },
                                onBoca3 = { viewModel.playSound(3) },
                                onBoca4 = { viewModel.playSound(4) }
                            )
                        } else {
                            // Se `usarImagem` for `false`, mostramos a tela com os botões.
                            TelaComBotoes(
                                onBoca1 = { viewModel.playSound(1) },
                                onBoca2 = { viewModel.playSound(2) },
                                onBoca3 = { viewModel.playSound(3) },
                                onBoca4 = { viewModel.playSound(4) }
                            )
                        }
                    }
                }
            }
        }
    }
}
