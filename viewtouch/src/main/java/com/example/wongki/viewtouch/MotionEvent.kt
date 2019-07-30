package com.example.wongki.viewtouch

class MotionEvent {
    var x = 0f
    var y = 0f
    var action: Action = Action.DOWN

    enum class Action {
        DOWN,
        MOVE,
        UP,
        CANCEL,
    }
}