package com.detroitlabs.community.activities;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.detroitlabs.community.R;
import com.detroitlabs.community.api.RestApi;
import com.detroitlabs.community.api.RestCallback;
import com.detroitlabs.community.exceptions.RegistrationValidationException;
import com.detroitlabs.community.model.User;
import com.detroitlabs.community.prefs.AppPrefs;
import com.detroitlabs.community.utils.SnoopLogg;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;

import static android.util.Patterns.EMAIL_ADDRESS;
import static com.detroitlabs.community.utils.Dialogger.showInvalidLoginDialog;
import static com.detroitlabs.community.utils.Dialogger.showWebRequestErrorDialog;

@EActivity(R.layout.activity_registration)
public class RegistrationActivity extends Activity implements RestCallback<User>, View.OnClickListener {

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

    @ViewById
    Button login;

    @ViewById
    Button register;

    @AfterInject
    void afterInject() {
        if (appPrefs.isUserSignedIn()) {
            goToNavigationActivity();
            finish();
        }
    }

    @AfterViews
    void afterViews() {
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final User.Builder builder = new User.Builder();
        if (v.getId() == R.id.login) {
            try {
                builder.username(validateUsername())
                        .password(validatePassword());
                api.login(builder.build(), this);
            } catch (RegistrationValidationException e) {
                handleValidationException(e);
            }
        } else if (v.getId() == R.id.register) {
            try {
                builder.username(validateUsername())
                        .password(validatePassword())
                        .name(validateName());
                api.register(builder.build(), this);
            } catch (RegistrationValidationException e) {
                handleValidationException(e);
            }
        }
    }

    @Override
    public void onSuccess(User user) {
        goToNavigationActivity();
    }

    @Override
    public void onFailure(RestClientException e) {
        SnoopLogg.e(e);
        if (e instanceof HttpStatusCodeException) {
            if (((HttpStatusCodeException) e).getStatusCode().value() == 401) {
                showInvalidLoginDialog(this);
                return;
            }
        }
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

    private void handleValidationException(RegistrationValidationException e) {
        e.getEditText().setError(e.getMessage());
    }
}
