package mirea.samsung.myapp_android_1;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    MyThread myThread;

    public MySurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        myThread = new MyThread(getHolder());
        myThread.SetRunning(true);
        myThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        boolean retry = true;
        myThread.SetRunning(false);

        while (retry) {
            try {
                myThread.join();
            } catch (Exception e) {
                retry = false;
                e.printStackTrace();
            }
        }
    }
}
