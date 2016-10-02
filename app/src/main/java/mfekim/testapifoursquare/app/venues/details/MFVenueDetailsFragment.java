package mfekim.testapifoursquare.app.venues.details;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import mfekim.testapifoursquare.app.MFBaseFragment;
import mfekim.testapifoursquare.app.R;
import mfekim.testapifoursquare.app.api.MFFoursquareClientAPI;
import mfekim.testapifoursquare.app.model.venue.MFVenue;

/**
 * Shows the details of a venue after retrieving it from the server.
 */
public class MFVenueDetailsFragment extends MFBaseFragment {
    /** Arguments keys. */
    public static final String ARG_VENUE_ID = "venue_id";

    /** Tag for logs. */
    private static final String TAG = MFVenueDetailsFragment.class.getSimpleName();

    /** The venue id. */
    private String mVenueId;

    /** The venue. */
    private MFVenue mVenue;

    /** Views. */
    private ImageView mImgBestPhoto;
    private TextView mTvName;
    private TextView mTvDescription;
    private TextView mTvAddress;
    private TextView mTvPhone;
    private TextView mTvTwitter;
    private TextView mTvRating;

    /**
     * @param venueId A venue id.
     * @return A new instance of {@link MFVenueDetailsFragment}.
     */
    public static MFVenueDetailsFragment newInstance(String venueId) {
        Bundle args = new Bundle();
        args.putString(ARG_VENUE_ID, venueId);

        MFVenueDetailsFragment fragment = new MFVenueDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mVenueId = args.getString(ARG_VENUE_ID);
        } else {
            Log.e(TAG, "No arguments found");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mf_fragment_details_venue, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Views
        mImgBestPhoto = (ImageView) view.findViewById(R.id.venue_best_photo);
        mTvName = (TextView) view.findViewById(R.id.venue_name);
        mTvDescription = (TextView) view.findViewById(R.id.venue_description);
        mTvAddress = (TextView) view.findViewById(R.id.venue_address);
        mTvPhone = (TextView) view.findViewById(R.id.venue_phone);
        mTvTwitter = (TextView) view.findViewById(R.id.venue_twitter);
        mTvRating = (TextView) view.findViewById(R.id.venue_rating);

        // Retrieve details of the venue
        getVenueDetails();
    }

    @Override
    public void onPause() {
        MFFoursquareClientAPI.getInstance().cancelAllRequests(mAppContext);
        super.onPause();
    }

    /**
     * Retrieves the details of a venue.
     */
    private void getVenueDetails() {
        if (!TextUtils.isEmpty(mVenueId)) {
            MFFoursquareClientAPI.getInstance().getVenueDetails(mAppContext, mVenueId,
                    new Response.Listener<MFVenue>() {
                        @Override
                        public void onResponse(MFVenue response) {
                            bindData(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            showErrorDialog();
                        }
                    });
        } else {
            Log.e(TAG, "Failed to get venue details - venue id null/empty");
            showErrorDialog();
        }
    }

    /**
     * Binds data.
     *
     * @param venue A venue.
     */
    private void bindData(MFVenue venue) {
        if (venue != null) {
            mVenue = venue;

            // Best Photo
            boolean hasBestPhoto = mVenue.hasBestPhoto();
            if (hasBestPhoto) {
                int width = (int) getResources().getDimension(R.dimen.mf_venue_best_photo_width);
                int height = (int) getResources().getDimension(R.dimen.mf_venue_best_photo_height);
                Picasso.with(getContext())
                       .load(mVenue.getBestPhotoUrl(width, height))
                       .into(mImgBestPhoto, new Callback() {
                           @Override
                           public void onSuccess() {
                               Log.d(TAG, "Best photo loading succeeded");
                           }

                           @Override
                           public void onError() {
                               Log.d(TAG, "Best photo loading failed");
                           }
                       });
            }
            mImgBestPhoto.setVisibility(hasBestPhoto ? View.VISIBLE : View.GONE);

            // Name
            String name = mVenue.optName("");
            mTvName.setText(name);
            mTvName.setVisibility(name.isEmpty() ? View.GONE : View.VISIBLE);

            // Description
            String description = mVenue.optDescription("");
            mTvDescription.setText(description);
            mTvDescription.setVisibility(description.isEmpty() ? View.GONE : View.VISIBLE);

            // Address
            String address = mVenue.optAddress("");
            mTvAddress.setText(getString(R.string.mf_details_venue_address_pattern, address));
            mTvAddress.setVisibility(address.isEmpty() ? View.GONE : View.VISIBLE);

            // Phone
            String phone = mVenue.optPhone("");
            mTvPhone.setText(getString(R.string.mf_details_venue_phone_pattern, phone));
            mTvPhone.setVisibility(phone.isEmpty() ? View.GONE : View.VISIBLE);

            // Twitter
            String twitter = mVenue.optTwitter("");
            mTvTwitter.setText(getString(R.string.mf_details_venue_twitter_pattern, twitter));
            mTvTwitter.setVisibility(twitter.isEmpty() ? View.GONE : View.VISIBLE);

            // Rating
            Double rating = mVenue.getRating();
            if (rating != null) {
                mTvRating.setText(getString(R.string.mf_details_venue_rating_pattern, rating));
                mTvRating.setVisibility(View.VISIBLE);
            } else {
                mTvRating.setVisibility(View.GONE);
            }
        } else {
            showErrorDialog();
        }
    }

    /**
     * Shows a dialog to inform that an error appeared.
     */
    private void showErrorDialog() {
        new AlertDialog.Builder(getActivity())
                .setMessage(R.string.mf_details_venue_error_dialog_message)
                .setPositiveButton(R.string.mf_try_again, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getVenueDetails();
                    }
                })
                .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().onBackPressed();
                    }
                })
                .create()
                .show();
    }
}
