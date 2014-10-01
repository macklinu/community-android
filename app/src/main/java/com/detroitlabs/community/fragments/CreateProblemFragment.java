package com.detroitlabs.community.fragments;

import android.app.Fragment;
import android.net.Uri;
import android.widget.EditText;
import android.widget.ImageView;

import com.detroitlabs.community.R;
import com.detroitlabs.community.utils.CaptureRequest;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import java.io.File;

import static android.app.Activity.RESULT_OK;
import static android.content.pm.PackageManager.FEATURE_CAMERA;
import static android.os.Environment.DIRECTORY_DCIM;
import static android.os.Environment.getExternalStoragePublicDirectory;

@EFragment(R.layout.fragment_create_problem)
public class CreateProblemFragment extends Fragment {

    private static final int RESULT_CODE_CAPTURE = 0x01;

    @ViewById
    ImageView image;

    @ViewById
    EditText description;

    private CaptureRequest captureRequest;

    @AfterViews
    void afterViews() {
        final File storageDirectory = getExternalStoragePublicDirectory(DIRECTORY_DCIM);
        captureRequest = new CaptureRequest(getActivity(), storageDirectory, null);
    }

    @Click
    void image() {
        if (hasCamera()) {
            startActivityForResult(captureRequest.getCaptureIntent(), RESULT_CODE_CAPTURE);
        } else {
            // TODO load from gallery
        }
    }

    @Click
    void submit() {
        // TODO post to API
    }

    @OnActivityResult(RESULT_CODE_CAPTURE)
    void onCaptureResult(int resultCode) {
        if (resultCode == RESULT_OK) {
            final Uri fileUri = captureRequest.getFileUri();
            if (fileUri != null) {
                image.setImageURI(fileUri);
                image.setBackground(null);
            }
        }
    }

    private boolean hasCamera() {
        return getActivity().getPackageManager().hasSystemFeature(FEATURE_CAMERA);
    }

}
