package me.andyreckt.uhc.backend;

import com.mongodb.*;
import me.andyreckt.uhc.UHC;
import me.andyreckt.uhc.utilties.Color;
import me.andyreckt.uhc.utilties.Manager;
import me.andyreckt.uhc.utilties.Tasks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import redis.clients.jedis.JedisPool;


public class DatabaseManager extends Manager {
    public MongoClient getClient() {
        return client;
    }

    public void setClient(MongoClient client) {
        this.client = client;
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public void setDatabase(MongoDatabase database) {
        this.database = database;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public boolean isCrates() {
        return crates;
    }

    public void setCrates(boolean crates) {
        this.crates = crates;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public MongoCollection getChallengeData() {
        return challengeData;
    }

    public void setChallengeData(MongoCollection challengeData) {
        this.challengeData = challengeData;
    }

    public MongoCollection getMeetupProfiles() {
        return meetupProfiles;
    }

    public void setMeetupProfiles(MongoCollection meetupProfiles) {
        this.meetupProfiles = meetupProfiles;
    }

    public MongoCollection getPunishProfiles() {
        return punishProfiles;
    }

    public void setPunishProfiles(MongoCollection punishProfiles) {
        this.punishProfiles = punishProfiles;
    }

    public MongoCollection getEssData() {
        return essData;
    }

    public void setEssData(MongoCollection essData) {
        this.essData = essData;
    }

    public MongoCollection getCrateData() {
        return crateData;
    }

    public void setCrateData(MongoCollection crateData) {
        this.crateData = crateData;
    }

    public MongoCollection getRanksGrants() {
        return ranksGrants;
    }

    public void setRanksGrants(MongoCollection ranksGrants) {
        this.ranksGrants = ranksGrants;
    }

    public MongoCollection getSecurityProfiles() {
        return securityProfiles;
    }

    public void setSecurityProfiles(MongoCollection securityProfiles) {
        this.securityProfiles = securityProfiles;
    }

    public MongoCollection getUhcProfiles() {
        return uhcProfiles;
    }

    public void setUhcProfiles(MongoCollection uhcProfiles) {
        this.uhcProfiles = uhcProfiles;
    }

    public MongoCollection getUhcDeaths() {
        return uhcDeaths;
    }

    public void setUhcDeaths(MongoCollection uhcDeaths) {
        this.uhcDeaths = uhcDeaths;
    }

    public MongoCollection getRanksProfiles() {
        return ranksProfiles;
    }

    public void setRanksProfiles(MongoCollection ranksProfiles) {
        this.ranksProfiles = ranksProfiles;
    }

    public MongoCollection getPracticeProfiles() {
        return practiceProfiles;
    }

    public void setPracticeProfiles(MongoCollection practiceProfiles) {
        this.practiceProfiles = practiceProfiles;
    }

    public MongoCollection getAuthmeProfiles() {
        return authmeProfiles;
    }

    public void setAuthmeProfiles(MongoCollection authmeProfiles) {
        this.authmeProfiles = authmeProfiles;
    }

    public MongoCollection getUhcData() {
        return uhcData;
    }

    public void setUhcData(MongoCollection uhcData) {
        this.uhcData = uhcData;
    }

    public JedisPool getPool() {
        return pool;
    }

    public void setPool(JedisPool pool) {
        this.pool = pool;
    }

    public String getDediIp() {
        return dediIp;
    }

    public void setDediIp(String dediIp) {
        this.dediIp = dediIp;
    }

    public boolean isDevMode() {
        return isDevMode;
    }

    public void setDevMode(boolean devMode) {
        isDevMode = devMode;
    }

    private MongoClient client;
    private MongoDatabase database;
    private boolean connected, crates;
    private String username;
    private String password;
    private String db;
    private String host;
    private int port;
    public MongoCollection
            challengeData,
            meetupProfiles,
            punishProfiles,
            essData,
            crateData,
            ranksGrants,
            securityProfiles,
            uhcProfiles,
            uhcDeaths,
            ranksProfiles,
            practiceProfiles,
            authmeProfiles,
            uhcData;
    private JedisPool pool;
    public String dediIp;
    public boolean isDevMode = false;

    public DatabaseManager(final UHC plugin) {
        super(plugin);
        this.dediIp = "localhost";
        this.client = null;
        this.database = null;
        this.username = "";
        this.password = "";
        this.db = "uhc";
        this.host = "localhost";
        this.port = 27017;
        setupMongo(this.username, this.password, this.db, this.host, this.port);
        this.setDatabase(this.db);
        this.connected = false;
        uhcData = database.getCollection("UHCData");
        securityProfiles = database.getCollection("SecurityProfiles");
        challengeData = database.getCollection("ChallangeData");
        punishProfiles = database.getCollection("punishmentProfiles");
        essData = database.getCollection("essentialsProfiles");
        if (crates) crateData = database.getCollection("crates");
        ranksGrants = database.getCollection("rankGrants");
        ranksProfiles = database.getCollection("rankProfiles");
        meetupProfiles = database.getCollection("meetupProfiles");
        authmeProfiles = database.getCollection("authmeProfiles");
        uhcProfiles = database.getCollection("uhcProfiles");
        uhcDeaths = database.getCollection("uhcDeats");

        Tasks.runLater(() -> {
            this.connected = true;
        }, 20L);
    }

    public void setupMongo(final String username, final String password, final String database2, final String host, final int port) {
       // final MongoCredential credential = MongoCredential.createCredential(username, database2, password.toCharArray());
        //client = new MongoClient(new ServerAddress("172.18.0.1", 25644));
        client = new MongoClient(new ServerAddress("localhost", 27017));
        //client = new MongoClient(new MongoClientURI("mongodb://ulxi4s6tvez9ckircdpk:R7nFCkRGsMeLjq1c7Z7e@b60upaqifl4t0hi-mongodb.services.clever-cloud.com:27017/b60upaqifl4t0hi"));
    }

    public void setDatabase(String db) {
        this.database = client.getDatabase(db);
        if (this.client == null) {
            Bukkit.getServer().getPluginManager().disablePlugin((Plugin) UHC.getInstance());
            Bukkit.getConsoleSender().sendMessage(Color.translate("&cMongo failed to connect!"));
            return;
        }
        Bukkit.getConsoleSender().sendMessage(Color.translate("&aMongo is now successfully connected!"));
    }

    public void closeConnection() {
        if (this.client != null) {
            this.client.close();
            Bukkit.getConsoleSender().sendMessage(Color.translate("&aMongo has been successfully disconnected!"));
        }
    }

    }
