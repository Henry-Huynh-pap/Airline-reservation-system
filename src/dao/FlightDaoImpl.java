package dao;

import model.Flight;
import util.DBConnection;

import java.sql.*;
import java.util.*;

public class FlightDaoImpl implements FlightDao{
    @Override
    public List<Flight> findAll() {

        List<Flight> list = new ArrayList<>();
        String sql = "SELECT * FROM flights";

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()){
                list.add(mapRow(rs));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Flight findById(int id) {

        String sql = "SELECT * FROM flights WHERE flight_id = ?";

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return mapRow(rs);
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    private Flight mapRow(ResultSet rs) throws SQLException{
        Flight f = new Flight();
        f.setFlightId(rs.getInt("flight_id"));
        f.setFlightCode(rs.getString("flight_code"));
        f.setOrigin(rs.getString("origin"));
        f.setDepartureTime(rs.getTimestamp("departure_time").toLocalDateTime());
        f.setArrivalTime(rs.getTimestamp("arrival_time").toLocalDateTime());
        f.setTotalSeats(rs.getInt("total_seats"));
        f.setAvailableSeats(rs.getInt("available_seats"));
        f.setBasePrice(rs.getDouble("base_price"));
        return f;
    }
}
