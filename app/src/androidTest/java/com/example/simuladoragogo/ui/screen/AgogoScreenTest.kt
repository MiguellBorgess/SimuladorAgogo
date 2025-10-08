package com.example.simuladoragogo.ui.screen

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class AgogoScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testTelaComBotoes_cliqueNoBotao1_chamaOnBoca1() {
        // Mock da função lambda para verificar a chamada
        val onBoca1: () -> Unit = mock()

        // Define o conteúdo da UI para o teste
        composeTestRule.setContent {
            TelaComBotoes(
                onBoca1 = onBoca1,
                onBoca2 = {},
                onBoca3 = {},
                onBoca4 = {}
            )
        }

        // Encontra o botão com o texto "1" e simula um clique
        composeTestRule.onNodeWithText("1").performClick()

        // Verifica se a função onBoca1 foi chamada exatamente uma vez
        verify(onBoca1).invoke()
    }
}