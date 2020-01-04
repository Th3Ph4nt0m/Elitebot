package de.th3ph4nt0m.elitebot.event;

import com.github.theholywaffle.teamspeak3.api.event.*;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import com.github.theholywaffle.teamspeak3.api.wrapper.ClientInfo;
import de.th3ph4nt0m.elitebot.Elitebot;
import de.th3ph4nt0m.elitebot.utils.ChannelHistory;
import de.th3ph4nt0m.elitebot.utils.DataPlayer;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Events {


    public static void loadEvents() {
        Elitebot.api.registerAllEvents();
        Elitebot.api.addTS3Listeners(new TS3Listener() {
            public void onTextMessage(TextMessageEvent textMessageEvent) {
                if (textMessageEvent.getMessage().startsWith("!msgall")) {
                    String[] args = textMessageEvent.getMessage().split(" ");
                    String msg = "";
                    for (int i = 1; i < args.length; i++) {
                        msg = msg + " " + args[i];
                    }
                    for (Client clients : Elitebot.api.getClients()) {
                        if (clients.getId() != textMessageEvent.getInvokerId()) {
                            Elitebot.api.sendPrivateMessage(clients.getId(), "[color=orange]-->[color=red]BROADCAST MESSAGE:[/color][br]" + msg);
                        }
                    }
                }
            }

            public void onClientJoin(ClientJoinEvent clientJoinEvent) {
                Client c = Elitebot.api.getClientInfo(clientJoinEvent.getClientId());
                Elitebot.clientChanneLHistory.put(c.getId(), new ChannelHistory());
/*                DataPlayer dataPlayer = Elitebot.arangoManager.getUser(clientJoinEvent.getInvokerUniqueId());

                if (dataPlayer.getLastlogout() == 0) {
                    Elitebot.api.sendPrivateMessage(clientJoinEvent.getClientId(), "Wilkommen");
                } else {
                    Date date = new Date(dataPlayer.getLastlogout());
                    String year = (new SimpleDateFormat("dd.MM.yyyy")).format(date);
                    String hour_min = (new SimpleDateFormat("HH:mm:ss")).format(date);

                    Elitebot.api.sendPrivateMessage(clientJoinEvent.getClientId(), "Du warst das letzte mal am" + year + " um" + hour_min + " Online");
                }*/
            }

            public void onClientLeave(ClientLeaveEvent event) {
 /*               System.out.println("1");
                DataPlayer dataPlayer = Elitebot.arangoManager.getUser(event.getInvokerUniqueId());
                System.out.println("2");
                dataPlayer.setLastlogout(System.currentTimeMillis() + 0);
                System.out.println("3");
                Elitebot.arangoManager.updateUser(dataPlayer);
                System.out.println("create");*/
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
                if (history.isChannelHopping() == 2) {
                    Elitebot.api.kickClientFromServer("Please stop channel hopping!", c);
                } else if (history.isChannelHopping() == 1) {
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
