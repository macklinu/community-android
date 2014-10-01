package com.detroitlabs.community.exceptions;

import android.widget.EditText;

public class RegistrationValidationException extends Exception {
    private final EditText editText;
    public RegistrationValidationException(EditText editText, String detailMessage) {
        super(detailMessage);
        this.editText = editText;
    }

    public EditText getEditText() {
        return editText;
    }
}
