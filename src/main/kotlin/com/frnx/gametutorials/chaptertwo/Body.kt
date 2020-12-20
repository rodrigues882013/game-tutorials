package com.frnx.gametutorials.chaptertwo

import java.awt.Color
import java.awt.Graphics2D

open class Body {

    var x: Int = 0
    var y: Int = 0
    var height: Int = 0
    var width: Int = 0
    var speed: Int = 0
    var active: Boolean = true
    var color: Color? = null

    open fun update() {

    }

    open fun drawing(g: Graphics2D) = g.drawRect(x, y, width, height)

    fun increasePositionX(increment: Int) {
        x += increment
    }

    fun increasePositionY(increment: Int) {
        y += increment
    }
}

class Tank : Body() {
    var tankGun: Int = 8
    var hatch: Int = 10

    init {
        width = 30
        height = 15
    }

    override fun update() {
    }

    override fun drawing(g: Graphics2D) {
        g.color = Color.GREEN
        g.fillRect(x + width / 2 - tankGun / 2, y - tankGun, tankGun, tankGun)

        g.fillRect(x, y, width, height)

        g.color = Color.YELLOW
        g.fillOval(x + width / 2 - hatch / 2, y + height / 2 - hatch / 2, hatch, hatch)
    }
}

enum class InvaderType {
    SMALL, MEDIUM, BIG, BOSS
}

class Invader(private val type: InvaderType? = null) : Body() {
    private var open: Boolean = false

    init {
        width = 20
        height = 20
    }

    override fun update() {
        open = !open
    }

    override fun drawing(g: Graphics2D) {
        if (!active) return

        var localWidth = width

        when(type) {
            InvaderType.SMALL -> {
                localWidth -= 2
                g.color = Color.BLUE

                if (open) {
                    g.fillOval(x, y, localWidth, width)

                    g.fillRect(x - 5, y - 5, 5, 5)
                    g.fillRect(x + localWidth, y - 5, 5, 5)

                    g.fillRect(x - 5, y + width, 5, 5)
                    g.fillRect(x + localWidth, y + localWidth, 5, 5)

                } else {
                    g.fillRect(x, y, localWidth, height)
                }

            }
            InvaderType.MEDIUM -> {
                g.color = Color.ORANGE

                if (open) {
                    g.drawRect(x, y, localWidth, height)

                } else {
                    g.fillRect(x, y, localWidth, height)
                }

            }
            InvaderType.BIG -> {
                localWidth += 4

                if (open) {
                    g.color = Color.DARK_GRAY
                    g.fillRect(x, y, height, localWidth)

                } else {
                    g.color = Color.GRAY;
                    g.fillRect(x, y, localWidth, height)
                }

            }
            else -> {
                localWidth += 10
                g.color = Color.RED
                g.fillOval(x, y, localWidth, height)

                if (open) {
                    g.color = Color.WHITE;

                    g.fillRect(x + 7, y + height / 2 - 2, 4, 4)
                    g.fillRect(x + 13, y + height / 2 - 2, 4, 4)
                    g.fillRect(x + 19, y + height / 2 - 2, 4, 4)
                }
            }
        }

    }

    fun getPrize(): Int = when(type) {
        InvaderType.SMALL -> 300
        InvaderType.MEDIUM -> 200
        InvaderType.BIG -> 100
        else -> 1000
    }
}

class Shot(private var enemy: Boolean = false) : Body() {
    init {
        width = 5
        height = 5
    }

    override fun update() {
    }

    override fun drawing(g: Graphics2D) {

        if (!active) return

        g.color = if (enemy) Color.RED else Color.WHITE
        g.fillRect(x, y, width, height)

    }
}
