package dev.gabrielhenrique.remindertt.ui

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import dev.gabrielhenrique.remindertt.R

class BackgroundDrawable(
    private val context : Context,
    private var x: Float,
    private var y: Float
) : Drawable() {
    private var transparency = 255
    private var expanded = false

    override fun draw(canvas: Canvas) {

//        if(expanded){
//            canvas.drawColor(context.getColor(R.color.backgroundClickLight))
//            return
//        }
        // Get the drawable's bounds
        val width: Int = bounds.width()
        val height: Int = bounds.height()
//        val radius: Float = Math.min(width, height).toFloat() / 2f
        val radius: Float = Math.min(width, height).toFloat()

        val gradient = RadialGradient(x,y, radius, context.getColor(R.color.backgroundClickLight2), context.getColor(R.color.backgroundClickDark) ,Shader.TileMode.CLAMP)
//        val myPaint = Paint().
        // Draw a red circle in the center
        val gradientPaint = Paint()
        gradientPaint.isDither = true
        gradientPaint.shader = gradient
        gradientPaint.alpha = transparency

        canvas.drawColor(context.getColor(R.color.backgroundClickDark))
        canvas.drawCircle(x, y, radius, gradientPaint)
    }

    override fun setAlpha(alpha: Int) {
        transparency = alpha
    }

    override fun getOpacity(): Int = PixelFormat.OPAQUE

    override fun setColorFilter(colorFilter: ColorFilter?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun setCoordinates(x: Float, y: Float){
        this.x = x
        this.y = y
    }

    fun expand(){
        expanded = true
        invalidateSelf()
    }
}