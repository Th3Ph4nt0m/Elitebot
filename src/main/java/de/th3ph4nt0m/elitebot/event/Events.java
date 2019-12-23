package de.th3ph4nt0m.elitebot.event;

import com.github.theholywaffle.teamspeak3.api.event.*;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import de.th3ph4nt0m.elitebot.Elitebot;
import de.th3ph4nt0m.elitebot.utils.ChannelHistory;

public class Events {

    //test

    public static void loadEvents(){
        Elitebot.api.registerAllEvents();
        Elitebot.api.addTS3Listeners(new TS3Listener() {
            public void onTextMessage(TextMessageEvent textMessageEvent) {

            }

            public void onClientJoin(ClientJoinEvent clientJoinEvent) {
                Client c = Elitebot.api.getClientInfo(clientJoinEvent.getClientId());
                Elitebot.clientChanneLHistory.put(c.getId(), new ChannelHistory());
            }

            public void onClientLeave(ClientLeaveEvent clientLeaveEvent) {

            }

            public void onServerEdit(ServerEditedEvent serverEditedEvent) {

            }

            public void onChannelEdit(ChannelEditedEvent channelEditedEvent) {

            }

            public void onChannelDescriptionChanged(ChannelDescriptionEditedEvent channelDescriptionEditedEvent) {

            }

            public void onClientMoved(ClientMovedEvent clientMovedEvent) {
                Client c = Elitebot.api.getClientInfo(clientMovedEvent.getClientId());
                ChannelHistory history = Elitebot.clientChanneLHistory.get(c.getId());

                history.addChannel();
                if(history.isChannelHopping() == 2){
                    Elitebot.api.kickClientFromServer("Please stop channel hopping!", c);
                }else if(history.isChannelHopping() == 1){
                    Elitebot.api.banClient(c.getId(), 7200, "Channel-Hopping");
                }
            }

            public void onChannelCreate(ChannelCreateEvent channelCreateEvent) {

            }

            public void onChannelDeleted(ChannelDeletedEvent channelDeletedEvent) {

            }

            public void onChannelMoved(ChannelMovedEvent channelMovedEvent) {

            }

            public void onChannelPasswordChanged(ChannelPasswordChangedEvent channelPasswordChangedEvent) {

            }

            public void onPrivilegeKeyUsed(PrivilegeKeyUsedEvent privilegeKeyUsedEvent) {

            }
        });
    }
}
