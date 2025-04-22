package gui;

import model.Booking;
import model.User;
import service.BookingService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CancelPanel extends JPanel {
    private JTable bookingTable;
    private DefaultTableModel tableModel;
    private BookingService bookingService;
    private User currentUser;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public CancelPanel(User user, CardLayout cardLayout, JPanel cardPanel) {
        this.currentUser = user;
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.bookingService = new BookingService();

        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"Booking ID", "Flight ID", "Seat No", "Status"}, 0);
        bookingTable = new JTable(tableModel);
        add(new JScrollPane(bookingTable), BorderLayout.CENTER);

        // Bottom button panel
        JPanel bottomPanel = new JPanel();
        JButton cancelBtn = new JButton("Cancel Selected Booking");
        JButton backBtn = new JButton("Back to Home");

        cancelBtn.addActionListener(e -> cancelSelectedBooking());
        backBtn.addActionListener(e -> cardLayout.show(cardPanel, "Home"));

        bottomPanel.add(cancelBtn);
        bottomPanel.add(backBtn);

        add(bottomPanel, BorderLayout.SOUTH);

        loadBookings();
    }

    // Load bookings of the current user

    public void loadBookings() {
        tableModel.setRowCount(0); // Clear existing data
        List<Booking> bookings = bookingService.getAllBookings();

        // Add bookings to the table
        for (Booking b : bookings) {
            if (b.getStatus().equalsIgnoreCase("booked")) {
            tableModel.addRow(new Object[]{
                    b.getId(),
                    b.getUserId(),
                    b.getPassengerName(),
                    b.getFlightId(),
                    b.getSeatNo(),
                    b.getStatus()
                });
            }
        }
    }

    // Cancel selected booking
    private void cancelSelectedBooking() {
        int selectedRow = bookingTable.getSelectedRow();
        if (selectedRow >= 0) {
            int bookingId = (int) tableModel.getValueAt(selectedRow, 0);

            // Cancel the booking and check if it was successful
            boolean isCanceled = bookingService.cancelBooking(bookingId);

            if (isCanceled) {
                JOptionPane.showMessageDialog(this, "Booking canceled successfully.");
                loadBookings();  // Refresh the table to show updated list of bookings
            } else {
                JOptionPane.showMessageDialog(this, "Failed to cancel booking.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a booking to cancel.");
        }
    }
}
