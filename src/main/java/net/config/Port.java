package net.config;

import javax.xml.bind.annotation.*;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public final class Port
{
    @XmlValue
    private int number;

    @XmlAttribute
    private boolean forwarded;

    public Port() {}

    public Port(int value) { this.number = value; }

    public void setNumber(int number) { this.number = number; }

    public void setForwarded(boolean forwarded) { this.forwarded = forwarded; }

    public int getNumber() { return number; }

    public boolean getForwarded() { return forwarded; }
}
