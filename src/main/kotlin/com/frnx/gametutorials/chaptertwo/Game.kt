package com.frnx.gametutorials.chaptertwo

import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.awt.image.BufferedImage
import java.util.*
import javax.swing.JFrame
import javax.swing.JPanel


class Game : JFrame(), KeyListener {

    private var screen: JPanel? = null
    private var px: Int = 0
    private var py: Int = 0
    private var isPlaying: Boolean = true
    private var isGameOver: Boolean = false
    private var g2d: Graphics2D? = null
    private var buffer: BufferedImage? = null

    private val keyControl: Array<Boolean> = Array(5) { false }
    private val FPS: Int = 1000 / 30
    private val WINDOW_HEIGHT = 680;
    private val WINDOW_WIDTH = 540;

    private var lives = 3
    private var life: Body = Tank()
    private var tankShot: Body? = null
    private var bossShot: Body? = null
    private var shots: Array<Body?> = Array(3) { Shot(true) }
    private var boss: Invader? = null
    private var tank: Tank? = null
    private val invaders = Array(11) { arrayOfNulls<Invader>(5) }
    private val lineByType: Array<InvaderType> =
            arrayOf(InvaderType.SMALL, InvaderType.MEDIUM, InvaderType.MEDIUM, InvaderType.BIG, InvaderType.BIG)

    private val baseLine = 60
    private val spaceBetween = 15
    private val destroyed = 0
    private var direction = 0
    private var enemiesTotal = 0
    private var waitCounter = 0
    private var newLine = false
    private var moveEnemy = false
    private val counter = 0
    private val points = 0
    private val level = 1
    private val rand: Random = Random()

    init {
        addKeyListener(this)

        screen = object : JPanel() {
            override fun paintComponent(g: Graphics) {
                g.drawImage(buffer, 0, 0, null)
            }
        }
        buffer = BufferedImage(WINDOW_WIDTH, WINDOW_HEIGHT, BufferedImage.TYPE_INT_RGB)
        g2d = buffer?.createGraphics();

        super.getContentPane().add(screen)
        super.setResizable(false)
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT)
        isVisible = true
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
            KeyEvent.VK_SPACE -> keyControl[4] = isPressed
        }
    }

    fun load() {
        tank = Tank()
        tank!!.speed = 3
        tank!!.active = true
        tank!!.x = screen!!.width / 2 - tank!!.width / 2
        tank!!.y = screen!!.height - tank!!.height - baseLine

        tankShot = Shot()
        tankShot!!.speed = -15

        boss = Invader(InvaderType.BOSS)

        bossShot = Shot(true)
        bossShot!!.speed = 20
        bossShot!!.height = 15

        for (i in invaders.indices) {
            for (j in invaders[i].indices) {
                val e = Invader(lineByType[j])
                e.active = true
                e.x = (i * e.width + (i + 1) * spaceBetween)
                e.y = (j * e.height + j * spaceBetween + baseLine)
                invaders[i][j] = e
            }
        }

        direction = 1
        enemiesTotal = invaders.size * invaders[0].size
        waitCounter = enemiesTotal / level
    }

    fun start() {

    }
}