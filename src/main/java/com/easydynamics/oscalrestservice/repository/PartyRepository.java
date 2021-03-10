package com.easydynamics.oscalrestservice.repository;


import com.easydynamics.oscalrestservice.model.OscalParty;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyRepository extends JpaRepository<OscalParty, String> {

  List<OscalParty> findByType(String type);

  OscalParty findByName(String name);

}