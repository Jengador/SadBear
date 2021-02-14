package com.example.a10_26_2019_loginpage.manager;

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
import com.example.a10_26_2019_loginpage.concierge.GarbageList;
import com.example.a10_26_2019_loginpage.concierge.dataProcessor.garbageDataProcessor;
import com.example.a10_26_2019_loginpage.manager.managerDataUpdate.UpdateBalance;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<Balance>> _listDataChild;

    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<Balance>> listChildData) {
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

        final String childName = ((Balance)getChild(groupPosition, childPosition)).getName();
        final String childDoorNo = String.valueOf(((Balance)getChild(groupPosition, childPosition)).getDoor_number());
        final String childBalance = String.valueOf(((Balance)getChild(groupPosition, childPosition)).getBalance_amount());
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_view_balance, null);
        }

        final Balance child = (Balance)getChild(groupPosition, childPosition);

        TextView balanceNameView = (TextView) convertView
                .findViewById(R.id.balanceNameView);

        TextView balanceDoorNumberView = (TextView) convertView.findViewById(R.id.balanceDoorNoView);
        final EditText balanceAmountView = (EditText) convertView.findViewById(R.id.balanceAmount);
        final Button balanceUpdateButton = (Button) convertView.findViewById(R.id.balanceUnselectButton);

        balanceUpdateButton.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        UpdateBalance updateBalance = new UpdateBalance();
                        double value = Double.parseDouble(String.valueOf(((Balance)getChild(groupPosition, childPosition)).getBalance_amount()));
                        try{
                            double deposit = Double.parseDouble(balanceAmountView.getText().toString());
                            updateBalance.updateBalance(((Balance)getChild(groupPosition, childPosition)).getId(),deposit);
                            balanceUpdateButton.setEnabled(false);
                            balanceUpdateButton.setText("UPDATED");
                            child.setStatus("UPDATED");
                            balanceAmountView.setEnabled(false);

                            double newBalance = child.getBalance_amount() + value;
                            child.setBalance_amount(newBalance);
                        }catch(Exception e) {
                            return;
                        }

                    }
                });

        if(child.getStatus().equals("UPDATED")){
            balanceUpdateButton.setEnabled(false);
            balanceUpdateButton.setText("UPDATED");
            balanceAmountView.setEnabled(false);
        }

        balanceDoorNumberView.setText(childDoorNo);
        balanceNameView.setText(childName);
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
