package com.bootcamp19.helloworld.repository;

import com.bootcamp19.helloworld.entity.CharAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CharAnalysisRepository extends JpaRepository<CharAnalysis, Long> {
    List<CharAnalysis> findByKalimatContainingIgnoreCase(String kata);

    // Query JPQL (menggunakan nama Entity Java dan field Java)
    @Query("SELECT c FROM CharAnalysis c WHERE c.totalHuruf >= :min")
    List<CharAnalysis> findByMinimalHuruf(@Param("min") int min);

    // Query Native (menggunakan nama tabel dan kolom SQL di database)
    @Query(value = "SELECT * FROM mst_char_analysis ORDER BY created_at DESC LIMIT 5", nativeQuery = true)
    List<CharAnalysis> findTop5Terbaru();
}
