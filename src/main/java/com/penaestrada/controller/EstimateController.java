package com.penaestrada.controller;

import com.penaestrada.dto.CreateEstimateRequest;
import com.penaestrada.dto.EstimateResponse;
import com.penaestrada.infra.security.TokenService;
import com.penaestrada.model.Client;
import com.penaestrada.model.Estimate;
import com.penaestrada.model.Vehicle;
import com.penaestrada.model.Workshop;
import com.penaestrada.service.ClientService;
import com.penaestrada.service.EstimateService;
import com.penaestrada.service.VehicleService;
import com.penaestrada.service.WorkshopService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/estimate")
public class EstimateController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private WorkshopService workshopService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private EstimateService estimateService;

    @PostMapping
    @Transactional
    public ResponseEntity<Void> scheduleEstimate(@CookieValue(name = "pe_access_token") String token, @RequestBody @Valid CreateEstimateRequest data) {
        String login = tokenService.getSubject(token);
        Client client = clientService.getClientByLogin(login);
        Vehicle vehicle = vehicleService.getVehicleById(data.vehicleId(), client.getVehicles());
        Workshop workshop = workshopService.findWorkshopById(data.workshopId());

        Estimate estimate = estimateService.createEstimate(data.description(), data.scheduledAt(), vehicle, workshop);
        EstimateResponse response = estimateService.estimateToResponse(estimate, workshopService.workshopToResponse(workshop));
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<EstimateResponse>> getAllEstimates(@CookieValue(name = "pe_access_token") String token) {
        String login = tokenService.getSubject(token);
        Client client = clientService.getClientByLogin(login);
        List<EstimateResponse> response = estimateService.getAllEstimates(client);
        return ResponseEntity.ok().body(response);
    }
}
