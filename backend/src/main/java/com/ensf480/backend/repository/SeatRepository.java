package com.ensf480.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ca.ucalgary.ensf480.flightapp.model.Seat;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

}