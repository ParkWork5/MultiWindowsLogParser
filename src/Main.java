import java.util.ArrayList;
import java.util.HashMap;

public class Main {


    public static void main(String[] args) {


        String sysmonFilePath=""; // Path to Sysmon xml file produced by CAPE
        String sysmonBasePath=""; // Where you want the parsed Sysmon files to go to
        HashMap<Integer, ArrayList<SysmonRecord>> parsedSysmon;
        FileOperations fileOps = new FileOperations();
        Object[] eventTypes;
        String formattedText,textSysmonID;
        parsedSysmon = fileOps.parseXML(sysmonFilePath);

        eventTypes = parsedSysmon.keySet().toArray();

        for(int i=0; i< eventTypes.length;i++)
        {
            textSysmonID = fileOps.SysmonEventMapper( Integer.valueOf(eventTypes[i].toString()));
            formattedText = fileOps.FormatFile(parsedSysmon.get(eventTypes[i]));
            fileOps.WriteFile(formattedText,sysmonBasePath,eventTypes[i].toString(),textSysmonID);
        }

    }
}