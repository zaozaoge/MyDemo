package com.zaozao.hu.module.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.zaozao.hu.R

class TransitionAnimatorAty : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transition_animator_aty)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAfterTransition()
    }
}
