package com.example.week08;

import com.example.week08.Entities.CoinLoreResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CoinService {
    @GET("tickers")
    Call<CoinLoreResponse> getCoinLoreResponse();
}
