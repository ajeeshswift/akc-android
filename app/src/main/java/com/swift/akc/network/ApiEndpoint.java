package com.swift.akc.network;

public final class ApiEndpoint {

    private ApiEndpoint() {}

    private static final String BASE_URL = "http://172.16.44.160:8080";

    public static final String LOGIN_API = BASE_URL + "/login/userValidation";

    public static final String FARMER_DETAILS_API = BASE_URL +"/harvest/farmDetails/{farmNo}";

    public static final String FLORA_AUTOCOMPLETE_API = BASE_URL +"/floraController/getFlora";

    public static final String HARVEST_API = BASE_URL + "/harvest/harvestEntry";

    public static final String HARVEST_FORECAST_API = BASE_URL + "/harvestForcasting";

    public static final String HARVEST_VISIT_LIST_API = BASE_URL + "/harvest/harvestList";

    public static final String HARVEST_VISIT_BET_DATE_API = BASE_URL + "/harvest/harvestListBetDate/{harvestDateFrom}/{harvestDateTo}";

    public static final String HARVEST_FORCASTING_LIST_API = BASE_URL + "/harvestForcastingList";
}
