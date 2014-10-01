package com.detroitlabs.community.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;
import java.io.IOException;

import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;
import static android.provider.MediaStore.EXTRA_OUTPUT;

public class CaptureRequest {

    /**
     * request code for a camera capture intent
     */
    public static final int CAPTURE_REQUEST_CODE = 0x54A;

    protected final Context context;
    protected final File directory;
    protected final String fileName;
    protected Uri fileUri = null;

    /**
     * Holds data used to make a capture intent for the Android camera. Recommend using {@code startActivityForResult} with request code
     * {@code CaptureRequest.CAPTURE_REQUEST_CODE} to launch.
     *
     * @param context   a valid context
     * @param directory a directory to save the photo to
     * @param fileName  A file name for the photo. If {@code fileName} is {@code null} or of length 0, the current time
     *                  in milliseconds is used as the {@code fileName}.
     */
    public CaptureRequest(Context context, File directory, String fileName) {
        this.context = context;
        this.directory = directory;

        if (fileName == null || fileName.length() == 0) {
            this.fileName = String.valueOf(System.currentTimeMillis());
        } else {
            this.fileName = fileName;
        }
    }

    /**
     * Get an intent used to capture a photo using the android camera.
     *
     * @return the intent to capture a photo, or null if the photo cannot be created or the camera is not available
     */
    public Intent getCaptureIntent() {
        Intent captureIntent = new Intent(ACTION_IMAGE_CAPTURE);

        File placeholderFile = createTempFile();
        if (placeholderFile != null && captureIntent.resolveActivity(context.getPackageManager()) != null) {
            fileUri = Uri.fromFile(placeholderFile);
            captureIntent.putExtra(EXTRA_OUTPUT, fileUri);
            return captureIntent;
        } else {
            return null;
        }
    }

    public Uri getFileUri() {
        return fileUri;
    }

    private File createTempFile() {
        try {
            File file = new File(directory, fileName + ".jpg");
            file.createNewFile();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
