package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;


public class Request {
    String _type = "";
    String _resource = "";
    String _protocol = "";
    String _host = "";
    String _userAgent = "";
    Socket _clientSocket = null;
    private ArrayList<String> _lines = null;

    public Request(Socket clientSocket) {
        _lines = new ArrayList<String>();
        _clientSocket = clientSocket;
        try {
            parseInputStream();
        }
        catch (IOException E){

        }

        extractDetails();
    }

    public String getType() {
        return _type;
    }

    public String getResource() {
        return _resource;
    }

    private void parseInputStream() throws IOException {
        String tmp = "";
        BufferedReader in = new BufferedReader(
            new InputStreamReader(_clientSocket.getInputStream()));
        tmp = in.readLine();
        while(!tmp.equals("")) {
            _lines.add(tmp);
            tmp = in.readLine();
        }
    }

    private void extractDetails() {
        parseFirstLine(_lines.get(0));
        // parse line 2
        // parse line 3
        // parse rest
    }

    private void parseFirstLine(String line) {
        // first line is always in the form of <VERB> <RESOURCE> <PROTOCOL>
        // source: https://www.w3.org/Protocols/rfc2616/rfc2616-sec5.html
        String[] pieces = line.split("\\s");
        _type = pieces[0];
        _resource = pieces[1];
        _protocol = pieces[2];
    }
}
