package nz.co.tsg.themedapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Jeremy.V on 8/08/17.
 */

public class GeneratePaletteRequest {

    @SerializedName("model")
    @Expose
    private String model = "default";

    public GeneratePaletteRequest(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }



}
