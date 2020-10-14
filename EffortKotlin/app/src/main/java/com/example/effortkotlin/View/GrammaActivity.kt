package com.example.effortkotlin.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.example.effortkotlin.R
import com.example.effortkotlin.factory.setBitmapImage
import com.google.android.material.tabs.TabLayout

class GrammaActivity : AppCompatActivity() {
    lateinit var pagerGramma: ViewPager
    lateinit var tabLayoutGramma: TabLayout
    lateinit var view: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gramma)


        view = findViewById(R.id.view_gramma)
        setBitmapImage.setBackground(applicationContext, view, R.drawable.background_all)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle("Grammar")
        val actionBar = supportActionBar
        actionBar?.setHomeButtonEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        addControl()


    }


    private fun addControl() {
        pagerGramma = findViewById(R.id.view_pager_gramma)
        tabLayoutGramma = findViewById(R.id.tab_layout_gramma)
        val manager = supportFragmentManager
        val adapter = com.example.effortkotlin.adapter.PagerGrammaAdapter(manager)
        pagerGramma.setAdapter(adapter)
        tabLayoutGramma.setupWithViewPager(pagerGramma)
        pagerGramma.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayoutGramma))
        tabLayoutGramma.setTabsFromPagerAdapter(adapter)
        tabLayoutGramma.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(pagerGramma))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                this.finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


}
