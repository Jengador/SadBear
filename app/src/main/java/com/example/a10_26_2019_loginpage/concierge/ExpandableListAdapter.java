package com.example.a10_26_2019_loginpage.concierge;

import com.example.a10_26_2019_loginpage.concierge.dataProcessor.*;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.a10_26_2019_loginpage.R;

import org.w3c.dom.Text;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<GarbageList>> _listDataChild;

    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<GarbageList>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {


        LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        switch(groupPosition){
            case 0:
                convertView = infalInflater.inflate(R.layout.list_view_garbager_list, null);
                final String childName = ((GarbageList)getChild(groupPosition, childPosition)).getName();
                final String childDoorNo = String.valueOf(((GarbageList)getChild(groupPosition, childPosition)).getDoor_number());
                TextView garbagerDoorNumberView = (TextView) convertView.findViewById(R.id.garbagerDoorNoView);
                TextView garbagerNameView = (TextView) convertView.findViewById(R.id.garbagerNameView);

                final Button garbageCollectButton = (Button) convertView.findViewById(R.id.garbagerUnselectButton);

                garbageCollectButton.setOnClickListener(
                        new View.OnClickListener(){
                            @Override
                            public void onClick(View v) {
                                garbageDataProcessor processData = new garbageDataProcessor();
                                processData.collectGarbage(((GarbageList)getChild(groupPosition, childPosition)).getId());

                                garbageCollectButton.setEnabled(false);
                                garbageCollectButton.setText("DONE");
                            }
                        });

                garbagerDoorNumberView.setText(childDoorNo);
                garbagerNameView.setText(childName);
                break;
            case 1:
                convertView = infalInflater.inflate(R.layout.list_view_hungry_list, null);
                TextView deliveryDate = convertView.findViewById(R.id.hungryPeopleOrderDataView);
                TextView from = convertView.findViewById(R.id.hungryPeopleFromView);
                TextView detail = convertView.findViewById(R.id.hungryPeopleRequestDetailView);
                TextView status =  convertView.findViewById(R.id.hungryPeopleFromStatus);
                final Button deliverButton = convertView.findViewById(R.id.hungryPeopleDeliverButton);
                final EditText cost = convertView.findViewById(R.id.hungryPeopleCostEdit);

                final TextView inputMessage =  convertView.findViewById(R.id.hungryPeopleInputMessage);

                final GarbageList child = ((GarbageList)getChild(groupPosition, childPosition));

                status.setText(child.getDeliveryStatus());
                deliveryDate.setText(child.getDeliveryDate());
                from.setText(child.getDeliverirName());
                detail.setText(child.getContent());

                if(child.getDeliveryStatus().equals("PENDING")){
                    deliverButton.setEnabled(false);
                    deliverButton.setText("PENDING");
                    cost.setText("" + child.getCost());
                    cost.setEnabled(false);
                }


                deliverButton.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        try{
                            child.setCost(Double.parseDouble(cost.getText().toString()));
                            child.setDeliveryStatus("PENDING");
                            inputMessage.setText("Request to deposit has been sent!");
                            cost.setEnabled(false);

                            deliveryDataProcessor processData = new deliveryDataProcessor();
                            processData.updateDeliveryCost(child);


                            deliverButton.setEnabled(false);
                            deliverButton.setText("PENDING");
                        }catch (Exception e){
                            inputMessage.setText("Please enter valid cost!");
                            return;
                        }


                    }
                });


                break;
        }


        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}