package com.raproject.first_kotlin_android_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var btnTap: Button
    private lateinit var txtScore: TextView
    private lateinit var txtTime: TextView

    private var score = 0
    private var isGameStarted = false

    private lateinit var countDownTimer: CountDownTimer
    private val initialCountDown: Long = 60000
    private val countDownInterval: Long = 1000
    private var timeLeftOnTimer: Long = 60000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG,"onCreate called. Score is: $score")

        btnTap = findViewById(R.id.btn_tap)
        txtScore = findViewById(R.id.txt_score)
        txtTime = findViewById(R.id.txt_time)

        btnTap.setOnClickListener {
            val bounceAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce)
            it.startAnimation(bounceAnimation)
            incrementScore()
        }

        if(savedInstanceState != null){
            score =  savedInstanceState.getInt(SCORE_KEY)
            timeLeftOnTimer = savedInstanceState.getLong(TIME_LEFT_KEY)
            restoreGame()
        }else{
            resetGame()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(SCORE_KEY, score)
        outState.putLong(TIME_LEFT_KEY, timeLeftOnTimer)
        countDownTimer.cancel()

        Log.d(TAG, "onSaveInstanceState: Saving Score: $score & time left: $timeLeftOnTimer")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroyed called.")
    }

    private fun incrementScore() {
        if (!isGameStarted){
            startGame()
        }
        score += 1
        val newScore = getString(R.string.txt_score, score)
        txtScore.text =  newScore

        val blinkAnimation = AnimationUtils.loadAnimation(this, R.anim.blink)
        txtScore.startAnimation(blinkAnimation)
    }

    private fun resetGame(){
        score = 0

        txtScore.text = getString(R.string.txt_score, score)
        val initialTimeLeft = initialCountDown / 1000
        txtTime.text = getString(R.string.txt_time, initialTimeLeft)

        countDownTimer = object : CountDownTimer(initialCountDown, countDownInterval){
            override fun onTick(millisUntilFinished: Long) {
                timeLeftOnTimer = millisUntilFinished
                val timeLeft = millisUntilFinished / 1000
                txtTime.text = getString(R.string.txt_time, timeLeft)
            }
            override fun onFinish() {
                endGame()
            }
        }
        isGameStarted = false
    }

    private fun restoreGame(){
        txtScore.text = getString(R.string.txt_score, score)

        val restoredTime = timeLeftOnTimer / 1000
        txtTime.text = getString(R.string.txt_time, restoredTime)

        countDownTimer = object : CountDownTimer(timeLeftOnTimer, countDownInterval){
            override fun onTick(millisUntilFinished: Long) {
                timeLeftOnTimer = millisUntilFinished
                val timeLeft = millisUntilFinished / 1000
                txtTime.text = getString(R.string.txt_time, timeLeft)
            }

            override fun onFinish() {
                endGame()
            }
        }
        countDownTimer.start()
        isGameStarted = true
    }

    private fun startGame(){
        countDownTimer.start()
        isGameStarted = true
    }

    private fun endGame(){
        Toast.makeText(this, getString(R.string.gameOverMsg, score), Toast.LENGTH_SHORT).show()
        resetGame()
    }

    companion object{
        private val TAG = MainActivity::class.java.simpleName
        private const val SCORE_KEY = "SCORE_KEY"
        private const val TIME_LEFT_KEY = "TIME_LEFT_KEY"
    }
}
