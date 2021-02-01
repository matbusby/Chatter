package ca.nait.mbusby1.chatter;


public class Chat
{
    private String chateDate = "";
    private String chatMessage = "";
    private String chatSender = "";

    public String getChatDate()
    {
        return chateDate;
    }

    public void setChateDate(String chateDate)
    {
        this.chateDate = chateDate;
    }

    public String getChatMessage()
    {
        return chatMessage;
    }

    public void setChatMessage(String chatMessage)
    {
        this.chatMessage = chatMessage;
    }

    public String getChatSender()
    {
        return chatSender;
    }

    public void setChatSender(String chatSender)
    {
        this.chatSender = chatSender;
    }
}
