package me.rezscipts.rpg.utils.gson;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import me.rezscipts.rpg.items.ItemManager;
import me.rezscipts.rpg.items.RPGItem;
import me.rezscipts.rpgexperience.utils.gson.RGson;

public class RPGItemAdapter extends TypeAdapter<RPGItem> {

    public static void register() {
        RGson.registerModifier((builder) -> {
            builder.registerTypeAdapter(RPGItem.class, new RPGItemAdapter());
            return builder;
        });
    }

    @Override
    public RPGItem read(JsonReader in) throws IOException {
        String id = "";
        in.beginObject();
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "identifier":
                    id = in.nextString();
                    break;
            }
        }
        in.endObject();
        return ItemManager.itemIdentifierToRPGItemMap.getOrDefault(id, null);
    }

    @Override
    public void write(JsonWriter out, RPGItem item) throws IOException {
        out.beginObject();
        out.name("identifier").value(item.identifier);
        out.endObject();
    }

}
