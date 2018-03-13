package co.roguestudios.spacexplorer.datatypes;

public class Launch {

    private String code;
    private String name;
    private String planet;
    private String type;
    private int amount;
    private double income;
    private double bonus;
    private double cost;
    private double rate;
    private double discount;
    private int rocket;

    //Constructors
    public Launch(String code, String name, String planet, String type, int amount, double income, double bonus, double cost, double rate, double discount, int rocket) {
        this.code = code;
        this.name = name;
        this.planet = planet;
        this.type = type;
        this.amount = amount;
        this.income = income;
        this.bonus = bonus;
        this.cost = cost;
        this.rate = rate;
        this.discount = discount;
        this.rocket = rocket;
    }

    //Methods
    public double getLaunchCost() {
        return Math.floor(Math.pow(rate, amount) * cost * discount);
    }

    public double getLaunchIncome() {
        return income * bonus * amount;
    }

    //Getters and Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlanet() {
        return planet;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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

    public int getRocket() {
        return rocket;
    }

    public void setRocket(int rocket) {
        this.rocket = rocket;
    }
}