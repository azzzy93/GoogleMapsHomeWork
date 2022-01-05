package kg.geektech.googlemapshomework;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Model.class}, version = 1)
@TypeConverters({ModelConvertor.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract MyDao dao();
}
