package org.ce.ap.discord.server.business.discord.impl;

import org.ce.ap.discord.common.boot.InitializingBean;
import org.ce.ap.discord.common.entity.boot.ApplicationContext;
import org.ce.ap.discord.common.entity.business.Person;
import org.ce.ap.discord.common.entity.business.ServerInformation;
import org.ce.ap.discord.common.entity.business.discord.PrivateChat;
import org.ce.ap.discord.server.business.discord.UserManagementService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Arian Qazvini
 * @since 6/27/2022
 */
public class UserManagementServiceImpl implements UserManagementService, InitializingBean {
    private ServerInformation serverInformation;

    @Override
    public void afterPropertiesSet(ApplicationContext context) {
        serverInformation = (ServerInformation) context.getApplicationBeans().get(ServerInformation.class);
    }

    @Override
    public Person updatePerson(String id) {
        return serverInformation.getPersons().get(id);
    }

    @Override
    public void changePassword(String personID, String newPassword) {
        Person person = serverInformation.getPersons().get(personID);
        person.setPassword(newPassword);
    }

    @Override
    public void changePhoto(String personID, byte[] newPhoto) {
        Person person = serverInformation.getPersons().get(personID);
        person.setPhoto(newPhoto);
    }

    @Override
    public List<Person> getFriendList(String personID) {
        ArrayList<Person> temp = new ArrayList<>();
        for (Map.Entry<String, PrivateChat> entry : serverInformation.getChats().entrySet()) {
            if (entry.getKey().contains(personID)) {
                PrivateChat pchat = entry.getValue();
                if (pchat.getPerson1().getId().equals(personID)) {
                    temp.add(pchat.getPerson2());
                } else {
                    temp.add(pchat.getPerson1());
                }
            }
        }
        return temp;
    }

    @Override
    public List<String> getBlockedList(String personID) {
        Person person = serverInformation.getPersons().get(personID);
        return person.getBlocked();
    }

    @Override
    public List<String> getFriendRequests(String personID) {
        Person person = serverInformation.getPersons().get(personID);
        return person.getFriendRequests();
    }

    @Override
    public List<PrivateChat> getChats(String personID) {
        ArrayList<PrivateChat> temp = new ArrayList<>();
        for (Map.Entry<String, PrivateChat> entry : serverInformation.getChats().entrySet()) {
            if (entry.getKey().contains(personID)) {
                PrivateChat pchat = entry.getValue();
                temp.add(pchat);
            }
        }
        return temp;
    }

    @Override
    public List<String> getServersList(String personID) {
        Person person = serverInformation.getPersons().get(personID);
        return person.getServerIdList();
    }
}
