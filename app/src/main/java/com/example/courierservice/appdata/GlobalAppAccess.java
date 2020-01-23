package com.example.courierservice.appdata;




public class GlobalAppAccess {

    private static final String BASE_URL = "https://IPS-Systems.com";

    public static final String URL_LOGIN = BASE_URL + "/driverLogin";
    public static final String URL_PENDING_ORDER = BASE_URL + "/pendingOrderList";
    public static final String URL_START_ORDER = BASE_URL + "/startOrder";
    public static final String URL_GET_LICENSE_HISTORY = BASE_URL + "/ParkingSentinel/GetLicenseHistory";
    public static final String URL_CREATE_SIGHTING = BASE_URL + "/ParkingSentinel/CreateSighting";



    public static final String DATE_FORMAT_LOCAL = "hh:mmaa dd MMM yyyy";

    public static final  int SUCCESS = 1;
    public static  final  int ERROR = 0;


    public static final String CAT_FAVOURITE = "Favourite";
    public static final String CAT_FAVOURITE_ID = "-100";
    public static final String CAT_EMERGENCY = "Emergency";
    public static final String CAT_EMERGENCY_ID = "Emergency";

}
