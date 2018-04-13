package lix5.ushare;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Kevin on 1/4/2018.
 */

public class Event implements Serializable, Comparable<Event>{
    private String hostID;
    private String hostName;
    private List<String> passengers = new ArrayList<>();
    private String pickUp;
    private String dropOff;
    private String dateTime;
    private String numOfSeat;
    private String type;
    private String boyOnly;
    private String girlOnly;
    private String message;
    private String isRequest;
    private List<ChatMessage> chatMessages = new ArrayList<>();
    private String pickUpID;
    private String dropOffID;

    public Event() {

    }

    public Event(String hostID, String hostName, String pickUP, String dropOff, String dateTime, String numOfSeat, String type, String boyOnly, String girlOnly, String message, String isRequest, String pickUpID, String dropOffID) {
        this.hostID = hostID;
        this.hostName = hostName;
        this.passengers = new ArrayList<>();
        this.pickUp = pickUP;
        this.dropOff = dropOff;
        this.dateTime = dateTime;
        this.numOfSeat = numOfSeat;
        this.type = type;
        this.boyOnly = boyOnly;
        this.girlOnly = girlOnly;
        this.message = message;
        this.isRequest = isRequest;
        this.chatMessages = new ArrayList<>();
        this.pickUpID = pickUpID;
        this.dropOffID = dropOffID;
    }

    public List<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public String getHostID() {
        return hostID;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getBoyOnly() {
        return boyOnly;
    }

    public String getDropOff() {
        return dropOff;
    }

    public String getHostName() {
        return hostName;
    }

    public String getGirlOnly() {
        return girlOnly;
    }

    public String getIsRequest() {
        return isRequest;
    }

    public String getMessage() {
        return message;
    }

    public String getNumOfSeat() {
        return numOfSeat;
    }

    public void setNumOfSeat(String numOfSeat) {
        this.numOfSeat = numOfSeat;
    }

    public List<String> getPassengers() {
        return passengers;
    }

    public String getPickUp() {
        return pickUp;
    }

    public String getType() {
        return type;
    }

    public String getPickUpID() {
        return pickUpID;
    }

    public String getDropOffID() {
        return dropOffID;
    }

    public Map<String, Object> toMapEvent() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("hostID", hostID);
        result.put("hostName", hostName);
        result.put("passengers", passengers);
        result.put("pickUp", pickUp);
        result.put("dropOff", dropOff);
        result.put("dateTime", dateTime);
        result.put("numOfSeat", numOfSeat);
        result.put("type", type);
        result.put("boyOnly", boyOnly);
        result.put("girlOnly", girlOnly);
        result.put("message", message);
        result.put("isRequest", isRequest);
        result.put("chatMessages", chatMessages);
        result.put("pickUpID", pickUpID);
        result.put("dropOffID", dropOffID);
        return result;
    }

    @Override
    public int compareTo(Event event){
        Date event1DateTime = null;
        Date event2DateTime = null;
        DateFormat formatter = new SimpleDateFormat("EE, dd MMMM, HH:mm", Locale.US);
        try {
            event1DateTime = formatter.parse(this.getDateTime());
            event2DateTime = formatter.parse(event.getDateTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return event1DateTime.compareTo(event2DateTime);
    }
}
