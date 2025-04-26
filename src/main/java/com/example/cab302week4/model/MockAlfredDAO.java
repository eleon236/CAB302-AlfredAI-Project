package com.example.cab302week4.model;

import java.util.ArrayList;
import java.util.List;

public class MockAlfredDAO implements IAlfredDAO {
    /**
     * A static list of contacts to be used as a mock database.
     */
    public static final ArrayList<Contact> contacts = new ArrayList<>();
    private static int autoIncrementedId = 0;

    public MockAlfredDAO() {
        // Add some initial contacts to the mock database
        addContact(new Contact("John", "Doe", "johndoe@example.com", "0423423423"));
        addContact(new Contact("Jane", "Doe", "janedoe@example.com", "0423423424"));
        addContact(new Contact("Jay", "Doe", "jaydoe@example.com", "0423423425"));

    }


    public void addContact(Contact contact) {
        contact.setId(autoIncrementedId);
        autoIncrementedId++;
        contacts.add(contact);
    }


    public void updateContact(Contact contact) {
        for (int i = 0; i < contacts.size(); i++) {
            if (contacts.get(i).getId() == contact.getId()) {
                contacts.set(i, contact);
                break;
            }
        }
    }

    public void deleteContact(Contact contact) {
        contacts.remove(contact);
    }


    public Contact getContact(int id) {
        for (Contact contact : contacts) {
            if (contact.getId() == id) {
                return contact;
            }
        }
        return null;
    }

    public List<Contact> getAllContacts() {
        return new ArrayList<>(contacts);
    }
}
