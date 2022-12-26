package com.api.dotainfo.mappers;

import com.api.dotainfo.dtos.TeamRequestDTO;
import com.api.dotainfo.dtos.TeamResponseDTO;
import com.api.dotainfo.models.PlayerModel;
import com.api.dotainfo.models.TeamModel;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-24T16:27:23-0300",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 17.0.3 (Azul Systems, Inc.)"
)
@Component
public class TeamMapperImpl extends TeamMapper {

    @Override
    public TeamModel requestToNewModel(TeamRequestDTO teamRequestDTO) {
        if ( teamRequestDTO == null ) {
            return null;
        }

        TeamModel teamModel = new TeamModel();

        teamModel.setPlayerModelList( playerTagListToModel( teamRequestDTO.getPlayerTagList() ) );
        teamModel.setTeamName( teamRequestDTO.getTeamName() );

        return teamModel;
    }

    @Override
    public TeamResponseDTO modelToResponse(TeamModel teamModel) {
        if ( teamModel == null ) {
            return null;
        }

        Long id = null;
        String teamName = null;

        id = teamModel.getId();
        teamName = teamModel.getTeamName();

        TeamResponseDTO teamResponseDTO = new TeamResponseDTO( id, teamName );

        teamResponseDTO.setPlayerTagList( playerModelListToString( teamModel.getPlayerModelList() ) );

        return teamResponseDTO;
    }

    @Override
    public TeamModel putUpdate(TeamModel teamModelPut, TeamModel teamModel) {
        if ( teamModelPut == null ) {
            return teamModel;
        }

        teamModel.setTeamName( teamModelPut.getTeamName() );
        if ( teamModel.getPlayerModelList() != null ) {
            List<PlayerModel> list = teamModelPut.getPlayerModelList();
            if ( list != null ) {
                teamModel.getPlayerModelList().clear();
                teamModel.getPlayerModelList().addAll( list );
            }
            else {
                teamModel.setPlayerModelList( null );
            }
        }
        else {
            List<PlayerModel> list = teamModelPut.getPlayerModelList();
            if ( list != null ) {
                teamModel.setPlayerModelList( new ArrayList<PlayerModel>( list ) );
            }
        }
        teamModel.setId( teamModelPut.getId() );

        return teamModel;
    }

    @Override
    public TeamModel patchUpdate(TeamModel teamModelPatch, TeamModel teamModel) {
        if ( teamModelPatch == null ) {
            return teamModel;
        }

        if ( teamModelPatch.getTeamName() != null ) {
            teamModel.setTeamName( teamModelPatch.getTeamName() );
        }
        if ( teamModel.getPlayerModelList() != null ) {
            List<PlayerModel> list = teamModelPatch.getPlayerModelList();
            if ( list != null ) {
                teamModel.getPlayerModelList().clear();
                teamModel.getPlayerModelList().addAll( list );
            }
        }
        else {
            List<PlayerModel> list = teamModelPatch.getPlayerModelList();
            if ( list != null ) {
                teamModel.setPlayerModelList( new ArrayList<PlayerModel>( list ) );
            }
        }
        if ( teamModelPatch.getId() != null ) {
            teamModel.setId( teamModelPatch.getId() );
        }

        return teamModel;
    }
}
