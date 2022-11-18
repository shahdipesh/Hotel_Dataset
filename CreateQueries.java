import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

import static java.lang.Integer.parseInt;

public class CreateQueries {

    public  void createAddress(String connectionUrl) throws SQLException, IOException {
        Connection conn = DriverManager.getConnection(connectionUrl);
        Statement statement = conn.createStatement();

        String SQL = "create table Address\n" +
                "(\n" +
                "    Hotel_Address varchar(255) not null\n" +
                "        primary key,\n" +
                "    Hotel_lat     float,\n" +
                "    Hotel_lng     float,\n" +
                "    constraint Address_1_Coordinate_Hotel_lat_Hotel_lng_fk\n" +
                "        foreign key (Hotel_lat, Hotel_lng) references Coordinate\n" +
                ")\n" +
                "go";

        PreparedStatement preparedStatement = conn.prepareStatement(SQL);

        //if table does not exist, create it
        try {
            preparedStatement.execute();
            System.out.println("Table Address created");
        } catch (SQLException e) {
            System.out.println("Table Address already exists");
        }




    }

    public void createCityInfo(String connectionUrl) throws SQLException, IOException {
        Connection conn = DriverManager.getConnection(connectionUrl);
        Statement statement = conn.createStatement();

        String SQL = "create table City_Info\n" +
                "(\n" +
                "    Hotel_City    varchar(255) not null\n" +
                "        primary key,\n" +
                "    Hotel_State   varchar(255),\n" +
                "    Hotel_Country varchar(255)\n" +
                "        constraint City_Info_Nationality_Country_Info_Reviewer_Country_fk\n" +
                "            references Nationality_Country_Info\n" +
                ")\n" +
                "go\n";

        PreparedStatement preparedStatement = conn.prepareStatement(SQL);

        //if table does not exist, create it
        try {
            preparedStatement.execute();
            System.out.println("Table CityInfo created");
        } catch (SQLException e) {
            System.out.println("Table CityInfo already exists");
        }

    }

    public void createCoordinate(String connectionUrl) throws SQLException, IOException {
        Connection conn = DriverManager.getConnection(connectionUrl);
        Statement statement = conn.createStatement();

        String SQL = "create table Coordinate\n" +
                "(\n" +
                "    Hotel_lat               float not null,\n" +
                "    Hotel_lng               float not null,\n" +
                "    Hotel_City              varchar(255)\n" +
                "        constraint Coordinate_City_Info_Hotel_City_fk\n" +
                "            references City_Info,\n" +
                "    Businesses_100m         smallint,\n" +
                "    Businesses_1km          smallint,\n" +
                "    Businesses_5km          smallint,\n" +
                "    Total_Number_of_Reviews smallint,\n" +
                "    primary key (Hotel_lat, Hotel_lng)\n" +
                ")\n" +
                "go";

        PreparedStatement preparedStatement = conn.prepareStatement(SQL);

        //if table does not exist, create it
        try {
            preparedStatement.execute();
            System.out.println("Table Coordinate created");
        } catch (SQLException e) {
            System.out.println("Table Coordinate already exists");
        }

    }

    public void createDate(String connectionUrl) throws SQLException, IOException {
        Connection conn = DriverManager.getConnection(connectionUrl);
        Statement statement = conn.createStatement();

        String SQL = "create table Date\n" +
                "(\n" +
                "    Day_of_Week      int,\n" +
                "    Day_of_Year      int\n" +
                "        constraint Day_of_Year\n" +
                "            references Part_of_year\n" +
                "            on update cascade on delete cascade,\n" +
                "    Week_of_Month    int,\n" +
                "    Week_of_Year     varchar(max),\n" +
                "    Review_Date      int not null\n" +
                "        primary key,\n" +
                "    Is_Hotel_Holiday int\n" +
                ")\n" +
                "go\n";

        PreparedStatement preparedStatement = conn.prepareStatement(SQL);

        //if table does not exist, create it
        try {
            preparedStatement.execute();
            System.out.println("Table Date created");
        } catch (SQLException e) {
            System.out.println("Table Date already exists");
        }

    }

    public void createHotel(String connectionUrl) throws SQLException, IOException {
        Connection conn = DriverManager.getConnection(connectionUrl);
        Statement statement = conn.createStatement();

        String SQL = "create table Hotel\n" +
                "(\n" +
                "    Hotel_Name    varchar(255) not null\n" +
                "        primary key,\n" +
                "    Hotel_Address varchar(255)\n" +
                "        constraint Hotel_Address_Hotel_Address_fk\n" +
                "            references Address\n" +
                ")\n" +
                "go";

        PreparedStatement preparedStatement = conn.prepareStatement(SQL);

        //if table does not exist, create it
        try {
            preparedStatement.execute();
            System.out.println("Table Hotel created");
        } catch (SQLException e) {
            System.out.println("Table Hotel already exists");
        }

    }

