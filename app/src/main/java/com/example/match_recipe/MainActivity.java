package com.example.match_recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter;
    private EditText ingreInput;
    private EditText cateInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_ingredient);

        //Read and add ingredients
        ListView listView=(ListView)findViewById(R.id.listv);
        String[] items = {};
        arrayList=new ArrayList<String>(Arrays.asList(items));
        adapter=new ArrayAdapter<String>(this,R.layout.list_item,R.id.txtitem,arrayList);
        listView.setAdapter(adapter);
        ingreInput=(EditText)findViewById(R.id.txtinput);
        Button btAdd=(Button) findViewById(R.id.add);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newItem=ingreInput.getText().toString();
                arrayList.add(newItem);
                adapter.notifyDataSetChanged();
                ingreInput.getText().clear();
            }
        });

        //delete ingredient
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> theAdapter, View v, int position, long arg3) {
                String value = (String) theAdapter.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), value + " is removed", Toast.LENGTH_LONG).show();
                arrayList.remove(value);
                adapter.notifyDataSetChanged();
            }
        });

        //Read category
        cateInput=(EditText)findViewById(R.id.category);

        //Search function
        Button btSearch=(Button) findViewById(R.id.search);
        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = cateInput.getText().toString();
                Intent intent = new Intent (MainActivity.this,RecipeLink.class);
                intent.putExtra("Ingredients",arrayList);
                intent.putExtra("Category",category);
                System.out.println(category);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onStart(){
        super.onStart();

    }
}