package dao;

import model.Booking;
import model.BookingStatus;
import model.TicketClass;
import util.DBConnection;

import java.sql.*;
import java.util.*;

public class BookingDaoImpl implements BookingDao{
    @Override
    public List<Booking> findAll() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings "
                   + "ORDER BY booking_time DESC";

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()){
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public Booking findById(int bookingId) {

        String sql = "SELECT * FROM bookings WHERE booking_id = ? ";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1,bookingId);

            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next()){
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Booking> findByPassengerId(int passengerId) {

        List<Booking> list = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE passenger_id = ? "
                   + "ORDER BY booking_time DESC";

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, passengerId);

            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    list.add(mapRow(rs));
                }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<String> findTakenSeats(int flightId) {

        List<String> seats = new ArrayList<>();
        String sql = "SELECT seat_number FROM bookings "
                   + "WHERE flight_id = ? AND status = 'CONFIRMED'";

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, flightId);

            try(ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    seats.add(rs.getString("seat_number"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seats;
    }

    private Booking mapRow(ResultSet rs) throws SQLException{
        Booking b = new Booking();
        b.setBookingId(rs.getInt("booking_id"));
        b.setFlightId(rs.getInt("flight_id"));
        b.setPassengerId(rs.getInt("passenger_id"));
        b.setSeatNumber(rs.getString("seat_number"));
        b.setTicketClass(TicketClass.valueOf(rs.getString("ticket_class")));
        b.setPrice(rs.getDouble("price"));
        b.setBookingTime(rs.getTimestamp("booking_time").toLocalDateTime());
        b.setStatus(BookingStatus.valueOf(rs.getString("status")));
        return b;
    }
}
