package com.myfriendapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?)
    { super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tampilMyFriendsFragment()
    //    tampilAddFriendsFragment()
    }
    private fun gantiFragment(
        fragmentManager: FragmentManager,
        fragment: Fragment, frameId: Int
    ) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(frameId, fragment)
        transaction.commit()
    }
    fun tampilMyFriendsFragment() {
        gantiFragment(supportFragmentManager,
            MyFriendsFragment.newInstance(), R.id.contentFrame)
    }
    fun tampilAddFriendsFragment(){
        gantiFragment(supportFragmentManager,
            AddFriendsFragment.newInstance(), R.id.contentFrame)
    }
}

