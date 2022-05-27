package dataclasses;

public class Car {
    private String model;
    private String plateNumber;
    private String color;
    private String insurance;
    private int finesCount;

    public Car(String model, String plateNumber, String color, String insurance, int finesCount){
        this.model = model;
        this.plateNumber = plateNumber;
        this.color = color;
        this.insurance = insurance;
        this.finesCount = finesCount;
    }

    public String getModel() {
        return model;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public String getColor() {
        return color;
    }

    public String getInsurance() {
        return insurance;
    }

    public int getFinesCount() {
        return finesCount;
    }
}
