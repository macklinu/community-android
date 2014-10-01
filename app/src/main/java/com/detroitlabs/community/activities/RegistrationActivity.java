package com.detroitlabs.community.activities;

import android.app.Activity;
import android.widget.EditText;

import com.detroitlabs.community.R;
import com.detroitlabs.community.api.RestApi;
import com.detroitlabs.community.api.RestCallback;
import com.detroitlabs.community.exceptions.RegistrationValidationException;
import com.detroitlabs.community.model.User;
import com.detroitlabs.community.prefs.AppPrefs;
import com.detroitlabs.community.utils.SnoopLogg;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import static android.util.Patterns.EMAIL_ADDRESS;
import static com.detroitlabs.community.utils.Dialogger.showWebRequestErrorDialog;

@EActivity(R.layout.activity_registration)
public class RegistrationActivity extends Activity implements RestCallback<User> {

    @Bean
    AppPrefs appPrefs;

    @Bean
    RestApi api;

    @ViewById
    EditText name;

    @ViewById
    EditText username;

    @ViewById
    EditText password;

    @AfterInject
    void afterInject() {
        if (isSignedIn()) {
            goToNavigationActivity();
            finish();
        }
    }

    private boolean isSignedIn() {
        return true;
        // TODO replace this method with the following code
        // appPrefs.isUserSignedIn()
    }

    @Click
    void register() {
        try {
            final User user = new User.Builder()
                    .name(validateName())
                    .username(validateUsername())
                    .password(validatePassword())
                    .build();

            api.register(user, this);

        } catch (RegistrationValidationException e) {
            e.getEditText().setError(e.getMessage());
        }
    }

    @Override
    public void onSuccess(User user) {
        goToNavigationActivity();
    }

    @Override
    public void onFailure(Exception e) {
        SnoopLogg.e(e);
        showWebRequestErrorDialog(this);
    }

    private String validateName() throws RegistrationValidationException {
        final String nameText = name.getText().toString();
        if (nameText != null && !nameText.isEmpty()) {
            return nameText;
        }
        throw new RegistrationValidationException(name, "Name must not be empty");
    }


    private String validateUsername() throws RegistrationValidationException {
        final String usernameText = username.getText().toString();
        if (usernameText != null && usernameText.matches(EMAIL_ADDRESS.pattern())) {
            return usernameText;
        }
        throw new RegistrationValidationException(username, "Must be a valid email address");
    }

    private String validatePassword() throws RegistrationValidationException {
        final String passwordText = password.getText().toString();
        if (passwordText != null && passwordText.length() >= 6) {
            return passwordText;
        }
        throw new RegistrationValidationException(password, "Password must be at least 6 characters");
    }

    private void goToNavigationActivity() {
        NavigationActivity_.
                intent(this)
                .start();
    }
}
