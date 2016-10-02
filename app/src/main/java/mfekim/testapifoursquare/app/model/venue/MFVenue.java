package mfekim.testapifoursquare.app.model.venue;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

/**
 * A venue.
 */
public class MFVenue implements Parcelable {
    /** Tag for logs. */
    private static final String TAG = MFVenue.class.getSimpleName();

    @SerializedName("id")
    private String mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("url")
    private String mUrl;

    @SerializedName("rating")
    private Double mRating;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("contact")
    private MFVenueContact mContact;

    @SerializedName("location")
    private MFVenueLocation mLocation;

    @SerializedName("bestPhoto")
    private MFVenuePhoto mBestPhoto;

    //region Parcelable Methods
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mId);
        out.writeString(mName);
        out.writeString(mUrl);
        out.writeDouble(mRating != null ? mRating : -1);
        out.writeString(mDescription);
        out.writeParcelable(mContact, flags);
        out.writeParcelable(mLocation, flags);
        out.writeParcelable(mBestPhoto, flags);
    }

    public static final Parcelable.Creator<MFVenue> CREATOR
            = new Parcelable.Creator<MFVenue>() {
        public MFVenue createFromParcel(Parcel in) {
            return new MFVenue(in);
        }

        public MFVenue[] newArray(int size) {
            return new MFVenue[size];
        }
    };

    private MFVenue(Parcel in) {
        mId = in.readString();
        mName = in.readString();
        mUrl = in.readString();
        double rating = in.readDouble();
        mRating = rating != -1 ? rating : null;
        mDescription = in.readString();
        mContact = in.readParcelable(MFVenueContact.class.getClassLoader());
        mLocation = in.readParcelable(MFVenueLocation.class.getClassLoader());
        mBestPhoto = in.readParcelable(MFVenuePhoto.class.getClassLoader());
    }
    //endregion

    //region Special Getters
    public String optName(String defaultValue) {
        return TextUtils.isEmpty(mName) ? defaultValue : mName;
    }

    public String optDescription(String defaultValue) {
        return TextUtils.isEmpty(mDescription) ? defaultValue : mDescription;
    }

    public String optAddress(String defaultValue) {
        return mLocation != null ? mLocation.optAddress(defaultValue) : defaultValue;
    }

    public String optTwitter(String defaultValue) {
        return mContact != null ? mContact.optTwitter(defaultValue) : defaultValue;
    }

    public String optPhone(String defaultValue) {
        return mContact != null ? mContact.optPhone(defaultValue) : defaultValue;
    }
    //endregion

    //region Getters
    public String getId() {
        return mId;
    }

    public Double getRating() {
        return mRating;
    }

    public Long getDistance() {
        return mLocation != null ? mLocation.getDistance() : null;
    }

    /**
     * Gets the best photo url.
     *
     * @param width  The wanted width.
     * @param height The wanted height.
     * @return An url, null otherwise.
     */
    public String getBestPhotoUrl(int width, int height) {
        return hasBestPhoto() ? mBestPhoto.getUrl(width, height) : null;
    }
    //endregion

    //region Checkers
    public boolean hasBestPhoto() {
        return mBestPhoto != null && mBestPhoto.hasUrl();
    }
    //endregion
}
