import javax.swing.*;
import java.io.*;
import java.sql.*;
import java.util.Properties;

import static java.lang.Integer.parseInt;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {

        Util util = new Util();
        CreateQueries createQueries = new CreateQueries();
        InsertQueries insertQueries = new InsertQueries();
        TopQueries topQueries = new TopQueries();
        Properties prop = new Properties();
        util.config();

        util.createTables();

        JFrame frame = new JFrame("Hotel Reviews");


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JButton top5Hotels = new JButton("Top 5 Hotels");
        JButton stayDurationHotel = new JButton("Average days spent at a hotel");
        JButton stayDurationTripType = new JButton("Average days spent based on type of trip");
        JButton reviewsPerQuarter = new JButton("Total Number of Reviews made per Quarter Year");
        JButton hotelRatingPerCountry = new JButton("Countries with the best hotels");
        JButton hotelsWithForeignNational = new JButton("Hotels with the highest count of foreign reviewers");
        JButton preferredDevice = new JButton("Percentage of reviews made from a mobile device");
        JButton searchByCityName = new JButton("Search for all Hotels in a City");
        JButton businessAroundHotel = new JButton("Hotels with the most surrounding businesses");
        JButton hotelsNearby = new JButton("Search for closest Hotels to a given hotel");

        util.customiseFrame(frame);

         businessAroundHotel.addActionListener(e -> {
          util.setCallback(frame, topQueries::businessWithin100m);
         });

        searchByCityName.addActionListener(e -> {
            String cityName = JOptionPane.showInputDialog("Enter City Name");
            util.setCallback(frame, (connectionUrl) -> topQueries.searchByCityName(connectionUrl, cityName));
        });

        preferredDevice.addActionListener(e -> {
            util.setCallback(frame, topQueries::preferredDevice);
        });

        hotelsWithForeignNational.addActionListener(e -> {
            util.setCallback(frame, topQueries::hotelsWithForeignNational);

        });
        hotelRatingPerCountry.addActionListener(e -> {
         util.setCallback(frame, topQueries::avgHotelRatingPerCountry);
        });
        reviewsPerQuarter.addActionListener(e -> {
            util.setCallback(frame, topQueries::reviewsPerQuarter);

        });
        stayDurationTripType.addActionListener(e -> {
            util.setCallback(frame, topQueries::averageStayDurationPerTripType);
        });
        stayDurationHotel.addActionListener(e -> {
           util.setCallback(frame, topQueries::averageStayDurationPerHotel);
        });
        top5Hotels.addActionListener(e -> {
            util.setCallback(frame, topQueries::top5Hotels);
        });

        //The hotel name should be exactly as they are in the database.
        hotelsNearby.addActionListener(e -> {
            String hotelName = JOptionPane.showInputDialog("Enter Hotel Name");
            util.setCallback(frame, (connectionUrl) -> topQueries.hotelsNearby(connectionUrl, hotelName));
        });


        util.modifyButton(top5Hotels);
        frame.getContentPane().add(top5Hotels);

        util.modifyButton(stayDurationHotel);
        frame.getContentPane().add(stayDurationHotel);

        util.modifyButton(stayDurationTripType);
        frame.getContentPane().add(stayDurationTripType);

        util.modifyButton(reviewsPerQuarter);
        frame.getContentPane().add(reviewsPerQuarter);

        util.modifyButton(hotelRatingPerCountry);
        frame.getContentPane().add(hotelRatingPerCountry);

        util.modifyButton(hotelsWithForeignNational);
        frame.getContentPane().add(hotelsWithForeignNational);

        util.modifyButton(preferredDevice);
        frame.getContentPane().add(preferredDevice);

        util.modifyButton(searchByCityName);
        frame.getContentPane().add(searchByCityName);

        util.modifyButton(businessAroundHotel);
        frame.getContentPane().add(businessAroundHotel);

        util.modifyButton(hotelsNearby);
        frame.getContentPane().add(hotelsNearby);




    }


}

