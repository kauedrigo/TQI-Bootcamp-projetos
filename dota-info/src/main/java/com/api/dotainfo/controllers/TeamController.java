package com.api.dotainfo.controllers;

import com.api.dotainfo.dtos.TeamRequestDTO;
import com.api.dotainfo.services.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/dota-team")
public class TeamController {

    final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping
    public ResponseEntity<Object> postTeam(@RequestBody @Valid TeamRequestDTO teamRequestDTO) {

        return ResponseEntity.status(HttpStatus.CREATED).body(teamService.save(teamRequestDTO));
    }

    @GetMapping
    public ResponseEntity<Object> findAllTeams() {

        return ResponseEntity.status(HttpStatus.OK).body(teamService.findAll());
    }

    @GetMapping("/{teamName}")
    public ResponseEntity<Object> findOneTeam(@PathVariable(value = "teamName") String teamName) {

        String formattedTeamName = teamName.replace("+", " ");

        return ResponseEntity.status(HttpStatus.OK).body(teamService.findByTeamName(formattedTeamName));
    }

    @DeleteMapping("/{teamName}")
    public ResponseEntity<Object> deleteTeam(@PathVariable(value = "teamName") String teamName) {

        String formattedTeamName = teamName.replace("+", " ");

        teamService.deleteByTeamName(formattedTeamName);

        return ResponseEntity.status(HttpStatus.OK).body("Team '" + formattedTeamName + "' deleted successfully.");
    }

    @PutMapping("/{teamName}")
    public ResponseEntity<Object> putUpdateTeam(@PathVariable(value = "teamName") String teamName,
                                                @RequestBody @Valid TeamRequestDTO teamRequestDTO) {

        String formattedTeamName = teamName.replace("+", " ");

        return ResponseEntity.status(HttpStatus.OK).body(teamService.putUpdate(teamRequestDTO, formattedTeamName));
    }

    @PatchMapping("/{teamName}")
    public ResponseEntity<Object> patchUpdateTeam(@PathVariable(value = "teamName") String teamName,
                                                  @RequestBody TeamRequestDTO teamRequestDTO) {

        String formattedTeamName = teamName.replace("+", " ");

        return ResponseEntity.status(HttpStatus.OK).body(teamService.patchUpdate(teamRequestDTO, formattedTeamName));
    }
}
