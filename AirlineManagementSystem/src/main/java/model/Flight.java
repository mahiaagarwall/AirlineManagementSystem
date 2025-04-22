package model;

import java.time.LocalDateTime;

public class Flight {
    private int id;
    private String Source;
    private String Destination;
    private LocalDateTime DepartureTime;
    private String AircraftId;
    private String FlightName;

    // Constructor, Getters & Setters
    public Flight(int id, String Source, String Destination, LocalDateTime DepartureTime, String AircraftId, String FlightName) {
        this.id = id;
        this.Source = Source;
        this.Destination = Destination;
        this.DepartureTime = DepartureTime;
        this.AircraftId = AircraftId;
        this.FlightName = FlightName;
    }

    public int getId() {
        return id;
    }

    public String getSource() {
        return Source;
    }
    
    public String getDestination() {
        return Destination;
    }

    public LocalDateTime getDepartureTime() {
        return DepartureTime;
    }

    public String getAircraftId() {
        return AircraftId;
    }

    public String getFlightName() {
        return FlightName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSource(String Source) {
        this.Source = Source;
    }

    public void setDestination(String Destination) {
        this.Destination = Destination;
    }

    public void setDepartureTime(LocalDateTime DepartureTime) {
        this.DepartureTime = DepartureTime;
    }

    public void setAircraftId(String AircraftId) {
        this.AircraftId = AircraftId;
    }

    public void setFlightName(String FlightName) {
        this.FlightName = FlightName;
    }
}
