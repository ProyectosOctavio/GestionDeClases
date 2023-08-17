package ni.edu.uca.msclases.Gestor

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import ni.edu.uca.msclases.databinding.FragmentRecursosBinding
import ni.edu.uca.msclases.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [recursos.newInstance] factory method to
 * create an instance of this fragment.
 */
class recursos() : Fragment() {


    private var _binding: FragmentRecursosBinding? = null
    private val binding: FragmentRecursosBinding
        get() = _binding!!


    private companion object {

        const val CREATE_FILE = 1
        const val OPEN_DIRECTORY_REQUEST_CODE = 0xf11e
        const val EMPTY_URI_STRING = ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View =
        FragmentRecursosBinding
            .inflate(inflater, container, false)
            .also { _binding = it }
            .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() = with(binding) {
        val mainActivity = (requireActivity() as AppCompatActivity)
        fabCreate.setOnClickListener {
            launchCreateFileIntent(Uri.parse(EMPTY_URI_STRING))
        }
        fabOpenDirectory.setOnClickListener{
            val openDocumentTreeIntent=Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
            mainActivity.startActivityForResult(openDocumentTreeIntent, OPEN_DIRECTORY_REQUEST_CODE)
        }

    }
     fun onSupportNavigateUp(): Boolean {
        activity?.supportFragmentManager?.popBackStack()
        return false
    }

    private fun openDirectory(){
        val intent =Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
        startActivityForResult(intent, OPEN_DIRECTORY_REQUEST_CODE)
    }

    private fun launchCreateFileIntent(pickerInitialUri: Uri) {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/docx"
            putExtra(Intent.EXTRA_TITLE, "invoice.docx")


            putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)

        }
        startActivityForResult(intent, CREATE_FILE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == OPEN_DIRECTORY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val directoryUri = data?.data ?: return


            activity?.contentResolver?.takePersistableUriPermission(
                directoryUri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            showDirectoryContents(directoryUri)
        }
    }

    fun showDirectoryContents(directoryUri: Uri) {
       val manager= requireActivity().supportFragmentManager.commit {
            val directoryTag = directoryUri.toString()
            val directoryFragment = DirectoryFragment.newInstance(directoryUri)
            replace(R.id.fragment_container, directoryFragment, directoryTag)
            addToBackStack(directoryTag)
        }
    }


}