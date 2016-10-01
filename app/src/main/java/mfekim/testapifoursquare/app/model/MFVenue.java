package mfekim.testapifoursquare.app.model;

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

    //region Parcelable Methods
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mId);
        out.writeString(mName);
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
    }
    //endregion

    //region Special Getters
    public String optName(String defaultValue) {
        return TextUtils.isEmpty(mName) ? defaultValue : mName;
    }
    //endregion
}
