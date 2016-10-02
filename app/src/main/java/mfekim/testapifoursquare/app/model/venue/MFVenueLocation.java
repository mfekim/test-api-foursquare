package mfekim.testapifoursquare.app.model.venue;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

/**
 * Venue location
 */
public class MFVenueLocation implements Parcelable {
    /** Tag for logs. */
    private static final String TAG = MFVenueLocation.class.getSimpleName();

    @SerializedName("address")
    private String mAddress;

    @SerializedName("lat")
    private double mLatitude;

    @SerializedName("lng")
    private double mLongitude;

    @SerializedName("distance")
    private Long mDistance;

    //region Parcelable Methods
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mAddress);
        out.writeDouble(mLatitude);
        out.writeDouble(mLongitude);
        out.writeLong(mDistance != null ? mDistance : -1);
    }

    public static final Parcelable.Creator<MFVenueLocation> CREATOR
            = new Parcelable.Creator<MFVenueLocation>() {
        public MFVenueLocation createFromParcel(Parcel in) {
            return new MFVenueLocation(in);
        }

        public MFVenueLocation[] newArray(int size) {
            return new MFVenueLocation[size];
        }
    };

    private MFVenueLocation(Parcel in) {
        mAddress = in.readString();
        mLatitude = in.readDouble();
        mLongitude = in.readDouble();
        long distance = in.readLong();
        mDistance = distance != -1 ? distance : null;
    }
    //endregion

    //region Specials Getters
    public String optAddress(String defaultValue) {
        return TextUtils.isEmpty(mAddress) ? defaultValue : mAddress;
    }
    //endregion

    //region Getters
    public Long getDistance() {
        return mDistance;
    }
    //endregion
}
