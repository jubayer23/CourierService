package com.example.courierservice.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.courierservice.R;
import com.example.courierservice.Utility.CommonMethods;
import com.example.courierservice.appdata.GlobalAppAccess;
import com.example.courierservice.appdata.MydApplication;
import com.example.courierservice.model.PendingOrder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OngoingFragment extends BaseFragment implements View.OnClickListener {


   private PendingOrder onGoingOrder;

   private TextView tv_order_number, tv_destination, tv_picked_up_time, tv_deadline, tv_instruction;

   private Button btn_show_route, btn_complete_order;

    LinearLayout ll_container_ongoing_true, ll_container_ongoing_false;

    public OngoingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ongoing, container, false);

        init(view);
        return view;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void init(View view){
        //rv_results = view.findViewById(R.id.rv_log);

        tv_order_number = view.findViewById(R.id.tv_order_number);
        tv_destination = view.findViewById(R.id.tv_destination_address);
        tv_picked_up_time = view.findViewById(R.id.tv_picked_time);
        tv_deadline = view.findViewById(R.id.tv_deadline);
        tv_instruction = view.findViewById(R.id.tv_instruction);

        btn_show_route = view.findViewById(R.id.btn_show_map);
        btn_show_route.setOnClickListener(this);
        btn_complete_order = view.findViewById(R.id.btn_order_complete);
        btn_complete_order.setOnClickListener(this);

        ll_container_ongoing_true = view.findViewById(R.id.ll_container_ongoing_true);
        ll_container_ongoing_false = view.findViewById(R.id.ll_container_ongoing_false);


    }

    @Override
    public void onResume() {
        super.onResume();

        onGoingOrder = MydApplication.getInstance().getPrefManger().getOnGoingOrder();

        updateUI(onGoingOrder);

    }

    private void updateUI(PendingOrder onGoingOrder){
        if(onGoingOrder == null){
            ll_container_ongoing_false.setVisibility(View.VISIBLE);
            ll_container_ongoing_true.setVisibility(View.GONE);
            return;
        }
        ll_container_ongoing_false.setVisibility(View.GONE);
        ll_container_ongoing_true.setVisibility(View.VISIBLE);

        tv_order_number.setText(String.valueOf(onGoingOrder.getOrderNumber()));
        tv_destination.setText(onGoingOrder.getDropOffAddress());
        tv_picked_up_time.setText(CommonMethods.getDateInStringFromMillis(String.valueOf(onGoingOrder.getPickedUpTime()), GlobalAppAccess.DATE_FORMAT_LOCAL));
        tv_deadline.setText(CommonMethods.getDateInStringFromMillis(String.valueOf(onGoingOrder.getPickedUpTime()), GlobalAppAccess.DATE_FORMAT_LOCAL));
        tv_instruction.setText("No Instruction");



    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.btn_order_complete){

        }

        if(id == R.id.btn_show_map){

        }
    }
}
