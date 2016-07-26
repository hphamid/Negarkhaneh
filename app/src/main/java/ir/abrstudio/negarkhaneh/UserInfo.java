package ir.abrstudio.negarkhaneh;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;

import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import ir.abrstudio.negarkhaneh.data.UserResponse;


/**
 * Created by hamid on 6/7/15.
 *
 */
public class UserInfo {
    public final static String USER_INFO_SHARED_PREF = "userinfo";
    public final static String USER_INFO_KEY = "userkey";

    private static UserInfo _instane;

    private final static Object syncObject = new Object();

    private String userId = null;

    private boolean lock = false;

    private Set<UserCallback> callbacks;

    private UserInfo(){
        callbacks = new HashSet<>();
    }

    public static UserInfo getInstance(){
        if(_instane == null){
            _instane = new UserInfo();
        }
        return _instane;
    }

    private void updateUserIdFromServer(final Context context){
        if(lock){
            return;
        }
        lock = true;
        Request request = new Request.Builder().url(NetworkHelper.getInstance()
                .getAddressHelper().getUserAddress(getIMEI(context))).build();
        NetworkHelper.getInstance().getClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
                setResult("", context);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) {
                    setResult("", context);
                    return;
                }
                Gson gson = new Gson();
                UserResponse userResponse = gson.fromJson(response.body().charStream(),
                        UserResponse.class);
                if (userResponse == null) {
                    setResult("", context);
                    return;
                }
                setResult(userResponse.getId(), context);
                lock = false;
            }
        });
    }

    public void getUserId(final Context context, final UserCallback callback){
        if(callback == null){
            return;
        }
        synchronized (syncObject){
            if(this.getSavedUserId(context) != null){
                callback.done(this.getSavedUserId(context));
            }else {
                addToWaitingList(callback);
                updateUserIdFromServer(context);
            }
        }
    }

    private void addToWaitingList(UserCallback callback){
        callbacks.add(callback);
    }

    private void setResult(String userId, Context context){
        synchronized (syncObject){
            if(userId != null && !userId.equals("")){
                saveUserIdToSharedPref(context, userId);
            }
            for(UserCallback callback: callbacks){
                callback.done(userId);
            }
            callbacks.clear();
            lock = false;
        }
    }

    public static String getIMEI(Context context){
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

    public String getUserIdFromSharedPref(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(USER_INFO_SHARED_PREF,
                Context.MODE_PRIVATE);
        return sharedPref.getString(USER_INFO_KEY, null);
    }

    public String getSavedUserId(Context context){
        if(this.userId == null){
            this.userId = getUserIdFromSharedPref(context);
        }
        return this.userId;
    }

    public void saveUserIdToSharedPref(Context context, String userId){
        this.userId = userId;
        SharedPreferences sharedPref = context.getSharedPreferences(USER_INFO_SHARED_PREF,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_INFO_KEY, userId);
        editor.apply();
    }
    public interface UserCallback {
        public void done(String userid);
    }
}
