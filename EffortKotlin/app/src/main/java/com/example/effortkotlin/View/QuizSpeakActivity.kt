package com.example.effortkotlin.View

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.*
import com.example.effortkotlin.Model.TuVung
import com.example.effortkotlin.R
import com.example.effortkotlin.factory.setBitmapImage
import java.util.*

class QuizSpeakActivity : AppCompatActivity() {

    companion object {
        val EXTRA_SCORE = "extraScore"
        val COUNTDOWN_IN_MILLIS: Long = 60000
        private val REQ_CODE_SPEECH_INPUT = 100
        var resultSpeaking: String? = ""
    }

    private var backPressedTime: Long = 0

    lateinit var tvQuestion2: TextView
    lateinit var tvScore2: TextView
    lateinit var tvQuestionCount2: TextView
    lateinit var quizThongbao: TextView
    lateinit var btspeak: TextView
    lateinit var quizTalk: Button
    lateinit var btSkip: Button
    lateinit var pgCount: ProgressBar

    internal val handler = Handler()
    private var tuVungList: List<TuVung>? = null

    private var answered: Boolean = false

    private var questionCounter = 0
    private var questionCountTotal: Int = 0
    private var currentQuestion: TuVung? = null
    private var textColorDefaultRb: ColorStateList? = null
    private var mTTS: TextToSpeech? = null
    private var score: Int = 0
    private var timeLeftInMillis: Long = 0
    private var countDownTimer: CountDownTimer? = null
    lateinit var viewBackground: View
    lateinit var mpTrue: MediaPlayer
    lateinit var mpFalse: MediaPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_speak)

        initView()

        mTTS = TextToSpeech(this, TextToSpeech.OnInitListener { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = mTTS?.setLanguage(Locale.ENGLISH)

                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "Language not supported")
                } else {
                    //mButtonSpeak.setEnabled(true);
                }
            } else {
                Log.e("TTS", "Initialization failed")
            }
        })

        tvScore2.text = "Score: 0"
        val paint = tvScore2.paint
        val width = paint.measureText("Score: 0")
        setColortextScore(width)

