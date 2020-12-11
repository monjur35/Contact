package com.example.contact.db;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.contact.DAO.ContactDao;
import com.example.contact.Entities.ContactModel;

import java.util.ArrayList;
import java.util.List;

public class ContactRepository {
    public LiveData<List<ContactModel>> contactModelList;
    private ContactDao contactDao;


    public ContactRepository(ContactDao contactDao){
        this.contactDao=contactDao;
        contactModelList=new MutableLiveData<>();
        contactModelList=contactDao.getAllContact();


    }

    public void addNewContact(ContactModel contactModel){
        ContactDatabase.dbExecutor.execute(() -> {
            contactDao.insertContact(contactModel);
        });


    }

    public LiveData<List<ContactModel>> getAllContacts(){

        return contactModelList;
    }


    public LiveData<ContactModel> getContactById(long id){

        return contactDao.getContactByID(id);
    }


    public void delete(ContactModel contactModel){
        ContactDatabase.dbExecutor.execute(() -> {
            contactDao.deleteContact(contactModel);
        });


    }

    public void updateContact(ContactModel contactModel){
        ContactDatabase.dbExecutor.execute(() ->{
            contactDao.updateContact(contactModel);
                });


    }

}
