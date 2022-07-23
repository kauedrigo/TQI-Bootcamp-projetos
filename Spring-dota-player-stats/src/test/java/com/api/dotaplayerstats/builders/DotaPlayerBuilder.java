package com.api.dotaplayerstats.builders;

import com.api.dotaplayerstats.models.DotaPlayerModel;

public class DotaPlayerBuilder {

    private Long id = 1L;
    private String dotaPlayerAlias = "ana";
    private String dotaPlayerName = "Anatham Pham";
    private String dotaPlayerRole = "Carry";

    public DotaPlayerModel build() {
        DotaPlayerModel dotaPlayerModel = new DotaPlayerModel();
        dotaPlayerModel.setId(this.id);
        dotaPlayerModel.setDotaPlayerAlias(this.dotaPlayerAlias);
        dotaPlayerModel.setDotaPlayerName(this.dotaPlayerName);
        dotaPlayerModel.setDotaPlayerRole(this.dotaPlayerRole);
        return dotaPlayerModel;
    }
}
