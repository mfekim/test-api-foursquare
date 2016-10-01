package mfekim.testapifoursquare.app.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * It's the only entry point to execute a request. If you need to execute a request, you MUST use
 * this class by calling the {@link MFNetworkClient#addToRequestQueue(Context, Request)} method.
 */
public class MFNetworkClient {
    /** Tag for logs. */
    private static final String TAG = MFNetworkClient.class.getSimpleName();

    /** Holder. */
    private static class SingletonHolder {
        /** Unique instance. */
        private final static MFNetworkClient INSTANCE = new MFNetworkClient();
    }

    /** Unique entry point to get the instance. */
    public static MFNetworkClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /** Private constructor. */
    private MFNetworkClient() {
    }

    /** A Volley request queue. */
    private RequestQueue mRequestQueue;

    /**
     * Adds a request to the queue to be executed.
     *
     * @param context A context.
     * @param request The request to execute.
     */
    public void addToRequestQueue(Context context, Request request) {
        getRequestQueue(context).add(request);
    }

    /**
     * Cancels all requests which have the tag passed as parameter.
     *
     * @param context    A context.
     * @param requestTag A request tag which indicates which requests to cancel.
     */
    public void cancelAllRequest(Context context, String requestTag) {
        Log.d(TAG, "Cancel requests with tag " + requestTag);
        getRequestQueue(context).cancelAll(requestTag);
    }

    /**
     * @param context A context.
     * @return The Volley request queue.
     */
    public RequestQueue getRequestQueue(Context context) {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return mRequestQueue;
    }
}
