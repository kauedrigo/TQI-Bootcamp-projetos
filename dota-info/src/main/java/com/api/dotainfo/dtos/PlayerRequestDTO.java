package com.api.dotainfo.dtos;

import javax.validation.constraints.*;

public class PlayerRequestDTO {

    @NotBlank
    private String tag;

    private String playerName;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer playerRoleNumber;

    private String teamName;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getPlayerRoleNumber() {
        return playerRoleNumber;
    }

    public void setPlayerRoleNumber(Integer playerRoleNumber) {
        this.playerRoleNumber = playerRoleNumber;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
