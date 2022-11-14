import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.function.Function;

public class Util {
    Properties prop = new Properties();
    String username = (prop.getProperty("username"));
    String password = (prop.getProperty("password"));
    String fileName = "auth.cfg";
    String connectionUrl;

    TopQueries topQueries = new TopQueries();

    public void setCallback(Frame frame, Function<String, StringBuilder> func) {

        StringBuilder sb = func.apply(connectionUrl);

        JTextArea textArea = new JTextArea(sb.toString());

        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
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

        //button text colour to red




    }
}


