package com.example.gosia.transitions;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.view.Gravity;
import android.view.Window;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTrainsitions();

    }

    private void setTrainsitions(){
        Slide slide = (Slide) TransitionInflater.from(this).inflateTransition(R.transition.slide);
        getWindow().setEnterTransition(slide);
    }
}
