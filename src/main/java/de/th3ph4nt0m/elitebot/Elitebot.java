package de.th3ph4nt0m.elitebot;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
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

    public void main(String[] args){
        config.setHost("eliteblocks.eu");
        config.setFloodRate(TS3Query.FloodRate.UNLIMITED);
        config.setDebugLevel(Level.ALL);
        query.connect();
        api.login("serveradmin", "Phant0m_01");
        api.selectVirtualServerById(1);
        api.setNickname("Elitebot");
        Events.loadEvents();
        this.startAFKMover();
        this.startRecordCheck();
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

    public void startAFKMover(){
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for(Client clients : api.getClients()){
                    if(clients.getIdleTime() >= 1000*60*15){
                        if(!clients.isInServerGroup(109)){
                            Elitebot.api.moveClient(clients.getId(), 171);
                        }else{
                            Elitebot.api.moveClient(clients.getId(), 151);
                        }
                    }
                }
            }
        }, 0,5*1000);
    }

    public void startRecordCheck(){
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for(Client clients : Elitebot.api.getClients()){
                    if(clients.isRecording()){
                        Elitebot.api.kickClientFromServer("Recording is not allowed here!", clients);
                    }
                }
            }
        }, 100, 1000);
    }
}
