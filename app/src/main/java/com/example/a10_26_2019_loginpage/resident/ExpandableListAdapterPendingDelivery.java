package com.example.a10_26_2019_loginpage.resident;

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
import com.example.a10_26_2019_loginpage.resident.residentDataUpdate.PendingDeliveryDataProcessor;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapterPendingDelivery extends BaseExpandableListAdapter {
    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<PendingDeliveryInfo>> _listDataChild;

    public ExpandableListAdapterPendingDelivery(Context context, List<String> listDataHeader,
                                               HashMap<String, List<PendingDeliveryInfo>> listChildData) {
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

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_view_pending_delivery, null);
        }
        TextView dateView = convertView.findViewById(R.id.pendingDeliveryDate);
        TextView detailView = convertView.findViewById(R.id.pendingDeliveryContent);
        TextView consentMessage = convertView.findViewById(R.id.pendingDeliveryConsentMessage);
        final Button acceptButton = convertView.findViewById(R.id.pendingDeliveryAcceptButton);

        final PendingDeliveryInfo child = ((PendingDeliveryInfo)getChild(groupPosition, childPosition));

        if(child.getStatus().equals("CLOSED")){
            acceptButton.setEnabled(false);
            acceptButton.setText(child.getCost() + "$ has been withdrawn");
        }

        dateView.setText(child.getDate());
        detailView.setText(child.getContent());
        consentMessage.setText("For the above delivery " + child.getCost() + "$ will be withdrawn from your account balance");

        acceptButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                PendingDeliveryDataProcessor dataProcessor = new PendingDeliveryDataProcessor();
                dataProcessor.closeDelivery(child);

                acceptButton.setEnabled(false);
                acceptButton.setText(child.getCost() + "$ has been withdrawn");
                child.setStatus("CLOSED");
            }});



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
