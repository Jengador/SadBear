package com.example.a10_26_2019_loginpage.manager;

import com.example.a10_26_2019_loginpage.manager.dataProcessor.*;
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

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapterRequestSupport extends BaseExpandableListAdapter {
    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<RequestList>> _listDataChild;

    public ExpandableListAdapterRequestSupport(Context context, List<String> listDataHeader,
                                               HashMap<String, List<RequestList>> listChildData) {
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
        final String _from = ((RequestList) getChild(groupPosition, childPosition)).get_from();
        final String issue_type = ((RequestList) getChild(groupPosition, childPosition)).getIssue_type();
        final String support_send_time = ((RequestList) getChild(groupPosition, childPosition)).getSupport_send_time();
        final String detail = ((RequestList) getChild(groupPosition, childPosition)).getDetail();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.r_and_m_new_request, null);
        }

        TextView _fromView = convertView.findViewById(R.id.RandMNewFromView);
        TextView issue_typeView = convertView.findViewById(R.id.RandMNewIssueTypeView);
        final EditText responseEdit = convertView.findViewById(R.id.RandMNewResponseEdit);
        TextView support_send_timeView = convertView.findViewById(R.id.RandMNewSendTimeView);
        TextView detailView = convertView.findViewById(R.id.RandMNewDetailView);
        final Button respondButton = convertView.findViewById(R.id.RandMRespondButton);
        final Button directButton = convertView.findViewById(R.id.RandMDirectButton);
        final TextView messageView = convertView.findViewById(R.id.RandMNewRequestMessage);

        directButton.setText("DIRECT TO CONCIERGE");


        directButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                directButton.setEnabled(false);
                respondButton.setEnabled(false);
                messageView.setVisibility(View.VISIBLE);
                responseEdit.setEnabled(false);
                messageView.setText("This request has been directed to Concierge!");

                newRequestSupportDataProcessor transfer = new newRequestSupportDataProcessor();
                transfer.transferToConcierge(((RequestList) getChild(groupPosition, childPosition)).getId());
            }
        });

        respondButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                if(responseEdit.getText().toString().length() < 10){
                    messageView.setVisibility(View.VISIBLE);
                    messageView.setText("Response cannot be shorter than 10 characters!");
                    return;
                }
                messageView.setVisibility(View.VISIBLE);
                messageView.setText("This request has been responded!!");
                responseEdit.setEnabled(false);
                directButton.setEnabled(false);
                respondButton.setEnabled(false);

                ((RequestList) getChild(groupPosition, childPosition)).setResponse(responseEdit.getText().toString());
                newRequestSupportDataProcessor transfer = new newRequestSupportDataProcessor();
                transfer.respondRequest((RequestList) getChild(groupPosition, childPosition));

            }
        });

        _fromView.setText(_from);
        issue_typeView.setText(issue_type);
        support_send_timeView.setText(support_send_time);
        detailView.setText(detail);

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
