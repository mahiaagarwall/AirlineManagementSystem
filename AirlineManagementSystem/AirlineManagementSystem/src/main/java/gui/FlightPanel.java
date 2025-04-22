package gui;

import model.Flight;
import service.FlightService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

public class FlightPanel extends JPanel {
    private JTable flightTable;
    private DefaultTableModel tableModel;
    private FlightService flightService;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public FlightPanel(CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

        flightService = new FlightService();

        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"ID", "Flight Name", "Source", "Destination", "Departure Time", "Aircraft ID"}, 0);
        flightTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(flightTable);
        add(scrollPane, BorderLayout.CENTER);

        JButton refreshBtn = new JButton("Refresh");
        JButton addBtn = new JButton("Add Flight");
        JButton homeBtn = new JButton("Home");

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(refreshBtn);
        bottomPanel.add(addBtn);
        bottomPanel.add(homeBtn);

        add(bottomPanel, BorderLayout.SOUTH);

        refreshBtn.addActionListener(e -> loadFlights());
        addBtn.addActionListener(e -> showAddFlightDialog());
        homeBtn.addActionListener(e -> cardLayout.show(cardPanel, "Home"));

        loadFlights();
    }

    private void loadFlights() {
        tableModel.setRowCount(0);
        List<Flight> flights = flightService.getAllFlights();
        for (Flight f : flights) {
            tableModel.addRow(new Object[]{
                f.getId(),
                f.getFlightName(),
                f.getSource(),
                f.getDestination(),
                f.getDepartureTime(),
                f.getAircraftId()
            });
        }
    }

    private void showAddFlightDialog() {
        JTextField flightNameField = new JTextField();
        JTextField originField = new JTextField();
        JTextField destField = new JTextField();
        JTextField timeField = new JTextField("2025-05-01T15:00");  // Example format
        JTextField aircraftField = new JTextField();

        Object[] message = {
            "Flight Name:", flightNameField,
            "Source:", originField,
            "Destination:", destField,
            "Departure (e.g. 2025-05-01T15:00):", timeField,
            "Aircraft ID:", aircraftField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add Flight", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Flight flight = new Flight(
                0,
                originField.getText(),
                destField.getText(),
                LocalDateTime.parse(timeField.getText()),
                aircraftField.getText(),
                flightNameField.getText()
            );
            flightService.addFlight(flight);
            loadFlights();
        }
    }
}
