package emerge.project.mrsolution_admin_micro.ui.activity.login;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.multidots.fingerprintauth.FingerPrintAuthCallback;
import com.multidots.fingerprintauth.FingerPrintAuthHelper;
import com.pddstudio.preferences.encrypted.EncryptedPreferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import emerge.project.mrsolution_admin_micro.R;
import emerge.project.mrsolution_admin_micro.ui.activity.visits.VisitsActivity;

public class LoginActivity extends Activity implements FingerPrintAuthCallback, LoginView {


    private FingerPrintAuthHelper mFingerPrintAuthHelper;

    @BindView(R.id.editText_username)
    EditText editTextUsername;

    @BindView(R.id.editText_password)
    EditText editTexPassword;

    @BindView(R.id.include_progres)
    View includeProgres;

    @BindView(R.id.include_nointernt)
    View includeNointernt;


    @BindView(R.id.checkBox_rememberme)
    CheckBox checkBoxRememberme;

    LoginPresenter loginPresenter;


    EncryptedPreferences encryptedPreferences;
    private static final String USER_ID = "userID";


    boolean isRemembermeActive=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        encryptedPreferences = new EncryptedPreferences.Builder(this).withEncryptionPassword("122547895511").build();

        loginPresenter = new LoginPresenterImpli(this);

        if (isFingerprintAvailable(this)) {
            mFingerPrintAuthHelper = FingerPrintAuthHelper.getHelper(this, this);
        } else {

        }


    }

    @OnClick(R.id.checkBox_rememberme)
    public void onClickCheckBox(View view) {
        if (checkBoxRememberme.isChecked()) {
            isRemembermeActive=true;
        }else {
            isRemembermeActive=false;
        }

    }

    @OnClick(R.id.btn_tryagin)
    public void onTryAgian(View view) {
        includeNointernt.setVisibility(View.GONE);
    }


    @OnClick(R.id.btn_login)
    public void onClickLogin(View view) {

        /*encryptedPreferences.edit().putInt(USER_ID,6).apply();

        Intent intentSingup = new Intent(this, VisitsActivity.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        finish();
        startActivity(intentSingup, bndlanimation);*/

        bloackUserInteraction();
       includeProgres.setVisibility(View.VISIBLE);
       loginPresenter.checkLogin(LoginActivity.this,editTextUsername.getText().toString(),editTexPassword.getText().toString(),isRemembermeActive);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isFingerprintAvailable(this)) {
            mFingerPrintAuthHelper.startAuth();
        } else {
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFingerprintAvailable(this)) {
            mFingerPrintAuthHelper.stopAuth();
        } else {
        }
    }

    @Override
    public void onNoFingerPrintHardwareFound() {

    }

    @Override
    public void onNoFingerPrintRegistered() {
        Toast.makeText(this, "No fingerprint configured. Please register at least one fingerprint in your device's Settings", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBelowMarshmallow() {

    }

    @Override
    public void onAuthSuccess(FingerprintManager.CryptoObject cryptoObject) {
        loginPresenter.checkIsEligiblUseFingerPrint(this);
    }

    @Override
    public void onAuthFailed(int errorCode, String errorMessage) {
       // Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show();
    }

    public static boolean isFingerprintAvailable(Context context) {
        FingerprintManagerCompat fingerprintManager = FingerprintManagerCompat.from(context);
        return fingerprintManager.hasEnrolledFingerprints();
    }

    @Override
    public void userNameEmpty() {
        includeProgres.setVisibility(View.GONE);
        unBloackUserInteraction();
        editTextUsername.setError("Empty");
        Toast.makeText(this, "User Name is empty", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void passwordEmpty() {
        includeProgres.setVisibility(View.GONE);
        unBloackUserInteraction();
        editTexPassword.setError("Empty");
        Toast.makeText(this, "Password is empty", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginSuccessful() {
        includeProgres.setVisibility(View.GONE);
        unBloackUserInteraction();

        Intent intentSingup = new Intent(this, VisitsActivity.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        finish();
        startActivity(intentSingup, bndlanimation);
    }

    @Override
    public void loginFail(String msg) {
        try {
            includeProgres.setVisibility(View.GONE);
            unBloackUserInteraction();
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Warning");
            alertDialogBuilder.setMessage(msg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            includeProgres.setVisibility(View.VISIBLE);
                            bloackUserInteraction();
                            loginPresenter.checkLogin(LoginActivity.this,editTextUsername.getText().toString(),editTexPassword.getText().toString(),isRemembermeActive);
                        }
                    });
            alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });
            alertDialogBuilder.show();
        } catch (WindowManager.BadTokenException e) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void loginError(String msg) {
        try {
            includeProgres.setVisibility(View.GONE);
            unBloackUserInteraction();
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage(msg);
            alertDialogBuilder.setPositiveButton("Re-Try",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });
            alertDialogBuilder.show();
        } catch (WindowManager.BadTokenException e) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    @Override
    public void loginNetworkFail() {
        unBloackUserInteraction();
        includeProgres.setVisibility(View.GONE);
        includeNointernt.setVisibility(View.VISIBLE);
    }

    @Override
    public void eligiblToUse() {
        Intent intentSingup = new Intent(this, VisitsActivity.class);
        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.fade_out).toBundle();
        finish();
        startActivity(intentSingup, bndlanimation);
    }

    @Override
    public void notEligiblToUse(String msg) {
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage(msg);
            alertDialogBuilder.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });
            alertDialogBuilder.show();
        } catch (WindowManager.BadTokenException e) {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {

        if (includeNointernt.getVisibility() == View.VISIBLE) {
            includeNointernt.setVisibility(View.GONE);
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Exit!");
            alertDialogBuilder.setMessage("Do you really want to exit ?");
            alertDialogBuilder.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });

            alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });
            alertDialogBuilder.show();
        }

    }


    private void bloackUserInteraction() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void unBloackUserInteraction() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }


}
