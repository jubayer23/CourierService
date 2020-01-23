package com.example.courierservice.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PendingOrder implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("orderNumber")
    @Expose
    private Integer orderNumber;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("pickUpAddress")
    @Expose
    private String pickUpAddress;
    @SerializedName("pickUpLat")
    @Expose
    private Float pickUpLat;
    @SerializedName("pickUpLang")
    @Expose
    private Float pickUpLang;
    @SerializedName("dropOffAddress")
    @Expose
    private String dropOffAddress;
    @SerializedName("dropOffLat")
    @Expose
    private Float dropOffLat;
    @SerializedName("dropOffLang")
    @Expose
    private Float dropOffLang;
    @SerializedName("parcelType")
    @Expose
    private String parcelType;
    @SerializedName("parcelWeight")
    @Expose
    private String parcelWeight;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("price")
    @Expose
    private Object price;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("driverId")
    @Expose
    private Integer driverId;
    @SerializedName("instruction")
    @Expose
    private Object instruction;
    @SerializedName("dueTime")
    @Expose
    private long dueTime;

    @SerializedName("pickedUpTime")
    @Expose
    private long pickedUpTime;

    private final static long serialVersionUID = 476684227061011180L;

    protected PendingOrder(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        if (in.readByte() == 0) {
            orderNumber = null;
        } else {
            orderNumber = in.readInt();
        }
        name = in.readString();
        email = in.readString();
        address = in.readString();
        pickUpAddress = in.readString();
        if (in.readByte() == 0) {
            pickUpLat = null;
        } else {
            pickUpLat = in.readFloat();
        }
        if (in.readByte() == 0) {
            pickUpLang = null;
        } else {
            pickUpLang = in.readFloat();
        }
        dropOffAddress = in.readString();
        if (in.readByte() == 0) {
            dropOffLat = null;
        } else {
            dropOffLat = in.readFloat();
        }
        if (in.readByte() == 0) {
            dropOffLang = null;
        } else {
            dropOffLang = in.readFloat();
        }
        parcelType = in.readString();
        parcelWeight = in.readString();
        if (in.readByte() == 0) {
            userId = null;
        } else {
            userId = in.readInt();
        }
        status = in.readString();
        if (in.readByte() == 0) {
            driverId = null;
        } else {
            driverId = in.readInt();
        }
        dueTime = in.readLong();
        pickedUpTime = in.readLong();
    }

    public static final Creator<PendingOrder> CREATOR = new Creator<PendingOrder>() {
        @Override
        public PendingOrder createFromParcel(Parcel in) {
            return new PendingOrder(in);
        }

        @Override
        public PendingOrder[] newArray(int size) {
            return new PendingOrder[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPickUpAddress() {
        return pickUpAddress;
    }

    public void setPickUpAddress(String pickUpAddress) {
        this.pickUpAddress = pickUpAddress;
    }

    public Float getPickUpLat() {
        return pickUpLat;
    }

    public void setPickUpLat(Float pickUpLat) {
        this.pickUpLat = pickUpLat;
    }

    public Float getPickUpLang() {
        return pickUpLang;
    }

    public void setPickUpLang(Float pickUpLang) {
        this.pickUpLang = pickUpLang;
    }

    public String getDropOffAddress() {
        return dropOffAddress;
    }

    public void setDropOffAddress(String dropOffAddress) {
        this.dropOffAddress = dropOffAddress;
    }

    public Float getDropOffLat() {
        return dropOffLat;
    }

    public void setDropOffLat(Float dropOffLat) {
        this.dropOffLat = dropOffLat;
    }

    public Float getDropOffLang() {
        return dropOffLang;
    }

    public void setDropOffLang(Float dropOffLang) {
        this.dropOffLang = dropOffLang;
    }

    public String getParcelType() {
        return parcelType;
    }

    public void setParcelType(String parcelType) {
        this.parcelType = parcelType;
    }

    public String getParcelWeight() {
        return parcelWeight;
    }

    public void setParcelWeight(String parcelWeight) {
        this.parcelWeight = parcelWeight;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Object getPrice() {
        return price;
    }

    public void setPrice(Object price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public Object getInstruction() {
        return instruction;
    }

    public void setInstruction(Object instruction) {
        this.instruction = instruction;
    }

    public long getDueTime() {
        return dueTime;
    }

    public void setDueTime(long dueTime) {
        this.dueTime = dueTime;
    }

    public long getPickedUpTime() {
        return pickedUpTime;
    }

    public void setPickedUpTime(long pickedUpTime) {
        this.pickedUpTime = pickedUpTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        if (orderNumber == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(orderNumber);
        }
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(address);
        dest.writeString(pickUpAddress);
        if (pickUpLat == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(pickUpLat);
        }
        if (pickUpLang == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(pickUpLang);
        }
        dest.writeString(dropOffAddress);
        if (dropOffLat == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(dropOffLat);
        }
        if (dropOffLang == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(dropOffLang);
        }
        dest.writeString(parcelType);
        dest.writeString(parcelWeight);
        if (userId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(userId);
        }
        dest.writeString(status);
        if (driverId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(driverId);
        }
        dest.writeLong(dueTime);
        dest.writeLong(pickedUpTime);
    }
}