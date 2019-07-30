package com.example.wongki.viewtouch;
public class Activity {

    public static void main(String[] args) {


    }

    public static void test(int x,int y) {
        ViewGroup decorView = new ViewGroup();
        decorView.setRect(0, 0, 1000, 1000);
        decorView.setName("decorView");

        ViewGroup viewGroup = new ViewGroup();
        decorView.setRect(0, 0, 500, 500);
        decorView.setName("viewGroup");

        View button = new View();
        decorView.setRect(200, 200, 400, 400);
        decorView.setName("button");

        viewGroup.addView(button);

        decorView.addView(viewGroup);

        MotionEvent motionEvent = new MotionEvent();
        motionEvent.setAction(MotionEvent.Action.DOWN);
        motionEvent.setX(x);
        motionEvent.setY(y);


        decorView.dispatchTouchEvent(motionEvent);

    }
}
