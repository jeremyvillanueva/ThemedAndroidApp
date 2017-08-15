package nz.co.tsg.themedapp.model;

/**
 * Created by Jeremy.V on 7/08/17.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GeneratePaletteResponse {

    @SerializedName("result")
    @Expose
    private List<List<Integer>> palette = null;


    public List<List<Integer>> getResult() {
        return palette;
    }

    public void setResult(List<List<Integer>> result) {
        this.palette = result;
    }



}