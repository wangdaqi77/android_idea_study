package com.example.wongki.viewtouch

class TouchTest{
    companion object {
        fun test(x: Float, y: Float) {
            val decorView = ViewGroup()
            decorView.setRect(0, 0, 1000, 1000)
            decorView.name = "decorView"

            val viewGroup = ViewGroup()
            viewGroup.setRect(0, 0, 500, 500)
            viewGroup.name = "viewGroup"

            val button = View()
            button.setRect(200, 200, 400, 400)
            button.name = "button"
            button.mTouchListener =object : TouchListener{
                override fun onTouch(view: View, event: MotionEvent): Boolean {

                    return true
                }
            }

            viewGroup.addView(button)

            decorView.addView(viewGroup)

            val motionEvent = MotionEvent()
            motionEvent.action = MotionEvent.Action.DOWN
            motionEvent.x = x
            motionEvent.y = y


            decorView.dispatchTouchEvent(motionEvent)

        }
    }
}