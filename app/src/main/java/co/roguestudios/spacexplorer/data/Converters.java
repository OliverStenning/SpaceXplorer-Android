package co.roguestudios.spacexplorer.data;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import co.roguestudios.spacexplorer.datatypes.Planet;

public class Converters {

    @TypeConverter
    public static ArrayList<Planet> planetsFromString(String value) {
        Type listType = new TypeToken<ArrayList<Planet>>(){}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String stringFromPlanets(ArrayList<Planet> planets) {
        Gson gson = new Gson();
        return gson.toJson(planets);
    }

}
