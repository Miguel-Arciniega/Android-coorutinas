package com.example.ladm_u2_practica1_blancaramirez

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.view.View
import kotlinx.coroutines.*
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.random.Random

class Lienzo(act:MainActivity) : View(act) {

    // Colores
    val COLOR_CIELO_NOCHE = Color.rgb(39,33,78)
    val COLOR_COLINAS_NOCHE = Color.rgb(30,65,25)

    val COLOR_CIELO_DIA = Color.rgb(159,215,233)
    val COLOR_COLINAS_DIA = Color.rgb(50,205,50)

    // Variables coorutina
    var colorCielo = COLOR_CIELO_NOCHE
    var colorColinas = COLOR_COLINAS_NOCHE
    var horas = 0;

    val scope = CoroutineScope(Job()+Dispatchers.Main)
    val corrutina = scope.launch (EmptyCoroutineContext, CoroutineStart.LAZY){
        while (true){
            // Aumenta las horas en cada iteración
            horas++
            act.runOnUiThread{
                invalidate()
            }
            delay(200L)
        }
    }

    override fun onDraw(c: Canvas) {
        super.onDraw(c)
        var p = Paint();

        c.drawColor(colorCielo)

        // Colinas
        dibujarColinas(p,c, colorColinas)

        //ARBOL
//        p.color = Color.rgb(180,114,20)
//        c.drawRect(500f,700f,550f,800f,p)
//        p.color = Color.rgb(10,62,12)
//        c.drawOval(450f,470f,600f,565f,p)
//        c.drawOval(450f,630f,600f,720f,p)
//        c.drawOval(450f,560f,600f,650f,p)

        //CASITA
        p.style = Paint.Style.FILL
        p.color = Color.rgb(1,87,12) // color madera
        c.drawRect(1250f, 400f, 1650f, 600f,p)
        p.color = Color.rgb(51, 14,2) //PUERTA
        c.drawRect(1300f,470f,1380f, 600f, p)
        p.color = Color.rgb(3,29,174) // color calido
        c.drawRect(1460f,470f,1560f,540f,p)
        p.color = Color.rgb(166,128,88) // Color rojillo

        var path = Path()
        path.moveTo(1210f, 430f)
        path.lineTo(1680f, 430f)
        path.lineTo(1645f, 330f)
        path.lineTo(1245f, 330f)
        path.lineTo(1210f, 430f)
        c.drawPath(path, p)

        //CHIMENEA
        p.color = Color.rgb(162,61,1)
        c.drawRect(1600f,275f,1520f, 360f, p)

        // Se inicia la corutina
        corrutina.start()

        // Se llama a la funcion para el ciclo día y noche
        cicloDiaYNoche(p, c)
    }

    fun cicloDiaYNoche(p:Paint, c:Canvas) {
        if (horas >= 0 && horas < 7) {
            dibujarNoche(p, c)
        }
        if (horas >= 7 && horas < 18) {
            dibujarDia(p, c)
        }
        if (horas >= 18 && horas < 24) {
            dibujarNoche(p, c)
            // colorCielo = Color.rgb(255,107,62)
        }
        if(horas == 24){
            dibujarNoche(p, c)
            horas = 0
        }
    }

    fun dibujarDia(p:Paint, c:Canvas){
        colorCielo = COLOR_CIELO_DIA
        colorColinas = COLOR_COLINAS_DIA
        dibujarSol(p, c)
        dibujarHumo(p, c)
    }

    fun dibujarNoche(p:Paint, c:Canvas){
        colorCielo = COLOR_CIELO_NOCHE
        colorColinas = COLOR_COLINAS_NOCHE
        dibujarLuciernagas(p, c)
        dibujarLuna(p, c)
    }

    fun dibujarLuna(p:Paint, c:Canvas){
        p.color = Color.WHITE
        c.drawCircle(500f,200f,80f,p)
        p.color = Color.rgb(39,33,78)
        c.drawCircle(530f,180f,50f,p)
    }

    fun dibujarSol(p:Paint, c:Canvas){
        p.color = Color.YELLOW
        c.drawCircle(500f,200f,80f,p)
    }

    fun dibujarHumo(p:Paint, c:Canvas){
        p.style = Paint.Style.FILL
        p.color = Color.LTGRAY
        c.drawOval(1600f,235f,1550f, 265f, p)

        p.style = Paint.Style.FILL
        p.color = Color.LTGRAY
        c.drawOval(1550f,170f,1450f, 230f, p)

        p.style = Paint.Style.FILL
        p.color = Color.LTGRAY
        c.drawOval(1290f,150f,1430f, 220f, p)
    }

    fun dibujarColinas(p:Paint, c:Canvas, colorColinas:Int){

        //MONTAÑA IZQUIERDA
        p.color = colorColinas
        c.drawOval(-100f,600f,1600f,1300f,p)
        p.style = Paint.Style.STROKE
        p.strokeWidth = 4.0f
        p.color = Color.BLACK
        c.drawOval(-100f,600f,1600f,1300f,p)

        //MONTAÑA IZQUIERDA
        p.color = colorColinas
        p.style = Paint.Style.FILL
        c.drawOval(900f,500f,2500f,1300f,p)
        p.style = Paint.Style.STROKE
        p.strokeWidth = 4.0f
        p.color = Color.BLACK
        c.drawOval(900f,500f,2500f,1300f,p)
    }

    fun dibujarLuciernagas(p:Paint, c:Canvas){
        (0..10).forEach {
            p.color = Color.YELLOW
            c.drawCircle(
                500f + Random.nextInt(-200, 200),
                500f + Random.nextInt(-50, 200),
                5f,p
            )
        }
    }
}