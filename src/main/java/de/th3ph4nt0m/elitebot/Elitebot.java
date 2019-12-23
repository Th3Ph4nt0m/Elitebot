package de.th3ph4nt0m.elitebot;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import de.th3ph4nt0m.elitebot.event.Events;
import de.th3ph4nt0m.elitebot.utils.ChannelHistory;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

public class Elitebot {

    public static HashMap<Integer, ChannelHistory> clientChanneLHistory = new HashMap<Integer, ChannelHistory>();

    public static final TS3Config config = new TS3Config();
    public static final TS3Query query = new TS3Query(config);
    public  static final TS3Api api = new TS3Api(query);

    public static void main(String[] args){
        config.setHost("eliteblocks.eu");
        config.setFloodRate(TS3Query.FloodRate.UNLIMITED);
        config.setDebugLevel(Level.ALL);
        query.connect();
        api.login("serveradmin", "Phant0m_01");
        api.selectVirtualServerById(1);
        api.setNickname("Elitebot");
        Events.loadEvents();
        System.out.println("Bot started!");
    }

    public void updateChannelHistory(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for(Map.Entry e : clientChanneLHistory.entrySet()){
                    ((ChannelHistory) e.getValue()).rmvChannel();
                }
            }
        }, 5*1000, 5*1000);
    }
}
