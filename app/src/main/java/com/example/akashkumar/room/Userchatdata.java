package com.example.akashkumar.room;

public class Userchatdata implements Comparable<Userchatdata> {

    String text;
    int id1,chatsize;

    public Userchatdata() {
    }

    public Userchatdata(String text, int id1, int chatsize) {
        this.text = text;
        this.id1 = id1;
        this.chatsize = chatsize;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId1() {
        return id1;
    }

    public void setId1(int id1) {
        this.id1 = id1;
    }

    public int getChatsize() {
        return chatsize;
    }

    public void setChatsize(int chatsize) {
        this.chatsize = chatsize;
    }


    @Override
    public int compareTo(Userchatdata o) {
        if(chatsize==o.chatsize)
            return 0;
        else if(chatsize>o.chatsize)
            return 1;
        else
            return -1;
    }
}
