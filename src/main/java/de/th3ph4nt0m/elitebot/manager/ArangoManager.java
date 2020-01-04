package de.th3ph4nt0m.elitebot.manager;

import com.arangodb.ArangoCollection;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDatabase;
import com.github.theholywaffle.teamspeak3.api.wrapper.ClientInfo;
import de.th3ph4nt0m.elitebot.Elitebot;
import de.th3ph4nt0m.elitebot.utils.DataPlayer;
import lombok.Getter;

@Getter
public class ArangoManager {

    private ArangoDB arangoDB;
    private ArangoDatabase database;
    private ArangoCollection botcollection;
    private String address, user, password, databaseName;
    private int port;

    public ArangoManager(String address, int port, String user, String password, String databaseName) {

        this.address = address;
        this.port = port;
        this.password = password;
        this.user = user;
        this.databaseName = databaseName;

    }

    public void connect(){

        this.arangoDB = new ArangoDB.Builder().user(user).password(password).host(address,port).build();

        if (!this.arangoDB.getDatabases().contains(databaseName))
            this.arangoDB.createDatabase(databaseName);

        this.database = this.arangoDB.db(databaseName);

        this.botcollection =  this.database.collection("bot");

    }

    public DataPlayer getUser(String uid){

        if (!botcollection.documentExists(uid)){
            DataPlayer dataPlayer = new DataPlayer();

            ClientInfo clientInfo = Elitebot.api.getClientByUId(uid);

            dataPlayer.set_key(""+clientInfo.getUniqueIdentifier());
            dataPlayer.setLastlogout(0);
            dataPlayer.setName(clientInfo.getNickname());

            botcollection.insertDocument(dataPlayer);
        }

        return botcollection.getDocument(uid,DataPlayer.class);

    }

    public void updateUser(DataPlayer dataPlayer) {
        botcollection.updateDocument(dataPlayer.get_key(), dataPlayer);
    }

}
