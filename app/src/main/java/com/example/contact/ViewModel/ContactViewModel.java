package com.example.contact.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.contact.DAO.ContactDao;
import com.example.contact.Entities.ContactModel;
import com.example.contact.db.ContactDatabase;
import com.example.contact.db.ContactRepository;

import java.util.List;

public class  ContactViewModel extends AndroidViewModel {
    private ContactRepository repository;
    private LiveData<List<ContactModel>> mutableLiveData;
    private ContactDatabase db;


    public ContactViewModel(@NonNull Application application) {
        super(application);
        db= ContactDatabase.getInstance(application);
        repository=new ContactRepository(db.getContactDao());
        mutableLiveData=new MutableLiveData<>();
        mutableLiveData=repository.getAllContacts();
    }

    public LiveData<List<ContactModel>> getMutableLiveData() {

        return mutableLiveData;
    }

    public void setContact(ContactModel contact){

        repository.addNewContact(contact);
    }
    public void updateContact(ContactModel contact){

        repository.updateContact(contact);
    }
    public void deleteContact(ContactModel contactModel){

        repository.delete(contactModel);
    }

    public LiveData<ContactModel> getContactById(long id) {

        return repository.getContactById(id) ;
    }

}
