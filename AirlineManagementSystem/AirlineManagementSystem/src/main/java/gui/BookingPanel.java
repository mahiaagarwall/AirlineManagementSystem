package gui;

import model.Booking;
import model.User;
import service.BookingService;
import service.FlightService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class BookingPanel extends JPanel {
    private JTable bookingTable;
    private DefaultTableModel tableModel;
    private BookingService bookingService;
    private User currentUser;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public BookingPanel(User user, CardLayout cardLayout, JPanel cardPanel) {
        this.currentUser = user;
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        this.bookingService = new BookingService();
        new FlightService();

        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{
                "Booking ID", "Passenger ID", "Passenger Name", "Flight ID", "Seat No", "Status"
        }, 0);
        bookingTable = new JTable(tableModel);
        add(new JScrollPane(bookingTable), BorderLayout.CENTER);

        JButton refreshBtn = new JButton("Refresh");
        JButton bookFlightBtn = new JButton("Book Flight");
        JButton homeBtn = new JButton("Home");
        JButton printTicketBtn = new JButton("Print Ticket"); // New Button

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(refreshBtn);
        bottomPanel.add(bookFlightBtn);
        bottomPanel.add(printTicketBtn); // Add new button to panel
        bottomPanel.add(homeBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        refreshBtn.addActionListener(e -> loadBookings());
        bookFlightBtn.addActionListener(e -> showBookingDialog());
        printTicketBtn.addActionListener(e -> printSelectedTicket()); // New Listener
        homeBtn.addActionListener(e -> cardLayout.show(cardPanel, "Home"));

        loadBookings();
    }

    public void loadBookings() {
        tableModel.setRowCount(0); // Clear existing data
        List<Booking> bookings = bookingService.getAllBookings();

        for (Booking b : bookings) {
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

    private void showBookingDialog() {
        JTextField passengerIdField = new JTextField();
        JTextField flightIdField = new JTextField();
        JTextField seatNoField = new JTextField();
        JTextField passengerNameField = new JTextField();

        Object[] message = {
                "Passenger ID:", passengerIdField,
                "Flight ID:", flightIdField,
                "Seat No:", seatNoField,
                "Passenger Name:", passengerNameField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Book Flight", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Booking booking = new Booking(
                    0,
                    Integer.parseInt(passengerIdField.getText()),
                    Integer.parseInt(flightIdField.getText()),
                    seatNoField.getText(),
                    "booked",
                    passengerNameField.getText()
            );
            bookingService.addBooking(booking);
            loadBookings();
        }
    }

    private void printSelectedTicket() {
        int selectedRow = bookingTable.getSelectedRow();
    
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking to print the ticket.");
            return;
        }
    
        String bookingId = tableModel.getValueAt(selectedRow, 0).toString();
        String passengerId = tableModel.getValueAt(selectedRow, 1).toString();
        String passengerName = tableModel.getValueAt(selectedRow, 2).toString();
        String flightId = tableModel.getValueAt(selectedRow, 3).toString();
        String seatNo = tableModel.getValueAt(selectedRow, 4).toString();
        String status = tableModel.getValueAt(selectedRow, 5).toString();
    
        String ticket = "===== FLIGHT TICKET =====\n"
                + "Booking ID: " + bookingId + "\n"
                + "Passenger ID: " + passengerId + "\n"
                + "Passenger Name: " + passengerName + "\n"
                + "Flight ID: " + flightId + "\n"
                + "Seat No: " + seatNo + "\n"
                + "Status: " + status + "\n"
                + "=========================";
    
        // Create a JTextArea for the ticket preview
        JTextArea textArea = new JTextArea(ticket);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
    
        // Create a custom panel with "OK" and "Print" buttons
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
    
        // Create buttons for "OK" and "Print"
        JButton okButton = new JButton("OK");
        JButton printButton = new JButton("Print");
    
        // Add button actions
        okButton.addActionListener(e -> {
            // Close the dialog when OK is clicked
            Window window = SwingUtilities.windowForComponent(okButton);
            if (window != null) {
                window.dispose();  // Close the JDialog
            }
        });
    
        printButton.addActionListener(e -> saveTicketToFile(ticket, bookingId)); // Trigger save ticket function
    
        // Panel to hold the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(printButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    
        // Create the dialog
        JDialog ticketDialog = new JDialog((Frame) null, "Ticket Preview", true);
        ticketDialog.setSize(400, 300);
        ticketDialog.setLocationRelativeTo(this);  // Center the dialog on the screen
        ticketDialog.add(panel);
        ticketDialog.setVisible(true);  // Show the dialog
    }
    
    private void saveTicketToFile(String ticket, String bookingId) {
        // File chooser dialog
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Ticket As");
        fileChooser.setSelectedFile(new File("Ticket_" + bookingId + ".txt"));
        fileChooser.setAcceptAllFileFilterUsed(false); // Disable "All Files" option
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text Files", "txt")); // Only allow text files
    
        int userSelection = fileChooser.showSaveDialog(this);
    
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
    
            // Check if the file already exists
            if (fileToSave.exists()) {
                int confirmOverwrite = JOptionPane.showConfirmDialog(this,
                        "File already exists. Do you want to overwrite it?", "Confirm Overwrite", JOptionPane.YES_NO_OPTION);
                if (confirmOverwrite == JOptionPane.NO_OPTION) {
                    return;
                }
            }
    
            // Write the ticket data to the selected file
            try (FileWriter writer = new FileWriter(fileToSave)) {
                writer.write(ticket);
                JOptionPane.showMessageDialog(this, "Ticket saved successfully:\n" + fileToSave.getAbsolutePath());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving ticket: " + ex.getMessage());
            }
        }
    }
}    