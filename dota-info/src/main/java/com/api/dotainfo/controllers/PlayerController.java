package com.api.dotainfo.controllers;

import com.api.dotainfo.dtos.PlayerRequestDTO;
import com.api.dotainfo.services.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/dota-player")
public final class PlayerController {

    final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping
    public ResponseEntity<Object> savePlayer(@RequestBody @Valid PlayerRequestDTO playerRequestDTO) {

        return ResponseEntity.status(HttpStatus.CREATED).body(playerService.save(playerRequestDTO));
    }

    @GetMapping
    public ResponseEntity<Object> findAllPlayers() {

        return ResponseEntity.status(HttpStatus.OK).body(playerService.findAll());
    }

    @GetMapping("/{tag}")
    public ResponseEntity<Object> findOnePlayer(@PathVariable(value = "tag") String tag) {

        String formattedTag = tag.replace("+", " ");

        return ResponseEntity.status(HttpStatus.OK).body(playerService.findByTag(formattedTag));
    }

    @DeleteMapping("/{tag}")
    public ResponseEntity<Object> deletePlayer(@PathVariable(value = "tag") String tag) {

        String formattedTag = tag.replace("+", " ");

        playerService.deleteByTag(formattedTag);

        return ResponseEntity.status(HttpStatus.OK).body("Player '" + tag + "' deleted successfully.");
    }

    @PutMapping("/{tag}")
    public ResponseEntity<Object> putUpdatePlayer(@PathVariable(value = "tag") String tag,
                                               @RequestBody @Valid PlayerRequestDTO playerRequestDTO) {

        String formattedTag = tag.replace("+", " ");

        return ResponseEntity.status(HttpStatus.OK).body(playerService.putUpdate(playerRequestDTO, formattedTag));
    }

    @PatchMapping("/{tag}")
    public ResponseEntity<Object> patchUpdatePlayer(@PathVariable(value = "tag") String tag,
                                                    @RequestBody PlayerRequestDTO playerRequestDTO) {

        String formattedTag = tag.replace("+", " ");

        return ResponseEntity.status(HttpStatus.OK).body(playerService.patchUpdate(playerRequestDTO, formattedTag));
    }
}
