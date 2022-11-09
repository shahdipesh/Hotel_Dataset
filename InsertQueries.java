import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

import static java.lang.Integer.parseInt;

public class InsertQueries {

    public static void insertNegativeReviewWordCount(String connectionUrl) throws IOException, SQLException {
        Connection conn = DriverManager.getConnection(connectionUrl);
        Statement statement = conn.createStatement();
        BufferedReader br = new BufferedReader(new FileReader("/Users/dipeshasd/Desktop/DemoJavaProjectRelease/Negative_Review_Word_Count.csv"));
        String line = null;
        br.readLine();
        //read the file line by line
        while ((line = br.readLine()) != null) {
            System.out.println(line);
            String[] values = line.split(",");
            String word = values[0].trim();
            //take only 100 words from word
            if (word.length() > 100) {
                word = word.substring(0, 100);
                word+="...";
            }
            int count = parseInt(values[1].trim());
            //insert the data into the database  and on duplicate key do nothin
            String sqlCheck = "Select * from Negative_Review_Word_Count where Negative_Review = '"+word+"'";
            ResultSet rs = statement.executeQuery(sqlCheck);
            //if the word is not present in the database then insert it
            if(!rs.next()){
                String sql = "INSERT INTO Negative_Review_Word_Count VALUES ('" + word + "', '" + count + "')";
                statement.execute(sql);
            }
        }
    }
}
