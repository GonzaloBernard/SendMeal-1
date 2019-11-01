package com.example.sendmeal.dao.database;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.sendmeal.domain.EstadoPedido;

import java.util.Date;

public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }
    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static String fromEstadoPedidoToString(EstadoPedido estadoPedido) {
        if (estadoPedido == null)
            return null;
        return estadoPedido.toString();
    }

    @TypeConverter
    public static EstadoPedido fromStringToEstadoPedido(String estadoPedido) {
        if (estadoPedido.isEmpty())
            return null;
        return EstadoPedido.valueOf(estadoPedido);
    }
}
