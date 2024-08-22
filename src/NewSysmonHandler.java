import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.ArrayList;
import java.util.HashMap;

public class NewSysmonHandler extends DefaultHandler {

    private HashMap<Integer, ArrayList<SysmonRecord>> sysmonRecords;
    private HashMap <String,String> dataRecords;
    private StringBuilder data = null;
    private Boolean bEventID=false;
    private Boolean bComputer=false;
    private Boolean bData=false;
    private Boolean bTimestamp=false;
    private SysmonRecord record;
    private Boolean resetTrigger=false;
    private String dataKey;
    private String dataValue;
    private Integer depthTracker=0;
    private ArrayList<SysmonRecord> runningList;
    private String timeStamp;
    private Boolean bUser=false;
    private String user;

    public NewSysmonHandler() {
        sysmonRecords = new HashMap<Integer, ArrayList<SysmonRecord>>();
    }
    public HashMap<Integer, ArrayList<SysmonRecord>> GetParsedEvents()
    {
        return sysmonRecords;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
        depthTracker++;

        if(qName.equals("Event")) // Checks if new sysmon event is starting to be parsed
        {
            record = new SysmonRecord();
            dataRecords = new HashMap<String,String>();
            resetTrigger=true;

        } else if (qName.equals("EventID")) {  // If the parser finds EventID in text it will grab the corresponding value
            bEventID=true;
        }
        else if (qName.equals("Computer")){
            bComputer=true;
        }
        else if (qName.equals("TimeCreated")){
            timeStamp = attributes.getValue("SystemTime");
            bTimestamp=true;
        }
        else if (qName.equals("Data")){
            dataKey= attributes.getValue("Name"); // This allows to me grab the Name value in the data sections
            bData=true;
        } else if (qName.equals("Security")) {
            user = attributes.getValue("UserID");
            bUser=true;
        }
        data = new StringBuilder(); // Not sure what this does. Guide told me to put it here
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        depthTracker--;
        if(bEventID)
        {
            bEventID=false;
            record.setSysmonEventID(Integer.valueOf(data.toString()));
        } else if (bComputer) {
            record.setHostname(data.toString());
            bComputer=false;
        } else if (bData) {
            dataValue = data.toString();
            dataRecords.put(dataKey,dataValue);
            bData=false;

        } else if (bTimestamp) {
            record.setSystemTime(timeStamp);
            bTimestamp=false;
        } else if (bUser) {
            record.setUserID(user);
            bUser=true;
        }
        if(depthTracker == 1) //Determines when the parsing of an event is about to conclude and creates a record of it
        {
            if(sysmonRecords.containsKey(record.getSysmonEventID()))
            {
                record.setData(dataRecords);
                runningList = sysmonRecords.get(record.getSysmonEventID()); // Pulls Arraylist for data record of the current sysmon event, appends new data record, and saves it back to the sysmon record
                runningList.add(record);
                sysmonRecords.put(record.getSysmonEventID(),runningList);
            }
            else
            {
                record.setData(dataRecords);
                runningList = new ArrayList<SysmonRecord>(); //  Creates a new arraylist if one does not exist for the sysmon event
                runningList.add(record);
                sysmonRecords.put(record.getSysmonEventID(),runningList);
            }
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        data.append(new String(ch, start, length)); // Not sure what this does
    }
}
