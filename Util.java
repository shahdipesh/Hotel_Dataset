import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.function.Function;

public class Util {
    Properties prop = new Properties();
    CreateQueries createQueries = new CreateQueries();
    String username = (prop.getProperty("username"));
    String password = (prop.getProperty("password"));
    String fileName = "auth.cfg";
    String connectionUrl;

    TopQueries topQueries = new TopQueries();

    public void setCallback(Frame frame, Function<String, StringBuilder> func) {

        StringBuilder sb = func.apply(connectionUrl);
        JTextArea textArea = new JTextArea(sb.toString());

        //if sb has no data, show a message
        if (sb.length() == 0) {
            textArea.setText("There is no data to display. Please try again with different Input");
            JScrollPane scrollPane = new JScrollPane(textArea);
            JOptionPane.showMessageDialog(null, scrollPane);
            return;
        }

        //enable scrolling
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 500));
        JOptionPane.showMessageDialog(null, scrollPane);


    }

    public void config() {
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

        if (username == null || password == null) {
            System.out.println("Username or password not provided.");
            System.exit(1);
        }

         connectionUrl =
                "jdbc:sqlserver://uranium.cs.umanitoba.ca:1433;"
                        + "database=cs3380;"
                        + "user=" + username + ";"
                        + "password=" + password + ";"
                        + "encrypt=false;"
                        + "trustServerCertificate=false;"
                        + "loginTimeout=30;";

    }

    public String getConnectionUrl() {
        return connectionUrl;
    }


    public void customiseFrame(JFrame frame) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 900);
        Font font = new Font("Code2000", Font.PLAIN, 18);
        frame.setFont(font);
        frame.setLayout(new GridLayout(5, 2));
        //set background colour to Gr.
        frame.getContentPane().setBackground(Color.BLACK);

        frame.setVisible(true);


    }

    public void modifyButton(JButton button) {
        Font font = new Font("Code2000", Font.PLAIN, 18);
        button.setFont(font);
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        //set border colour to light grey
        button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,8));
        button.setVisible(true);
        //change border opacity
        button.setOpaque(true);
        //make buttons round
        button.setBounds(100, 100, 100, 100);
        button.setVisible(true);
    }

    //take in latitudes and longitudes and return the distance between them in km
    public double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;
        return (dist);
    }

    private double rad2deg(double dist) {
        return (dist * 180.0 / Math.PI);
    }

    private double deg2rad(double lat1) {
        return (lat1 * Math.PI / 180.0);
    }

    //given hotelName return lat and long
    public float[] getCoordinate(String hotelName) {
        this.config();
        try (
                Connection con = DriverManager.getConnection(connectionUrl);
             Statement stmt = con.createStatement();
             )
        {
            String SQL = "select  A.Hotel_lat,A.Hotel_lng from Review\n" +
                    "join Hotel H on Review.Hotel_Name = H.Hotel_Name\n" +
                    "join Address A on A.Hotel_Address = H.Hotel_Address\n" +
                    "join Coordinate C on A.Hotel_lat = C.Hotel_lat and A.Hotel_lng = C.Hotel_lng\n" +
                    "where Review.Hotel_Name = ? group by A.Hotel_lat,A.Hotel_lng";

            PreparedStatement preparedStatement = con.prepareStatement(SQL);
            preparedStatement.setString(1, hotelName);
            java.sql.ResultSet rs = preparedStatement.executeQuery();

            //if there is no data, return null
            if (!rs.next()) {
                return null;
            }
            float[] coordinate = new float[2];
            coordinate[0] = rs.getFloat("Hotel_lat");
            coordinate[1] = rs.getFloat("Hotel_lng");
            return coordinate;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


    public void createTables() throws SQLException, IOException {
        createQueries.createAddress(this.getConnectionUrl());
        createQueries.createCityInfo(this.getConnectionUrl());
        createQueries.createCoordinate(this.getConnectionUrl());
        createQueries.createDate(this.getConnectionUrl());
        createQueries.createHotel(this.getConnectionUrl());
        createQueries.createNationalityCountryInfo(this.getConnectionUrl());
        createQueries.createNegativeReviewWordCount(this.getConnectionUrl());
        createQueries.createPartOfYear(this.getConnectionUrl());
        createQueries.createPositiveReviewWordCount(this.getConnectionUrl());
        createQueries.createReview(this.getConnectionUrl());
    }


}


