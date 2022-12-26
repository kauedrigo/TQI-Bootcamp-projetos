package com.api.dotainfo.dtos;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class TeamRequestDTO {

    @NotBlank
    private String teamName;

    private List<String> playerTagList;

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
