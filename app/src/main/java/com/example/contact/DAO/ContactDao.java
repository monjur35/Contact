package com.example.contact.DAO;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.contact.Entities.ContactModel;

import java.util.List;

@Dao
public interface ContactDao {

    @Insert
    long insertContact(ContactModel contactModel);

    @Update
    int updateContact(ContactModel contactModel);

    @Delete
    int deleteContact(ContactModel contactModel);


    @Query("select * from tbl_contact")
    LiveData<List<ContactModel>> getAllContact();

    @Query("select * from tbl_contact where id=:id ")
    LiveData<ContactModel> getContactByID(long id);



}
