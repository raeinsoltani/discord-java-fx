package org.ce.ap.discord.server.business.discord;

import org.ce.ap.discord.common.entity.business.Person;
import org.ce.ap.discord.common.entity.business.discord.PrivateChat;

import java.util.List;

/**
 * @author Arian Qazvini
 * @since 6/27/2022
 */
public interface UserManagementService {

    Person updatePerson(String id);

    void changePassword(String personID, String newPassword);

    void changePhoto(String personID, byte[] newPhoto);

    List<Person> getFriendList(String personID);

    List<String> getBlockedList(String personID);

    List<String> getFriendRequests(String personID);

    List<PrivateChat> getChats(String personID);

    List<String> getServersList(String personID);
}
