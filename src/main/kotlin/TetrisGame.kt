package org.ltaddey

import javafx.animation.AnimationTimer
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.stage.Stage

class TetrisGame : Application() {

    private val boardWidth = 10
    private val boardHeight = 20
    private val cellSize = 30
    private lateinit var board: Array<IntArray>
    private lateinit var currentPiece: Piece
    private lateinit var canvas: Canvas
    override fun start(stage: Stage) {
        board = Array(boardHeight) { IntArray(boardWidth) }
        currentPiece = Piece.randomPiece()

        canvas = Canvas(boardWidth * cellSize.toDouble(), boardHeight * cellSize.toDouble())
        val root = StackPane(canvas)
        val scene = Scene(root)

        stage.scene = scene
        stage.title = "Tetris"
        stage.show()

        scene.setOnKeyPressed { event -> handleKeyPress(event) }

        object : AnimationTimer() {
            override fun handle(now: Long){
                update()
                draw()
            }
        }.start()
    }
    private fun update(){
        if(canMovePiece(0,1)){
            currentPiece.y++
        }else{
            placePiece()
            clearLines()
            currentPiece = Piece.randomPiece()
            if(canMovePiece(0,0)){
                board = Array(boardHeight) { IntArray(boardWidth) }
            }
        }
    }
    private fun draw(){
        val gc = canvas.graphicsContext2D
        gc.clearRect(0.0, 0.0, canvas.width, canvas.height)
        for (y in 0 until boardHeight){
            for (x in 0 until boardWidth){
                if(board[y][x] != 0){
                    gc.fill = Color.GRAY
                    gc.fillRect(x * cellSize.toDouble(), y * cellSize.toDouble(), cellSize.toDouble(), cellSize.toDouble())
                }
            }
            gc.fill = Color.BLUE
            for(block in currentPiece.shape){
                gc.fillRect((currentPiece.x + block.x) * cellSize.toDouble(),
                    (currentPiece.y + block.y) * cellSize.toDouble(),
                    cellSize.toDouble(), cellSize.toDouble())
            }
        }
    }
    private fun handleKeyPress(event: KeyEvent){
        when(event.code){
            KeyCode.LEFT -> if(canMovePiece(-1,0)) currentPiece.x--
            KeyCode.RIGHT -> if(canMovePiece(1,0)) currentPiece.x++
            KeyCode.DOWN -> if(canMovePiece(0,1)) currentPiece.y++
            KeyCode.UP -> rotatePiece()
            else -> {}
        }
    }
    private fun canMovePiece(dx: Int , dy:Int) : Boolean{
        for(block in currentPiece.shape){
            val newX = currentPiece.x + block.x + dx
            val newY = currentPiece.y + block.y + dy
            if(newX < 0 || newX >= boardWidth || newY >= boardHeight || board[newY][newX] != 0){
                return false
            }
        }
        return true;
    }
    private fun placePiece(){
        for(block in currentPiece.shape){
            val x = currentPiece.x + block.x
            val y = currentPiece.y + block.y
            if(y >= 0){
                board[y][x] = 1
            }
        }
    }
    private fun clearLines(){
        var linesCleared = 0
        for(y in boardHeight - 1 downTo 0){
            if(board[y].all { it != 0 }){
                for(y2 in y downTo 1){
                    board[y2] = board[y2 - 1]
                }
                board[0] = IntArray(boardWidth)
                linesCleared++
            }
        }
    }
    private fun rotatePiece(){
        val rotated = currentPiece.rotated()
        if(canPlacePiece(rotated)){
            currentPiece = rotated
        }
    }
    private fun  canPlacePiece(piece: Piece) : Boolean{
        for(block in piece.shape){
            val x = piece.x + block.x
            val y = piece.y + block.y
            if(x < 0 || x >= boardWidth || y >= boardHeight || board[y][x] != 0){
                return false
            }
        }
        return true;
    }
    companion object{
        @JvmStatic
        fun main(args: Array<String>){
            launch(TetrisGame::class.java)
        }
    }
}