package com.example.gosia.transitions.FragmentTransitions;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.AutoTransition;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.transition.Transition;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.gosia.transitions.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Gosia on 2015-10-24.
 */
public class FragmentPanel extends Fragment {


    @Bind(R.id.animated_content_fragment)
    RelativeLayout animatedContentFragment;
    @Bind(R.id.image)
    RelativeLayout image;
    @Bind(R.id.circle)
    ImageView circle;

    private Callback listener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = null;
        if (getArguments().getInt(Args.TYPE, Args.TYPE_A) == Args.TYPE_A) {
            root = inflater.inflate(R.layout.fragment1, container, false);
        } else {
            root = inflater.inflate(R.layout.fragment2, container, false);
        }
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof Callback){
            listener = (Callback)activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public void addNextFragment(boolean overlap, final Activity act) {
        FragmentPanel sharedElementFragment2 = FragmentPanel.getInstance(Args.TYPE_B);

        Slide slideTransition = new Slide(Gravity.RIGHT);
        slideTransition.setInterpolator(new AccelerateDecelerateInterpolator());
        slideTransition.setDuration(500);

        AutoTransition changeBoundsTransition = new AutoTransition();
        changeBoundsTransition.setInterpolator(new AccelerateDecelerateInterpolator());
        changeBoundsTransition.setDuration(500);
        changeBoundsTransition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {

                ((Callback)act).callback();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });

        sharedElementFragment2.setEnterTransition(slideTransition);
        sharedElementFragment2.setAllowEnterTransitionOverlap(overlap);
        sharedElementFragment2.setAllowReturnTransitionOverlap(overlap);
        sharedElementFragment2.setSharedElementEnterTransition(changeBoundsTransition);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.sliding_panel, sharedElementFragment2, "A")
                //.addToBackStack(null)
                .addSharedElement(animatedContentFragment, animatedContentFragment.getTransitionName())
                .addSharedElement(image, image.getTransitionName())
                .addSharedElement(circle, circle.getTransitionName())
                .commit();
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({Args.TYPE_A, Args.TYPE_B})
    public @interface FragmentType {
    }

    public static FragmentPanel getInstance(@FragmentType int type) {
        FragmentPanel panel = new FragmentPanel();
        Bundle args = new Bundle();
        args.putInt(Args.TYPE, type);
        panel.setArguments(args);
        return panel;
    }

    public static class Args {
        public static final String TYPE = "TYPE";


        public static final int TYPE_A = 1;
        public static final int TYPE_B = 2;
    }

    //callbacks
    public static interface Callback{
        void callback();
    }


}