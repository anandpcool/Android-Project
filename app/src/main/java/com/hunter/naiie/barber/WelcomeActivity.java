package com.hunter.naiie.barber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hunter.naiie.R;
import com.hunter.naiie.adapter.SliderAdapter;
import com.hunter.naiie.fragment.BookingFragment;
import com.hunter.naiie.fragment.HomeFragment;
import com.hunter.naiie.fragment.ProfileFragment;
import com.hunter.naiie.fragment.ServiceFragment;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class WelcomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    SliderView sliderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        sliderView = findViewById(R.id.imageSlider);


        //fragment load
        //loading the default fragment
        loadFragment(new HomeFragment());

//getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

//      Slider images
        final SliderAdapter adapter = new SliderAdapter(this);
        adapter.setCount(5);

        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimations.SLIDE); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.startAutoCycle();

        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                sliderView.setCurrentPagePosition(position);
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.navigation_home:
                fragment = new HomeFragment();
                break;

            case R.id.navigation_service:
                fragment = new ServiceFragment();
                break;

            case R.id.navigation_booking:
                fragment = new BookingFragment();
                break;

            case R.id.navigation_profile:
                fragment = new ProfileFragment();
                break;

        }

        return loadFragment(fragment);
    }

    // load fragment
    private boolean loadFragment(Fragment fragment) {
//switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;


    }
}
