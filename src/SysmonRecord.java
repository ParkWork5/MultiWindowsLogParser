import java.util.HashMap;

public class SysmonRecord {

    private Integer sysmonEventID;
    private String timestamp;
    private String userID;
    private HashMap<String,String> data;

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    private String hostname;

    public String getSystemTime() {
        return timestamp;
    }

    public void setSystemTime(String timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getSysmonEventID() {
        return sysmonEventID;
    }

    public void setSysmonEventID(Integer sysmonEventID) {
        this.sysmonEventID = sysmonEventID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public HashMap<String, String> getData() {
        return data;
    }

    public void setData(HashMap<String, String> data) {
        this.data = data;
    }
}
