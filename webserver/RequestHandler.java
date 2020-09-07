package webserver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
///////////////////////////
import java.util.Date;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.nio.file.attribute.FileTime;
import java.util.HashMap;


public class RequestHandler {
    String _root = "";
    String _resource = "";
    Request _request = null;
    int _statusCode = 0;
    String _content = null;

    public RequestHandler(Request request) {
        _request = request;
        _setRoot();
        _setResource(request.getResource());
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

        try{
            loadFile();
        }catch(IOException e) {
            System.out.println("404");
            _statusCode = 404;
        }


        return _content;
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

/////////////////////////////////////////////////////////////
//Date, Server, Last-Modified, Connection, Content-Length, Content-Type.

    Response2 re = new Response2();


    public void setDateHeader() {
        Date date = new Date();
        String stringDate = String.valueOf(date);

        re.setHeader("Date", stringDate);
    }

    public void setServerHeader() {
        // TODO: the server name (root) must come from somewhere else (configuration).
        re.setHeader("Server", "webserver");
    }

    public void setLastModifiedHeader() {
        FileTime fileTime = null;
        Path path = Paths.get(_resource);
        try {
            fileTime = Files.getLastModifiedTime(path);
            //DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
            //DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - hh:mm:ss");
            //System.out.println(dateFormat.format(fileTime.toMillis()));
        }catch (IOException e) {
            System.err.println("Cannot get the last modified time - " + e);
        }
        DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");

        //Date lastModifiedDate = dateFormat.format(fileTime.toMillis());

        String lastModifiedDateString = String.valueOf(dateFormat.format(fileTime.toMillis()));
        re.setHeader("Last-Modified",  lastModifiedDateString);
    }

    public void setContentLengthHeader() {
        String stringContentLength = String.valueOf(_resource.length());
        re.setHeader("Content-Length", stringContentLength);
    }

    public void setContentTypeHeader() {

        String[] test = _resource.split("\\.");
        String resourceSuffix = test[test.length-1];
        String contentType = setContentType(resourceSuffix);
        re.setHeader("Content-Type", contentType);
    }


    public String setContentType(String resourceSuffix) {
        String contentType = "";
        //linking a resource suffix with a content type needs to be done with a hashmap
        HashMap<String, String> cntType = new HashMap<String, String>();
        cntType.put("html", "text/html");
        cntType.put("txt", "text/plain");
        cntType.put("jpg", "image/jpeg");
        cntType.put("png", "image/png");
        cntType.put("tiff", "image/tiff");
        cntType.put("bmp", "image/bmp");
        cntType.put("avi", "video/x-msvideo");
        cntType.put("mp4", "video/mp4");
        cntType.put("mp3", "audio/mpeg");
        cntType.put("ogga", "audio/ogg");
        cntType.put("pdf", "application/pdf");
        cntType.put("doc", "application/msword");
        cntType.put("xls", "application/vnd.ms-excel");
        cntType.put("ppt", "application/vnd.ms-powerpoint");

        contentType = cntType.get(resourceSuffix);

        return contentType;
    }

    public String respond() {
        setDateHeader();
        setServerHeader();
        setLastModifiedHeader();
        setContentTypeHeader();
        setContentLengthHeader();
        String response = re.getHeaders();
        //System.out.println("The response in RequestHandler is: "+response);
        return response;
    }
}
