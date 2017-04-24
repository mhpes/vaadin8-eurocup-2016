package es.mhp.services.dto;

import java.time.LocalDate;
import java.util.Date;

/**
 * Created by User on 12/04/2017.
 */
public class PlayerDTO extends AbstractDTO {

    private Long id;
    private String name;
    private TeamDTO team;
    private String countryFlag;
    private LocalDate birthdate;
    private Boolean injured;

    public PlayerDTO() {

        super();
    }

    public PlayerDTO(PlayerDTO p) {

        if (p != null) {
            this.id = p.getTeamId();
            this.name = p.getName();
            this.countryFlag = p.getCountryFlag();
            this.team = p.getTeam();
            this.birthdate = p.getBirthdate();
            this.injured = p.getInjured();
        }
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public Date getBirthdateAsDate() {
        return new Date();
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public Boolean getInjured() {
        return injured;
    }

    public void setInjured(Boolean injured) {
        this.injured = injured;
    }

    public String getCountryFlag() {
        return countryFlag;
    }

    public void setCountryFlag(String countryFlag) {
        this.countryFlag = countryFlag;
    }

    @Override
    public Object getId() {
        return getTeamId();
    }

    public Long getTeamId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TeamDTO getTeam() {
        return team;
    }

    public void setTeam(TeamDTO team) {
        this.team = team;
    }
}
