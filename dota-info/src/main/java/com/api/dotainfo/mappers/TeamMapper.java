package com.api.dotainfo.mappers;

import com.api.dotainfo.dtos.TeamRequestDTO;
import com.api.dotainfo.dtos.TeamResponseDTO;
import com.api.dotainfo.exceptions.PlayerNotFoundException;
import com.api.dotainfo.exceptions.TeamCompositionException;
import com.api.dotainfo.models.PlayerModel;
import com.api.dotainfo.models.TeamModel;
import com.api.dotainfo.repositories.PlayerRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Mapper(componentModel = "spring")
public abstract class TeamMapper {

    @Autowired
    private PlayerRepository playerRepository;

    @Mapping(target = "playerModelList", source = "playerTagList")
    public abstract TeamModel requestToNewModel(TeamRequestDTO teamRequestDTO);

    @Mapping(target = "playerTagList", source = "playerModelList")
    public abstract TeamResponseDTO modelToResponse(TeamModel teamModel);

    public abstract TeamModel putUpdate(TeamModel teamModelPut, @MappingTarget TeamModel teamModel);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract TeamModel patchUpdate(TeamModel teamModelPatch, @MappingTarget TeamModel teamModel);

    protected List<PlayerModel> playerTagListToModel(List<String> playerTagList) {

        if (playerTagList == null) {
            return null;
        }

        List<PlayerModel> playerModelList = new ArrayList<>();

        for (String playerTag : playerTagList) {
            playerModelList.add(Optional.ofNullable(playerRepository.findByTag(playerTag))
                    .orElseThrow(() -> new PlayerNotFoundException("Player '" + playerTag + "' not found.")));
        }

        if (playerModelList.size() > 5) {
            throw new TeamCompositionException
                    ("Invalid number of players on the team. A team can have a maximum of 5 players.");
        }

        return playerModelList;
    }

    protected List<String> playerModelListToString(List<PlayerModel> playerModelList) {

        if (playerModelList == null) {
            return null;
        }

        List<String> playerTagList = new ArrayList<>();

        for (PlayerModel playerModel : playerModelList) {
            playerTagList.add(playerModel.getPlayerRole().getPlayerRoleNumber() + ". "
                    + playerModel.getPlayerRole().getPlayerRoleName() + ": " + playerModel.getTag());
        }

        Collections.sort(playerTagList);

        return playerTagList;
    }
}
