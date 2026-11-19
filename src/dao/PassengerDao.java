package dao;

import model.Passenger;
import java.util.*;

public interface PassengerDao {
    List<Passenger> findAll();
    Passenger findById(int id);
    Passenger findByIdNumber(String idNumber);
    boolean insert(Passenger p);
}
