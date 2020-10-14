package com.example.effortkotlin.View

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.effortkotlin.R
import com.example.effortkotlin.api.TranslateTalkEng
import com.example.effortkotlin.api.TranslateTalkViet
import com.example.effortkotlin.factory.setBitmapImage
import java.util.*


class TalkTranstalteActivity : AppCompatActivity() {
    lateinit var btEnglishTalk: Button
    lateinit var btVietnamTalk: Button
    lateinit var btSpeakEng: Button
    lateinit var btSpeakViet: Button

    companion object {
        lateinit var tvTalkEng: TextView
        lateinit var tvTalkViet: TextView
         var ngonngu: String = "";
    }

    private val REQ_CODE_SPEECH_INPUT = 100
    internal var clickedEng = false
    internal var clickedViet = false
    internal var context: Context = this
    lateinit var view: View
    internal var resutlTalk: String? = null
    var languagePair: String? = ""

    private var mTTSen: TextToSpeech? = null
    private var mTTSvi: TextToSpeech? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_talk_transtalte)

        view = findViewById(R.id.view_talk)
        setBitmapImage.setBackground(applicationContext, view, R.drawable.background_all)


        val toolbar = findViewById(R.id.toolbar) as Toolbar


        setSupportActionBar(toolbar)
        supportActionBar?.setTitle("Talk")
        val actionBar = supportActionBar
        actionBar?.setHomeButtonEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)



        mTTSen = TextToSpeech(this, TextToSpeech.OnInitListener { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = mTTSen?.setLanguage(Locale.ENGLISH)

                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "Language not supported")
                } else {
                    //                            mButtonSpeak.setEnabled(true);
                }
            } else {
                Log.e("TTS", "Initialization failed")
            }
        })

//        val languageToLoad = "vi_VN" // your language
        val locale = Locale("vi","VN")


        mTTSvi = TextToSpeech(this, TextToSpeech.OnInitListener { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = mTTSvi?.setLanguage(Locale.CHINESE)

                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("TTS", "Language not supported")
                } else {
                    //                            mButtonSpeak.setEnabled(true);
                }
            } else {
                Log.e("TTS", "Initialization failed")
            }
        })


        AnhXa()

        btEnglishTalk.setOnClickListener {
            tvTalkEng.text = null
            tvTalkViet.text = null
            promptSpeechInputUS()
            clickedEng = true
//            clickedViet = false
            btSpeakEng.visibility = View.INVISIBLE
            btSpeakViet.visibility = View.VISIBLE
        }

        btVietnamTalk.setOnClickListener {
            tvTalkEng.text = null
            tvTalkViet.text = null
            promptSpeechInputVN()
            clickedViet = true
//            clickedEng = false

            btSpeakViet.visibility = View.INVISIBLE
            btSpeakEng.visibility = View.VISIBLE
        }

        btSpeakEng.setOnClickListener {
            mTTSen?.setPitch(1.05f)
            mTTSen?.setSpeechRate(0.9f)
            ngonngu = "en"
            mTTSen?.speak(tvTalkEng.text.toString(), TextToSpeech.QUEUE_FLUSH, null)
        }

        btSpeakViet.setOnClickListener {
            mTTSen?.setPitch(1.1f)
            mTTSen?.setSpeechRate(1f)
            ngonngu = "vi"
            mTTSen?.speak(tvTalkViet.text.toString(), TextToSpeech.QUEUE_FLUSH, null)
        }


    }

    private fun AnhXa() {
        btEnglishTalk = findViewById(R.id.bt_talk_english)
        btVietnamTalk = findViewById(R.id.bt_talk_vietnamese)
        btSpeakEng = findViewById(R.id.bt_talk_speak_english)
        btSpeakViet = findViewById(R.id.bt_talk_speak_vietnamese)
        tvTalkEng = findViewById(R.id.tv_talk_eng)
        tvTalkViet = findViewById(R.id.tv_talk_viet)

    }


    private fun promptSpeechInputVN() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "vi_VN")

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

    private fun promptSpeechInputUS() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")

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



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQ_CODE_SPEECH_INPUT -> {
                if (resultCode == RESULT_OK && null != data) {
                    val result = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    if (clickedEng == true) {
                        tvTalkEng.text = result!![0]
                        clickedEng = false
                        languagePair = "en-vi"
                        Translate(result[0], languagePair!!)
                    } else if (clickedViet == true) {
                        tvTalkViet.text = result!![0]
                        clickedViet = false
                        languagePair = "vi-en"
                        TranslateViet(result[0], languagePair!!)
                    }
                }
            }
        }
    }


    private fun Translate(textToBeTranslated: String, languagePair: String): String {
        val translateTalkEng = TranslateTalkEng(this)
        val translationResult = translateTalkEng.execute(
            textToBeTranslated,
            languagePair
        ) // Returns the translated text as a String
        return translationResult.get()
    }


    private fun TranslateViet(textToBeTranslated: String?, languagePair: String?) {
        val translateTalk = TranslateTalkViet(context)
        var translationResult: String? = ""
        translationResult = java.lang.String.valueOf(
            translateTalk.execute(
                textToBeTranslated,
                languagePair
            )
        ) // Returns the translated text as a String
        Log.d("Translation Result", translationResult) // Logs the result in Android Monitor
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                this.finish()
                mTTSen?.shutdown()

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mTTSen?.shutdown()

    }
}
