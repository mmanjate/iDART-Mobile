package mz.org.fgh.idartlite.util;

import android.app.Activity;
import android.view.LayoutInflater;

import androidx.appcompat.app.AlertDialog;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.activity.BaseActivity;

public class LoadingDialog {
    private Activity activity;
    private AlertDialog alertDialog;

    public LoadingDialog(BaseActivity myactivity)
    {
        activity= myactivity;
    }

    public void startLoadingDialog()
    {
        if (alertDialog != null && alertDialog.isShowing()) return;

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.custom_dialog_loading,null));
        builder.setCancelable(false);


        alertDialog = builder.create();
        alertDialog.show();
    }


    public void dismisDialog()
    {
        alertDialog.dismiss();
    }
}
