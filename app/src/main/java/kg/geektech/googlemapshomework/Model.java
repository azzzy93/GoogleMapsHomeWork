package kg.geektech.googlemapshomework;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

@Entity
public class Model {
    @PrimaryKey
    private Integer id;
    @TypeConverters({ModelConvertor.class})
    private List<LatLng> listLatLng;

    public Model() {
    }

    public List<LatLng> getListLatLng() {
        return listLatLng;
    }

    public void setListLatLng(List<LatLng> listLatLng) {
        this.listLatLng = listLatLng;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
