package com.api.dotaplayerstats.services;

import com.api.dotaplayerstats.models.DotaPlayerModel;
import com.api.dotaplayerstats.repositories.DotaPlayerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class DotaPlayerService {

    final DotaPlayerRepository dotaPlayerRepository;

    public DotaPlayerService(DotaPlayerRepository dotaPlayerRepository) {
        this.dotaPlayerRepository = dotaPlayerRepository;
    }

    @Transactional
    public DotaPlayerModel save(DotaPlayerModel dotaPlayerModel) {
        return dotaPlayerRepository.save(dotaPlayerModel);
    }

    public List<DotaPlayerModel> findAll() {
        return dotaPlayerRepository.findAll();
    }

    public boolean existsByDotaPlayerAlias(String dotaPlayerAlias) {
        return dotaPlayerRepository.existsByDotaPlayerAlias(dotaPlayerAlias);
    }


    public Optional<DotaPlayerModel> findByDotaPlayerAlias(String dotaPlayerAlias) {
        return dotaPlayerRepository.findByDotaPlayerAlias(dotaPlayerAlias);
    }

    public void delete(DotaPlayerModel dotaPlayerModel) {
        dotaPlayerRepository.delete(dotaPlayerModel);
    }
}
