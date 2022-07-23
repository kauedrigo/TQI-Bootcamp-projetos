package com.api.dotaplayerstats.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TB_DOTA_PLAYER")
public class DotaPlayerModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @Column(nullable = false, unique = true, length = 20)
    private String dotaPlayerAlias;

    @Column(nullable = false, length = 50)
    private String dotaPlayerName;

    @Column(nullable = false, length = 20)
    private String dotaPlayerRole;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

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
