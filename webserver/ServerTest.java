package webserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.io.PrintWriter;

public class ServerTest {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(5000);

        System.out.println("Server started...");

        while(true) {
            Socket cs = ss.accept();
            PrintWriter out = new PrintWriter(cs.getOutputStream(), true);
            Request r = new Request(cs);

            //WORKING WITH REQUEST,RESPONSE
            //Response resp = new Response(r);

            //WORKING WITH REQUEST,REQUESTHANDLER,RESPONSE2
            RequestHandler rqh = new RequestHandler(r);


            //TODO: sendResponse(resp);
            //out.println(resp.getContent());

            out.println(rqh.respond());
            out.println(rqh.getContent());

            out.close();
            cs.close();
        }
    }
}
