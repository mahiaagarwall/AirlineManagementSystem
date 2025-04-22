package model;

public class Booking {
    private int id;
    private int userId;  // Now represents the passenger ID
    private int flightId;
    private String seatNo;
    private String status;
    private String passengerName;  // Passenger Name field

    // Constructor, Getters & Setters
    public Booking(int id, int userId, int flightId, String seatNo, String status, String passengerName) {
        this.id = id;
        this.userId = userId;  // User ID is now the Passenger ID
        this.flightId = flightId;
        this.seatNo = seatNo;
        this.status = status;
        this.passengerName = passengerName;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getUserId() {  // This is the Passenger ID now
        return userId;
    }

    public int getFlightId() {
        return flightId;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public String getStatus() {
        return status;
    }

    public String getPassengerName() {  // Getter for passenger name
        return passengerName;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {  // Set the Passenger ID
        this.userId = userId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPassengerName(String passengerName) {  // Setter for passenger name
        this.passengerName = passengerName;
    }
}
