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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Graphics;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Objects;
import java.applet.Applet;
import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;

public class Dashboard implements ActionListener {

    JPanel panel;
    JFrame frame;

    String[][] dataArray = new String[1000][1000];

    JButton editButton;
    JButton newButton;
    JButton deleteButton;
    JButton izvozButton;

    JTable booksTable;
    JScrollPane scrollPane;

    GridBagConstraints gbc = new GridBagConstraints();

    public Dashboard() {

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        frame = new JFrame();
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setBackground(new Color(76, 218, 240));
        frame.setTitle("Dashboard");

        newButton = new JButton("Dodaj knjigo");
        newButton.addActionListener(this);
        newButton.setBackground(new Color(77, 152, 218));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(newButton, gbc);

        editButton = new JButton("Spremeni oznaceno knjigo");
        editButton.addActionListener(this);
        editButton.setBackground(new Color(77, 152, 218));
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(editButton, gbc);

        deleteButton = new JButton("Izbrisi oznaceno knjigo");
        deleteButton.addActionListener(this);
        deleteButton.setBackground(new Color(77, 152, 218));
        gbc.gridx = 2;
        gbc.gridy = 1;
        panel.add(deleteButton, gbc);

        izvozButton = new JButton("Izvoz podatkov");
        izvozButton.addActionListener(this);
        izvozButton.setBackground(new Color(77, 152, 218));
        gbc.gridx = 3;
        gbc.gridy = 1;
        panel.add(izvozButton, gbc);

        getBooksData();
        String vrste[] = { "ID", "Knjiga", "Ime avtorja", "Priimek avtorja", "St. strani", "Zanri", "Datum izdaje" };
        booksTable = new JTable(dataArray, vrste);
        booksTable.setDefaultEditor(Object.class, null);
        booksTable.getColumnModel().getColumn(0).setPreferredWidth(1);
        booksTable.setFont(new Font("Arial", Font.PLAIN, 14));
        booksTable.setBackground(new Color(77, 218, 180));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        scrollPane = new JScrollPane(booksTable);
        scrollPane.setPreferredSize(new Dimension(800, 500));
        scrollPane.setFont(new Font("Arial", Font.PLAIN, 14));
        scrollPane.setVisible(true);
        panel.add(scrollPane, gbc);

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {

        new Dashboard();
    }

    public void getBooksData() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection c = DriverManager
                    .getConnection(
                            "jdbc:postgresql://ep-wild-darkness-767526.eu-central-1.aws.neon.tech/neondb?user=nik.krnjovsek&password=a1hjwRmFZeE0",
                            "nik.krnjovsek", "a1hjwRmFZeE0");
            Statement select = c.createStatement();
            String sql = "SELECT dashboardBooks()";
            ResultSet rs = select.executeQuery(sql);
            Integer counter = 0;
            while (rs.next()) {
                String item = rs.getString(1);
                item = item.replace("(", "");
                item = item.replace(")", "");
                item = item.replace("\"", "");
                String zanri = item.substring(item.indexOf("{") + 1, item.indexOf("}"));
                item = item.replace(zanri, "");
                item = item.replace("{", "");
                item = item.replace("}", "");
                System.out.println(item);
                String[] chop = item.split(",");
                String idKnjige = chop[0];
                String imeKnjige = chop[1];
                String imeAvtorja = chop[2];
                String priimekAvtorja = chop[3];
                String steviloStrani = chop[4];
                String datumIzdaje = "ni podatka";
                if (chop.length > 5) {
                    datumIzdaje = chop[6];
                }
                dataArray[counter][0] = idKnjige;
                dataArray[counter][1] = imeKnjige;
                dataArray[counter][2] = imeAvtorja;
                dataArray[counter][3] = priimekAvtorja;
                dataArray[counter][4] = steviloStrani;
                dataArray[counter][5] = zanri;
                dataArray[counter][6] = datumIzdaje;
                counter++;
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void deleteBook(String IdKnjige) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection c = DriverManager
                    .getConnection(
                            "jdbc:postgresql://ep-wild-darkness-767526.eu-central-1.aws.neon.tech/neondb?user=nik.krnjovsek&password=a1hjwRmFZeE0",
                            "nik.krnjovsek", "a1hjwRmFZeE0");
            Statement select = c.createStatement();
            String sql = "SELECT deleteBook(" + IdKnjige + ")";
            ResultSet rs = select.executeQuery(sql);

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newButton) {
            AddBook addBook = new AddBook();
            frame.dispose();
        } else if (e.getSource() == editButton) {
            EditBook editBook = new EditBook();
            editBook.bookNameText
                    .setText(booksTable.getModel().getValueAt(booksTable.getSelectedRow(), 1).toString());
            editBook.authorNameText
                    .setText(booksTable.getModel().getValueAt(booksTable.getSelectedRow(), 2).toString());
            editBook.authorSurnameText
                    .setText(booksTable.getModel().getValueAt(booksTable.getSelectedRow(), 3).toString());
            editBook.noOfPagesText
                    .setText(booksTable.getModel().getValueAt(booksTable.getSelectedRow(), 4).toString());
            editBook.idKnjige = booksTable.getModel().getValueAt(booksTable.getSelectedRow(), 0).toString();
            frame.dispose();
        } else if (e.getSource() == deleteButton) {
            deleteBook(booksTable.getModel().getValueAt(booksTable.getSelectedRow(), 0).toString());
            Dashboard dashboard = new Dashboard();
            frame.dispose();
        } else if (e.getSource() == izvozButton) {
            try (Connection c = DriverManager
                    .getConnection(
                            "jdbc:postgresql://ep-wild-darkness-767526.eu-central-1.aws.neon.tech/neondb?user=nik.krnjovsek&password=a1hjwRmFZeE0",
                            "nik.krnjovsek", "a1hjwRmFZeE0")) {
                CopyManager copyManager = new CopyManager((BaseConnection) c);
                File file = new File("C:/Users/nikkr/Desktop/temporar/podatki.csv");
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                // and finally execute the COPY command to the file with this method:
                copyManager.copyOut("COPY ("
                        + "SELECT  DISTINCT k.id, k.ime, p.ime, p.priimek, k.stevilo_strani, z.ime, k.datum_objave FROM knjige k INNER JOIN pisatelji p on k.pisatelji_id = p.id INNER JOIN zanri_knjige zk ON k.id = zk.knjiga_id INNER JOIN zanri z ON zk.zanr_id = z.id"
                        + ") TO STDOUT WITH (FORMAT CSV, HEADER)", fileOutputStream);
            } catch (SQLException | IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }

    }
}