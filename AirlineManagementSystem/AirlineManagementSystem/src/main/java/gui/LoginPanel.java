package gui;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    public LoginPanel() {
        setLayout(new GridLayout(3, 2, 10, 10));
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();
        JButton loginBtn = new JButton("Login");

        add(emailLabel); add(emailField);
        add(passLabel); add(passField);
        add(new JLabel("")); add(loginBtn);
    }
}
