import javax.swing.*;
import java.io.*;
import java.sql.*;
import java.util.Properties;

import static java.lang.Integer.parseInt;

public class Main {

    // Connect to your database.
    // Replace server name, username, and password with your credentials
    public static void main(String[] args) throws SQLException, IOException {

       Util util = new Util();
        InsertQueries insertQueries = new InsertQueries();
        TopQueries topQueries = new TopQueries();
        Properties prop = new Properties();
        util.config();



   // InsertQueries.insertPositiveReviewWordCount(util.getConnectionUrl());

//
        JFrame frame = new JFrame("Hotel Reviews");


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JButton top5Hotels = new JButton("Top 5 Hotels");
        JButton stayDurationHotel = new JButton("Average stay duration for each hotel");
        JButton stayDurationTripType = new JButton("Average stay duration for each trip type");
        JButton reviewsPerQuarter = new JButton("Total Number of Reviews made per Quarter");
        JButton hotelRatingPerCountry = new JButton("Countries with best hotels");
        JButton hotelsWithForeignNational = new JButton("Hotels hotly debated among foreign nationals");
        JButton preferredDevice = new JButton("Device Preferred by Reviewers to make Reviews");
        JButton searchByCityName = new JButton("Search for all Hotels in a City");
        JButton businessAroundHotel = new JButton("Hotels with most businesses around them");

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




    }


}

