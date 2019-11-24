package com.tsystems.mms.evaluate.srf02;
import java.util.Enumeration;
import gnu.io.CommPortIdentifier;
import java.util.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
@ConfigurationProperties(prefix = "srf02")
public class Srf02Configuration
{
     DistanceMeasurementConfiguration dmConfig;
    public void Conf()
    {

        Enumeration<?> ports = CommPortIdentifier.getPortIdentifiers();
        while (ports.hasMoreElements())
        {
            CommPortIdentifier port = (CommPortIdentifier) ports.nextElement();
            if(port.getPortType()==CommPortIdentifier.PORT_SERIAL
                    && "COM5".equals(port.getName().toString()))
            {
                dmConfig.isSimulation=false;
            }

        }
    }
}

