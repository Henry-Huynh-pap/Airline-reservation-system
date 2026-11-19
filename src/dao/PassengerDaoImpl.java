package dao;

import model.Passenger;
import util.DBConnection;

import java.sql.*;
import java.util.*;

public class PassengerDaoImpl implements PassengerDao {
    @Override
    public List<Passenger> findAll() {

        List<Passenger> list = new ArrayList<>();
        String sql = "SELECT * FROM passengers";

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
    public Passenger findById(int id) {

        String sql = "SELECT * FROM passengers WHERE passenger_id = ?";

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1,id);

            try(ResultSet rs = ps.executeQuery()){
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
    public Passenger findByIdNumber(String idNumber) {

        String sql = "SELECT * FROM passengers WHERE id_number = ?";

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1,idNumber);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return mapRow(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean insert(Passenger p) {

        String sql = "INSERT INTO passengers (full_name, id_number, phone, email) "
                   + "VALUES (?, ?, ?, ?)";

        try(Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1,p.getFullName());
            ps.setString(2,p.getIdNumber());
            ps.setString(3, p.getPhone());
            ps.setString(4,p.getEmail());

            return ps.executeUpdate() > 0;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Passenger mapRow(ResultSet rs) throws SQLException{
        Passenger p = new Passenger();
        p.setPassengerID(rs.getInt("passenger_id"));
        p.setFullName(rs.getString("full_name"));
        p.setIdNumber(rs.getString("id_number"));
        p.setPhone(rs.getString("phone"));
        p.setEmail(rs.getString("email"));
        return p;
    }
}
