package com.example.gosia.transitions.FragmentTransitions;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.transition.Slide;
import android.view.Gravity;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.gosia.transitions.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Gosia on 2015-10-24.
 */
public class FragmentTransitionsActivity extends AppCompatActivity implements FragmentPanel.Callback{

    @Bind(R.id.content)
    FrameLayout content;
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.btn_changefragnets)
    Button btnChangefragnets;

    int mode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragmenttransitionsactivity_layout);
        ButterKnife.bind(this);
        getWindow().setEnterTransition(new Explode());


    }

    @OnClick(R.id.btn_changefragnets)
    public void Change(){
        if(mode == 0){
            FragmentPanel fragmentPanel = FragmentPanel.getInstance(FragmentPanel.Args.TYPE_A);
            Slide slideTransition = new Slide(Gravity.BOTTOM);
            slideTransition.setDuration(500);
            slideTransition.setInterpolator(new AccelerateDecelerateInterpolator());

            ChangeBounds changeBoundsTransition = new ChangeBounds();
            changeBoundsTransition.setDuration(500);
            changeBoundsTransition.setInterpolator(new AccelerateDecelerateInterpolator());

            fragmentPanel.setEnterTransition(slideTransition);
            fragmentPanel.setAllowEnterTransitionOverlap(false);
            fragmentPanel.setAllowReturnTransitionOverlap(false);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.sliding_panel, fragmentPanel, "A")
                    .commit();
            mode = 1;
        }
        else if(mode == 1) {
            FragmentPanel fragmentPanel = (FragmentPanel) getSupportFragmentManager().findFragmentByTag("A");
            fragmentPanel.addNextFragment(false, this);
            mode = 2;
        }
        else if(mode == 2) {
            FragmentPanel fragmentPanel = (FragmentPanel) getSupportFragmentManager().findFragmentByTag("A");
            Slide slideTransition = new Slide(Gravity.BOTTOM);
            slideTransition.setInterpolator(new AccelerateDecelerateInterpolator());
            slideTransition.setDuration(300);

            ChangeBounds changeBoundsTransition = new ChangeBounds();
            changeBoundsTransition.setDuration(300);
            changeBoundsTransition.setInterpolator(new AccelerateDecelerateInterpolator());

            fragmentPanel.setExitTransition(slideTransition);
            fragmentPanel.setAllowEnterTransitionOverlap(false);
            fragmentPanel.setAllowReturnTransitionOverlap(false);
            getSupportFragmentManager().beginTransaction()
                    .remove(fragmentPanel)
                    .commit();

            mode = 0;
        }
    }


    @Override
    public void callback() {
        ContentFragment fragment = new ContentFragment();
        Slide slideTransition = new Slide(Gravity.TOP);
        slideTransition.setDuration(500);

//        ChangeBounds changeBoundsTransition = new ChangeBounds();
//        changeBoundsTransition.setDuration(300);

        fragment.setEnterTransition(slideTransition);
        fragment.setAllowEnterTransitionOverlap(false);
        fragment.setAllowReturnTransitionOverlap(false);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }
}
