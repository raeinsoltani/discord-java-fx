package org.ce.ap.discord.common.entity.business;

import org.ce.ap.discord.common.entity.business.discord.Role;
import org.ce.ap.discord.common.entity.business.enumeration.UserStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Parham Ahmady
 * @since 30/5/2022
 */
public class Person implements Serializable {
    private final String id; //equals username
    private final String name;
    private final String email;
    private final String phoneNumber;
    private final List<String> serverIdList;
    private final List<String> friendRequests;
    private final List<String> blocked;
    private UserStatus userStatus;
    private final Map<String, Role> serverRoleMap;
    private final Map<String, List<String>> serverChannelMap;
    private String password;
    private byte[] photo;

    public Person(String id, String name, String password, String email, String phoneNumber, byte[] photo) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.friendRequests = new ArrayList<>();
        this.blocked = new ArrayList<>();
        serverRoleMap = new HashMap<>();
        serverIdList = new ArrayList<>();
        serverChannelMap = new HashMap<>();
        this.photo = photo;
    }

    public Map<String, List<String>> getServerChannelMap() {
        return serverChannelMap;
    }

    public List<String> getServerIdList() {
        return serverIdList;
    }

    public String getName() {
        return name;
    }

    public Map<String, Role> getServerRoleMap() {
        return serverRoleMap;
    }

    public String getId() {
        return id;
    }

    public void addFriendRequest(String senderID) {
        this.friendRequests.add(senderID);
    }

    public void removeFriendRequest(String senderID) {
        this.friendRequests.remove(senderID);
    }

    public List<String> getFriendRequests() {
        return friendRequests;
    }

    public List<String> getBlocked() {
        return this.blocked;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    @Override
    public String toString() {
        return "\n Person INFO" + "\nName: " + name + "\npassword: ******" + "\nEmail: " + email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Person clone() throws CloneNotSupportedException {
        return (Person) super.clone();
    }
}
