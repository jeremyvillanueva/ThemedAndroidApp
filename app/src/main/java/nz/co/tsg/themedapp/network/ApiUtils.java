package nz.co.tsg.themedapp.network;

/**
 * Created by Jeremy.V on 8/08/17.
 */

public class ApiUtils {



    public static final String BASE_URL = "http://colormind.io/";

    public static ColormindService getColormindService() {
            return RetrofitClient.getClient(BASE_URL).create(ColormindService.class);
    }
}
