package com.example.ticketBooking.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String director;
    private int price;
    private  int availableSeats;


    public Movie() {
    }

    public Movie(int id, String name, String director, int price, int availableSeats) {
        this.id = id;
        this.name = name;
        this.director = director;
        this.price = price;
        this.availableSeats = availableSeats;
    }
}
