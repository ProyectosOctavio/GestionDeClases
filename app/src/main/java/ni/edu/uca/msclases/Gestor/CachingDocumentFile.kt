package ni.edu.uca.msclases.Gestor

import androidx.documentfile.provider.DocumentFile

data class CachingDocumentFile(private val documentFile: DocumentFile) {
    val name: String? by lazy { documentFile.name }
    val type: String? by lazy { documentFile.type }

    val isDirectory: Boolean by lazy { documentFile.isDirectory }

    val uri get() = documentFile.uri

    fun rename(newName: String): CachingDocumentFile {
        documentFile.renameTo(newName)
        return CachingDocumentFile(documentFile)
    }
}

fun Array<DocumentFile>.toCachingList(): List<CachingDocumentFile> {
    val list = mutableListOf<CachingDocumentFile>()
    for (document in this) {
        list += CachingDocumentFile(document)
    }
    return list
}