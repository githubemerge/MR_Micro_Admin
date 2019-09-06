package emerge.project.mrsolution_admin_micro.ui.activity.login;




/**
 * Created by Himanshu on 4/4/2017.
 */

public interface LoginView {

        void userNameEmpty();
        void passwordEmpty();
        void loginSuccessful();
        void loginFail(String msg);
        void loginError(String msg);
        void loginNetworkFail();


        void eligiblToUse();
        void notEligiblToUse(String msg);





}
