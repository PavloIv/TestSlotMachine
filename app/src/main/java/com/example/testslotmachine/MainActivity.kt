package com.example.testslotmachine

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testslotmachine.databinding.ActivityMainBinding
import com.example.testslotmachine.slotImageScroll.EventEnd
import com.example.testslotmachine.slotImageScroll.Utils
import kotlin.random.Random

class MainActivity : AppCompatActivity(),EventEnd {

    private lateinit var binding: ActivityMainBinding

    private var countDown = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        binding.image1.setEventEnd(this@MainActivity)
        binding.image2.setEventEnd(this@MainActivity)
        binding.image3.setEventEnd(this@MainActivity)

        binding.button.setOnClickListener{
            if(Utils.score >= 50){
                binding.apply {
                    image1.setRandomValue(Random.nextInt(6), Random.nextInt(15-5+1)+5)
                    image2.setRandomValue(Random.nextInt(6), Random.nextInt(15-5+1)+5)
                    image3.setRandomValue(Random.nextInt(6), Random.nextInt(15-5+1)+5)  }

                Utils.score -= 50
                binding.scoreTv.text = Utils.score.toString()
            }else{
                Toast.makeText(this,"You dont have enough money", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun eventEnd(result: Int, count: Int) {
        if(countDown < 2){
            countDown++
        }
        else{
            countDown = 0

            if(binding.image1.value == binding.image2.value && binding.image2.value == binding.image3.value){
                Toast.makeText(this,"YOU WON!!!!", Toast.LENGTH_SHORT).show()
                Utils.score +=300
                binding.scoreTv.text = Utils.score.toString()
            }
            else if(binding.image1.value == binding.image2.value || binding.image2.value == binding.image3.value || binding.image1.value == binding.image3.value){
                Toast.makeText(this,"You did good.", Toast.LENGTH_SHORT).show()
                Utils.score +=100
                binding.scoreTv.text = Utils.score.toString()
            }
            else{
                Toast.makeText(this,"You lost. Better luck next time.", Toast.LENGTH_SHORT).show()
                Utils.score +=0
                binding.scoreTv.text = Utils.score.toString()
            }
        }
    }
}