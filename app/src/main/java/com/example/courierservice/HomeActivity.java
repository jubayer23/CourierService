package com.example.courierservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.courierservice.appdata.MydApplication;
import com.example.courierservice.fragment.OngoingFragment;
import com.example.courierservice.fragment.PendingFragment;
import com.example.courierservice.model.PendingOrder;

import java.util.ArrayList;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout btn_ongoing, btn_pending, btn_completed;

    private FrameLayout container;

    Fragment onGoingFragment, pendingFragment;

    private FragmentTransaction transaction;

    private static String TAG_FRAG_ONGOING  = "ongoingFragment";
    private static String TAG_FRAG_PENDING  = "pendingFragment";

    private static final int TAB_COLOR_UNSELECTED = R.color.gray_dark;
    private static final int TAB_COLOR_SELECTED = R.color.blue;


    private TextView tv_notification_counter;
    //private PendingOrder pendingOrderFromNotification = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();

        initToolbar(getString(R.string.app_name), false);

        //PendingOrder pendingOrderFromNotification = getIntent().getParcelableExtra("pendingOrder");

        showFragment(TAG_FRAG_ONGOING);


    }

    private void init(){
        btn_ongoing = findViewById(R.id.btn_ongoing);
        btn_ongoing.setOnClickListener(this);

        btn_pending = findViewById(R.id.btn_pending);
        btn_pending.setOnClickListener(this);

        btn_completed = findViewById(R.id.btn_completed);
        btn_completed.setOnClickListener(this);

        tv_notification_counter = findViewById(R.id.tv_notification_counter);
        clearNotificationCounter();

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
                onGoingFragment.onResume();

            } else { // fragment needs to be added to frame container
                transaction.add(R.id.container, onGoingFragment, TAG_FRAG_ONGOING);
            }
            // Hide fragment B
            if (pendingFragment.isAdded()) {
                transaction.hide(pendingFragment);
                /*
                * in here we are clearing cached order numbers came via notification as because we already visited the
                * pending fragment so that we already viewed the noti so we dont need this cache data anymore.
                * */
                ArrayList<Integer> notiOrderNumbers = MydApplication.getInstance().getPrefManger().getNotiOrderNumber();
                notiOrderNumbers.clear();
                MydApplication.getInstance().getPrefManger().setNotiOrderNumber(notiOrderNumbers);
            }

        }else if(selectedFragment.equals(TAG_FRAG_PENDING)){
            transaction = getSupportFragmentManager()
                    .beginTransaction();
            if (pendingFragment.isAdded()) { // if the fragment is already in container
                transaction.show(pendingFragment);
                pendingFragment.onResume();
                clearNotificationCounter();

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
            btn_completed.setBackgroundColor(getResources().getColor(TAB_COLOR_UNSELECTED));
        } else if (selectedFragment.equalsIgnoreCase(TAG_FRAG_PENDING)) {
            btn_pending.setBackgroundColor(getResources().getColor(TAB_COLOR_SELECTED));
            btn_ongoing.setBackgroundColor(getResources().getColor(TAB_COLOR_UNSELECTED));
            btn_completed.setBackgroundColor(getResources().getColor(TAB_COLOR_UNSELECTED));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_top_right_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.item_logout:
                processLogout();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void processLogout(){
        MydApplication.getInstance().getPrefManger().setUser(null);
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerBroadReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unRegisterBroadReceiver();
    }

    BroadcastReceiver broadcastReceiver;

    private void registerBroadReceiver(){
        registerReceiver(getBroadcastReceiver(), new IntentFilter("receive_pending_order"));
    }

    private void unRegisterBroadReceiver(){
        unregisterReceiver(broadcastReceiver);
    }

    private BroadcastReceiver getBroadcastReceiver(){
        broadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                //String sms=arg1.getExtras().getString("m");
                // intext.setText(sms);





            }
        };

        return broadcastReceiver;

    }

    private void showNotificationForeground(){
        PendingFragment test = (PendingFragment) getSupportFragmentManager().findFragmentByTag(TAG_FRAG_PENDING);
        if (test != null && test.isVisible()) {
            //DO STUFF
            //PendingFragment test = (PendingFragment) getSupportFragmentManager().findFragmentByTag(TAG_FRAG_PENDING);
            test.displayRefreshButton();
        }
        else {
            //Whatever
            tv_notification_counter.setVisibility(View.VISIBLE);

            int previousCounter = Integer.parseInt(tv_notification_counter.getText().toString());
            previousCounter++;

            tv_notification_counter.setText(String.valueOf(previousCounter));
        }
    }

    private void clearNotificationCounter(){
        tv_notification_counter.setText("0");
        tv_notification_counter.setVisibility(View.GONE);
    }
}
