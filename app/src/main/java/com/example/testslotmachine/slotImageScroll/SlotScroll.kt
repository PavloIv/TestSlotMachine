package com.example.testslotmachine.slotImageScroll

import android.animation.Animator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.testslotmachine.R
import com.example.testslotmachine.databinding.SlotImageScrollBinding

class SlotScroll: FrameLayout {

    private lateinit var binding: SlotImageScrollBinding
    internal lateinit var eventEnd: EventEnd
    internal var lastResult = 0
    internal var oldValue = 0

    companion object {
        private const val ANIMATION_DURATION = 150
    }

    val value: Int
        get() = Integer.parseInt(binding.nextImage.tag.toString())

    fun setEventEnd(eventEnd: EventEnd){
        this.eventEnd = eventEnd
    }

    constructor(context: Context) : super(context){
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs){
        init(context)
    }

    private fun init(context: Context){
        val inflater = LayoutInflater.from(context)

        binding = SlotImageScrollBinding.inflate(inflater,this,true)

        binding.root

        binding.nextImage.translationY = height.toFloat()
    }

    fun setRandomValue(image:Int, numRoll:Int){
        binding.currentImage.animate()
            .translationY(-height.toFloat())
            .setDuration(ANIMATION_DURATION.toLong()).start()

        binding.nextImage.translationY = binding.nextImage.height.toFloat()

        binding.nextImage.animate()
            .translationY(0f).setDuration(ANIMATION_DURATION.toLong())
            .setListener(object: Animator.AnimatorListener{
                override fun onAnimationEnd(animation: Animator) {
                    setImage(binding.currentImage, oldValue%6)
                    binding.currentImage.translationY = 0f
                    if(oldValue != numRoll) {
                        setRandomValue(image,numRoll)
                        oldValue++
                    }
                    else {
                        lastResult = 0
                        oldValue = 0
                        setImage(binding.nextImage, image)
                        eventEnd.eventEnd(image%6, numRoll)

                    }

                }

                override fun onAnimationRepeat(animation: Animator) {
                }
                override fun onAnimationCancel(animation: Animator) {
                }
                override fun onAnimationStart(animation: Animator) {
                }

            }).start()
    }

    private fun setImage(currentImage: ImageView?, value: Int){
        when (value) {
            Utils.bar -> currentImage!!.setImageResource(R.drawable.bar)
            Utils.lemon -> currentImage!!.setImageResource(R.drawable.lemon)
            Utils.orange -> currentImage!!.setImageResource(R.drawable.orange)
            Utils.seven -> currentImage!!.setImageResource(R.drawable.seven)
            Utils.triple -> currentImage!!.setImageResource(R.drawable.triple_seven)
            Utils.watermelon -> currentImage!!.setImageResource(R.drawable.watermelon)
        }

        currentImage!!.tag = value
        lastResult = value
    }
}