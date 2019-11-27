package com.rujal.hotelreservationsystem;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView tvSelectCheckInDate, tvSelectedCheckInDate, tvSelectCheckOutDate, tvSelectedCheckOutDate, tvValidationMessage;
    TextView tvAmount, tvVatAmount, tvTotalAmount;
    Spinner selectRoomSpinner;
    EditText etNumberOfAdults, etNumberOfRooms, etNumberOfChilds;
    Button btnCalculate;

    final float vatInPercentage = 13;
    final String[] roomType = Arrays.stream(RoomType.values())
            .map(RoomType::getDisplayText)
            .toArray(String[]::new);

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
        
        btnCalculate.setOnClickListener(i -> calculate());
    }

    private void calculate() {
        if (checkIfAllRequiredFieldsAreFilled() && checkIfAllRequiredFieldsAreValid() && ChronoUnit.DAYS.between(checkInDate.toInstant(), checkOutDate.toInstant()) > 0) {
            resetAllAmounts();
            float numberOfRooms = Float.valueOf(etNumberOfRooms.getText().toString());
            float costOfPerRoom = getCostOfRoom(RoomType.valueOfLabel(selectRoomSpinner.getSelectedItem().toString()));
            long daysStaying = ChronoUnit.DAYS.between(checkInDate.toInstant(), checkOutDate.toInstant());

            if (costOfPerRoom == 0) {
                tvValidationMessage.setText("Please enter valid values");
                return;
            }

            float amount = numberOfRooms * costOfPerRoom * daysStaying ;
            float vatAmount = vatInPercentage/100 * (amount);
            float totalAmount = amount + vatAmount;

            setAllAmountsInUI(amount, vatAmount, totalAmount);

        } else {
            tvValidationMessage.setText("Please enter valid values");
        }
    }

    private void resetAllAmounts() {
        tvAmount.setText("Amount: ");
        tvVatAmount.setText("Vat Amount: ");
        tvTotalAmount.setText("Total Amount: ");
        tvValidationMessage.setText("");
    }

    private void setAllAmountsInUI(float amount, float vatAmount, float totalAmount) {
        tvAmount.setText(tvAmount.getText().toString().concat(String.format("%.2f", amount)));
        tvVatAmount.setText(tvVatAmount.getText().toString().concat(String.format("%.2f", vatAmount)));
        tvTotalAmount.setText(tvTotalAmount.getText().toString().concat(String.format("%.2f", totalAmount)));
    }

    private float getCostOfRoom(RoomType roomType) {
        switch (roomType) {
            case DELUXE:
                return RoomType.DELUXE.getCost();
            case PREMIUM:
                return RoomType.PREMIUM.getCost();
            case PRESIDENTIAL:
                return RoomType.PRESIDENTIAL.getCost();
            default:
                return RoomType.NONE.getCost();
        }
    }

    private boolean checkIfAllRequiredFieldsAreValid() {
        try {
            Float.parseFloat(etNumberOfAdults.getText().toString());
            Float.parseFloat(etNumberOfRooms.getText().toString());
            Float.parseFloat(etNumberOfChilds.getText().toString());

            return true;
        } catch (Exception e) {
            return  false;
        }
    }

    private boolean checkIfAllRequiredFieldsAreFilled() {
        System.out.println(selectRoomSpinner.getSelectedItem());
        return !tvSelectedCheckInDate.getText().toString().isEmpty() &&
                !tvSelectedCheckOutDate.getText().toString().isEmpty() &&
                !String.valueOf(selectRoomSpinner.getSelectedItem()).equals(RoomType.NONE) &&
                !etNumberOfRooms.getText().toString().isEmpty() &&
                !etNumberOfAdults.getText().toString().isEmpty() &&
                !etNumberOfChilds.getText().toString().isEmpty();
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
        tvVatAmount = findViewById(R.id.tvVatAmount);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);

        selectRoomSpinner = findViewById(R.id.spinSelectRoom);

        tvValidationMessage = findViewById(R.id.tvValidationMessage);

        btnCalculate = findViewById(R.id.btnCalculate);
    }

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
        }, year, month, day);

        datePickerDialog.show();

    }
}
