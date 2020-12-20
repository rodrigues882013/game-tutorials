package com.frnx.chapterone

import java.awt.Color
import java.awt.Graphics
import javax.swing.JFrame
import javax.swing.JPanel

class ScreenAnimation(
        private var screen: JPanel? = null,
        private val fps: Int = 1000 / 50,
        private var counter: Int = 0,
        private var anime: Boolean = true) : JFrame() {

    init {
        screen = object : JPanel() {
            override fun paintComponent(g: Graphics) {
                g.color = Color.WHITE
                g.fillRect(0, 0, screen!!.width, screen!!.height)

                g.color = Color.BLUE
                g.drawLine(0, 240 + counter, 640, 240 + counter)
                g.drawRect(10, 25 + counter, 20, 20)
                g.drawOval(30 + counter, 20, 40, 30)

                g.color = Color.YELLOW
                g.drawLine(320 - counter, 0, 320 - counter, 480)
                g.fillRect(110, 125, 120 - counter, 120 - counter)
                g.fillOval(230, 220, 240 + counter, 230)

                g.color = Color.RED
                g.drawString("Eu	seria	um	ótimo	Score!	" + counter, 5, 10)

                contentPane.add(screen)
                defaultCloseOperation = EXIT_ON_CLOSE
                setSize(640, 480)
                isVisible = true
                screen!!.repaint()
            }
        }
        super.getContentPane().add(screen)
        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(640, 480)
        isVisible = true
    }

    fun startAnimation() {
        var nextAnimation: Long = 0

        while (anime) {
            if (System.currentTimeMillis() >= nextAnimation) {
                counter += 1
                screen?.repaint()

                nextAnimation = System.currentTimeMillis() + fps

                if (counter == 100) {
                    anime = false
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val anima = ScreenAnimation()
            anima.startAnimation()
        }
    }

}