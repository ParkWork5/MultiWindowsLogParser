import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FileOperations {

    private String recordFormat="";
    private WindowsRecord currentRecord;


    public FileOperations() {
    }

    public HashMap<Integer, ArrayList<WindowsRecord>> parseXML(String filePath)
    {
        File file = new File(filePath);
        RecordHandler handler = new RecordHandler();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(true);
        SAXParser saxParser = null;
        try {
            saxParser = factory.newSAXParser();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }

        try {
            saxParser.parse(file, handler);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return handler.GetParsedEvents();

    }

    public String FormatFile(ArrayList<WindowsRecord> recordsToWrite)
    {
        recordFormat="";

        for (int i=0; i < recordsToWrite.size(); i++)
        {
            currentRecord = recordsToWrite.get(i);
            recordFormat= recordFormat +"############################################################################################"+"\n";
            recordFormat = recordFormat + "System Time:" + currentRecord.getSystemTime() + "\n";
            recordFormat = recordFormat + "Event ID: " + currentRecord.getSysmonEventID() + "\n";
            recordFormat = recordFormat + "User: " + currentRecord.getUserID() + "\n";
            recordFormat = recordFormat + "Computer: " + currentRecord.getHostname() + "\n";

           for (Map.Entry<String, String> entry : currentRecord.getData().entrySet())
            {
                    recordFormat = recordFormat + entry.getKey() +": " + entry.getValue() + "\n";

            }
           recordFormat = recordFormat +"############################################################################################"+"\n";

        }


        return recordFormat;
    }

    public void WriteFile(String formattedRecord,String baseFilePath,String sysmonEventCategory,String folderName)
    {
        try {
            Files.createDirectories(Paths.get(baseFilePath+folderName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File newFile = new File(baseFilePath+"/"+folderName+"/"+sysmonEventCategory+".txt");
        try {
            RandomAccessFile stream = new RandomAccessFile(baseFilePath+"/"+folderName+"/"+sysmonEventCategory+".txt","rw");
            FileChannel channel = stream.getChannel();
            byte[] strBytes = formattedRecord.getBytes();
            ByteBuffer buffer = ByteBuffer.allocate(strBytes.length);

            buffer.put(strBytes);
            buffer.flip();
            try {
                channel.write(buffer);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
    public String SysmonEventMapper(Integer eventID)
    {

        switch (eventID){
            case 1:
                return "1-Process creation";
            case 2:
                return "2-A process changed a file creation time";
            case 3:
                return "3-Network connection";
            case 4:
                return "4-Sysmon service state changed";
            case 5:
                return "5-Process terminated";
            case 6:
                return "6-Driver loaded";
            case 7:
                return "7-Image loaded";
            case 8:
                return "8-CreateRemoteThread";
            case 9:
                return "9-RawAccessRead";
            case 10:
                return "10-ProcessAccess";
            case 11:
                return "11-FileCreate";
            case 12:
                return "12-RegistryEvent Object create and delete";
            case 13:
                return "13-RegistryEvent Value Set";
            case 14:
                return "14-RegistryEvent Key and Value Rename";
            case 15:
                return "15-FileCreateStreamHash";
            case 16:
                return "16-ServiceConfigurationChange";
            case 17:
                return "17-PipeEvent Pipe Created";
            case 18:
                return "18-PipeEvent Pipe Connected";
            case 19:
                return "19-WmiEvent WmiEventFilter activity detected";
            case 20:
                return "20-WmiEvent WmiEventConsumer activity detected";
            case 21:
                return "21-WmiEvent WmiEventConsumerToFilter activity detected";
            case 22:
                return "22-DNSEvent DNS query";
            case 23:
                return "23-FileDelete File Delete archived";
            case 24:
                return "24-ClipboardChange New content in the clipboard";
            case 25:
                return "25-ProcessTampering Process image change";
            case 26:
                return "26-FileDeleteDetected File Delete logged";
            case 27:
                return "27-FileBlockExecutable";
            case 28:
                return "28-FileBlockShredding";
            case 29:
                return "29-FileExecutableDetected";
            case 225:
                return "225-Sysmon Error";
            case 4103:
                return "4103-PowerShell Module";
            case 4104:
                return "4104-PowerShell Script Block";


        }
     return eventID.toString();
    }
}
