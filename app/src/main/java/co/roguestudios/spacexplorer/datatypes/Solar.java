package co.roguestudios.spacexplorer.datatypes;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.ArrayList;

@Entity
public class Solar {

    @PrimaryKey
    @NonNull
    private String systemName;
    private double balance;
    private int rocket;
    private ArrayList<Launch> launches;

    private double income;

    //Constructors
    public Solar(@NonNull String systemName, double balance, int rocket, ArrayList<Launch> launches, double income) {
        this.systemName = systemName;
        this.balance = balance;
        this.rocket = rocket;
        this.launches = launches;
        this.income = income;
    }

    //Methods
    public boolean canLaunch(int planet) {
        if (balance >= launches.get(planet).getLaunchCost()) return true;
        else return false;
    }

    public void launchMission(int planet) {
        launches.get(planet).setAmount(launches.get(planet).getAmount() + 1);
    }

    public double calculateIncome() {
        double income = 0;
        for (Launch launch : launches) {
            income += launch.getLaunchIncome();
        }
        return income;
    }

    //Getters and Setters
    @NonNull
    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(@NonNull String systemName) {
        this.systemName = systemName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getRocket() {
        return rocket;
    }

    public void setRocket(int rocket) {
        this.rocket = rocket;
    }

    public ArrayList<Launch> getLaunches() {
        return launches;
    }

    public void setLaunches(ArrayList<Launch> launches) {
        this.launches = launches;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

}