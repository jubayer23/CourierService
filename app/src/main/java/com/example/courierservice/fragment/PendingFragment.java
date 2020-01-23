package com.example.courierservice.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.courierservice.R;
import com.example.courierservice.Utility.CommonMethods;
import com.example.courierservice.adapter.PendingOrderAdapter;
import com.example.courierservice.appdata.DummyResponse;
import com.example.courierservice.appdata.GlobalAppAccess;
import com.example.courierservice.appdata.MydApplication;
import com.example.courierservice.fragment.dialog.StartOrderDialogFragment;
import com.example.courierservice.model.PendingOrder;
import com.example.courierservice.model.PendingOrders;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class PendingFragment extends BaseFragment implements PendingOrderAdapter.OnItemClickListener {

    private PendingOrderAdapter adapter;
    private ArrayList<PendingOrder> pendingOrders = new ArrayList<>(0);
    private PendingOrder selectedItem;

    private RecyclerView rv_pending_order;


    public PendingFragment() {
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
        View view = inflater.inflate(R.layout.fragment_pending, container, false);

        init(view);

        initRecyclerVIew();

        getPendingOrder();

        return view;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void init(View view){
        //rv_results = view.findViewById(R.id.rv_log);

        rv_pending_order = view.findViewById(R.id.rv_pending_order);
    }

    private void initRecyclerVIew(){
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rv_pending_order.setLayoutManager(mLayoutManager);

        //Set Adapter
        adapter = new PendingOrderAdapter(pendingOrders, this);
       rv_pending_order.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();

    }


    public void getPendingOrder() {



        final StringRequest req = new StringRequest(Request.Method.POST, GlobalAppAccess.URL_PENDING_ORDER,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        PendingOrders pendingOrdersList = MydApplication.gson.fromJson(response, PendingOrders.class);
                        pendingOrders.addAll(pendingOrdersList.getPendingOrders());
                        adapter.notifyDataSetChanged();

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();

                PendingOrders pendingOrdersList = MydApplication.gson.fromJson(DummyResponse.pendingOrder(), PendingOrders.class);
                Log.d("DEBUG", String.valueOf(pendingOrdersList.getPendingOrders().size()));
                Log.d("DEBUG", String.valueOf(pendingOrders.size()));

                pendingOrders.addAll(pendingOrdersList.getPendingOrders());
                adapter.notifyDataSetChanged();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("driverId", MydApplication.getInstance().getPrefManger().getUserInfo().getId().toString());
                return params;
            }



        };

        req.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // TODO Auto-generated method stub
        MydApplication.getInstance().addToRequestQueue(req);

    }

    @Override
    public void onRouteClickListener(PendingOrder item) {

    }

    @Override
    public void onDetailsClickListener(PendingOrder item) {

    }

    @Override
    public void onStartClickListener(PendingOrder item) {

        //TODO cross check with pickup location and user current location





        StartOrderDialogFragment confirmationDialog = new StartOrderDialogFragment();

        Bundle data = new Bundle();
        data.putParcelable("pendingOrder", item);

        confirmationDialog.setArguments(data);
        confirmationDialog.setTargetFragment(PendingFragment.this, 1333);
        confirmationDialog.show(getFragmentManager(), "fragment_start_order_confirmation");

    }

    public void onConfirmOrderStart(PendingOrder item){

        item.setPickedUpTime(CommonMethods.getCurrentTimeInMiliSecs());
        MydApplication.getInstance().getPrefManger().setOnGoingOrder(item);
    }
}
