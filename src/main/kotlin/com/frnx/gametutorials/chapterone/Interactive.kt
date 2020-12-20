package com.frnx.chapterone

import java.awt.Color
import java.awt.Graphics
import java.awt.Point
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import javax.swing.JFrame
import javax.swing.JPanel

class Interactive(
        private var screen: JPanel? = null,
        private var px: Int = 0,
        private var py: Int = 0,
        private var isPlaying: Boolean = true,
        private val FPS: Int = 1000 / 30,
        private val keyControl: Array<Boolean> = Array(4) { false },
        private var mouseClick: Point? = Point()
) : JFrame(), KeyListener, MouseListener {

    init {
        super.addKeyListener(this)
        //super.addMouseListener(this)

        screen = object : JPanel() {
            override fun paintComponent(g: Graphics) {
                g.color = Color.WHITE
                g.fillRect(0, 0, screen!!.width, screen!!.height)
                val x: Int = screen!!.width / 2 - 20 + px
                val y: Int = screen!!.height / 2 - 20 + py
                g.color = Color.BLUE
                g.fillRect(x, y, 40, 40)
                g.drawString("Agora	eu	estou	em	" + x + "x" + y, 5, 10)
            }
        }
        super.getContentPane().add(screen)
        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(640, 480)
        isVisible = true
    }

    override fun keyTyped(e: KeyEvent?) {
        TODO("Not yet implemented")
    }

    override fun keyPressed(e: KeyEvent?) = setKey(e?.keyCode, true)

    override fun keyReleased(e: KeyEvent?) = setKey(e?.keyCode, false)

    override fun mouseClicked(e: MouseEvent?) {
        mouseClick = e?.point;
    }

    override fun mousePressed(e: MouseEvent?) {
        TODO("Not yet implemented")
    }

    override fun mouseReleased(e: MouseEvent?) {
        TODO("Not yet implemented")
    }

    override fun mouseEntered(e: MouseEvent?) {
        TODO("Not yet implemented")
    }

    override fun mouseExited(e: MouseEvent?) {
        TODO("Not yet implemented")
    }

    private fun setKey(key: Int?, isPressed: Boolean) {
        when(key) {
            KeyEvent.VK_ESCAPE -> {
                isPlaying = false
                dispose()
            }
            KeyEvent.VK_UP -> keyControl[0] = isPressed
            KeyEvent.VK_DOWN -> keyControl[1] = isPressed
            KeyEvent.VK_LEFT -> keyControl[2] = isPressed
            KeyEvent.VK_RIGHT -> keyControl[3] = isPressed

        }
    }

    private fun updateGame() {
        if (keyControl[0]) py--
        else if (keyControl[1]) py++

        if (keyControl[2]) px--
        else if (keyControl[3]) px++

//        px = mouseClick!!.x;
//        py = mouseClick!!.y;


    }

    fun start() {
        var nextAnimation: Long = 0

        while (isPlaying) {
            if (System.currentTimeMillis() >= nextAnimation) {
                updateGame()
                screen!!.repaint()
                nextAnimation = System.currentTimeMillis() + FPS
            }
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val interactive = Interactive()
            interactive.start()
        }
    }

}