package com.easydynamics.oscalrestservice.repository;

import com.easydynamics.oscalrestservice.model.OscalParty;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartyRepository extends JpaRepository<OscalParty, String> {

  List<OscalParty> findByName(String name);

  List<OscalParty> findByType(String type);

  Optional<OscalParty> findByUuid(String uuid);



}