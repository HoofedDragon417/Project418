package com.example.project418.adapters.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(context: FragmentActivity, private val fragments: List<Fragment>) :
    FragmentStateAdapter(context) {
    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}