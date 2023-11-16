package com.example.ticketBooking.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingTickets {
    private int id;
    private String name;
    private String director;
    private int totalPrice;
    private int bookedSeats;
}
