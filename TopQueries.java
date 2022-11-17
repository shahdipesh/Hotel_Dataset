import java.sql.*;
import java.text.DecimalFormat;

public class TopQueries {
    public StringBuilder top5Hotels(String connectionUrl) {

        StringBuilder sb = new StringBuilder();

        try (Connection con = DriverManager.getConnection(connectionUrl);
             Statement stmt = con.createStatement();) {

            String SQL = "select Hotel_Name ,count(Review_Is_Positive) Total_positive_reviews,\n" +
                    "       (select count(Review_Is_Positive) from Review innerReview where Review_Is_Positive = 0 and outerReview.Hotel_Name=innerReview.Hotel_Name ) as Total_negative_reviews\n" +
                    "from Review outerReview\n" +
                    "where Review_Is_Positive = 1\n" +
                    "group by Hotel_Name\n" +
                    "order by total_positive_reviews desc offset 0 rows fetch next 5 rows only\n" +
                    "\n";

            //make the query sqlinjection safe
            PreparedStatement pstmt = con.prepareStatement(SQL);
            ResultSet rs = pstmt.executeQuery();


            // Iterate through the data in the result set and add to string builder and display in arranged format.
            while (rs.next()) {
                String hotelName = rs.getString("Hotel_Name");
                int totalPositiveReviews = rs.getInt("Total_positive_reviews");
                int totalNegativeReviews = rs.getInt("Total_negative_reviews");
                int totalReviews = totalPositiveReviews + totalNegativeReviews;
                double positiveReviewPercentage = (double) totalPositiveReviews / totalReviews * 100;
                DecimalFormat df = new DecimalFormat("#.##");
                sb.append("Hotel Name: " + hotelName + "\n");
                sb.append("Total Positive Reviews: " + totalPositiveReviews + "\n");
                sb.append("Total Reviews: " + totalReviews + "\n");
                sb.append("Positivity sentiment: " + df.format(positiveReviewPercentage) + "%" + "\n");
                sb.append("----------------------------------------" + "\n");

            }
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
        return sb;
    }

    public StringBuilder averageStayDurationPerHotel(String connectionUrl) {
        StringBuilder sb = new StringBuilder();
        try (Connection con = DriverManager.getConnection(connectionUrl);
             Statement stmt = con.createStatement();) {

            String SQL = "select Hotel_Name,avg(Stay_Duration) as Average_stay_duration_in_days\n" +
                    "    from Review\n" +
                    "    group by Hotel_Name\n" +
                    "    order by Average_stay_duration_in_days desc\n";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String hotelName = rs.getString("Hotel_Name");
                double averageStayDuration = rs.getDouble("Average_stay_duration_in_days");
                DecimalFormat df = new DecimalFormat("#.##");
                sb.append("Hotel Name: " + hotelName + "\n");
                sb.append("Average Stay Duration: " + df.format(averageStayDuration) + " days" + "\n");
                sb.append("----------------------------------------" + "\n");
            }

        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
        return sb;
    }

    public StringBuilder averageStayDurationPerTripType(String connectionUrl) {
        StringBuilder sb = new StringBuilder();
        try (Connection con = DriverManager.getConnection(connectionUrl);
             Statement stmt = con.createStatement();) {

            String SQL = "select Trip_Type,avg(Stay_Duration)as avgStayDuration,count(Stay_Duration) as rowsEvaluated" +
                    " from review\n" +
                    "  where Trip_Type != 'NULL'" +
                    "    group by Trip_Type " +
                    "    order by avgStayDuration desc ";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String tripType = rs.getString("Trip_Type");
                double averageStayDuration = rs.getDouble("avgStayDuration");
                int rowsEvaluated = rs.getInt("rowsEvaluated");
                DecimalFormat df = new DecimalFormat("#.##");
                sb.append("Trip Type: " + tripType + "\n");
                sb.append("Average Stay Duration: " + df.format(averageStayDuration) + " days" + "\n");
                sb.append("----------------------------------------" + "\n");
            }

        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
        return sb;
    }

    public StringBuilder reviewsPerQuarter(String connectionUrl) {
        try (Connection con = DriverManager.getConnection(connectionUrl);
             Statement stmt = con.createStatement();) {

            String SQL = "select Quarter_of_Year,count(id) as numberOfReviews\n" +
                    "from Review\n" +
                    "join Date on Review.Review_Date = Date.Review_Date\n" +
                    "join Part_of_year on Date.Day_of_Year = Part_of_year.Day_of_Year\n" +
                    "group by Part_of_year.Quarter_of_Year\n" +
                    "order by numberOfReviews desc ";

            PreparedStatement pstmt = con.prepareStatement(SQL);
            ResultSet rs = pstmt.executeQuery();

            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                String quarterOfYear = rs.getString("Quarter_of_Year");
                int numberOfReviews = rs.getInt("numberOfReviews");
                sb.append("Quarter of Year: " + quarterOfYear + "\n");
                sb.append("Number of Reviews: " + numberOfReviews + "\n");
                sb.append("----------------------------------------" + "\n");
            }
            return sb;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public StringBuilder avgHotelRatingPerCountry(String connectionUrl) {
        StringBuilder sb = new StringBuilder();
        try (Connection con = DriverManager.getConnection(connectionUrl);
             Statement stmt = con.createStatement();) {

            String SQL = "select NCI.Reviewer_Nationality as Country ,avg(Average_Score) as Avg_Hotel_Rating\n" +
                    "    from Review\n" +
                    "    join Hotel on Hotel.Hotel_Name = Review.Hotel_Name\n" +
                    "    join Address on Hotel.Hotel_Address = Address.Hotel_Address\n" +
                    "    join Coordinate on Address.Hotel_lat = Coordinate.Hotel_lat and Address.Hotel_lng = Coordinate.Hotel_lng\n" +
                    "    join City_Info on City_Info.Hotel_City = Coordinate.Hotel_City\n" +
                    "    join Nationality_Country_Info NCI on Hotel_Country=NCI.Reviewer_Country\n" +
                    "group by NCI.Reviewer_Nationality\n" +
                    "order by Avg_Hotel_Rating desc ";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            ResultSet rs = pstmt.executeQuery();

            // Iterate through the data in the result set and display it.
            while (rs.next()) {
                String country = rs.getString("Country");
                double avgHotelRating = rs.getDouble("Avg_Hotel_Rating");
                DecimalFormat df = new DecimalFormat("#.##");
                sb.append("Country: " + country + "\n");
                sb.append("Average Hotel Rating: " + df.format(avgHotelRating) + ".0\n");
//                sb.append("Number of Hotels Available: " + numHotels + "\n");
                sb.append("----------------------------------------" + "\n");
            }

        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
        return sb;

    }

    public StringBuilder hotelsWithForeignNational(String connectionUrl) {
        StringBuilder sb = new StringBuilder();
        try (Connection con = DriverManager.getConnection(connectionUrl);
             Statement stmt = con.createStatement();) {

            String SQL = "---hotels that host most number of foreign nationals based on reviews\n" +
                    "select Hotel.Hotel_Name,(select Reviewer_Nationality from Nationality_Country_Info where Reviewer_Country = Hotel_Country) as location, count(Reviewer_Country) as No_of_foreign_National_who_reviewed\n" +
                    "    from Review\n" +
                    "    join Hotel on Hotel.Hotel_Name = Review.Hotel_Name\n" +
                    "    join Address on Hotel.Hotel_Address = Address.Hotel_Address\n" +
                    "    join Coordinate on Address.Hotel_lat = Coordinate.Hotel_lat and Address.Hotel_lng = Coordinate.Hotel_lng\n" +
                    "    join City_Info on City_Info.Hotel_City = Coordinate.Hotel_City\n" +
                    "    join Nationality_Country_Info on Review.Reviewer_Nationality=Nationality_Country_Info.Reviewer_Nationality\n" +
                    "where Hotel_Country!=Reviewer_Country\n" +
                    "group by Hotel.Hotel_Name, Hotel_Country\n" +
                    "order by No_of_foreign_National_who_reviewed desc\n" +
                    "offset 0 rows fetch next 10 rows only";
            ResultSet rs = stmt.executeQuery(SQL);

            // Iterate through the data in the result set and display it.


            while (rs.next()) {
                String hotelName = rs.getString("Hotel_Name");
                String location = rs.getString("location");
                int noOfForeignNational = rs.getInt("No_of_foreign_National_who_reviewed");
                sb.append("Hotel Name: " + hotelName + "\n");
                sb.append("Location: " + location + "\n");
                sb.append("Reviews By Foreign National: " + noOfForeignNational + "\n");
                sb.append("------------------------------------------");
                sb.append(" " + "\n");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sb;
    }

    public StringBuilder preferredDevice(String connectionUrl) {
        StringBuilder sb = new StringBuilder();
        try (Connection con = DriverManager.getConnection(connectionUrl);
             Statement stmt = con.createStatement();) {
            String SQL = "(select (count(*)*1.0/(select count(*)*1.0 from Review as aa)*100)as PercentReviewByMob  from Review where Submitted_from_Mobile=1 )\n";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            ResultSet rs = pstmt.executeQuery();

            //get only 2 decimal places
            DecimalFormat df = new DecimalFormat("#.##");


            while (rs.next()) {
                double percentReviewByMob = rs.getDouble("PercentReviewByMob");
                sb.append(df.format(percentReviewByMob) + "% of Reviews were made using Mobile Phone\n");
                sb.append(" " + "\n");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sb;
    }

    public StringBuilder searchByCityName(String connectionUrl, String cityName) {
        StringBuilder sb = new StringBuilder();
        try (Connection con = DriverManager.getConnection(connectionUrl);
             Statement stmt = con.createStatement();) {
            String SQL = "select H.Hotel_Name,A.Hotel_Address from Review\n" +
                    "                    join Hotel H on Review.Hotel_Name = H.Hotel_Name\n" +
                    "                    join Address A on A.Hotel_Address = H.Hotel_Address\n" +
                    "                    join Coordinate C on A.Hotel_lat = C.Hotel_lat and A.Hotel_lng = C.Hotel_lng\n" +
                    "                    join City_Info CI on C.Hotel_City = CI.Hotel_City\n" +
                    "                    group by H.Hotel_Name,A.Hotel_Address,CI.Hotel_City\n" +
                    "                    having CI.Hotel_City = "+ "'"+cityName +"'"+"\n";

            ResultSet rs = stmt.executeQuery(SQL);

            while (rs.next()) {

                String hotelName = rs.getString("Hotel_Name");
                String hotelAddress = rs.getString("Hotel_Address");

                sb.append("Hotel Name: " + hotelName + "\n");
                sb.append("Hotel Address: " + hotelAddress + "\n");
                sb.append("------------------------------------------\n");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sb;
    }


    public StringBuilder businessWithin100m (String connectionUrl) {
        StringBuilder sb = new StringBuilder();
        try (Connection con = DriverManager.getConnection(connectionUrl);
             Statement stmt = con.createStatement();) {
            String SQL = "select Review.Hotel_Name,A.Hotel_Address,Businesses_100m from Review\n" +
                    "join Hotel H on Review.Hotel_Name = H.Hotel_Name\n" +
                    "join Address A on A.Hotel_Address = H.Hotel_Address\n" +
                    "join Coordinate C on C.Hotel_lat = A.Hotel_lat and C.Hotel_lng = A.Hotel_lng\n" +
                    "group by Review.Hotel_Name,Businesses_100m,A.Hotel_Address\n" +
                    "having Businesses_100m >50\n" +
                    "order by Businesses_100m desc;";
            PreparedStatement pstmt = con.prepareStatement(SQL);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String hotelName = rs.getString("Hotel_Name");
                String hotelAddress = rs.getString("Hotel_Address");
                int businesses100m = rs.getInt("Businesses_100m");

                sb.append("Hotel Name: " + hotelName + "\n");
                sb.append("Hotel Address: " + hotelAddress + "\n");
                sb.append("Businesses within 100m: " + businesses100m + "\n");
                sb.append("------------------------------------------\n");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return sb;
    }

    public  StringBuilder hotelsNearby (String connectionUrl, String hotelName) {

        Util util = new Util();
        StringBuilder sb = new StringBuilder();

        float[] coordinates = util.getCoordinate(hotelName);

        if(coordinates==null){
            sb.append("Hotel not found");
            return sb;
        }

        float lat = coordinates[0];
        float lng = coordinates[1];

        String SQL = "select distinct  H.Hotel_Name,A.Hotel_lat,A.Hotel_lng,A.Hotel_Address from Review\n" +
                "join Hotel H on Review.Hotel_Name = H.Hotel_Name\n" +
                "join Address A on A.Hotel_Address = H.Hotel_Address\n" +
                "join Coordinate C on A.Hotel_lat = C.Hotel_lat and A.Hotel_lng = C.Hotel_lng\n";

        try (Connection con = DriverManager.getConnection(connectionUrl);
                Statement stmt = con.createStatement();) {

                ResultSet rs = stmt.executeQuery(SQL);

                while (rs.next()) {
                    String hotelName1 = rs.getString("Hotel_Name");
                    float lat1 = rs.getFloat("Hotel_lat");
                    float lng1 = rs.getFloat("Hotel_lng");
                    String address = rs.getString("Hotel_Address");

                    //if distance is less than 1km
                    if(util.distance(lat,lng,lat1,lng1)<1){
                        sb.append("Hotel Name: " + hotelName1 + "\n");
                        sb.append("Hotel Address: " + address + "\n");
                        sb.append("------------------------------------------\n");
                    }
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

       return sb;
    }



    }






