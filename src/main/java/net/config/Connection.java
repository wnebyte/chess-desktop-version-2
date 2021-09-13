package net.config;

import javax.xml.bind.annotation.*;

@XmlType(name = "connection", propOrder = { "host", "port" })
@XmlAccessorType(XmlAccessType.FIELD)
public final class Connection
{
    @XmlElement
    private String host;

    @XmlElement
    private Port port;

    public Connection() {}

    public Connection(String host, Port port) { this.host = host; this.port = port; }

    public String getHost() { return host; }

    public Port getPort() { return port; }

    public void setHost(String host) { this.host = host; }

    public void setPort(Port port) { this.port = port; }
}