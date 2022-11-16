import java.io.*;
import java.sql.*;

import static java.lang.Integer.parseInt;

public class InsertQueries {

    public static void insertCoordinates(String connectionUrl) throws SQLException, IOException {
        Connection conn = DriverManager.getConnection(connectionUrl);
        Statement statement = conn.createStatement();
        BufferedReader br = new BufferedReader(new FileReader("/Users/dipeshasd/Desktop/DemoJavaProjectRelease/Coordinate.csv"));
        String line = null;
        br.readLine();

        while ((line = br.readLine()) != null) {
            System.out.println(line);
            String[] values = line.split(",");
            float latitude = Float.parseFloat(values[0].trim());
            float longitude = Float.parseFloat(values[1].trim());
            String hotel_city = values[2].trim();
            int business_100m = parseInt(values[3].trim());
            int business_1km = parseInt(values[4].trim());
            int business_5km = parseInt(values[5].trim());
            int total_number_of_reviews = parseInt(values[6].trim());

            String sqlCheck = "Select * from Coordinate where Hotel_lat = '"+latitude+"' and Hotel_lng = '"+longitude+"'";
            ResultSet rs = statement.executeQuery(sqlCheck);
            if(!rs.next()){
                String sql = "INSERT INTO Coordinate VALUES ('" + latitude + "', '" + longitude + "', '" + hotel_city + "', '" + business_100m + "', '" + business_1km + "', '" + business_5km + "', '" + total_number_of_reviews + "')";
                statement.execute(sql);
            }

        }



    }

    public static void insertAddress(String connectionUrl) throws SQLException, IOException {
        Connection conn = DriverManager.getConnection(connectionUrl);
        Statement statement = conn.createStatement();

        BufferedReader br = new BufferedReader(new FileReader("/Users/dipeshasd/Desktop/DemoJavaProjectRelease/Address.csv"));

        String line = null;
        br.readLine();

        //read log file's first line
        BufferedReader brLog = new BufferedReader(new FileReader("/Users/dipeshasd/Desktop/DemoJavaProjectRelease/log.txt"));


        int lineFromFile = Integer.parseInt(brLog.readLine());

        int currLine=0;



        while ((line = br.readLine()) != null) {
            currLine++;
            if(currLine<lineFromFile){
                continue;
            }
            System.out.println(currLine);
            System.out.println(line);
            String[] values = line.split(",");
            String address= values[0].trim();
            float latitude = 0;
            float longitude = 0;


            latitude = Float.parseFloat(values[1].trim());
            longitude = Float.parseFloat(values[2].trim());

            try {
                String sqlCheck = "Select * from Address where Hotel_Address = '" + address + "'";
                ResultSet rs = statement.executeQuery(sqlCheck);
                if (!rs.next()) {
                    String sql = "INSERT INTO Address VALUES ('" + address + "', '" + latitude + "', '" + longitude + "')";
                    statement.execute(sql);
                }
            }catch (Exception e){
                BufferedWriter bw = new BufferedWriter(new FileWriter("/Users/dipeshasd/Desktop/DemoJavaProjectRelease/log1.txt"));
                bw.write(currLine);
                bw.close();
            }

        }
    }

    public static  void insertCityInfo(String connectionUrl)throws SQLException, IOException{
        Connection conn = DriverManager.getConnection(connectionUrl);
        Statement statement = conn.createStatement();

        BufferedReader br = new BufferedReader(new FileReader("/Users/dipeshasd/Desktop/DemoJavaProjectRelease/City_Info.csv"));

        String line = null;
        br.readLine();

        //read log file's first line
        BufferedReader brLog = new BufferedReader(new FileReader("/Users/dipeshasd/Desktop/DemoJavaProjectRelease/log.txt"));


        int lineFromFile = Integer.parseInt(brLog.readLine());

        int currLine=0;

        while ((line = br.readLine()) != null) {
            currLine++;
            if(currLine<lineFromFile){
                continue;
            }
            System.out.println(currLine);
            System.out.println(line);
            String[] values = line.split(",");
            String city= values[0].trim();
            String state = values[1].trim();
            String country = values[2].trim();

            try {
                String sqlCheck = "Select * from City_Info where Hotel_City = '" + city + "'";
                ResultSet rs = statement.executeQuery(sqlCheck);
                if (!rs.next()) {
                    String sql = "INSERT INTO City_Info VALUES ('" + city + "', '" + state + "', '" + country + "')";
                    statement.execute(sql);
                }

            }catch (Exception e){
                BufferedWriter bw = new BufferedWriter(new FileWriter("/Users/dipeshasd/Desktop/DemoJavaProjectRelease/log1.txt"));
                bw.write(currLine);
                bw.close();
            }

        }


    }

