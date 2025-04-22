package service;

import db.DatabaseConnection;
import model.Flight;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FlightService {

    public List<Flight> getAllFlights() {
        List<Flight> flights = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM flights");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Flight flight = new Flight(
                    rs.getInt("id"),
                    rs.getString("Source"),
                    rs.getString("Destination"),
                    rs.getTimestamp("DepartureTime").toLocalDateTime(),
                    rs.getString("AircraftId"),
                    rs.getString("FlightName")
                );
                flights.add(flight);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flights;
    }

    public void addFlight(Flight flight) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                 "INSERT INTO flights (Source, Destination, DepartureTime, AircraftId, FlightName) VALUES (?, ?, ?, ?, ?)"
             )) {

            stmt.setString(1, flight.getSource());
            stmt.setString(2, flight.getDestination());
            stmt.setTimestamp(3, Timestamp.valueOf(flight.getDepartureTime()));
            stmt.setString(4, flight.getAircraftId());
            stmt.setString(5, flight.getFlightName());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
