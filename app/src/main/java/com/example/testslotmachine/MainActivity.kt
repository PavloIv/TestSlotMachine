package com.example.testslotmachine

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testslotmachine.databinding.ActivityMainBinding
import com.example.testslotmachine.slotImageScroll.EventEnd
import kotlin.random.Random

class MainActivity : AppCompatActivity(),EventEnd {

    private var score = 1000
    private var bet = 50

    private lateinit var binding: ActivityMainBinding

    private var countDown = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        binding.scoreTv.text = score.toString()
        binding.bet.text = bet.toString()

        binding.image1.setEventEnd(this@MainActivity)
        binding.image2.setEventEnd(this@MainActivity)
        binding.image3.setEventEnd(this@MainActivity)

        binding.buttPlus.setOnClickListener {
            if (score > bet) bet += 50
            binding.bet.text = bet.toString()
        }

        binding.buttMinus.setOnClickListener {
            if (bet > 50 ) {
                bet -= 50
                binding.bet.text = bet.toString()
            }
        }

        binding.buttonSpin.setOnClickListener{
            binding.buttonSpin.isClickable = false
            if(score >= 50){
                binding.apply {
                    image1.setRandomValue(Random.nextInt(6), Random.nextInt(15-5+1)+5)
                    image2.setRandomValue(Random.nextInt(6), Random.nextInt(15-5+1)+5)
                    image3.setRandomValue(Random.nextInt(6), Random.nextInt(15-5+1)+5) }

                score -= bet
                binding.scoreTv.text = score.toString()
            }else{
                Toast.makeText(this,"You don't have enough money", Toast.LENGTH_SHORT).show()
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
                score += bet * 6
                binding.scoreTv.text = score.toString()
                binding.buttonSpin.isClickable = true
            }
            else if(binding.image1.value == binding.image2.value || binding.image2.value == binding.image3.value || binding.image1.value == binding.image3.value){
                Toast.makeText(this,"You did good.", Toast.LENGTH_SHORT).show()
                score += bet * 2
                binding.scoreTv.text = score.toString()
                binding.buttonSpin.isClickable = true
            }
            else{
                Toast.makeText(this,"You lost. Better luck next time.", Toast.LENGTH_SHORT).show()
                score +=0
                binding.scoreTv.text = score.toString()
                if (bet > score){
                    bet = score
                    binding.bet.text = bet.toString()
                }
                binding.buttonSpin.isClickable = true
            }
        }
    }
}