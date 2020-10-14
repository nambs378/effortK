package com.example.effortkotlin.View

import android.content.Context
import android.graphics.Color
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.TextView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.effortkotlin.R
import com.example.effortkotlin.factory.Server
import com.example.effortkotlin.factory.setBitmapImage
import java.util.HashMap

class ResultActivity : AppCompatActivity() {
    lateinit var tvResult: TextView
    internal var totalQ: Double = 0.toDouble()
    internal var score:Double = 0.toDouble()
    internal var levelresult: Int = 0
    lateinit var view: View
    var kind : String? = ""
    var idlevel : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        tvResult = findViewById(R.id.tv_result)
        view = findViewById(R.id.view_result)
        setBitmapImage.setBackground(applicationContext, view, R.drawable.background_all)

        val bundle = intent.extras
        totalQ = bundle!!.getInt("totalQuestion").toDouble()
        score = bundle.getInt("score").toDouble()
        levelresult = bundle.getInt("levelresult")
        kind = bundle.getString("kind2")
        idlevel = bundle.getString("idlevel2")





        val resultT: String
        val a = totalQ / score

        val updateLevelState  = UpdateLevelState(this)
        if (a >= 1.0 && a < 2.0) {
            resultT = "Great"
            tvResult.text = resultT
            updateLevelState.execute(kind,idlevel,"1")
            tvResult.setTextColor(Color.GREEN)
        } else if (a == 2.0) {
            resultT = "Not bad"
            tvResult.text = resultT
            updateLevelState.execute(kind,idlevel,"2")
            tvResult.setTextColor(Color.YELLOW)
        } else if (a > 2.0) {
            resultT = "Bad"
            tvResult.setTextColor(Color.RED)
            updateLevelState.execute(kind,idlevel,"3")
            tvResult.text = resultT
        }
        tvResult.setOnClickListener { finish() }
        Handler().postDelayed({
            try {
                //                    Level2Activity.levelAdapter.notifyDataSetChanged();
            } catch (e: Exception) {

            }
            finish()
        }, 3000)


    }


}

class UpdateLevelState(internal var ctx: Context) : AsyncTask<String, Void, Void>(){
    var idlevel : String?= ""
    var kind : Int? = 0
    var duongdan : String? =""
    var tablename : String? = ""
    var levelstateasyn : String? = ""
    override fun doInBackground(vararg p0: String?): Void? {
        kind = p0[0]?.toInt()
        idlevel = p0[1]
        levelstateasyn = p0[2]


        if (kind ==1){
            tablename = "levelgiaotiep"
        } else if (kind ==2){
            tablename = "leveltuvung"
        }

        Log.e("zxczxcz","idlevel : $idlevel  levelstateasyn : $levelstateasyn  table:  $tablename")

        val requestQueue = Volley.newRequestQueue(ctx)
        val stringRequest = object : StringRequest(Request.Method.POST, Server.DuongdanUpdateLevelState ,
            Response.Listener { response ->
                Log.e("zxczxcz",response.toString())

//                TranslateActivity.transVocaList.clear()
//
//                TranslateActivity.setAdapterr(ctx)

            }, Response.ErrorListener { error -> Log.e("errrorr", error.toString()) }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String?> {
                val param = HashMap<String, String?>()
                param["idlevel"] = idlevel
                param["levelstatekey"] = levelstateasyn
                param["tablename"] = tablename
                return param
            }
        }
        requestQueue.add(stringRequest)

        return null

    }

}

