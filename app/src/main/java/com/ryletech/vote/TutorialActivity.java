package com.ryletech.vote;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.pixplicity.easyprefs.library.Prefs;

import static com.ryletech.vote.AppConfig.FIRST_RUN;

public class TutorialActivity extends AppIntro2 {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!Prefs.getBoolean(FIRST_RUN, true)) {

//            show tutorial
        startActivity(new Intent(TutorialActivity.this, SplashScreenActivity.class));
        finish();
    }

        addSlide(AppIntroFragment.newInstance("Opinion Track ", "A firm that conducts research", R.drawable.planet_earth, Color.parseColor("#27ae60")));
        addSlide(AppIntroFragment.newInstance("Research Provision", "We provide research to clients across all industries", R.drawable.cloudy, Color.parseColor("#34495e")));
        addSlide(AppIntroFragment.newInstance("Quality.", "Conduct qualitative research. By measuring the quantity", R.drawable.full_moon, Color.parseColor("#c0392b")));
        addSlide(AppIntroFragment.newInstance("Analysis", "We focus on analysing and solving social and real life problems.", R.drawable.planet_earth, Color.parseColor("#27ae60")));
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        loadMainActivity();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {

    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        loadMainActivity();
    }

    private void loadMainActivity(){
        //        Initialize Tutorial Status
        Prefs.putBoolean(FIRST_RUN, false);

        startActivity(new Intent(TutorialActivity.this, LoginActivity.class));
        finish();
    }
}
