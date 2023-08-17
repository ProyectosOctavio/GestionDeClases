package ni.edu.uca.msclases

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import ni.edu.uca.msclases.databinding.FragmentMenuBinding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Menu : Fragment() {

    private lateinit var fbinding: FragmentMenuBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fbinding = FragmentMenuBinding.inflate(layoutInflater)
        iniciar()
        return fbinding.root

    }


    private fun iniciar() {
        fbinding.btnHorario.setOnClickListener {
            Navigation.findNavController(fbinding.root).navigate(R.id.horario)
        }
        fbinding.btnApuntes.setOnClickListener {
            Navigation.findNavController(fbinding.root).navigate(R.id.apunte)

        }
        fbinding.btnContactos.setOnClickListener {
            Navigation.findNavController(fbinding.root).navigate(R.id.contacto)
        }
        fbinding.btnRecursos.setOnClickListener {
            Navigation.findNavController(fbinding.root).navigate(R.id.recursos)
        }

    }

}