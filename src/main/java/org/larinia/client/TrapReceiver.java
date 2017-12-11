package org.larinia.client;

/**
 * Created by lyu on 11/05/17.
 */
import org.snmp4j.*;
import org.snmp4j.log.LogFactory;
import org.snmp4j.mp.MPv1;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.mp.StateReference;
import org.snmp4j.mp.StatusInformation;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.TcpAddress;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.tools.console.SnmpRequest;
import org.snmp4j.transport.DefaultTcpTransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.MultiThreadedMessageDispatcher;
import org.snmp4j.util.ThreadPool;


import java.io.IOException;

public class TrapReceiver implements CommandResponder
{
    public TrapReceiver()
    {
    }

    public static void main(String[] args)
    {
        TrapReceiver snmp4jTrapReceiver = new TrapReceiver();
        try
        {
            snmp4jTrapReceiver.listen("udp:0.0.0.0/161");
        }
        catch (IOException e)
        {
            System.err.println("Error in Listening for Trap");
            e.printStackTrace();
            System.err.println("Exception Message = " + e.getMessage());
        }
    }

    /**
     * This method will listen for traps and response pdu's from SNMP agent.
     */
    public synchronized void listen(String listenaddress) throws IOException
    {
        Address address = GenericAddress.parse(listenaddress);
        TransportMapping transport;
        if (address instanceof TcpAddress)
        {
            transport = new DefaultTcpTransportMapping((TcpAddress) address);
        }
        else
        {
            System.out.println("udp type address");
            transport = new DefaultUdpTransportMapping((UdpAddress) address);
        }

        ThreadPool threadPool = ThreadPool.create("SNMP4J-TrapReceiver["+this.toString()+"]", 4);
        MessageDispatcher mtDispatcher = new MultiThreadedMessageDispatcher(threadPool, new MessageDispatcherImpl());

        // add message processing models
        mtDispatcher.addMessageProcessingModel(new MPv1());
        mtDispatcher.addMessageProcessingModel(new MPv2c());

        // add all security protocols
        SecurityProtocols.getInstance().addDefaultProtocols();
        //SecurityProtocols.getInstance().addPrivacyProtocol(new Priv3DES());

        //Create Target
    /*CommunityTarget target = new CommunityTarget();
    target.setCommunity( new OctetString("oadadm"));*/

        Snmp snmp = new Snmp(mtDispatcher, transport);
        snmp.addCommandResponder(this);

        snmp.listen();
        System.out.println("Listening on " + address);

        try
        {
            this.wait();
            System.out.println("waiting till now");
        }
        catch (InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * This method will be called whenever a pdu is received on the given port specified in the listen() method
     */
    public synchronized void processPdu(CommandResponderEvent cmdRespEvent)
    {
        System.out.println("Received PDU...");
        PDU pdu = cmdRespEvent.getPDU();
        if (pdu != null)
        {

            System.out.println("Trap Type = " + pdu.getType());
            System.out.println("Variable Bindings = " + pdu.getVariableBindings());

            int pduType = pdu.getType();
            if ((pduType != PDU.TRAP) && (pduType != PDU.V1TRAP) && (pduType != PDU.REPORT)
                    && (pduType != PDU.RESPONSE))
            {
                pdu.setErrorIndex(0);
                pdu.setErrorStatus(0);
                pdu.setType(PDU.RESPONSE);
                StatusInformation statusInformation = new StatusInformation();
                StateReference ref = cmdRespEvent.getStateReference();
                try
                {
                    System.out.println(cmdRespEvent.getPDU());
                    cmdRespEvent.getMessageDispatcher().returnResponsePdu(cmdRespEvent.getMessageProcessingModel(),
                            cmdRespEvent.getSecurityModel(), cmdRespEvent.getSecurityName(), cmdRespEvent.getSecurityLevel(),
                            pdu, cmdRespEvent.getMaxSizeResponsePDU(), ref, statusInformation);
                }
                catch (MessageException ex)
                {
                    System.err.println("Error while sending response: " + ex.getMessage());
                    LogFactory.getLogger(SnmpRequest.class).error(ex);
                }
            }
        }
    }
}
