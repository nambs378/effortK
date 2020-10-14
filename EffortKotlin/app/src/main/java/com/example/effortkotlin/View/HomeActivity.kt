package com.example.effortkotlin.View

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.effortkotlin.Model.NotiVoca
import com.example.effortkotlin.R
import com.example.effortkotlin.database.QuizDBHelper
import com.example.effortkotlin.factory.CheckConnection
import com.example.effortkotlin.factory.setBitmapImage
import com.thekhaeng.pushdownanim.PushDownAnim
import com.thekhaeng.pushdownanim.PushDownAnim.MODE_STATIC_DP

class HomeActivity : AppCompatActivity() {


    lateinit var btgomulti: ImageView
    lateinit var btGoTrans:ImageView
    lateinit var btGoGrammar:ImageView
    lateinit var btGoNoti: ImageView
    lateinit var btGoListen : ImageView
    lateinit var btGoSpeak : ImageView

    lateinit var homelg:ImageView
    internal var tvNoti: TextView? = null
    internal var context: Context? = null
    lateinit var view: View
    lateinit var homeLogo: Animation
    lateinit var bt1:Animation
    lateinit var bt2:Animation
    lateinit var bt3:Animation
    lateinit var bt4:Animation
    lateinit var bt5:Animation
    lateinit var bt6:Animation

//    public static TextToSpeech mTTS;


    lateinit var quizDBHelper: QuizDBHelper
    private val notiVocaList: List<NotiVoca>? = null

    lateinit var linearLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        homeLogo = AnimationUtils.loadAnimation(this, R.anim.home_logo)
        bt1 = AnimationUtils.loadAnimation(this, R.anim.bt1_home)
        bt2 = AnimationUtils.loadAnimation(this, R.anim.bt2_home)
        bt3 = AnimationUtils.loadAnimation(this, R.anim.bt3_home)
        bt4 = AnimationUtils.loadAnimation(this, R.anim.bt4_home)
        bt5 = AnimationUtils.loadAnimation(this, R.anim.bt5_home)
        bt6 = AnimationUtils.loadAnimation(this, R.anim.bt6_home)

        AnhXa()

        homelg = findViewById(R.id.logo__home)
        homelg.startAnimation(homeLogo)
        btgomulti.startAnimation(bt1)
        btGoListen.startAnimation(bt2)
        btGoSpeak.startAnimation(bt3)
        btGoGrammar.startAnimation(bt4)
        btGoNoti.startAnimation(bt5)
        btGoTrans.startAnimation(bt6)
        linearLayout = findViewById(R.id.liner_home)


        val params = linearLayout.layoutParams

        params.height = (MainActivity.widthScreen + (MainActivity.widthScreen / 2) ) - 40
//        params.height = 500

        params.width = MainActivity.widthScreen
        Log.e("z","${params.height }   :  ngang ${params.width}")

        linearLayout.layoutParams = params


        view = findViewById(R.id.view_home)
        setBitmapImage.setBackground(applicationContext, view, R.drawable.background_all)


//        PushDownAnim.setPushDownAnimTo(btgomulti)
//            .setScale(MODE_STATIC_DP, 8f)
//            .setDurationPush(PushDownAnim.DEFAULT_PUSH_DURATION)
//            .setDurationRelease(PushDownAnim.DEFAULT_RELEASE_DURATION)
//            .setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR)
//            .setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR)
//            .setOnClickListener {
//                val intent = Intent(this@HomeActivity, Level2Activity::class.java)
//                intent.putExtra("kindd",2)
//                startActivity(intent)
//                overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
//            }


        btgomulti.setOnClickListener {
            val intent = Intent(this@HomeActivity, Level2Activity::class.java)
            intent.putExtra("kindd",2)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
        }

//        PushDownAnim.setPushDownAnimTo(btGoNoti)
//            .setScale(MODE_STATIC_DP, 8f)
//            .setDurationPush(PushDownAnim.DEFAULT_PUSH_DURATION)
//            .setDurationRelease(PushDownAnim.DEFAULT_RELEASE_DURATION)
//            .setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR)
//            .setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR)
//
//            .setOnClickListener {
//
//            }

