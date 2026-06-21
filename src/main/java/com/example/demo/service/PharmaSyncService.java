package com.example.demo.service;

import com.example.demo.model.Medicine;
import com.example.demo.repository.MedicineRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class PharmaSyncService {

    private final MedicineRepository medicineRepository;
    private final RestClient restClient;

    public PharmaSyncService(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
        // Pointing to a public mock API testing endpoint wrapper
        this.restClient = RestClient.builder()
                .baseUrl("https://jsonplaceholder.typicode.com") 
                .build();
    }

    public void fetchAndSaveMockData() {
        // Simulating an external REST GET API call retrieval
        String response = restClient.get()
                .uri("/posts/1") 
                .retrieve()
                .body(String.class);

        System.out.println("External API Raw Response: " + response);

        // Seeding a dummy record manually into MySQL to verify connection
        Medicine medicine = new Medicine();
        medicine.setBatchId("B2026-X9");
        medicine.setName("Paracetamol Parallels");
        medicine.setCount(142);

        medicineRepository.save(medicine);
        System.out.println("Data record successfully committed to MySQL Database!");
    }
}