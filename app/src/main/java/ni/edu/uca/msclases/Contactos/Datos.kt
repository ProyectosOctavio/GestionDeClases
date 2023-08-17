package ni.edu.uca.msclases.Contactos

import java.io.Serializable

// Clase con los datos que voy a almacenar y leer
// Serializable es porque debo poderlo mandar a un flujo (stream) para guardarlo en el archivo
class Datos  // Constructor para asignar datos
internal constructor(
// Métodos para leer datos (getters)
    // Campos: información a guardar
    val nombre: String,
    val correo: String,
    val telefono: Int,
    val ubicacion: String,
    val notas: String,
) : Serializable