import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.DimensionUIResource;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class GUI implements ActionListener {

    JPanel panel;
    JFrame frame;

    JLabel usernamelabel;
    JLabel passwordlabel;

    private static JTextField userText;
    private static JTextField passwordText;

    JButton loginButton;

    public GUI() {

        panel = new JPanel();
        frame = new JFrame();
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);

        panel.setLayout(null);

        usernamelabel = new JLabel("User");
        usernamelabel.setBounds(10, 20, 80, 25);
        panel.add(usernamelabel);

        userText = new JTextField();
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        passwordlabel = new JLabel("Password");
        passwordlabel.setBounds(10, 60, 80, 25);
        panel.add(passwordlabel);

        passwordText = new JTextField();
        passwordText.setBounds(100, 60, 165, 25);
        panel.add(passwordText);

        loginButton = new JButton(new AbstractAction("login") {
            @Override
            public void actionPerformed(ActionEvent e) {
                gettrust();

            }
        });
        loginButton.setBounds(100, 100, 120, 25);
        panel.add(loginButton);

        frame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {

        new GUI();
    }

    public void gettrust() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection c = DriverManager
                    .getConnection(
                            "jdbc:postgresql://ep-wild-darkness-767526.eu-central-1.aws.neon.tech/neondb?user=nik.krnjovsek&password=a1hjwRmFZeE0",
                            "nik.krnjovsek", "a1hjwRmFZeE0");
            Statement select = c.createStatement();
            String sql = "SELECT login('" + userText.getText() + "', '" + passwordText.getText() + "')";
            ResultSet rs = select.executeQuery(sql);
            if (rs.next()) {
                if (rs.getBoolean(1)) {
                    System.out.print("logged in!");
                } else {
                    System.out.print("napacni podatki!");
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }
}