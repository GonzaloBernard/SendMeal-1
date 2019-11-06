package com.example.sendmeal.dao.database;
import androidx.room.TypeConverter;
import com.example.sendmeal.dao.PlatoRepository;
import com.example.sendmeal.domain.EstadoPedido;
import com.example.sendmeal.domain.Plato;
import java.util.Date;

public class Converters {
    //convierte el tipo date a tipo long y viceversa,para la BD
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }
    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    //de EstadoPedido a String, convierto el EstadoPedido de la bd al string que le corresponde en EstadoPedido
    @TypeConverter
    public static String deEstadoPedidoAString(EstadoPedido estadoPedido) {
        if (estadoPedido == null)
            return null;
        return estadoPedido.toString();
    }

    //de String a EstadoPedido, convierto el string de la BD al enum que le corresponde en EstadoPedido
    @TypeConverter
    public static EstadoPedido deStringAEstadoPedido(String estadoPedido) {
        if (estadoPedido.isEmpty())
            return null;
        return EstadoPedido.valueOf(estadoPedido);
    }

    //De plato a integer, tomo el id de plato para almacenarlo
    @TypeConverter
    public static Integer dePlatoAInteger(Plato plato) {
        if (plato == null) {
            return null;
        } else {
            return plato.getId();
        }
    }

    // De Integer a plato, tomo el id de plato para recuperar el plato
    @TypeConverter
    public static Plato deIntegerAPlato (Integer id){
        if(id==null)
            return null;
        else
            return PlatoRepository.getInstance().getPlatoById(id);
    }
}
