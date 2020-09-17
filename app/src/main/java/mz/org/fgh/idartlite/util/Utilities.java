package mz.org.fgh.idartlite.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.appcompat.app.AlertDialog;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import mz.org.fgh.idartlite.R;

public class Utilities {

    private static Utilities instance;

    private static MessageDigest digester;

    private Utilities() {
    }

    public static Utilities getInstance(){
        if (instance == null){
            instance = new Utilities();
        }
        return instance;
    }

    static {
        try {
            digester = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String MD5Crypt(String str) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("String to encript cannot be null or zero length");
        }

        digester.update(str.getBytes());
        byte[] hash = digester.digest();
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            if ((0xff & hash[i]) < 0x10) {
                hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
            }
            else {
                hexString.append(Integer.toHexString(0xFF & hash[i]));
            }
        }
        return hexString.toString();
    }

    public static boolean isNetworkAvailable(Context context) {

        boolean isNetAvailable = false;
        if (context != null) {

            final ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (mConnectivityManager != null) {

                boolean mobileNetwork = false;
                boolean wifiNetwork = false;

                boolean mobileNetworkConnected = false;
                boolean wifiNetworkConnected = false;

                final NetworkInfo mobileInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                final NetworkInfo wifiInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                if (mobileInfo != null) {

                    mobileNetwork = mobileInfo.isAvailable();
                }

                if (wifiInfo != null) {

                    wifiNetwork = wifiInfo.isAvailable();
                }

                if (wifiNetwork || mobileNetwork) {

                    if (mobileInfo != null) {

                        mobileNetworkConnected = mobileInfo.isConnectedOrConnecting();
                    }
                    wifiNetworkConnected = wifiInfo.isConnectedOrConnecting();
                }
                isNetAvailable = (mobileNetworkConnected || wifiNetworkConnected);
            }
        }

        return isNetAvailable;
    }

    public static boolean listHasElements(List list) {
        if (list == null) return false;
        return !(list.size() <= 0 || list.isEmpty());
    }

    /**
     * Common AppCompat Alert Dialog to be used in the Application everywhere
     *
     * @param mContext, Context of where to display
     */
    public static void displayAlertDialog(final Context mContext, final String alertMessage) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.StyleAppCompatAlertDialog);
            builder.setTitle(mContext.getResources().getString(R.string.app_name));
            builder.setMessage(alertMessage);
            builder.setPositiveButton(mContext.getResources().getString(R.string.alert_ok), null);
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> List<T> parseToList(T... obj){
        if (obj == null || obj.length == 0) return null;

        List<T> list = new ArrayList<T>();

        for (T o : obj) list.add(o);

        return list;
    }

    public <T extends Object, S extends Object> List<S> parseList(List<T> list, Class<S> classe){
        if (list == null) return null;

        List<S> parsedList = new ArrayList<S>();

        for (T t : list){
            parsedList.add((S) t);
        }

        return parsedList;
    }

    public static boolean stringHasValue(String string){
        return string != null && !string.isEmpty() && string.length() > 0;
    }

}
