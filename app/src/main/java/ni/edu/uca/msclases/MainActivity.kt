package ni.edu.uca.msclases

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ni.edu.uca.msclases.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Proyecto terminado
    }

}


