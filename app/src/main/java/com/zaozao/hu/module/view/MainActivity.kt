package com.zaozao.hu.module.view

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationSet
import com.zaozao.hu.R
import com.zaozao.hu.anim.MyAnimation
import com.zaozao.hu.tools.AnimationFactory
import com.zaozao.hu.widgets.RoundTextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        flowContainer.setOnItemClickListener {
            val position: Int = it.tag as Int
            val roundTextView: RoundTextView = it as RoundTextView
            //  Toast.makeText(this, "position:$position" + "view:width${roundTextView.width},height${roundTextView.height}", Toast.LENGTH_LONG).show()
            doAnimAction(roundTextView, position)
        }
    }

    private fun doAnimAction(view: View, position: Int) {
        when (position) {
            0 -> doTranslate(view)
            1 -> doRotate(view)
            2 -> doScale(view)
            3 -> doAlpha(view)
            4 -> doAnimSet(view)
            5 -> doCustomAnim(view)
            6 -> doAttrTranslate(view)
            7 -> doMultiAnim(view)
            8 -> doSceneTransition(getString(R.string.app_name))
            9 -> doAnim(view)
            10 -> doMultiTransition(view)
            11 -> do3DRotate()
        }
    }

    private fun doTranslate(view: View) {
        val animationFactory: AnimationFactory = AnimationFactory.getInstance()
//        val anim: Animation = animationFactory.getTranslate(Animation.RELATIVE_TO_SELF, 0f,
//                Animation.RELATIVE_TO_PARENT, 1f,
//                Animation.RELATIVE_TO_SELF, 0f,
//                Animation.RELATIVE_TO_PARENT, 1f,
//                3000, false)
        val anim: Animation = animationFactory.getTranslate(0f, 100f, 0f, 200f, 3000)
        view.startAnimation(anim)
    }

    private fun doRotate(view: View) {
        val animationFactory: AnimationFactory = AnimationFactory.getInstance()
        val anim = animationFactory.getRotate(0f, 180f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f)
        view.startAnimation(anim)
    }

    private fun doScale(view: View) {
        val animationFactory: AnimationFactory = AnimationFactory.getInstance()
        val anim = animationFactory.getScaleAnim(0.4f, 1f,
                0.4f, 1f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f)
        view.startAnimation(anim)
    }

    private fun doAlpha(view: View) {
        val animationFactory: AnimationFactory = AnimationFactory.getInstance()
        val anim = animationFactory.getAlpha(0.5f, 1f)
        view.startAnimation(anim)
    }

    private fun doAnimSet(view: View) {
        val animationFactory: AnimationFactory = AnimationFactory.getInstance()
        val anim: AnimationSet = animationFactory.animSet
        view.startAnimation(anim)
    }

    private fun doCustomAnim(view: View) {
        val anim: Animation = MyAnimation()
        view.startAnimation(anim)
    }

    private fun doAttrTranslate(view: View) {
        val animationFactory: AnimationFactory = AnimationFactory.getInstance()
        animationFactory.getTranslate(view, 1000)
    }

    private fun doMultiAnim(view: View) {
        val animationFactory: AnimationFactory = AnimationFactory.getInstance()
        animationFactory.getMultiAnim(view, 2000)
    }

    private fun doSceneTransition(transitionName: String) {
        val activityOptionsCompat: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(this, custom, transitionName)
        val intent = Intent(this, TransitionAnimatorAty::class.java)
        startActivity(intent, activityOptionsCompat.toBundle())
    }

    private fun doAnim(view: View) {
        val activityOptionsCompat: ActivityOptionsCompat =
                ActivityOptionsCompat.makeClipRevealAnimation(view, 0, 0, view.right, view.bottom)
        val intent = Intent(this, TransitionAnimatorAty::class.java)
        startActivity(intent, activityOptionsCompat.toBundle())
    }

    private fun doMultiTransition(view: View) {
        val intent = Intent(this, MultiTransitionAty::class.java)
        val activityOptionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(view, 0, 0, view.width, view.height)
        startActivity(intent, activityOptionsCompat.toBundle())
    }

    private fun do3DRotate() {
        val intent = Intent(this, CameraMatrixAty::class.java)
        startActivity(intent)
    }
}
