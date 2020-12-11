package com.example.contact.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contact.Entities.ContactModel;
import com.example.contact.R;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private Context context;
    private List<ContactModel> contactModelList;
    private ContactEditDeleteListener listener;
    private final String TAG="Checked";


    public ContactAdapter(Context context, List<ContactModel> contactModelList, Fragment fragment) {
        this.context = context;
        this.contactModelList = contactModelList;
        listener= (ContactEditDeleteListener) fragment;

    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.e(TAG,"successful in onCreateViewHolder ");
        View itemView = LayoutInflater.from(context).inflate(R.layout.contact_row,parent,false);
        return new ContactViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.nameTV.setText(contactModelList.get(position).getName());
        holder.callTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Uri phoneNumber=Uri.parse("tel:"+contactModelList.get(position).getPhone());

                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + phoneNumber));
                if (callIntent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(callIntent);
                }

            }
        });
        holder.smsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Uri phonenumber=Uri.parse("smsto:"+contactModelList.get(position).getPhone());
                Intent smsintent = new Intent(Intent.ACTION_SEND);
                smsintent.setData(Uri.parse("smsto:" + phonenumber));


                if (smsintent.resolveActivity(context.getPackageManager()) != null) {
                   context.startActivity(smsintent);
                }

            }
        });
        holder.emailTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Uri email=Uri.parse("mailto:"+contactModelList.get(position).getEmail());
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"+email));
                emailIntent.putExtra(Intent.EXTRA_EMAIL, email);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");


            }
        });
        holder.menuTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu popupMenu=new PopupMenu(context,view);
                popupMenu.getMenuInflater().inflate(R.menu.contact_menu,popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.edit_menuID:
                                final Bundle bundle=new Bundle();
                                bundle.putLong("id",contactModelList.get(position).getId());
                                Navigation.findNavController(view).navigate(R.id.action_contactListFragment_to_addContactFragment,bundle);
                                break;
                            case R.id.delete_menuID:
                                showAlertDialog(position);
                                break;
                        }
                        return false;
                    }
                });

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Bundle bundle=new Bundle();
                bundle.putLong("id",contactModelList.get(position).getId());
                Navigation.findNavController(view).navigate(R.id.action_contactListFragment_to_contactDetailsFragment,bundle);
            }
        });



    }

    private void showAlertDialog(int position) {
        final AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("Delete "+contactModelList.get(position).getName()+"?");
        builder.setIcon(R.drawable.ic_baseline_delete_forever_24);
        builder.setMessage("Are you sure to delete this contact?");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.delete(contactModelList.get(position));

            }
        });
        builder.setNegativeButton("Cancel",null);
        final AlertDialog dialog=builder.create();
        dialog.show();

    }


    @Override
    public int getItemCount() {
        return contactModelList.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView nameTV,menuTV;
        ImageView profileImageTV,callTV,smsTV,emailTV;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTV=itemView.findViewById(R.id.nameID);
            profileImageTV=itemView.findViewById(R.id.profileImageId);
            callTV=itemView.findViewById(R.id.calliD);
            smsTV=itemView.findViewById(R.id.smsID);
            emailTV=itemView.findViewById(R.id.emailID);
            menuTV=itemView.findViewById(R.id.optionMenuId);
        }
    }


    public interface ContactEditDeleteListener{
        void edit(ContactModel contactModel);
        void delete(ContactModel contactModel);
    }
}
