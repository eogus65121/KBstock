package com.ezTstock.stock_db.urlRequest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class UrlRequest {

    public String user_get_name(String user_name) throws IOException {
        URL obj = new URL("http://localhost:8080/db/get/user?name=" + user_name);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        String line = in.readLine();
        return line;
    }

    public void user_add_user(String user_name) throws IOException{
        URL obj = new URL("http://localhost:8080/db/add/user?name="+user_name+"&requirement=off");
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setDoOutput(true);
        con.setRequestMethod("PUT");
        OutputStreamWriter out = new OutputStreamWriter(
                con.getOutputStream());
        out.write("Resource content");
        out.close();
        con.getInputStream();
    }

    public void user_delete_user(String user_name) throws IOException{
        URL obj = new URL("http://localhost:8080/db/delete/user?name="+user_name);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setDoOutput(true);
        con.setRequestProperty(
                "Content-Type", "application/x-www-form-urlencoded" );
        con.setRequestMethod("DELETE");
        con.connect();
    }

    public String variance_get_userData(String name) throws IOException{  // return list 형식 get
        URL obj = new URL("http://localhost:8080/variance/get/user_data?name=" + name);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        String line = in.readLine();
        return line;
    }

    public void variance_insert_value(String subject_name, String name, String value) throws IOException{ //put
        URL obj = new URL("http://localhost:8080/variance/add/user?subject="+subject_name+"&user_name="+name+"&value="+value);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setDoOutput(true);
        con.setRequestMethod("PUT");
        OutputStreamWriter out = new OutputStreamWriter(
                con.getOutputStream());
        out.write("Resource content");
        out.close();
        con.getInputStream();
    }

    public void variance_update_value(String subject_name, String name, String value) throws IOException{ //post
        URL obj = new URL("http://localhost:8080/variance/value/update?subject="+subject_name+"&user_name="+name+"&value="+value);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setDefaultUseCaches(false);
        con.setDoInput(true); // 서버에서 읽기 모드 지정
        con.setDoOutput(true); // 서버로 쓰기 모드 지정
        con.setRequestMethod("POST"); // 전송 방식은 POST
        OutputStreamWriter out = new OutputStreamWriter(
                con.getOutputStream());
        out.write("Resource content");
        out.close();
        con.getInputStream();
    }

    public void variance_delete_value(String subject_name, String name) throws IOException { //delete mapping
        URL obj = new URL("http://localhost:8080/variance/value/delete?subject="+subject_name+"&user_name="+name);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setDoOutput(true);
        con.setRequestProperty(
                "Content-Type", "application/x-www-form-urlencoded" );
        con.setRequestMethod("DELETE");
        con.connect();
    }
}
