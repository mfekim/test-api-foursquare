package mfekim.testapifoursquare.app.venues.details;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;

import mfekim.testapifoursquare.app.MFBaseActivity;
import mfekim.testapifoursquare.app.R;

/**
 * Shows the details of a venue.
 */
public class MFVenueDetailsActivity extends MFBaseActivity {
    /** Extras keys. */
    public static final String EXTRA_VENUE_ID = "venue_id";

    /** Tag for logs. */
    private static final String TAG = MFVenueDetailsActivity.class.getSimpleName();

    /** Fragments tags. */
    private static final String DETAILS_VENUE_FRAGMENT_TAG = "details_venue_fragment";

    /** The venue id. */
    private String mVenueId;

    /**
     * Launches the activity.
     *
     * @param activity Current activity.
     * @param venueId  A venue id.
     */
    public static void launchActivity(Activity activity, String venueId) {
        Intent intent = new Intent(activity, MFVenueDetailsActivity.class);
        intent.putExtra(EXTRA_VENUE_ID, venueId);
        activity.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.mf_activity_in, R.anim.mf_activity_out);
        setContentView(R.layout.mf_activity_details_venue);

        // Init action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Retrieve venue id from extra
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_VENUE_ID)) {
            mVenueId = intent.getStringExtra(EXTRA_VENUE_ID);
        } else {
            Log.e(TAG, "No venue id found into extra");
        }

        // Add fragment
        Fragment fragment = MFVenueDetailsFragment.newInstance(mVenueId);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.details_venue_container, fragment, DETAILS_VENUE_FRAGMENT_TAG)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.mf_activity_back_in, R.anim.mf_activity_back_out);
    }
}
