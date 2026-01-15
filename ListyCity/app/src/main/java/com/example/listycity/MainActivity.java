package com.example.listycity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {


    // Variable declaration for later reference
    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

    // Button declaration
    Button buttonAdd;
    Button buttonDelete;

    Button buttonConfirm;
    EditText cityInput;

    int selectedIndex = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Initialize variables
        cityList = findViewById(R.id.city_list);
        buttonAdd = findViewById(R.id.button_add);
        buttonDelete = findViewById(R.id.button_delete);
        cityInput = findViewById(R.id.city_input);
        buttonConfirm = findViewById(R.id.button_confirm);

        // define array
        String []cities = {"Edmonton", "Vancouver", "Moscow", "Sydney", "Berlin", "Vienna", "Tokyo", "Beijing", "Osaka", "New Dehli"};
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        cityList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        //LISTENERS
        //Track which item is selected in the list
        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedIndex = position;
            cityList.setItemChecked(position, true);
        });

        //Add button
        buttonAdd.setOnClickListener(v -> {
            cityInput.setText("");
            cityInput.setVisibility(View.VISIBLE);
            buttonConfirm.setVisibility(View.VISIBLE);
        });

        //Delete button
        buttonDelete.setOnClickListener(v -> {
            if (selectedIndex == -1) {
                Toast.makeText(this, "Select a city to delete", Toast.LENGTH_SHORT).show();
                return;
            }

            String removedCity = dataList.remove(selectedIndex);
            selectedIndex = -1;

            cityList.clearChoices();
            cityAdapter.notifyDataSetChanged();

            Toast.makeText(this, removedCity + " removed", Toast.LENGTH_SHORT).show();
        });
        //Confirm button
        buttonConfirm.setOnClickListener(v -> {
            String newCity = cityInput.getText().toString().trim();

            if (newCity.isEmpty()) {
                Toast.makeText(this, "Enter a city name", Toast.LENGTH_SHORT).show();
                return;
            }

            dataList.add(newCity);
            cityAdapter.notifyDataSetChanged();

            cityInput.setVisibility(View.GONE);
            buttonConfirm.setVisibility(View.GONE);

            Toast.makeText(this, newCity + " added", Toast.LENGTH_SHORT).show();
        });
    }
}