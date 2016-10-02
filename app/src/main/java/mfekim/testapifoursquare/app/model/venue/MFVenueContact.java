package mfekim.testapifoursquare.app.model.venue;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

/**
 * Venue contact.
 */
public class MFVenueContact implements Parcelable {
    /** Tag for logs. */
    private static final String TAG = MFVenueContact.class.getSimpleName();

    @SerializedName("twitter")
    private String mTwitter;

    @SerializedName("phone")
    private String mPhone;

    //region Parcelable Methods
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mTwitter);
        out.writeString(mPhone);
    }

    public static final Parcelable.Creator<MFVenueContact> CREATOR
            = new Parcelable.Creator<MFVenueContact>() {
        public MFVenueContact createFromParcel(Parcel in) {
            return new MFVenueContact(in);
        }

        public MFVenueContact[] newArray(int size) {
            return new MFVenueContact[size];
        }
    };

    private MFVenueContact(Parcel in) {
        mTwitter = in.readString();
        mPhone = in.readString();
    }
    //endregion

    //region Special Getters
    public String optTwitter(String defaultValue) {
        return TextUtils.isEmpty(mTwitter) ? defaultValue : mTwitter;
    }

    public String optPhone(String defaultValue) {
        return TextUtils.isEmpty(mPhone) ? defaultValue : mPhone;
    }
    //endregion
}
