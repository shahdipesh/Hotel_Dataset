import javax.swing.*;
import java.io.*;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

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



        //create a button that says "Click Me" and when clicked, calls the topQueries.top5Hotels(connectionUrl) method and displays the results in console
        JButton getTopHotels = new JButton("Top Hotels");
        getTopHotels.addActionListener(e -> {
            topQueries.top5Hotels(connectionUrl);
        });
        // set up the JFrame with size 1000 and display it
        JFrame frame = new JFrame("SQL Server Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(getTopHotels);
        frame.setSize(1000, 1000);
        frame.setVisible(true);








//
//        Scanner console = new Scanner(System.in);
//        System.out.print("Welcome! Type h for help. ");
//        System.out.print("db > ");
//        String line = console.nextLine();
//        String[] parts;
//        String arg = "";
//
//        while (line != null && !line.equals("q")) {
//            parts = line.split("\\s+");
//            if (line.indexOf(" ") > 0)
//                arg = line.substring(line.indexOf(" ")).trim();
//            if (parts[0].equals("h"))
//                printHelp();
//            else if (parts[0].equals("topHotels")) {
//                topQueries.top5Hotels(connectionUrl);
//            }
//            else if (parts[0].equals("avgStayDurationHotel")) {
//                topQueries.averageStayDurationPerHotel(connectionUrl);
//            }
//            else if (parts[0].equals("avgStayDurationTripType")) {
//                topQueries.averageStayDurationPerTripType(connectionUrl);
//            }
//            else if (parts[0].equals("reviewsPerQuarter")) {
//                topQueries.reviewsPerQuarter(connectionUrl);
//            }
//            else if (parts[0].equals("ratingPerCountry")) {
//                topQueries.avgHotelRatingPerCountry(connectionUrl);
//            }
//            else if (parts[0].equals("preferredByForeigners")) {
//                topQueries.hotelsWithForeignNational(connectionUrl);
//            }
//            else if (parts[0].equals("percentReviewsByMobile")) {
//                topQueries.percentReviewInHoliday(connectionUrl);
//            }
//
//
//
//            else {
//                System.out.println("Read the help with h, and run a valid query.");
//            }
//            System.out.print("db > ");
//            line = console.nextLine();
//        }


    }






    private static void printHelp() {
        System.out.println("Commands:");
        System.out.println("h - Get help");
        System.out.println("topHotels - Top 5 hotels with the most number of good reviews");
        System.out.println("avgStayDurationHotel - Average stay duration per hotel");
        System.out.println("reviewsPerQuarter - Get how many reviews were made per quarter of the year in the platform");
        System.out.println("ratingPerCountry - Countries based on average hotel rating");
        System.out.println("avgStayDurationTripType - Average stay duration of different type of guests");
        System.out.println("preferredByForeigners - list of hotels that are preferred by foreign nationals, based on how many reviews are by foreigners");
        System.out.println("percentReviewsByMobile - Percentage of reviews made users using their mobile phone");
        System.out.println("");

        System.out.println("q - Exit the program");

        System.out.println("---- end help ----- ");
    }

}

