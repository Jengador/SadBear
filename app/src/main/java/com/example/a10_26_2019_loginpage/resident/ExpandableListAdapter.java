package com.example.a10_26_2019_loginpage.resident;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.a10_26_2019_loginpage.R;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<HistoryRequestList>> _listDataChild;

    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<HistoryRequestList>> listChildData) {
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
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {



        LayoutInflater infalInflater = (LayoutInflater) this._context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        switch (groupPosition){
            case 0:
                convertView = infalInflater.inflate(R.layout.resident_delivery_list, null);
                TextView orderDateView = convertView.findViewById(R.id.residentHistoryDeliveryOrderDateView);
                TextView costView = convertView.findViewById(R.id.residentHistoryDeliveryCostView);
                TextView contentView = convertView.findViewById(R.id.residentHistoryDeliveryContentView);
                final String orderDate= ((HistoryRequestList) getChild(groupPosition, childPosition)).getDeliveryDate();
                final String cost = ((HistoryRequestList) getChild(groupPosition, childPosition)).getCost() + "$";
                final String content = ((HistoryRequestList) getChild(groupPosition, childPosition)).getContent();

                orderDateView.setText(orderDate);
                costView.setText(cost);
                contentView.setText(content);

                break;
            case 1:
                convertView = infalInflater.inflate(R.layout.resident_history_list, null);

                final String _to= ((HistoryRequestList) getChild(groupPosition, childPosition)).get_to();
                final String issue_type = ((HistoryRequestList) getChild(groupPosition, childPosition)).getIssue_type();
                final String response = ((HistoryRequestList) getChild(groupPosition, childPosition)).getResponse();
                final String support_send_time = ((HistoryRequestList) getChild(groupPosition, childPosition)).getSupport_send_time();
                final String support_result_time = ((HistoryRequestList) getChild(groupPosition, childPosition)).getSupport_result_time();
                final String detail = ((HistoryRequestList) getChild(groupPosition, childPosition)).getDetail();

                TextView _toView = convertView.findViewById(R.id.residentHistoryToView);
                TextView issue_typeView = convertView.findViewById(R.id.residentHistoryTypeView);
                TextView responseView = convertView.findViewById(R.id.residentHistoryResponseView);
                TextView support_send_timeView = convertView.findViewById(R.id.residentHistorySendTimeView);
                TextView support_result_timeView = convertView.findViewById(R.id.residentHistoryResultTimeView);
                TextView detailView = convertView.findViewById(R.id.residentHistoryDetailView);

                _toView.setText(_to);
                issue_typeView.setText(issue_type);
                responseView.setText(response);
                support_send_timeView.setText(support_send_time);
                support_result_timeView.setText(support_result_time);
                detailView.setText(detail);



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