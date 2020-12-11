package com.example.contact.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.contact.Adapter.ContactAdapter;
import com.example.contact.Entities.ContactModel;
import com.example.contact.R;
import com.example.contact.ViewModel.ContactViewModel;
import com.example.contact.databinding.FragmentContactListBinding;

import java.util.List;


public class ContactListFragment extends Fragment implements ContactAdapter.ContactEditDeleteListener{

    private FragmentContactListBinding binding;
    private ContactAdapter adapter;
    private ContactViewModel contactViewModel;

    public ContactListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding= FragmentContactListBinding.inflate(inflater);
        contactViewModel=new ViewModelProvider(getActivity()).get(ContactViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        contactViewModel.getMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<ContactModel>>() {
            @Override
            public void onChanged(List<ContactModel> contactModels) {
                if (contactModels.size()==0){
                    binding.emptyListmsg.setVisibility(View.VISIBLE);
                }
                else {
                    binding.emptyListmsg.setVisibility(View.INVISIBLE);
                }
                adapter=new ContactAdapter(getActivity(), contactModels,ContactListFragment.this);
                final LinearLayoutManager llm =new LinearLayoutManager(getActivity());
                binding.contactRV.setLayoutManager(llm);
                binding.contactRV.setAdapter(adapter);
            }
        });


       binding.fab.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Navigation.findNavController(view).navigate(R.id.action_contactListFragment_to_addContactFragment);
           }
       });
    }

    @Override
    public void edit(ContactModel contactModel) {

    }

    public void delete(ContactModel contactModel){

        contactViewModel.deleteContact(contactModel);
    }
}