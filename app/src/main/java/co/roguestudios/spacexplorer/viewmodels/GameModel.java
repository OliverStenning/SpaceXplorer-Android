package co.roguestudios.spacexplorer.viewmodels;

import java.util.Timer;
import java.util.TimerTask;

import co.roguestudios.spacexplorer.datatypes.Solar;

public class GameModel extends BaseModel {

    private Timer timer;

    public void startTick() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Solar system = db.solarDao().getSystem(systemName);
                if (system != null) {
                    double income = system.calculateIncome();
                    system.setIncome(income);
                    if (income > 0) {
                        system.setBalance(system.getBalance() + income);
                    }
                    db.solarDao().setSystem(system);
                }
            }
        }, 0, 1000);
    }

    public void stopTick() {
        if (timer != null) {
            timer.cancel();
        }
    }

}
