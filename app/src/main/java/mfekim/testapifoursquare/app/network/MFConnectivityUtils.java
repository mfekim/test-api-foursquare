/*
 * PXConnectivityUtils
 * PaddixTool
 *
 * Created by Mehdi Feki on 27/8/2015.
 * Copyright (c) 2015 Palpix. All rights reserved.
 */

package mfekim.testapifoursquare.app.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Regroups helpful methods about connectivity.
 */
public class MFConnectivityUtils {
    /**
     * @param context A context.
     * @return true if there is a connection, false otherwise.
     */
    public static boolean isConnected(Context context) {
        NetworkInfo networkInfo = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}
