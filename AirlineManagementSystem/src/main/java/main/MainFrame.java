package main;

import gui.BookingPanel;
import gui.CancelPanel;
import gui.FlightPanel;
import model.User;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private User currentUser;

    // Shared panel instances
    private BookingPanel bookingPanel;
    private CancelPanel cancelPanel;

    public MainFrame(User user) {
        this.currentUser = user;
        setTitle("Airline Management System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Initialize shared panels
        bookingPanel = new BookingPanel(currentUser, cardLayout, cardPanel);
        cancelPanel = new CancelPanel(currentUser, cardLayout, cardPanel);

        // Create home panel with background image
        JPanel homePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon icon = new ImageIcon("resources\\1687344492020.jpeg"); // Image path
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this); // Resize to fit panel
            }
        };
        homePanel.setLayout(new BorderLayout());
        homePanel.setOpaque(false);

        // Content panel with welcome text and quick booking button
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();

        // Welcome Label at top center
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(40, 0, 20, 0);
        JLabel welcomeLabel = new JLabel("Welcome to Airline Management System!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.BLACK);
        contentPanel.add(welcomeLabel, gbc);

        // Quick Booking Button at left middle
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 40, 10, 10);

        JButton quickBookingBtn = new JButton("Book a Flight");
        quickBookingBtn.setFont(new Font("Arial", Font.PLAIN, 18));
        quickBookingBtn.setBackground(new Color(0, 123, 255));
        quickBookingBtn.setForeground(Color.WHITE);
        quickBookingBtn.setFocusPainted(false);
        quickBookingBtn.setBorder(new EmptyBorder(10, 10, 10, 10));
        quickBookingBtn.addActionListener(e -> {
            bookingPanel.loadBookings();
            cardLayout.show(cardPanel, "Booking");
        });

        contentPanel.add(quickBookingBtn, gbc);

        homePanel.add(contentPanel, BorderLayout.CENTER);

        // Add all panels to card layout
        cardPanel.add(homePanel, "Home");
        cardPanel.add(new FlightPanel(cardLayout, cardPanel), "Flight Management");
        cardPanel.add(bookingPanel, "Booking");
        cardPanel.add(cancelPanel, "Cancel Booking");

        // Menu setup
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");

        JMenuItem homeItem = new JMenuItem("Home");
        JMenuItem manageFlightsItem = new JMenuItem("Manage Flights");
        JMenuItem bookFlightItem = new JMenuItem("Book Flight");
        JMenuItem cancelFlightItem = new JMenuItem("Cancel Booking");
        JMenuItem exitItem = new JMenuItem("Exit");

        homeItem.addActionListener(e -> cardLayout.show(cardPanel, "Home"));
        manageFlightsItem.addActionListener(e -> cardLayout.show(cardPanel, "Flight Management"));
        bookFlightItem.addActionListener(e -> {
            bookingPanel.loadBookings();
            cardLayout.show(cardPanel, "Booking");
        });
        cancelFlightItem.addActionListener(e -> {
            cancelPanel.loadBookings();
            cardLayout.show(cardPanel, "Cancel Booking");
        });
        exitItem.addActionListener(e -> System.exit(0));

        menu.add(homeItem);
        menu.add(manageFlightsItem);
        menu.add(bookFlightItem);
        menu.add(cancelFlightItem);
        menu.addSeparator();
        menu.add(exitItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        add(cardPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            User user = new User(1, "admin", "admin", "admin");
            MainFrame frame = new MainFrame(user);
            frame.setVisible(true);
        });
    }
}
