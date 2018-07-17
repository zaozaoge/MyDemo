package com.zaozao.hu.module.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.zaozao.hu.R
import com.zaozao.hu.adapter.MyAdapter
import kotlinx.android.synthetic.main.activity_multi_transition_aty.*

class MultiTransitionAty : AppCompatActivity(), MyAdapter.OnItemClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multi_transition_aty)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        val list = ArrayList<String>()
        for (i in 1..10) {
            list.add("动画测试$i")
        }
        val myAdapter = MyAdapter(list, this)
        recyclerView.adapter = myAdapter
    }

    override fun onItemClick(view: TextView?, imageView: ImageView?) {
        val activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, Pair.create(view as View, getString(R.string.app_text)),
                Pair.create(imageView as View, getString(R.string.app_name)))
        val intent = Intent(this, TransitionAnimatorAty::class.java)
        startActivity(intent, activityOptionsCompat.toBundle())
    }
}
