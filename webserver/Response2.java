package webserver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Response2 {
    HashMap<String, String> _headers = new HashMap<String, String>();

    public void setHeader(String headerName, String value) {
        _headers.put(headerName, value);
    }

    public String getHeaders() {
        String headers = "";

        System.out.println("Size of hashmap " + _headers.size());
        for (Map.Entry<String, String> entry : _headers.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            headers = headers + key + ": " + value + "\r\n";
        }



        //System.out.println("The response in Response2 is: "+headers);
        return headers;
    }


}
