package service;

import dao.FlightDao;
import model.Flight;

import java.time.*;
import java.util.*;

public class FlightService {

    private final FlightDao flightDao;

    public FlightService(FlightDao flightDao){
        this.flightDao = flightDao;
    }

    public List<Flight> getAllFlights() {
        return flightDao.findAll();
    }

    public Flight getFlightById(int flightId){
        if(flightId <= 0){
            throw new IllegalArgumentException("Flight ID is not correct");
        }

        Flight flight = flightDao.findById(flightId);

        if(flight == null){
            throw new NoSuchElementException(flightId + " is not found");
        }

        return flight;
    }

    public List<Flight> searchFlights(String origin, String destination, LocalDate date){
        if(origin == null || origin.trim().isEmpty()) {
            throw new IllegalArgumentException("Origin can't be empty");
        }
        if(destination == null || destination.trim().isEmpty()) {
            throw new IllegalArgumentException("Destination can't be empty");
        }
        if(origin.trim().equalsIgnoreCase(destination.trim())){
            throw new IllegalArgumentException("Origin can't be the same with destination");
        }
        if(date == null){
            throw new IllegalArgumentException("Date can't be empty");
        }
        if(date.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("Date isn't valid");
        }

        return flightDao.search(origin.trim(),destination.trim(),date);
    }
}
