package com.example.courierservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.courierservice.fragment.OngoingFragment;
import com.example.courierservice.fragment.PendingFragment;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout btn_ongoing, btn_pending;

    private FrameLayout container;

    Fragment onGoingFragment, pendingFragment;

    private FragmentTransaction transaction;

    private static String TAG_FRAG_ONGOING  = "ongoingFragment";
    private static String TAG_FRAG_PENDING  = "pendingFragment";

    private static final int TAB_COLOR_UNSELECTED = R.color.gray_dark;
    private static final int TAB_COLOR_SELECTED = R.color.blue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();

        initToolbar(getString(R.string.app_name), false);

        showFragment(TAG_FRAG_ONGOING);
    }

    private void init(){
        btn_ongoing = findViewById(R.id.btn_ongoing);
        btn_ongoing.setOnClickListener(this);

        btn_pending = findViewById(R.id.btn_pending);
        btn_pending.setOnClickListener(this);

        container = findViewById(R.id.container);

        onGoingFragment = new OngoingFragment();
        pendingFragment = new PendingFragment();
    }

    private void showFragment(String selectedFragment){
        if(selectedFragment.equals(TAG_FRAG_ONGOING)){

            transaction = getSupportFragmentManager()
                    .beginTransaction();
            if (onGoingFragment.isAdded()) { // if the fragment is already in container
                transaction.show(onGoingFragment);

            } else { // fragment needs to be added to frame container
                transaction.add(R.id.container, onGoingFragment, TAG_FRAG_ONGOING);
            }
            // Hide fragment B
            if (pendingFragment.isAdded()) {
                transaction.hide(pendingFragment);
            }

        }else if(selectedFragment.equals(TAG_FRAG_PENDING)){
            transaction = getSupportFragmentManager()
                    .beginTransaction();
            if (pendingFragment.isAdded()) { // if the fragment is already in container
                transaction.show(pendingFragment);
                pendingFragment.onResume();

            } else { // fragment needs to be added to frame container
                transaction.add(R.id.container, pendingFragment, TAG_FRAG_PENDING);
            }
            // Hide fragment B
            if (onGoingFragment.isAdded()) {
                transaction.hide(onGoingFragment);
            }
        }

        transaction.commit();

        toggleBottomButtonColor(selectedFragment);
    }

    private void toggleBottomButtonColor(String selectedFragment){
        if (selectedFragment.equalsIgnoreCase(TAG_FRAG_ONGOING)) {
            btn_ongoing.setBackgroundColor(getResources().getColor(TAB_COLOR_SELECTED));
            btn_pending.setBackgroundColor(getResources().getColor(TAB_COLOR_UNSELECTED));
        } else if (selectedFragment.equalsIgnoreCase(TAG_FRAG_PENDING)) {
            btn_pending.setBackgroundColor(getResources().getColor(TAB_COLOR_SELECTED));
            btn_ongoing.setBackgroundColor(getResources().getColor(TAB_COLOR_UNSELECTED));
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == R.id.btn_ongoing){
            showFragment(TAG_FRAG_ONGOING);
        }

        if(id == R.id.btn_pending){
            showFragment(TAG_FRAG_PENDING);
        }
    }
}
