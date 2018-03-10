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
    private int statingPlanet;

    private double balance;
    private int rocket;
    private ArrayList<Planet> planets;

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
        planets.get(planet).setAmount(planets.get(planet).getAmount() + 1);
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
}
