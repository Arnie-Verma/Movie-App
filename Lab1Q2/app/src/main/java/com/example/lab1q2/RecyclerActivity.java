
package com.example.lab1q2;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.lab1q2.provider.MovieViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class RecyclerActivity extends AppCompatActivity {

    RecyclerAdapter adapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private MovieViewModel movieVM;

    Gson gson = new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);

        recyclerView=findViewById(R.id.recycler_layout_id);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

   //     String dataStr = getIntent().getStringExtra("JSON");

    //    Type type = new TypeToken<ArrayList<MovieDetails>>() {}.getType();


    //    ArrayList db = gson.fromJson(dataStr, type);
        //dataSource=new ArrayList<>();
        adapter=new RecyclerAdapter();
        recyclerView.setAdapter(adapter);
        movieVM = new ViewModelProvider(this).get(MovieViewModel.class);
        movieVM.getMovies().observe(this, newData -> {
            adapter.setMovies(newData);
            adapter.notifyDataSetChanged();
    });

    }}