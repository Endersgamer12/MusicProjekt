import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.event.ActionEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class RegisterPage implements ActionListener {

    JPanel panel;
    JFrame frame;

    JLabel usernamelabel;
    JLabel passwordlabel;

    private static JTextField userText;
    private static JTextField passwordText;

    JButton registerButton;
    JButton loginButton;

    public RegisterPage() {

        panel = new JPanel();
        frame = new JFrame();
        frame.setSize(350, 240);
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

        registerButton = new JButton("Register");
        registerButton.addActionListener(this);
        registerButton.setBounds(100, 100, 120, 25);
        panel.add(registerButton);

        loginButton = new JButton("Have an account?");
        loginButton.addActionListener(this);
        loginButton.setBounds(100, 150, 120, 20);
        loginButton.setFont(new Font("Arial", Font.PLAIN, 8));
        panel.add(loginButton);

        frame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {

        new RegisterPage();
    }

    public void gettrust() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection c = DriverManager
                    .getConnection(
                            "jdbc:postgresql://ep-wild-darkness-767526.eu-central-1.aws.neon.tech/neondb?user=nik.krnjovsek&password=a1hjwRmFZeE0",
                            "nik.krnjovsek", "a1hjwRmFZeE0");
            Statement select = c.createStatement();
            String sql = "SELECT register('" + userText.getText() + "', '" + passwordText.getText() + "')";
            select.executeQuery(sql);

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            gettrust();
            LoginPage loginPage = new LoginPage();
            frame.dispose();
        }
        if (e.getSource() == loginButton) {
            LoginPage loginPage = new LoginPage();
            frame.dispose();
        }
    }
}