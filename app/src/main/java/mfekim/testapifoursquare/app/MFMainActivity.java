package mfekim.testapifoursquare.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import mfekim.testapifoursquare.app.api.MFFoursquareClientAPI;
import mfekim.testapifoursquare.app.model.MFVenue;
import mfekim.testapifoursquare.app.venues.list.MFVenueListFragment;

/**
 * Main activity.
 */
public class MFMainActivity extends AppCompatActivity {
    /** Tag for logs. */
    private static final String TAG = MFMainActivity.class.getSimpleName();

    private static final double KOOLICAR_LATITUDE = 45.5333759;
    private static final double KOOLICAR_LONGITUDE = -73.62132600000001;

    private static final int MAX_RESULT_COUNT = 10;

    private static final String VENUE_LIST_FRAGMENT_TAG = "venue_list_fragment_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mf_activity_main);

        MFFoursquareClientAPI.getInstance().searchFoodVenues(getApplicationContext(),
                KOOLICAR_LATITUDE, KOOLICAR_LONGITUDE, MAX_RESULT_COUNT,
                new Response.Listener<List<MFVenue>>() {
                    @Override
                    public void onResponse(List<MFVenue> response) {
                        Fragment fragment = MFVenueListFragment.newInstance(new ArrayList<>(response));
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.main_list_venue_container, fragment, VENUE_LIST_FRAGMENT_TAG)
                                .commit();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
    }
}
