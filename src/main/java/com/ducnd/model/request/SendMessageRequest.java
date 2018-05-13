package com.ducnd.model.request;

/**
 * Created by Lap trinh on 3/28/2018.
 */
public class SendMessageRequest {
    private Integer receiverId;
    private String content;

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
