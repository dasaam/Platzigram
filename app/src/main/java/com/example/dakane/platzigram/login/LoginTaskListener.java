package com.example.dakane.platzigram.login;

/**
 * Created by dakane on 15/02/20.
 */

public interface LoginTaskListener {
    void loginSuccess();
    void loginError(String error);
}
