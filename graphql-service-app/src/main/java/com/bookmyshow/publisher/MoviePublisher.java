package com.bookmyshow.publisher;

import com.bookmyshow.mappers.MovieMapper;
import com.bookmyshow.models.MovieDTO;
import com.bookmyshow.repositories.MovieRepository;
import org.mapstruct.factory.Mappers;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MoviePublisher {

    private final Flux<List<MovieDTO>> publisher;
    private final MovieMapper mapper = Mappers.getMapper(MovieMapper.class);
    private final MovieRepository movieRepository;
    private FluxSink<List<MovieDTO>> emitter;

    public MoviePublisher(MovieRepository movieRepository) {
        Flux<List<MovieDTO>> createdPublisher = Flux.create(emitter -> this.emitter = emitter, FluxSink.OverflowStrategy.BUFFER);
        ConnectableFlux<List<MovieDTO>> connectableFlux = createdPublisher.share().publish();
        connectableFlux.connect();
        publisher = Flux.from(connectableFlux);
        this.movieRepository = movieRepository;
    }

    public void publish() {
        List<MovieDTO> movieDTOS = mapper.movieToMovieDTOList(movieRepository.findAll());
        emitter.next(movieDTOS);
    }

    public Publisher<List<MovieDTO>> getMoviesPublisherByGenre(String genre) {
        Thread t = new Thread(this::publish);
        t.start();
        return this.publisher
                .map(movieDTOList -> movieDTOList.stream().filter(movieDTO -> genre.equals(movieDTO.getGenre())).collect(Collectors.toList()));
    }

    public Publisher<List<MovieDTO>> getMoviesPublisher() {
        Thread t = new Thread(this::publish);
        t.start();
        return this.publisher;
    }

}