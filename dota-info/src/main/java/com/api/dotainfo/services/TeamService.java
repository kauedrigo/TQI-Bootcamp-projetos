package com.api.dotainfo.services;

import com.api.dotainfo.dtos.TeamRequestDTO;
import com.api.dotainfo.dtos.TeamResponseDTO;
import com.api.dotainfo.exceptions.TeamNameNotAvailableException;
import com.api.dotainfo.exceptions.TeamNotFoundException;
import com.api.dotainfo.exceptions.TeamCompositionException;
import com.api.dotainfo.mappers.TeamMapper;
import com.api.dotainfo.models.PlayerModel;
import com.api.dotainfo.models.PlayerRole;
import com.api.dotainfo.models.TeamModel;
import com.api.dotainfo.repositories.PlayerRepository;
import com.api.dotainfo.repositories.TeamRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class TeamService {

    final TeamRepository teamRepository;

    final TeamMapper teamMapper;

    final PlayerRepository playerRepository;

    public TeamService(TeamRepository teamRepository, TeamMapper teamMapper, PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
        this.playerRepository = playerRepository;
    }

    @Transactional
    public TeamResponseDTO save(TeamRequestDTO teamRequestDTO) {

        if (teamRepository.existsByTeamName(teamRequestDTO.getTeamName())) {
            throw new TeamNameNotAvailableException("Team name is already in use.");
        }

        TeamModel teamModel = teamMapper.requestToNewModel(teamRequestDTO);

        if (teamModel.getPlayerModelList() != null) {
            List<PlayerRole> playerRoleList = new ArrayList<>();

            for (PlayerModel playerModel : teamModel.getPlayerModelList()) {
                playerRoleList.add(playerModel.getPlayerRole());
            }

            Set<PlayerRole> playerRoleSet = new HashSet<>(playerRoleList);

            if (playerRoleList.size() != playerRoleSet.size()) {
                throw new TeamCompositionException
                        ("Invalid team role composition. A team can only have one player of each role.");
            }
        }

        teamRepository.save(teamModel);

        if (teamModel.getPlayerModelList() != null) {
            for (PlayerModel playerModel : teamModel.getPlayerModelList()) {
                playerModel.setTeamModel(teamModel);
                playerRepository.save(playerModel);
            }
        }

        return teamMapper.modelToResponse(teamModel);
    }

    public List<TeamResponseDTO> findAll() {

        List<TeamResponseDTO> teamResponseDTOList = new ArrayList<>();

        for (TeamModel teamModel : teamRepository.findAll()) {
            teamResponseDTOList.add(teamMapper.modelToResponse(teamModel));
        }

        return teamResponseDTOList;
    }

    public TeamResponseDTO findByTeamName(String teamName) {

        return teamMapper.modelToResponse(Optional.ofNullable(teamRepository.findByTeamName(teamName))
                .orElseThrow(() -> new TeamNotFoundException("Team not found.")));
    }

    @Transactional
    public void deleteByTeamName(String teamName) {

        TeamModel teamModel = Optional.ofNullable(teamRepository.findByTeamName(teamName))
                .orElseThrow(() -> new TeamNotFoundException("Team not found."));

        if (teamModel.getPlayerModelList() != null) {
            for (PlayerModel playerModel : teamModel.getPlayerModelList()) {
                playerModel.setTeamModel(null);
                playerRepository.save(playerModel);
            }
        }

        teamRepository.deleteByTeamName(teamName);
    }

    @Transactional
    public TeamResponseDTO putUpdate(TeamRequestDTO teamRequestDTO, String teamName) {

        TeamModel teamModel = Optional.ofNullable(teamRepository.findByTeamName(teamName))
                .orElseThrow(() -> new TeamNotFoundException("Team not found."));

        TeamModel teamModelPut = teamMapper.requestToNewModel(teamRequestDTO);
        teamModelPut.setId(teamModel.getId());

        if (teamModelPut.getPlayerModelList() != null) {

            List<PlayerRole> playerRoleList = new ArrayList<>();

            for (PlayerModel player : teamModelPut.getPlayerModelList()) {
                playerRoleList.add(player.getPlayerRole());
            }

            Set<PlayerRole> playerRoleSet = new HashSet<>(playerRoleList);

            if (playerRoleList.size() != playerRoleSet.size()) {
                throw new TeamCompositionException
                        ("Invalid team role composition. A team can only have one player of each role.");
            }

            for (PlayerModel player : teamModel.getPlayerModelList()) {
                player.setTeamModel(null);
                playerRepository.save(player);
            }
        }

        teamMapper.putUpdate(teamModelPut, teamModel);
        teamRepository.save(teamModel);

        if (teamModel.getPlayerModelList() != null) {
            for (PlayerModel player : teamModel.getPlayerModelList()) {
                player.setTeamModel(teamModel);
                playerRepository.save(player);
            }
        }

        return teamMapper.modelToResponse(teamModel);
    }

    @Transactional
    public TeamResponseDTO patchUpdate(TeamRequestDTO teamRequestDTO, String teamName) {

        TeamModel teamModel = Optional.ofNullable(teamRepository.findByTeamName(teamName))
                .orElseThrow(() -> new TeamNotFoundException("Team not found."));

        TeamModel teamModelPatch = teamMapper.requestToNewModel(teamRequestDTO);

        List<PlayerModel> playerModelList = new ArrayList<>();

        if (teamModelPatch.getPlayerModelList() != null) {
            playerModelList.addAll(teamModelPatch.getPlayerModelList());
        } else if (teamModel.getPlayerModelList() != null) {
            playerModelList.addAll(teamModel.getPlayerModelList());
        } else {
            playerModelList = null;
        }

        if (playerModelList != null) {

            List<PlayerRole> playerRoleList = new ArrayList<>();

            for (PlayerModel player : playerModelList) {
                playerRoleList.add(player.getPlayerRole());
            }

            Set<PlayerRole> playerRoleSet = new HashSet<>(playerRoleList);

            if (playerRoleList.size() != playerRoleSet.size()) {
                throw new TeamCompositionException
                        ("Invalid team role composition. A team can only have one player of each role.");
            }

            if (teamModel.getPlayerModelList() != null) {
                for (PlayerModel playerModel : teamModel.getPlayerModelList()) {
                    playerModel.setTeamModel(null);
                    playerRepository.save(playerModel);
                }
            }
        }

        teamMapper.patchUpdate(teamModelPatch, teamModel);
        teamRepository.save(teamModel);

        if (playerModelList != null) {
            for (PlayerModel playerModel : playerModelList) {
                playerModel.setTeamModel(teamModel);
                playerRepository.save(playerModel);
            }
        }

        return teamMapper.modelToResponse(teamModel);
    }
}
