package model;

import java.time.*;

public class Booking {
    private int bookingId;
    private int flightId;
    private int passengerId;
    private String seatNumber;
    private TicketClass ticketClass;
    private double price;
    private LocalDateTime bookingTime;
    private BookingStatus status;

    public Booking(int bookingId, int flightId, int passengerId, String seatNumber, TicketClass ticketClass, double price, LocalDateTime bookingTime, BookingStatus status) {
        this.bookingId = bookingId;
        this.flightId = flightId;
        this.passengerId = passengerId;
        this.seatNumber = seatNumber;
        this.ticketClass = ticketClass;
        this.price = price;
        this.bookingTime = bookingTime;
        this.status = status;
    }

    public Booking(){

    }

    public Booking(int flightId, int passengerId, String seatNumber, TicketClass ticketClass, double price) {
        this.flightId = flightId;
        this.passengerId = passengerId;
        this.seatNumber = seatNumber;
        this.ticketClass = ticketClass;
        this.price = price;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public TicketClass getTicketClass() {
        return ticketClass;
    }

    public void setTicketClass(TicketClass ticketClass) {
        this.ticketClass = ticketClass;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", flightId=" + flightId +
                ", passengerId=" + passengerId +
                ", seatNumber='" + seatNumber + '\'' +
                ", ticketClass=" + ticketClass +
                ", price=" + price +
                ", bookingTime=" + bookingTime +
                ", status=" + status +
                '}';
    }
}