    public static void insertNegativeReviewWordCount(String connectionUrl) throws SQLException, IOException {
        Connection conn = DriverManager.getConnection(connectionUrl);
        Statement statement = conn.createStatement();

        BufferedReader br = new BufferedReader(new FileReader("/Users/dipeshasd/Desktop/DemoJavaProjectRelease/Negative_Review_Word_Count.csv"));

        String line = null;
        br.readLine();

        //read log file's first line
        BufferedReader brLog = new BufferedReader(new FileReader("/Users/dipeshasd/Desktop/DemoJavaProjectRelease/log.txt"));


        int lineFromFile = Integer.parseInt(brLog.readLine());

        int currLine = 0;

        while ((line = br.readLine()) != null) {
            currLine++;
            if (currLine < lineFromFile) {
                continue;
            }
            System.out.println(currLine);
            System.out.println(line);
            String[] values = line.split(",");
            String review = values[0].trim();
            int word_count = Integer.parseInt(values[1].trim());

            try {
                String sqlCheck = "Select * from Negative_Review_Word_Count where Negative_Review = '" + review + "'";
                ResultSet rs = statement.executeQuery(sqlCheck);
                if (!rs.next()) {
                    String sql = "INSERT INTO Negative_Review_Word_Count VALUES ('" + review + "', '" + word_count + "')";
                    statement.execute(sql);
                }
            } catch (Exception e) {
                BufferedWriter bw = new BufferedWriter(new FileWriter("/Users/dipeshasd/Desktop/DemoJavaProjectRelease/log1.txt"));
                bw.write(currLine);
                bw.close();
            }


        }

    }


    public  static  void insertHotel(String connectionUrl) throws SQLException, IOException {
        Connection conn = DriverManager.getConnection(connectionUrl);
        Statement statement = conn.createStatement();

        BufferedReader br = new BufferedReader(new FileReader("/Users/dipeshasd/Desktop/DemoJavaProjectRelease/Hotel.csv"));

        String line = null;
        br.readLine();

        //read log file's first line
        BufferedReader brLog = new BufferedReader(new FileReader("/Users/dipeshasd/Desktop/DemoJavaProjectRelease/log.txt"));

        int lineFromFile = Integer.parseInt(brLog.readLine());

        int currLine = 0;

        while ((line = br.readLine()) != null) {


            currLine++;
            if (currLine < lineFromFile) {
                continue;
            }
            System.out.println(currLine);
            System.out.println(line);
            String[] values = line.split(",");

            try {


            String hotel_name = values[0].trim();
            String address = values[1].trim();

            try {
                String sqlCheck = "Select * from Hotel where Hotel_Name = '" + hotel_name + "'";
                ResultSet rs = statement.executeQuery(sqlCheck);
                if (!rs.next()) {
                    String sql = "INSERT INTO Hotel VALUES ('" + hotel_name + "', '" + address + "')";
                    statement.execute(sql);
                }
            } catch (Exception e) {
                BufferedWriter bw = new BufferedWriter(new FileWriter("/Users/dipeshasd/Desktop/DemoJavaProjectRelease/log1.txt"));
                bw.write(currLine+"");
                bw.close();
            }
            }catch (Exception e){
                BufferedWriter bw = new BufferedWriter(new FileWriter("/Users/dipeshasd/Desktop/DemoJavaProjectRelease/log1.txt"));
                bw.write(currLine+"");
                bw.close();
            }

        }
    }

