package co.roguestudios.spacexplorer.viewmodels;

import android.os.AsyncTask;

import co.roguestudios.spacexplorer.datatypes.Solar;

public class PlanetModel extends BaseModel {

    public void clickLaunch(int planet) {
        new clickLaunch().execute(planet);
    }

    private class clickLaunch extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... planet) {
            Solar system = db.solarDao().getSystem(systemName);
            if (system.canLaunch(planet[0])) {
                system.setBalance(system.getBalance() - system.getPlanets().get(planet[0]).getLaunchCost());
                system.launchMission(planet[0]);
                db.solarDao().setSystem(system);
            }
            return null;
        }
    }

    public void clickPlanet(int planet) {
        new clickPlanet().execute(planet);
    }

    private class clickPlanet extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... planets) {
            changeBalance(db.solarDao().getSystem(systemName).getPlanets().get(planets[0]).getClickAmount());
            return null;
        }
    }

}
