package service;

import dao.*;
import model.*;

import java.time.LocalDateTime;
import java.util.*;

public class BookingService {

    private final FlightDao flightDao;
    private final PassengerDao passengerDao;
    private final BookingDao bookingDao;
    private final PricingService pricingService;

    public BookingService (FlightDao flightDao,PassengerDao passengerDao,BookingDao bookingDao, PricingService pricingService){
        this.flightDao= flightDao;
        this.passengerDao = passengerDao;
        this.bookingDao = bookingDao;
        this.pricingService = pricingService;
    }

    public List<Booking> getBookingsByPassenger(int passengerId){
        if(passengerId <= 0){
            throw new IllegalArgumentException("Passenger Id is not valid");
        }

        return bookingDao.findByPassengerId(passengerId);
    }

    public void cancelBooking(int bookingId){
        if(bookingId <= 0){
            throw new IllegalArgumentException("Booking Id must be greater than 0");
        }

        Booking booking = bookingDao.findById(bookingId);

        if(booking == null){
            throw new NoSuchElementException("Can't find your booking");
        }

        if(booking.getStatus() == BookingStatus.CANCELLED) {
            throw new IllegalArgumentException("Your booking got cancelled");
        }

        boolean success = bookingDao.cancelBooking(bookingId);
        if(!success){
            throw new RuntimeException("Cancel failed, please try again");
        }
    }

    public Booking bookTicket(int flightId,int passengerId, String seatNumber, TicketClass ticketClass){
        if(flightId <= 0 || passengerId <= 0){
            throw new IllegalArgumentException("Flight Id and Passenger Id must be greater than 0");
        }
        if(seatNumber == null || seatNumber.isBlank()){
            throw new IllegalArgumentException("Seat number must not be empty");
        }
        if(ticketClass == null){
            throw new IllegalArgumentException("Ticket class must not be null");
        }



        Flight flight = flightDao.findById(flightId);

        if(flight == null){
            throw new NoSuchElementException("Flight not found");
        }

        Passenger passenger = passengerDao.findById(passengerId);

        if(passenger == null){
            throw new NoSuchElementException("Passenger not found");
        }

        if(flight.getDepartureTime().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("Flight has already departed");
        }

        if(flight.getAvailableSeats() <= 0){
            throw new IllegalArgumentException("No seats left on this flight");
        }

        String seat = seatNumber.trim().toUpperCase();

        if(bookingDao.findTakenSeats(flightId).contains(seat)){
            throw new IllegalArgumentException("The seat is already booked");
        }

        double price = pricingService.calculatePrice(flight.getBasePrice(), ticketClass);

        Booking booking = new Booking( flightId, passengerId,seat,ticketClass,price);

        boolean ok = bookingDao.bookTicket(booking);

        if(!ok){
            throw new RuntimeException("Booking failed: the seat was just taken or the flight is full");
        }

        booking.setStatus(BookingStatus.CONFIRMED);

        return booking;
    }
}
