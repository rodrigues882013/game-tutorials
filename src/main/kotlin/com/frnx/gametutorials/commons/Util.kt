package com.frnx.chapterone.commons

import com.frnx.chapterone.Element

class Util {

    companion object {
        fun wasThereCollision (bodyA: Element, bodyB: Element): Boolean {
            if (!bodyA.active || !bodyB.active)
                return false

            val xPositionBodyA = bodyA.x + bodyA.width
            val xPositionBodyB = bodyB.x + bodyB.width

            val yPositionBodyA = bodyA.y + bodyA.height
            val yPositionBodyB = bodyB.y + bodyB.height

            return (xPositionBodyA > bodyB.x && bodyA.x < xPositionBodyB
                    && yPositionBodyA > bodyB.y && bodyA.y < yPositionBodyB)
        }
    }
}