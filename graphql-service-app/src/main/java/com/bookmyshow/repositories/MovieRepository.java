package com.bookmyshow.repositories;

import com.bookmyshow.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    List<Movie> findByGenre(String genre);

    Optional<Movie> findByName(String name);

    void deleteByName(String name);

}
