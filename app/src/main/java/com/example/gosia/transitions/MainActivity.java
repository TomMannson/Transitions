package com.example.gosia.transitions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.gosia.transitions.FragmentTransitions.FragmentTransitionsActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setActivityTransitions();
        ButterKnife.bind(this);
    }

    private void setActivityTransitions(){
        Slide slideTransition = new Slide();
        slideTransition.setSlideEdge(Gravity.LEFT);
        slideTransition.setDuration(1000);
        getWindow().setReenterTransition(slideTransition);
        getWindow().setExitTransition(slideTransition);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.button)
    public void click(View v){
        testStart(SecondActivity.class);
        //startActivity(new Intent(this, SecondActivity.class));
    }

    @OnClick(R.id.button2)
    public void click2(View v){
        Slide slideTransition = new Slide();
        slideTransition.setSlideEdge(Gravity.TOP);
        slideTransition.setDuration(500);
        getWindow().setReenterTransition(slideTransition);
        getWindow().setExitTransition(slideTransition);
        getWindow().setAllowEnterTransitionOverlap(false);
        getWindow().setAllowReturnTransitionOverlap(false);
        transitionToActivity(FragmentTransitionsActivity.class);
        //startActivity(new Intent(this, SecondActivity.class));
    }

    private  void testStart(Class target){
        Pair<View, String>[] a = new Pair[0];
        Intent i = new Intent(this, target);
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, a);
        this.startActivity(i, transitionActivityOptions.toBundle());
    }

    private void transitionToActivity(Class target) {
        final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(this, true);
        startActivity(target, pairs);
    }


    private void startActivity(Class target, Pair<View, String>[] pairs) {
        Intent i = new Intent(this, target);
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairs);
        this.startActivity(i, transitionActivityOptions.toBundle());
    }
}
