package com.tsystems.mms.evaluate.srf02;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
public class Srf02Connector implements DistanceMeasurementProvider
{
    private String srfVersion = null;
    private static final int timeout = 100;
    private static final int BAUD = 19200;
    DistanceMeasurementConfiguration x;
    private SerialPort port;

    public Srf02Connector(String comPort) throws Exception
    {
        try

        {

            CommPortIdentifier commPortIdentifier =
                    CommPortIdentifier.getPortIdentifier(comPort);
            CommPort comport = commPortIdentifier.open(Srf02Connector.class.getSimpleName(), timeout);

            if( comport instanceof SerialPort )
            {
                ((SerialPort) comport).setSerialPortParams(BAUD, SerialPort.DATABITS_8, SerialPort.STOPBITS_2, SerialPort.PARITY_NONE);
                this.port = (SerialPort) comport;
            }

        }
        catch (Exception e)
        {
            SRFLogger.showMessage(SRFLogger.ERROR_MSG, "SRF02 Initialization failed...");
            throw new Exception("Cannot initialize SRF02 Connector: " + (e.getClass().getName()) + " :: " + e.getMessage());
        }
    }

    public String getVersion() throws IOException, InterruptedException
    {
        if (this.srfVersion != null) return this.srfVersion;
        OutputStream os;
        InputStream is;
        os = port.getOutputStream();
        is = port.getInputStream();
        final byte[] cmd = {0x55,(byte)0xE1,(byte)0x00,(byte)0x01};
        os.write(cmd);
        os.flush();
        try
        {
            Thread.sleep(700);
        }
        catch (InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
        int result =is.read();
        this.srfVersion = result + "";
        os.close();
        is.close();
        SRFLogger.showMessage(SRFLogger.SUCCESS, "Softwere version ist: "+result);
        return null;
    }


    @Override
    public double getDistance() throws IOException, InterruptedException
    {

        final byte addreW=(byte)0xE0;
        final byte addreR=(byte)0xE1;
        final byte  regiR2=(byte)0x02;
        final byte  regiW=(byte)0x00;
        final byte   data=(byte)0x51;
        byte resW=writeRegister(addreW,regiW,data);
        byte readV=readRegister(addreR,regiR2);
        int result=(readV & 0xFF);
        getVersion();
        return (double)result;
    }

    // ================================================================================================================

    private byte readRegister(byte address, byte register) throws IOException
    {
        OutputStream os;
        InputStream is;
        os = port.getOutputStream();
        is = port.getInputStream();
        final byte[] cmd = {0x55, address, register, 0x02};
        os.write(cmd);
        os.flush();
        try
        {
            Thread.sleep(1500);
        }
        catch (InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
        int result =is.read();

        result = is.read();  // receive high byte (overwrites previous reading)
        result = result << 8;    // shift high byte to be high 8 bits
        result |= is.read();
        os.close();
        is.close();
        is=null;
        os=null;
        return (byte) (result & 0x00FF);
    }

    private byte writeRegister(byte address, byte register, byte data) throws IOException
    {
        OutputStream os;
        InputStream is;
        os = port.getOutputStream();
        is = port.getInputStream();
        byte[] cmd = {(byte) 0x55, address, (byte) register, (byte) 0x01, data};
        os.write(cmd);
        try
        {
            Thread.sleep(650);

        } catch (InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
        int result2=0;
        os.close();
        is.close();
        //System.out.println("Write Data is: "+result2);
        return (byte) (result2 & 0x00FF);
    }

    void close() {
        if(this.port!=null)
        {
            this.port.close();
        }
    }

}
