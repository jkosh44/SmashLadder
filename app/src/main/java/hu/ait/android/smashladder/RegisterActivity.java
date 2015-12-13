package hu.ait.android.smashladder;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    public static final String NAME_TAG = "NAME_TAG";
    public static final String RANK_TAG = "RANK_TAG";
    public static final String WIN_TAG = "WIN_TAG";
    public static final String LOSE_TAG = "LOSE_TAG";

    private EditText mEmailView;
    private EditText mTagView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mRegisterFormView;

    private String eMial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Intent intent = getIntent();
        eMial = intent.getStringExtra(LoginActivity.EMAIL_TAG);

        mEmailView = (EditText) findViewById(R.id.etRegEmail);
        mTagView = (EditText) findViewById(R.id.etTag);
        mPasswordView = (EditText) findViewById(R.id.etRegPassword);
        mProgressView = findViewById(R.id.register_progress);
        mRegisterFormView = findViewById(R.id.register_form);

        mEmailView.setText(eMial);

        Button btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    attemptRegister();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        Button btnCancel = (Button) findViewById(R.id.btnRegCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cancelIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(cancelIntent);
            }
        });

    }

    private void attemptRegister() throws ParseException {
        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String tag = mTagView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if ("".equals(password) /*&& !isPasswordValid(password)*/) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        if ("".equals(tag)) {
            mTagView.setError(getString(R.string.error_field_required));
            focusView = mTagView;
            cancel = true;
        }

        // Check for a valid email address.
        if ("".equals(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            //creates new user
            showProgress(true);

            final ParseQuery<ParseUser> query = ParseUser.getQuery();
            int userAmount = query.count();

            ParseUser user = new ParseUser();
            user.setUsername(email);
            user.setPassword(password);
            user.put(NAME_TAG, tag);
            user.put(RANK_TAG, userAmount + 1);
            user.put(WIN_TAG, 0);
            user.put(LOSE_TAG, 0);

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    showProgress(false);

                    if (e == null) {
                        Toast.makeText(RegisterActivity.this, "Registration ok", Toast.LENGTH_SHORT).show();
                        Intent mainActIntent = new Intent(RegisterActivity.this, PlayerListActivity.class);
                        startActivity(mainActIntent);
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@wesleyan.edu");
    }

    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisterFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
