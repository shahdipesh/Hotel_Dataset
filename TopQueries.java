import java.sql.*;
import java.text.DecimalFormat;

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

            boolean first = true;

            System.out.println("\n\n|--------------------------------------------------------------------------| ");
            System.out.println("| Hotel Name || Number of positive Reviews || Number of Negative Reviews)  |");
            System.out.println("|--------------------------------------------------------------------------| \n");

            while (rs.next()) {
                if (first) {
                    System.out.println("|--------------------------------------------------------------------------|");
                    first = false;
                }

                System.out.println(" "+rs.getString("Hotel_Name") + " || " + rs.getString("Total_positive_reviews") + " || " + rs.getString("Total_negative_reviews"));
                System.out.println("|--------------------------------------------------------------------------|");

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
            System.out.println("\n\n|--------------------------------------------------------------------------| ");
            System.out.println("| Hotel Name || Average Stay Duration in Days |");
            System.out.println("|--------------------------------------------------------------------------| \n");

            while (rs.next()) {
                System.out.println("| " + rs.getString("Hotel_Name") + " || " + rs.getString("Average_stay_duration_in_days"));
                System.out.println("|--------------------------------------------------------------------------|");

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

            System.out.println("\n\n|-------------------------------------------------------------------| ");
            System.out.println("|    Trip Type || Average Stay Duration in Days |");
            System.out.println("|-------------------------------------------------------------------| \n");


            while (rs.next()) {
                System.out.println("|    " + rs.getString("Trip_Type") + " || " + rs.getString("avgStayDuration") );
                System.out.println("|-------------------------------------------------------------------|");
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

            System.out.println("\n------Quarter Of Year-------||---------No. of Reviews------------");

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

            System.out.println("\n|------Country-------||---------Avg Rating of hotels--------------|\n");

            while (rs.next()) {
                System.out.println("    " + rs.getString("Country").trim() + "   ||  " +
                        " " + rs.getString("Avg_Hotel_Rating") + "");
                System.out.println("|-----------------------------------------------------------------|");
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
                    "order by No_of_foreign_National_who_reviewed desc\n";
            ResultSet rs = stmt.executeQuery(SQL);

            // Iterate through the data in the result set and display it.

            System.out.println("\n\n|----Hotel Name || Hotel Country || No. of foreign national)----|\n");

            while (rs.next()) {
                System.out.println(" " + rs.getString("Hotel_Name") + " || " +
                        " "+rs.getString("location") +" || "+ rs.getString("No_of_foreign_National_who_reviewed") + "");
                System.out.println("|---------------------------------------------------------------------------|");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void percentReviewInHoliday(String connectionUrl) {

        try (Connection con = DriverManager.getConnection(connectionUrl);
             Statement stmt = con.createStatement();) {
            String sql = "(select (count(*)*1.0/(select count(*)*1.0 from Review as aa)*100)as PercentReviewByMob  from Review where Submitted_from_Mobile=1 )\n";
            ResultSet rs = stmt.executeQuery(sql);

            //get only 2 decimal places
            DecimalFormat df = new DecimalFormat("#.##");

            while (rs.next()) {
                System.out.println( df.format(rs.getDouble("PercentReviewByMob")) + "% reviews were made using mobile phone \n");

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}




