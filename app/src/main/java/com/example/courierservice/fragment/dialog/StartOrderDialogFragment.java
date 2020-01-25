package com.example.courierservice.fragment.dialog;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.courierservice.R;
import com.example.courierservice.Utility.UserLastKnowLocationFused;
import com.example.courierservice.appdata.GlobalAppAccess;
import com.example.courierservice.appdata.MydApplication;
import com.example.courierservice.fragment.BaseDialogFragment;
import com.example.courierservice.fragment.PendingFragment;
import com.example.courierservice.model.PendingOrder;
import com.example.courierservice.service.LocationUpdateService;
import com.example.courierservice.service.ServiceUtils;

import java.util.HashMap;
import java.util.Map;

public class StartOrderDialogFragment extends BaseDialogFragment implements View.OnClickListener {

    private PendingOrder pendingOrder;

    private Button btn_cancel, btn_confirm;





    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            pendingOrder = getArguments().getParcelable("pendingOrder");

        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dialog_start_order, container, false);

        init(view);
        return view;
    }

    private void init(View view) {

        btn_cancel = view.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);

        btn_confirm = view.findViewById(R.id.btn_start);
        btn_confirm.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == R.id.btn_cancel){

            dismiss();

        }

        if(id == R.id.btn_start){


            showProgressDialog("Fetching location", true, false);
            new UserLastKnowLocationFused(getActivity(), new UserLastKnowLocationFused.LocationResultFused() {
                @Override
                public void gotLocation(Location location) {
                    if(location != null){
                        sendRequestToServerForStartOrder(location);
                    }
                }
            });





        }
    }


    private void sendRequestToServerForStartOrder(final Location location){
        showProgressDialog("Loading", true, false);
        final StringRequest req = new StringRequest(Request.Method.POST, GlobalAppAccess.URL_START_ORDER,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        dismissProgressDialog();

                        PendingFragment stockFragment = ((PendingFragment) getTargetFragment());
                        if (null != stockFragment) {

                            getActivity().stopService(new Intent(getActivity(), LocationUpdateService.class));
                            getActivity().startService(new Intent(getActivity(), LocationUpdateService.class));

                            //int qtyDelivered = binding.getViewModel().getProductQuantityDelivered();
                            // int totalQty = Integer.parseInt(pendingOrder.getQuantity());

                            // stockFragment.refreshScreen(totalQty - qtyDelivered);
                        }

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                dismissProgressDialog();

                //Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();

                //comment out below code
                PendingFragment targetFragment = ((PendingFragment) getTargetFragment());
                if (null != targetFragment) {

                    if (ServiceUtils.isMyServiceRunning(LocationUpdateService.class, getActivity())) {
                        getActivity().stopService(new Intent(getActivity(), LocationUpdateService.class));
                    } else {
                        getActivity().startService(new Intent(getActivity(), LocationUpdateService.class));
                    }

                    targetFragment.onConfirmOrderStart(pendingOrder);

                    dismiss();

                   // getActivity().stopService(new Intent(getActivity(), LocationUpdateService.class));
                    //getActivity().startService(new Intent(getActivity(), LocationUpdateService.class));

                    //int qtyDelivered = binding.getViewModel().getProductQuantityDelivered();
                    // int totalQty = Integer.parseInt(pendingOrder.getQuantity());

                    // stockFragment.refreshScreen(totalQty - qtyDelivered);
                }


            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("driverId", MydApplication.getInstance().getPrefManger().getUser().getId().toString());
                params.put("orderId", pendingOrder.getOrderNumber().toString());
                params.put("latitude", String.valueOf(location.getLatitude()));
                params.put("longitude", String.valueOf(location.getLongitude()));
                params.put("status", "start");
                return params;
            }



        };

        req.setRetryPolicy(new DefaultRetryPolicy(60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // TODO Auto-generated method stub
        MydApplication.getInstance().addToRequestQueue(req);
    }
}
