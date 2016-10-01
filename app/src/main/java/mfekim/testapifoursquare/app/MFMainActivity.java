package mfekim.testapifoursquare.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import mfekim.testapifoursquare.app.api.MFFoursquareClientAPI;

/**
 * Main activity.
 */
public class MFMainActivity extends AppCompatActivity {
    /** Tag for logs. */
    private static final String TAG = MFMainActivity.class.getSimpleName();

    private static final double KOOLICAR_LATITUDE = 45.5333759;
    private static final double KOOLICAR_LONGITUDE = -73.62132600000001;

    private static final int MAX_RESULT_COUNT = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mf_activity_main);

        // Just for test
        MFFoursquareClientAPI.getInstance().searchFoodVenues(getApplicationContext(),
                KOOLICAR_LATITUDE, KOOLICAR_LONGITUDE, MAX_RESULT_COUNT, null, null);
    }
}
