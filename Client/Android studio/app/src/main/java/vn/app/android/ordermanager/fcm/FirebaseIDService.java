package vn.app.android.ordermanager.fcm;

import android.content.SharedPreferences;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import vn.app.android.ordermanager.preference.CachePreference;

/**
 * Created by Mr Vuong on 4/2/2017.
 */
public class FirebaseIDService extends FirebaseInstanceIdService {
    public static final String ORDER_PREFERENCES ="OrderManagerReferent";
    public static final String KEY_DEVICE_TOKEN ="OrderManagerReferent";
    private SharedPreferences sharedpreferences;
    public class MyFirebaseIDService extends FirebaseInstanceIdService {
        @Override
        public void onTokenRefresh() {
            super.onTokenRefresh();
            String token= FirebaseInstanceId.getInstance().getToken();
            CachePreference.putDeviceToken(getBaseContext(),token);
        }

    }
}
