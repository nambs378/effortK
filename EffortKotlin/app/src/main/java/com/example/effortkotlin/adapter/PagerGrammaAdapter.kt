package com.example.effortkotlin.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.effortkotlin.fragment.fragmentCau
import com.example.effortkotlin.fragment.fragmentTu

class PagerGrammaAdapter(fm : FragmentManager) : FragmentStatePagerAdapter(fm) {



    override fun getItem(position: Int): Fragment {
        var frag: Fragment? = null
        when (position) {
            0 -> frag = fragmentCau()
            1 -> frag = fragmentTu()
        }

        return frag!!
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var title = ""
        when (position) {
            0 -> title = "Câu"
            1 -> title = "Từ"
        }
        return title
    }



}