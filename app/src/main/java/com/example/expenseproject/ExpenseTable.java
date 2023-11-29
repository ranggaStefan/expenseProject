package com.example.expenseproject;


import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.time.LocalDate;

@Entity(tableName = "expense")
public class ExpenseTable {
    @PrimaryKey (autoGenerate = true)
    private int id;
    @TypeConverters(LocalDateConverter.class)
    private LocalDate expenseDate;
    private String expenseCategory;
    private String expenseDescription;
    private long expenseAmount;

    public ExpenseTable() {
    }

    public ExpenseTable(int id, LocalDate expenseDate, String expenseCategory, String expenseDescription, int expenseAmount) {
        this.id = id;
        this.expenseDate = expenseDate;
        this.expenseCategory = expenseCategory;
        this.expenseDescription = expenseDescription;
        this.expenseAmount = expenseAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(LocalDate expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getExpenseCategory() {
        return expenseCategory;
    }

    public void setExpenseCategory(String expenseCategory) {
        this.expenseCategory = expenseCategory;
    }

    public String getExpenseDescription() {
        return expenseDescription;
    }

    public void setExpenseDescription(String expenseDescription) {
        this.expenseDescription = expenseDescription;
    }

    public long getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(long expenseAmount) {
        this.expenseAmount = expenseAmount;
    }
}

