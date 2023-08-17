package ni.edu.uca.msclases.Apuntes
import java.io.Serializable

// Clase con los datos que voy a almacenar y leer
// Serializable es porque debo poderlo mandar a un flujo (stream) para guardarlo en el archivo
class Apuntes  // Constructor para asignar datos
internal constructor(
// Métodos para leer datos (getters)
    // Campos: información a guardar
    val Titulo: String,
    val Descripcion: String,

) : Serializable