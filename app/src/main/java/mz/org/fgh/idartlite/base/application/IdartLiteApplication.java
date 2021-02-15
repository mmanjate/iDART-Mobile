package mz.org.fgh.idartlite.base.application;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import mz.org.fgh.idartlite.util.LruBitmapCache;

public class IdartLiteApplication extends Application {
    public static final String TAG = IdartLiteApplication.class
            .getSimpleName();

    public static final String CHANNEL_1_ID = "channel1";
    public static final String CHANNEL_2_ID = "channel2";


    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private static IdartLiteApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        createNotificationChannels();
    }

    private void createNotificationChannels(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel1 = new NotificationChannel(CHANNEL_1_ID, "channel 1", NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("This is Channel 1");

            NotificationChannel channel2 = new NotificationChannel(CHANNEL_2_ID, "channel 2", NotificationManager.IMPORTANCE_HIGH);
            channel2.setDescription("This is Channel 2");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);

        }
    }

    public static synchronized IdartLiteApplication getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
