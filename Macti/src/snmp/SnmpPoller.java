package snmp;
import java.io.IOException;
import java.util.Scanner;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
public class SnmpPoller {
	
	//private static Scanner skanneri = new Scanner (System.in);
	private static String  port    = "161";
	private static int    snmpVersion  = SnmpConstants.version2c;
	private static String  community  = "public!";
	public Poller() throws IOException {
		 /* System.out.println("Give hostname: ");
		  String ipAddress = skanneri.nextLine();
		  ipAddress.trim();
		  System.out.println("Give OID: ");
		  String oid = skanneri.nextLine();
		  */	   
		  getsnmp("hostname", ".1.3.6.1.4.1.2021.11.11.0");
		  
		  
	  }
	  private static void getsnmp (String iippari, String oid) throws IOException { 
		//String oidValue  = ".1.3.6.1.4.1.2021.11.11.0";
		  String oidValue = oid;
		  String ipAddress = iippari;
	    // Create TransportMapping and Listen
	    @SuppressWarnings("rawtypes")
		TransportMapping transport = new DefaultUdpTransportMapping();
	    transport.listen();

	    // Create Target Address object
	    CommunityTarget target = new CommunityTarget();
	    target.setCommunity(new OctetString(community));
	    target.setVersion(snmpVersion);
	    target.setAddress(new UdpAddress(ipAddress + "/" + port));
	    target.setRetries(2);
	    target.setTimeout(1000);

	    // Create SNMP Protocol data unit
	    PDU pdu = new PDU();
	    pdu.add(new VariableBinding(new OID(oidValue)));
	    pdu.setType(PDU.GET);
	    pdu.setRequestID(new Integer32(1));

	    // Create Snmp object for sending data to Agent
	    Snmp snmp = new Snmp(transport);

	    System.out.println("Trying with: community: " +target.getCommunity()+ " version: "+target.getVersion()+ " destination/port: "+target.getAddress());
	    ResponseEvent response = snmp.get(pdu, target);

	    // Process Agent Response
	    if (response != null)
	    {
	      System.out.println("Got Response from Agent");
	      PDU responsePDU = response.getResponse();

	      if (responsePDU != null)
	      {
	        int errorStatus = responsePDU.getErrorStatus();
	        int errorIndex = responsePDU.getErrorIndex();
	        String errorStatusText = responsePDU.getErrorStatusText();

	        if (errorStatus == PDU.noError)
	        {
	          System.out.println("Snmp Get Response = " + responsePDU.getVariableBindings());
	        }
	        else
	        {
	          System.out.println("Error: Request Failed");
	          System.out.println("Error Status = " + errorStatus);
	          System.out.println("Error Index = " + errorIndex);
	          System.out.println("Error Status Text = " + errorStatusText);
	        }
	      }
	      else
	      {
	        System.out.println("Error: Response PDU is null");
	      }
	    }
	    else
	    {
	      System.out.println("Error: Agent Timeout... ");
	    }
	    snmp.close();
	}

}
