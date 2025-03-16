package iticbcn.m7.provavalobike

data class Bici(
    val id: Int,
    val matricula: String,
    val puntuacio: Int,
    val comentario: String,
    val data: String,
    val idUsuari: Int
)