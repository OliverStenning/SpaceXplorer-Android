package co.roguestudios.spacexplorer.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import co.roguestudios.spacexplorer.datatypes.Solar;

@Database(entities = {Solar.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class SolarDatabase extends RoomDatabase {

    private static SolarDatabase INSTANCE;
    public abstract SolarDao solarDao();

    public static SolarDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), SolarDatabase.class, "systems").build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
