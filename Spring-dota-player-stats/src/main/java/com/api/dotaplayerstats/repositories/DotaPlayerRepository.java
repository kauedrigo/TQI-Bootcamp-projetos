package com.api.dotaplayerstats.repositories;

import com.api.dotaplayerstats.models.DotaPlayerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DotaPlayerRepository extends JpaRepository<DotaPlayerModel, Long> {

    boolean existsByDotaPlayerAlias(String dotaPlayerAlias);

    Optional<DotaPlayerModel> findByDotaPlayerAlias(String dotaPlayerAlias);
}
