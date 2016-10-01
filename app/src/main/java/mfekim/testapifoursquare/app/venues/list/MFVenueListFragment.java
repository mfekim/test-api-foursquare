package mfekim.testapifoursquare.app.venues.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mfekim.testapifoursquare.app.MFBaseFragment;
import mfekim.testapifoursquare.app.R;
import mfekim.testapifoursquare.app.model.MFVenue;

/**
 * Shows a list of venues.
 */
public class MFVenueListFragment extends MFBaseFragment {
    /** Arguments keys. */
    public static final String ARG_VENUES = "venues";

    /** Tag for logs. */
    private static final String TAG = MFVenueListFragment.class.getSimpleName();

    /** Views. */
    private RecyclerView mRecyclerView;

    /** Layout manager of the recycler view. */
    private RecyclerView.LayoutManager mLayoutManager;

    /** Adapter of the recycler view. */
    private RecyclerView.Adapter mAdapter;

    /** A list of venues. */
    private List<MFVenue> mVenues;

    /**
     * @param venues A list of venues.
     * @return A new instance of {@link MFVenueListFragment}.
     */
    public static MFVenueListFragment newInstance(ArrayList<MFVenue> venues) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_VENUES, venues);

        MFVenueListFragment fragment = new MFVenueListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            // Venues
            if (args.containsKey(ARG_VENUES)) {
                mVenues = args.getParcelableArrayList(ARG_VENUES);
            } else {
                Log.e(TAG, "Venues not found into arguments");
            }
        } else {
            Log.e(TAG, "No arguments found");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mf_fragment_list_venue, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.list_venue_recycler_view);

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // Set layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Set adapter
        mAdapter = new MFVenueListAdapter(mVenues);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * Manages a list of venues.
     */
    private class MFVenueListAdapter extends RecyclerView.Adapter<MFVenueListAdapter.ViewHolder> {
        private List<MFVenue> mItems;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        class ViewHolder extends RecyclerView.ViewHolder {
            /** Views. */
            TextView tvName;
            View vDivider;

            /**
             * Constructor.
             *
             * @param itemView The item view.
             */
            ViewHolder(View itemView) {
                super(itemView);
                tvName = (TextView) itemView.findViewById(R.id.item_list_venue_name);
                vDivider = itemView.findViewById(R.id.item_list_venue_divider);

                // Click
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO
                    }
                });
            }
        }

        /**
         * Constructor.
         *
         * @param venues A list of venues.
         */
        MFVenueListAdapter(List<MFVenue> venues) {
            mItems = venues;
        }

        @Override
        public MFVenueListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(getActivity())
                                          .inflate(R.layout.mf_item_list_venue, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // Name
            holder.tvName.setText(mItems.get(position).optName("-"));
            // Divider visibility
            holder.vDivider.setVisibility(position == getItemCount() - 1 ?
                    View.INVISIBLE : View.VISIBLE);
        }

        @Override
        public int getItemCount() {
            return mItems != null ? mItems.size() : 0;
        }
    }
}
