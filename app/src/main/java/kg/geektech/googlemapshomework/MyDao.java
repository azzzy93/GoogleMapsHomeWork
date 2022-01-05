package kg.geektech.googlemapshomework;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface MyDao {
    @Query("SELECT * FROM model")
    Model getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Model model);
}
