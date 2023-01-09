import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.DimensionUIResource;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI implements ActionListener {

    int count = 0;
    JLabel label;
    JPanel panel;
    JButton button;
    JButton button1;
    JFrame frame;

    public GUI() {
        frame = new JFrame();

        button = new JButton("Click");
        button.addActionListener(this);

        label = new JLabel("Number of clicks: " + count);

        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setLayout(new GridLayout());
        panel.add(button);
        panel.add(label);

        frame.setPreferredSize(new DimensionUIResource(300, 200));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("GUI");
        frame.setVisible(true);
    }

    public static void main(String[] args) throws Exception {
        new GUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        count++;
        label.setText("Number of clicks: " + count);
    }
}