package com.email_scanner.service;

import com.email_scanner.repository.TrackingCodeRepository;
import com.email_scanner.entity.TrackingCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TrackingCodeService {

    private final TrackingCodeRepository repository;

    @Autowired
    public TrackingCodeService(TrackingCodeRepository repository){
        this.repository = repository;
    }

    public void save(List<String> codes, String originEmail){
        for (String code : codes){
            if (!repository.existsByCode(code)){
                TrackingCode trackingCode = new TrackingCode(
                        code,
                        originEmail,
                        LocalDateTime.now(),
                        false
                );
                repository.save(trackingCode);
            }
        }
    }

    public List<TrackingCode> findUnreceivedTrackingCoes() {
        return repository.findByReceivedFalse();
    }

    public void markAsReceivedByCode(String code){
        TrackingCode trackingCode = repository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Código de rastreio não encontrado: " + code));

        trackingCode.setReceived(true);
        repository.save(trackingCode);
    }

    public List<TrackingCode> getAllTrackingCodes() {
        return repository.findAll();
    }
}
