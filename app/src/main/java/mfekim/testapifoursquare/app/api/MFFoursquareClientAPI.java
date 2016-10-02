package mfekim.testapifoursquare.app.api;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import mfekim.testapifoursquare.app.model.venue.MFVenue;
import mfekim.testapifoursquare.app.network.MFNetworkClient;

/**
 * The unique client to interact with the Foursquare API.
 */
public class MFFoursquareClientAPI {
    /** Tag for logs. */
    private static final String TAG = MFFoursquareClientAPI.class.getSimpleName();

    /** Client ID. */
    private final String CLIENT_ID = "EGE31RYMBDN2JP0B2SQZCT3FJVEH1V4BICXAXPZJ4TPKZZ1S";

    /* Client server. */
    private final String CLIENT_SECRET = "UQ4OMPWFHT0S4G0O4TW5YOQMV4FXZ3MX0XOPV0NBL23N0VKS";

    /* List of urls. */
    private final String SEARCH_VENUES_URL = "https://api.foursquare.com/v2/venues/search";
    private final String VENUE_DETAILS_URL = "https://api.foursquare.com/v2/venues";

    /** List of categories id. */
    private final String FOOD_CATEGORY_ID = "4d4b7105d754a06374d81259";

    /** Gson instance. */
    private final Gson GSON = new Gson();

    /** Singleton holder. */
    private static class SingletonHolder {
        /** Unique instance. */
        private static final MFFoursquareClientAPI INSTANCE = new MFFoursquareClientAPI();
    }

    /**
     * The unique entry point to get the instance.
     *
     * @return The unique instance of {@link MFFoursquareClientAPI}.
     */
    public static MFFoursquareClientAPI getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /** Private constructor. */
    private MFFoursquareClientAPI() {
    }

    /**
     * Searches the food venues around a location.
     *
     * @param context       A context.
     * @param latitude      The location latitude.
     * @param longitude     The location longitude.
     * @param limit         The maximum number of results.
     * @param listener      A listener.
     * @param errorListener An error listener.
     */
    public void searchFoodVenues(Context context, double latitude, double longitude, int limit,
                                 final Response.Listener<List<MFVenue>> listener,
                                 final Response.ErrorListener errorListener) {
        // Build the url
        String url = addRequiredParameters(SEARCH_VENUES_URL);
        url += "&ll=" + latitude + "," + longitude;
        url += "&categoryId=" + FOOD_CATEGORY_ID;
        url += "&limit=" + limit;
        Log.d(TAG, "Search food venues URL: " + url);

        // Build and execute the request
        JsonObjectRequest request = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Search food venues succeeded");

                        // Retrieves venues
                        List<MFVenue> venues = null;
                        if (response != null && response.has("response") &&
                                response.optJSONObject("response").has("venues")) {
                            JSONArray venuesJSONArray = response.optJSONObject("response")
                                                                .optJSONArray("venues");
                            // Parse json array to a list
                            venues = GSON.fromJson(venuesJSONArray.toString(),
                                    new TypeToken<ArrayList<MFVenue>>() {
                                    }.getType());
                        } else {
                            Log.e(TAG, "No venues found");
                        }

                        // Listener
                        if (listener != null) {
                            listener.onResponse(venues);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Search food venues failed");

                        // Listener
                        if (errorListener != null) {
                            errorListener.onErrorResponse(error);
                        }
                    }
                });
        MFNetworkClient.getInstance().addToRequestQueue(context, request);
    }

    /**
     * Retrieves the details of a venue.
     *
     * @param context       A context.
     * @param venueId       A venue id.
     * @param listener      A listener.
     * @param errorListener An error listener.
     */
    public void getVenueDetails(Context context, String venueId,
                                final Response.Listener<MFVenue> listener,
                                final Response.ErrorListener errorListener) {
        if (!TextUtils.isEmpty(venueId)) {
            // Build the url
            String url = VENUE_DETAILS_URL + "/" + venueId + "/";
            url = addRequiredParameters(url);
            Log.d(TAG, "Venue details URL: " + url);

            // Build and execute the request
            JsonObjectRequest request = new JsonObjectRequest(url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            MFVenue venue = null;
                            if (response != null && response.has("response") &&
                                    response.optJSONObject("response").has("venue")) {
                                JSONObject venueJSON = response.optJSONObject("response")
                                                               .optJSONObject("venue");
                                // Parse json to venue object
                                venue = GSON.fromJson(venueJSON.toString(), MFVenue.class);
                            } else {
                                Log.e(TAG, "No venue found");
                            }

                            // Listener
                            if (listener != null) {
                                listener.onResponse(venue);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO

                            // Listener
                            if (errorListener != null) {
                                errorListener.onErrorResponse(error);
                            }
                        }
                    });
            MFNetworkClient.getInstance().addToRequestQueue(context, request);
        } else {
            Log.e(TAG, "Failed to get the venue details - venue id null/empty");
            // TODO
        }
    }

    /**
     * Adds required parameters to a foursquare url.
     *
     * @param foursquareUrl A foursquare url.
     * @return A foursquare url with the required parameters.
     */
    private String addRequiredParameters(String foursquareUrl) {
        return foursquareUrl + "?client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET
                + "&v=20161001";
    }
}
