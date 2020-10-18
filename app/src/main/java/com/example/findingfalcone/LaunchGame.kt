package com.example.findingfalcone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.intro_logo.*
import timber.log.Timber

class LaunchGame: Fragment() {
    companion object{
        fun getInstance() = LaunchGame()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.intro_logo,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val fadeIn = AnimationUtils.loadAnimation(this.requireContext(),R.anim.fade_in)
        val fadeIn1 = AnimationUtils.loadAnimation(this.requireContext(),R.anim.fade_in)
        val fadeIn2 = AnimationUtils.loadAnimation(this.requireContext(),R.anim.fade_in)
        logoCardView.visibility = View.VISIBLE
        logoCardView.startAnimation(fadeIn)
        fadeIn.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) {
                Timber.d("Animation repeated")
            }

            override fun onAnimationEnd(animation: Animation?) {
                logoHeader.visibility = View.VISIBLE
                logoBottom.visibility = View.VISIBLE
                logoHeader.startAnimation(fadeIn1)
                logoBottom.startAnimation(fadeIn2)
            }

            override fun onAnimationStart(animation: Animation?) {
                Timber.d("Animation started")
            }
        })
        super.onActivityCreated(savedInstanceState)
    }
}