package com.dualcnhq.apppermissions;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by dualcnhq on 10/16/15.
 */
public class SendMessageActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mNumberText;
    private EditText mMessageText;

    private static final String TAG = SendMessageActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        mNumberText = (EditText) findViewById(R.id.et_number);
        mMessageText = (EditText) findViewById(R.id.et_message);
        Button mSendMessageButton = (Button) findViewById(R.id.btn_send);
        mSendMessageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        sendMessage();
    }

    private void sendMessage() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) ==
                PackageManager.PERMISSION_GRANTED) {
            String number = mNumberText.getText().toString();
            String message = mMessageText.getText().toString();
            SmsManager.getDefault().sendTextMessage(number, null, message, null, null);
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (permissions.length > 0) {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    String number = mNumberText.getText().toString();
                    String message = mMessageText.getText().toString();

                    Log.d(TAG, "number: " + number);
                    Log.d(TAG, "message: " + message);

                    SmsManager.getDefault().sendTextMessage(number, null, message, null, null);
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

}
