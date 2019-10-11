package com.saucefan.stuff.mapassign

class Alphabetdraw (val mapDraw:MapDraw){


    fun drawB(
        x:Double
              ,y:Double,
              x2:Double,
              y2:Double,
              muta:String?=null) {

        var x=x
        var y = y
        var x2=x2
        var y2=y2
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
        mapDraw.bresenhamAlgo(x,y,x2,y2)
        //backbone of B




        x=48.0
        y =161.0
        x2= 48.0
        y2=180.0

        // middle - in f
        mapDraw.bresenhamAlgo(x,y,x2,y2)

        x=34.0
        y =161.0
        x2= 55.0
        y2=161.0

        // | in f
        mapDraw.bresenhamAlgo(x,y,x2,y2)



    }
}