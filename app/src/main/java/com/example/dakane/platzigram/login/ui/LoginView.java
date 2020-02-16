package com.example.dakane.platzigram.login.ui;

/**
 * Created by dakane on 15/02/20.
 */

public interface LoginView {
    void enableInputs();
    void disableInputs();
    void showprogressBar();
    void hideprogressBar();

    void handleSignIn();
    void navigateToMainScreen();
    void navigateToCreateAccount();
    void loginError(String erro);
}
