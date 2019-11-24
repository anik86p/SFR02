package com.tsystems.mms.evaluate.srf02;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DistanceService {

    @Autowired
    private DistanceMeasurementProvider distanceService;

    private Distance distance = null;
    private long lastUpdate = 0;

    @RequestMapping(value = "/distance", method = RequestMethod.GET)
    @ResponseBody
    public Distance getDistance() throws Exception
    {
        if (distance==null || System.currentTimeMillis() - lastUpdate > 1000)
        {
            refresh();
        }
        return distance;
    }

    private void refresh()
    {
        try {
            this.lastUpdate = System.currentTimeMillis();
            this.distance = new Distance(distanceService.getDistance(), Distance.UNIT_CM);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            // e.printStackTrace();
        }
        catch (InterruptedException e)
        {
            // TODO Auto-generated catch block
            // e.printStackTrace();
        }
    }


}
