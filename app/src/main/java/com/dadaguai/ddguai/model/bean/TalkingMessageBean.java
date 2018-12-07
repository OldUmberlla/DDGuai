package com.dadaguai.ddguai.model.bean;

/**
 * Created by GongSS
 * Date: 2018/12/7 15:44
 * _Umbrella
 */
public class TalkingMessageBean {
    public String personMessage;
    public String myMessage;


    @Override
    public String toString() {
        return "TalkingMessageBean{" +
                "personMessage='" + personMessage + '\'' +
                ", myMessage='" + myMessage + '\'' +
                '}';
    }

    public String getPersonMessage() {
        return personMessage;
    }

    public void setPersonMessage(String personMessage) {
        this.personMessage = personMessage;
    }

    public String getMyMessage() {
        return myMessage;
    }

    public void setMyMessage(String myMessage) {
        this.myMessage = myMessage;
    }
}
