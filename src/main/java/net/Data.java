package net;

import com.google.gson.*;
import model.Action;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public final class Data {

    private static final ExclusionStrategy SERIALIZATION_EXCLUSION_STRATEGY = new ExclusionStrategy() {

        final List<String> EXCLUDED_FIELDS = Collections.singletonList("DELAY");

        @Override
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            return EXCLUDED_FIELDS.contains(fieldAttributes.getName().toUpperCase(Locale.getDefault()));
        }

        @Override
        public boolean shouldSkipClass(Class<?> aClass) {
            return false;
        }
    };

    private static final Gson GSON = new GsonBuilder()
            .addSerializationExclusionStrategy(SERIALIZATION_EXCLUSION_STRATEGY).create();

    private static final Gson GSON_PRETTY = new GsonBuilder().setPrettyPrinting()
            .addSerializationExclusionStrategy(SERIALIZATION_EXCLUSION_STRATEGY).create();

    private final Action action;

    private final boolean forfeit;

    private final int delay = 0;

    public Data() {
        this.action = null;
        this.forfeit = false;
    }

    public Data(Action action) {
        this.action = action;
        this.forfeit = false;
    }

    public Data(Action action, boolean forfeit) {
        this.action = action;
        this.forfeit = forfeit;
    }

    public static Data fromJSON(String json) throws JsonSyntaxException {
        return GSON.fromJson(json, Data.class);
    }

    public static Data fromJsonObject(JsonObject jsonObject) throws JsonSyntaxException {
        return GSON.fromJson(jsonObject, Data.class);
    }

    public final String toJSON() {
        return GSON.toJson(this);
    }

    public final String toPrettyJSON() {
        return GSON_PRETTY.toJson(this);
    }

    public final JsonObject toJsonObject() {
        return GSON.toJsonTree(this, Data.class).getAsJsonObject();
    }

    public final Action getAction() {
        return action;
    }

    public final boolean hasAction() {
        return getAction() != null;
    }

    public final boolean getForfeit() {
        return forfeit;
    }

    public final boolean isForfeit() {
        return getForfeit();
    }

    public final int getDelay() {
        return delay;
    }

    public String toString() {
        return String.format("Action: %s, Forfeit: %s Delay: %d",
                this.getAction(), this.getForfeit(), this.getDelay());
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Data)) {
            return false;
        }
        if (o == this) {
            return true;
        }
        Data packet = (Data) o;
        return (packet.getAction().equals(this.getAction())) && (packet.getForfeit() == this.getForfeit()) &&
                (packet.getDelay() == this.getDelay());
    }

    public int hashCode() {
        return 55 + (this.getAction() != null ? this.getAction().hashCode() : 0) +
                (this.getForfeit() ? 12 : 24) + (this.getDelay());
    }
}
