package com.cyj.hb.controller;

import com.cyj.hb.exception.InvalidReservationDateException;
import com.cyj.hb.model.Reservation;
import com.cyj.hb.model.User;
import com.cyj.hb.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
public class ReservationController {

    private ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public List<Reservation> listReservations(Principal principal) {
        return reservationService.listByGuest(principal.getName());
    }

    @PostMapping("/reservations")
    public void addReservation(@RequestBody Reservation reservation, Principal principal) {
        LocalDate checkinDate = reservation.getCheckinDate();
        LocalDate checkoutDate = reservation.getCheckoutDate();
        if (checkinDate.equals(checkoutDate) || checkinDate.isAfter(checkoutDate) || checkinDate.isBefore(LocalDate.now())) {
            throw new InvalidReservationDateException("Invalid date for reservation");
        }
        reservation.setGuest(new User.Builder().setUsername(principal.getName()).build());
        reservationService.add(reservation);
    }

    @DeleteMapping("/reservations/{reservationId}")
    public void deleteReservation(@PathVariable Long reservationId, Principal principal) {
        reservationService.delete(reservationId, principal.getName());
    }

}
