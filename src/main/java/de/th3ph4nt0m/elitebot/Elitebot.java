package de.th3ph4nt0m.elitebot;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.th3ph4nt0m.elitebot.event.Events;
import de.th3ph4nt0m.elitebot.utils.ChannelHistory;
import de.th3ph4nt0m.elitebot.utils.SQL;

import java.util.*;
import java.util.logging.Level;

public class Elitebot {

    private static SQL sql;

    public static HashMap<Integer, ChannelHistory> clientChanneLHistory = new HashMap<Integer, ChannelHistory>();



    public static final TS3Config config = new TS3Config();
    public static final TS3Query query = new TS3Query(config);
    public static final TS3Api api = new TS3Api(query);


    public static void main(String[] args) {
        config.setHost("eliteblocks.eu");
        config.setFloodRate(TS3Query.FloodRate.UNLIMITED);
        config.setDebugLevel(Level.ALL);
        query.connect();
        api.login("serveradmin", "Phant0m_01");
        api.selectVirtualServerById(1);
        api.setNickname("Elitebot");
        Events.loadEvents();
        sql.connect();
        startAFKMover();
        startRecordCheck();
        System.out.println("Bot started!");
    }

    public static void updateChannelHistory() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (Map.Entry e : clientChanneLHistory.entrySet()) {
                    ((ChannelHistory) e.getValue()).rmvChannel();
                }
            }
        }, 5 * 1000, 5 * 1000);
    }

    public static void startAFKMover() {
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (Client clients : api.getClients()) {
                    if (clients.getIdleTime() >= 1000 * 60 * 15) {
                        if (!clients.isInServerGroup(109)) {
                            if (clients.getChannelId() != 171)
                                Elitebot.api.moveClient(clients.getId(), 171);
                        } else {
                            if (!clients.isInServerGroup(69) && clients.getChannelId() != 151)
                                Elitebot.api.moveClient(clients.getId(), 151);
                        }
                    }
                    if (clients.getIdleTime() >= 1000 * 60 * 60 * 5) {
                        Elitebot.api.kickClientFromServer("You were away too long...", clients);
                    }
                }
            }
        }, 0, 5 * 1000);
    }

    public static void startRecordCheck() {
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (Client clients : Elitebot.api.getClients()) {
                    if (clients.isRecording()) {
                        Elitebot.api.kickClientFromServer("Recording is not allowed here!", clients);
                    }
                }
            }
        }, 100, 1000);
    }
}
