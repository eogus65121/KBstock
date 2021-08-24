package com.ezTstock.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

public class SlackImpl implements JsonRead {
    @Override
    public String readJ(String jKey){
        try {
            Reader reader = new FileReader("C://ezTstock_config/config.json");
            Gson gson = new Gson();
            JsonObject jobj = gson.fromJson(reader, JsonObject.class);
            JsonObject jobj_slack = gson.fromJson(jobj.get("slack"), JsonObject.class);
            String value = jobj_slack.get(jKey).toString().replaceAll("\"", "");
            return value;
        }catch(FileNotFoundException e){
            e.getStackTrace();
        }
        return "";
    }
}