    public void createNationalityCountryInfo(String connectionUrl) throws SQLException, IOException {
        Connection conn = DriverManager.getConnection(connectionUrl);
        Statement statement = conn.createStatement();

        String SQL = "create table Nationality_Country_Info\n" +
                "(\n" +
                "    Reviewer_Nationality varchar(255)\n" +
                "        unique,\n" +
                "    Reviewer_Country     varchar(255) not null\n" +
                "        primary key\n" +
                ")\n" +
                "go\n";

        PreparedStatement preparedStatement = conn.prepareStatement(SQL);
        //if table does not exist, create it
        try {
            preparedStatement.execute();
            System.out.println("Table NationalityCountryInfo created");
        } catch (SQLException e) {
            System.out.println("Table NationalityCountryInfo already exists");
        }
    }

    public void createNegativeReviewWordCount(String connectionUrl) throws SQLException, IOException {
        Connection conn = DriverManager.getConnection(connectionUrl);
        Statement statement = conn.createStatement();

        String SQL = "create table Negative_Review_Word_Count\n" +
                "(\n" +
                "    Negative_Review                   varchar(500) not null\n" +
                "        primary key,\n" +
                "    Review_Total_Negative_Word_Counts int\n" +
                ")\n" +
                "go\n";

        PreparedStatement preparedStatement = conn.prepareStatement(SQL);
        //if table does not exist, create it
        try {
            preparedStatement.execute();
            System.out.println("Table NegativeReviewWordCount created");
        } catch (SQLException e) {
            System.out.println("Table NegativeReviewWordCount already exists");
        }
    }

    public void createPartOfYear(String connectionUrl) throws SQLException, IOException {
        Connection conn = DriverManager.getConnection(connectionUrl);
        Statement statement = conn.createStatement();

        String SQL = "create table Part_of_year\n" +
                "(\n" +
                "    Day_of_Year     int not null\n" +
                "        primary key,\n" +
                "    Quarter_of_Year int\n" +
                ")\n" +
                "go";

        PreparedStatement preparedStatement = conn.prepareStatement(SQL);
        //if table does not exist, create it
        try {
            preparedStatement.execute();
            System.out.println("Table PartOfYear created");
        } catch (SQLException e) {
            System.out.println("Table PartOfYear already exists");
        }
    }

    public void createPositiveReviewWordCount(String connectionUrl) throws SQLException, IOException {
        Connection conn = DriverManager.getConnection(connectionUrl);
        Statement statement = conn.createStatement();

        String SQL = "create table Positive_Review_Word_Count\n" +
                "(\n" +
                "    Positive_Review                   varchar(255) not null\n" +
                "        primary key,\n" +
                "    Review_Total_Positive_Word_Counts int\n" +
                ")\n" +
                "go";

        PreparedStatement preparedStatement = conn.prepareStatement(SQL);
        //if table does not exist, create it
        try {
            preparedStatement.execute();
            System.out.println("Table PositiveReviewWordCount created");
        } catch (SQLException e) {
            System.out.println("Table PositiveReviewWordCount already exists");
        }
    }

    public void createReview(String connectionUrl) throws SQLException, IOException {
        Connection conn = DriverManager.getConnection(connectionUrl);
        Statement statement = conn.createStatement();

        String SQL = "create table Review\n" +
                "(\n" +
                "    id                                         int,\n" +
                "    Hotel_Name                                 varchar(max),\n" +
                "    Room_Type                                  varchar(max),\n" +
                "    Room_Type_Level                            varchar(max),\n" +
                "    Bed_Type                                   varchar(max),\n" +
                "    Guest_Type                                 varchar(max),\n" +
                "    Trip_Type                                  varchar(max),\n" +
                "    Stay_Duration                              int,\n" +
                "    Review_Date                                int\n" +
                "        constraint Review_Date_Review_Date_fk\n" +
                "            references Date,\n" +
                "    Days_Since_Review                          varchar(max),\n" +
                "    Review_Is_Positive                         int,\n" +
                "    Reviewer_Nationality                       varchar(max),\n" +
                "    Negative_Review                            varchar(500)\n" +
                "        constraint Review_Negative_Review_Word_Count_Negative_Review_fk\n" +
                "            references Negative_Review_Word_Count,\n" +
                "    Positive_Review                            varchar(255)\n" +
                "        constraint Review_Positive_Review_Word_Count_Positive_Review_fk\n" +
                "            references Positive_Review_Word_Count,\n" +
                "    Average_Score                              int,\n" +
                "    Reviewer_Score                             int,\n" +
                "    Total_Number_of_Reviews_Reviewer_Has_Given int,\n" +
                "    Additional_Number_of_Scoring               int,\n" +
                "    Submitted_from_Mobile                      int,\n" +
                "    Is_Reviewer_Holiday                        int\n" +
                ")\n" +
                "go";

        PreparedStatement preparedStatement = conn.prepareStatement(SQL);
        //if table does not exist, create it
        try {
            preparedStatement.execute();
            System.out.println("Table Review created");
        } catch (SQLException e) {
            System.out.println("Table Review already exists");
        }
    }
}
