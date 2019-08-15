package com.example.achilis;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import static com.example.achilis.DeliveryActivity.SELECT_ADDRESS;
import static com.example.achilis.HomeActivity.MANAGE_ADDRESS_FRAG;
import static com.example.achilis.HomeActivity.refreshItemFrag;
import static com.example.achilis.MyAccountFragment.MANAGE_ADDRESS;
import static com.example.achilis.MyAddressActivity.refreshItem;

public class AddressesAdapter extends RecyclerView.Adapter<AddressesAdapter.ViewHolder> {

    private List<AddressesModel> addressesModelList;
    private int preSelectedPosition_SELECT_MODE;
    private int preSelectedPosition_MANGE_MODE=-1;
    private int mode;

    public AddressesAdapter(List<AddressesModel> addressesModelList, int mode) {
        this.addressesModelList = addressesModelList;
        this.mode = mode;
    }

    @NonNull
    @Override
    public AddressesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.addresses_item_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressesAdapter.ViewHolder viewHolder, int i) {
        String name = addressesModelList.get(i).getFullName();
        String address = addressesModelList.get(i).getAddress();
        String pincode = addressesModelList.get(i).getPinCode();
        boolean s = addressesModelList.get(i).isSelected();
        viewHolder.setData(name, address, pincode, s, i);
    }

    @Override
    public int getItemCount() {
        return addressesModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView fullName;
        private TextView address;
        private TextView pinCode;
        private ImageView icon;
        private LinearLayout optionContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fullName = itemView.findViewById(R.id.name_myaddress);
            address = itemView.findViewById(R.id.address_myaddress);
            pinCode = itemView.findViewById(R.id.pincode_myaddress);
            icon = itemView.findViewById(R.id.icon_view_myaddress);
            optionContainer = itemView.findViewById(R.id.option_container_addresses_item);
        }

        private void setData(String s1, String s2, String s3, boolean s, final int position) {
            fullName.setText(s1);
            address.setText(s2);
            pinCode.setText(s3);

            if (mode == SELECT_ADDRESS) {

                icon.setImageResource(R.mipmap.ic_check_24px);
                if (s) {
                    icon.setVisibility(View.VISIBLE);
                    preSelectedPosition_SELECT_MODE = position;
                } else {
                    icon.setVisibility(View.GONE);

                }

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (preSelectedPosition_SELECT_MODE != position) {

                            addressesModelList.get(position).setSelected(true);
                            addressesModelList.get(preSelectedPosition_SELECT_MODE).setSelected(false);
                            refreshItem(preSelectedPosition_SELECT_MODE, position);
                            preSelectedPosition_SELECT_MODE = position;
                        }
                    }
                });
            } else if (mode == MANAGE_ADDRESS) {
                optionContainer.setVisibility(View.GONE);
                icon.setImageResource(R.mipmap.ic_more_vert_24px);
                icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        optionContainer.setVisibility(View.VISIBLE);
                        refreshItem(preSelectedPosition_MANGE_MODE,preSelectedPosition_MANGE_MODE);
                        preSelectedPosition_MANGE_MODE = position;
                    }
                });
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        refreshItem(preSelectedPosition_MANGE_MODE,preSelectedPosition_MANGE_MODE);
                        preSelectedPosition_MANGE_MODE=-1;
                    }
                });

            }else if (mode == MANAGE_ADDRESS_FRAG) {
                optionContainer.setVisibility(View.GONE);
                icon.setImageResource(R.mipmap.ic_more_vert_24px);
                icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        optionContainer.setVisibility(View.VISIBLE);
                        refreshItemFrag(preSelectedPosition_MANGE_MODE,preSelectedPosition_MANGE_MODE);
                        preSelectedPosition_MANGE_MODE = position;
                    }
                });
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        refreshItemFrag(preSelectedPosition_MANGE_MODE,preSelectedPosition_MANGE_MODE);
                        preSelectedPosition_MANGE_MODE=-1;
                    }
                });

            }
        }
    }
}
