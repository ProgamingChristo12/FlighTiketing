package com.smk.dao;

import com.smk.Model.Schedule;
import com.smk.Model.dto.ScheduleDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.Optional;

public class ScheduleDao implements Dao<Schedule,Integer> {
    private final Optional<Connection> connection;

    public ScheduleDao() {
        connection = JdbcConnection.getConnection();
    }

    @Override
    public Optional<Schedule> get(int id) {
        return Optional.empty();
    }

    @Override
    public Collection<Schedule> getAll() {
        return null;
    }

    @Override
    public Optional<Integer> save(Schedule schedule) {
        return Optional.empty();
    }

    @Override
    public void update(Schedule schedule) {

    }

    @Override
    public void delete(Schedule schedule) {

    }

    @Override
    public Collection<Schedule> search(String keyword) {
        return null;
    }
    public Collection<ScheduleDTO> searchSchedule(long depatureID, long arrivalID, Date depatureDate){
        Collection<ScheduleDTO> result = new LinkedList<>();
        String sql = "SELECT schedule.*, location_depature.name depature, location_arrival.name arrival " +
                "from schedule inner join location location_depature " +
                        "on schedule.depature_id = location_depature.id " +
                        "inner join location location_arrival on schedule.arrival_id = location_arrival.id " +
                        "where depature_id = ? and arrival_id = ? and depature_date::timestamptz::  date = ?;";
        connection.ifPresent(conn -> {
            try{
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setLong(1, depatureID);
                ps.setLong(2, arrivalID);
                ps.setDate(3, new java.sql.Date(depatureDate.getTime()));
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    ScheduleDTO scheduleDTO = new ScheduleDTO();
                    scheduleDTO.setId(rs.getInt("id"));
                    scheduleDTO.setDepatureLocation(rs.getString("depature"));
                    scheduleDTO.setArrivalLocation(rs.getString("arrival"));
                    scheduleDTO.setDepatureDate(rs.getDate("depature_date"));
                    scheduleDTO.setFlightNumber(rs.getString("flight_number"));
                    result.add(scheduleDTO);
                }
            } catch (SQLException e){
                e.printStackTrace();
            }
        });
        return result;
    }
}
