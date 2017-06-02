package com.example.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.R;
import com.example.bean.LayerMgrBean;

import java.util.List;

/**
 * Created by weiyuanfei on 2017/5/26.
 */

public class LayerMgrAdapter extends BaseAdapter {

    public LayerMgrAdapter(Context context,List<LayerMgrBean> dataList) {
        this.mContext = context;
        this.mDataList = dataList;
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return mDataList.size();
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
        return Integer.parseInt(mDataList.get(position).getId());
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

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layermgr_gridview_item, null);

            holder.layout = (RelativeLayout) convertView.findViewById(R.id.layout);
            holder.layout_img = (LinearLayout) convertView.findViewById(R.id.layout_img);
            holder.tv = (TextView) convertView.findViewById(R.id.tv_text);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        LayerMgrBean lmb = mDataList.get(position);

        holder.tv.setText(lmb.getName());
        if (lmb.getBitmap() != null) {
            holder.layout_img.setBackground(new BitmapDrawable(lmb.getBitmap()));
        } else {
            holder.layout_img.setBackgroundResource(R.drawable.jianzhu);
        }
        // 判断是否选中，如果选中就改变边框的颜色，否则就还是原来的样子
		if (lmb.isShow()) {
            holder.layout.setBackgroundResource(R.drawable.gridview_item_selected);
            holder.tv.setTextColor(0xff0099FF);

        } else {

            holder.layout.setBackgroundColor(Color.TRANSPARENT);
            holder.tv.setTextColor(0xff000000);
        }

        return convertView;
    }

    class ViewHolder {
        RelativeLayout layout;
        TextView tv;
        LinearLayout layout_img;
    }

    Context             mContext;
    List<LayerMgrBean>  mDataList;
}
