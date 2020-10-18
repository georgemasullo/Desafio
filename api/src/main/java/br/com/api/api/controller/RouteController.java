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
@RequestMapping("/api/v1/routes")
public class RouteController {
    
    private final RouteRepository routeRepository;
    private final StopRepository stopRepository;

    @Autowired
    public RouteController(RouteRepository routeRepository, StopRepository stopRepository) {
        this.routeRepository = routeRepository;
        this.stopRepository = stopRepository;
    }

    @GetMapping
    public ResponseEntity<Page<Route>> getAll(Pageable pageable) {
        return ResponseEntity.ok(routeRepository.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Route> getById(@PathVariable Long id) {
        Optional<Route> optionalRoute = routeRepository.findById(id);
    if (!optionalRoute.isPresent()) {
        return ResponseEntity.unprocessableEntity().build();
    }

    return ResponseEntity.ok(optionalRoute.get());
    }

    @PostMapping
    public ResponseEntity<Route> create(@Valid @RequestBody Route route){
        
        if(route.getStops().size()<1 || !route.getStatus().toUpperCase().equals("pendente".toUpperCase())){
            return ResponseEntity.unprocessableEntity().build();    
        }
        Route savedRoute = routeRepository.save(route);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(savedRoute.getRouteKey()).toUri();

    return ResponseEntity.created(location).body(savedRoute);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Route> update(@PathVariable Long id, @Valid @RequestBody Route route) {
        Optional<Route> optionalRoute = routeRepository.findById(id);
    if (!optionalRoute.isPresent()) {
        return ResponseEntity.unprocessableEntity().build();
    }

    route.setId(optionalRoute.get().getRouteKey());
    routeRepository.save(route);

    return ResponseEntity.noContent().build();
    
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Route> delete(@PathVariable Long id) {
        Optional<Route> optionalRoute = routeRepository.findById(id);
        if (!optionalRoute.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        routeRepository.delete(optionalRoute.get());

        return ResponseEntity.noContent().build();
    }

}