package com.api.dotainfo.repositories;

import com.api.dotainfo.dtos.PlayerResponseDTO;
import com.api.dotainfo.models.PlayerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerModel, Long> {

    boolean existsByTag(String tag);

    PlayerModel findByTag(String tag);

    long deleteByTag(String tag);
}
