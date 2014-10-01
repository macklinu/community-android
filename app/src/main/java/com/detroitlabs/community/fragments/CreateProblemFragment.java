package com.detroitlabs.community.fragments;

import android.app.Fragment;
import android.location.Location;
import android.net.Uri;
import android.widget.EditText;
import android.widget.ImageView;

import com.detroitlabs.community.R;
import com.detroitlabs.community.api.RestApi;
import com.detroitlabs.community.api.RestCallback;
import com.detroitlabs.community.managers.LocationManager;
import com.detroitlabs.community.model.Problem;
import com.detroitlabs.community.prefs.AppPrefs;
import com.detroitlabs.community.utils.CaptureRequest;
import com.detroitlabs.community.utils.SnoopLogg;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import java.io.File;

import static android.app.Activity.RESULT_OK;
import static android.content.pm.PackageManager.FEATURE_CAMERA;
import static android.os.Environment.DIRECTORY_DCIM;
import static android.os.Environment.getExternalStoragePublicDirectory;
import static android.view.View.GONE;
import static com.detroitlabs.community.utils.Dialogger.showWebRequestErrorDialog;

@EFragment(R.layout.fragment_create_problem)
public class CreateProblemFragment extends Fragment implements RestCallback<Problem> {

    private static final int RESULT_CODE_CAPTURE = 0x01;

    @Bean
    RestApi api;

    @Bean
    AppPrefs appPrefs;

    @Bean
    LocationManager locationManager;

    @ViewById
    ImageView image;

    @ViewById
    EditText description;

    private CaptureRequest captureRequest;

    @AfterViews
    void afterViews() {
        final File storageDirectory = getExternalStoragePublicDirectory(DIRECTORY_DCIM);
        captureRequest = new CaptureRequest(getActivity(), storageDirectory, null);
        image.setVisibility(GONE);
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
        final Location location = locationManager.getLocation();
        final Problem.Builder builder = new Problem.Builder(1);
        if (location != null) {
            builder.lat(location.getLatitude())
                    .lng(location.getLongitude());
        }
        builder.description(description.getText().toString());
        api.addProblem(builder.build(), this);
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

    @Override
    public void onSuccess(Problem response) {
        // TODO handle successful problem creation
    }

    @Override
    public void onFailure(Exception e) {
        SnoopLogg.e(e);
        showWebRequestErrorDialog(getActivity());
    }

    private boolean hasCamera() {
        return getActivity().getPackageManager().hasSystemFeature(FEATURE_CAMERA);
    }
}
