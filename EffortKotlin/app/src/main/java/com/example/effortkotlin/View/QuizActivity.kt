package com.example.effortkotlin.View

import android.app.Activity
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
import android.speech.tts.TextToSpeech
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.*
import com.example.effortkotlin.Model.TuVung
import com.example.effortkotlin.R
import com.example.effortkotlin.factory.setBitmapImage
import java.util.*

class QuizActivity : AppCompatActivity() {

    companion object {
        val EXTRA_SCORE = "extraScore"
        val COUNTDOWN_IN_MILLIS: Long = 30000
    }

    lateinit var tvQuestion2: TextView
    lateinit var tvScore2: TextView
    lateinit var tvQuestionCount2: TextView
    lateinit var quizThongbao: TextView
    lateinit var rb1: TextView
    lateinit var rb2: TextView
    lateinit var rb3: TextView
    lateinit var rb4: TextView
    lateinit var btspeak: TextView
    lateinit var quizTalk: Button
    lateinit var btSkip: Button
    lateinit var pgCount: ProgressBar
    internal val handler = Handler()
    private var tuVungList: List<TuVung>? = null
    internal var rdSoDapan: Int = 0
    private var answered: Boolean = false
    private var backPressedTime: Long = 0
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
    lateinit var relativeLayout: RelativeLayout

