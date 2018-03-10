package co.roguestudios.spacexplorer.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

import co.roguestudios.spacexplorer.data.SolarDatabase;
import co.roguestudios.spacexplorer.datatypes.Solar;

public class BaseModel extends ViewModel {

    private static final boolean TESTING = false;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    protected SolarDatabase db;
    protected String systemName;
    protected Context appContext;

    public void loadDatabase(Context context) {
        appContext = context.getApplicationContext();

        System.out.println("Made database");
        db = SolarDatabase.getDatabase(context);

        //TODO load data into database
        try {
            String[] systemList = context.getAssets().list("Systems");
            if (systemList != null) {
                new loadSystems().execute(systemList);
            }
        } catch (IOException e) {
            Log.e("Loading", "Loading system data");
        }

    }

    public void getDatabase(Context context) {
        db = SolarDatabase.getDatabase(context);
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }
    public String getSystemName() {
        return systemName;
    }

    public LiveData<Double> getBalanceLive() {
        return db.solarDao().getBalanceLive(systemName);
    }

    public LiveData<Solar> getSystemLive() {
        return db.solarDao().getSolarSystemLive(systemName);
    }

    private class loadSystems extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... systemList) {
            for (String systemFileName : systemList) {

                if (db.solarDao().getSystem(systemFileName) == null) {
                    //Create system from file
                    try {
                        String url = "Systems/" + systemFileName + "/SystemInfo.json";
                        System.out.println(url);
                        InputStream inputStream = appContext.getAssets().open(url);
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                        Type type = new TypeToken<Solar>(){}.getType();
                        Solar newSystem = gson.fromJson(inputStreamReader, type);
                        db.solarDao().insertAll(newSystem);
                        System.out.println(newSystem.toString());
                    } catch (IOException e) {
                        Log.e("Loading", "Loading system data");
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }
    }

    protected void changeBalance(double amount) {
        db.solarDao().setBalance(systemName, db.solarDao().getBalance(systemName) + amount);
    }

}