package com.bramblellc.myapplication.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bramblellc.myapplication.R;
import com.bramblellc.myapplication.fragments.LoadingBar;


public class SignUp extends Activity {

    private EditText desiredUsernameEditText;
    private EditText desiredPasswordEditText;
    private EditText phoneNumberEditText;
    private Button continueButton;

    private FragmentManager fm;
    private FragmentTransaction ft;
    private LoadingBar loadingBar;

    private String username;
    private String password;
    private String phoneNumber;

    //private SignUpCredentialsReceiver signUpCredentialsReceiver;

    private CharSequence[] countryCodeArray;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_layout);

        fm = getFragmentManager();
        loadingBar = new LoadingBar();

        desiredUsernameEditText = (EditText) findViewById(R.id.editTextDesiredUsername);
        desiredPasswordEditText = (EditText) findViewById(R.id.editTextDesiredPassword);
        phoneNumberEditText = (EditText) findViewById(R.id.editTextPhoneNumber);
        continueButton = (Button) findViewById(R.id.buttonSignUp);

        phoneNumberEditText.addTextChangedListener(new TextWatcher() {

            private boolean isFormatting;
            private boolean deletingHyphen;
            private int hyphenStart;
            private boolean deletingBackward;

            @Override
            public void afterTextChanged(Editable text) {
                if (isFormatting)
                    return;

                isFormatting = true;

                // If deleting hyphen, also delete character before or after it
                if (deletingHyphen && hyphenStart > 0) {
                    if (deletingBackward) {
                        if (hyphenStart - 1 < text.length()) {
                            text.delete(hyphenStart - 1, hyphenStart);
                        }
                    } else if (hyphenStart < text.length()) {
                        text.delete(hyphenStart, hyphenStart + 1);
                    }
                }
                if (text.length() == 3 || text.length() == 7) {
                    text.append('-');
                }

                isFormatting = false;
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (isFormatting)
                    return;

                // Make sure user is deleting one char, without a selection
                final int selStart = Selection.getSelectionStart(s);
                final int selEnd = Selection.getSelectionEnd(s);
                if (s.length() > 1 // Can delete another character
                        && count == 1 // Deleting only one character
                        && after == 0 // Deleting
                        && s.charAt(start) == '-' // a hyphen
                        && selStart == selEnd) { // no selection
                    deletingHyphen = true;
                    hyphenStart = start;
                    // Check if the user is deleting forward or backward
                    if (selStart == start + 1) {
                        deletingBackward = true;
                    } else {
                        deletingBackward = false;
                    }
                } else {
                    deletingHyphen = false;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        phoneNumberEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    SignUp();
                }
                return false;
            }
        });

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //signUpCredentialsReceiver = new SignUpCredentialsReceiver();
        //IntentFilter filter = new IntentFilter(ActionConstants.VALIDATION_ACTION);
        //LocalBroadcastManager.getInstance(this).registerReceiver(signUpCredentialsReceiver, filter);
    }

    public void disableButtons() {
        continueButton.setEnabled(false);
        desiredUsernameEditText.setEnabled(false);
        desiredPasswordEditText.setEnabled(false);
        phoneNumberEditText.setEnabled(false);
    }

    public void enableButtons() {
        continueButton.setEnabled(true);
        desiredUsernameEditText.setEnabled(true);
        desiredPasswordEditText.setEnabled(true);
        phoneNumberEditText.setEnabled(true);
    }

    // when the continue button is pressed (sign up)
    public void continueSignUpPressed(View v) {
        SignUp();
    }

    public void SignUp() {

        // native checks on inputs gathered
        if (username.equals("")) {
            Toast.makeText(this, getResources().getString(R.string.sign_up_credentials_empty_username_error), Toast.LENGTH_SHORT).show();
        }
        else if (password.equals("")) {
            Toast.makeText(this, getResources().getString(R.string.sign_up_credentials_empty_password_error), Toast.LENGTH_SHORT).show();
        }
        else if(phoneNumber.equals("")) {
            Toast.makeText(this, getResources().getString(R.string.sign_up_credentials_empty_phone_number_error), Toast.LENGTH_SHORT).show();
        }
        else if(!username.matches("^[a-zA-Z0-9_]+$")) {
            Toast.makeText(this, getResources().getString(R.string.sign_up_credentials_invalid_username_error), Toast.LENGTH_SHORT).show();
        }
        else if(!password.matches("^[a-zA-Z0-9_\\-!@#$%^&*]+$")) {
            Toast.makeText(this,  getResources().getString(R.string.sign_up_credentials_invalid_password_characters_error), Toast.LENGTH_SHORT).show();
        }
        else if(password.length() < 6 || password.length() > 20){
            Toast.makeText(this,  getResources().getString(R.string.sign_up_credentials_invalid_password_length_error), Toast.LENGTH_SHORT).show();
        }
        else {
            disableButtons();
            ft = fm.beginTransaction();
            ft.add(R.id.loading_frame, loadingBar);
            ft.commit();
            continueButton.setText("SIGNING UP");
            //new CheckCredentialTask().execute(signUpContainer.getDesiredEmail(), signUpContainer.getCountryCode() + signUpContainer.getFullPhoneNumber());
            //Intent intent = new Intent(this, CheckSignUpCredentialsService.class);
            //intent.putExtra("username", username);
            //intent.putExtra("phoneNumber", phoneNumber);
            //startService(intent);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AccountPortal.class);
        startActivity(intent);
        finish();
    }

    public String getDesiredUsernameString() {
        return this.desiredUsernameEditText.getText().toString();
    }

    public String getDesiredPasswordString() {
        return this.desiredPasswordEditText.getText().toString();
    }

    public String getPhoneNumberString() {
        return this.phoneNumberEditText.getText().toString();
    }

    public void setDesiredUsernameString(String desiredUsername) {
        this.desiredUsernameEditText.setText(desiredUsername);
    }

    public void setDesiredPasswordString(String desiredPassword) {
        this.desiredPasswordEditText.setText(desiredPassword);
    }

    public void setPhoneNumberString(String phoneNumber) {
        this.phoneNumberEditText.setText(phoneNumber);
    }

    /*
    private class SignUpCredentialsReceiver extends BroadcastReceiver {

        private SignUpCredentialsReceiver() {

        }

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean successful = intent.getBooleanExtra("successful", false);
            if (!successful) {
                ft = fm.beginTransaction();
                ft.remove(loadingBar);
                ft.commit();
                Toast.makeText(SignUpCredentials.this, intent.getStringExtra("message"), Toast.LENGTH_LONG).show();
                continueButton.setText(getResources().getString(R.string.continue_string));
                SignUpCredentials.this.enableButtons();
            }
            else {
                LocalBroadcastManager.getInstance(SignUpCredentials.this).unregisterReceiver(signUpCredentialsReceiver);
                SignUpCredentials.this.enableButtons();
                Intent startIntent = new Intent(SignUpCredentials.this, SignUpBiographical.class);
                startActivity(startIntent);
                finish();
            }
        }
    }
    */
}

