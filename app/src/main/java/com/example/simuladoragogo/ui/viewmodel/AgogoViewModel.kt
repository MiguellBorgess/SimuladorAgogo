package com.example.simuladoragogo.ui.viewmodel

import android.app.Application
import android.media.AudioAttributes
import android.media.SoundPool
import androidx.lifecycle.AndroidViewModel
import com.example.simuladoragogo.R

class AgogoViewModel(application: Application) : AndroidViewModel(application) {

    private val soundPool: SoundPool
    private var sound1: Int = 0
    private var sound2: Int = 0
    private var sound3: Int = 0
    private var sound4: Int = 0

    init {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(4)
            .setAudioAttributes(audioAttributes)
            .build()

        sound1 = soundPool.load(application.applicationContext, R.raw.sound1, 1)
        sound2 = soundPool.load(application.applicationContext, R.raw.sound2, 1)
        sound3 = soundPool.load(application.applicationContext, R.raw.sound3, 1)
        sound4 = soundPool.load(application.applicationContext, R.raw.sound4, 1)
    }

    fun playSound(soundId: Int) {
        when (soundId) {
            1 -> soundPool.play(sound1, 1f, 1f, 0, 0, 1f)
            2 -> soundPool.play(sound2, 1f, 1f, 0, 0, 1f)
            3 -> soundPool.play(sound3, 1f, 1f, 0, 0, 1f)
            4 -> soundPool.play(sound4, 1f, 1f, 0, 0, 1f)
        }
    }

    public override fun onCleared() {
        super.onCleared()
        soundPool.release()
    }
}
