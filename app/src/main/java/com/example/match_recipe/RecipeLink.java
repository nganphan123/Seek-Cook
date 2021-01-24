package com.example.match_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecipeLink extends AppCompatActivity {
    String category;
    ArrayList<String> ingredients;
    ListView mRecipeListView;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference recipeRef;
    private Button cook;
    private ArrayList<String> arrayList=new ArrayList<String>();;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_link);
//        category=getIntent().getStringExtra("Category");
        ingredients= getIntent().getStringArrayListExtra("Ingredients");

        cook = (Button) findViewById(R.id.cook);

    }
    @Override
    protected void onStart(){
        super.onStart();
//        recipeRef= mRootRef.child(category);
//        HashMap<String,Boolean> inputs = new HashMap<>();
//        for (String i : ingredients) {
//            inputs.put(i, false);
//        }
//
//        mRootRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot tasksSnapshot) {
//                HashMap<String, Object> database = (HashMap<String, Object>) tasksSnapshot.child("dessert").getValue();
//                SearchRecipe tool = new SearchRecipe();
//                String text = tool.search(database,inputs);
//                System.out.println(text);
//                mRecipeTextView.setText(text);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//
//            }
//        });

        mRecipeListView=(ListView)findViewById(R.id.recipe);

        OkHttpClient client = new OkHttpClient();

        String url1 = "https://food2fork.ca/api/recipe/search/?page=1&query=";

        for(String i : ingredients){

            i = i.toLowerCase();

            url1+=i.concat("+");

        }

        url1 = url1.substring(0,url1.length()-1);

        System.out.println(url1);

        Request request = new Request.Builder()
                .url(url1)
                .get()
                .addHeader("Authorization", "Token 9c8b06d329136da358c2d00e76946b0111ce2c48")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(response.isSuccessful()){
                    String myResponse = response.body().string();
                    System.out.println(myResponse);
                    JSONObject jsonObject;

                    try {

                        jsonObject = new JSONObject(myResponse);

                        JSONArray ingredients =(JSONArray) jsonObject.getJSONArray("results").getJSONObject(0).get("ingredients");

                        String url2 = jsonObject.getJSONArray("results").getJSONObject(0).getString("source_url");

                        for(int i =0; i<ingredients.length();i++){
                            arrayList.add(ingredients.get(i).toString());
                        }

                        adapter=new ArrayAdapter<String>(RecipeLink.this,R.layout.list_recipe,R.id.recipeItem,arrayList);

                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                mRecipeListView.setAdapter(adapter);

                                cook.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {

                                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url2));

                                        startActivity(browserIntent);
                                    }
                                });
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }
}