package com.example.effortkotlin.View

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.effortkotlin.R
import com.example.effortkotlin.factory.setBitmapImage
import com.github.ybq.android.spinkit.style.Wave

class MainActivity : AppCompatActivity() {
    lateinit var view: View
    lateinit var logo: ImageView
    lateinit var fromlogo: Animation

    companion object{
        var widthScreen: Int = 0
        var heightScreen: Int = 0
    }

    var MY_PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestStoragePermission()

        view = findViewById(R.id.view_main)
        setBitmapImage.setBackground(applicationContext, view, R.drawable.background_all)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        heightScreen = displayMetrics.heightPixels
        widthScreen = displayMetrics.widthPixels

        logo = findViewById(R.id.logo)

        fromlogo = AnimationUtils.loadAnimation(this, R.anim.fromlogo)
        logo.animation = fromlogo


        val progressBar = findViewById(R.id.spin_kit) as ProgressBar
        val wave = Wave()
        progressBar.indeterminateDrawable = wave

//        val tu1 : String = "AbcDe"
//        val tu2 : String = "aBCde"
//
//        val tu1up : String = tu1.toUpperCase()
//        val tu2up : String = tu2.toUpperCase()
//
//        if (tu1up.equals(tu2up)){
//            Log.e("check","dung roi : $tu1 , $tu2" )
//
//        } else {
//            Log.e("check","ggwp met roif day" )
//
//        }

        Handler().postDelayed({
            val intent = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
        }, 4000)




    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_CONTACTS -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Handler().postDelayed({
                        val intent = Intent(this@MainActivity, HomeActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
                    }, 4000)

                } else {

                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
                Handler().postDelayed({
                    val intent = Intent(this@MainActivity, HomeActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
                }, 4000)
            }
        }



    }


    private fun requestStoragePermission() {
//        if (ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.SYSTEM_ALERT_WINDOW
//            ) == PackageManager.PERMISSION_GRANTED
//        ) return
//        if (ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.INTERNET
//            ) == PackageManager.PERMISSION_GRANTED
//        ) return
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) return
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) return
//        if (ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_NETWORK_STATE
//            ) == PackageManager.PERMISSION_GRANTED
//        ) return
        ActivityCompat.requestPermissions(
            this, arrayOf(
//                Manifest.permission.SYSTEM_ALERT_WINDOW,
//                Manifest.permission.INTERNET,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE
//                Manifest.permission.ACCESS_NETWORK_STATE
            ), MY_PERMISSIONS_REQUEST_READ_CONTACTS
        )
    }

}
