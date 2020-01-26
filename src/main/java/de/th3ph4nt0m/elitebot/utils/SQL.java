package de.th3ph4nt0m.elitebot.utils;

import java.sql.*;

@SuppressWarnings("ALL")
public class SQL {

    private Connection connection;

    private String host, user, database, password, url;

    public SQL(String host, String user, String database, String password) {

        this.host = host;
        this.user = user;
        this.database = database;
        this.password = password;
        this.url = "jdbc:mysql://" + this.host + ":3306/" + this.database;
    }

    public boolean isConnected() {
        return this.connection != null;
    }

    public void connect() {
        if (!(isConnected())) {
            try {
                this.connection = DriverManager.getConnection(this.url, this.user, this.password);
                System.out.println("[MySQL] The Connection was successfully created");
            } catch (SQLException e) {
                System.out.println("[MySQL] Failed to connect");
            }
        }
    }

    public void disconnect() {
        if (isConnected()) {
            try {
                this.connection.close();
                this.connection = null;
                System.out.println("[MySQL] The Connection is disconnected");
            } catch (SQLException e) {
                System.out.println("[MySQL] Failed to disconnect");
            }
        }
    }

    public void update(String qry) {
        try {
            final PreparedStatement preparedStatement = this.connection.prepareStatement(qry);
            preparedStatement.executeUpdate(qry);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet query(String qry) {
        try {
            final PreparedStatement preparedStatement = this.connection.prepareStatement(qry);
            return preparedStatement.executeQuery(qry);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Connection getConnection(){return connection;}
}
