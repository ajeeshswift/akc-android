package com.swift.akc.network;

public final class ApiEndpoint {

    private ApiEndpoint() {}

    private static final String BASE_URL = "http://10.0.2.2:8080";

    public static final String LOGIN_API = BASE_URL + "/login/userValidation";

    public static final String FARMER_DETAILS_API = BASE_URL +"/harvest/farmDetails/{farmNo}";

    public static final String HARVEST_API = BASE_URL + "/harvest/harvestEntry";

    public static final String HARVEST_FORECAST_API = BASE_URL + "/harvestForcasting";


}
