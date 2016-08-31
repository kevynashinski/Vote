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

        addSlide(AppIntroFragment.newInstance("Get all supermarkets around you.", "Within 1Km Radius", R.drawable.planet_earth, Color.parseColor("#27ae60")));
        addSlide(AppIntroFragment.newInstance("Get a list of all the categories in each supermarket.", "Shop Easily With Simplified Supermarkets' Products Categories", R.drawable.cloudy, Color.parseColor("#34495e")));
        addSlide(AppIntroFragment.newInstance("View all the products offered at a supermarket.", "Have a Glimpse of all Products a Supermarket Offers", R.drawable.full_moon, Color.parseColor("#c0392b")));
        addSlide(AppIntroFragment.newInstance("Fill your details and order the products in your cart.", "Experience This Smart Revolutionary Shopping With Karibu pay in your hands", R.drawable.planet_earth, Color.parseColor("#27ae60")));
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
