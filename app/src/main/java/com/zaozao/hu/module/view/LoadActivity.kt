package com.zaozao.hu.module.view

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import com.zaozao.hu.R
import com.zaozao.hu.tools.AnimationFactory
import kotlinx.android.synthetic.main.activity_load.*

class LoadActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load)
        val animationFactory: AnimationFactory = AnimationFactory.getInstance()
        animationFactory.getFadingAnim(enter, 4000, object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
                enter.text = getText(R.string.producer)
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                val intent = Intent(this@LoadActivity, FunctionListAty::class.java)
                startActivity(intent)
                this@LoadActivity.finish()
            }
        })
    }


}
