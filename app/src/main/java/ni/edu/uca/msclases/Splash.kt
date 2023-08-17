package ni.edu.uca.msclases

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import ni.edu.uca.msclases.databinding.FragmentSplashBinding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class Splash : Fragment() {




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

      val view = inflater.inflate(R.layout.fragment_splash,container,false)
        Handler(Looper.myLooper()!!).postDelayed({
            findNavController().navigate(R.id.menu)

        },2500)

        return view

    }


}