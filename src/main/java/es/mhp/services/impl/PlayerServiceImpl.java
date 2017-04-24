package es.mhp.services.impl;

import es.mhp.services.IPlayerService;
import es.mhp.services.ITeamService;
import es.mhp.services.dto.AbstractDTO;
import es.mhp.services.dto.PlayerDTO;
import es.mhp.services.dto.TeamDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by User on 12/04/2017.
 */
@Service
public class PlayerServiceImpl implements IPlayerService {

    Set<AbstractDTO> playerDTOs;

    @Autowired
    private ITeamService teamService;

    public PlayerServiceImpl() {


    }

    @PostConstruct
    void loadPlayers() {

        playerDTOs = retrievePlayers();
    }

    @Override
    public Set<AbstractDTO> find(String playerName, String teamId) {

        Set<AbstractDTO> tmp = new HashSet<AbstractDTO>();

        Iterator<AbstractDTO> iterator = playerDTOs.iterator();
        while (iterator.hasNext()) {
            PlayerDTO playerDTO = (PlayerDTO)iterator.next();
            if (StringUtils.isEmpty(playerName) || playerName.equals(playerDTO.getName())) {
                if (teamId == null || playerDTO.getTeam().getTeamId().equals(teamId)) {
                    tmp.add(playerDTO);
                }
            }
        }

        return tmp;
    }

    @Override
    public void save(Object playerDTO) {

        if (((PlayerDTO) playerDTO).getId() == null) {
            playerDTOs.add((PlayerDTO) playerDTO);
        } else {
            Iterator<AbstractDTO> iterator = playerDTOs.iterator();
            while (iterator.hasNext()) {
                PlayerDTO t = (PlayerDTO)iterator.next();
                if (((PlayerDTO) playerDTO).getId().equals(t.getId())) {
                    t.setName(((PlayerDTO) playerDTO).getName());
                    t.setTeam(((PlayerDTO) playerDTO).getTeam());
                    t.setBirthdate(((PlayerDTO) playerDTO).getBirthdate());
                    t.setInjured(((PlayerDTO) playerDTO).getInjured());
                    break;
                }
            }
        }
    }

    @Override
    public void delete(Object playerDTO) {

        playerDTOs.remove(playerDTO);
    }

    @Override
    public Set<AbstractDTO> findAll() {

        return playerDTOs;
    }

    private Set<AbstractDTO> retrievePlayers() {

        Set<AbstractDTO> tmp = new HashSet<AbstractDTO>();

        String requestURL = "http://footballpool.dataaccess.eu/data/info.wso/AllPlayerNames/JSON/debug?bSelected=";
        try {
            URL request = new URL(requestURL);
            URLConnection connection = request.openConnection();
            connection.setDoOutput(true);
            Scanner scanner = new Scanner(request.openStream());
            String response = scanner.useDelimiter("\\Z").next();
            JSONArray jsonArray = new JSONArray(response);
            PlayerDTO playerDTO;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonTeam = jsonArray.getJSONObject(i);
                playerDTO = new PlayerDTO();
                playerDTO.setId(jsonTeam.getLong("iId"));
                playerDTO.setName(jsonTeam.getString("sName"));
                playerDTO.setCountryFlag(jsonTeam.getString("sCountryFlag"));
                playerDTO.setTeam(findTeam(jsonTeam.getString("sCountryName")));
                playerDTO.setBirthdate(LocalDate.now());
                playerDTO.setInjured(Boolean.FALSE);
                tmp.add(playerDTO);
            }
            scanner.close();
        } catch (Exception e) {
            com.vaadin.ui.Notification.show("Error");
        }

        return tmp;
    }

    private TeamDTO findTeam(String teamName) {

        TeamDTO teamDTO = null;

        Set<AbstractDTO> teams = teamService.find(teamName, null);
        if (teams != null && teams.size() > 0) {
            teamDTO = (TeamDTO) teams.toArray()[0];
        }

        return teamDTO;
    }
}
