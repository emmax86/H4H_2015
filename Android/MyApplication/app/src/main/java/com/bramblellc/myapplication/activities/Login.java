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
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bramblellc.myapplication.R;
import com.bramblellc.myapplication.fragments.LoadingBar;

public class Login extends Activity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private boolean buttonsEnabled;

    private FragmentManager fm;
    private FragmentTransaction ft;
    private LoadingBar loadingBar;

    //private LoginReceiver loginReceiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in_layout);

        fm = getFragmentManager();
        loadingBar = new LoadingBar();

        usernameEditText = (EditText) findViewById(R.id.editTextLoginUsername);
        //usernameEditText.setText(User.getUser().getUsername());
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
                    //login();
                }
                return false;
            }
        });

        usernameEditText.requestFocus();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //loginReceiver = new LoginReceiver();
        //IntentFilter filter = new IntentFilter(ActionConstants.LOGIN_ACTION);
        //LocalBroadcastManager.getInstance(this).registerReceiver(loginReceiver, filter);
    }

    public void disableButtons() {
        loginButton.setEnabled(false);
        usernameEditText.setEnabled(false);
        passwordEditText.setEnabled(false);
        buttonsEnabled = false;
    }

    public void enableButtons() {
        loginButton.setEnabled(true);
        usernameEditText.setEnabled(true);
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
        //login();
    }

    /*
    public void login() {
        String username = getUsernameString();
        String password = getPasswordString();
        if (username.equals("") || password.equals("")) {
            Toast.makeText(this, getResources().getString(R.string.enter_username_and_password_prompt), Toast.LENGTH_SHORT).show();
        }
        else {
            disableButtons();
            ft = fm.beginTransaction();
            ft.add(R.id.loading_frame, loadingBar);
            ft.commit();
            loginButton.setText(getResources().getString(R.string.log_in_in_progress));
            //new LoginTask().execute(username, password);
            Intent intent = new Intent(this, LoginService.class);
            intent.putExtra("username", username);
            intent.putExtra("password", password);
            startService(intent);
        }
    }
    */
    /*
    public void showEmailRecoveryDialog() {
        if(buttonsEnabled) {
            new MaterialDialog.Builder(this)
                    .title(getResources().getString(R.string.password_recovery_material_dialog_title))
                    .content(getResources().getString(R.string.password_recovery_material_dialog_content))
                    .inputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
                    .input("", User.getUser().getEmail(), new MaterialDialog.InputCallback() {
                        @Override
                        public void onInput(MaterialDialog dialog, CharSequence input) {

                        }
                    })
                    .positiveText(getResources().getString(R.string.submit))
                    .negativeText(getResources().getString(R.string.cancel))
                    .callback(new MaterialDialog.ButtonCallback() {
                        @Override
                        public void onPositive(MaterialDialog dialog) {

                        }

                        @Override
                        public void onNegative(MaterialDialog dialog) {

                        }
                    })
                    .show();
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
                Toast.makeText(Login.this, getResources().getString(R.string.invalid_username_or_password), Toast.LENGTH_LONG).show();
                loginButton.setText(getResources().getString(R.string.log_in));
                Login.this.setPasswordString("");
                Login.this.enableButtons();
            }
            else {
                LocalBroadcastManager.getInstance(Login.this).unregisterReceiver(loginReceiver);
                String username = intent.getStringExtra("username");
                String email = intent.getStringExtra("email");
                String phoneNumber = intent.getStringExtra("phoneNumber");
                String name = intent.getStringExtra("name");
                String authToken = intent.getStringExtra("auth_token");
                User.getUser().setAuthenticationToken(authToken);
                User theUser = User.getUser();
                theUser.setUsername(username);
                theUser.setEmail(email);
                theUser.setPhoneNumber(phoneNumber);
                theUser.setName(name);
                Intent startIntent = new Intent(Login.this, Main.class);
                startActivity(startIntent);
                finish();
            }
        }
    }

    public String getUsernameString() {
        return usernameEditText.getText().toString();
    }

    public String getPasswordString() {
        return passwordEditText.getText().toString();
    }

    public void setUsernameString(String username) {
        this.usernameEditText.setText(username);
    }

    public void setPasswordString(String password) {
        this.passwordEditText.setText(password);
    }
    */
}

