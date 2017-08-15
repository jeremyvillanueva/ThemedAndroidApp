package nz.co.tsg.themedapp.network;

/**
 * Created by Jeremy.V on 8/08/17.
 */


import com.google.gson.JsonObject;

import nz.co.tsg.themedapp.model.GeneratePaletteResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ColormindService {

    @POST("api/")
    Call<GeneratePaletteResponse> fetchPalette(
            @Body JsonObject jsonObject
    );


}