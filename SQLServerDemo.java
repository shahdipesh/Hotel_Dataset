import java.io.*;
import java.sql.*;
import java.util.Properties;

import static java.lang.Integer.parseInt;

public class SQLServerDemo {

    // Connect to your database.
    // Replace server name, username, and password with your credentials
    public static void main(String[] args) throws SQLException, IOException {
        InsertQueries insertQueries = new InsertQueries();
        TopQueries topQueries = new TopQueries();
        Properties prop = new Properties();
        String fileName = "auth.cfg";
        try {
            FileInputStream configFile = new FileInputStream(fileName);
            prop.load(configFile);
            configFile.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Could not find config file.");
            System.exit(1);
        } catch (IOException ex) {
            System.out.println("Error reading config file.");
            System.exit(1);
        }
        String username = (prop.getProperty("username"));
        String password = (prop.getProperty("password"));

        if (username == null || password == null){
            System.out.println("Username or password not provided.");
            System.exit(1);
        }

        String connectionUrl =
                "jdbc:sqlserver://uranium.cs.umanitoba.ca:1433;"
                + "database=cs3380;"
                + "user=" + username + ";"
                + "password="+ password +";"
                + "encrypt=false;"
                + "trustServerCertificate=false;"
                + "loginTimeout=30;";

     //   insertQueries.insertNegativeReviewWordCount(connectionUrl);

     //topQueries.top5Hotels(connectionUrl);
     //topQueries.averageStayDurationPerHotel(connectionUrl);
     //topQueries.averageStayDurationPerTripType(connectionUrl);
        // topQueries.reviewsPerQuarter(connectionUrl);
    // topQueries.avgHotelRatingPerCountry(connectionUrl);
    // topQueries.hotelsWithForeignNational(connectionUrl);



    }






    private static void printHelp() {
        System.out.println("Library database");
        System.out.println("Commands:");
        System.out.println("h - Get help");
        System.out.println("s <name> - Search for a name");
        System.out.println("l <id> - Search for a user by id");
        System.out.println("sell <author id> - Search for a stores that sell books by this id");
        System.out.println("notread - Books not read by its own author");
        System.out.println("all - Authors that have read all their own books");
        System.out.println("notsell <author id>  - list of stores that do not sell this author");
        System.out.println("mp - Authors with the most publishers");
        System.out.println("mc - Authors with books in the most cities");
        System.out.println("mr - Most read book by country");
        System.out.println("");

        System.out.println("q - Exit the program");

        System.out.println("---- end help ----- ");
    }

}

