package com.api.dotainfo.dtos;

import com.api.dotainfo.models.TeamModel;

import java.util.List;

public class TeamResponseDTO {

    private Long id;

    private String teamName;

    private List<String> playerTagList;

    public TeamResponseDTO(Long id, String teamName) {
        this.id = id;
        this.teamName = teamName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<String> getPlayerTagList() {
        return playerTagList;
    }

    public void setPlayerTagList(List<String> playerTagList) {
        this.playerTagList = playerTagList;
    }
}
