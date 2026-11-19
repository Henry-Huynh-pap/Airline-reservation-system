package dao;

import model.Booking;
import java.util.*;

public interface BookingDao {
    List<Booking> findAll();
    Booking findById(int bookingId);
    List<Booking> findByPassengerId(int passengerId);
    List<String> findTakenSeats(int flightId);
    boolean bookTicket(Booking booking);
}
