package net;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;
import common.Relay;
import common.Request;
import common.Response;
import common.util.JsonObjectBuilder;
import model.Action;
import model.Color;
import model.Position;
import model.player.LocalPlayer;
import model.player.Player;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public final class Session {

    private static final ExclusionStrategy EXCLUSION_STRATEGY = new ExclusionStrategy() {

        private final List<String> EXCLUDED_NAMES = Collections.singletonList("CLIENT");

        @Override
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            return EXCLUDED_NAMES.contains(fieldAttributes.getName().toUpperCase(Locale.ENGLISH));
        }

        @Override
        public boolean shouldSkipClass(Class<?> aClass) {
            return false;
        }
    };

    private static final Gson GSON = new GsonBuilder().setExclusionStrategies(EXCLUSION_STRATEGY)
            .create();

    private static final Gson GSON_PRETTY = new GsonBuilder().setExclusionStrategies(EXCLUSION_STRATEGY)
            .setPrettyPrinting().create();

    private transient Client client;

    private transient LocalPlayer localPlayer;

    private transient List<Player> players;

    private Color color;

    @SerializedName(value = "connection-id")
    private int connectionID;

    @SerializedName(value = "socket-id")
    private int socketID;

    public static Session fromJSON(String json) throws JsonSyntaxException {
        return GSON.fromJson(json, Session.class);
    }

    public static Session fromJsonObject(JsonObject jsonObject) throws JsonSyntaxException {
        return GSON.fromJson(jsonObject, Session.class);
    }

    public String toJSON() {
        return GSON.toJson(this);
    }

    public String toPrettyJSON() {
        return GSON_PRETTY.toJson(this);
    }

    public JsonObject toJsonObject() {
        return GSON.toJsonTree(this, Session.class).getAsJsonObject();
    }

    public void send(Data data) throws IOException {
        if (data == null) {
            throw new IllegalArgumentException(
                    "data must not be null."
            );
        }

        JsonObject requestBody = new JsonObjectBuilder()
                .add("session", this.toJsonObject())
                .add("data", data.toJsonObject())
                .build();
        Request request = new Request(new Request.Header(Request.RELAY), requestBody);
        Client client = new Client(this.client.getRemoteSocketAddress(), this.client.getPort());
        client.send(request);
        client.close();
    }

    public void send(Action data) throws IOException {
        if (data == null) {
            throw new IllegalArgumentException(
                    "data must not be null."
            );
        }
        JsonObject body = new JsonObjectBuilder()
                .add("connection-id", connectionID)
                .add("socket-id", socketID)
                .add("data", new JsonObjectBuilder()
                        .add("start-x", data.getStartPosition().getX())
                        .add("start-y", data.getStartPosition().getY())
                        .add("end-x", data.getEndPosition().getX())
                        .add("end-y", data.getEndPosition().getY())
                        .build())
                .build();
        Relay packet = new Relay(new Relay.Header(), body);
        packet.getHeader().setSent();
        Client client = new Client(this.client.getRemoteSocketAddress(), this.client.getPort());
        client.send(new Request(new Request.Header(Request.RELAY, false),
                packet.toJsonObject()));
        client.close();
    }

    /*
    public void send(Data data) throws IOException {
        Relay packet = new Relay(new Relay.Header(), new JsonObjectBuilder(data.toJsonObject())
                .add("connection-id", connectionID)
                .add("socket-id", socketID)
                .build());
        packet.getHeader().setSent();
        Client client = new Client(this.client.getRemoteSocketAddress(), this.client.getPort());
        client.send(new Request(new Request.Header(Request.RELAY, false), packet.toJsonObject()));
        client.close();
    }
     */

    public Action read() throws IOException, JsonSyntaxException {
        Response response = client.read();
        Relay packet = Relay.fromJsonObject(response.getBody());
        packet.getHeader().setReceived();
        if (!(packet.hasBody()) || !(packet.getBody().has("data"))) {
            throw new IllegalArgumentException(
                    "response does not have body, or body does not have data field."
            );
        }
        JsonObject data = packet.getBody().get("data").getAsJsonObject();
        return new Action(
                new Position(data.get("start-x").getAsInt(), data.get("start-y").getAsInt()),
                new Position(data.get("end-x").getAsInt(), data.get("end-y").getAsInt())
        );
    }

    public Action read(int timeout) throws IOException, JsonSyntaxException {
        Response response = client.read(timeout);
        Relay route = Relay.fromJsonObject(response.getBody());
        route.getHeader().setReceived();
        if (!(route.hasBody()) || !(route.getBody().has("data"))) {
            throw new IllegalArgumentException(
                    "response does not have body, or body does not have data field."
            );
        }
        JsonObject data = route.getBody().get("data").getAsJsonObject();
        return new Action(
                new Position(data.get("start-x").getAsInt(), data.get("start-y").getAsInt()),
                new Position(data.get("end-x").getAsInt(), data.get("end-y").getAsInt())
        );
    }

    public final Color getColor() {
        return color;
    }

    public final void setColor(Color color) {
        this.color = color;
    }

    public final void setClient(Client client) {
        this.client = client;
    }

    public final void setPlayers(List<Player> players) {
        this.players = players;
    }

    public final List<Player> getPlayers() {
        return players;
    }

    public final void setLocalPlayer(LocalPlayer localPlayer) {
        this.localPlayer = localPlayer;
    }

    public final LocalPlayer getLocalPlayer() {
        return localPlayer;
    }
}
