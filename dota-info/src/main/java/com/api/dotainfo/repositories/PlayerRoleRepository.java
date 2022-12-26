package com.api.dotainfo.repositories;

import com.api.dotainfo.models.PlayerRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRoleRepository extends JpaRepository<PlayerRole, Long> {
    PlayerRole findByPlayerRoleNumber(Integer playerRoleNumber);
}
