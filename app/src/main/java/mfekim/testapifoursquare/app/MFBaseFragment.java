package mfekim.testapifoursquare.app;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Base fragment.
 */
public class MFBaseFragment extends Fragment {
    /** Tag for logs. */
    private static final String TAG = MFBaseFragment.class.getSimpleName();

    /** The application context. */
    protected Context mAppContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAppContext = context.getApplicationContext();
    }
}
