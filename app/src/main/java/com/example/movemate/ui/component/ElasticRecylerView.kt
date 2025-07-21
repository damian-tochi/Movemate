package com.example.movemate.ui.component

import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import android.widget.EdgeEffect
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.*


class ElasticRecyclerView @JvmOverloads constructor(
context: Context, attrs: AttributeSet? = null
) : RecyclerView(context, attrs) {

    private val fadePaint = Paint()
    private var fadeHeight = 150f
    private var showBottomFade = true

    init {
        layoutManager = LinearLayoutManager(context)
        overScrollMode = View.OVER_SCROLL_ALWAYS
        edgeEffectFactory = BounceEdgeEffectFactory()
        initBottomFade()
    }

    private fun initBottomFade() {
        val fadeColor = ContextCompat.getColor(context, android.R.color.white)
        fadePaint.shader = LinearGradient(
            0f, height - fadeHeight, 0f, height.toFloat(),
            fadeColor, 0x00FFFFFF, Shader.TileMode.CLAMP
        )
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        if (showBottomFade) {
            canvas.drawRect(
                0f,
                height - fadeHeight,
                width.toFloat(),
                height.toFloat(),
                fadePaint
            )
        }
    }

    override fun fling(velocityX: Int, velocityY: Int): Boolean {
        val scaledVelocityY = (velocityY * 1.3f).toInt()
        return super.fling(velocityX, scaledVelocityY)
    }

    private inner class BounceEdgeEffectFactory : EdgeEffectFactory() {
        override fun createEdgeEffect(view: RecyclerView, direction: Int): EdgeEffect {
            return object : EdgeEffect(view.context) {
                override fun onPull(deltaDistance: Float) {
                    super.onPull(deltaDistance * 1.5f)
                    view.invalidate()
                }

                override fun onPull(deltaDistance: Float, displacement: Float) {
                    super.onPull(deltaDistance * 1.5f, displacement)
                    view.invalidate()
                }

                override fun onRelease() {
                    super.onRelease()
                    view.invalidate()
                }

                override fun onAbsorb(velocity: Int) {
                    super.onAbsorb((velocity * 1.5f).toInt())
                    view.invalidate()
                }
            }
        }
    }

}
