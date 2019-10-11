package com.saucefan.stuff.mapassign

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlin.random.Random


class MapDraw(var mMap: GoogleMap) {
    val r = Random


    fun leftCircle(centerX: Double, centerY: Double, radius: Double) {

        var d = (5 - radius * 4) / 4
        var x:Double = 0.0
        var y = radius

        do {
            with(mMap) {
                makeArbMarker(centerX+x, centerY+y)
                makeArbMarker(centerX + x, centerY - y)
                makeArbMarker(centerX - x, centerY + y)
                makeArbMarker(centerX - x, centerY - y)
                makeArbMarker(centerX + y, centerY + x)
                makeArbMarker(centerX + y, centerY - x)
                makeArbMarker(centerX - y, centerY + x)
                makeArbMarker(centerX - y, centerY - x)
            }
            if (d < 0) {
                d += 2 * x + 1
            }
            else {
                d += 2 * (x - y) + 1
                y--
            }
            x++
        }
        while (x <= y)
    }

    //midpoint circle
    fun midpointCircle(centerX: Double, centerY: Double, radius: Double) {

            var d = (5 - radius * 4) / 4
            var x:Double = 0.0
            var y = radius

            do {
                with(mMap) {
                    makeArbMarker(centerX+x, centerY+y)
                    makeArbMarker(centerX + x, centerY - y)
                    makeArbMarker(centerX - x, centerY + y)
                    makeArbMarker(centerX - x, centerY - y)
                    makeArbMarker(centerX + y, centerY + x)
                    makeArbMarker(centerX + y, centerY - x)
                    makeArbMarker(centerX - y, centerY + x)
                    makeArbMarker(centerX - y, centerY - x)
                   /* setPixel(centerX + x, centerY + y, circleColor)
                    setPixel(centerX + x, centerY - y, circleColor)
                    setPixel(centerX - x, centerY + y, circleColor)
                    setPixel(centerX - x, centerY - y, circleColor)
                    setPixel(centerX + y, centerY + x, circleColor)
                    setPixel(centerX + y, centerY - x, circleColor)
                    setPixel(centerX - y, centerY + x, circleColor)
                    setPixel(centerX - y, centerY - x, circleColor)*/
                }
                if (d < 0) {
                    d += 2 * x + 1
                }
                else {
                    d += 2 * (x - y) + 1
                    y--
                }
                x++
            }
            while (x <= y)
        }



//Bresenham’s Algorithm

    fun bresenhamAlgo(x1: Double, y1: Double, x2: Double, y2: Double, mutateBy:Double=1.0) {


        //iterators
        var x: Double
        var y: Double
        var dx: Double
        var dy: Double
        var dx1: Double
        var dy1: Double
        var px: Double
        var py: Double
        var xe: Double
        var ye: Double


        //deltas, difference of end points on both axises
        dx = x2 - x1
        dy = y2 - y1

        //"positive copy" of deltas for iterating, i.e. if negative get absolute value from zero
        //on number line
        dx1 = Math.abs(dx)
        dy1 = Math.abs(dy)

        //'Calculate error intervals for both axis'
        //"An error interval is the range of values that a number could have taken before being
        // rounded or truncated. Error intervals are usually written as a range using inequalities,
        // with a lower bound and an upper bound."
        //this means very little to me -Jack
        px = 2 * dy1 - dx1
        py = 2 * dx1 - dy1



        //if the line is x-axis dominant, i.e.
        //ie if it goes further in the x axis then in the why
        if (dy1 <= dx1) {

            //if line the draws left ot right
            if (dx >= 0) {
                x = x1
                y = y1
                xe = x2
            } else {
                //line is right to left
                x = x2
                y = y2
                xe = x1
            }
            //here we call our pixel maker
            xAxisDomLine(x, y, xe, px, dx1, dy1, dx, dy)
        }
        else {
            // Line is drawn bottom to top
            if (dy >= 0) {
                x = x1; y = y1; ye = y2
            } else { // Line is drawn top to bottom
                x = x2; y = y2; ye = y1
            }
            yAxisDomLine(x,y,ye,py,dx1,dy1,dx,dy)
        }

    }


