package co.roguestudios.spacexplorer.data;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import co.roguestudios.spacexplorer.datatypes.Launch;

public class Converters {

    @TypeConverter
    public static ArrayList<Launch> planetsFromString(String value) {
        Type listType = new TypeToken<ArrayList<Launch>>(){}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String stringFromPlanets(ArrayList<Launch> launches) {
        Gson gson = new Gson();
        return gson.toJson(launches);
    }

}
