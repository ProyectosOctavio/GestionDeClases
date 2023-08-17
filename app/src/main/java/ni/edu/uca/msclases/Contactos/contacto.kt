package ni.edu.uca.msclases.Contactos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.Fragment
import ni.edu.uca.msclases.R
import ni.edu.uca.msclases.databinding.FragmentContactoBinding
import java.io.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class contacto : Fragment() {


    var listaD: MutableList<Datos> = ArrayList()
    val listaN: MutableList<String> = ArrayList()

    private var _binding: FragmentContactoBinding? = null
    private val binding: FragmentContactoBinding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentContactoBinding
        .inflate(inflater, container, false)
        .also { _binding = it }
        .root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
    }

    private fun setupListeners() = with(binding) {
        val mainActivity = (requireActivity() as AppCompatActivity)
        iniciar()

    }

    fun GuardarLista(): Boolean {
        val nombre = binding.etNombre.text.toString()
        val corre = binding.etCorreo.text.toString()
        val telefono = binding.etTelefono.text.toString()
        val ubicacion = binding.etUbicacion.text.toString()
        val notas = binding.etNotas.text.toString()
        val lstnombres = activity?.findViewById<Spinner>(R.id.lstNombres)

        var retorno = true


        if (binding.etNombre.text.toString().isEmpty()) {
            binding.etNombre.setError("Este campo no puede estar vacio")
            retorno = false
        }
        if (binding.etCorreo.text.toString().isEmpty()) {
            binding.etCorreo.setError("Este campo no puede estar vacio")
            retorno = false
        }
        if (binding.etTelefono.text.toString().isEmpty()) {
            binding.etTelefono.setError("Este campo no puede estar vacio")
            retorno = false
        }
        if (binding.etUbicacion.text.toString().isEmpty()) {
            binding.etUbicacion.setError("Este campo no puede estar vacio")
            retorno = false
        }
        if (binding.etNotas.text.toString().isEmpty()) {
            binding.etNotas.setError("Este campo esta no puede quear vacio")
            retorno = false
        } else {
            listaD.add(Datos(nombre, corre, telefono.toInt(), ubicacion, notas))
            Toast.makeText(context, "Agregado a la lista", Toast.LENGTH_SHORT).show()

            binding.etNombre.setText("")
            binding.etCorreo.setText("")
            binding.etTelefono.setText("")
            binding.etUbicacion.setText("")
            binding.etNotas.setText("")


            listaN.add(nombre)
            val llenarSpinner: ArrayAdapter<String> = ArrayAdapter<String>(
                requireActivity().applicationContext,
                android.R.layout.simple_spinner_item,
                listaN
            )
            llenarSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            lstnombres?.adapter = llenarSpinner
        }
        return true
    }

    fun GuardarEnArchivo() {
        val ruta = activity?.applicationContext?.filesDir
        val nombreArch = "archivo.tpo"

        try {
            val escribirARCH = FileOutputStream(File(ruta, nombreArch))
            val streamARCH = ObjectOutputStream(escribirARCH)
            streamARCH.writeObject(listaD)
            streamARCH.close()


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun LeeDelArchivo() {

        val lstnombres = activity?.findViewById<Spinner>(R.id.lstNombres)
        val ruta = activity?.applicationContext?.filesDir
        val nombreARCH = "archivo.tpo"

        listaN.clear()
        var llenarSpinner: ArrayAdapter<String> = ArrayAdapter<String>(
            requireActivity().applicationContext,
            android.R.layout.simple_spinner_item,
            listaN
        )
        llenarSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        lstnombres?.adapter = llenarSpinner

        try {
            val lleARCH = FileInputStream(File(ruta, nombreARCH))
            val streamARCH = ObjectInputStream(lleARCH)
            listaD = streamARCH.readObject() as ArrayList<Datos>
            streamARCH.close()

            listaN.clear()

            for (i in listaD.indices) {
                listaN.add(listaD[i].nombre)
            }

            llenarSpinner = ArrayAdapter(
                requireContext(), android.R.layout.simple_spinner_item, listaN
            )

            llenarSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            lstnombres?.adapter = llenarSpinner

        } catch (e: Exception) {
            e.printStackTrace()
        }


    }


    fun MostrarDtos() {
        val lstNombres = activity?.findViewById<Spinner>(R.id.lstNombres)
        val constructor = AlertDialog.Builder(requireContext())
        constructor.setTitle("Contactos")
        constructor.setPositiveButton("Aceptar", null)


        val index = lstNombres?.selectedItemId
        if (index != null) {
            if (index > -1) {
                constructor.setMessage(
                    "\n *Nombre: " + listaD[index!!.toInt()].nombre
                            + "\n-Correo: " + listaD[index!!.toInt()].correo
                            + "\n-Telefono " + listaD[index!!.toInt()].telefono
                            + "\n-Ubicacion " + listaD[index!!.toInt()].ubicacion
                            + "\n-Notas: " + listaD[index!!.toInt()].notas

                )
            } else {
                constructor.setMessage("Debe selecionar uun nombre en la lista ")
            }
        }

        val ventanaMensaje = constructor.create()
        ventanaMensaje.show()

    }

    fun iniciar() {

        binding.btnGuardar.setOnClickListener {
            GuardarLista()
            GuardarEnArchivo()
        }
        binding.btnLeer.setOnClickListener {
            LeeDelArchivo()
        }
        binding.btnMostrar.setOnClickListener {
            MostrarDtos()
        }


    }
}



