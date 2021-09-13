package net.config;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlType(propOrder = { "server", "localPlayer", "publicPlayer" })
@XmlAccessorType(XmlAccessType.FIELD)
public final class Configuration
{
    @XmlElement
    private Server server;

    @XmlElement(name = "local-player")
    private LocalPlayer localPlayer;

    @XmlElement(name = "public-player")
    private PublicPlayer publicPlayer;

    public Configuration() {}

    public Configuration(Server server, LocalPlayer localPlayer, PublicPlayer publicPlayer) {
        this.server = server;
        this.localPlayer = localPlayer;
        this.publicPlayer = publicPlayer;
    }

    public void setServer(Server server) { this.server = server; }

    public void setLocalPlayer(LocalPlayer localPlayer) { this.localPlayer = localPlayer; }

    public void setPublicPlayer(PublicPlayer publicPlayer) { this.publicPlayer = publicPlayer; }

    public Server getServer() { return server; }

    public LocalPlayer getLocalPlayer() { return localPlayer; }

    public PublicPlayer getPublicPlayer() { return publicPlayer; }

    @XmlType
    @XmlAccessorType(XmlAccessType.FIELD)
    public static final class Server
    {
        @XmlElement
        private Connection connection;

        public Server() {}

        public Server(Connection connection) { this.connection = connection; }

        public void setConnection(Connection connection) { this.connection = connection; }

        public Connection getConnection() { return connection; }
    }

    @XmlType
    @XmlAccessorType(XmlAccessType.FIELD)
    public static final class LocalPlayer
    {
        @XmlElement
        private Connection connection;

        public LocalPlayer() {}

        public LocalPlayer(Connection connection) { this.connection = connection; }

        public void setConnection(Connection connection) { this.connection = connection; }

        public Connection getConnection() { return connection; }
    }

    @XmlType
    @XmlAccessorType(XmlAccessType.FIELD)
    public static final class PublicPlayer
    {
        @XmlElement
        private Connection connection;

        public PublicPlayer() {}

        public PublicPlayer(Connection connection) { this.connection = connection; }

        public void setConnection(Connection connection) { this.connection = connection; }

        public Connection getConnection() { return connection; }
    }
}
