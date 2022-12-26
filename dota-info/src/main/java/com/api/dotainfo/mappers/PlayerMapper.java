package com.api.dotainfo.mappers;

import com.api.dotainfo.dtos.PlayerRequestDTO;
import com.api.dotainfo.dtos.PlayerResponseDTO;
import com.api.dotainfo.exceptions.InvalidPlayerRoleNumberException;
import com.api.dotainfo.exceptions.TeamNotFoundException;
import com.api.dotainfo.models.PlayerModel;
import com.api.dotainfo.models.PlayerRole;
import com.api.dotainfo.models.TeamModel;
import com.api.dotainfo.repositories.PlayerRoleRepository;
import com.api.dotainfo.repositories.TeamRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class PlayerMapper {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRoleRepository playerRoleRepository;

    @Mapping(target = "teamModel", source = "teamName")
    @Mapping(target = "playerRole", source = "playerRoleNumber")
    public abstract PlayerModel requestToNewModel(PlayerRequestDTO playerRequestDTO);

    @Mapping(target = "teamName", source = "teamModel")
    public abstract PlayerResponseDTO modelToResponse(PlayerModel playerModel);

    public abstract void putUpdate(PlayerModel playerModelPut, @MappingTarget PlayerModel playerModel);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void patchUpdate(PlayerModel playerModelPatch, @MappingTarget PlayerModel playerModel);

    protected TeamModel stringToTeamModel(String teamName) {
        if (teamName == null) {
            return null;
        }

        return Optional.ofNullable(teamRepository.findByTeamName(teamName))
                .orElseThrow(() -> new TeamNotFoundException("Team not found."));
    }

    protected String teamModelToString(TeamModel teamModel) {
        if (teamModel == null) {
            return null;
        }

        return teamModel.getTeamName();
    }

    protected PlayerRole integerToPlayerRole(Integer playerRoleNumber) {
        return Optional.ofNullable(playerRoleRepository.findByPlayerRoleNumber(playerRoleNumber))
                .orElseThrow(() -> new InvalidPlayerRoleNumberException
                        ("Invalid player role number. Must be between 1 and 5."));
    }

    protected String playerRoleToInteger(PlayerRole playerRole) {
        return playerRole.getPlayerRoleName();
    }
}
