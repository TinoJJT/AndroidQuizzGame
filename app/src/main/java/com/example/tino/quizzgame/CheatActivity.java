package com.example.tino.quizzgame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {
    private static final String TAG = "CheatActivity";
    private static final String EXTRA_AWNSER_IS_TRUE = "com.example.tino.quizzgame.answer_is_true";
    private static final String EXTRA_AWNSER_SHOWN = "com.example.tino.quizzgame.answer_shown";
    private static final String KEY_CHEATING = "cheating";
    private static final String KEY_ANSWER_TRUE = "true";


    private boolean mAnswerIsTrue;
    private boolean mCheater;

    private TextView mAnswerTextView;
    private TextView mAndroidVersionTextView;
    private Button mShowAnswerButton;

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_AWNSER_IS_TRUE, answerIsTrue);
        return intent;
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_AWNSER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerTextView = findViewById(R.id.answer_text_view);
        mAndroidVersionTextView = findViewById(R.id.versionTextView);
        mShowAnswerButton = findViewById(R.id.show_answer_button);

        mAndroidVersionTextView.setText(getApplicationContext().getString(R.string.API) + " " +
        Integer.toString(Build.VERSION.SDK_INT));

        if(savedInstanceState != null){
            if(savedInstanceState.getBoolean(KEY_CHEATING, false)){
                setAnswerShownResult(true);
                mAnswerIsTrue = savedInstanceState.getBoolean(KEY_ANSWER_TRUE);
                if(mAnswerIsTrue){
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                }
            }
        }

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_AWNSER_IS_TRUE, false);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAnswerIsTrue){
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                mCheater = true;
                setAnswerShownResult(true);
            }
        });
    }

    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_AWNSER_SHOWN, isAnswerShown);
        setResult(Activity.RESULT_OK, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        Log.i(TAG, "onSaveInstateState");
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(KEY_ANSWER_TRUE, mAnswerIsTrue);
        savedInstanceState.putBoolean(KEY_CHEATING, mCheater);
    }
}
