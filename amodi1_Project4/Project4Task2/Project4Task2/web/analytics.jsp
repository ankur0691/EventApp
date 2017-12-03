<%-- 
    Document   : analytics
    Created on : Nov 14, 2017, 12:32:48 AM
    Author     : ankur
--%>

<%@page import="com.mongodb.client.MongoCursor"%>
<%@page import="org.bson.Document"%>
<%@page import="com.mongodb.client.MongoCollection"%>
<%@page import="com.mongodb.client.MongoDatabase"%>
<%@page import="com.mongodb.MongoClient"%>
<%@page import="com.mongodb.MongoClientURI"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title> EVENTS QUERY ANALYTICS </title>
    </head>
    <body>
        <h2> EVENTS QUERY ANALYTICS </h2>
        <div>
        <TABLE cellpadding="15" border="1" style="background-color:#080808; color: #ffffff;">
            <h3> ANALYTICS: </h3>
            <TR>
                <TD> Average Latency (in milliseconds) </TD>
                <TD> Most Searched Location </TD>
                <TD> Most Popular Request Device </TD>
            </TR>
            <TR>
                <TD> <%= request.getAttribute("Latency")%> </TD>
                <TD> <%= request.getAttribute("MostSearchedLocation")%> </TD>
                <TD> <%= request.getAttribute("MostPopularDevice")%> </TD>
            </TR>
            </div>

        <div>
        </TABLE>
        <TABLE cellpadding="15" border="1" style="background-color:#080808; color: #f7ec16;" >    
        <h3> LOGS: </h3>
        <% MongoClientURI uri = new MongoClientURI("mongodb://ankur:ankur91@ds159235.mlab.com:59235/eventdb1");
           MongoClient mongo = new MongoClient(uri);
           System.out.println("Connected to MongoDB!");
           MongoDatabase db = mongo.getDatabase("eventdb1");
           MongoCollection<Document> col = db.getCollection("logs");
           MongoCursor<Document> cursorDoc = col.find().iterator();%>
           <TR>
           <TD>Mobile Type</TD>
           <TD>Request Timestamp</TD>
           <TD>API Request Parameters</TD>
           <TD>Total Matches</TD>
           <TD>Top 10 Events</TD>
           <TD>Response Timestamp</TD>
           </TR>
           
         <%  while (cursorDoc.hasNext()) {
           Document doc = cursorDoc.next();%>
           <TR>
           <TD><%=doc.getString("MobileType")%></TD>
           <TD><%=doc.getString("RequestTimestamp")%></TD>
           <TD><%=doc.getString("APIRequest")%></TD>
           <TD><%=doc.getString("TotalMatches")%></TD>
           <TD><%=doc.getString("AndroidResponse")%></TD>
           <TD><%=doc.getString("ResponseTimestamp")%></TD>
           </TR>
        <% } %>
        </TABLE>
        </div>
    </body>
</html>
