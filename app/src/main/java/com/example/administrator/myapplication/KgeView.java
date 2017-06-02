package com.example.administrator.myapplication;

import android.app.AlertDialog;
import android.app.NativeActivity;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.LayerMgrAdapter;
import com.example.adapter.ViewPointAdapter;
import com.example.bean.LayerMgrBean;
import com.example.bean.ViewPointBean;

import java.util.ArrayList;
import java.util.List;

public class KgeView extends NativeActivity {
    static {
        Log.e("wyf","aaaaaaaaaa");
        System.loadLibrary("c++_shared");
        Log.e("wyf","bbbbbbbbbb");
    }

//    Rect mWndRect = new Rect();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide toolbar
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        Log.i("OnCreate", "OnCreate!!!");
        if(SDK_INT >= 19)
        {
            setImmersiveSticky();
            View decorView = getWindow().getDecorView();
            decorView.setOnSystemUiVisibilityChangeListener
                    (new View.OnSystemUiVisibilityChangeListener() {
                        @Override
                        public void onSystemUiVisibilityChange(int visibility) {
                            setImmersiveSticky();
                        }
                    });
        }

//        WindowManager wndMgr = (WindowManager) getSystemService(WINDOW_SERVICE);
//        wndMgr.getDefaultDisplay().getRectSize(mWndRect);
        Log.e("wyf", "after OnCreate!!!");
    }

    @Override
    protected void onResume(){
        super.onResume();
        //Hide toolbar
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if(SDK_INT >= 11 && SDK_INT < 14)
        {
            getWindow().getDecorView().setSystemUiVisibility(View.STATUS_BAR_HIDDEN);
        }
        else if(SDK_INT >= 14 && SDK_INT < 19)
        {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LOW_PROFILE);
        }
        else if(SDK_INT >= 19)
        {
            setImmersiveSticky();
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        if (_popupFPS != null) {

            _popupFPS.dismiss();
            _popupFPS = null;
        }
        if (_popupVP != null) {

            _popupVP.dismiss();
            _popupVP = null;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //newConfig.orientation获得当前屏幕状态是横向或者竖向
        //Configuration.ORIENTATION_PORTRAIT 表示竖向
        //Configuration.ORIENTATION_LANDSCAPE 表示横屏
        Log.e("wyf","onConfigurationChanged");
        if(newConfig.orientation==Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "现在是竖屏", Toast.LENGTH_SHORT).show();
        }
        if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){
            Toast.makeText(this, "现在是横屏", Toast.LENGTH_SHORT).show();
        }
    }

    // Our popup window, you will call it from your C/C++ code later
    void setImmersiveSticky() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }

    KgeView _activity;
    PopupWindow _popupFPS;
    PopupWindow _popupVP;
    PopupWindow _popupTopRightTools;
    PopupWindow _popupViewPoint;
    TextView _label;
    FloatingActionButton _fab;
    LayerMgrAdapter _layermgrAdapter;
    ViewPointAdapter _viewpointAdapter;
    HorizontalScrollView _hsv;

    public static final int HANDLERMSG_SCROOL_TO_END = 1;

    public Handler _handler = new Handler() {
        /**
         * Subclasses must implement this to receive messages.
         *
         * @param msg
         */
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLERMSG_SCROOL_TO_END:
                    Log.e("wyf","qqqqqqqqqqqqq ");

                    if (_popupViewPoint != null && _popupViewPoint.isShowing())
                    {
//                        int index = _viewpointAdapter.getCount() - 1 - gridView.getFirstVisiblePosition();
//                        Log.e("wyf","index is " + index);
//                        Log.e("wyf","getNumColumns is " + gridView.getNumColumns());
//                        Log.e("wyf","getChildCount is " + gridView.getChildCount());
//
//                        int nLeft = gridView.getChildAt(index).getLeft();
                        Log.e("wyf","scrollTo 2500 ");
                        _hsv.scrollTo(2500, 0);
                    } else {
                        Log.e("wyf","_popupViewPoint is null!!!");
                    }

            }
        }
    };

    public PopupWindow showPopupWindow(@LayoutRes int resource, View parent, int gravity, int x, int y)
    {
        LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(resource, null);
        PopupWindow popupWnd = new PopupWindow( popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        // Show our UI over NativeActivity window
        popupWnd.showAtLocation(parent, gravity, 10, 10);
        popupWnd.update();
        return popupWnd;
    }

    public void showUI()
    {
        if( _popupFPS != null )
            return;

        _activity = this;

        this.runOnUiThread(new Runnable()  {
            @Override
            public void run()  {
//                final LinearLayout mainLayout = new LinearLayout(_activity);
//                final RelativeLayout mainLayout = new RelativeLayout(_activity);
                final CoordinatorLayout mainLayout = new CoordinatorLayout(_activity);

                MarginLayoutParams params = new MarginLayoutParams(100,100);
//                MarginLayoutParams params = new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

                params.setMargins(0, 0, 0, 0);
                _activity.setContentView(mainLayout, params);

                _popupFPS = showPopupWindow(R.layout.widgets, mainLayout, Gravity.TOP | Gravity.LEFT, 10, 10);
                _label = (TextView)_popupFPS.getContentView().findViewById(R.id.textViewFPS);

                _popupVP = showPopupWindow(R.layout.activity_kge_view, mainLayout, Gravity.RIGHT|Gravity.BOTTOM, 0, 0);
                _fab = (FloatingActionButton)_popupVP.getContentView().findViewById(R.id.fab111);
                _fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (view == null) {
                            return;
                        }
                        _fab.setVisibility(View.INVISIBLE);

                        if (_viewpointAdapter == null) {
                            initViewPointAdapter();
                        }
                        _viewpointAdapter.setCheckable(false);

                        DisplayMetrics dm = new DisplayMetrics();
                        getWindowManager().getDefaultDisplay().getMetrics(dm);
                        final int spacing = 20; // in pixels.
                        int nWndWidth = dm.widthPixels < dm.heightPixels ? dm.widthPixels : dm.heightPixels;
                        int itemWidth = nWndWidth / 3 - spacing; // in pixels.
                        final int gridviewWidth =  _viewpointAdapter.getCount() * (itemWidth + spacing)
                                - spacing; // in pixels.
                        _viewpointAdapter.setItemWidth(itemWidth);
//                        Log.e("wyf","the screen width= " + mWndRect.width());
//                        Log.e("wyf","the screen height= " + mWndRect.height());
//                        Log.e("wyf","the dm.widthPixels= " + dm.widthPixels);
//                        Log.e("wyf","the dm.heightPixels= " + dm.heightPixels);
//                        Log.e("wyf","the dm.density= " + dm.density);
//                        Log.e("wyf","the dm.densityDpi= " + dm.densityDpi);
//                        Log.e("wyf","the dm.xdpi= " + dm.xdpi);
//                        Log.e("wyf","the dm.ydpi= " + dm.ydpi);
//                        Log.e("wyf","the Grid Item Width= " + itemWidth);
//                        Log.e("wyf","the Grid Width= " + gridviewWidth);

//                        Log.e("wyf",view.getClass().getName());
//                        Log.e("wyf",view.getClass().getSuperclass().getName());
//                        System.out.println(view.getClass().getSuperclass());
//                        Log.e("wyf",view.getParent().getClass().getName());
//                        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                                .setAction("Action", null).show();
                        View viewpointView = getLayoutInflater()
                                .inflate(R.layout.popup_viewpoint, null);
                        viewpointView.setFocusable(true);
                        viewpointView.setFocusableInTouchMode(true);

                        _hsv = (HorizontalScrollView) viewpointView.findViewById(R.id.hsv);
                        _hsv.setSmoothScrollingEnabled(true);

                        final LinearLayout ll_point_edit = (LinearLayout) viewpointView
                                .findViewById(R.id.ll_point_edit);
                        final CheckBox img_choose_all = (CheckBox) viewpointView.
                                findViewById(R.id.checkBox);;
                        final ImageView image_close = (ImageView) viewpointView.
                                findViewById(R.id.image_close);
                        final GridView gridView = (GridView) viewpointView.
                                findViewById(R.id.gridView);

                        ll_point_edit.setVisibility(View.GONE);
                        img_choose_all.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (img_choose_all.isChecked()) {
                                    _viewpointAdapter.selectAll();
                                }
                                else {
                                    _viewpointAdapter.deselectAll();
                                }
//                                if (_viewpointAdapter.isAllSelected()) {
//                                    _viewpointAdapter.deselectAll();
////                                    img_choose_all.setBackgroundResource(R.drawable.choose_all_point_normal);
//                                } else {
//                                    _viewpointAdapter.selectAll();
////                                    img_choose_all.setBackgroundResource(R.drawable.choose_all_point_pressed);
//                                }
                            }
                        });
                        image_close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (_viewpointAdapter.hasSelected()) {
                                    Toast.makeText(_activity, "请在此确认之后删除选择的靓点", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(_activity, "请选择您要删除的靓点", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        LinearLayout.LayoutParams params =
                                new LinearLayout.LayoutParams(gridviewWidth, itemWidth);
                        gridView.setLayoutParams(params);       // 设置GirdView布局参数,横向布局的关键
                        gridView.setHorizontalSpacing(spacing); // The amount of horizontal space between items, in pixels.
                        gridView.setColumnWidth(itemWidth);     // Set the width of columns in the grid, in pixels.
                        gridView.setNumColumns(_viewpointAdapter.getCount());
                        gridView.setStretchMode(GridView.NO_STRETCH);
                        gridView.setAdapter(_viewpointAdapter);
                        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                if (_viewpointAdapter.checkable()) {
                                    if (position == _viewpointAdapter.getCount()-1) {
                                        Toast.makeText(_activity,"在此添加保存靓点的操作", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        int newStatus = _viewpointAdapter.getItemStatus(position) == 1 ?
                                                0 : 1;
                                        _viewpointAdapter.setItemStatus(position, newStatus);
                                    }

                                } else {
                                    if (position == _viewpointAdapter.getCount()-1) {
                                        Toast.makeText(_activity,"在此添加保存靓点的操作", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(_activity,"在此添加切换视点的操作", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
                        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                if (position != _viewpointAdapter.getCount()-1) {
                                    ll_point_edit.setVisibility(View.VISIBLE);
                                    _viewpointAdapter.setCheckable(true);
    //                                    handler.sendEmptyMessage(POINT_SCROOL_TO_LAST);
                                    int index = _viewpointAdapter.getCount() - 1 - gridView.getFirstVisiblePosition();
                                    int nLeft = gridView.getChildAt(index).getLeft();
                                    int nRight = gridView.getChildAt(index).getRight();
                                    Log.e("wyf","index is " + index);
                                    Log.e("wyf","nLeft is " + nLeft);
                                    Log.e("wyf","nRight is " + nRight);
                                    Log.e("wyf","gridviewWidth is " + gridviewWidth);
                                    _hsv.scrollTo(nRight, 0);
                                }
                                return true;
                            }
                        });

                        PopupWindow _popupViewPoint =
                                new PopupWindow(viewpointView, dm.widthPixels, itemWidth, true);
                        _popupViewPoint.setFocusable(true);
                        _popupViewPoint.setBackgroundDrawable(new BitmapDrawable());
                        _popupViewPoint.setOutsideTouchable(true);
                        _popupViewPoint.showAtLocation(mainLayout, Gravity.BOTTOM, 0, 0);
                        _popupViewPoint.setOnDismissListener(new PopupWindow.OnDismissListener() {
                            @Override
                            public void onDismiss() {
                                _fab.setVisibility(View.VISIBLE);
                            }
                        });
//                        _handler.sendEmptyMessage(HANDLERMSG_SCROOL_TO_END);
                        _handler.sendEmptyMessageDelayed(HANDLERMSG_SCROOL_TO_END, 10);
                    }
                });

                _popupTopRightTools = showPopupWindow(R.layout.top_right_floating_button, mainLayout, Gravity.RIGHT|Gravity.TOP, 10, 10);
                FloatingActionButton fab_layer = (FloatingActionButton)_popupTopRightTools.
                        getContentView().findViewById(R.id.fab_layer);
                fab_layer.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        View layerView = LayoutInflater.from(_activity).inflate(
                                R.layout.layermgr_dialog, null);

                        PopupWindow layermgrDlg = new PopupWindow(layerView, -2, -2);
                        if (_layermgrAdapter == null) {
                            initLayerAdapter();
                        }
                        GridView gridView = (GridView) layerView.findViewById(R.id.gridView);
                        gridView.setAdapter(_layermgrAdapter);
                        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                LayerMgrBean lmb = (LayerMgrBean)_layermgrAdapter.getItem(position);
                                if (lmb.isShow()) {
                                    lmb.setShow(false);
                                    Toast.makeText(_activity, "取消显示："+lmb.getName(),
                                            Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    lmb.setShow(true);
                                    Toast.makeText(_activity, "显示图层："+lmb.getName(),
                                            Toast.LENGTH_SHORT).show();
                                }
                                _layermgrAdapter.notifyDataSetChanged();
                            }
                        });
                        layermgrDlg.setFocusable(true);
                        layermgrDlg.setBackgroundDrawable(new BitmapDrawable());
                        layermgrDlg.setOutsideTouchable(true);
                        layermgrDlg.showAtLocation(mainLayout, Gravity.CENTER, 0, 0);
                    }
                });


            }});
    }

    private void initViewPointAdapter() {
        if (_viewpointAdapter != null) {
            return;
        }
        List<ViewPointBean> dataList = new ArrayList<>();
        dataList.add(new ViewPointBean(1, "a"));
        dataList.add(new ViewPointBean(2, "b"));
        dataList.add(new ViewPointBean(3, "c"));
        dataList.add(new ViewPointBean(4, "d"));
        dataList.add(new ViewPointBean(5, "e"));
        dataList.add(new ViewPointBean(6, "f"));
        _viewpointAdapter = new ViewPointAdapter(this, dataList);
    }

    private void initLayerAdapter() {
        if (_layermgrAdapter != null)
            return;
        List<LayerMgrBean> dataList = new ArrayList<>();
        dataList.add(new LayerMgrBean("1","aaa"));
        dataList.add(new LayerMgrBean("2","bbb"));
        dataList.add(new LayerMgrBean("3","ccc"));
        dataList.add(new LayerMgrBean("4","ddd"));
        dataList.add(new LayerMgrBean("5","eee"));
        _layermgrAdapter = new LayerMgrAdapter(this, dataList);
    }

    public void updateFPS(final float fFPS)
    {
        if( _label == null )
            return;

        _activity = this;
        this.runOnUiThread(new Runnable()  {
            @Override
            public void run()  {
                _label.setText(String.format("%2.2f FPS", fFPS));
            }});
    }
}
