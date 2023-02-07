import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import java.awt.event.ActionListener;
import java.io.Console;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;

public class AddBook implements ActionListener {

    JPanel panel;
    JFrame frame;

    JLabel bookName;
    JLabel authorName;
    JLabel authorSurname;
    JLabel noOfPages;
    JLabel genres;

    JTextField bookNameText;
    JTextField authorNameText;
    JTextField authorSurnameText;
    JTextField noOfPagesText;
    JTextField genresText;

    JButton newButton;
    JButton nazajButton;

    GridBagConstraints gbc = new GridBagConstraints();

    public AddBook() {

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        frame = new JFrame();
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Dodajanje knjige");
        panel.setBackground(new Color(76, 218, 240));

        bookName = new JLabel("Ime knjige:");
        bookName.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.weighty = 0.5;
        panel.add(bookName, gbc);

        authorName = new JLabel("Ime avtorja:");
        authorName.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(authorName, gbc);

        authorSurname = new JLabel("Priimek avtorja:");
        authorSurname.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(authorSurname, gbc);

        noOfPages = new JLabel("Stevilo strani:");
        noOfPages.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(noOfPages, gbc);

        genres = new JLabel("Zanri:");
        genres.setFont(new Font("Arial", Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(genres, gbc);

        bookNameText = new JTextField();
        bookNameText.setPreferredSize(new Dimension(150, 30));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.7;
        panel.add(bookNameText, gbc);

        authorNameText = new JTextField();
        authorNameText.setPreferredSize(new Dimension(150, 30));
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(authorNameText, gbc);

        authorSurnameText = new JTextField();
        authorSurnameText.setPreferredSize(new Dimension(150, 30));
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(authorSurnameText, gbc);

        noOfPagesText = new JTextField();
        noOfPagesText.setPreferredSize(new Dimension(150, 30));
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(noOfPagesText, gbc);

        genresText = new JTextField();
        genresText.setPreferredSize(new Dimension(150, 30));
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(genresText, gbc);

        newButton = new JButton("Add item");
        newButton.addActionListener(this);
        newButton.setBackground(new Color(77, 152, 218));
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(newButton, gbc);

        nazajButton = new JButton("Nazaj");
        nazajButton.addActionListener(this);
        nazajButton.setBackground(new Color(77, 152, 218));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(nazajButton, gbc);

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {

        new AddBook();
    }

    public void createNewBook(String ime, String pisateljIme, String pisateljPriimek, String steviloStrani,
            String zanri) {
        try {
            Integer temp = 1;
            for (int i = 0; i < zanri.length(); i++) {
                if (zanri.charAt(i) == ',') {
                    temp++;
                }
            }
            Class.forName("org.postgresql.Driver");
            Connection c = DriverManager
                    .getConnection(
                            "jdbc:postgresql://ep-wild-darkness-767526.eu-central-1.aws.neon.tech/neondb?user=nik.krnjovsek&password=a1hjwRmFZeE0",
                            "nik.krnjovsek", "a1hjwRmFZeE0");
            Statement select = c.createStatement();
            String sql = "SELECT ustvariKnjigo('" + ime + "', '" + pisateljIme + "','" +
                    pisateljPriimek + "', " + steviloStrani + ", '" + zanri + "', " + temp + ")";
            ResultSet rs = select.executeQuery(sql);

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newButton) {
            createNewBook(bookNameText.getText(), authorNameText.getText(), authorSurnameText.getText(),
                    noOfPagesText.getText(), genresText.getText());

        }
        Dashboard dashboard = new Dashboard();
        frame.dispose();
    }
}