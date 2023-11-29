package com.example.expenseproject;

import androidx.annotation.Nullable;
import androidx.room.TypeConverter;
import java.time.LocalDate;


public class LocalDateConverter {
    @TypeConverter
    public static LocalDate fromLong(@Nullable Long epoch) {
        return epoch == null ? null : LocalDate.ofEpochDay(epoch);
    }

    @TypeConverter
    public static Long localDateToEpoch(@Nullable LocalDate localDate) {
        return localDate == null ? null : localDate.toEpochDay();
    }
}
