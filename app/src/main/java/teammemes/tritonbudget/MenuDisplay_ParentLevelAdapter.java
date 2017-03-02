package teammemes.tritonbudget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MenuDisplay_ParentLevelAdapter extends BaseExpandableListAdapter {
    private final Context mContext;
    private final List<String> mListDataHeader;
    private final Map<String, List<String>> mListData_SecondLevel_Map;
    private final Map<String, List<String>> mListData_ThirdLevel_Map;

    public MenuDisplay_ParentLevelAdapter(Context mContext, List<String> mListDataHeader) {
        this.mContext = mContext;
        this.mListDataHeader = new ArrayList<>();
        this.mListDataHeader.addAll(mListDataHeader);
        // SECOND LEVEL
        String[] mItemHeaders;
        mListData_SecondLevel_Map = new HashMap<>();
        int parentCount = mListDataHeader.size();
        for (int i = 0; i < parentCount; i++) {
            String content = mListDataHeader.get(i);
            switch (content) {
                case "Burger Lounge":
                    mItemHeaders = mContext.getResources().getStringArray(R.array.BL);
                    break;
                case "Revelle Cuisine":
                    mItemHeaders = mContext.getResources().getStringArray(R.array.RC);
                    break;
                case "Market 64":
                    mItemHeaders = mContext.getResources().getStringArray(R.array.MT);
                    break;
                case "Vertically Crafted Deli":
                    mItemHeaders = mContext.getResources().getStringArray(R.array.VCD);
                    break;
                case "Wok":
                    mItemHeaders = mContext.getResources().getStringArray(R.array.WK);
                    break;

                default:
                    mItemHeaders = mContext.getResources().getStringArray(R.array.RC);
            }
            mListData_SecondLevel_Map.put(mListDataHeader.get(i), Arrays.asList(mItemHeaders));
        }
        // THIRD LEVEL
        String[] mItemChildOfChild;
        List<String> listChild;
        mListData_ThirdLevel_Map = new HashMap<>();
        for (Object o : mListData_SecondLevel_Map.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            Object object = entry.getValue();
            if (object instanceof List) {
                List<String> stringList = new ArrayList<>();
                Collections.addAll(stringList, (String[]) ((List) object).toArray());
                for (int i = 0; i < stringList.size(); i++) {
                    mItemChildOfChild = mContext.getResources().getStringArray(R.array.NT);
                    listChild = Arrays.asList(mItemChildOfChild);
                    mListData_ThirdLevel_Map.put(stringList.get(i), listChild);
                }
            }
        }
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final MenuDisplay_CustomExpListView secondLevelExpListView = new MenuDisplay_CustomExpListView(this.mContext);
        String parentNode = (String) getGroup(groupPosition);
        secondLevelExpListView.setAdapter(new MenuDisplay_SecondLevelAdapter(this.mContext, mListData_SecondLevel_Map.get(parentNode), mListData_ThirdLevel_Map));
        secondLevelExpListView.setGroupIndicator(null);
//        secondLevelExpListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//            int previousGroup = -1;
//            @Override
//            public void onGroupExpand(int groupPosition) {
//                if (groupPosition != previousGroup)
//                    secondLevelExpListView.collapseGroup(previousGroup);
//                previousGroup = groupPosition;
//            }
//        });
        return secondLevelExpListView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.mListDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.mListDataHeader.size();
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
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.drawer_list_group, parent, false);
        }
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setTextColor(Color.BLACK);
        lblListHeader.setText(headerTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}