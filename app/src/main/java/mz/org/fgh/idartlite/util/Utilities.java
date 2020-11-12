package mz.org.fgh.idartlite.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AlertDialog;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.model.BaseModel;
import mz.org.fgh.idartlite.common.DialogListener;
import mz.org.fgh.idartlite.common.ListbleDialogListener;
import mz.org.fgh.idartlite.model.Prescription;

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

        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        return isConnected;
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
    public static AlertDialog displayAlertDialog(final Context mContext, final String alertMessage, DialogListener listener) {
        return genericDisplayAlertDialog(mContext, alertMessage, listener);
    }

    /**
     * Common AppCompat Alert Dialog to be used in the Application everywhere
     *
     * @param mContext, Context of where to display
     */
    public static AlertDialog displayAlertDialog(final Context mContext, final String alertMessage) {
        return genericDisplayAlertDialog(mContext, alertMessage, null);
    }
    /**
     * Common AppCompat Alert Dialog to be used in the Application everywhere
     *
     * @param mContext, Context of where to display
     */
    private static AlertDialog genericDisplayAlertDialog(final Context mContext, final String alertMessage, DialogListener listener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                .setMessage(alertMessage)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (listener != null) listener.doOnConfirmed();
                        dialog.dismiss();
                    }

                });

        return builder.create();
    }

    private static AlertDialog displayConfirmationDialog(final Context mContext, final String dialogMesg, String positive, String negative, int position, BaseModel baseModel, ListbleDialogListener listener)
        {
            AlertDialog myQuittingDialogBox = new AlertDialog.Builder(mContext)
                    // set message, title, and icon
                    .setTitle(mContext.getResources().getString(R.string.app_name))
                    .setMessage(dialogMesg)

                    .setPositiveButton(positive, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            if (baseModel != null){
                                listener.remove(baseModel);
                            }else {
                                try {
                                    listener.remove(position);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                            dialog.dismiss();
                        }

                    })
                    .setNegativeButton(negative, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();

                        }
                    })
                    .create();

            return myQuittingDialogBox;
        }

    public static AlertDialog displayConfirmationDialog(final Context mContext, final String dialogMesg, String positive, String negative, DialogListener listener)
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(mContext)
                // set message, title, and icon
                .setTitle(mContext.getResources().getString(R.string.app_name))
                .setMessage(dialogMesg)

                .setPositiveButton(positive, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        listener.doOnConfirmed();
                        dialog.dismiss();
                    }

                })
                .setNegativeButton(negative, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        listener.doOnDeny();
                        dialog.dismiss();

                    }
                })
                .create();

        return myQuittingDialogBox;
    }

    public static AlertDialog displayDeleteConfirmationDialogFromList(final Context mContext, final String dialogMesg, int position, ListbleDialogListener listener) {
        return displayConfirmationDialog(mContext, dialogMesg, mContext.getString(R.string.remove), mContext.getString(R.string.cancel), position, null, listener);
    }

    public static AlertDialog displayDeleteConfirmationDialog(final Context mContext, final String dialogMesg,  BaseModel baseModel, ListbleDialogListener listener) {
        return displayConfirmationDialog(mContext, dialogMesg, mContext.getString(R.string.remove), mContext.getString(R.string.cancel), 0, baseModel, listener);
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

    /*@RequiresApi(api = Build.VERSION_CODES.N)
    public static DatePickerDialog showDateDialog(Context context, DatePickerDialog.OnDateSetListener dateSetListener){

        int mYear, mMonth, mDay;

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context);
        dateSetListener.onDateSet();

    }*/

    public static boolean stringHasValue(String string){
        return string != null && !string.isEmpty() && string.trim().length() > 0;
    }

    public static String parseIntToString(int toParse){
        return String.valueOf(toParse);
    }

    public static String parseDoubleToString(double toParse){
        return String.valueOf(toParse);
    }

    public static UUID getNewUUID(){
        return UUID.randomUUID();
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService( Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow( activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static String parseSupplyToLabel(int supply){

       switch (supply){
           case 2:
               return Prescription.DURATION_TWO_WEEKS;
           case 4:
               return Prescription.DURATION_ONE_MONTH;
           case 8:
               return Prescription.DURATION_TWO_MONTHS;
           case 12:
               return Prescription.DURATION_THREE_MONTHS;
           case 16:
               return Prescription.DURATION_FOUR_MONTHS;
           case 20:
               return Prescription.DURATION_FIVE_MONTHS;
           case 24:
               return Prescription.DURATION_SIX_MONTHS;
           default:
               return "";
       }
    }

    public static String garantirXCaracterOnNumber(long number, int x){
        String formatedNumber = "";
        int numberOfCharacterToIncrise = 0;

        formatedNumber = number + "";

        numberOfCharacterToIncrise = x - formatedNumber.length();

        for(int i = 0; i < numberOfCharacterToIncrise; i++) formatedNumber = "0" + formatedNumber;

        return formatedNumber;
    }

    public static String concatStrings(String currentString, String toConcant, String scapeStr){
        if (!stringHasValue(currentString)) return toConcant;

        if (!stringHasValue(toConcant)) return currentString;

        return currentString + scapeStr+ toConcant;
    }

    public static boolean isStringIn(String value, String... inValues){
        if (inValues == null || value == null) return false;

        for (String str : inValues){
            if (value.equals(str)) return true;
        }

        return false;
    }

    public static boolean listHasElements(ArrayList<?> list){
        return list != null && !list.isEmpty() && list.size() > 0;
    }

    public static <T extends BaseModel> T findOnArray(List<T> list, T toFind){
        for (T o : list) {
            if (o.equals(toFind)) return o;
        }
        return null;
    }

    public static void expand(View view) {
        Animation animation = expandAction(view);
        view.startAnimation(animation);
    }

    private static Animation expandAction(final View view) {

        view.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int actualheight = view.getMeasuredHeight();

        view.getLayoutParams().height = 0;
        view.setVisibility(View.VISIBLE);

        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                view.getLayoutParams().height = interpolatedTime == 1 ? ViewGroup.LayoutParams.WRAP_CONTENT : (int) (actualheight * interpolatedTime);
                view.requestLayout();
            }
        };

        animation.setDuration((long) (actualheight / view.getContext().getResources().getDisplayMetrics().density));
        view.startAnimation(animation);

        return animation;


    }

    public static void collapse(final View view) {

        final int actualHeight = view.getMeasuredHeight();

        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {

                if (interpolatedTime == 1) {
                    view.setVisibility(View.GONE);
                } else {
                    view.getLayoutParams().height = actualHeight - (int) (actualHeight * interpolatedTime);
                    view.requestLayout();

                }
            }
        };

        animation.setDuration((long) (actualHeight/ view.getContext().getResources().getDisplayMetrics().density));
        view.startAnimation(animation);
    }
    /*public static NotificationCompat.Builder showNotification(String title, String contntText, Context context, String channelId){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(contntText);
        return mBuilder;
    }*/
}
