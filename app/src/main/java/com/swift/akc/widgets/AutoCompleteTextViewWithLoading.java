package com.swift.akc.widgets;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

import com.swift.akc.R;

public class AutoCompleteTextViewWithLoading extends AppCompatAutoCompleteTextView {

    private ProgressBar mLoadingIndicator;

    public AutoCompleteTextViewWithLoading(Context context) {
        super(context);
    }

    public AutoCompleteTextViewWithLoading(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoCompleteTextViewWithLoading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLoadingIndicator(ProgressBar progressBar) {
        mLoadingIndicator = progressBar;
    }

    public void displayIndicator(){
        if(mLoadingIndicator != null){
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }
        this.setText("");
    }

    public void removeIndicator(){
        if(mLoadingIndicator != null){
            mLoadingIndicator.setVisibility(View.GONE);
        }
    }
}
