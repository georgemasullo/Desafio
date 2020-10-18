package br.com.api.api.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.api.api.entity.Route;


public interface RouteRepository extends JpaRepository<Route, Long> { }