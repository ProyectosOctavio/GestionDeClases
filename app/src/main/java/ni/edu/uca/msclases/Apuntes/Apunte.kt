package ni.edu.uca.msclases.Apuntes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import ni.edu.uca.msclases.Contactos.Datos
import ni.edu.uca.msclases.R

import ni.edu.uca.msclases.databinding.FragmentApunteBinding
import java.io.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class Apunte : Fragment() {

    var listaA:MutableList<Apuntes> =ArrayList()
    var listaNA:MutableList<String> =ArrayList()

    private var _binding:FragmentApunteBinding?=null
    private val binding:FragmentApunteBinding
    get() = _binding!!





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =FragmentApunteBinding
        .inflate(inflater,container,false)
        .also { _binding=it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
    }


    private fun setupListener()= with(binding){
        val mainActivity =(requireActivity()as AppCompatActivity)
        iniciar()
    }



    fun GuardarListaApunte():Boolean {
        val Titulo = binding.etTitulo2.text.toString()
        val descripcion = binding.etDescripcion2.text.toString()
        val lstnombre = activity?.findViewById<Spinner>(R.id.lstNombres1)

        var retorno=true
        if (binding.etTitulo2.text.toString().isEmpty()) {
            binding.etTitulo2.setError("Este campo no puede quedar vacio")
            retorno= false

        } else if (binding.etDescripcion2.text.toString().isEmpty()) {
            binding.etDescripcion2.setError("Este campo no puede quedar vacio")
            return false

        }else{
            listaA.add(Apuntes(Titulo, descripcion))
            Toast.makeText(context, "Agregado a la lista", Toast.LENGTH_SHORT).show()
            listaNA.add(Titulo)

            binding.etTitulo2.setText("")
            binding.etDescripcion2.setText("")
        }
        val llenarSpinner: ArrayAdapter<String> = ArrayAdapter<String>(
            requireActivity().applicationContext,
            android.R.layout.simple_spinner_item,
            listaNA
        )
        llenarSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        lstnombre?.adapter = llenarSpinner

        return true
    }

    fun GuardarEnArchivoApunte() {
        val ruta = activity?.applicationContext?.filesDir
        val nombreArch = "archivoP.tpo"

        try {
            val escribirARCH = FileOutputStream(File(ruta, nombreArch))
            val streamARCH = ObjectOutputStream(escribirARCH)
            streamARCH.writeObject(listaA)
            streamARCH.close()


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun LeeDelArchivoApuntes() {

        val lstnombres = activity?.findViewById<Spinner>(R.id.lstNombres1)
        val ruta = activity?.applicationContext?.filesDir
        val nombreARCH = "archivoP.tpo"

        listaA.clear()
        var llenarSpinner: ArrayAdapter<String> = ArrayAdapter<String>(
            requireActivity().applicationContext,
            android.R.layout.simple_spinner_item,
            listaNA
        )
        llenarSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        lstnombres?.adapter = llenarSpinner

        try {
            val lleARCH = FileInputStream(File(ruta, nombreARCH))
            val streamARCH = ObjectInputStream(lleARCH)
            listaA= streamARCH.readObject() as ArrayList<Apuntes>
            streamARCH.close()

            listaNA.clear()

            for (i in listaA.indices) {
                listaNA.add(listaA[i].Titulo)
            }

            llenarSpinner = ArrayAdapter(
                requireContext(), android.R.layout.simple_spinner_item, listaNA
            )

            llenarSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            lstnombres?.adapter = llenarSpinner

        } catch (e: Exception) {
            e.printStackTrace()
        }



    }

    fun MostrarDtosApuntes() {
        val lstNombres = activity?.findViewById<Spinner>(R.id.lstNombres1)
        val constructor = AlertDialog.Builder(requireContext())
        constructor.setTitle("Apuntes")
        constructor.setPositiveButton("Aceptar", null)


        val index = lstNombres?.selectedItemId
        if (index != null) {
            if (index > -1) {
                constructor.setMessage(
                    "\n *Titulo: " + listaA[index!!.toInt()].Titulo
                            + "\n-Descripcion: " + listaA[index!!.toInt()].Descripcion


                )
            } else {
                constructor.setMessage("Debe selecionar uun nombre en la lista ")
            }
        }

        val ventanaMensaje = constructor.create()
        ventanaMensaje.show()

    }


    fun iniciar() {

      binding.btnGuardar2.setOnClickListener {
          GuardarListaApunte()
          GuardarEnArchivoApunte()
      }
        binding.btnLeer2.setOnClickListener {
            LeeDelArchivoApuntes()
        }
        binding.btnMostrar2.setOnClickListener {
            MostrarDtosApuntes()
        }



    }


}


