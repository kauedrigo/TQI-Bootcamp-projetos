package com.api.dotainfo.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "player_roles")
public class PlayerRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "player_role_number", unique = true, nullable = false)
    private int playerRoleNumber;

    @Column(name = "player_role_name", unique = true, nullable = false)
    private String playerRoleName;

    @OneToMany(mappedBy = "playerRole")
    private List<PlayerModel> playerModelList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPlayerRoleNumber() {
        return playerRoleNumber;
    }

    public void setPlayerRoleNumber(int playerRoleNumber) {
        this.playerRoleNumber = playerRoleNumber;
    }

    public String getPlayerRoleName() {
        return playerRoleName;
    }

    public void setPlayerRoleName(String playerRoleName) {
        this.playerRoleName = playerRoleName;
    }

    public List<PlayerModel> getPlayerModelList() {
        return playerModelList;
    }

    public void setPlayerModelList(List<PlayerModel> playerModelList) {
        this.playerModelList = playerModelList;
    }
}
