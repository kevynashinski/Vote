package com.ryletech.vote;

/**
 * Created by sydney on 6/26/2016.
 */
public final class AppConfig {

    public static final String TAG = "vote";


    public static final String RESULTS = "results";
    public static final String STATUS = "status";

    public static final String OK = "OK";
    public static final String ZERO_RESULTS = "ZERO_RESULTS";
    public static final String REQUEST_DENIED = "REQUEST_DENIED";
    public static final String OVER_QUERY_LIMIT = "OVER_QUERY_LIMIT";
    public static final String UNKNOWN_ERROR = "UNKNOWN_ERROR";
    public static final String INVALID_REQUEST = "INVALID_REQUEST";

    //    Key for nearby locations
    public static final String GEOMETRY = "geometry";
    public static final String LOCATION = "location";
    public static final String LATITUDE = "lat";
    public static final String LONGITUDE = "lng";
    public static final String ICON = "icon";
    public static final String SUPERMARKET_ID = "id";

    //    public static final String SUPERMARKET_NAME = "supermarket_name";
    public static final String SUPERMARKET_NAME = "name";
    public static final String PLACE_ID = "place_id";
    public static final String REFERENCE = "reference";
    public static final String VICINITY = "vicinity";
    public static final String PLACE_NAME = "place_name";

    //    user's account
    public static final String ID_NUMBER = "id_number";
    public static final String PASSWORD = "password";
    public static final String GENDER = "gender";
    public static final String EMAIL_ADDRESS = "email_address";
    public static final String PHONE_NUMBER = "phone_number";

    //    Keys for orders
    public static final String ORDERS_JSON = "orders";

    public static final String GOOGLE_BROWSER_API_KEY = "AIzaSyA_iXa9oR6cwbmYS6BjFW8xiXeoezeRXOk";
    public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final int PROXIMITY_RADIUS = 5000;
    // The minimum distance to change Updates in meters
    public static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    public static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    //    keys for intent passing data
    public static final String INTENT_SUPERMARKETSACTIVITY_CATEGORIESACTIVITY_DATA = "intent_supermarketactivity_categoriesactivity_data";
    public static final String INTENT_CATEGORIESACTIVITY_PRODUCTSACTIVITY_DATA = "intent_categoriesactivity_productaactivity_data";

    public static final String FILENAME_NEARBY_PLACES = "nearby_places.json";

    //    keys for categories
    public static final String CATEGORY_ID = "category_id";
    public static final String CATEGORY_NAME = "category_name";
    public static final String CATEGORY_DESCRIPTION = "category_description";
    public static final String CATEGORY_ICON = "category_icon";

    //    keys for orders
    public static final String ORDER_ID = "product_id";
    public static final String ORDERED_DATE = "ordered_date";


    //    keys for product
    public static final String PRODUCT_ID = "product_id";
    public static final String COUNTY_NAME = "county_name";
    public static final String PRESIDENT_NAME = "president_name";
    public static final String GOVERNOR_NAME = "governor_name";
    public static final String SENATOR_NAME = "senator_name";
    public static final String WOMAN_REP_NAME = "woman_rep_name";
    //    preferences
    public static final String FIRST_RUN = "first_run";
    public static final String VOTED = "voted";
//    private static final String SERVER_URL = "https://orders-kevynashinski.c9users.io/";
private static final String SERVER_URL = "http://192.168.58.1/opinionpolls/";
    //    private static final String SERVER_URL = "http://ecea9b2e.ngrok.io/orders/";
    public static final String LOGIN_URL = SERVER_URL + "auth.php";
    public static final String REGISTER_URL = SERVER_URL + "register.php";
    public static final String VOTE_URL = SERVER_URL + "vote.php";
    public static final String PRESIDENTS_URL = SERVER_URL + "presidents.php";
    public static final String GOVERNORS_URL = SERVER_URL + "governors.php";
    public static final String SENATORS_URL = SERVER_URL + "senators.php";
    public static final String WOMEN_REPS_URL = SERVER_URL + "womenreps.php";
    public static final String COUNTIES_URL = SERVER_URL + "counties.php";
    public static final String USER_ACCOUNTS_URL = SERVER_URL + "user_account.php";
}
