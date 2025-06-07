package com.email_scanner.controller;

import com.email_scanner.entity.TrackingCode;
import com.email_scanner.service.TrackingCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tracking-codes")
public class TrackingCodeController {

    private final TrackingCodeService trackingCodeService;

    @Autowired
    public TrackingCodeController(TrackingCodeService trackingCodeService){
        this.trackingCodeService = trackingCodeService;
    }

    @GetMapping("pending")
    public List<TrackingCode> getPendingTrackingCodes(){
        return trackingCodeService.findUnreceivedTrackingCoes();
    }

    @PutMapping("/mark-as-received/{code}")
    public ResponseEntity<String> markAsReceivedByCode(@PathVariable String code){
        try {
            trackingCodeService.markAsReceivedByCode(code);
            return ResponseEntity.ok("Código " + code + " marcado como recebido com sucesso.");
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<TrackingCode>> getAllTrackingCodes() {
        List<TrackingCode> codes = trackingCodeService.getAllTrackingCodes();
        return ResponseEntity.ok(codes);
    }

}
