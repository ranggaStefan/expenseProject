package com.example.expenseproject;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;


import com.example.expenseproject.databinding.AddExpenseBinding;

import java.sql.Date;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class AddActivity
        extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {

    AddExpenseBinding binding;
    Calendar calendar = Calendar.getInstance();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AddExpenseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TextView inputDate = (TextView) findViewById(R.id.expenseDate);
        inputDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        inputDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        View backToHome = findViewById(R.id.addGoHome);
        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Spinner spinnerCategory = (Spinner)findViewById(R.id.expenseCategory);
        spinnerCategory.setOnItemSelectedListener(this);


        String[] category = { "Kategori", "Transportasi", "Makanan", "Shopping","Tagihan" };

        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                category);


        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        spinnerCategory.setAdapter(ad);



        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalDate date = LocalDateTime.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId()).toLocalDate();
                String category = binding.expenseCategory.getSelectedItem().toString();
                String desc = binding.expenseDesc.getText().toString();
                String amount = binding.expenseAmount.getText().toString();

                ExpenseTable expenseTable = new ExpenseTable();

                expenseTable.setExpenseDate(date);
                expenseTable.setExpenseCategory(category);
                expenseTable.setExpenseDescription(desc);
                expenseTable.setExpenseAmount(Long.parseLong(amount));

                ExpenseDatabase expenseDatabase = ExpenseDatabase.getInstance(view.getContext());
                ExpenseDao expenseDao = expenseDatabase.getDao();

                expenseDao.insertExpense(expenseTable);

                Toast.makeText(getApplicationContext(), "Input Data Berhasil", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String dateSet = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        TextView inputDate = (TextView) findViewById(R.id.expenseDate);
        inputDate.setText(dateSet);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
