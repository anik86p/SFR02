package com.tsystems.mms.evaluate.srf02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({Srf02Configuration.class})
@Configuration
public class DistanceMeasurementConfiguration
{
    public static boolean isSimulation=true;
    private final Srf02Configuration srf02Configuration;
    public DistanceMeasurementConfiguration(@Autowired Srf02Configuration srf02UsbConfiguration)
    {
        this.srf02Configuration = srf02UsbConfiguration;

        srf02UsbConfiguration.Conf();
    }
    @Bean
    public DistanceMeasurementProvider getDistanceMeasurementProvider() throws Exception
    {
        if (isSimulation)
        {

            SRFLogger.showMessage(SRFLogger.INFO_MSG, "Starting in SIMULATION mode");
            return new SimulationDistanceProvider();
        }
        else
        {

            SRFLogger.showMessage(SRFLogger.INFO_MSG, "Starting in SENSOR mode");
            return new Srf02Connector("COM5");
        }
    }

}


