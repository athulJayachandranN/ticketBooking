package com.example.ticketBooking.service;

import com.example.ticketBooking.entity.BookingTickets;
import com.example.ticketBooking.entity.Movie;
import com.example.ticketBooking.repository.MovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {
@Autowired
    private MovieRepo movieRepo;

    public List<Movie> getAllMovies() {
        return movieRepo.findAll();
    }

    public Movie getMoviesById(int id) {
        return movieRepo.findById(id).orElseThrow(()->
                new IllegalArgumentException("invalid movie Id :"+id));
    }

    public void addMovies(Movie movie) {
        movieRepo.save(movie);
    }

    public void deleteMovie(int id) {
        movieRepo.findById(id).orElseThrow(()->
                new IllegalArgumentException("moive not found ..."));
        movieRepo.deleteById(id);
    }

    public void updateMovie(int id, Movie movie) {
        Movie existingMoive=movieRepo.findById(id).orElseThrow(()->
                new IllegalArgumentException("moive not found ..."));
        existingMoive.setName(movie.getName());
        existingMoive.setDirector(movie.getDirector());
        existingMoive.setPrice(movie.getPrice());
        existingMoive.setAvailableSeats(movie.getAvailableSeats());
        movieRepo.save(existingMoive);
    }

    public void bookTicket(int id, int quantity, int totalPrice) {
        Movie movie=getMoviesById(id);
        int availableseats= movie.getAvailableSeats();
        if (quantity>availableseats){
            throw  new IllegalArgumentException("no seats available at this time...!!!");
        }
        int totalPriceCalculated=quantity*movie.getPrice();
        if (totalPriceCalculated!=totalPrice){
            throw  new IllegalArgumentException("invalid total price..!!");
        }
        availableseats-=quantity;
        movie.setAvailableSeats(availableseats);
        movieRepo.save(movie);
    }

    public List<BookingTickets> getBookingHistory() {
        List<Movie>movies=movieRepo.findAll();
        List<BookingTickets>bookingTickets=new ArrayList<>();

        for (Movie movie:movies){
            int bookedTickets=10-movie.getAvailableSeats();

            if (bookedTickets>0){
                int totalPrice=bookedTickets* movie.getPrice();
                BookingTickets booking=new BookingTickets();
                booking.setId(movie.getId());
                booking.setName(movie.getName());
                booking.setDirector(movie.getDirector());
                booking.setBookedSeats(bookedTickets);
                booking.setTotalPrice(totalPrice);
                bookingTickets.add(booking);
            }
        }
        return bookingTickets;
    }
}
