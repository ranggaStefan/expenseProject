package com.example.expenseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.expenseproject.databinding.HomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class HomeActivity extends AppCompatActivity implements ClickEvent{

    private BottomNavigationView bottomNav;

    HomeBinding binding;
    ExpenseAdapter expenseAdapter;
    ExpenseDatabase expenseDatabase;
    ExpenseDao expenseDao;
    long totalExpense = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = HomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bottomNav = findViewById(R.id.bottom_navigation);

        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.addNav) {
                    Intent addIntent = new Intent(HomeActivity.this, AddActivity.class);
                    startActivity(addIntent);
                    return true;
                } else if (item.getItemId() == R.id.historyNav) {
                    Intent historyIntent = new Intent(HomeActivity.this, HistoryActivity.class);
                    startActivity(historyIntent);
                    return true;
                } else {
                    return false;
                }
            }
        });

    };

    @Override
    protected void onResume() {
        super.onResume();
        expenseDatabase = ExpenseDatabase.getInstance(this);
        expenseDao = expenseDatabase.getDao();
        expenseAdapter = new ExpenseAdapter(this, this);

        binding.itemsRecycler.setAdapter(expenseAdapter);
        binding.itemsRecycler.setLayoutManager(new LinearLayoutManager(this));

        List<ExpenseTable> expenseTables = expenseDao.getAll();

        Collections.sort(expenseTables, (o1, o2) -> o2.getExpenseDate().compareTo(o1.getExpenseDate()));

        if (totalExpense!=0) totalExpense = 0;

        for (int i = 0; i<expenseTables.size(); i++){
            totalExpense += expenseTables.get(i).getExpenseAmount();
            expenseAdapter.add(expenseTables.get(i));
        }


        binding.totalExpense.setText("Rp" + String.format("%,d", (int)totalExpense));
    }

    @Override
    public void OnClick(int pos) {
        int id = expenseAdapter.getId(pos);
        expenseDao.delete(id);
        expenseAdapter.delete(pos);
        Toast.makeText(getApplicationContext(), "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
        onResume();
    }
}