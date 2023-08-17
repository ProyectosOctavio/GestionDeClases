package ni.edu.uca.msclases.Gestor

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.DocumentsContract
import androidx.appcompat.widget.Toolbar;

import android.view.View

import androidx.fragment.app.commit
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ni.edu.uca.msclases.R
import ni.edu.uca.msclases.databinding.ActivityMainBinding

class MainActivityRecursos : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tool = findViewById<Toolbar>(R.id.tollbar)
        setSupportActionBar(tool)

        val openDirectoryButton = findViewById<FloatingActionButton>(R.id.fab_open_directory)
        openDirectoryButton.setOnClickListener {
            openDirectory()

        }
        val createDirectoryButton = findViewById<FloatingActionButton>(R.id.fab_Create)
        createDirectoryButton.setOnClickListener {
            createFile(Uri.parse(""))
        }



        supportFragmentManager.addOnBackStackChangedListener {
            val directoryOpen = supportFragmentManager.backStackEntryCount > 0
            supportActionBar?.let { actionBar ->
                actionBar.setDisplayHomeAsUpEnabled(directoryOpen)
                actionBar.setDisplayShowHomeEnabled(directoryOpen)
            }

            if (directoryOpen) {
                openDirectoryButton.visibility = View.GONE
            } else {
                openDirectoryButton.visibility = View.VISIBLE
            }
        }


        supportFragmentManager.addOnBackStackChangedListener {
            val directoryCreate = supportFragmentManager.backStackEntryCount > 0
            supportActionBar?.let { actionBar ->
                actionBar.setDisplayHomeAsUpEnabled(directoryCreate)
                actionBar.setDisplayHomeAsUpEnabled(directoryCreate)

            }
            if (directoryCreate) {
                createDirectoryButton.visibility = View.GONE
            } else {
                createDirectoryButton.visibility = View.VISIBLE
            }
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == OPEN_DIRECTORY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val directoryUri = data?.data ?: return

            contentResolver.takePersistableUriPermission(
                directoryUri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            showDirectoryContents(directoryUri)
        }
    }

    fun showDirectoryContents(directoryUri: Uri) {
        supportFragmentManager.commit {
            val directoryTag = directoryUri.toString()
            val directoryFragment = DirectoryFragment.newInstance(directoryUri)
            replace(R.id.fragment_container, directoryFragment, directoryTag)
            addToBackStack(directoryTag)
        }
    }


    private fun openDirectory() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
        startActivityForResult(intent, OPEN_DIRECTORY_REQUEST_CODE)
    }

    val CREATE_FILE = 1

    private fun createFile(pickerInitialUri: Uri) {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/docx"
            putExtra(Intent.EXTRA_TITLE, "invoice.docx")

            // Optionally, specify a URI for the directory that should be opened in
            // the system file picker before your app creates the document.
            putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
        }
        startActivityForResult(intent, CREATE_FILE)
    }


}

private const val OPEN_DIRECTORY_REQUEST_CODE = 0xf11e

