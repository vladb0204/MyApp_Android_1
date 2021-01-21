package mirea.samsung.myapp_android_1;

import android.animation.ArgbEvaluator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.Random;

public class MyThread extends Thread {

    private static final int FRACTION_TIME = 10000000;

    private ArgbEvaluator argbEvaluator;
    private Paint paint;
    private SurfaceHolder surfaceHolder;
    private boolean flag;
    private long startTime;
    private long buffRedrawTime;

    public MyThread(SurfaceHolder surface) {
        flag = false;

        surfaceHolder = surface;

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        argbEvaluator = new ArgbEvaluator();
    }

    public void SetRunning(boolean input) {
        this.flag = input;
    }

    @Override
    public void run() {
        Canvas canvas;

        startTime = getTime();
        while (flag) {
            long currentTime = getTime();
            long elapsedTime = currentTime - buffRedrawTime;

            if (elapsedTime < 1000) {
                continue;
            }

            canvas = surfaceHolder.lockCanvas();
            drawCircles(canvas);
            surfaceHolder.unlockCanvasAndPost(canvas);

            buffRedrawTime = getTime();
        }
    }

    public long getTime() {
        return System.nanoTime() / 1000;
    }

    public void drawCircles(Canvas canvas) {
        int[] randomColors = new int[] {Color.YELLOW, Color.RED, Color.GRAY, Color.GREEN, Color.LTGRAY, Color.WHITE, Color.MAGENTA, Color.DKGRAY};
        long currentTime = getTime();

        int centerX = canvas.getWidth() / 2;
        int centerY = canvas.getHeight() / 2;

        float maxRadius = Math.min(canvas.getHeight(), canvas.getWidth());
        Log.d("#### Maximum Radius", Float.toString(maxRadius));
        float fraction = (float) (currentTime % FRACTION_TIME) / FRACTION_TIME;

        int color = (int) argbEvaluator.evaluate(fraction, randomColors[new Random().nextInt(randomColors.length)], Color.BLACK);
        Log.d("#### color", Integer.toString(color));

        paint.setColor(color);
        canvas.drawColor(Color.BLACK); // Setting background color
        canvas.drawCircle(centerX, centerY, maxRadius * fraction, paint);
    }
}