    internal var kind: Int = 0
    internal var idlevel: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        mTTS = TextToSpeech(this, TextToSpeech.OnInitListener { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = mTTS?.setLanguage(Locale.ENGLISH)

                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "Language not supported")
                } else {
                    //                            mButtonSpeak.setEnabled(true);
                }
            } else {
                Log.e("TTS", "Initialization failed")
            }
        })
        mTTS!!.setPitch(0.9f)
        mTTS!!.setSpeechRate(0.88f)



        initView()

        tvScore2.text = "Score: 0"
        val paint = tvScore2.paint
        val width = paint.measureText("Score: 0")
        setColortextScore(width)

        if (kind == 1) {
            showNextListen()
            Log.e("ggwp",tuVungList!!.get(0)!!.getTu() )
//            mTTS!!.speak(tuVungList!!.get(0)!!.getTu(), TextToSpeech.QUEUE_FLUSH, null)

        } else if (kind == 2) {
            showNextQuestionMulti()
            Log.e("ggwp",tuVungList!!.get(0)!!.getTu() )
//            mTTS!!.speak(tuVungList!!.get(0)!!.getTu(), TextToSpeech.QUEUE_FLUSH, null)
        }


        btspeak.setOnClickListener{
            Log.e("ggwp","${questionCounter}" )

            mTTS!!.speak(tuVungList!!.get(questionCounter-1)!!.getTu(), TextToSpeech.QUEUE_FLUSH, null)

        }

        tvQuestion2.setOnClickListener {
            mTTS!!.speak(tuVungList!!.get(questionCounter-1)!!.getTu(), TextToSpeech.QUEUE_FLUSH, null)

        }

        rb1.setOnClickListener {
            countDownTimer?.cancel()

            if (kind == 1) {
                if (rb1.text.toString() == currentQuestion?.getTu()) {
                    score++
                    tvScore2.text = "Score: $score"
                    val width = paint.measureText("Score: $score")
                    setColortextScore(width)
                    mpTrue.start()
                } else {
                    mpFalse.start()
                }
                showSolution()
            } else if (kind == 2) {
                if (rb1.text.toString() == currentQuestion?.getNghia()) {
                    score++
                    tvScore2.text = "Score: $score"
                    val width = paint.measureText("Score: $score")
                    setColortextScore(width)
                    mpTrue.start()
                } else {
                    mpFalse.start()
                }
                showSolution()
            }

            rb1.setEnabled(false)
            rb2.setEnabled(false)
            rb3.setEnabled(false)
            rb4.setEnabled(false)


            handler.postDelayed({
                if (kind == 1) {
                    showNextListen()
                    rb1.setEnabled(true)
                    rb2.setEnabled(true)
                    rb3.setEnabled(true)
                    rb4.setEnabled(true)

                } else if (kind == 2) {
                    showNextQuestionMulti()
                    rb1.setEnabled(true)
                    rb2.setEnabled(true)
                    rb3.setEnabled(true)
                    rb4.setEnabled(true)

                }
            }, 1000)
        }

        rb2.setOnClickListener {
            countDownTimer?.cancel()

            if (kind == 1) {
                if (rb2.text.toString() == currentQuestion?.getTu()) {
                    score++
                    tvScore2.text = "Score: $score"
                    val width = paint.measureText("Score: $score")
                    setColortextScore(width)
                    mpTrue.start()
                } else {
                    mpFalse.start()
                }
                showSolution()
            } else if (kind == 2) {
                if (rb2.text.toString() == currentQuestion?.getNghia()) {
                    score++
                    tvScore2.text = "Score: $score"
                    val width = paint.measureText("Score: $score")
                    setColortextScore(width)
                    mpTrue.start()
                } else {
                    mpFalse.start()
                }
                showSolution()
            }

            rb1.setEnabled(false)
            rb2.setEnabled(false)
            rb3.setEnabled(false)
            rb4.setEnabled(false)

            handler.postDelayed({
                if (kind == 1) {
                    showNextListen()
                    rb1.setEnabled(true)
                    rb2.setEnabled(true)
                    rb3.setEnabled(true)
                    rb4.setEnabled(true)
                } else if (kind == 2) {
                    showNextQuestionMulti()
                    rb1.setEnabled(true)
                    rb2.setEnabled(true)
                    rb3.setEnabled(true)
                    rb4.setEnabled(true)
                }
            }, 1000)
        }

        rb3.setOnClickListener {
            countDownTimer?.cancel()

            if (kind == 1) {
                if (rb3.text.toString() == currentQuestion?.getTu()) {
                    score++
                    tvScore2.text = "Score: $score"
                    val width = paint.measureText("Score: $score")
                    setColortextScore(width)
                    mpTrue.start()
                } else {
                    mpFalse.start()
                }
                showSolution()
            } else if (kind == 2) {
                if (rb3.text.toString() == currentQuestion?.getNghia()) {
                    score++
                    tvScore2.text = "Score: $score"
                    val width = paint.measureText("Score: $score")
                    setColortextScore(width)
                    mpTrue.start()
                } else {
                    mpFalse.start()
                }
                showSolution()
            }

            rb1.setEnabled(false)
            rb2.setEnabled(false)
            rb3.setEnabled(false)
            rb4.setEnabled(false)

            handler.postDelayed({
                if (kind == 1) {
                    showNextListen()
                    rb1.setEnabled(true)
                    rb2.setEnabled(true)
                    rb3.setEnabled(true)
                    rb4.setEnabled(true)
                } else if (kind == 2) {
                    showNextQuestionMulti()
                    rb1.setEnabled(true)
                    rb2.setEnabled(true)
                    rb3.setEnabled(true)
                    rb4.setEnabled(true)
                }
            }, 1000)
        }

        rb4.setOnClickListener {
            countDownTimer?.cancel()

            if (kind == 1) {
                if (rb4.text.toString() == currentQuestion?.getTu()) {
                    score++
                    tvScore2.text = "Score: $score"
                    val width = paint.measureText("Score: $score")
                    setColortextScore(width)
                    mpTrue.start()
                } else {
                    mpFalse.start()
                }
                showSolution()
            } else if (kind == 2) {
                if (rb4.text.toString() == currentQuestion?.getNghia()) {
                    score++
                    tvScore2.text = "Score: $score"
                    val width = paint.measureText("Score: $score")
                    setColortextScore(width)
                    mpTrue.start()
                } else {
                    mpFalse.start()
                }
                showSolution()
            }

            rb1.setEnabled(false)
            rb2.setEnabled(false)
            rb3.setEnabled(false)
            rb4.setEnabled(false)

            handler.postDelayed({
                if (kind == 1) {
                    showNextListen()
                    rb1.setEnabled(true)
                    rb2.setEnabled(true)
                    rb3.setEnabled(true)
                    rb4.setEnabled(true)
                } else if (kind == 2) {
                    showNextQuestionMulti()
                    rb1.setEnabled(true)
                    rb2.setEnabled(true)
                    rb3.setEnabled(true)
                    rb4.setEnabled(true)
                }
            }, 1000)
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


        relativeLayout = findViewById(R.id.liner_quiz)
        val params = relativeLayout.layoutParams
        params.height = widthScreen - 20
        params.width = widthScreen
        relativeLayout.layoutParams = params

        tvQuestion2 = findViewById(R.id.tv_question2)
        tvScore2 = findViewById(R.id.tv_score2)
        rb1 = findViewById(R.id.bt_1)
        rb2 = findViewById(R.id.bt_2)
        rb3 = findViewById(R.id.bt_3)
        rb4 = findViewById(R.id.bt_4)
        btspeak = findViewById(R.id.bt_speaker)
        pgCount = findViewById(R.id.pg_countdown)
        tvQuestionCount2 = findViewById(R.id.tv_question_count2)
        quizTalk = findViewById(R.id.quiz_talk)
        btSkip = findViewById(R.id.quiz_skip)
        btspeak = findViewById(R.id.bt_speaker)
        //        viewTraloi = findViewById(R.id.layout_traloi);
        quizThongbao = findViewById(R.id.quiz_thongbao)
        textColorDefaultRb = rb1.textColors

        val intent = intent

        kind = intent.getIntExtra("kindtoresult", 0)

        tuVungList = intent.getSerializableExtra("listVoca") as List<TuVung>

        idlevel = intent.getStringExtra("idlevelresult")


        Log.e("testlist", "$idlevel : kind $kind ")

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
                Color.parseColor("#BBC1F4"),
                Color.parseColor("#BBC1F4"),
                Color.parseColor("#ff7bed")
            ), null, Shader.TileMode.CLAMP
        )
        tvQuestionCount2.paint.shader = textShader
    }

    private fun showNextListen() {
        rb1.setTextColor(textColorDefaultRb)
        rb2.setTextColor(textColorDefaultRb)
        rb3.setTextColor(textColorDefaultRb)
        rb4.setTextColor(textColorDefaultRb)

        if (questionCounter < questionCountTotal) {

            currentQuestion = tuVungList!!.get(questionCounter)

            quizTalk.visibility = View.INVISIBLE
            quizThongbao.visibility = View.INVISIBLE


            tvQuestion2.visibility = View.INVISIBLE
            //            viewTraloi.setVisibility(View.VISIBLE);
            btspeak.visibility = View.VISIBLE

            btSkip.visibility = View.INVISIBLE


            mTTS!!.speak(currentQuestion!!.getTu(), TextToSpeech.QUEUE_FLUSH, null)

            questionCounter++

            tvQuestionCount2.text = "Question: $questionCounter/$questionCountTotal"

            val rdDapan = Random()
            rdSoDapan = rdDapan.nextInt(4 ) + 1


            var dapankhac1 = tuVungList!!.get(Random().nextInt(tuVungList!!.size)).getTu()
            var dapankhac2 = tuVungList!!.get(Random().nextInt(tuVungList!!.size)).getTu()
            var dapankhac3 = tuVungList!!.get(Random().nextInt(tuVungList!!.size)).getTu()

            Log.e("dapan", "$dapankhac1 $dapankhac2 $dapankhac3")
            //edit sau
            while (currentQuestion?.getTu().equals(dapankhac1)) {
                dapankhac1 = tuVungList!!.get(Random().nextInt(tuVungList!!.size)).getTu()
            }

            while (currentQuestion?.getTu().equals(dapankhac2) || dapankhac1 == dapankhac2) {
                dapankhac2 = tuVungList?.get(Random().nextInt(tuVungList!!.size))?.getTu()
            }

            while (currentQuestion?.getTu().equals(dapankhac3) || dapankhac1 == dapankhac3 || dapankhac2 == dapankhac3) {
                dapankhac3 = tuVungList!!.get(Random().nextInt(tuVungList!!.size)).getTu()
            }

            when (rdSoDapan) {
                1 -> {
                    rb1.text = currentQuestion!!.getTu()
                    rb2.text = dapankhac1
                    rb3.text = dapankhac2
                    rb4.text = dapankhac3
                }
                2 -> {
                    rb1.text = dapankhac1
                    rb2.text = currentQuestion!!.getTu()
                    rb3.text = dapankhac2
                    rb4.text = dapankhac3
                }
                3 -> {
                    rb1.text = dapankhac1
                    rb2.text = dapankhac2
                    rb3.text = currentQuestion!!.getTu()
                    rb4.text = dapankhac3
                }
                4 -> {
                    rb1.text = dapankhac1
                    rb2.text = dapankhac2
                    rb3.text = dapankhac3
                    rb4.text = currentQuestion!!.getTu()
                }
            }


            answered = false
            timeLeftInMillis = COUNTDOWN_IN_MILLIS
            startCountDown1()

        } else {
            finishQuiz()
        }

    }

    private fun showNextQuestionMulti() {
        rb1.setTextColor(textColorDefaultRb)
        rb2.setTextColor(textColorDefaultRb)
        rb3.setTextColor(textColorDefaultRb)
        rb4.setTextColor(textColorDefaultRb)

        if (questionCounter < questionCountTotal) {
            currentQuestion = tuVungList!!.get(questionCounter)

            btspeak.visibility = View.INVISIBLE
            btSkip.visibility = View.INVISIBLE
            quizTalk.visibility = View.INVISIBLE
            tvQuestion2.visibility = View.VISIBLE
            //                viewTraloi.setVisibility(View.VISIBLE);
            quizThongbao.visibility = View.INVISIBLE
            btspeak.visibility = View.INVISIBLE


            mTTS?.speak(currentQuestion?.getTu(), TextToSpeech.QUEUE_FLUSH, null)

            tvQuestion2.text = currentQuestion?.getTu()

            val rdDapan = Random()
            rdSoDapan = rdDapan.nextInt(4 - 1 + 1) + 1

            var dapanNghia1 = tuVungList?.get(Random().nextInt(tuVungList!!.size))!!.getNghia()
            var dapanNghia2 = tuVungList?.get(Random().nextInt(tuVungList!!.size))!!.getNghia()
            var dapanNghia3 = tuVungList?.get(Random().nextInt(tuVungList!!.size))!!.getNghia()

            //edit sau
            while (currentQuestion?.getNghia().equals(dapanNghia1)) {
                dapanNghia1 = tuVungList!!.get(Random().nextInt(tuVungList!!.size)).getNghia()
            }

            while (currentQuestion?.getNghia().equals(dapanNghia2) || dapanNghia1 == dapanNghia2) {
                dapanNghia2 = tuVungList!!.get(Random().nextInt(tuVungList!!.size)).getNghia()
            }

            while (currentQuestion?.getNghia().equals(dapanNghia3) || dapanNghia1 == dapanNghia3 || dapanNghia2 == dapanNghia3) {
                dapanNghia3 = tuVungList!!.get(Random().nextInt(tuVungList!!.size)).getNghia()
            }

            when (rdSoDapan) {
                1 -> {
                    rb1.text = currentQuestion!!.getNghia()
                    rb2.text = dapanNghia1
                    rb3.text = dapanNghia2
                    rb4.text = dapanNghia3
                }
                2 -> {
                    rb1.text = dapanNghia1
                    rb2.text = currentQuestion!!.getNghia()
                    rb3.text = dapanNghia2
                    rb4.text = dapanNghia3
                }
                3 -> {
                    rb1.text = dapanNghia1
                    rb2.text = dapanNghia2
                    rb3.text = currentQuestion!!.getNghia()
                    rb4.text = dapanNghia3
                }
                4 -> {
                    rb1.text = dapanNghia1
                    rb2.text = dapanNghia2
                    rb3.text = dapanNghia3
                    rb4.text = currentQuestion!!.getNghia()
                }
            }

            questionCounter++
            tvQuestionCount2.text = "Question: $questionCounter/$questionCountTotal"

            val paint = tvQuestionCount2.paint
            val width = paint.measureText("Question: $questionCounter/$questionCountTotal")
            setColortextQuestinList(width)
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
        pgCount.max = 31
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
        showSolution()

        handler.postDelayed({
            if (kind == 1) {
                showNextListen()
            } else if (kind == 2) {
                showNextQuestionMulti()
            }
        }, 1000)

    }

    private fun finishQuiz() {
        val resultIntent = Intent()
        resultIntent.putExtra(EXTRA_SCORE, score)
        setResult(Activity.RESULT_OK, resultIntent)
        countDownTimer?.cancel()
        mTTS?.shutdown()

        val intent = Intent(this@QuizActivity, ResultActivity::class.java)
        val bundle = Bundle()
        //        bundle.putInt("levelresult", level);
        bundle.putInt("totalQuestion", questionCountTotal)
        bundle.putInt("score", score)
        bundle.putString("kind2", "$kind")
        bundle.putString("idlevel2", "$idlevel")


        intent.putExtras(bundle)

        startActivity(intent)
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
        finish()
    }

    private fun showSolution() {
        rb1.setTextColor(Color.RED)
        rb2.setTextColor(Color.RED)
        rb3.setTextColor(Color.RED)
        rb4.setTextColor(Color.RED)

        if (kind == 1) {
            if (currentQuestion?.getTu().equals(rb1.text.toString())) {
                rb1.setTextColor(Color.GREEN)
            } else if (currentQuestion?.getTu().equals(rb2.text.toString())) {
                rb2.setTextColor(Color.GREEN)
            } else if (currentQuestion?.getTu().equals(rb3.text.toString())) {
                rb3.setTextColor(Color.GREEN)
            } else if (currentQuestion?.getTu().equals(rb4.text.toString())) {
                rb4.setTextColor(Color.GREEN)
            }

        } else if (kind == 2) {

            if (currentQuestion?.getNghia().equals(rb1.text.toString())) {
                rb1.setTextColor(Color.GREEN)
            } else if (currentQuestion?.getNghia().equals(rb2.text.toString())) {
                rb2.setTextColor(Color.GREEN)
            } else if (currentQuestion?.getNghia().equals(rb3.text.toString())) {
                rb3.setTextColor(Color.GREEN)
            } else if (currentQuestion?.getNghia().equals(rb4.text.toString())) {
                rb4.setTextColor(Color.GREEN)

            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mTTS?.shutdown()
        countDownTimer?.cancel();
    }

    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finishQuiz()
        } else {
            Toast.makeText(this, "Press back again to finish", Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}
