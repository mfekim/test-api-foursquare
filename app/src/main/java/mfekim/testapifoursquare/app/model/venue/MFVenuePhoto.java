package mfekim.testapifoursquare.app.model.venue;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

/**
 * Venue photo.
 */
public class MFVenuePhoto implements Parcelable {
    /** Tag for logs. */
    private static final String TAG = MFVenuePhoto.class.getSimpleName();

    @SerializedName("prefix")
    private String mPrefix;

    @SerializedName("suffix")
    private String mSuffix;

    //region Parcelable Methods
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mPrefix);
        out.writeString(mSuffix);
    }

    public static final Parcelable.Creator<MFVenuePhoto> CREATOR
            = new Parcelable.Creator<MFVenuePhoto>() {
        public MFVenuePhoto createFromParcel(Parcel in) {
            return new MFVenuePhoto(in);
        }

        public MFVenuePhoto[] newArray(int size) {
            return new MFVenuePhoto[size];
        }
    };

    private MFVenuePhoto(Parcel in) {
        mPrefix = in.readString();
        mSuffix = in.readString();
    }
    //endregion

    //region Getters
    public String getUrl(int width, int height) {
        return hasUrl() ? (mPrefix + width + "x" + height + mSuffix) : null;
    }
    //endregion

    //region Checkers
    public boolean hasUrl() {
        return !TextUtils.isEmpty(mPrefix) && !TextUtils.isEmpty(mSuffix);
    }
    //endregion
}
