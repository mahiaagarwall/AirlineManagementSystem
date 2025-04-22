package model;

import java.time.LocalDateTime;

public class Checkin {
    private int id;
    private int bookingId;
    private int staffId;
    private LocalDateTime checkinTime;

    // Constructor, Getters & Setters
    public Checkin(int id, int bookingId, int staffId, LocalDateTime checkinTime) {
        this.id = id;
        this.bookingId = bookingId;
        this.staffId = staffId;
        this.checkinTime = checkinTime;
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getBookingId() {
        return bookingId;
    }

    public int getStaffId() {
        return staffId;
    }

    public LocalDateTime getCheckinTime() {
        return checkinTime;
    }

    //Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public void setCheckinTime(LocalDateTime checkinTime) {
        this.checkinTime = checkinTime;
    }
}
