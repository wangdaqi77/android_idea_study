package com.example.wongki.commonlib.utils

import android.animation.ObjectAnimator
import android.animation.TypeEvaluator
import android.graphics.Path
import android.util.Log
import android.view.View

class AnimatorPath {
    private val paths by lazy { ArrayList<Path>() }

    fun moveTo(x: Float, y: Float) {
        paths.add(Path(PathType.MOVE,
                PointF(x, y)
        )
        )
    }

    fun lineTo(x: Float, y: Float) {
        paths.add(Path(PathType.LINE,
                PointF(x, y)
        )
        )
    }

    fun cubicTo(x: Float, y: Float,
                cx1: Float, cy1: Float,
                cx2: Float, cy2: Float) {

        paths.add(Path(PathType.CUBIC,
                PointF(x, y),
                PointF(cx1, cy1),
                PointF(cx2, cy2)
        )
        )
    }

    private lateinit var view: View

    fun start(view: View, duration: Long) {
        this.view = view
        ObjectAnimator
                .ofObject(this, "update", E(paths.size), *paths.toArray())
                .setDuration(duration)
                .start()
    }

    fun setUpdate(path: Path) {
        val point = path.point
        view.translationX = point.x
        view.translationY = point.y
    }

    enum class PathType {
        MOVE,
        LINE,
        CUBIC,
    }

    class Path(
            val type: PathType,
            val point: PointF,
            val controlPoint1: PointF? = null,
            val controlPoint2: PointF? = null
    ) {
        override fun toString(): String {
            return "type:$type, points:$point"
        }
    }

    class PointF(
            val x: Float,
            val y: Float
    ) {
        override fun toString(): String {
            return "x:$x, y$y"
        }
    }

    private class E(val pathSize: Int) : TypeEvaluator<Path> {
        override fun evaluate(t: Float, startValue: Path, endValue: Path): Path {

            Log.i("TEST", "======================\n" +
                    "fraction:$t\n " +
                    "startValue:$startValue\n " +
                    "endValue:$endValue")
            val type = endValue.type
            var x = 0f
            var y = 0f
            when (type) {
                PathType.MOVE -> {
                    x = endValue.point.x
                    y = endValue.point.y
                }
                PathType.LINE -> {
                    val startPoint = startValue.point
                    val endPoint = endValue.point
                    x = startPoint.x + t * (endPoint.x - startPoint.x)
                    y = startPoint.y + t * (endPoint.y - startPoint.y)
                }
                PathType.CUBIC -> {
                    val minusT = 1 - t
                    val p0 = startValue.point
                    val p1 = endValue.controlPoint1!!
                    val p2 = endValue.controlPoint2!!
                    val p3 = endValue.point

                    x = p0.x * minusT * minusT * minusT + 3 * p1.x * t * minusT * minusT + 3 * p2.x * t * t * minusT + p3.x * t * t * t
                    y = p0.y * minusT * minusT * minusT + 3 * p1.y * t * minusT * minusT + 3 * p2.y * t * t * minusT + p3.y * t * t * t

                }

            }
            return Path(PathType.MOVE, PointF(x, y))
        }


    }
}
