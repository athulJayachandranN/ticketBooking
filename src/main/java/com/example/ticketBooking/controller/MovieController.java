package com.example.ticketBooking.controller;


import com.example.ticketBooking.entity.AuthRequest;
import com.example.ticketBooking.entity.BookingTickets;
import com.example.ticketBooking.entity.Movie;
import com.example.ticketBooking.entity.UserInfo;
import com.example.ticketBooking.service.JwtService;
import com.example.ticketBooking.service.MovieService;
import com.example.ticketBooking.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping
    public List<Movie> getMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public Movie getById(@PathVariable("id") Integer id) {
        return movieService.getMoviesById(id);
    }

    @PostMapping("/admin/addMovie")
    @PreAuthorize(("hasAuthority('ROLE_ADMIN')"))
    public String addMovie(@RequestBody Movie movie) {
        movieService.addMovies(movie);
        return "movie added successfully...";
    }

    @DeleteMapping("/admin/{id}")
    @PreAuthorize(("hasAuthority('ROLE_ADMIN')"))
    public String deleteMovie(@PathVariable("id") int id) {
        movieService.deleteMovie(id);
        return "successfully deleted....";
    }

    @PutMapping("/admin/{id}")
    @PreAuthorize(("hasAuthority('ROLE_ADMIN')"))
    public String updateMovie(@PathVariable("id") int id, @RequestBody Movie movie) {
        movieService.updateMovie(id, movie);
        return "updation successful.....";
    }

    @PostMapping("/booking/{id}/{quantity}/{totalPrice}")
    public String bookTicket(@PathVariable("id") int id, @PathVariable("quantity")
    int quantity, @PathVariable("totalPrice") int totalPrice) {
        movieService.bookTicket(id, quantity, totalPrice);
        return "booking successful....";
    }

    @GetMapping("/history")
    public List<BookingTickets> getHistory() {
        return movieService.getBookingHistory();
    }
}
