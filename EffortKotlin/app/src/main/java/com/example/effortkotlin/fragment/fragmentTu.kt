package com.example.effortkotlin.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.effortkotlin.Model.GrammarKind
import com.example.effortkotlin.R
import com.example.effortkotlin.adapter.GrammarKindAdapter
import com.example.effortkotlin.factory.Server
import org.json.JSONArray
import org.json.JSONException
import java.util.HashMap

class fragmentTu : Fragment() {


    lateinit var recyclerView: RecyclerView
    lateinit var grammarKindAdapter: GrammarKindAdapter
    lateinit var grammarKindList: List<GrammarKind>
    internal var kind = "cau"
    lateinit var gramkindlist: MutableList<GrammarKind>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_cau, container, false)
        recyclerView = view.findViewById(R.id.rv_Cau_fragment)
        gramkindlist = ArrayList<GrammarKind>()
        setAdapter()

        return view

    }

    private fun setAdapter() {

//        if (kind == 1) {
//            duongdan = Server.Duongdantugiaotiep
//        } else if (kind == 2) {
//            duongdan = Server.Duongdantuvung
//        }

        val requestQueue = Volley.newRequestQueue(activity)
        val stringRequest = object : StringRequest(Request.Method.POST, Server.Duongdangrammar1,
            Response.Listener { response ->
                var grammar: String
                var description: String
                var pdf: Int
                var cautu: Int
                if (response != null) {
                    try {
                        val jsonArray = JSONArray(response)

                        for (i in 0 until jsonArray.length()) {
                            val jsonObject = jsonArray.getJSONObject(i)
                            grammar = jsonObject.getString("gramma")
                            description = jsonObject.getString("description")
                            pdf = jsonObject.getInt("pdf")
                            cautu = jsonObject.getInt("cautu")

                            gramkindlist.add(GrammarKind(grammar, description, pdf, cautu))
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

//                    Log.e("vxxcv", tuVunglist.toString())


                    grammarKindAdapter = GrammarKindAdapter(activity,gramkindlist,  kind)
                    val layoutManager = LinearLayoutManager(context)
                    recyclerView.layoutManager = layoutManager
                    recyclerView.adapter = grammarKindAdapter


                }
            }, Response.ErrorListener { error -> Log.e("errrorr", error.toString()) }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String?> {
                val param = HashMap<String, String?>()
                param["cautu"] = "2"
                return param
            }
        }
        requestQueue.add(stringRequest)


    }


}