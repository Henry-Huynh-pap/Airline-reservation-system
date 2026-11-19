package dao;

import model.Flight;
import util.DBConnection;

import java.sql.*;
import java.util.*;
import java.time.*;

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

    @Override
    public List<Flight> search(String origin,String destination,LocalDate date){
        List<Flight> list = new ArrayList<>();
        String sql = "SELECT * FROM flights "
                   + "WHERE origin = ? AND destination = ? "
                   + "AND departure_time >= ? AND departure_time < ? "
                   + "AND available_seats > 0 "
                   + "ORDER BY departure_time";

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {

            Timestamp from = Timestamp.valueOf(date.atStartOfDay());
            Timestamp to = Timestamp.valueOf(date.plusDays(1).atStartOfDay());

            ps.setString(1,origin);
            ps.setString(2,destination);
            ps.setTimestamp(3,from);
            ps.setTimestamp(4,to);

            try(ResultSet rs = ps.executeQuery()) {
                while(rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    private Flight mapRow(ResultSet rs) throws SQLException{
        Flight f = new Flight();
        f.setFlightId(rs.getInt("flight_id"));
        f.setFlightCode(rs.getString("flight_code"));
        f.setOrigin(rs.getString("origin"));
        f.setDestination(rs.getString("destination"));
        f.setDepartureTime(rs.getTimestamp("departure_time").toLocalDateTime());
        f.setArrivalTime(rs.getTimestamp("arrival_time").toLocalDateTime());
        f.setTotalSeats(rs.getInt("total_seats"));
        f.setAvailableSeats(rs.getInt("available_seats"));
        f.setBasePrice(rs.getDouble("base_price"));
        return f;
    }
}
