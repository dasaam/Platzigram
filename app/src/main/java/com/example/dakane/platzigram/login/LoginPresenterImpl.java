package com.example.dakane.platzigram.login;

import com.example.dakane.platzigram.login.ui.LoginView;

/**
 * Created by dakane on 16/02/20.
 */

public class LoginPresenterImpl implements LoginPresenter, LoginTaskListener {
    LoginView loginView;
    LoginInteractor loginInteractor;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        this.loginInteractor = new LoginInteratorImpl(this);
    }

    @Override
    public void onDestroy() {
        loginView = null;
    }

    @Override
    public void validateLogin(String email, String password) {
        if (loginView != null){
            loginView.disableInputs();
            loginView.showprogressBar();
        }
        loginInteractor.doSignIn(email, password);
    }

    @Override
    public void loginSuccess() {
        if (loginView != null){
            loginView.navigateToMainScreen();

        }
    }

    @Override
    public void loginError(String error) {
        loginView.loginError(error);
    }
}
