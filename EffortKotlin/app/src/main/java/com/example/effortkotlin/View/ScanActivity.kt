package com.example.effortkotlin.View

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import com.example.effortkotlin.R
import com.example.effortkotlin.api.TranslateScan
import com.example.effortkotlin.api.TranslatorBackgroundTask
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.text.TextBlock
import com.google.android.gms.vision.text.TextRecognizer
import java.io.IOException

class ScanActivity : AppCompatActivity() {

    lateinit var cameraView: SurfaceView
    lateinit var cameraSource: CameraSource
    internal val RequestCameraPermissionID = 1001
     var transtext: String?=""


    //Translate
    internal var etEN: EditText? = null
    internal var btDich: Button? = null
    internal var context: Context = this

    companion object{
        private val REQUEST_CODE = 1234
        lateinit var textView: TextView
        var tvValue: TextView? = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        cameraView = findViewById(R.id.surface_view)
        textView = findViewById(R.id.tv_scan)

        val textRecognizer = TextRecognizer.Builder(applicationContext).build()

        if (!textRecognizer.isOperational) {
            Log.w("MainActivity", "Detector dependencies are not yet available")
        } else {

            cameraSource = CameraSource.Builder(applicationContext, textRecognizer)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1280, 1024)
                .setRequestedFps(2.0f)
                .setAutoFocusEnabled(true)
                .build()

            cameraView.holder.addCallback(object : SurfaceHolder.Callback {
                override fun surfaceCreated(surfaceHolder: SurfaceHolder) {

                    try {
                        if (ActivityCompat.checkSelfPermission(
                                applicationContext,
                                Manifest.permission.CAMERA
                            ) !== PackageManager.PERMISSION_GRANTED
                        ) {

                            ActivityCompat.requestPermissions(
                                this@ScanActivity,
                                arrayOf(Manifest.permission.CAMERA),
                                RequestCameraPermissionID
                            )
                            return
                        }
                        cameraSource.start(cameraView.holder)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }

                override fun surfaceChanged(surfaceHolder: SurfaceHolder, i: Int, i1: Int, i2: Int) {

                }

                override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) {
                    cameraSource.stop()
                }
            })

            textRecognizer.setProcessor(object : Detector.Processor<TextBlock> {
                override fun release() {

                }

                override fun receiveDetections(detections: Detector.Detections<TextBlock>) {

                    val items = detections.detectedItems
                    if (items.size() != 0) {
                        textView.post {
                            val stringBuilder = StringBuilder()
                            for (i in 0 until items.size()) {
                                val item = items.valueAt(i)
                                stringBuilder.append(item.value)
                                stringBuilder.append("\n")
                            }
                            //                                textView.setText(stringBuilder.toString());
                            transtext = stringBuilder.toString()
                            val languagePair = "en-vi" //English to bengali ("<source_language>-<target_language>")
                            //Executing the translation function
                            Translate(transtext!!, languagePair)
                        }
                    }
                }
            })
        }


        textView.setOnClickListener {
            val intent = Intent(this@ScanActivity, TranslateActivity::class.java)
//            quizDBHelper.insertTranslistVoca(transtext, TranslateScan.resultString, 0)
            intent.putExtra("EnglishScan", transtext)
            intent.putExtra("VietnameseScan", TranslateScan.resultString)
            setResult(Activity.RESULT_OK, intent)
//            overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
            finish()
            //Toast.makeText(context, transtext + " "+ TranslateScan.resultString, Toast.LENGTH_SHORT).show();
        }


    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        when (requestCode) {
            RequestCameraPermissionID -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.CAMERA
                        ) !== PackageManager.PERMISSION_GRANTED
                    ) {
                        return
                    }
                    try {
                        cameraSource.start(cameraView.holder)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            }
        }
    }

    private fun Translate(textToBeTranslated: String, languagePair: String): String {
        val translateScan = TranslateScan(this)
        val translationResult = translateScan.execute(textToBeTranslated, languagePair) // Returns the translated text as a String
        return translationResult.get()
    }




}
