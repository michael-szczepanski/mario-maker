package jade;

import com.google.gson.*;

import java.lang.reflect.Type;

public class ComponentDeserializer
        implements JsonSerializer<Component>, JsonDeserializer<Component> {
    // TODO: Split ComponentSerializer out of this class
    // Currently, we are using Serializer and Deserializer as one class, this should be split in the future

    @Override
    public Component deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return null;
    }

    @Override
    public JsonElement serialize(Component src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        result.add("type", new JsonPrimitive(src.getClass().getCanonicalName()));
        result.add("properties", context.serialize(src, src.getClass()));
        return result;
    }
}
