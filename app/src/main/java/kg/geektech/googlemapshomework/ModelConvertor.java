package kg.geektech.googlemapshomework;

import androidx.room.TypeConverter;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ModelConvertor {

    @TypeConverter
    public static List<LatLng> toList(String value) {
        if (value == null) {
            return null;
        }
        Type type = new TypeToken<List<LatLng>>(){}.getType();
        return new Gson().fromJson(value, type);
    }

    @TypeConverter
    public static String toString(List<LatLng> list) {
        if (list == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.toJson(list);
    }

}
