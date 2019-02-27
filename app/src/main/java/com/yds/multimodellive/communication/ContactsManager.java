package com.yds.multimodellive.communication;

import java.util.List;

public class ContactsManager {
    private List<Contact> contacts;

    public ContactsManager() {
        update();
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void update(){
        UserFinder finder = new UserFinder();
        contacts = finder.findLocalUser();
    }
}
