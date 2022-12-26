package com.api.dotainfo.repositories;

import com.api.dotainfo.models.TeamModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<TeamModel, Long> {

    boolean existsByTeamName(String teamName);

    TeamModel findByTeamName(String teamName);

    long deleteByTeamName(String teamName);
}
