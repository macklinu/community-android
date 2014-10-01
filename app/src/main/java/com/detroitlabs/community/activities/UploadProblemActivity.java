package com.detroitlabs.community.activities;

import android.app.Activity;
import android.widget.EditText;
import android.widget.ImageView;

import com.detroitlabs.community.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import static android.content.pm.PackageManager.FEATURE_CAMERA;

@EActivity(R.layout.activity_upload_problem)
public class UploadProblemActivity extends Activity {

    @ViewById
    ImageView image;

    @ViewById
    EditText description;

    @AfterViews
    void afterViews() {
        if (hasCamera()) {
            // TODO show image view
        } else {
            // TODO hide image view
        }
    }

    @Click
    void image() {

    }

    @Click
    void submit() {

    }

    private boolean hasCamera() {
        return getPackageManager().hasSystemFeature(FEATURE_CAMERA);
    }

}
