package co.roguestudios.spacexplorer.datatypes;

public class Planet {

    private String planetName;
    private int amount;
    private double clickAmount;
    private double income;
    private double bonus;
    private double cost;
    private double rate;
    private double discount;
    private long launchTime;
    private double speed;
    private int rocket;
    private long finishTime;

    public Planet(String planetName, double income, double bonus, double cost, double rate, long launchTime, double speed, int rocket, long finishTime) {
        this.planetName = planetName;
        this.income = income;
        this.bonus = bonus;
        this.cost = cost;
        this.rate = rate;
        this.launchTime = launchTime;
        this.speed = speed;
        this.rocket = rocket;
        this.finishTime = finishTime;
    }

    public double getLaunchCost() {
        return Math.floor(Math.pow(rate, amount) * cost * discount);
    }


    public String getPlanetName() {
        return planetName;
    }

    public void setPlanetName(String planetName) {
        this.planetName = planetName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getClickAmount() {
        return clickAmount;
    }

    public void setClickAmount(double clickAmount) {
        this.clickAmount = clickAmount;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public long getLaunchTime() {
        return launchTime;
    }

    public void setLaunchTime(long launchTime) {
        this.launchTime = launchTime;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getRocket() {
        return rocket;
    }

    public void setRocket(int rocket) {
        this.rocket = rocket;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }
}
