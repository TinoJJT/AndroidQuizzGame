package com.example.tino.quizzgame;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_SCORE = "score";
    private static final String KEY_CHEATING = "cheating";
    private static final String KEY_CHEATS = "cheats";
    private static final int REQUES_CODE_CHEAT = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mCheatButton;
    private TextView cheatsLeftTextView;
    private int mQuestionIndex = 0;
    private int mScore = 0;
    private int cheatsLeft = 3;
    private boolean mIsCheater;

    private Question[] mQuestions= new Question[] {
            new Question(R.string.question_helsinki, true),
            new Question(R.string.question_mario, false),
            new Question(R.string.question_android, false),
            new Question(R.string.question_sata, true),
            new Question(R.string.question_creator, false),
            new Question(R.string.question_relativity, true),
            new Question(R.string.question_earth, false),
            new Question(R.string.question_moon, true),
            new Question(R.string.question_atlantic, false),
            new Question(R.string.question_haghel, false)
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            mQuestionIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mScore = savedInstanceState.getInt(KEY_SCORE, 0);
            mIsCheater = savedInstanceState.getBoolean(KEY_CHEATING, false);
            cheatsLeft = savedInstanceState.getInt(KEY_CHEATS, 3);
        }

        setQuestionText(mQuestionIndex);

        mTrueButton = findViewById(R.id.trueButton);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
                mNextButton.setVisibility(View.VISIBLE);
                hideAnswerButtons();
            }
        });

        mFalseButton = findViewById(R.id.falseButton);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
                mNextButton.setVisibility(View.VISIBLE);
                hideAnswerButtons();
            }
        });

        mCheatButton = findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean answerIsTrue = mQuestions[mQuestionIndex].isTrue();
                Intent intent = CheatActivity.newIntent(MainActivity.this, answerIsTrue);
                startActivityForResult(intent, REQUES_CODE_CHEAT);

            }
        });

        updateCheatsLeft();

        mNextButton = findViewById(R.id.nextButton);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mQuestionIndex += 1;
                if(mQuestionIndex == mQuestions.length){
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.end_toast) + mScore, Toast.LENGTH_LONG).show();
                    mScore = 0;
                    mQuestionIndex = 0;
                    cheatsLeft = 3;
                }
                mIsCheater = false;
                setQuestionText(mQuestionIndex);
                mNextButton.setVisibility(View.GONE);
                showAnswerButtons();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onActivityResult() called");
        if (resultCode != Activity.RESULT_OK) return;

        if (requestCode == REQUES_CODE_CHEAT) {
            if (data == null) return;
            mIsCheater = CheatActivity.wasAnswerShown(data);
            if(mIsCheater) cheatsLeft--;
        }
        updateCheatsLeft();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstance");
        savedInstanceState.putInt(KEY_INDEX, mQuestionIndex);
        savedInstanceState.putInt(KEY_SCORE, mScore);
        savedInstanceState.putBoolean(KEY_CHEATING, mIsCheater);
        savedInstanceState.putInt(KEY_CHEATS, cheatsLeft);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    private void setQuestionText(int mQuestionIndex){
        TextView questionTextView = findViewById(R.id.textView);
        questionTextView.setText(mQuestions[mQuestionIndex].getTextResId());
    }

    private void checkAnswer(boolean userClickedTrue) {
        boolean answerIsTrue = mQuestions[mQuestionIndex].isTrue();

        if(mIsCheater) Toast.makeText(MainActivity.this, R.string.judgment_toast, Toast.LENGTH_SHORT).show();
        else {
            if (userClickedTrue == answerIsTrue)
                Toast.makeText(MainActivity.this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(MainActivity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        }
        if (userClickedTrue == answerIsTrue) mScore += 10;
    }

    private void hideAnswerButtons(){
        mTrueButton.setEnabled(false);
        mFalseButton.setEnabled(false);
    }

    private void showAnswerButtons(){
        mTrueButton.setEnabled(true);
        mFalseButton.setEnabled(true);
    }

    private void updateCheatsLeft(){
        cheatsLeftTextView = findViewById(R.id.cheats_left);
        cheatsLeftTextView.setText(getApplicationContext().getString(R.string.cheats_left) + " "+ Integer.toString(cheatsLeft));
        if(cheatsLeft == 0) {
            mCheatButton.setEnabled(false);
        }
        else {
            mCheatButton.setEnabled(true);
        }
    }
}
