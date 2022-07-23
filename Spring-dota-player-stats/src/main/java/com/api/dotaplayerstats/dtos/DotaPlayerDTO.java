package com.api.dotaplayerstats.dtos;

import javax.validation.constraints.NotBlank;

public class DotaPlayerDTO {

    @NotBlank
    private String dotaPlayerAlias;

    @NotBlank
    private String dotaPlayerName;

    @NotBlank
    private String dotaPlayerRole;

    public String getDotaPlayerAlias() {
        return dotaPlayerAlias;
    }

    public void setDotaPlayerAlias(String dotaPlayerAlias) {
        this.dotaPlayerAlias = dotaPlayerAlias;
    }

    public String getDotaPlayerName() {
        return dotaPlayerName;
    }

    public void setDotaPlayerName(String dotaPlayerName) {
        this.dotaPlayerName = dotaPlayerName;
    }

    public String getDotaPlayerRole() {
        return dotaPlayerRole;
    }

    public void setDotaPlayerRole(String dotaPlayerRole) {
        this.dotaPlayerRole = dotaPlayerRole;
    }
}
