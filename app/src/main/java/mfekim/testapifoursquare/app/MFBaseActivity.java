package mfekim.testapifoursquare.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Base activity.
 */
public class MFBaseActivity extends AppCompatActivity {
    /** Tag for logs. */
    private static final String TAG = MFBaseActivity.class.getSimpleName();

    /** Application context. */
    protected Context mAppContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAppContext = getApplicationContext();
    }
}
