package com.tsystems.mms.evaluate.srf02;
import java.util.Enumeration;
import gnu.io.CommPortIdentifier;
import java.util.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
@ConfigurationProperties(prefix = "srf02")
public class Srf02Configuration
{
     DistanceMeasurementConfiguration x;
    public void Conf()
    {

        Enumeration<?> ports = CommPortIdentifier.getPortIdentifiers();
        while (ports.hasMoreElements())
        {
            CommPortIdentifier port = (CommPortIdentifier) ports.nextElement();
            String P=port.getName();
            String T="COM5";
            P=P.toString();
            if(port.getPortType()==CommPortIdentifier.PORT_SERIAL && T.equals(P))
            {
                x.isSimulation=false;
            }
            else
            {
                x.isSimulation = x.isSimulation;
            }

        }
    }
}

