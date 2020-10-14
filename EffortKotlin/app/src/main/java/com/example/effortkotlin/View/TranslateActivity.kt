package com.example.effortkotlin.View

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.NonNull
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.effortkotlin.Model.LevelGiaoTiep
import com.example.effortkotlin.Model.TransVoca
import com.example.effortkotlin.R
import com.example.effortkotlin.adapter.LevelGiaoTiepAdapter
import com.example.effortkotlin.adapter.TransListAdapter
import com.example.effortkotlin.api.TranslatorBackgroundTask
import com.example.effortkotlin.asyn.DeleteItemTrans
import com.example.effortkotlin.factory.LanguagesForSpinner
import com.example.effortkotlin.factory.Server
import com.example.effortkotlin.factory.setBitmapImage
import org.json.JSONException
import java.util.*
import kotlin.collections.ArrayList

class TranslateActivity : AppCompatActivity() {

    lateinit var view: View
    lateinit var etEN: EditText
    lateinit var btDich: Button
    lateinit var btChangeTrans:Button
    lateinit var btToMicro:Button
    lateinit var btgoTalk:Button

//    lateinit var tvKindInput: TextView
//    lateinit var tvKindResult:TextView

    lateinit var goScan:TextView
    lateinit var tvSpeakTop:TextView
    lateinit var tvSpeakBottom:TextView
    lateinit var spinInput : Spinner
    lateinit var spinOutput : Spinner

    var input : String? = ""
    var output : String? = ""

    var context: Context = this
     var kindTranslate: String? ="en-vi"

    private var mTTS: TextToSpeech? = null

    private val REQ_CODE_SPEECH_INPUT = 100

    private val REQUEST_FORM = 1



    companion object{
//        lateinit var transListAdapter: TransListAdapter
//        lateinit var transVocaList: MutableList<TransVoca>
        lateinit var rvListTranslate: RecyclerView
        lateinit var tvValue: TextView
        lateinit var transVocaList: MutableList<TransVoca>
        lateinit var transListAdapter: TransListAdapter

        fun setAdapterr( context: Context) {

            val requestQueue = Volley.newRequestQueue(context)
            val jsonArrayRequest = JsonArrayRequest(Server.Duongdangetalltranslist, Response.Listener { response ->
                if (response != null) {

                    var vocatrans: String
                    var mean: String
                    var importants: Int

                    for (i in 0 until response.length()) {
                        try {
                            val jsonObject = response.getJSONObject(i)
                            vocatrans = jsonObject.getString("vocatrans")
                            mean = jsonObject.getString("mean")
                            importants = jsonObject.getInt("importants")

                            transVocaList.add(TransVoca( vocatrans, mean, importants))


                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }

                    }
                    Log.e("zxc", " $transVocaList")



                    transListAdapter = TransListAdapter(transVocaList, context)
                    val layoutManager = LinearLayoutManager(context)
                    rvListTranslate.layoutManager = layoutManager
                    rvListTranslate.adapter = transListAdapter

                }
            }, Response.ErrorListener { error -> Log.e("cvcv", " $error") })

            requestQueue.add(jsonArrayRequest)
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translate)

        view = findViewById(R.id.view_translate)
        setBitmapImage.setBackground(applicationContext, view, R.drawable.background_all)


        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle("Translate")
        val actionBar = supportActionBar
        actionBar?.setHomeButtonEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        AnhXa()

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

//        btChangeTrans.performClick()
//        btChangeTrans.performClick()
        //Set Data In Spinners


        spinEvent()

        goScan.setOnClickListener {
            val intent = Intent(this@TranslateActivity, ScanActivity::class.java)
            startActivityForResult(intent, REQUEST_FORM)
        }

//        val intent = intent
//        val Eng = intent.getStringExtra("EnglishScan")
//        val Vie = intent.getStringExtra("VietnameseScan")



        btgoTalk.setOnClickListener {
            val intent1 = Intent(this@TranslateActivity, TalkTranstalteActivity::class.java)
            startActivity(intent1)
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
        }

        btDich.setOnClickListener { view ->
            kindTranslate = "${LanguagesForSpinner().getLangCodeEN(spinInput.selectedItemPosition)}" +
                    "-${LanguagesForSpinner().getLangCodeEN(spinOutput.selectedItemPosition)}"

            Log.e("spinner",""+kindTranslate)

            if (etEN.text.toString().trim { it <= ' ' }.length > 0) {
                btDich(view)
            } else {
                Toast.makeText(this@TranslateActivity, "Vui lòng nhập từ muốn dịch", Toast.LENGTH_SHORT).show()
            }

            try {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus?.getWindowToken(), 0)
            } catch (e: Exception) {

            }
        }

