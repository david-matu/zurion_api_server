package com.zuriontech.web.test.david.zurionwebapi.util;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SqlDateAdapter implements JsonDeserializer<Date>, JsonSerializer<Date> {
    private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    private final SimpleDateFormat formatter = new SimpleDateFormat(PATTERN, Locale.ENGLISH);

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        System.out.println("Deserializing date: " + json.getAsString());
    	try {
            return formatter.parse(json.getAsString());
        } catch (ParseException e) {
            throw new JsonParseException("Failed to parse date: " + json.getAsString(), e);
        }
    }

    @Override
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
    	System.out.println("Serializing date: " + src.toString());
    	
    	return new JsonPrimitive(formatter.format(src));
    }
}

/*
public class SqlDateAdapter implements JsonDeserializer<Date>, JsonSerializer<Date> {
    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return Date.valueOf(json.getAsString()); // Assumes format is "YYYY-MM-DD"
    }

    @Override
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toString()); // Outputs "YYYY-MM-DD"
    }
}
*/
