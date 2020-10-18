package br.com.api.api.repository;

import org.springframework.data.domain.Page;  
import org.springframework.data.domain.Pageable;  
import org.springframework.data.jpa.repository.JpaRepository;  
import org.springframework.data.jpa.repository.Modifying;  
import org.springframework.data.jpa.repository.Query;

import br.com.api.api.entity.Stop;

public interface StopRepository extends JpaRepository<Stop, Long> { }