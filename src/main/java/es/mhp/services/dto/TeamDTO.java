package es.mhp.services.dto;

/**
 * Created by User on 12/04/2017.
 */
public class TeamDTO extends AbstractDTO {

    private String id;
    private String name;
    private String countryFlag;
    private String wikipediaUrl;
    private String group;

    public TeamDTO() {

        super();
    }
    public TeamDTO(TeamDTO t) {

        if (t != null) {
            this.id = t.getTeamId();
            this.name = t.getName();
            this.countryFlag = t.getCountryFlag();
            this.wikipediaUrl = t.getWikipediaUrl();
            this.group = t.getGroup();
        }
    }

    public String getWikipediaUrl() {
        return wikipediaUrl;
    }

    public void setWikipediaUrl(String wikipediaUrl) {
        this.wikipediaUrl = wikipediaUrl;
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

    public String getTeamId() {
        return id;
    }

    public void setTeamId(String id) {
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
