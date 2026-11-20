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
        flightDao.findAll();
        return null;
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

    }
}
