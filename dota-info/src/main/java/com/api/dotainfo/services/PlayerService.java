package com.api.dotainfo.services;

import com.api.dotainfo.dtos.PlayerRequestDTO;
import com.api.dotainfo.dtos.PlayerResponseDTO;
import com.api.dotainfo.exceptions.*;
import com.api.dotainfo.mappers.PlayerMapper;
import com.api.dotainfo.models.PlayerModel;
import com.api.dotainfo.models.PlayerRole;
import com.api.dotainfo.models.TeamModel;
import com.api.dotainfo.repositories.PlayerRepository;
import com.api.dotainfo.repositories.TeamRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class PlayerService {

    final PlayerRepository playerRepository;

    final TeamRepository teamRepository;

    final PlayerMapper playerMapper;

    public PlayerService(PlayerRepository playerRepository, TeamRepository teamRepository, PlayerMapper playerMapper) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
         this.playerMapper = playerMapper;
    }

    @Transactional
    public PlayerResponseDTO save(PlayerRequestDTO playerRequestDTO) throws PlayerTagNotAvailableException {

        if (playerRepository.existsByTag(playerRequestDTO.getTag())) {
            throw new PlayerTagNotAvailableException("Player tag is already in use.");
        }

        PlayerModel playerModel = playerMapper.requestToNewModel(playerRequestDTO);

        return playerMapper.modelToResponse(playerRepository.save(playerModel));
    }

    public List<PlayerResponseDTO> findAll() {

        List<PlayerResponseDTO> playerResponseDTOList = new ArrayList<>();

        for (PlayerModel playerModel : playerRepository.findAll()) {
            playerResponseDTOList.add(playerMapper.modelToResponse(playerModel));
        }

        return playerResponseDTOList;
    }

    public PlayerResponseDTO findByTag(String tag) throws PlayerNotFoundException {

        return playerMapper.modelToResponse(
                Optional.ofNullable(playerRepository.findByTag(tag))
                .orElseThrow(() -> new PlayerNotFoundException("Player not found.")));
    }

    @Transactional
    public void deleteByTag(String tag) {

        if (playerRepository.deleteByTag(tag) < 1) {
            throw new PlayerNotFoundException("Player not found");
        }
    }

    @Transactional
    public PlayerResponseDTO putUpdate(PlayerRequestDTO playerRequestDTO, String tag) {

        PlayerModel playerModel = Optional.ofNullable(playerRepository.findByTag(tag))
                .orElseThrow(() -> new PlayerNotFoundException("Player not found."));

        if (playerRepository.existsByTag(playerRequestDTO.getTag())) {
            throw new PlayerTagNotAvailableException("Player tag is already in use.");
        }

        PlayerModel playerModelPut = playerMapper.requestToNewModel(playerRequestDTO);

        // team composition verifications (if there is a team)
        if (playerModelPut.getTeamModel() != null) {

            TeamModel teamModel = playerModelPut.getTeamModel();

            // 1. if the team is there, remove it
            teamModel.getPlayerModelList().remove(playerModel);

            // 2. if +1 puts it above 5 players, throw exception
            if (teamModel.getPlayerModelList().size() == 5) {
                throw new TeamCompositionException
                        ("Invalid number of players on the team. A team can have a maximum of 5 players.");
            }

            // 3. check for role conflict
            List<PlayerRole> playerRoleList = new ArrayList<>();
            playerRoleList.add(playerModelPut.getPlayerRole());

            for (PlayerModel player : teamModel.getPlayerModelList()) {
                playerRoleList.add(player.getPlayerRole());
            }

            Set<PlayerRole> playerRoleSet = new HashSet<>(playerRoleList);

            if(playerRoleSet.size() != playerRoleList.size()) {
                throw new TeamCompositionException
                        ("Invalid team role composition. A team can only have one player of each role.");
            }
        }

        playerModelPut.setId(playerModel.getId());
        playerMapper.putUpdate(playerModelPut, playerModel);

        return playerMapper.modelToResponse(playerRepository.save(playerModel));
    }

    @Transactional
    public PlayerResponseDTO patchUpdate(PlayerRequestDTO playerRequestDTO, String tag) {

        PlayerModel playerModel = Optional.of(playerRepository.findByTag(tag))
                .orElseThrow(() -> new PlayerNotFoundException("Player not found"));

        if (playerRepository.existsByTag(playerRequestDTO.getTag())) {
            throw new PlayerTagNotAvailableException("Player tag is already in use.");
        }

        PlayerModel playerModelPatch = playerMapper.requestToNewModel(playerRequestDTO);

        // team composition verifications (if there is a team)
        TeamModel teamModel;

        if (playerModelPatch.getTeamModel() != null) {
            teamModel = playerModelPatch.getTeamModel();
        } else if (playerModel.getTeamModel() != null) {
            teamModel = playerModel.getTeamModel();
        } else {
            teamModel = null;
        }

        if (teamModel != null) {

            // 1. if the team is there, remove it
            teamModel.getPlayerModelList().remove(playerModel);

            // 2. if +1 puts it above 5 players, throw exception
            if (teamModel.getPlayerModelList().size() == 5) {
                throw new TeamCompositionException
                        ("Invalid number of players on the team. A team can have a maximum of 5 players.");
            }
        }

        playerMapper.patchUpdate(playerModelPatch, playerModel);

        if (teamModel != null) {

            // 3. check for role conflict
            List<PlayerRole> playerRoleList = new ArrayList<>();
            playerRoleList.add(playerModel.getPlayerRole());

            for (PlayerModel player : teamModel.getPlayerModelList()) {
                playerRoleList.add(player.getPlayerRole());
            }

            Set<PlayerRole> playerRoleSet = new HashSet<>(playerRoleList);

            if(playerRoleSet.size() != playerRoleList.size()) {
                throw new TeamCompositionException
                        ("Invalid team role composition. A team can only have one player of each role.");
            }
        }

        return playerMapper.modelToResponse(playerRepository.save(playerModel));
    }
}
