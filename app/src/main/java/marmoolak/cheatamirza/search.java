package marmoolak.cheatamirza;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class search extends AppCompatActivity{

    private UserLoginTask mAuthTask = null;

    private EditText mSearchView;
    private View mProgressView;
    private View mSearchFormView;
    private String[] TEXT_CREDENTIALS={};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mSearchView = (EditText) findViewById(R.id.search_text);
        mSearchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptSearch();
                    return true;
                }
                return false;
            }
        });

        Button mSearchButton = (Button) findViewById(R.id.search_button);
        mSearchButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSearch();
            }
        });

        mSearchFormView = findViewById(R.id.search_form);
        mProgressView = findViewById(R.id.search_progress);
    }


    private void attemptSearch() {
        if (mAuthTask != null) {
            return;
        }
        mSearchView.setError(null);

        String search_text = mSearchView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(search_text) && !isTextValid(search_text)) {
            mSearchView.setError("فقط فارسی با من صحبت کن :)");
            focusView = mSearchView;
            cancel = true;
        }
        if (TextUtils.isEmpty(search_text)) {
            mSearchView.setError("خالی گذاشتیش که...!");
            focusView = mSearchView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            mAuthTask = new UserLoginTask(search_text);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isTextValid(String text) {
        for (int i = 0; i < Character.codePointCount(text, 0, text.length()); i++) {
            int c = text.codePointAt(i);
            if (c >= 0x0600 && c <=0x06FF || c== 0xFB8A || c==0x067E || c==0x0686 || c==0x06AF)
                return true;
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mSearchFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mSearchFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mSearchFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mSearchView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mText;

        UserLoginTask(String text) {
            mText = text;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                //add data query here
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for(String credential : TEXT_CREDENTIALS) {
                String[] pieces = credential.split(",");
                //query match
                return true;
                }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
            } else {
                mSearchView.setError("Error");
                mSearchView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

