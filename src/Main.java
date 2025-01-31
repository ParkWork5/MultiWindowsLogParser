import java.util.ArrayList;
import java.util.HashMap;

public class Main {


    public static void main(String[] args) {


        String inputFile=""; // Windows log file goes here
        String folderOutputs=""; // Where you want the parsed and seperated files to go
        HashMap<Integer, ArrayList<WindowsRecord>> parsedSysmon;
        FileOperations fileOps = new FileOperations();
        Object[] eventTypes;
        String formattedText,textSysmonID;
        parsedSysmon = fileOps.parseXML(inputFile);

        eventTypes = parsedSysmon.keySet().toArray();

        for(int i=0; i< eventTypes.length;i++)
        {
            textSysmonID = fileOps.SysmonEventMapper( Integer.valueOf(eventTypes[i].toString()));
            formattedText = fileOps.FormatFile(parsedSysmon.get(eventTypes[i]));
            fileOps.WriteFile(formattedText,folderOutputs,eventTypes[i].toString(),textSysmonID);
        }

    }
}