package com.example.simuladoragogo.ui.viewmodel

import android.app.Application
import android.media.SoundPool
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class AgogoViewModelTest {

    private lateinit var viewModel: AgogoViewModel
    private val soundPool: SoundPool = mock()
    private val application: Application = mock()

    @Before
    fun setUp() {
        // Configura o mock para retornar IDs de som previsíveis
        whenever(soundPool.load(application, com.example.simuladoragogo.R.raw.sound1, 1)).thenReturn(101)
        whenever(soundPool.load(application, com.example.simuladoragogo.R.raw.sound2, 1)).thenReturn(102)
        whenever(soundPool.load(application, com.example.simuladoragogo.R.raw.sound3, 1)).thenReturn(103)
        whenever(soundPool.load(application, com.example.simuladoragogo.R.raw.sound4, 1)).thenReturn(104)

        // Inicializa o ViewModel com o SoundPool mockado
        viewModel = AgogoViewModel(application)
    }

    @Test
    fun testPlaySound_chamaSoundPoolPlayComArgumentosCorretos() {
        // Chama o método a ser testado
        viewModel.playSound(1)

        // Verifica se soundPool.play foi chamado com os argumentos esperados para sound1
        // O ID do som (101) é o que definimos no setup do mock
        verify(soundPool).play(101, 1f, 1f, 0, 0, 1f)
    }

    @Test
    fun onCleared_chamaSoundPoolRelease() {
        // Chama o método onCleared (geralmente chamado pelo framework)
        viewModel.onCleared()

        // Verifica se o método release do soundPool foi chamado
        verify(soundPool).release()
    }
}