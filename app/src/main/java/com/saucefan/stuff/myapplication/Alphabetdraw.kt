package com.saucefan.stuff.mapassign

class Alphabetdraw (val mapDraw:MapDraw){


    fun drawB(
        x:Double
              ,y:Double,
              zoom:Float=1f) {

        val adjzoom=zoom/1
        var x=x
        var y = y

        /*  ****************
        *  y   _
        *  ^  | \
        *  |  |_/
        *  |  |  \
        *  |  |_/
        * 0|----->>x
        *  0
        *                   */
        // top -
        var x2=x+(20*adjzoom)
        var y2=y

        mapDraw.bresenhamAlgo(x,y,x2,y2)
        //backbone of B

x=x2
y2=y+(15*adjzoom)
        // top - in f
        mapDraw.bresenhamAlgo(x,y,x2,y2)


x=x2-(10*adjzoom)
        x2=x
        // middle - in f
        mapDraw.bresenhamAlgo(x,y,x2,y2)





    }
}