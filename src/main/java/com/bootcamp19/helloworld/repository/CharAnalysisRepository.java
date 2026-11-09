package com.bootcamp19.helloworld.repository;

import com.bootcamp19.helloworld.entity.CharAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharAnalysisRepository extends JpaRepository<CharAnalysis, Long> {
    List<CharAnalysis> findByKalimatContainingIgnoreCase(String kata);
}
