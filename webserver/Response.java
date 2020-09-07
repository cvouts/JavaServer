package webserver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.text.DateFormat;  
import java.nio.file.attribute.FileTime;

public class Response {
    String _root = "";
    String _resource = "";
    Request _request = null;
    int _statusCode = 0;
    String _content = null;

    public Response(String resource) {
        _setRoot();
        _setResource(resource);
        buildResponse();
    }

    public Response(Request request) {
        _request = request;
        _setRoot();
        _setResource(request.getResource());
        buildResponse();
    }

    private void _setRoot() {
        // TODO: Remove this function, the ROOT should be
        // specified by the config file

        // from: http://stackoverflow.com/a/22350931/1477072
        String currentDir = Paths.get(".").toAbsolutePath().normalize().toString();

        // from: http://stackoverflow.com/a/20925261/1477072
        String packageName = Response.class.getPackage().getName();
        _root = currentDir + "/" + packageName;
    }

    private void _setResource(String resource) {
        _resource = _root + resource;
    }

    public String getContent() {
        return _content;
    }

    private void buildResponse() {
        // check resource
        checkResource();
        // generate headers
    }

    private void checkResource() {
        File f = new File(_resource);
        if(!f.exists()) {
            System.out.println(_resource);
            System.out.println("404");
            _statusCode = 404;
            return;
        }

        if(f.isDirectory()) {
            // do something
            System.out.println("directory");
        } else {
            try {
                loadFile();
            }
            catch (IOException E){
                // do something
            }
        }

        return;
    }

    private void loadFile() throws IOException {
        _content = new String(
            Files.readAllBytes(Paths.get(_resource)));
    }
}
