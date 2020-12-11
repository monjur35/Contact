package com.example.contact.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.contact.Adapter.ContactAdapter;
import com.example.contact.Entities.ContactModel;
import com.example.contact.R;
import com.example.contact.ViewModel.ContactViewModel;
import com.example.contact.databinding.FragmentContactDetailsBinding;

import java.util.List;


public class ContactDetailsFragment extends Fragment {
    private FragmentContactDetailsBinding binding;
    private ContactViewModel contactViewModel;
    private long id=0;


    public ContactDetailsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentContactDetailsBinding.inflate(inflater);
        contactViewModel=new ViewModelProvider(requireActivity()).get(ContactViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Bundle bundle=getArguments();
        if (bundle!=null){
            id=bundle.getLong("id");
            contactViewModel.getContactById(id).observe(getViewLifecycleOwner(), new Observer<ContactModel>() {
                @Override
                public void onChanged(ContactModel contactModel) {
                    binding.nameDetailsId.setText(contactModel.getName());
                    binding.phoneNumberID.setText(contactModel.getPhone());
                    binding.emailDetailsID.setText(contactModel.getEmail());
                    binding.addressDetailsID.setText(contactModel.getAddress());
                    binding.cityDetailsId.setText(contactModel.getCity());
                    binding.genderDetailsId.setText(contactModel.getGender());
                    binding.bloodGruopDetails.setText(contactModel.getBloodGroup());


                }
            });

        }

    }

}