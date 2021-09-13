package net;

import com.google.gson.JsonSyntaxException;
import common.Request;
import common.Response;
import common.stream.RequestWriter;
import common.stream.ResponseReader;

import java.io.*;
import java.net.*;

/**
 * Client class is to be used to communicate with the server when the client requires
 * that a new socket connection needs to be made prior to communication with the server.
 */
public final class Client
{
    private final Socket socket;

    private final RequestWriter out;

    private final ResponseReader in;

    /**
     * Constructs and connects a new socket to the specified remote addr/port.
     */
    public Client(final String host, final int port) throws IOException
    {
        this.socket = new Socket(host, port);
        this.out = new RequestWriter(socket.getOutputStream());
        this.in = new ResponseReader(socket.getInputStream());
    }

    public Client(final InetAddress addr, final int port) throws IOException {
        if (addr == null) {
            throw new IllegalArgumentException(
                    "socket address must not be null."
            );
        }
        this.socket = new Socket(addr, port);
        this.out = new RequestWriter(socket.getOutputStream());
        this.in = new ResponseReader(socket.getInputStream());
    }

    public final void send(final Request request) throws IOException
    {
        if (request == null) {
            throw new IllegalArgumentException(
                    "request must not be null."
            );
        }
        out.write(request);
    }

    public final Response read() throws IOException, JsonSyntaxException
    {
        return in.read();
    }

    public final Response read(int timeout) throws IOException, JsonSyntaxException
    {
        socket.setSoTimeout(timeout);

        try {
            return in.read();
        } finally {
            socket.setSoTimeout(0);
        }
    }

    public final void close()
    {
        try
        {
            socket.close();
        }
        catch (IOException ignored) {}
    }

    public final boolean isClosed() {
        return socket.isClosed();
    }

    public final String getLocalHostAddress() {
        return socket.getLocalAddress().getHostAddress();
    }

    public final int getLocalPort() {
        return socket.getLocalPort();
    }

    public final boolean isConnected() {
        return (socket != null) && !(socket.isClosed());
    }

    public final InetAddress getRemoteSocketAddress(){
        return socket.getInetAddress();
    }

    public final int getPort() {
        return socket.getPort();
    }
}
