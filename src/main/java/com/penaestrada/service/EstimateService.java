package com.penaestrada.service;

import com.penaestrada.dto.EstimateResponse;
import com.penaestrada.dto.ServiceResponse;
import com.penaestrada.dto.VehicleResponse;
import com.penaestrada.dto.WorkshopDetailsResponse;
import com.penaestrada.model.Client;
import com.penaestrada.model.Estimate;
import com.penaestrada.model.Vehicle;
import com.penaestrada.model.Workshop;
import com.penaestrada.repository.EstimateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class EstimateService {

    @Autowired
    private EstimateRepository repository;

    @Autowired
    private WorkshopService workshopService;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public Estimate createEstimate(String description, String scheduledAt, Vehicle vehicle, Workshop workshop) {
        Estimate estimate = new Estimate();
        estimate.setVehicle(vehicle);
        estimate.setWorkshop(workshop);
        estimate.setInitialDescription(description);
        estimate.setScheduledAt(LocalDateTime.parse(scheduledAt));
        estimate.setCreatedAt(LocalDateTime.now());
        estimate.createServicesList();
        repository.save(estimate);
        return estimate;
    }

    public EstimateResponse estimateToResponse(Estimate estimate, WorkshopDetailsResponse workshop) {
        Vehicle vehicle = estimate.getVehicle();
        String formmatedFinishedAt;
        List<ServiceResponse> services = new ArrayList<>();
        if (!estimate.getServices().isEmpty()) {
            services = estimate.getServices().stream().map(s -> new ServiceResponse(s.getId(), s.getDescription(), s.getPrice(), s.getCreatedAt().format(formatter))).toList();
        }

        try {
            formmatedFinishedAt = estimate.getFinishedAt().format(formatter);
        } catch (Exception e) {
            formmatedFinishedAt = null;
        }
        return new EstimateResponse(estimate.getId(), vehicle.getClient().getName(),
                new VehicleResponse(vehicle.getId(), vehicle.getBrand(), vehicle.getModel(), String.valueOf(vehicle.getYear()), vehicle.getLicensePlate()),
                workshop, estimate.getInitialDescription(), estimate.getScheduledAt().format(formatter),
                estimate.getCreatedAt().format(formatter), estimate.getValue(), formmatedFinishedAt,
                services
                );
    }

    public List<EstimateResponse> getAllEstimates(Client client) {
        List<Estimate> estimates = repository.findAllByClient(client);

        return estimates.stream().map(e -> estimateToResponse(e, workshopService.workshopToResponse(e.getWorkshop()))).toList();
    }
}