        btGoNoti.setOnClickListener {
            val intent = Intent(this@HomeActivity, NotiActivity::class.java)

            startActivity(intent)
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
        }

//        PushDownAnim.setPushDownAnimTo(btGoTrans)
//            .setScale(MODE_STATIC_DP, 8f)
//            .setDurationPush(PushDownAnim.DEFAULT_PUSH_DURATION)
//            .setDurationRelease(PushDownAnim.DEFAULT_RELEASE_DURATION)
//            .setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR)
//            .setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR)
//            .setOnClickListener {
//                if (CheckConnection.haveNetworkConnection(this@HomeActivity)) {
//                    val intent = Intent(this@HomeActivity, TranslateActivity::class.java)
//                    startActivity(intent)
//                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
//                } else {
////                    CheckConnection.ShowDialog(this@HomeActivity)
//                }
//            }


        btGoTrans.setOnClickListener {
            if (CheckConnection.haveNetworkConnection(this@HomeActivity)) {
                val intent = Intent(this@HomeActivity, TranslateActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
            } else {
//                    CheckConnection.ShowDialog(this@HomeActivity)
            }
        }

//        PushDownAnim.setPushDownAnimTo(btGoGrammar)
//            .setScale(MODE_STATIC_DP, 8f)
//            .setDurationPush(PushDownAnim.DEFAULT_PUSH_DURATION)
//            .setDurationRelease(PushDownAnim.DEFAULT_RELEASE_DURATION)
//            .setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR)
//            .setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR)
//            .setOnClickListener {
//                val intent = Intent(this@HomeActivity, GrammaActivity::class.java)
//                startActivity(intent)
//                overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
//            }


        btGoGrammar.setOnClickListener {
            val intent = Intent(this@HomeActivity, GrammaActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
        }
//
//        PushDownAnim.setPushDownAnimTo(btGoListen)
//            .setScale(MODE_STATIC_DP, 8f)
//            .setDurationPush(PushDownAnim.DEFAULT_PUSH_DURATION)
//            .setDurationRelease(PushDownAnim.DEFAULT_RELEASE_DURATION)
//            .setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR)
//            .setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR)
//            .setOnClickListener {
//                val intent = Intent(this@HomeActivity, Level2Activity::class.java)
//                intent.putExtra("kindd",1)
//                startActivity(intent)
//                overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
//            }

        btGoListen.setOnClickListener {
            val intent = Intent(this@HomeActivity, Level2Activity::class.java)
            intent.putExtra("kindd",1)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
        }

//        PushDownAnim.setPushDownAnimTo(btGoSpeak)
//            .setScale(MODE_STATIC_DP, 8f)
//            .setDurationPush(PushDownAnim.DEFAULT_PUSH_DURATION)
//            .setDurationRelease(PushDownAnim.DEFAULT_RELEASE_DURATION)
//            .setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR)
//            .setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR)
//            .setOnClickListener {
//                val intent = Intent(this@HomeActivity, LevelSpeakActivity::class.java)
//                startActivity(intent)
//                overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
//            }

        btGoSpeak.setOnClickListener {
            val intent = Intent(this@HomeActivity, LevelSpeakActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
        }

    }


    private fun AnhXa() {
        btgomulti = findViewById(R.id.bt_gomulti)
        btGoNoti = findViewById(R.id.go_noti)
        //        tvNoti = findViewById(R.id.num_noti);
        btGoTrans = findViewById(R.id.bt_goTrans)
        btGoGrammar = findViewById(R.id.bt_goGrammar)
        btGoListen = findViewById(R.id.bt_golisten)
        btGoSpeak = findViewById(R.id.bt_gospeak)
    }


    override fun onBackPressed() {
//        super.onBackPressed()
    }
}
