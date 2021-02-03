package mz.org.fgh.idartlite.view.splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import mz.org.fgh.idartlite.R;
import mz.org.fgh.idartlite.base.activity.BaseActivity;
import mz.org.fgh.idartlite.base.viewModel.BaseViewModel;
import mz.org.fgh.idartlite.rest.helper.RESTServiceHandler;
import mz.org.fgh.idartlite.util.Utilities;
import mz.org.fgh.idartlite.viewmodel.splash.SplashVM;

public class SplashActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (!getRelatedViewModel().isCentralServerConfigured()) {
            getRelatedViewModel().requestConfiguration();
        }else syncApp();
    }

    public void syncApp(){
        getRelatedViewModel().syncApp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        finish();
    }

    @Override
    public BaseViewModel initViewModel() {
        return new ViewModelProvider(this).get(SplashVM.class);
    }

    @Override
    public SplashVM getRelatedViewModel() {
        return (SplashVM) super.getRelatedViewModel();
    }

    public void requestCentralServerSettings(){
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        dialogBuilder.setCanceledOnTouchOutside(false);

        final EditText editText = (EditText) dialogView.findViewById(R.id.edt_comment);
        Button button1 = (Button) dialogView.findViewById(R.id.buttonSubmit);
        Button button2 = (Button) dialogView.findViewById(R.id.buttonCancel);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRelatedViewModel().exitApp();
                dialogBuilder.dismiss();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utilities.stringHasValue(editText.getText().toString())) {
                    if (RESTServiceHandler.getServerStatus(editText.getText().toString())) {
                        getRelatedViewModel().saveSettings(editText.getText().toString());

                        dialogBuilder.dismiss();
                    }else {
                        Utilities.displayAlertDialog(SplashActivity.this, "A aplicação não conseguiu ligar-se ao servidor central, por favor verifique a URL informada ou a configuração de rede do dispositivo.").show();
                    }
                }else {
                    Utilities.displayAlertDialog(SplashActivity.this, "Por favor indicar a URL do servidor central.").show();
                }
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }
}