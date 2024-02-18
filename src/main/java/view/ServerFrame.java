package view;

import javax.swing.*;
import java.awt.event.ActionListener;

public class ServerFrame extends JFrame {

    private static final String TITLE = "Servidor";
    public JButton initBtn;
    public JTextField portTxt;
    public ServerFrame() {
        setTitle(TITLE);
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        JLabel label = new JLabel("Puerto:");
        panel.add(label);

        portTxt = new JTextField(20);
        panel.add(portTxt);

        initBtn = new JButton("Init Server");
        panel.add(initBtn);

        getContentPane().add(panel);
    }
    public void setActionListener(ActionListener actionListener) {
        this.initBtn.addActionListener(actionListener);
    }

    public JButton getInitBtn() {
        return initBtn;
    }
}
