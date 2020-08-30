package com.thesevenq.uhc.backend;

import com.mongodb.*;
import com.thesevenq.uhc.UHC;
import com.thesevenq.uhc.utilties.Color;
import com.thesevenq.uhc.utilties.Manager;
import com.thesevenq.uhc.utilties.Tasks;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import redis.clients.jedis.JedisPool;

@Getter
@Setter
public class DatabaseManager extends Manager {

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
        this.dediIp = UHC.getInstance().getConfig().getString("DATABASE.AUTHENTICATION.DEDICATEDIP");
        this.client = null;
        this.database = null;
        this.username = "destiny";
        this.password = "m2462463o236236n236326g751528o2584236";
        this.db = "destiny";
        this.host = "ds253104.mlab.com";
        this.port = 53104;
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
        final MongoCredential credential = MongoCredential.createCredential(username, database2, password.toCharArray());
        client = new MongoClient(new ServerAddress("127.0.0.1", 27017));
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
