package com.api.dotainfo.models;

import javax.persistence.*;

@Entity
@Table(name = "players")
public class PlayerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "tag", unique = true, nullable = false)
    private String tag;

    @Column(name = "player_name")
    private String playerName;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private PlayerRole playerRole;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private TeamModel teamModel;

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

    public TeamModel getTeamModel() {
        return teamModel;
    }

    public void setTeamModel(TeamModel teamModel) {
        this.teamModel = teamModel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlayerRole getPlayerRole() {
        return playerRole;
    }

    public void setPlayerRole(PlayerRole playerRole) {
        this.playerRole = playerRole;
    }
}
