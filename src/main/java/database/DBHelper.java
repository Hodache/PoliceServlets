package database;

import dataclasses.Car;
import dataclasses.Fine;
import dataclasses.Driver;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DBHelper {
    private static Connection connection = null;

    private static final String url = "jdbc:sqlserver://localhost:1433;";
    private static final String dbName = "databaseName=PoliceDB;";
    private static final String secureConnection = "encrypt=true;trustServerCertificate=true;";
    private static final String user = "user=sa;";
    private static final String userPassword = "password=123";

    public static void makeConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionUrl = url + dbName + secureConnection + user + userPassword;
            connection = DriverManager.getConnection(connectionUrl);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // Извлечение списка машин по номеру водительских прав
    public static ArrayList<Car> selectCarsByLicense(String license) {
        ArrayList<Car> carsList = new ArrayList<>();

        try {

            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM Cars WHERE D_LICENSE = ?");

            statement.setString(1, license);

            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                String plate = rs.getString("C_PLATE");
                String color = rs.getString("C_COlOR");
                String insurance = rs.getString("C_INSURANCE");
                String model = rs.getString("C_MODEL");
                int fines = rs.getInt("C_FINES");

                Car car = new Car(
                        model,
                        plate,
                        color,
                        insurance,
                        fines);

                carsList.add(car);

            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return carsList;
    }

    // Извлечение списка штрафов по номеру водительских прав
    public static ArrayList<Fine> selectFinesByLicense(String license) {
        ArrayList<Fine> finesList = new ArrayList<>();

        try {

            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM Fines WHERE D_LICENSE = ?");

            statement.setString(1, license);

            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                String id = rs.getString("F_ID");
                String date = rs.getDate("F_DATE").toString();
                String description = rs.getString("F_DESCRIPTION");
                int size = rs.getInt("F_SIZE");

                Fine fine = new Fine(
                        id,
                        date,
                        description,
                        size
                        );

                finesList.add(fine);

            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return finesList;
    }

    public static boolean insertFine(String license, String description, int size){
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO Fines (D_LICENSE, F_DATE, F_DESCRIPTION, F_SIZE)" +
                            "VALUES (?, ?, ?, ?)");

            statement.setString(1, license);
            statement.setDate(2, Date.valueOf(LocalDateTime.now().toLocalDate()));
            statement.setString(3, description);
            statement.setInt(4, size);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean checkAccount(String login, String password){
        boolean status = false;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    "SELECT A_LOGIN FROM Accounts WHERE A_LOGIN = ? AND A_PASSWORD = ?");

            statement.setString(1, login);
            statement.setString(2, password);

            ResultSet rs = statement.executeQuery();

            if (rs.next())
                status = true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return status;
    }

    public static Driver getDriverByLicense(String license) {
        Driver driver = null;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM Drivers WHERE D_LICENSE = ?");

            statement.setString(1, license);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                driver = new Driver(
                        rs.getString("D_LICENSE"),
                        rs.getString("D_FIRSTNAME"),
                        rs.getString("D_LASTNAME")
                );
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return driver;
    }

    public static ArrayList<Driver> getDrivers() {
        ArrayList<Driver> driversList = new ArrayList<>();

        try {

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM Drivers");

            while (rs.next()){
                String license = rs.getString("D_LICENSE").toString();
                String firstName = rs.getString("D_FIRSTNAME");
                String lastName = rs.getString("D_LASTNAME");

                Driver driver = new Driver(
                        license,
                        firstName,
                        lastName
                );
                driversList.add(driver);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return driversList;
    }

    public static boolean insertDriver(String license, String firstName, String lastName) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO Drivers (D_LICENSE, D_FIRSTNAME, D_LASTNAME)" +
                            "VALUES (?, ?, ?)");

            statement.setString(1, license);
            statement.setString(2, firstName);
            statement.setString(3, lastName);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean deleteDriver(String license) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM Drivers WHERE D_LICENSE = ?");

            statement.setString(1, license);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static ArrayList<Car> getCars(String license) {
        ArrayList<Car> carsList = new ArrayList<>();

        try {

            PreparedStatement statement =
                    connection.prepareStatement("SELECT * FROM Cars WHERE D_LICENSE = ?");
            statement.setString(1, license);

            ResultSet rs = statement.executeQuery();

            while (rs.next()){

                String plate = rs.getString("C_PLATE");
                String color = rs.getString("C_COlOR");
                String insurance = rs.getString("C_INSURANCE");
                String model = rs.getString("C_MODEL");
                int fines = rs.getInt("C_FINES");

                Car car = new Car(
                        model,
                        plate,
                        color,
                        insurance,
                        0
                );
                carsList.add(car);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return carsList;
    }

    public static boolean insertCar(String license, String model, String plate, String color, String insurance) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO Cars (D_LICENSE, C_PLATE, C_COLOR, C_INSURANCE, C_MODEL)" +
                            "VALUES (?, ?, ?, ?, ?)");

            statement.setString(1, license);
            statement.setString(2, plate);
            statement.setString(3, color);
            statement.setString(4, insurance);
            statement.setString(5, model);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean deleteCar(String plate) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM Cars WHERE C_PLATE = ?");

            statement.setString(1, plate);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean deleteFine(String id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM Fines WHERE F_ID = ?");

            statement.setString(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void updateFine(int id, String license, String description, int size) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE Fines SET " +
                            "D_LICENSE = ?, F_DESCRIPTION = ?, F_SIZE = ? " +
                            "WHERE F_ID = ? ");

            statement.setString(1, license);
            statement.setString(2, description);
            statement.setInt(3, size);
            statement.setInt(4, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Driver> getBadDrivers(int finesCount) {
        ArrayList<Driver> driversList = new ArrayList<>();

        try {

            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM Drivers d " +
                            "WHERE EXISTS (SELECT D_LICENSE " +
                            "FROM Fines " +
                            "WHERE d.D_LICENSE = Fines.D_LICENSE " +
                            "GROUP BY Fines.D_LICENSE " +
                            "HAVING COUNT (Fines.F_ID) > ?)"
            );

            statement.setInt(1, finesCount);

            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                String license = rs.getString("D_LICENSE").toString();
                String firstName = rs.getString("D_FIRSTNAME");
                String lastName = rs.getString("D_LASTNAME");

                Driver driver = new Driver(
                        license,
                        firstName,
                        lastName
                );
                driversList.add(driver);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return driversList;
    }

    public static int getFinesSum() {
        int finesSum = 0;

        try {

            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery("SELECT SUM(F_SIZE) AS 'finesSum' From Fines");

            rs.next();
            finesSum = rs.getInt("finesSum");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return finesSum;
    }
}
