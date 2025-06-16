package pl.edu.wsisiz.darkavenger54;

public enum EnEventType
{
    MESSAGE("MESSAGE "), SET_NAME("SET_NAME "), DISCONNECT("DISCONNECT "), CONNECT("CONNECT "), SERVER_STOPPED("SERVER_STOPPED "),
    ASK_FOR_GAME("ASK_FOR_GAME "), CANCELED("CANCELED "), CONFIRMED("CONFIRMED "), START_GAME("START_GAME "),
    MOVE("MOVE "), YOUR_TURN("YOUR_TURN "), WAITING_FOR_OPPONENT("WAITING_FOR_OPPONENT "), WIN("WIN "), LOSE("LOSE "), DRAW("DRAW "),
    SERVER_FULL("SERVER_FULL ");
    String eventType;

    EnEventType(String eventType)
    {
        this.eventType = eventType;
    }

    public String getEventType()
    {
        return eventType;
    }
}
