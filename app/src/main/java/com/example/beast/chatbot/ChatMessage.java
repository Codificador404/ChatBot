package com.example.beast.chatbot;

public class ChatMessage {

    private String msgText;
    private String msgUser;
    private boolean isDeleted;

    public ChatMessage(String msgText, String msgUser){
        this.msgText = msgText;
        this.msgUser = msgUser;
        this.isDeleted = false;
    }

    public ChatMessage(){

    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }

    public String getMsgUser() {
        return msgUser;
    }

    public void setMsgUser(String msgUser) {
        this.msgUser = msgUser;
    }
}
