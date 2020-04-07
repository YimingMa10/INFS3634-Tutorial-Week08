package com.example.week08;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.week08.Entities.Coin;
import com.example.week08.Entities.CoinLoreResponse;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.detail_container) != null) {
            mTwoPane = true;
        }

        MainActivity mainActivity = this;

        RecyclerView mRecyclerView = findViewById(R.id.rvList);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.coinlore.net/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CoinService service = retrofit.create(CoinService.class);

        Call<CoinLoreResponse> coinsCall = service.getCoinLoreResponse();

        coinsCall.enqueue(new Callback<CoinLoreResponse>() {
            @Override
            public void onResponse(Call<CoinLoreResponse> call, Response<CoinLoreResponse> response) {
                if(response.isSuccessful()){
                    Log.d("isSuccessful", "onResponse: DO RESPONSE CODE HERE");

                    CoinLoreResponse coinLoreResponse = response.body();
                    Gson gson = new Gson();
                    CoinLoreResponse.json = gson.toJson(coinLoreResponse);

                    List<Coin> coins = coinLoreResponse.getData();
                    RecyclerView.Adapter mAdapter = new CoinAdapter(mainActivity, coins, mTwoPane);
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    Log.d("!isSuccessful", "onResponse: ERROR IS: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<CoinLoreResponse> call, Throwable t) {
                Log.d("onFailure", "onFailure: ON FAILURE IS " + t.getLocalizedMessage());
            }
        });

        /*
        Gson gson = new Gson();
        CoinLoreResponse response = gson.fromJson(CoinLoreResponse.json, CoinLoreResponse.class);
        List<Coin> coins = response.getData();

         */
    }
}
