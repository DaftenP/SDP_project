package com.jpl.sdp_project.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitInterface {

    @GET("/1471000/MdcCompDrugInfoService02/getMdcCompDrugList02")
    Call<Result> getData(@Query("serviceKey") String serviceKey,
                         @Query("pageNo") int pageNo,
                         @Query("numOfRows") int numOfRows,
                         @Query("item_name") String item_name,
                         @Query("ingr_name") String ingr_name,
                         @Query("type") String type);

}