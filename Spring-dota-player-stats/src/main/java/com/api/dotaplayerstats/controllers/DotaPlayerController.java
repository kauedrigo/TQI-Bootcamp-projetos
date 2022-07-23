package com.api.dotaplayerstats.controllers;

import com.api.dotaplayerstats.dtos.DotaPlayerDTO;
import com.api.dotaplayerstats.models.DotaPlayerModel;
import com.api.dotaplayerstats.services.DotaPlayerService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/dota-player")
public class DotaPlayerController {

    final DotaPlayerService dotaPlayerService;

    public DotaPlayerController(DotaPlayerService dotaPlayerService) {
        this.dotaPlayerService = dotaPlayerService;
    }

    @PostMapping
    public ResponseEntity<Object> saveDotaPlayer(@RequestBody @Valid DotaPlayerDTO dotaPlayerDTO) {
        if (dotaPlayerService.existsByDotaPlayerAlias(dotaPlayerDTO.getDotaPlayerAlias())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Dota Player Alias already registered.");
        }

        var dotaPlayerModel = new DotaPlayerModel();
        BeanUtils.copyProperties(dotaPlayerDTO, dotaPlayerModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(dotaPlayerService.save(dotaPlayerModel));
    }

    @GetMapping
    public ResponseEntity<List<DotaPlayerModel>> getAllDotaPlayers() {
        return ResponseEntity.status(HttpStatus.OK).body(dotaPlayerService.findAll());
    }

    @GetMapping("/{dotaPlayerAlias}")
    public ResponseEntity<Object> getOneDotaPlayer(@PathVariable(value = "dotaPlayerAlias") String dotaPlayerAlias) {
        Optional<DotaPlayerModel> dotaPlayerModelOptional = dotaPlayerService.findByDotaPlayerAlias(dotaPlayerAlias);

        if (dotaPlayerModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dota Player Alias not found.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(dotaPlayerModelOptional.get());
    }

    @PutMapping("/{dotaPlayerAlias}")
    public ResponseEntity<Object> updateDotaPlayer(@PathVariable(value = "dotaPlayerAlias") String dotaPlayerAlias,
                                                   @RequestBody @Valid DotaPlayerDTO dotaPlayerDTO) {
        Optional<DotaPlayerModel> dotaPlayerModelOptional = dotaPlayerService.findByDotaPlayerAlias(dotaPlayerAlias);

        if (dotaPlayerModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dota Player Alias not found.");
        }

        var dotaPlayerModel = new DotaPlayerModel();
        BeanUtils.copyProperties(dotaPlayerDTO, dotaPlayerModel);
        dotaPlayerModel.setId(dotaPlayerModelOptional.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(dotaPlayerService.save(dotaPlayerModel));
    }

    @DeleteMapping("/{dotaPlayerAlias}")
    public ResponseEntity<Object> deleteDotaPlayer(@PathVariable(value = "dotaPlayerAlias") String dotaPlayerAlias) {
        Optional<DotaPlayerModel> dotaPlayerModelOptional = dotaPlayerService.findByDotaPlayerAlias(dotaPlayerAlias);

        if (dotaPlayerModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Dota Player Alias not found.");
        }

        dotaPlayerService.delete(dotaPlayerModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Dota Player '" + dotaPlayerAlias + "' deleted successfully.");
    }
}
