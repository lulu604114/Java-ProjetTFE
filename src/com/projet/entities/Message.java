package com.projet.entities;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@Table(name = "Messages", schema = "jsf_tfe")
@NamedQueries({
        @NamedQuery(name = "Message.findAll", query = "SELECT m FROM Message m")
})
public class Message {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name = "sender")
    private String sender;

    @Basic
    @Column(name = "sendTime")
    private LocalDateTime sendTime;

    @Basic
    @Column(name = "content")
    private String content;

    @Basic
    @Column(name = "avatar")
    private String avatar;

    public Message() {
    }

    public Message(int id, String sender, LocalDateTime sendTime, String content, String avatar) {
        this.id = id;
        this.sender = sender;
        this.sendTime = sendTime;
        this.content = content;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSendTimeFromNow() {
        LocalDateTime sendTime = this.getSendTime();
        return sendTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return id == message.id &&
                Objects.equals(sender, message.sender) &&
                Objects.equals(sendTime, message.sendTime) &&
                Objects.equals(content, message.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sender, sendTime, content);
    }
}
