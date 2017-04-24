package es.mhp.services.impl;

import elemental.json.*;
import elemental.json.impl.JreJsonObject;
import es.mhp.services.ITeamService;
import es.mhp.services.dto.AbstractDTO;
import es.mhp.services.dto.TeamDTO;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.management.Notification;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by User on 12/04/2017.
 */
@Service
public class TeamServiceImpl implements ITeamService {

    Set<AbstractDTO> teamDTOs;

    public TeamServiceImpl() {

        teamDTOs = retrieveTeams();
    }

    @Override
    public Set<AbstractDTO> find(String teamName, String group) {

        Set<AbstractDTO> tmp = new HashSet<AbstractDTO>();

        Iterator<AbstractDTO> iterator = teamDTOs.iterator();
        while (iterator.hasNext()) {
            TeamDTO teamDTO = (TeamDTO)iterator.next();
            if (StringUtils.isEmpty(teamName) || teamDTO.getName().equals(teamName)) {
                if (group == null || teamDTO.getGroup().equals(group)) {
                    tmp.add(teamDTO);
                }
            }
        }

        return tmp;
    }


    private Set<AbstractDTO> retrieveTeams() {

        Set<AbstractDTO> tmp = new HashSet<AbstractDTO>();

        String requestURL = "http://footballpool.dataaccess.eu/data/info.wso/Teams/JSON/debug";
        try {
            URL request = new URL(requestURL);
            URLConnection connection = request.openConnection();
            connection.setDoOutput(true);
            Scanner scanner = new Scanner(request.openStream());
            String response = scanner.useDelimiter("\\Z").next();
            JSONArray jsonArray = new JSONArray(response);
            TeamDTO teamDTO;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonTeam = jsonArray.getJSONObject(i);
                teamDTO = new TeamDTO();
                teamDTO.setId(String.valueOf(jsonTeam.getInt("iId")));
                teamDTO.setName(jsonTeam.getString("sName"));
                teamDTO.setCountryFlag(jsonTeam.getString("sCountryFlag"));
                teamDTO.setWikipediaUrl(jsonTeam.getString("sWikipediaURL"));
                if (i % 4 == 0) {
                    teamDTO.setGroup("A");
                } else {
                    if (i % 4 == 1) {
                        teamDTO.setGroup("B");
                    } else {
                        if (i % 4 == 2) {
                            teamDTO.setGroup("C");
                        } else {
                            teamDTO.setGroup("D");
                        }
                    }
                }

                tmp.add(teamDTO);
            }
            scanner.close();
        } catch (Exception e) {
            com.vaadin.ui.Notification.show("Error");
        }

        return tmp;
    }

    @Override
    public Set<AbstractDTO> findAll() {

        return teamDTOs;
    }

    @Override
    public void save(Object teamDTO) {

        if (((TeamDTO) teamDTO).getId() == null) {
            teamDTOs.add((TeamDTO) teamDTO);
        } else {
            Iterator<AbstractDTO> iterator = teamDTOs.iterator();
            while (iterator.hasNext()) {
                TeamDTO t = (TeamDTO)iterator.next();
                if (((TeamDTO) teamDTO).getId().equals(t.getId())) {
                    t.setName(((TeamDTO) teamDTO).getName());
                    t.setGroup(((TeamDTO) teamDTO).getGroup());
                }
            }
        }
    }

    @Override
    public void delete(Object teamDTO) {

        teamDTOs.remove(teamDTO);
    }
}
