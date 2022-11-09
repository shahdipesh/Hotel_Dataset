import java.sql.*;

public class TopQueries {
    public void top5Hotels(String connectionUrl) {

        try (Connection con = DriverManager.getConnection(connectionUrl);
             Statement stmt = con.createStatement();) {

            String SQL = "select Hotel_Name ,count(Review_Is_Positive) Total_positive_reviews,\n" +
                    "       (select count(Review_Is_Positive) from Review innerReview where Review_Is_Positive = 0 and outerReview.Hotel_Name=innerReview.Hotel_Name ) as Total_negative_reviews\n" +
                    "from Review outerReview\n" +
                    "where Review_Is_Positive = 1\n" +
                    "group by Hotel_Name\n" +
                    "order by total_positive_reviews desc offset 0 rows fetch next 5 rows only\n" +
                    "\n";
            ResultSet rs = stmt.executeQuery(SQL);

            // Iterate through the data in the result set and display it.
            System.out.println("Top 5 Hotels with highest number of positive reviews");

            while (rs.next()) {
                System.out.println("Name: " + rs.getString("Hotel_Name") + "   --- Positive Reviews: " + rs.getString("Total_positive_reviews") + "    ---Negative Reviews: " + rs.getString("Total_negative_reviews"));

            }
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void averageStayDurationPerHotel(String connectionUrl) {
        try (Connection con = DriverManager.getConnection(connectionUrl);
             Statement stmt = con.createStatement();) {

            String SQL = "select Hotel_Name,avg(Stay_Duration) as Average_stay_duration_in_days\n" +
                    "    from Review\n" +
                    "    group by Hotel_Name\n" +
                    "    order by Average_stay_duration_in_days desc\n";
            ResultSet rs = stmt.executeQuery(SQL);

            // Iterate through the data in the result set and display it.
            System.out.println("Country with most number of hotels");

            while (rs.next()) {
                System.out.println("Name: " + rs.getString("Hotel_Name") + "   --- Average Stay Duration: " + rs.getString("Average_stay_duration_in_days"));


            }
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void averageStayDurationPerTripType(String connectionUrl) {
        try (Connection con = DriverManager.getConnection(connectionUrl);
             Statement stmt = con.createStatement();) {

            String SQL = "select Trip_Type,avg(Stay_Duration)as avgStayDuration,count(Stay_Duration) as rowsEvaluated" +
                    " from review\n" +
                    "  where Trip_Type != 'NULL'" +
                    "    group by Trip_Type " +
                    "    order by avgStayDuration desc ";
            ResultSet rs = stmt.executeQuery(SQL);

            // Iterate through the data in the result set and display it.
            System.out.println("Average Stay Duration Per Trip Type");
            System.out.println("------------------------------------");

            while (rs.next()) {
                System.out.println("Trip Type: " + rs.getString("Trip_Type") + "   --- Average Stay Duration: " + rs.getString("avgStayDuration") + " ---No. of reviews by this guest type: " + rs.getString("rowsEvaluated"));
            }
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void reviewsPerQuarter(String connectionUrl) {
        try (Connection con = DriverManager.getConnection(connectionUrl);
             Statement stmt = con.createStatement();) {

            String SQL = "select Quarter_of_Year,count(id) as numberOfReviews\n" +
                    "from Review\n" +
                    "join Date on Review.Review_Date = Date.Review_Date\n" +
                    "join Part_of_year on Date.Day_of_Year = Part_of_year.Day_of_Year\n" +
                    "group by Part_of_year.Quarter_of_Year\n" +
                    "order by numberOfReviews desc ";
            ResultSet rs = stmt.executeQuery(SQL);

            // Iterate through the data in the result set and display it.
            System.out.println("Reviews Per Quarter");
            System.out.println("------Quarter Of Year-------||---------No. of Reviews--------------");

            while (rs.next()) {
                System.out.println("|           " + rs.getString("Quarter_of_Year") + "                               " +
                        "" + rs.getString("numberOfReviews") + "");
                System.out.println("-----------------------------------------------------------------");
            }
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // average hotel rating per country
    public void avgHotelRatingPerCountry(String connectionUrl) {
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
            ResultSet rs = stmt.executeQuery(SQL);

            // Iterate through the data in the result set and display it.
            System.out.println("Average Hotel Rating Per Country\n");
            System.out.println("---(Country, Avg Rating of hotels)-----\n");

            while (rs.next()) {
                System.out.println("|    " + rs.getString("Country").trim() + "," +
                        " " + rs.getString("Avg_Hotel_Rating") + "");
                System.out.println("---------------------------------");
            }
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void hotelsWithForeignNational(String connectionUrl) {
        try (Connection con = DriverManager.getConnection(connectionUrl);
             Statement stmt = con.createStatement();) {

            String SQL = "select Hotel.Hotel_Name, count(Reviewer_Country) as No_of_foreign_National_who_reviewed\n" +
                    "    from Review\n" +
                    "    join Hotel on Hotel.Hotel_Name = Review.Hotel_Name\n" +
                    "    join Address on Hotel.Hotel_Address = Address.Hotel_Address\n" +
                    "    join Coordinate on Address.Hotel_lat = Coordinate.Hotel_lat and Address.Hotel_lng = Coordinate.Hotel_lng\n" +
                    "    join City_Info on City_Info.Hotel_City = Coordinate.Hotel_City\n" +
                    "    join Nationality_Country_Info on Review.Reviewer_Nationality=Nationality_Country_Info.Reviewer_Nationality\n" +
                    "where Hotel_Country!=Reviewer_Country\n" +
                    "group by Hotel.Hotel_Name\n" +
                    "order by No_of_foreign_National_who_reviewed desc";
            ResultSet rs = stmt.executeQuery(SQL);

            // Iterate through the data in the result set and display it.
            System.out.println("Hotels with foreign national\n");
            System.out.println("---(Hotel Name, No. of foreign national who reviewed)---\n");

            while (rs.next()) {
                System.out.println("" + rs.getString("Hotel_Name") + "," +
                        " " + rs.getString("No_of_foreign_National_who_reviewed") + "");
                System.out.println("-----------------------------------------------------------------");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}




