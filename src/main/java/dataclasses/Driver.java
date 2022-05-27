package dataclasses;

public class Driver {
    private String license;
    private String firstName;
    private String lastName;

    public Driver(String license, String firstName, String lastName) {
        this.license = license;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getLicense() {
        return license;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
