package com.example.dakane.platzigram.login;

/**
 * Created by dakane on 16/02/20.
 */

public class LoginInteratorImpl implements LoginInteractor {
    LoginRepository loginRepository;

    public LoginInteratorImpl(LoginTaskListener listener) {
        loginRepository = new LoginRepositoryImpl(listener);
    }

    @Override
    public void doSignIn(String email, String password) {
        loginRepository.signIn(email, password);
    }
}
