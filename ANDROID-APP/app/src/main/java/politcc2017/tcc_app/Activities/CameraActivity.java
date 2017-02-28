package politcc2017.tcc_app.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import politcc2017.tcc_app.Components.CustomTextView;
import politcc2017.tcc_app.Components.Helpers.DialogHelper;
import politcc2017.tcc_app.OCRLib.Config;
import politcc2017.tcc_app.OCRLib.EasyOcrScanner;
import politcc2017.tcc_app.OCRLib.EasyOcrScannerListener;
import politcc2017.tcc_app.R;

/**
 * Created by Jonatas on 27/02/2017.
 */

public class CameraActivity extends BaseActivity implements EasyOcrScannerListener {

    EasyOcrScanner mEasyOcrScanner;
    CustomTextView textView;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        textView = (CustomTextView) findViewById(R.id.camera_activity_scanned_text);
        // initialize EasyOcrScanner instance.
        checkPermissions();
        mEasyOcrScanner = new EasyOcrScanner(CameraActivity.this, "EasyOcrScanner",
                Config.REQUEST_CODE_CAPTURE_IMAGE, "eng");

        // Set ocrScannerListener
        mEasyOcrScanner.setOcrScannerListener(this);
        setActivityTitle("Camera");
        DialogHelper.CustomDialog(CameraActivity.this, "Camera - Escanear textos", "Tire uma foto do texto que deseja escanear. Preferencialmente aproxime a câmera de forma a tornar o texto claramente identificável", "OK", "Cancelar").show();

        findViewById(R.id.camera_activity_scan_button).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mEasyOcrScanner.takePicture();
            }
        });

    }

    private void checkPermissions(){
        if (Build.VERSION.SDK_INT >= 23) {
            boolean needWriteExtStorage = checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED;
            boolean needCamera = checkSelfPermission(android.Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED;
            if (needWriteExtStorage || needCamera){
                String[] permissions = new String[((needWriteExtStorage)?(1):(0)) + ((needCamera)?(1):(0))];
                int idx = 0;
                if (needWriteExtStorage){
                    permissions[idx] = android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
                    idx++;
                }
                if (needCamera){
                    permissions[idx] = android.Manifest.permission.CAMERA;
                    idx++;
                }
                ActivityCompat.requestPermissions(
                        this, permissions, 1);
            }
        } else {
            //permission is automatically granted on sdk<23 upon installation
            //Log.e("testing", "Permission is already granted");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Call onImageTaken() in onActivityResult.
        if (resultCode == RESULT_OK && requestCode == Config.REQUEST_CODE_CAPTURE_IMAGE){
            mEasyOcrScanner.onImageTaken();
        }
    }

    /**
     * Callback when after taking picture, scanning process starts.
     * Good place to show a progress dialog.
     * @param filePath file path of the image file being processed.
     */
    @Override
    public void onOcrScanStarted(String filePath) {
        mProgressDialog = new ProgressDialog(CameraActivity.this);
        mProgressDialog.setMessage("Scanning...");
        mProgressDialog.show();
    }

    /**
     * Callback when scanning is finished.
     * Good place to hide teh progress dialog.
     * @param bitmap Bitmap of image that was scanned.
     * @param recognizedText Scanned text.
     */
    @Override
    public void onOcrScanFinished(Bitmap bitmap, String recognizedText) {
        textView.setText(recognizedText);
        if (mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }

}
