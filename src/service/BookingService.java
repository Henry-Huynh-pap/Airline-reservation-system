package service;

import dao.*;
import model.*;

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
            throw new IllegalArgumentException("Can't find booking Id");
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
}
