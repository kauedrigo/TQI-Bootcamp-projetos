package com.api.dotainfo.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teams")
public class TeamModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "team_name", unique = true, nullable = false)
    private String teamName;

    @OneToMany(mappedBy = "teamModel")
    @Size(max = 5)
    private List<PlayerModel> playerModelList;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<PlayerModel> getPlayerModelList() {
        return playerModelList;
    }

    public void setPlayerModelList(List<PlayerModel> playerModelList) {
        this.playerModelList = playerModelList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
