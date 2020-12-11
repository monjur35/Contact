package com.example.contact.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.contact.Entities.ContactModel;
import com.example.contact.R;
import com.example.contact.ViewModel.ContactViewModel;
import com.example.contact.databinding.FragmentAddContactBinding;

import java.util.Arrays;
import java.util.List;


public class addContactFragment extends Fragment {

    private FragmentAddContactBinding binding;
    private String gender="Male";
    private String city;
    public static String[] citise={"Dhaka","Chittagong","Comilla","Noakhali","Rajshahi"};
    public static List<String> citise2= Arrays.asList("Dhaka","Chittagong","Comilla","Noakhali","Rajshahi");
    public static List<String> bloodGroups= Arrays.asList("A+","A-","B+","B-","AB+","AB-","O+","O-");
    private String bloodGroup;

    private ContactViewModel contactViewModel;
    private long id=0;

    public addContactFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding=FragmentAddContactBinding.inflate(inflater);
        contactViewModel=new ViewModelProvider(getActivity()).get(ContactViewModel.class);
        return binding.getRoot();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final Bundle bundle=getArguments();
        if (bundle!=null){
            id=bundle.getLong("id");
            contactViewModel.getContactById(id).observe(getViewLifecycleOwner(), new Observer<ContactModel>() {
                @Override
                public void onChanged(ContactModel contactModel) {
                    setInfoIntoViews(contactModel);

                }
            });

        }
        super.onViewCreated(view, savedInstanceState);
        final ArrayAdapter<String> cityAdapter=new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item,citise2);
        binding.citySP.setAdapter(cityAdapter);

        final ArrayAdapter<String> bloodGroupAdapter=new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item,bloodGroups);
        binding.bloodgroupSP.setAdapter(bloodGroupAdapter);




        binding.genderRGID.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                final RadioButton rb=radioGroup.findViewById(i);
                gender=rb.getText().toString();
            }
        });




        binding.citySP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                city= citise[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        binding.bloodgroupSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bloodGroup=bloodGroups.get(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });





        binding.saveBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData(view);


            }
        });




        binding.updateBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData(view);
            }
        });
    }




    private void updateData(View view) {
        final String name=binding.addContactNameID.getText().toString();
        final String phone=binding.addContactphoneId.getText().toString();
        final String email=binding.addEmailID.getText().toString();
        final String streetAddress=binding.addStreetAddressID.getText().toString();

        if (name.isEmpty()||phone.isEmpty()||email.isEmpty()||streetAddress.isEmpty()){
            Toast.makeText(getActivity(),"All field must be filled",Toast.LENGTH_SHORT).show();
        }
        else {
            final ContactModel contactModel=new ContactModel(name,phone,email,streetAddress,city,gender,bloodGroup);
            contactModel.setId(id);
            contactViewModel.updateContact(contactModel);
            Navigation.findNavController(view).navigate(R.id.action_addContactFragment_to_contactListFragment);
        }
    }


    private void setInfoIntoViews(ContactModel contactModel) {
        binding.saveBtnId.setVisibility(View.GONE);
        binding.updateBtnId.setVisibility(View.VISIBLE);
        binding.addContactNameID.setText(contactModel.getName());
        binding.addContactphoneId.setText(contactModel.getPhone());
        binding.addEmailID.setText(contactModel.getEmail());
        binding.addStreetAddressID.setText(contactModel.getAddress());
        gender=contactModel.getGender();
        if (gender.equals("Female")){
            binding.femaleRBID.setChecked(true);
        }
        city =contactModel.getCity();
        final int position =citise2.indexOf(city);
        binding.citySP.setSelection(position);

        binding.bloodgroupSP.setSelection(bloodGroups.indexOf(contactModel.getBloodGroup()));
    }




    private void saveData(View view) {
        final String name=binding.addContactNameID.getText().toString();
        final String phone=binding.addContactphoneId.getText().toString();
        final String email=binding.addEmailID.getText().toString();
        final String streetAddress=binding.addStreetAddressID.getText().toString();

        if (name.isEmpty()||phone.isEmpty()||email.isEmpty()||streetAddress.isEmpty()){
            Toast.makeText(getActivity(),"All field must be filled",Toast.LENGTH_SHORT).show();
        }
        else {
            final ContactModel contactModel=new ContactModel(name,phone,email,streetAddress,city,gender,bloodGroup);
            contactViewModel.setContact(contactModel);
            Navigation.findNavController(view).navigate(R.id.action_addContactFragment_to_contactListFragment);
        }
    }


}