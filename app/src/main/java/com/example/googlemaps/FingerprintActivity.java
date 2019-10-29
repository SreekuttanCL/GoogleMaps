package com.example.googlemaps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import androidx.biometric.BiometricPrompt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FingerprintActivity extends AppCompatActivity {

    private final String TAG = "FIngerPrint Error"; //FingerprintActivity.class.getString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint);

        //create a thread pool with single thread
        Executor newExecutor = Executors.newSingleThreadExecutor();

        FragmentActivity activity = this;

        //start listening authentication events
        final BiometricPrompt myBiometricPrompt = new BiometricPrompt(activity,
                newExecutor,
                new BiometricPrompt.AuthenticationCallback() {

                    //onAuthentication Error is called when a fatal error occur
                    @Override
                    public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);

                        if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {


                        }
                        else {
                            Log.d(TAG, "An unrecoverable error occured");
                        }
                    }

                    //OnAuthenticationSucceedded is Called

                    @Override
                    public void OonAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);

                        Log.d(TAG,"Fingerprint recognized sucessfully");
                    }

                    //onAuthenticationFFailed is called when fingerprint doesn't match

                    @Override
                    public void onAuthenticationFailed(){
                        super.onAuthenticationFailed();
                        Log.d(TAG,"Fingerprint not recognized");
                    }
                });

        //create our biometric prompt
        final BiometricPrompt.PromptInfo promptInfo = new
                BiometricPrompt.PromptInfo.Builder()
                //adding text to the biometric
                .setTitle("Title text")
                .setSubtitle("Sibtitile text")
                .setDescription("Description text")
                .setNegativeButtonText("Cancel")
                //build the dialog
                .build();
        //assign onClickListener to the app "authentication button"
        findViewById(R.id.launchAuthentication).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBiometricPrompt.authenticate(promptInfo);
            }
        });

    }

    public void onSucess() {
        this.startActivity(new Intent(this, MapsActivity.class));
    }

}
