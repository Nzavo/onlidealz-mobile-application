package com.example.OnliDealz.Black;

public class MessagingMode {//id,rate,message,receiver,sender,name,phone,post,send_date,reply,reply_date
    String id = null;
    String rate = null;
    String message = null;
    String receiver = null;
    String sender = null;
    String name = null;
    String phone = null;
    String post = null;
    String send_date = null;
    String reply = null;
    String reply_date = null;

    public MessagingMode(String id, String rate, String message, String receiver, String sender, String name, String phone, String post, String send_date, String reply, String reply_date) {
        this.id = id;
        this.rate = rate;
        this.message = message;
        this.receiver = receiver;
        this.sender = sender;
        this.name = name;
        this.phone = phone;
        this.post = post;
        this.send_date = send_date;
        this.reply = reply;
        this.reply_date = reply_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getSend_date() {
        return send_date;
    }

    public void setSend_date(String send_date) {
        this.send_date = send_date;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getReply_date() {
        return reply_date;
    }

    public void setReply_date(String reply_date) {
        this.reply_date = reply_date;
    }

    public String toString() {
        return id + " " + rate + " " + message + " " + receiver + " " + sender + " " + name + " " + phone + " " + post + " " + send_date + " " + reply + " " + reply_date;
    }
}
