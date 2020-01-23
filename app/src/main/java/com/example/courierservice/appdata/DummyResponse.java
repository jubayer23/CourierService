package com.example.courierservice.appdata;

public class DummyResponse {

    public static String pendingOrder(){
        return "{\n" +
                "\t\"pendingOrders\": [{\n" +
                "\t\t\"id\": 1,\n" +
                "\t\t\"orderNumber\": 123,\n" +
                "\t\t\"name\": \"Md Al Amin\",\n" +
                "\t\t\"email\": \"md_alamin53@yahoo.co.uk\",\n" +
                "\t\t\"address\": \"Salimullah Road (Block-D), Mohammadpur\",\n" +
                "\t\t\"pickUpAddress\": \"P\",\n" +
                "\t\t\"pickUpLat\": 12.34,\n" +
                "\t\t\"pickUpLang\": 33.11,\n" +
                "\t\t\"dropOffAddress\": \"D\",\n" +
                "\t\t\"dropOffLat\": 22.1,\n" +
                "\t\t\"dropOffLang\": 44.1,\n" +
                "\t\t\"parcelType\": \"4\",\n" +
                "\t\t\"parcelWeight\": \"21.87\",\n" +
                "\t\t\"userId\": 1,\n" +
                "\t\t\"price\": null,\n" +
                "\t\t\"status\": \"pending\",\n" +
                "\t\t\"driverId\": 3,\n" +
                "\t\t\"instruction\": null,\n" +
                "\t\t\"dueTime\": 1575310723000\n" +
                "\t}, {\n" +
                "\t\t\"id\": 2,\n" +
                "\t\t\"orderNumber\": 133,\n" +
                "\t\t\"name\": \"Md Al Amin\",\n" +
                "\t\t\"email\": \"md_alamin53@yahoo.co.uk\",\n" +
                "\t\t\"address\": \"Salimullah Road (Block-D), Mohammadpur\",\n" +
                "\t\t\"pickUpAddress\": \"AAA\",\n" +
                "\t\t\"pickUpLat\": 12.34,\n" +
                "\t\t\"pickUpLang\": 33.11,\n" +
                "\t\t\"dropOffAddress\": \"BB CC\",\n" +
                "\t\t\"dropOffLat\": 22.1,\n" +
                "\t\t\"dropOffLang\": 44.1,\n" +
                "\n" +
                "\t\t\"parcelType\": \"2\",\n" +
                "\t\t\"parcelWeight\": \"32.66\",\n" +
                "\t\t\"userId\": null,\n" +
                "\t\t\"price\": null,\n" +
                "\t\t\"status\": \"pending\",\n" +
                "\t\t\"driverId\": 3,\n" +
                "\t\t\"instruction\": null,\n" +
                "\t\t\"dueTime\": 1575310723000\n" +
                "\t}, {\n" +
                "\t\t\"id\": 3,\n" +
                "\t\t\"orderNumber\": 1234,\n" +
                "\t\t\"name\": \"Md Al Amin\",\n" +
                "\t\t\"email\": \"md_alamin53@yahoo.co.uk\",\n" +
                "\t\t\"address\": \"Salimullah Road (Block-D), Mohammadpur\",\n" +
                "\t\t\"pickUpAddress\": \"5/5\",\n" +
                "\t\t\"pickUpLat\": 12.34,\n" +
                "\t\t\"pickUpLang\": 33.11,\n" +
                "\t\t\"dropOffAddress\": \"AA\",\n" +
                "\t\t\"dropOffLat\": 22.1,\n" +
                "\t\t\"dropOffLang\": 44.1,\n" +
                "\t\t\"parcelType\": \"3\",\n" +
                "\t\t\"parcelWeight\": \"453.88\",\n" +
                "\t\t\"userId\": 2,\n" +
                "\t\t\"price\": null,\n" +
                "\t\t\"status\": \"pending\",\n" +
                "\t\t\"driverId\": 3,\n" +
                "\t\t\"instruction\": null,\n" +
                "\t\t\"dueTime\": 1575310723000\n" +
                "\t}]\n" +
                "}";
    }

    public static String getUserInfo(){
        return "{\n" +
                "\t\"status\": true,\n" +
                "\t\"id\": 1,\n" +
                "\t\"username\": \"alamin_sust\",\n" +
                "\t\"email\": \"alaminbbsc@gmail.com\",\n" +
                "\t\"password\": \"11\",\n" +
                "\t\"name\": \"Md Al-Amin\",\n" +
                "\t\"address\": null,\n" +
                "\t\"postCode\": null,\n" +
                "\t\"phone\": \"01711389230\",\n" +
                "\t\"type\": 2\n" +
                "}";
    }
}
