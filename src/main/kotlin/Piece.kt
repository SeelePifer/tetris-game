package org.ltaddey

data class Piece(val shape: List<Point>, var x: Int = 0, var y: Int = 0){
    fun rotated(): Piece{
        val rotatedShape = shape.map { Point(-it.y, it.x) }
        return copy(shape = rotatedShape)
    }
    companion object{
        private val pieces = listOf(
            listOf(Point(0, 0), Point(1, 0), Point(0, 1), Point(1, 1)), // O
            listOf(Point(0, 0), Point(0, 1), Point(0, 2), Point(0, 3)), // I
            listOf(Point(0, 0), Point(1, 0), Point(2, 0), Point(2, 1)), // L
            listOf(Point(0, 0), Point(1, 0), Point(2, 0), Point(0, 1)), // J
            listOf(Point(0, 0), Point(1, 0), Point(1, 1), Point(2, 1)), // S
            listOf(Point(0, 1), Point(1, 1), Point(1, 0), Point(2, 0)), // Z
            listOf(Point(0, 0), Point(1, 0), Point(2, 0), Point(1, 1))  // T
        )
        fun randomPiece(): Piece{
            return Piece(pieces.random(),5,0)
        }
    }
}
