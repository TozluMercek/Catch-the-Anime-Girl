package com.example.myapplication78
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.myapplication78.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var score = 0
    var imageArray = ArrayList<ImageView>()
    var runnable = Runnable { }
    var handler = Handler(Looper.getMainLooper())

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        imageArray.add(binding.imageView16)
        imageArray.add(binding.imageView19)
        imageArray.add(binding.imageView20)
        imageArray.add(binding.imageView21)
        imageArray.add(binding.imageView22)
        imageArray.add(binding.imageView23)
        imageArray.add(binding.imageView24)
        imageArray.add(binding.imageView18)
        imageArray.add(binding.imageView17)

        hideImages()

        object : CountDownTimer(50500, 1000) {
            override fun onTick(p0: Long) {
                binding.timeText.text = "Time:${p0 / 1000}"
            }

            override fun onFinish() {
                binding.timeText.text = "Time:0"
                handler.removeCallbacks(runnable)
                for (image in imageArray) {
                    image.visibility = View.INVISIBLE
                }
                val alertDialog = AlertDialog.Builder(this@MainActivity)
                alertDialog.setTitle("Game Over")
                alertDialog.setMessage("Restart the Game?")
                alertDialog.setPositiveButton(
                    "Yes",
                    DialogInterface.OnClickListener { dialogInterface, i ->
                        val intent = intent
                        finish()
                        startActivity(intent)
                    })
                alertDialog.setNegativeButton("No", DialogInterface.OnClickListener
                { dialogInterface, i ->
                    Toast.makeText(
                        this@MainActivity,
                        "Game Over!",
                        Toast.LENGTH_LONG
                    ).show()
                })
                alertDialog.show()

            }
        }.start()

    }

    fun hideImages() {
        runnable = object : Runnable {
            override fun run() {
                for (image in imageArray) {
                    image.visibility = View.INVISIBLE
                }
                val randomIndex = Random.nextInt(9)
                imageArray[randomIndex].visibility = View.VISIBLE
                //View.GONE, View.INVISIBLE vs View.VISIBLE
                handler.postDelayed(runnable, 500)
            }

        }
        handler.post(runnable)
    }

    fun incrementScore(view: View) {
        score++
        binding.scoreText.text = "Score:${score}"

    }
}