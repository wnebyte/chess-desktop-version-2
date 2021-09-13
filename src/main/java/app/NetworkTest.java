package app;

import com.google.gson.JsonObject;
import common.Request;
import common.Response;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class NetworkTest
{
    private final static String SERVER_HOST = "127.0.0.1";

    private final static int SERVER_PORT = 8282;

    public static void main(String[] args) throws Exception
    {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
        testJoin(socket);

        while (true) {

        }
    }

    /*
    OutputStreamWriter out =
                    new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
    var in = new BufferedReader(
    new InputStreamReader(new DataInputStream(socket.getInputStream()), ENCODING))
     */
    public static void test00()
    {
        try
        {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
            System.out.println("Connected to: " + socket);

            String request = new Request(new Request.Header(Request.STATUS), null).toJSON();
            var out = new BufferedWriter(new OutputStreamWriter(new DataOutputStream(socket.getOutputStream()), "UTF-8"));
            var in = new BufferedReader(new InputStreamReader(new DataInputStream(socket.getInputStream()), "UTF-8"));

            out.write(request);
            out.flush();

            String line;
            StringBuilder builder = new StringBuilder();

            while ((line = in.readLine()) != null) {
                builder.append(line);
            }

            String content = builder.toString();
            System.out.println("Read: \n" + content);
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void test01()
    {
        try
        {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
            System.out.println("Connected to: " + socket);

            var in = new DataInputStream(socket.getInputStream());
            var out = new DataOutputStream(socket.getOutputStream());

            byte b = 50;
            out.write(b);
            out.flush();

            b = in.readByte();
            System.out.println("Read: " + b);

        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    private static void test03(Socket socket)
    {
        try
        {
            var in = new DataInputStream(socket.getInputStream());
            var out = new DataOutputStream(socket.getOutputStream());

            String data = new Request(new Request.Header(Request.STATUS), null).toJSON();
            out.writeUTF(data);
            System.out.println("Wrote: " + data);

            String utf = in.readUTF();
            System.out.print("Read: " + utf);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testJoin(Socket socket)
    {
        try
        {
            var in = new DataInputStream(socket.getInputStream());
            var out = new DataOutputStream(socket.getOutputStream());

            JsonObject body = new JsonObject();
            body.addProperty("host", "localhost");
            body.addProperty("port", 3030);
            Request request = new Request(new Request.Header(Request.JOIN), body);

            out.writeUTF(request.toJSON());
            System.out.println("Wrote: " + request.toJSON());

            String utf = in.readUTF();
            System.out.println("Read: " + utf);
            Response response = Response.fromJSON(utf);

            if (response.getHeader().getResultCode() == Response.OK) {
                utf = in.readUTF();
                System.out.println("Read: " + utf);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
