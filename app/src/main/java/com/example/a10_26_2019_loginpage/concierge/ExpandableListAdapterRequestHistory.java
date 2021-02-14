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
import android.widget.TextView;

import com.example.a10_26_2019_loginpage.R;

public class ExpandableListAdapterRequestHistory extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<RequestList>> _listDataChild;

    public ExpandableListAdapterRequestHistory(Context context, List<String> listDataHeader,
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
        final String response = ((RequestList) getChild(groupPosition, childPosition)).getResponse();
        final String support_send_time = ((RequestList) getChild(groupPosition, childPosition)).getSupport_send_time();
        final String support_result_time = ((RequestList) getChild(groupPosition, childPosition)).getSupport_result_time();
        final String detail = ((RequestList) getChild(groupPosition, childPosition)).getDetail();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.r_and_m_history_list, null);
        }

        TextView _fromView = convertView.findViewById(R.id.RandMFromView);
        TextView issue_typeView = convertView.findViewById(R.id.RandMIssueTypeView);
        TextView responseView = convertView.findViewById(R.id.RandMResponseView);
        TextView support_send_timeView = convertView.findViewById(R.id.RandMSendTimeView);
        TextView support_result_timeView = convertView.findViewById(R.id.RandMResultTimeView);
        TextView detailView = convertView.findViewById(R.id.RandMDetailView);

        _fromView.setText(_from);
        issue_typeView.setText(issue_type);
        responseView.setText(response);
        support_send_timeView.setText(support_send_time);
        support_result_timeView.setText(support_result_time);
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