        btToMicro.setOnClickListener { promptSpeechInput() }

        tvSpeakTop.setOnClickListener {
            mTTS?.setPitch(1.05f)
            mTTS?.setSpeechRate(0.88f)

            mTTS?.speak(etEN.text.toString(), TextToSpeech.QUEUE_FLUSH, null)
        }

        tvSpeakBottom.setOnClickListener {
            mTTS?.setPitch(1.05f)
            mTTS?.setSpeechRate(0.88f)

            mTTS?.speak(tvValue.text.toString(), TextToSpeech.QUEUE_FLUSH, null)
        }

        transVocaList = ArrayList<TransVoca>();

        setAdapterr(context)

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(@NonNull recyclerView: RecyclerView, @NonNull viewHolder: RecyclerView.ViewHolder, @NonNull viewHolder1: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(@NonNull viewHolder: RecyclerView.ViewHolder, i: Int) {

                val deleteItemTrans = DeleteItemTrans(this@TranslateActivity)
//                Log.e("zxcz",transListAdapter.getTranAt(viewHolder.getAdapterPosition()))
                deleteItemTrans.execute(transListAdapter.getTranAt(viewHolder.getAdapterPosition()))



            }
        }).attachToRecyclerView(rvListTranslate)


    }


    private fun spinEvent() {

        val languages: Array<String> = LanguagesForSpinner().getLangsEN()

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, languages)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinInput.setAdapter(adapter)
        spinOutput.setAdapter(adapter)
        spinInput.setSelection(languages.indexOf("English"))
        spinOutput.setSelection(languages.indexOf("Vietnamese"))


        input = spinInput.selectedItem.toString()
        output = spinOutput.selectedItem.toString()
        tvSpeakTop.text = input
        tvSpeakBottom.text = output

        btChangeTrans.setOnClickListener {
            input = spinInput.selectedItem.toString()
            output = spinOutput.selectedItem.toString()
            spinInput.setSelection(languages.indexOf(output))
            spinOutput.setSelection(languages.indexOf(input))
            tvSpeakTop.text = output
            tvSpeakBottom.text = input

        }

        spinInput.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                tvSpeakTop.text = spinInput.selectedItem.toString()


            }

        }

        spinOutput.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                tvSpeakBottom.text = spinOutput.selectedItem.toString()

            }

        }
    }

    private fun AnhXa() {
        etEN = findViewById(R.id.et_ip_translate)
        btDich = findViewById(R.id.bt_translate)
        tvValue = findViewById(R.id.tv_result_translate)
        btChangeTrans = findViewById(R.id.swap)
//        tvKindResult = findViewById(R.id.tv_kind_result)
        goScan = findViewById(R.id.go_scan)
        btToMicro = findViewById(R.id.bt_tospeak)
        btgoTalk = findViewById(R.id.bt_go_talk)
        tvSpeakTop = findViewById(R.id.tv_speak_kind_top)
        tvSpeakBottom = findViewById(R.id.tv_speak_kind_bottom)
        rvListTranslate = findViewById(R.id.rv_listTranslate)
        spinInput = findViewById(R.id.id_kind_input)
        spinOutput = findViewById(R.id.id_kind_output)
    }

    private fun Translate(textToBeTranslated: String, languagePair: String): String {
        val translatorBackgroundTask = TranslatorBackgroundTask(this)
        val translationResult = translatorBackgroundTask.execute(textToBeTranslated, languagePair) // Returns the translated text as a String
        return translationResult.get()
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

     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQ_CODE_SPEECH_INPUT -> {
                if (resultCode == RESULT_OK && null != data) {

                    val result = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    etEN.setText(result!![0])
                }
            }
            REQUEST_FORM -> {
                if (resultCode == RESULT_OK && null != data) {

                    val Eng = data.getStringExtra("EnglishScan")
                    val Vie = data.getStringExtra("VietnameseScan")
                    if (Eng != null) {
                        etEN.setText(Eng)
                        tvValue.text = Vie
                    }
                }
            }

        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                this.finish()
                mTTS?.shutdown()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    fun btDich(view: View) {
        var textToBeTranslated = ""
        textToBeTranslated = etEN.text.toString()
        val languagePair = kindTranslate //English to bengali ("<source_language>-<target_language>")
        //Executing the translation function
        Translate(textToBeTranslated, languagePair!!)

    }


    override fun onDestroy() {
        super.onDestroy()
        mTTS?.shutdown()

    }

}
