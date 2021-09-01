package com.ezTstock.config;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

public class DBImpl implements JsonRead{
    @Override
    public String readJ(String jKey){
        try {
            Reader reader = new FileReader("");
            Gson gson = new Gson();
            JsonObject jobj = gson.fromJson(reader, JsonObject.class);
            JsonObject jobj_db = gson.fromJson(jobj.get("stock_db"), JsonObject.class);
            String value = jobj_db.get(jKey).toString().replaceAll("\"", "");
            return value;
        }catch(FileNotFoundException e){
            e.getStackTrace();
        }
        return "";
    }
}
