package com.example.demo.controller;

import com.example.demo.service.PharmaSyncService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SyncController {

    private final PharmaSyncService pharmaSyncService;

    public SyncController(PharmaSyncService pharmaSyncService) {
        this.pharmaSyncService = pharmaSyncService;
    }

    @GetMapping("/api/sync")
    public String triggerSync() {
        pharmaSyncService.fetchAndSaveMockData();
        return "Sync executed completely! Check application console and MySQL table rows.";
    }
}