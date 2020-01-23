package com.example.courierservice.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PendingOrders extends BaseModel implements Serializable
{

    @SerializedName("pendingOrders")
    @Expose
    private List<PendingOrder> pendingOrders = null;
    private final static long serialVersionUID = 3869498160058823278L;

    public List<PendingOrder> getPendingOrders() {
        return pendingOrders;
    }

    public void setPendingOrders(List<PendingOrder> pendingOrder) {
        this.pendingOrders = pendingOrder;
    }

}
