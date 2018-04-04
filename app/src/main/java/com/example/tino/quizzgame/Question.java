package com.example.tino.quizzgame;

/**
 * Created by Tino on 18/03/2018.
 */

public class Question {
    private int mTextResId;
    private boolean mIsTrue;

    public Question(int textResId, boolean isTrue) {
        mTextResId = textResId;
        mIsTrue = isTrue;
    }

    public boolean isTrue() {
        return mIsTrue;
    }

    public void setTrue(boolean aTrue) {
        mIsTrue = aTrue;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }
}
