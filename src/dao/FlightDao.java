package dao;

import model.Flight;

import java.time.LocalDate;
import java.util.*;

public interface FlightDao {
    List<Flight> findAll();
    Flight findById(int id);
    List<Flight> search(String origin, String destination, LocalDate Date);
}
