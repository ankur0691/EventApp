
package event;
import java.sql.*;
/**
 * This is a POJO class that defines few parameters that we will be storing in MongoDB.
 * @author ankur
 */
public class LogEntry {
    private String requestTimestamp;
    private String responseTimestamp;
    private String mobileType;
    private String searchRequest;
    private String APIRequest;
    private String AndroidResponse;
    private String totalMatches;

    public String getTotalMatches() {
        return totalMatches;
    }

    public void setTotalMatches(String totalMatches) {
        this.totalMatches = totalMatches;
    }
    
    public String getRequestTimestamp() {
        return requestTimestamp;
    }

    public void setRequestTimestamp(String requestTimestamp) {
        this.requestTimestamp = requestTimestamp;
    }

    public String getResponseTimestamp() {
        return responseTimestamp;
    }

    public void setResponseTimestamp(String responseTimestamp) {
        this.responseTimestamp = responseTimestamp;
    }

    public String getMobileType() {
        return mobileType;
    }

    public void setMobileType(String mobileType) {
        this.mobileType = mobileType;
    }

    public String getSearchRequest() {
        return searchRequest;
    }

    public void setSearchRequest(String searchRequest) {
        this.searchRequest = searchRequest;
    }

    public String getAPIRequest() {
        return APIRequest;
    }

    public void setAPIRequest(String APIRequest) {
        this.APIRequest = APIRequest;
    }

    public String getAndroidResponse() {
        return AndroidResponse;
    }

    public void setAndroidResponse(String AndroidResponse) {
        this.AndroidResponse = AndroidResponse;
    }
    
}
