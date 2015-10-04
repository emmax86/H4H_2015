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
import com.bramblellc.myapplication.services.ActionConstants;
import com.bramblellc.myapplication.services.LoginService;

public class Login extends Activity {

    private EditText phoneNumberEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private boolean buttonsEnabled;

    private FragmentManager fm;
    private FragmentTransaction ft;
    private LoadingBar loadingBar;

    private LoginReceiver loginReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in_layout);

        fm = getFragmentManager();
        loadingBar = new LoadingBar();

        phoneNumberEditText = (EditText) findViewById(R.id.editTextLoginUsername);

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

        //phoneNumberEditText.setText(User.getUser().getUsername());
        passwordEditText = (EditText) findViewById(R.id.editTextPassword);
        loginButton = (Button) findViewById(R.id.buttonSignIn);
        buttonsEnabled = true;

        /*
        SpannableString ss = new SpannableString(getString(R.string.login_clickable_span_text));
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                //showEmailRecoveryDialog();
            }
        };
        ss.setSpan(clickableSpan1, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        TextView textView = (TextView) findViewById(R.id.login_textview);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        */
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    login();
                }
                return false;
            }
        });

        phoneNumberEditText.requestFocus();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        loginReceiver = new LoginReceiver();
        IntentFilter filter = new IntentFilter(ActionConstants.LOGIN_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(loginReceiver, filter);
    }

    public void disableButtons() {
        loginButton.setEnabled(false);
        phoneNumberEditText.setEnabled(false);
        passwordEditText.setEnabled(false);
        buttonsEnabled = false;
    }

    public void enableButtons() {
        loginButton.setEnabled(true);
        phoneNumberEditText.setEnabled(true);
        passwordEditText.setEnabled(true);
        buttonsEnabled = true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AccountPortal.class);
        startActivity(intent);
        finish();
    }


    // when the login button is pressed (login)
    public void loginPressed(View v){
        login();
    }

    public void login() {
        String phoneNumber = phoneNumberEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if (phoneNumber.equals("") || password.equals("")) {
            Toast.makeText(this, "Enter your username or password please", Toast.LENGTH_SHORT).show();
        }
        else {
            disableButtons();
            ft = fm.beginTransaction();
            ft.add(R.id.loading_frame, loadingBar);
            ft.commit();
            loginButton.setText("LOGGING IN");
            //new LoginTask().execute(username, password);
            Intent intent = new Intent(this, LoginService.class);
            intent.putExtra("phone_number", phoneNumber.replaceAll("-", ""));
            intent.putExtra("password", password);
            startService(intent);
        }
    }

    private class LoginReceiver extends BroadcastReceiver {

        private LoginReceiver() {

        }

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean successful = intent.getBooleanExtra("successful", false);
            if (!successful) {
                ft = fm.beginTransaction();
                ft.remove(loadingBar);
                ft.commit();
                Toast.makeText(Login.this, "Invalid phone number or password", Toast.LENGTH_LONG).show();
                loginButton.setText("LOG IN");
                Login.this.passwordEditText.setText("");
                Login.this.enableButtons();
            }
            else {
                LocalBroadcastManager.getInstance(Login.this).unregisterReceiver(loginReceiver);
                Intent startIntent = new Intent(Login.this, Landing.class);
                startActivity(startIntent);
                finish();
            }
        }
    }
}