    public  static  void insertReview(String connectionUrl) throws SQLException, IOException {
        Connection conn = DriverManager.getConnection(connectionUrl);
        Statement statement = conn.createStatement();
        BufferedWriter bw = new BufferedWriter(new FileWriter("/Users/dipeshasd/Desktop/DemoJavaProjectRelease/log1.txt"));
        BufferedReader br = new BufferedReader(new FileReader("/Users/dipeshasd/Desktop/DemoJavaProjectRelease/Review.csv"));

        String line = null;
        br.readLine();

        //read log file's first line
        BufferedReader brLog = new BufferedReader(new FileReader("/Users/dipeshasd/Desktop/DemoJavaProjectRelease/log.txt"));

        int lineFromFile = 28800;

        int currLine = 1;

        while ((line = br.readLine()) != null) {
            currLine++;
            if (currLine < lineFromFile) {
                continue;
            }
            System.out.println(currLine);
            String[] values = line.split(",");

            try {

                String id = values[0].trim();
                String hotel_name = values[1].trim();
                String room_type = values[2].trim();
                String room_type_level = values[3].trim();
                String bed_type = values[4].trim();
                String guest_type = values[5].trim();
                String trip_type = values[6].trim();
                int stay_duration = Integer.parseInt(values[7].trim());
                int review_date = Integer.parseInt(values[8].trim());
                String days_since_review = values[9].trim();
                int review_is_positive = Integer.parseInt(values[10].trim());
                String reviewer_nationality = values[11].trim();
                String negative_review = values[12].trim();
                String positive_review = values[13].trim();
                int average_score = Integer.parseInt(values[14].trim());
                int reviewer_score = Integer.parseInt(values[15].trim());
                int total_number_of_reviews_reviewer_has_given = Integer.parseInt(values[16].trim());
                int additional_number_of_scoring = Integer.parseInt(values[17].trim());
                int submitted_from_mobile = Integer.parseInt(values[18].trim());
                int is_reviewer_holiday = Integer.parseInt(values[19].trim());

                //if any exception occurs, write the line number to log file


                try {

                    System.out.println(line);
                    String sqlCheck = "Select * from Review where ID = '" + id + "'";
                    ResultSet rs = statement.executeQuery(sqlCheck);
                    if (!rs.next()) {
                        String sql = "INSERT INTO Review VALUES ('" + id + "', '" + hotel_name + "', '" + room_type + "', '" + room_type_level + "', '" + bed_type + "', '" + guest_type + "', '" + trip_type + "', '" + stay_duration + "', '" + review_date + "', '" + days_since_review + "', '" + review_is_positive + "', '" + reviewer_nationality + "', '" + negative_review + "', '" + positive_review + "', '" + average_score + "', '" + reviewer_score + "', '" + total_number_of_reviews_reviewer_has_given + "', '" + additional_number_of_scoring + "', '" + submitted_from_mobile + "', '" + is_reviewer_holiday + "')";
                        statement.execute(sql);
                    }
                } catch (Exception e) {
                    System.out.println(e);
                    bw.append(String.valueOf(currLine)+"\n");
                }

            }
            catch (Exception e) {
                bw.append(String.valueOf(currLine)+"\n");
            }


        }
        bw.close();
    }

    public static void insertPositiveReviewWordCount(String connectionUrl) throws SQLException, IOException {
        Connection conn = DriverManager.getConnection(connectionUrl);
        Statement statement = conn.createStatement();

        BufferedReader br = new BufferedReader(new FileReader("/Users/dipeshasd/Desktop/DemoJavaProjectRelease/Positive_Review_Word_Count.csv"));

        String line = null;
        br.readLine();

        //read log file's first line
        BufferedReader brLog = new BufferedReader(new FileReader("/Users/dipeshasd/Desktop/DemoJavaProjectRelease/log.txt"));

        int lineFromFile = 13294;

        int currLine = 1;

        while ((line = br.readLine()) != null) {
            currLine++;
            if (currLine < lineFromFile) {
                continue;
            }
            String[] values = line.split(",");
            System.out.println(currLine);
            try {

                String review = values[0].trim();
                int count = Integer.parseInt(values[1].trim());

                //if any exception occurs, write the line number to log file
                try {
                    String sqlCheck = "Select * from Positive_Review_Word_Count where Positive_Review = '" + review + "'";
                    ResultSet rs = statement.executeQuery(sqlCheck);
                    if (!rs.next()) {
                        String sql = "INSERT INTO Positive_Review_Word_Count VALUES ('" + review + "', '" + count + "')";
                        statement.execute(sql);
                    }
                } catch (Exception e) {
                    System.out.println("-------" + e);
                }

            } catch (Exception e) {
                System.out.println("------" + e);
            }
        }

        }
}
