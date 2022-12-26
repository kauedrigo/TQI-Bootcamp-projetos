package com.api.dotainfo.mappers;

import com.api.dotainfo.dtos.PlayerRequestDTO;
import com.api.dotainfo.dtos.PlayerResponseDTO;
import com.api.dotainfo.models.PlayerModel;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-08-24T13:05:22-0300",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 17.0.3 (Azul Systems, Inc.)"
)
@Component
public class PlayerMapperImpl extends PlayerMapper {

    @Override
    public PlayerModel requestToNewModel(PlayerRequestDTO playerRequestDTO) {
        if ( playerRequestDTO == null ) {
            return null;
        }

        PlayerModel playerModel = new PlayerModel();

        playerModel.setTeamModel( stringToTeamModel( playerRequestDTO.getTeamName() ) );
        playerModel.setPlayerRole( integerToPlayerRole( playerRequestDTO.getPlayerRoleNumber() ) );
        playerModel.setPlayerName( playerRequestDTO.getPlayerName() );
        playerModel.setTag( playerRequestDTO.getTag() );

        return playerModel;
    }

    @Override
    public PlayerResponseDTO modelToResponse(PlayerModel playerModel) {
        if ( playerModel == null ) {
            return null;
        }

        PlayerResponseDTO playerResponseDTO = new PlayerResponseDTO();

        playerResponseDTO.setTeamName( teamModelToString( playerModel.getTeamModel() ) );
        playerResponseDTO.setId( playerModel.getId() );
        playerResponseDTO.setPlayerName( playerModel.getPlayerName() );
        playerResponseDTO.setTag( playerModel.getTag() );
        playerResponseDTO.setPlayerRole( playerRoleToInteger( playerModel.getPlayerRole() ) );

        return playerResponseDTO;
    }

    @Override
    public void putUpdate(PlayerModel playerModelPut, PlayerModel playerModel) {
        if ( playerModelPut == null ) {
            return;
        }

        playerModel.setPlayerName( playerModelPut.getPlayerName() );
        playerModel.setTag( playerModelPut.getTag() );
        playerModel.setTeamModel( playerModelPut.getTeamModel() );
        playerModel.setId( playerModelPut.getId() );
        playerModel.setPlayerRole( playerModelPut.getPlayerRole() );
    }

    @Override
    public void patchUpdate(PlayerModel playerModelPatch, PlayerModel playerModel) {
        if ( playerModelPatch == null ) {
            return;
        }

        if ( playerModelPatch.getPlayerName() != null ) {
            playerModel.setPlayerName( playerModelPatch.getPlayerName() );
        }
        if ( playerModelPatch.getTag() != null ) {
            playerModel.setTag( playerModelPatch.getTag() );
        }
        if ( playerModelPatch.getTeamModel() != null ) {
            playerModel.setTeamModel( playerModelPatch.getTeamModel() );
        }
        if ( playerModelPatch.getId() != null ) {
            playerModel.setId( playerModelPatch.getId() );
        }
        if ( playerModelPatch.getPlayerRole() != null ) {
            playerModel.setPlayerRole( playerModelPatch.getPlayerRole() );
        }
    }
}
