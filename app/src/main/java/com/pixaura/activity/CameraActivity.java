package com.pixaura.activity;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;
import android.hardware.Camera.CameraInfo;

import com.pixaura.R;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Displays live camera preview with overlay
 * Gets SurfaceHolder callbacks from the SurfaceView in layouts/camera.xml
 */
public class CameraActivity extends Activity implements SurfaceHolder.Callback, Camera.ShutterCallback, Camera.PictureCallback {
    Camera _camera;
    SurfaceView _surfaceView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);
        _surfaceView = (SurfaceView)findViewById(R.id.preview);
        _surfaceView.getHolder().addCallback(this);
        // TODO: depricated
        _surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        // TODO: On some devices, this method may take a long time to complete.
        // It is best to call this method from a  worker thread android.os.AsyncTask to avoid
        // blocking the main application UI thread.
        _camera = getCameraInstance(CameraInfo.CAMERA_FACING_FRONT);
        if (_camera == null) {
            _camera = getCameraInstance(CameraInfo.CAMERA_FACING_BACK);
            if (_camera == null) {
                Log.w("Camera", "Camera(s) detected but was not CAMERA_FACING_BACK or CAMERA_FACING_FRONT.");
            }
        }
    }

    private static Camera getCameraInstance(int instance) {
        Camera c = null;
        try {
            // TODO: Check API level 9?
            c = Camera.open(instance);
        }
        catch (Exception e) {
            // TODO: If the same camera is opened by other applications, this will throw a RuntimeException.
            //       Inform user?
            e.printStackTrace();
            Log.w("Camera", "Camera is not available (in use or does not exist).");
        }

        return c;
    }

    @Override
    public void onPause() {
        super.onPause();
        _camera.stopPreview();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        _camera.release();
    }

    public void onCancelClick(View v) {
        finish();
    }

    public void onSnapClick(View v) {
        _camera.takePicture(this, null, null, this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // Wait until surfaceCreated()
        // to attach SurfaceHolder of our SurfaceView to camera
        try {
            _camera.setPreviewDisplay(_surfaceView.getHolder());
        } catch (IOException e) {
            // TODO: No camera?
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Wait until surfaceChanged()
        // to size preview and start drawing
        _camera.setParameters(getCameraParameters());
        //_camera.setDisplayOrientation(90); // TODO: 90 degrees - needed?
        _camera.startPreview();
    }

    private Camera.Parameters getCameraParameters() {
        Camera.Parameters parameters = _camera.getParameters();
        // Get all the device's supported sizes, typically ordered largest-smallest
        List<Camera.Size> sizes = parameters.getSupportedPreviewSizes();
        // and pick the first (largest) preview resolution
        Camera.Size selected = sizes.get(0);
        parameters.setPreviewSize(selected.width, selected.height);

        return parameters;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onShutter() {
        // TODO: Custom sound?
        Toast.makeText(this, "Click!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Called multiple times when different image data is available:
     * 1. data is RAW image (may be null)
     * 2. data is scaled image data (POSTVIEW image) (may be null)
     * 3. JPEG image data
     * @param data RAW image data (TODO: Can be null when memory limited)
     * @param camera
     */
    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        try {

            FileOutputStream out = openFileOutput("picture.jpg", Activity.MODE_PRIVATE);
            out.write(data);
            out.flush();
            out.close();

            // TODO: Store image with an id

            // TODO: Switch to RecordSpotsActivity

        } catch (FileNotFoundException e) {
            // TODO: Internal storage?
            e.printStackTrace();
        }
        catch (IOException e2) {
            // TODO: Internal storage?
            e2.printStackTrace();
        }

        // TODO: Restart preview - probably not, this was from the example code
        //_camera.startPreview();
    }
}
