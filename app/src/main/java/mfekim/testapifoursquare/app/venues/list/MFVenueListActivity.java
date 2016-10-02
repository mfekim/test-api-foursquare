package mfekim.testapifoursquare.app.venues.list;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import mfekim.testapifoursquare.app.MFBaseActivity;
import mfekim.testapifoursquare.app.R;
import mfekim.testapifoursquare.app.api.MFFoursquareClientAPI;
import mfekim.testapifoursquare.app.model.venue.MFVenue;
import mfekim.testapifoursquare.app.network.MFConnectivityUtils;

/**
 * Shows a list of venues.
 */
public class MFVenueListActivity extends MFBaseActivity {
    /** Tag for logs. */
    private static final String TAG = MFVenueListActivity.class.getSimpleName();

    /** Fragments tags. */
    private static final String VENUE_LIST_FRAGMENT_TAG = "venue_list_fragment_tag";

    /** Koolicar's coordinates. */
    private static final double KOOLICAR_LATITUDE = 45.5333759;
    private static final double KOOLICAR_LONGITUDE = -73.62132600000001;

    /** The maximum of results. */
    private static final int MAX_RESULT_COUNT = 10;

    /** Views. */
    private View mVLoader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mf_activity_list_venue);

        // Views
        mVLoader = findViewById(R.id.list_venue_loader);

        // Search
        searchFoodVenues();
    }

    @Override
    protected void onPause() {
        MFFoursquareClientAPI.getInstance().cancelAllRequests(mAppContext);
        super.onPause();
    }

    /**
     * Searches food venues.
     */
    private void searchFoodVenues() {
        if (MFConnectivityUtils.isConnected(mAppContext)) {
            // Show loader
            mVLoader.setVisibility(View.VISIBLE);
            // Search
            MFFoursquareClientAPI.getInstance().searchFoodVenues(mAppContext, KOOLICAR_LATITUDE,
                    KOOLICAR_LONGITUDE, MAX_RESULT_COUNT,
                    new Response.Listener<List<MFVenue>>() {
                        @Override
                        public void onResponse(List<MFVenue> response) {
                            if (response != null) {
                                // Add fragment
                                Fragment fragment = MFVenueListFragment.newInstance(new ArrayList<>(response));
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.list_venue_container, fragment, VENUE_LIST_FRAGMENT_TAG)
                                        .commit();
                                // Hide loader
                                mVLoader.setVisibility(View.INVISIBLE);
                            } else {
                                Log.e(TAG, "List of venues null");
                                showErrorDialog();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            showErrorDialog();
                            mVLoader.setVisibility(View.INVISIBLE);
                        }
                    });
        } else {
            showNoConnectionDialog();
        }
    }

    /**
     * Shows a dialog to inform that no internet connexion found.
     */
    private void showNoConnectionDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.mf_no_internet_connection_dialog_title)
                .setMessage(R.string.mf_list_venue_no_internet_connection_dialog_message)
                .setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .create()
                .show();
    }

    /**
     * Shows a dialog to inform that the search failed.
     */
    private void showErrorDialog() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.mf_list_venue_error_dialog_message)
                .setPositiveButton(R.string.mf_try_again, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        searchFoodVenues();
                    }
                })
                .setNegativeButton(R.string.mf_exit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .create()
                .show();
    }
}
