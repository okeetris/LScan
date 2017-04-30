package model;

/**
 * Created by 788340 on 25/03/2017.
 */

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class FoodResponse {

    @SerializedName("total_hits")
    private int totalHits;
    @SerializedName("max_score")
    private int maxScore;

    public int getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(int totalHits) {
        this.totalHits = totalHits;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }


}
