package com.example.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.bean.ViewPointBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/31.
 */

public class ViewPointAdapter extends BaseAdapter {

    Context mContext;
    List<ViewPointBean> mDataList;
    boolean mCheckable = false;
    int mItemWidth = 100; // in pixels

    public ViewPointAdapter(Context mContext, List<ViewPointBean> mDataList) {
        this.mContext = mContext;
        this.mDataList = mDataList;
    }

    public void selectAll() {
        for (ViewPointBean vpb : mDataList) {
            vpb.setStatus(1);
        }
        notifyDataSetChanged();
    }

    public void deselectAll() {
        for (ViewPointBean vpb : mDataList) {
            vpb.setStatus(0);
        }
        notifyDataSetChanged();
    }

    public void deleteSelected() {
        if (!hasSelected()) {
            return;
        }
        List<ViewPointBean> lstTmp = new ArrayList<>();
        for ( ViewPointBean vpb : mDataList ) {
            if (vpb.getStatus() != 1) {
                lstTmp.add(vpb);
            }
        }
        mDataList.clear();
        mDataList.addAll(lstTmp);
        notifyDataSetChanged();
    }

    public boolean hasSelected() {
        for ( ViewPointBean vpb : mDataList ) {
            if (vpb.getStatus() == 1) {
                return true;
            }
        }
        return false;
    }

    public boolean isAllSelected() {
        for ( ViewPointBean vpb : mDataList ) {
            if (vpb.getStatus() != 1) {
                return false;
            }
        }
        return true;
    }

    public boolean checkable() {
        return mCheckable;
    }

    public void setCheckable(boolean checkable) {
        this.mCheckable = checkable;
        notifyDataSetChanged();
    }

    public void setItemWidth (int width) {
        this.mItemWidth = width;
        notifyDataSetChanged();
    }

    public int getItemWidth() {
        return mItemWidth;
    }

    public void setItemStatus(int position, int status) {
        if (position < 0 || position >= mDataList.size())
            return;
        mDataList.get(position).setStatus(status);
        notifyDataSetChanged();
    }

    public int getItemStatus(int position) {
        if (position < 0 || position >= mDataList.size())
            return -1;
        return mDataList.get(position).getStatus();
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return mDataList.size() + 1;
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        if (position < 0 || position >= mDataList.size())
            return null;
        return mDataList.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        if (position < 0 || position >= mDataList.size())
            return 0;
        return mDataList.get(position).getId();
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder vh;
        if(convertView == null)
        {
            convertView = ((Activity) mContext).getLayoutInflater().inflate(R.layout.viewpoint_gridview_item, null);
            vh = new ViewHolder();
            vh.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            vh.point_name = (TextView) convertView.findViewById(R.id.point_name);
            vh.image_delete = (ImageView) convertView.findViewById(R.id.image_delete);
            vh.cb_delete = (CheckBox) convertView.findViewById(R.id.delete_checkbox);
            convertView.setTag(vh);
        }
        else {
            vh = (ViewHolder) convertView.getTag();
        }

        vh.imageView.setLayoutParams(new RelativeLayout.LayoutParams(getItemWidth(),getItemWidth()));

        if (position == getCount()-1) {
            vh.point_name.setText("点击添加靓点");
            vh.point_name.setVisibility(View.VISIBLE);
            vh.imageView.setImageResource(R.drawable.plus);

            vh.cb_delete.setVisibility(View.GONE);
            vh.image_delete.setVisibility(View.GONE);

        } else {
            vh.point_name.setText(mDataList.get(position).getName());
            vh.point_name.setVisibility(View.VISIBLE);
            vh.imageView.setImageResource(R.drawable.jianzhu);

            if (checkable()) {
                vh.cb_delete.setVisibility(View.VISIBLE);
                vh.image_delete.setVisibility(View.GONE);

                boolean bChecked = mDataList.get(position).getStatus() == 1;
                if (bChecked) {
                    vh.cb_delete.setChecked(true);
                } else {
                    vh.cb_delete.setChecked(false);
                }

            }else {
                vh.cb_delete.setVisibility(View.GONE);
                vh.image_delete.setVisibility(View.GONE);
            }
        }
        return convertView;
    }

    class ViewHolder {
        public ImageView    imageView;
        public TextView     point_name;
        public ImageView    image_delete;
        public CheckBox     cb_delete;
    }
}