//        mTTS!!.setPitch(0.85f)
//        mTTS!!.setSpeechRate(0.85f)
//        mTTS!!.speak(tuVungList?.get(0)?.getTu(), TextToSpeech.QUEUE_FLUSH, null)

        showNextSpeak()

        quizTalk.setOnClickListener {
            promptSpeechInput()
        }

        btSkip.setOnClickListener {
            mTTS?.stop()
            countDownTimer?.cancel()
            showNextSpeak()

        }


        tvQuestion2.setOnClickListener {
            mTTS!!.setPitch(0.85f)
            mTTS!!.setSpeechRate(0.85f)
            mTTS!!.speak(tvQuestion2.text.toString(), TextToSpeech.QUEUE_FLUSH, null)
        }

    }


    private fun promptSpeechInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(
            RecognizerIntent.EXTRA_PROMPT,
            getString(R.string.speech_prompt)
        )
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT)
        } catch (a: ActivityNotFoundException) {
            Toast.makeText(
                applicationContext,
                getString(R.string.speech_not_supported),
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    private fun initView() {
        viewBackground = findViewById(R.id.view_quiz)
        setBitmapImage.setBackground(applicationContext, viewBackground, R.drawable.background_all)

        mpTrue = MediaPlayer.create(this, R.raw.definite)
        mpFalse = MediaPlayer.create(this, R.raw.bruteforce)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val heightScreen = displayMetrics.heightPixels
        val widthScreen = displayMetrics.widthPixels

        tvQuestion2 = findViewById(R.id.tv_question2)
        tvScore2 = findViewById(R.id.tv_score2)

//        btspeak = findViewById(R.id.bt_speaker)
        pgCount = findViewById(R.id.pg_countdown)
        tvQuestionCount2 = findViewById(R.id.tv_question_count2)
        quizTalk = findViewById(R.id.quiz_talk)
        btSkip = findViewById(R.id.quiz_skip)
//        btspeak = findViewById(R.id.bt_speaker)
        //        viewTraloi = findViewById(R.id.layout_traloi);
        quizThongbao = findViewById(R.id.quiz_thongbao)

        val intent = intent

        tuVungList = intent.getSerializableExtra("listVoca") as List<TuVung>
        Log.e("testlist", tuVungList.toString())

        questionCountTotal = tuVungList!!.size
        Collections.shuffle(tuVungList)


    }

    private fun setColortextScore(width: Float) {
        val textShader = LinearGradient(
            0f, 0f, width, tvScore2.textSize,
            intArrayOf(
                Color.parseColor("#47d3ff"),
                Color.parseColor("#65daff"),
                //                        Color.parseColor("#65daff"),
                Color.parseColor("#BBC1F4"),
                Color.parseColor("#BBC1F4"),
                Color.parseColor("#ff7bed")
            ), null, Shader.TileMode.CLAMP
        )
        tvScore2.paint.shader = textShader
    }

    private fun setColortextQuestinList(width: Float) {
        val textShader = LinearGradient(
            0f, 0f, width, tvScore2.textSize,
            intArrayOf(
                Color.parseColor("#47d3ff"),
                Color.parseColor("#65daff"),
                //                        Color.parseColor("#65daff"),
                Color.parseColor("#BBC1F4"),
                Color.parseColor("#BBC1F4"),
                Color.parseColor("#ff7bed")
            ), null, Shader.TileMode.CLAMP
        )
        tvQuestionCount2.paint.shader = textShader
    }


    private fun showNextSpeak() {

        if (questionCounter < questionCountTotal) {
            currentQuestion = tuVungList!!.get(questionCounter)
//            btspeak.visibility = View.INVISIBLE
            quizTalk.visibility = View.VISIBLE
            quizThongbao.visibility = View.VISIBLE
            tvQuestion2.visibility = View.VISIBLE
            btSkip.visibility = View.VISIBLE

            questionCounter++

            tvQuestionCount2.text = "Question: $questionCounter/$questionCountTotal"

            mTTS!!.setPitch(0.9f)
            mTTS!!.setSpeechRate(0.9f)
            mTTS!!.speak(currentQuestion!!.getTu(), TextToSpeech.QUEUE_FLUSH, null)

            tvQuestion2.text = currentQuestion?.getTu()

            answered = false

            timeLeftInMillis = COUNTDOWN_IN_MILLIS

            startCountDown1()

        } else {
            finishQuiz()
        }

    }

    private fun startCountDown1() {
        val j = 0
        pgCount.progress = j
        pgCount.max = 61
        countDownTimer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                var current = pgCount.progress
                if (current >= pgCount.max) {
                    current = 0
                }
                pgCount.progress = current + 1
            }

            override fun onFinish() {
                timeLeftInMillis = 0
                checkAnswer()
            }
        }.start()
    }

    private fun checkAnswer() {
        mTTS?.stop()
        countDownTimer?.cancel()

        handler.postDelayed({
            showNextSpeak()
        }, 1000)

    }

    private fun finishQuiz() {
        val resultIntent = Intent()
        resultIntent.putExtra(QuizActivity.EXTRA_SCORE, score)
        setResult(Activity.RESULT_OK, resultIntent)
        countDownTimer?.cancel()
        mTTS?.shutdown()

        val intent = Intent(this, ResultActivity::class.java)
        val bundle = Bundle()

        bundle.putInt("totalQuestion", questionCountTotal)
        bundle.putInt("score", score)
        intent.putExtras(bundle)

        startActivity(intent)
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQ_CODE_SPEECH_INPUT -> {
                if (resultCode == RESULT_OK && null != data) {

                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)

                    var res: String? = result!![0]
                    resultSpeaking = res?.toUpperCase()

                    var cauhoi = currentQuestion!!.getTu()?.toUpperCase()

                    Log.e("resutl", "$resultSpeaking :  $cauhoi ")
                    quizThongbao.text = resultSpeaking

                    if (resultSpeaking!!.equals(cauhoi)) {
                        quizThongbao.text = "Chính xác"
                        score++
                        tvScore2.text = "Score: $score"

                        handler.postDelayed({
                            showNextSpeak()
                        }, 1000)

                    } else {
                        quizThongbao.text = "It isn't '$res' , try again!"
                    }

                }
            }
        }

    }

    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finishQuiz()
        } else {
            Toast.makeText(this, "Press back again to finish", Toast.LENGTH_SHORT).show()
        }

        backPressedTime = System.currentTimeMillis()
    }

    override fun onDestroy() {
        super.onDestroy()
        mTTS?.shutdown()
        countDownTimer?.cancel();
    }

}
