package service;

import db.DatabaseConnection;
import model.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingService {

    // Get all bookings
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM bookings");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Booking booking = new Booking(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("flight_id"),
                        rs.getString("seat_no"),
                        rs.getString("status"),
                        rs.getString("passenger_name")  // Get passenger name from database
                );
                bookings.add(booking);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    // Add a booking
    public void addBooking(Booking booking) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO bookings (flight_id, user_id, seat_no, status, passenger_name) VALUES (?, ?, ?, ?, ?)"
             )) {

            stmt.setInt(1, booking.getFlightId());
            stmt.setInt(2, booking.getUserId());
            stmt.setString(3, booking.getSeatNo());
            stmt.setString(4, booking.getStatus());
            stmt.setString(5, booking.getPassengerName());  // Store passenger name

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Cancel a booking and return a boolean indicating success or failure
    public boolean cancelBooking(int bookingId) {
        String sql = "UPDATE bookings SET status = 'cancelled' WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookingId);
            int rowsAffected = stmt.executeUpdate();
            
            // Return true if the update affected at least one row
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;  // Return false if there was an error or no rows were affected
    }

    public int generateUniqueBookingId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateUniqueBookingId'");
    }
}
