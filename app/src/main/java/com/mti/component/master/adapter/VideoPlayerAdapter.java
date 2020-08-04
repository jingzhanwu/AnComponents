package com.mti.component.master.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mti.component.master.R;
import com.mti.component.master.callback.OnAdapterItemClickListener;
import com.mti.component.master.model.VideoEntry;
import com.mti.component.media.video.MediaUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author created by jzw
 * @date 2020/5/22
 * @change
 * @describe 视频播放-本地视频列表适配器
 **/
public class VideoPlayerAdapter extends RecyclerView.Adapter<VideoPlayerAdapter.VideoPlayerHolder> {

    private Context mContext;
    private List<VideoEntry> mData;

    private OnAdapterItemClickListener mOnItemClick;

    //    private static final String URL = "rtmp://live.hkstv.hk.lxdns.com/live/hks";
    private static final String URL = "http://vfx.mtime.cn/Video/2019/03/12/mp4/190312143927981075.mp4";
    //    private static final String URL = "http://youku163.zuida-bofang.com/20190126/26805_c313a74d/index.m3u8";

    public VideoPlayerAdapter(Context context, List<VideoEntry> data) {
        mContext = context;
        mData = data == null ? new ArrayList<>() : data;
    }

    public void setOnItemClickListener(OnAdapterItemClickListener listener) {
        mOnItemClick = listener;
    }

    @NonNull
    @Override
    public VideoPlayerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_video_player, viewGroup, false);
        return new VideoPlayerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoPlayerHolder holder, int i) {
        VideoEntry model = mData.get(i);
        holder.tvTitle.setText(model.getName());
        String time = millisToStringShort(model.getDuration());
        holder.tvDuration.setText(time);
        long sizeM = model.getSize() / 1024 / 1024;
        holder.tvSize.setText(sizeM + "M");
        MediaUtils.getImageForVideo(model.getUrl(), file -> Glide.with(mContext)
                .load(file)
                .into(holder.ivCover));
        holder.itemView.setOnClickListener(v -> {
            if (mOnItemClick != null) {
                mOnItemClick.onItemClick(i, model);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class VideoPlayerHolder extends RecyclerView.ViewHolder {
        private ImageView ivCover;
        private TextView tvTitle;
        private TextView tvSize;
        private TextView tvDuration;

        public VideoPlayerHolder(@NonNull View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.ivImage);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSize = itemView.findViewById(R.id.tvSize);
            tvDuration = itemView.findViewById(R.id.tvDuration);
        }
    }

    public String millisToStringShort(long l) {
        StringBuffer sb = new StringBuffer();
        long millis = 1;
        long seconds = 1000 * millis;
        long minutes = 60 * seconds;
        long hours = 60 * minutes;
        long days = 24 * hours;
//        if (l / days >= 1)
//            sb.append((int) (l / days) + "天");
        //小时
        if (l / hours >= 1) {
            int h = (int) (l / hours);
            sb.append(h >= 10 ? (h + ":") : ("0" + h + ":"));
        } else {
            sb.append("00:");
        }
        //分钟
        if (l % hours / minutes >= 1) {
            int m = (int) (l % hours / minutes);
            sb.append(m >= 10 ? (m + ":") : ("0" + m + ":"));
        } else {
            sb.append("00:");
        }
        //秒
        if (l % hours % minutes / seconds >= 1) {
            int s = (int) (l % hours % minutes / seconds);
            sb.append(s >= 10 ? s : ("0" + s));
        } else {
            sb.append("00");
        }
//        if (l % days % hours % minutes % seconds / millis >= 1)
//            sb.append((int) (l % days % hours % minutes % seconds / millis) + "毫秒");
        return sb.toString();
    }
}
