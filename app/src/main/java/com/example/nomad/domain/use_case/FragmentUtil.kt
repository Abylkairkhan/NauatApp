package com.example.nomad.domain.use_case

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.nomad.R

object FragmentUtil {

    fun refreshFragment(context: Context?) {
        context?.let {
            val fragmentManager = (context as? AppCompatActivity)?.supportFragmentManager
            fragmentManager?.let {
                val currentFragment = fragmentManager.findFragmentById(R.id.nav_host_fragment_container)
                currentFragment?.let {
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.detach(it)
                    fragmentTransaction.attach(it)
                    fragmentTransaction.commit()
//                    Log.d("MyLog", "Fragment Refresh Commit")
                }
            }
        }
//        Log.d("MyLog", "Fragment Refresh Fun End")
    }
}