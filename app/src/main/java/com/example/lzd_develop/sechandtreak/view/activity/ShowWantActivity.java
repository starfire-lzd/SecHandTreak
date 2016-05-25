package com.example.lzd_develop.sechandtreak.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lzd_develop.sechandtreak.R;
import com.example.lzd_develop.sechandtreak.doman.WantInfo;
import com.example.lzd_develop.sechandtreak.service.ILoadInfoService;
import com.example.lzd_develop.sechandtreak.service.ReturnType;
import com.example.lzd_develop.sechandtreak.service.ServiceFectroy;
import com.example.lzd_develop.sechandtreak.utils.ViewUtil;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lzd-develop on 16-5-10.
 */
public class ShowWantActivity extends BaceActivity {
    ILoadInfoService service;
    @Bind(R.id.ib_goods_return)
    ImageView ibGoodsReturn;
    @Bind(R.id.iv_goods_header)
    RoundedImageView ivGoodsHeader;
    @Bind(R.id.ll_goods_authinfo)
    ImageView llGoodsAuthinfo;
    @Bind(R.id.tv_goods_username)
    TextView tvGoodsUsername;
    @Bind(R.id.tv_goods_name)
    TextView tvGoodsName;
    @Bind(R.id.tv_goods_content)
    TextView tvGoodsContent;
    @Bind(R.id.tv_goods_time)
    TextView tvGoodsTime;
    @Bind(R.id.tv_goods_dist)
    TextView tvGoodsDist;
    @Bind(R.id.tv_goods_price)
    TextView tvGoodsPrice;
    @Bind(R.id.iv_goods_chat)
    Button ivGoodsChat;
    @Bind(R.id.ll_goods_info)
    LinearLayout llGoodsInfo;
    @Bind(R.id.tv_goods_commentnum)
    TextView tvGoodsCommentnum;
    @Bind(R.id.ll_goods_commentinfo)
    LinearLayout llGoodsCommentinfo;
    @Bind(R.id.buyinginfo_comment_list)
    ListView buyinginfoCommentList;
    @Bind(R.id.et_goods_commentcontent)
    EditText etGoodsCommentcontent;
    @Bind(R.id.btn_goods_comment)
    TextView btnGoodsComment;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodsbuyinginfo);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        //TODO 完成时改成 -1
        int buyId = intent.getIntExtra("buyId", -1);
        if (buyId == -1) {
            return;
        }
        service = (ILoadInfoService) ServiceFectroy.getService(ServiceFectroy.ServiceType.loadinfo, handler);
        service.onLodaWantInfo(buyId, ILoadInfoService.goodsType.want);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ReturnType.LOAD_WANT_INFO_SUCCESS:
                    loadSuccess((WantInfo) msg.obj);
                    break;
                case ReturnType.LOAD_WANT_INFO_NETWORK:
                    break;
                case ReturnType.LOAD_WANT_INFO_SELLED:
                    break;
                case ReturnType.LOAD_WANT_INFO_DOWN:
                    break;
            }

        }
    };

    private void loadSuccess(WantInfo info) {
        tvGoodsUsername.setText(info.getWantWriter());
        tvGoodsName.setText(info.getWantTitle());
        tvGoodsPrice.setText("￥" + info.getWantPrice());
        tvGoodsContent.setText(info.getWantContent());

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.header_default)
                .showImageOnFail(R.drawable.header_default)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoader.getInstance().displayImage(info.getWanterPic(), ivGoodsHeader, options);

        tvGoodsTime.setText(info.getWantTime());
        buyinginfoCommentList.setAdapter(new WorldsAdapter(info.getWantWords()));
        ViewUtil.setListViewHeightBasedOnChildren(buyinginfoCommentList);

        tvGoodsCommentnum.setText(info.getWantWords().size()+"");

    }



    class WorldsAdapter extends BaseAdapter {

        private List<WantInfo.WantWordsBean> list;
        private LayoutInflater inflator;

        public WorldsAdapter(List<WantInfo.WantWordsBean> list) {
            if (list == null) {
                this.list = new ArrayList<>(5);
                return;
            }
            this.list = list;
            this.inflator = LayoutInflater.from(getContext());
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View item = inflator.inflate(R.layout.item_listview_worlds, null);
            ViewHolder holder = new ViewHolder(item);
            holder.worldsPeople.setText(list.get(position).getWorldWriter());
            holder.worldsWorld.setText(list.get(position).getWorldContent());
            return item;
        }

        class ViewHolder {
            @Bind(R.id.worlds_people)
            TextView worldsPeople;
            @Bind(R.id.worlds_world)
            TextView worldsWorld;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }


}
