package com.zuriontech.web.test.david.zurionwebapi.util;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        synchronized (sdf) {
            return new JsonPrimitive(sdf.format(src));
        }
    }

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            synchronized (sdf) {
                return sdf.parse(json.getAsString());
            }
        } catch (ParseException e) {
            throw new JsonParseException("Failed to parse Date: " + json.getAsString(), e);
        }
    }
}