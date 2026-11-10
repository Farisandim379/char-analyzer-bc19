package com.bootcamp19.helloworld.util;

import com.bootcamp19.helloworld.entity.CharAnalysis;
import com.bootcamp19.helloworld.repository.CharAnalysisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final CharAnalysisRepository repository;

    @Override
    public void run(String... args) throws Exception {
        // Cek agar seeder hanya berjalan saat database masih kosong
        if (repository.count() == 0) {
            log.info("Database masih kosong. Memulai proses seeding data awal...");

            List<String> kalimatAwal = List.of(
                    "gajah mati meninggalkan gading",
                    "harimau mati meninggalkan belang",
                    "rajin pangkal pandai",
                    "kebersihan sebagian dari iman",
                    "sedikit demi sedikit lama lama menjadi bukit"
            );

            for (String kalimat : kalimatAwal) {
                CharCounter counter = new CharCounter(kalimat);

                CharAnalysis data = new CharAnalysis();
                data.setKalimat(kalimat);
                data.setTotalHuruf(counter.getTotalHuruf());
                data.setCreatedAt(LocalDateTime.now());

                repository.save(data);
            }

            log.info("Seeding data awal berhasil! Total {} data tersimpan di database.", repository.count());
        } else {
            log.info("Database sudah memiliki data (total: {}). Seeder dilewati.", repository.count());
        }
    }
}