    fun xAxisDomLine(
        x: Double,
        y: Double,
        xe: Double,
        px: Double,
        dx1: Double,
        dy1: Double,
        dx: Double,
        dy: Double
    ) {
        makeArbMarker(x, y) // Draw first pixel
        var x = x
        var y = y
        var px = px
        while (x < xe) {
            x++

            // Deal with octants...
            if (px < 0) {
                px = px + 2 * dy1
            } else {
                if ((dx < 0 && dy < 0) || (dx > 0 && dy > 0)) {
                    y++
                } else {
                    y--
                }
                px = (px + 2 * (dy1 - dx1))
            }
            // Draw pixel from line span at
            // currently rasterized position
            makeArbMarker(x, y)
        }
    }

    fun yAxisDomLine(
        x: Double,
        y: Double,
        ye: Double,
        py:Double,
        dx1: Double,
        dy1: Double,
        dx: Double,
        dy: Double
    ) {
var x =x
        var y = y
        var py =py

        makeArbMarker(x, y); // Draw first pixel

        // Rasterize the line
        while(y < ye) {

            y++

            // Deal with octants...
            if (py <= 0) {
                py = (py + 2 * dx1)
            } else {
                if ((dx < 0 && dy < 0) || (dx > 0 && dy > 0)) {
                    x++
                } else {
                    x--
                }
                py = (py + 2 * (dx1 - dy1))
            }

            // Draw pixel from line span at
            // currently rasterized position
            makeArbMarker(x, y)
        }

    }
    //draws a cross
    fun example() {
        for (i in -25 until 50) {
            val lat = 34.0 - (i.toDouble() + r.nextDouble(0.0, 1.9) - r.nextDouble(0.0, 1.9))
            val lng = 151.00

            makeArbMarker(lat, lng)

        }
        for (i in -35 until 35) {
            val lat = 50.0
            val lng = 151.00 - (i.toDouble() + r.nextDouble(0.0, 1.9) - r.nextDouble(0.0, 1.9))
            makeArbMarker(lat, lng)

        }
    }

    fun makeArbMarker(x: Double, y: Double) {
        val chords = LatLng(x, y)
        mMap.addMarker(MarkerOptions().position(chords).title("${x}, ${y}"))

    }


    /* class BrownianTree(): Runnable {
        val r = Random
        private val img:
        private val particles = mutableListOf<Particle>()


        private inner class Particle {
            private var x = r.nextInt(img.width)
            private var y = r.nextInt(img.height)

            *//* returns true if either out of bounds or collided with tree *//*
            fun move(): Boolean {
                val dx = r.nextInt(3) - 1
                val dy = r.nextInt(3) - 1
                if ((x + dx < 0) || (y + dy < 0) || (y + dy >= img.height) ||
                    (x + dx >= img.width)) return true
                x += dx
                y += dy
                if ((img.getRGB(x, y) and 0xff00) == 0xff00) {
                    img.setRGB(x - dx, y - dy, 0xff00)
                    return true
                }
                return false
            }
        }

        init {
            setBounds(100, 100, 400, 300)
            defaultCloseOperation = EXIT_ON_CLOSE
            img = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
            img.setRGB(img.width / 2, img.height / 2, 0xff00)
        }

        override fun paint(g: Graphics) {
            g.drawImage(img, 0, 0, this)
        }

        override fun run() {
            (0 until 20000).forEach { particles.add(Particle()) }

            while (!particles.isEmpty()) {
                val iter = particles.iterator()
                while (iter.hasNext()) {
                    if (iter.next().move()) iter.remove()
                }
                repaint()
            }
        }
    }*/
}