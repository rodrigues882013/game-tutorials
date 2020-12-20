package com.frnx.spaceinvaders.chapterone

import java.awt.Color
import java.awt.Graphics
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.util.*
import javax.swing.JFrame
import javax.swing.JPanel

data class Element (
        var x: Int,
        var y: Int,
        var height: Int,
        val width: Int,
        var speed: Double = 0.0)

class SimpleGame (
        private var screen: JPanel? = null,
        private var isPlaying: Boolean = true,
        private var gameOver: Boolean = false,
        private val FPS: Int = 1000 / 30,
        private val keyControl: Array<Boolean> = Array(4) { false },
        private var shoot: Element? = null,
        private var player: Element? = null,
        private var blocks: Array<Element>? = null,
        private var points: Int = 0,
        private val defaultWidth: Int = 50,
        private val limitLine: Int = 350,
        private val r: Random = Random()
): JFrame(), KeyListener {

    init {
        addKeyListener(this)
        shoot = Element(0, 0 , 0, 1)
        player = Element(0, 0, defaultWidth, defaultWidth)
        player!!.speed = 5.0

        blocks = Array(5) {
            val space = it * defaultWidth + 10 * (it + 1)
            val element = Element(space, 0, defaultWidth, defaultWidth)
            element.speed = 1.0
            element
        }

        screen = object : JPanel() {
            override fun paintComponent(g: Graphics) {
                g.color = Color.WHITE
                g.fillRect(0, 0, screen!!.width, screen!!.height)

                g.color = Color.RED
                g.fillRect(shoot!!.x, shoot!!.y, shoot!!.width, screen!!.height)

                g.color = Color.GREEN
                g.fillRect(player!!.x, player!!.y, player!!.width, player!!.height)

                g.color = Color.BLUE
                blocks!!.forEach { g.fillRect(it.x, it.y, it.width, it.height) }

                g.color = Color.GRAY
                g.drawLine(0, limitLine, screen!!.width, limitLine)

                g.drawString("Points: $points", 0, 10)
            }
        }

        super.getContentPane().add(screen)
        super.setResizable(false)
        setSize(640, 480)
        isVisible = true

        player!!.x = screen!!.width / 2 - player!!.x / 2
        player!!.y = screen!!.height - player!!.height
        shoot!!.height = screen!!.height - player!!.height

    }

    override fun keyTyped(e: KeyEvent?) {
        TODO("Not yet implemented")
    }

    override fun keyPressed(e: KeyEvent?) = setKey(e?.keyCode, true)

    override fun keyReleased(e: KeyEvent?) = setKey(e?.keyCode, false)

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
        if (gameOver)
            return

        if (keyControl[2]) {
            player!!.x -= player!!.speed.toInt()
        } else if (keyControl[3]) {
            player!!.x += player!!.speed.toInt()
        }

        if (player!!.x < 0) {
            player!!.x = screen!!.width - player!!.width
        }

        if (player!!.x + player!!.width > screen!!.width) {
            player!!.x = 0
        }

        shoot!!.y = 0
        shoot!!.x = player!!.x + player!!.width / 2

        blocks!!.forEach {
            if (it.y > limitLine) {
                gameOver = true
                return
            }

            if (collide(it, shoot!!) && it.y > 0) {
                it.y -= (it.speed * 2).toInt()
                shoot!!.y = it.y
            } else {

                when (r.nextInt(10)) {
                    0 -> it.y += (it.speed + 1).toInt()
                    5 -> it.y -= it.speed.toInt()
                    else -> it.y += it.speed.toInt()
                }
            }
        }

        points += blocks!!.size
    }

    private fun collide(a: Element, b: Element): Boolean =
            a.x + a.width >= b.x && a.x <= b.x + b.width

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
            val game = SimpleGame()
            game.start()
        }
    }
}