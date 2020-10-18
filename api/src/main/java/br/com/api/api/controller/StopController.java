package br.com.api.api.controller;

import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.data.domain.Page;  
import org.springframework.data.domain.Pageable;  
import org.springframework.http.ResponseEntity;  
import org.springframework.web.bind.annotation.*;  
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;  
import java.net.URI;  
import java.util.Optional;

import br.com.api.api.entity.Route;
import br.com.api.api.entity.Stop;
import br.com.api.api.repository.RouteRepository;
import br.com.api.api.repository.StopRepository;

@RestController
@RequestMapping("/api/v1/stops")
public class StopController {
    private final RouteRepository routeRepository;
    private final StopRepository stopRepository;

    @Autowired
    public StopController(RouteRepository routeRepository, StopRepository stopRepository) {
        this.routeRepository = routeRepository;
        this.stopRepository = stopRepository;
    }

    @PostMapping
    public ResponseEntity<Stop> create(@RequestBody @Valid Stop stop) {
        Optional<Route> optionalRoute = routeRepository.findById(stop.getRoute().getRouteKey());
        if (!optionalRoute.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        stop.setRoute(optionalRoute.get());

        Stop savedStop = stopRepository.save(stop);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(savedStop.getId()).toUri();

        return ResponseEntity.created(location).body(savedStop);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Stop> update(@RequestBody @Valid Stop stop, @PathVariable Long id) {
        Optional<Route> optionalRoute = routeRepository.findById(stop.getRoute().getId());
        if (!optionalRoute.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        Optional<Stop> optionalStop = stopRepository.findById(id);
        if (!optionalStop.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        stop.setRoute(optionalRoute.get());
        stop.setId(optionalStop.get().getId());
        stopRepository.save(stop);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Stop> delete(@PathVariable Long id) {
        Optional<Stop> optionalStop = stopRepository.findById(id);
        if (!optionalStop.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        stopRepository.delete(optionalStop.get());
        Route r =optionalStop.get().getRoute();
        if(r.getStops().size()<1){
            routeRepository.delete(r);    
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<Stop>> getAll(Pageable pageable) {
        return ResponseEntity.ok(stopRepository.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stop> getById(@PathVariable Long id) {
        Optional<Stop> optionalStop = stopRepository.findById(id);
        if (!optionalStop.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        return ResponseEntity.ok(optionalStop.get());
    }
}