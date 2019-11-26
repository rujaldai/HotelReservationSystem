package com.rujal.hotelreservationsystem;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView tvSelectCheckInDate, tvSelectedCheckInDate, tvSelectCheckOutDate, tvSelectedCheckOutDate;
    TextView tvAmount, tvTaxAmount, tvVatAmount, tvTotalAmount;
    Spinner selectRoomSpinner;
    EditText etNumberOfAdults, etNumberOfRooms, etNumberOfChilds;
    Button btnCalculate;

    final String[] roomType = {" -- Select room type -- ", "Premium", "Presedential", "Deluxe"};

    /*
     * Creating calendar instance just so we can set it later (because we can not set to null)
     */
    Calendar checkInDate = Calendar.getInstance();
    Calendar checkOutDate = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindUi();

        selectRoomSpinner.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, roomType) {
        });

        tvSelectCheckOutDate.setOnClickListener(i -> addCheckInCheckOutDate(tvSelectedCheckOutDate, checkOutDate));
        tvSelectCheckInDate.setOnClickListener(i -> addCheckInCheckOutDate(tvSelectedCheckInDate, checkInDate));

    }

    private void bindUi() {
        tvSelectCheckInDate = findViewById(R.id.tvSelectCheckInDate);
        tvSelectedCheckInDate = findViewById(R.id.tvSelectedCheckInDate);
        tvSelectCheckOutDate = findViewById(R.id.tvSelectCheckOutDate);
        tvSelectedCheckOutDate = findViewById(R.id.tvSelectedCheckOutDate);

        etNumberOfAdults = findViewById(R.id.etNumberOfAdults);
        etNumberOfChilds = findViewById(R.id.etNumberOfChilds);
        etNumberOfRooms = findViewById(R.id.etNumberOfRooms);

        tvAmount = findViewById(R.id.tvNetAmount);
        tvTaxAmount = findViewById(R.id.tvTaxAmount);
        tvVatAmount = findViewById(R.id.tvVatAmount);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);

        selectRoomSpinner = findViewById(R.id.spinSelectRoom);
    }

    @TargetApi(26)
    private void addCheckInCheckOutDate(TextView textView, Calendar variableToSet) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                variableToSet.set(year, month, dayOfMonth);
                textView.setText( month + "/" + dayOfMonth + "/" + year);

            }
        },
                year, month, day);
        datePickerDialog.show();

    }
}
