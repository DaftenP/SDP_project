package com.jpl.sdp_project.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitInterface {

    String key = "FbibLpOcunqY9iK%2B0S4zn5XYmhAwfQ5yzZL74ykXbEq%2BQrw9w%2Fu0T0y9tu9I5cAUXWq4%2FpiRbRELSr01pbZWcA%3D%3D";

    @GET("/1471000/MdcCompDrugInfoService02/getMdcCompDrugList02?serviceKey="+key)
    Call<Result> getData(@Query("pageNo") int pageNo,
                         @Query("numOfRows") int numOfRows,
                         @Query("item_name") String item_name,
                         @Query("ingr_name") String ingr_name,
                         @Query("type") String type);

}