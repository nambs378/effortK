package com.example.effortkotlin.View

import android.app.AlarmManager
import android.app.Dialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.effortkotlin.Model.NotiVoca
import com.example.effortkotlin.R
import com.example.effortkotlin.adapter.NotiAdapter
import com.example.effortkotlin.database.QuizDBHelper
import com.example.effortkotlin.factory.setBitmapImage
import com.example.effortkotlin.service.AlarmBroadcastReceiver
import com.example.effortkotlin.service.NotiVocaReceiver
import java.util.*

class NotiActivity : AppCompatActivity() {

    lateinit var dialogAdd: Button
    lateinit var btSaveSetting:Button
    lateinit var btCancelSetting:Button
    lateinit var recyclerView: RecyclerView
    lateinit var sw_quizvoca: Switch
    lateinit var sw_notion:Switch
    lateinit var etTuvung: EditText
    lateinit var etNghia:EditText
    lateinit var etTimeNoti:EditText
    lateinit var btadd: TextView
    lateinit var view: View

    lateinit var quizDBHelper: QuizDBHelper
    lateinit var notiAdapter: NotiAdapter
    private var notiVocaList: MutableList<NotiVoca>? = null
    internal var levelActivity: Level2Activity? = null

    lateinit var dialog_voca: String
    lateinit var dialog_mean:String

    private val switchOnOff: Boolean = false
    val SWITCH1 = "switch1"
    val SHARED_PREFS = "sharedPrefs"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noti)

        view = findViewById(R.id.view_noti)
        setBitmapImage.setBackground(applicationContext, view, R.drawable.background_all)
        quizDBHelper = QuizDBHelper(applicationContext)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Notification"
        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        recyclerView = findViewById(R.id.rv_notivoca)
        btadd = findViewById(R.id.bt_addnotivoca)

        notiVocaList = quizDBHelper.getAllNoti()

        notiAdapter = NotiAdapter(notiVocaList!!, applicationContext)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = notiAdapter

        btadd.setOnClickListener {
            val dialog = Dialog(this@NotiActivity)
            dialog.setContentView(R.layout.dialog_addnoti)
            dialog.setTitle("")
            etTuvung = dialog.findViewById(R.id.et_tuvung)
            etNghia = dialog.findViewById(R.id.et_nghia)
            dialogAdd = dialog.findViewById(R.id.dialog_addvocanoti)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            dialogAdd.setOnClickListener {




                dialog_voca = etTuvung.text.toString()
                dialog_mean = etNghia.text.toString()

                if (dialog_voca.equals("") ) {
                    etTuvung.error = "Vui lòng nhập từ vựng"
                } else if (dialog_mean.equals("")) {
                    etNghia.error = "Vui lòng nhập nghĩa"
                } else if (!dialog_voca.equals("") && !dialog_mean.equals("")){
                    quizDBHelper.insertVocaNoti(dialog_voca, dialog_mean, 0)
                    dialog.dismiss()
                    notiVocaList!!.clear()
                    notiVocaList!!.addAll(quizDBHelper.getAllNoti())
                    notiAdapter.notifyItemRangeChanged(0, notiVocaList!!.size)
                }


            }
            dialog.show()
        }


        //Swipe out delete
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                viewHolder1: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, i: Int) {
                notiAdapter.getNotiAt(viewHolder.adapterPosition)?.let { quizDBHelper.deleteVocaNoti(it) }
                notiVocaList!!.clear()
                notiVocaList!!.addAll(quizDBHelper.getAllNoti())
                notiAdapter.notifyDataSetChanged()


            }
        }).attachToRecyclerView(recyclerView)


    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_settings, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                //Write your logic here
                this.finish()
                return true
            }
            R.id.myswitch -> {
                showSetting()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    private fun showSetting() {
        val dialog = Dialog(this@NotiActivity)
        dialog.setContentView(R.layout.dialog_noti_settings)
        dialog.setTitle("")
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        sw_quizvoca = dialog.findViewById(R.id.sw_quizvoca)
        sw_notion = dialog.findViewById(R.id.sw_notion)
        btSaveSetting = dialog.findViewById(R.id.bt_savesetting)
        btCancelSetting = dialog.findViewById(R.id.bt_cancelsetting)
        etTimeNoti = dialog.findViewById(R.id.et_timenoti)


        val settings = quizDBHelper.getSetting(1)

        if (settings.getSw() == 0) {
            sw_quizvoca.isChecked = false
            sw_notion.isChecked = false

        } else if (settings.getSw() == 1) {
            sw_quizvoca.isChecked = true
            sw_notion.isChecked = false

        } else if (settings.getSw() == 2) {
            sw_quizvoca.isChecked = false
            sw_notion.isChecked = true
        }


        sw_quizvoca.setOnCheckedChangeListener{compoundButton, b ->
            if (b){
                sw_notion.isChecked = false;
            } else {

            }

        }

        sw_notion.setOnCheckedChangeListener{compoundButton, b ->
            if (b){
                sw_quizvoca.isChecked = false;
            } else {

            }

        }
        val alarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val _intent = Intent(applicationContext, AlarmBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(applicationContext, 0, _intent, 0)


        val it = Intent(applicationContext, NotiVocaReceiver::class.java)
        val pendingJustVoca = PendingIntent.getBroadcast(applicationContext, 0, it, 0)


        val t = Calendar.getInstance()
        t.timeInMillis = System.currentTimeMillis()

        etTimeNoti.setText(settings.getTimeSet().toString() + "")

        btSaveSetting.setOnClickListener {
            val timeNoti = Integer.parseInt(etTimeNoti.text.toString())

            if (timeNoti<5){
                etTimeNoti.error = "Tối thiểu 5 phút"
            } else {
                if (sw_quizvoca.isChecked) {
                    alarmManager.setRepeating(AlarmManager.RTC, t.timeInMillis, (timeNoti * 60000).toLong(), pendingIntent)

                    alarmManager.cancel(pendingJustVoca)

                    quizDBHelper.updateSetting(timeNoti, 1)


                } else if (sw_notion.isChecked) {
                    alarmManager.setRepeating(
                        AlarmManager.RTC,
                        t.timeInMillis,
                        (timeNoti * 60000).toLong(),
                        pendingJustVoca
                    )

                    alarmManager.cancel(pendingIntent)

                    quizDBHelper.updateSetting(timeNoti, 2)

                } else if (!sw_quizvoca.isChecked && !sw_notion.isChecked) {
                    alarmManager.cancel(pendingIntent)
                    alarmManager.cancel(pendingJustVoca)
                    quizDBHelper.updateSetting(timeNoti, 0)
                }

                dialog.dismiss()
            }


        }

        btCancelSetting.setOnClickListener { dialog.dismiss() }


        dialog.show()

    }


}
