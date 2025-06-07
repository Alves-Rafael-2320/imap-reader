package com.email_scanner.repository;

import com.email_scanner.entity.TrackingCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrackingCodeRepository extends JpaRepository<TrackingCode, Long> {

    Optional<TrackingCode> findByCode(String code);

    boolean existsByCode(String code);

    List<TrackingCode> findByReceivedFalse();
}
