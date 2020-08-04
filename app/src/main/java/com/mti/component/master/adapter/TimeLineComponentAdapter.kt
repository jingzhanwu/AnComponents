package com.mti.component.master.adapter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mti.component.common.TimeLineComponent
import com.mti.component.master.R
import com.mti.component.master.model.TimeLineEntry
import com.mti.component.master.util.VectorDrawableUtils
import com.mti.component.master.view.example.timeline.OrderStatus
import com.mti.component.master.view.example.timeline.Orientation
import com.mti.component.master.view.example.timeline.TimelineAttributes
import kotlinx.android.synthetic.main.item_timeline.view.*

/**
 * 时间轴组件
 */
class TimeLineComponentAdapter(private val mFeedList: List<TimeLineEntry>, private var mAttributes: TimelineAttributes) : RecyclerView.Adapter<TimeLineComponentAdapter.TimeLineViewHolder>() {

    private lateinit var mLayoutInflater: LayoutInflater

    override fun getItemViewType(position: Int): Int {
        return TimeLineComponent.getTimeLineComponentType(position, itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeLineViewHolder {

        if (!::mLayoutInflater.isInitialized) {
            mLayoutInflater = LayoutInflater.from(parent.context)
        }

        val view = if (mAttributes.orientation == Orientation.HORIZONTAL) {
            mLayoutInflater.inflate(R.layout.item_timeline_horizontal, parent, false)
        } else {
            mLayoutInflater.inflate(R.layout.item_timeline, parent, false)
        }
        return TimeLineViewHolder(view, viewType)
    }

    override fun onBindViewHolder(holder: TimeLineViewHolder, position: Int) {
        val timeLineModel = mFeedList[position]
        when {
            timeLineModel.status == OrderStatus.INACTIVE -> {
                holder.timeline.marker = VectorDrawableUtils.getDrawable(holder.itemView.context, R.drawable.ic_marker_inactive, mAttributes.markerColor)
            }
            timeLineModel.status == OrderStatus.ACTIVE -> {
                holder.timeline.marker = VectorDrawableUtils.getDrawable(holder.itemView.context, R.drawable.ic_marker_active, mAttributes.markerColor)
            }
            else -> {
                holder.timeline.setMarker(ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_marker), mAttributes.markerColor)
            }
        }

        if (timeLineModel.date.isNotEmpty()) {
            holder.date.setVisible()
            holder.date.text = timeLineModel.date.format("yyyy-MM-dd HH:mm", "hh:mm a, dd-MMM-yyyy")
        } else
            holder.date.setGone()

        holder.message.text = timeLineModel.message
    }

    override fun getItemCount() = mFeedList.size

    inner class TimeLineViewHolder(itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {

        val date = itemView.text_timeline_date
        val message = itemView.text_timeline_title
        val timeline = itemView.timeline

        init {
            timeline.initLine(viewType)
            timeline.markerSize = mAttributes.markerSize
            timeline.setMarkerColor(mAttributes.markerColor)
            timeline.isMarkerInCenter = mAttributes.markerInCenter
            timeline.markerPaddingLeft = mAttributes.markerLeftPadding
            timeline.markerPaddingTop = mAttributes.markerTopPadding
            timeline.markerPaddingRight = mAttributes.markerRightPadding
            timeline.markerPaddingBottom = mAttributes.markerBottomPadding
            timeline.linePadding = mAttributes.linePadding

            timeline.lineWidth = mAttributes.lineWidth
            timeline.setStartLineColor(mAttributes.startLineColor, viewType)
            timeline.setEndLineColor(mAttributes.endLineColor, viewType)
            timeline.lineStyle = mAttributes.lineStyle
            timeline.lineStyleDashLength = mAttributes.lineDashWidth
            timeline.lineStyleDashGap = mAttributes.lineDashGap
        }
    }

    fun View.setVisible() {
        visibility = View.VISIBLE
    }

    fun View.setInvisible() {
        visibility = View.INVISIBLE
    }

    fun View.setGone() {
        visibility = View.GONE
    }
}