package com.penaestrada.repository;

import com.penaestrada.model.Client;
import com.penaestrada.model.Estimate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EstimateRepository extends JpaRepository<Estimate, Long> {

    @Query("""
    select e from Estimate e
    left join e.vehicle v
    where v.client = :client
""")
    List<Estimate> findAllByClient(Client client);

}
