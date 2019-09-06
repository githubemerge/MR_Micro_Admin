package emerge.project.mrsolution_admin_micro.ui.activity.login;


import android.content.Context;

/**
 * Created by Himanshu on 4/4/2017.
 */

public interface LoginPresenter {
    void checkLogin(Context context, String userName, String password,boolean rememberMe);
    void checkIsEligiblUseFingerPrint(Context context);




}
