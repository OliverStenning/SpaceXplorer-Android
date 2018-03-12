package co.roguestudios.spacexplorer.datatypes;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.ArrayList;

@Entity
public class Solar {

    @PrimaryKey
    @NonNull
    private String systemName;
    private int statingPlanet;

    private double balance;
    private int rocket;
    private ArrayList<Planet> planets;

    private double income;

    public Solar(@NonNull String systemName, int statingPlanet, double balance, int rocket, ArrayList<Planet> planets) {
        this.systemName = systemName;
        this.statingPlanet = statingPlanet;
        this.balance = balance;
        this.rocket = rocket;
        this.planets = planets;
    }

    public boolean canLaunch(int planet) {
        if (balance >= planets.get(planet).getLaunchCost()) return true;
        else return false;
    }

    public void launchMission(int planet) {
        if (planets.get(planet).getAmount() == 0) {
            planets.get(planet).setTimeRemaining(planets.get(planet).getLaunchTime());
        }
        planets.get(planet).setAmount(planets.get(planet).getAmount() + 1);
    }

    public double calculateIncome() {
        double income = 0;
        for (Planet planet : planets) {
            if (planet.getAmount() > 0) {
                planet.setTimeRemaining(planet.getTimeRemaining() - 1000);
                if (planet.getTimeRemaining() <= 0) {
                    income += planet.getLaunchIncome();
                    planet.setTimeRemaining(planet.getLaunchTime());
                }
            }
        }
        return income;
    }



    @NonNull
    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(@NonNull String systemName) {
        this.systemName = systemName;
    }

    public int getStatingPlanet() {
        return statingPlanet;
    }

    public void setStatingPlanet(int statingPlanet) {
        this.statingPlanet = statingPlanet;
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

    public ArrayList<Planet> getPlanets() {
        return planets;
    }

    public void setPlanets(ArrayList<Planet> planets) {
        this.planets = planets;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }
}
