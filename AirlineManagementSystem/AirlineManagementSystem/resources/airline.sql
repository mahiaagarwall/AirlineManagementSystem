CREATE DATABASE IF NOT EXISTS airline;
USE airline;

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(100),
    role ENUM('admin', 'passenger', 'staff')
);

CREATE TABLE Aircraft (
    id INT PRIMARY KEY AUTO_INCREMENT,
    model VARCHAR(50),
    capacity INT
);

CREATE TABLE flights (
    id INT PRIMARY KEY AUTO_INCREMENT,
    Source VARCHAR(100),
    Destination VARCHAR(100),
    DepartureTime DATETIME,
    AircraftId INT,
    FOREIGN KEY (AircraftId) REFERENCES Aircraft(id)
);

CREATE TABLE bookings (
    id INT PRIMARY KEY AUTO_INCREMENT,
    UserId INT,
    flightId INT,
    seatNo VARCHAR(5),
    status ENUM('booked', 'cancelled'),
    FOREIGN KEY (UserId) REFERENCES users(id),
    FOREIGN KEY (FlightId) REFERENCES flights(id)
);

CREATE TABLE checkin (
    id INT PRIMARY KEY AUTO_INCREMENT,
    bookingId INT,
    staffId INT,
    checkinTime DATETIME,
    FOREIGN KEY (bookingId) REFERENCES bookings(id),
    FOREIGN KEY (staffId) REFERENCES users(id)
);
