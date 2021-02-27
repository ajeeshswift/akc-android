package com.swift.akc.network;

public final class ApiEndpoint {

    private ApiEndpoint() {}

    private static final String BASE_URL = "http://192.168.0.101:8080";

    public static final String LOGIN_API = BASE_URL + "/login/userValidation";

    public static final String GETTING_FLORA_API = BASE_URL +"/flora/floraDetails";

    public static final String HARVEST_API = BASE_URL + "/harvest/harvestEntry";

    public static final String HARVEST_FORECAST_API = BASE_URL + "/harvestForcasting";


}
