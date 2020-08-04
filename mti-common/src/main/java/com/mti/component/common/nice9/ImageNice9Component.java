package com.mti.component.common.nice9;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.OnePlusNLayoutHelper;
import com.mti.component.common.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by wxy on 2017/5/25.
 * 模仿nice首页列表 9种样式图片
 * 依赖淘宝vLayout开源控件 实现
 * 1
 * -------------------------
 * |                       |
 * |                       |
 * |           1           |
 * |                       |
 * |                       |
 * |                       |
 * -------------------------
 * <p>
 * 2
 * * -------------------------
 * |           |           |
 * |           |           |
 * |           |           |
 * |     1     |     2     |
 * |           |           |
 * |           |           |
 * |           |           |
 * -------------------------
 * 3
 * -------------------------
 * |           |           |
 * |           |     2     |
 * |           |           |
 * |     1     |-----------|
 * |           |           |
 * |           |     3     |
 * |           |           |
 * -------------------------
 * 4
 * -------------------------
 * |                       |
 * |           1           |
 * |                       |
 * |-----------------------|
 * |      |        |       |
 * |   2  |     3  |    4  |
 * |      |        |       |
 * -------------------------
 * 5
 * -------------------------
 * |          |            |
 * |    1     |   2        |
 * |          |            |
 * |-----------------------|
 * |      |        |       |
 * |   3  |    4   |    5  |
 * |      |        |       |
 * -------------------------
 * 6
 * -------------------------
 * |           |           |
 * |           |     2     |
 * |           |           |
 * |     1     |-----------|
 * |           |           |
 * |           |     3     |
 * |           |           |
 * -------------------------
 * |      |        |       |
 * |   4  |   5    |    6  |
 * |      |        |       |
 * -------------------------
 * <p>
 * 7
 * -------------------------
 * |                       |
 * |           1           |
 * |                       |
 * |-----------------------|
 * |      |        |       |
 * |   2  |     3  |    4  |
 * |      |        |       |
 * -------------------------
 * |      |        |       |
 * |   5  |     6  |    7  |
 * |      |        |       |
 * -------------------------
 * 8
 * -------------------------
 * |          |            |
 * |    1     |   2        |
 * |          |            |
 * |-----------------------|
 * |      |        |       |
 * |   3  |    4   |    5  |
 * |      |        |       |
 * -------------------------
 * |      |        |       |
 * |   6  |     7  |    8  |
 * |      |        |       |
 * -------------------------
 * 9
 * |-----------------------|
 * |      |        |       |
 * |   1  |     2  |    3  |
 * |      |        |       |
 * -------------------------
 * |      |        |       |
 * |   4  |     5  |    6  |
 * |      |        |       |
 * -------------------------
 * |      |        |       |
 * |   7  |     8  |    9  |
 * |      |        |       |
 * -------------------------
 */

public class ImageNice9Component extends LinearLayout implements Nice9ItemTouchCallback.OnDragListener {
    private RecyclerView mRecycler;
    private TextView mTip;
    private VirtualLayoutManager layoutManager;
    private List<LayoutHelper> helpers;
    private ItemTouchHelper itemTouchHelper;
    private GridLayoutHelper gridLayoutHelper;
    private OnePlusNLayoutHelper onePlusHelper;
    private ImageNice9Adapter mNice9Adapter;
    private boolean canDrag = false;
    //打开编辑模式feedback
    private boolean openEditModel = false;
    private Drawable mDelDrawable;
    private Context mContext;
    private boolean isShowTip = false;
    private int itemMargin = 10;
    //编辑模式下的添加图标
    private Drawable addDrawable = null;


    public ImageNice9Component(Context context) {
        this(context, null);
    }

    public ImageNice9Component(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageNice9Component(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ImageNice9Component);
        final int N = typedArray.getIndexCount();//取得本集合里面总共有多少个属性
        for (int i = 0; i < N; i++) {//遍历这些属性，拿到对应的属性
            initAttr(typedArray.getIndex(i), typedArray);
        }
        typedArray.recycle();
        mNice9Adapter = new ImageNice9Adapter(layoutManager, mContext, canDrag, itemMargin);
        setDelImageDrawable(mDelDrawable);
    }


    private void initAttr(int attr, TypedArray typedArray) {
        if (attr == R.styleable.ImageNice9Component_nice9_canDrag) {
            canDrag = typedArray.getBoolean(attr, false);
        }
        if (attr == R.styleable.ImageNice9Component_nice9_delDrawable) {
            mDelDrawable = typedArray.getDrawable(attr);
        }
        if (attr == R.styleable.ImageNice9Component_nice9_itemMargin) {
            itemMargin = (int) typedArray.getDimension(attr, 5);
        }
        if (attr == R.styleable.ImageNice9Component_nice9_tipText) {
            setTipText(typedArray.getString(attr));
        }
        if (attr == R.styleable.ImageNice9Component_nice9_tipColor) {
            setTipColor(typedArray.getColor(attr, Color.parseColor("#ffffff")));
        }
        if (attr == R.styleable.ImageNice9Component_nice9_tipBgColor) {
            setTipBgColor(typedArray.getColor(attr, Color.parseColor("#40000000")));
        }
        if (attr == R.styleable.ImageNice9Component_nice9_tipBgDrawable) {
            setTipBgDrawable(typedArray.getDrawable(attr));
        }
    }

    /**
     * 提示文字背景
     **/
    public void setTipBgDrawable(Drawable tipBgDrawable) {
        mTip.setBackground(tipBgDrawable);
    }

    /**
     * 提示文字颜色
     **/
    public void setTipColor(int tipBgColor) {
        mTip.setTextColor(tipBgColor);
    }

    /**
     * 提示背景颜色
     **/
    public void setTipBgColor(int tipBgColor) {
        mTip.setBackgroundColor(tipBgColor);
    }

    /**
     * 提示文字
     **/
    public void setTipText(String string) {
        mTip.setText(string);
    }

    public void setTipText(@StringRes int string) {
        setTipText(getResources().getString(string));
    }

    private void initView(Context context) {
        mContext = context;
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_imagemulit_layout, this);
        mRecycler = view.findViewById(R.id.drag_recycler);
        mTip = view.findViewById(R.id.drag_tip);
        layoutManager = new VirtualLayoutManager(mContext);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.setNestedScrollingEnabled(false);
    }

    public void setItemDelegate(ItemDelegate itemDelegate) {
        mNice9Adapter.setItemDelegate(itemDelegate);
    }

    /**
     * 设置是否可以拖拽,在bindData 之前调用
     **/
    public void setCanDrag(boolean canDrag) {
        this.canDrag = canDrag;
    }

    /**
     * 设置是否可编辑,在bindData 之前调用
     *
     * @param canEdit
     */
    public void openEditModel(boolean canEdit) {
        this.openEditModel = canEdit;
    }

    private int getDisplayWidth(Context context) {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        return point.x;
    }

    /**
     * 移除指定位置的item
     *
     * @param position
     */
    public void removeItemAt(int position) {
        mNice9Adapter.removeItemAt(position);
    }

    /**
     * 设置占位图片,在bindData 之前调用
     *
     * @param drawable
     */
    public void setPlaceholderDrawable(Drawable drawable) {
        mNice9Adapter.setPlaceholderDrawable(drawable);
    }

    /**
     * 设置删除view的图标,在bindData 之前调用
     *
     * @param drawable
     */
    public void setDelImageDrawable(Drawable drawable) {
        mNice9Adapter.setDelImageDrawable(drawable);
    }

    /**
     * 设置添加图标
     *
     * @param drawable
     */
    public void setAddDrawable(Drawable drawable) {
        this.addDrawable = drawable;
    }

    /**
     * 最佳数据
     *
     * @param pictures
     */
    public void addData(List<String> pictures) {
        if (pictures != null && mNice9Adapter != null) {
            List<String> list = new ArrayList<>();
            list.addAll(getAfterPicList());
            if (openEditModel) {
                if (list.size() > 0) {
                    list.remove(list.size() - 1);
                }
                list.addAll(pictures);
                list.add("");
            } else {
                list.addAll(pictures);
            }
            setNewData(list);
        }
    }

    /**
     * 绑定数据，根据数据，先行计算recyclerview高度，固定高度，防止多重滑动时候冲突
     *
     * @param pictures
     */
    public void setNewData(List<String> pictures) {
        if (pictures != null) {
            //如果是空数据并且是编辑模式，则添加一条空数据
            if (pictures.size() == 0 && openEditModel) {
                pictures.add("");
            }
            helpers = new LinkedList<>();
            gridLayoutHelper = new GridLayoutHelper(6);
            gridLayoutHelper.setGap(itemMargin);
            gridLayoutHelper.setHGap(itemMargin);
            onePlusHelper = new OnePlusNLayoutHelper(3);
            mTip.setVisibility(canDrag ? VISIBLE : INVISIBLE);

            final int num = pictures.size();
            ViewGroup.LayoutParams layoutParams = mRecycler.getLayoutParams();

            int displayW = getDisplayWidth(mContext);
            //动态计算显示的宽高
            int width = displayW;
            int height = (int) (displayW * 0.33);

            if (num == 1) {
                height = layoutParams.width;
            } else if (num == 2) {
                height = (int) (displayW * 0.5);
            } else if (num == 3) {
                height = (int) (displayW * 0.66) - itemMargin - itemMargin / 2;
            } else if (num == 4) {
                height = (int) (displayW * 0.5) + itemMargin + (int) (displayW * 0.33);
            } else if (num == 5) {
                height = (int) (displayW * 0.5) + itemMargin + (int) (displayW * 0.33);
            } else if (num == 6) {
                height = (int) (displayW * 0.66) + (int) (displayW * 0.33) - itemMargin / 2;
            } else if (num == 7 || num == 8) {
                height = (int) (displayW * 0.5) + 2 * itemMargin + (int) (displayW * 0.33) * 2;
            } else {
                height = (int) ((displayW * 0.33) * 3 + 3 * itemMargin - itemMargin / 2);
            }

            layoutParams.width = width;
            layoutParams.height = height;
            System.out.println("宽高：" + layoutParams.width + "--" + layoutParams.height);
            mRecycler.setLayoutParams(layoutParams);
            //根据数量和位置 设置span占比
            gridLayoutHelper.setSpanSizeLookup(new GridLayoutHelper.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (num == 1) {
                        return 6;
                    } else if (num == 2) {
                        return 3;
                    } else if (num == 3) {
                        return 2;
                    } else if (num == 4) {
                        if (position == 0) {
                            return 6;
                        } else {
                            return 2;
                        }
                    } else if (num == 5) {
                        if (position == 0 || position == 1) {
                            return 3;
                        } else {
                            return 2;
                        }
                    } else if (num == 6) {
                        return 2;
                    } else if (num == 7) {
                        if (position == 0) {
                            return 6;
                        } else {
                            return 2;
                        }
                    } else if (num == 8) {
                        if (position == 0 || position == 1) {
                            return 3;
                        } else {
                            return 2;
                        }
                    } else {
                        return 2;
                    }
                }
            });
            helpers.clear();
            if (num == 3) {
                helpers.add(onePlusHelper);
                gridLayoutHelper.setItemCount(0);
            } else {
                if (num == 6) {
                    helpers.add(onePlusHelper);
                    gridLayoutHelper.setItemCount(3);
                } else {
                    gridLayoutHelper.setItemCount(num);
                }
                helpers.add(gridLayoutHelper);
            }
            layoutManager.setLayoutHelpers(helpers);
            mRecycler.setLayoutManager(layoutManager);

            mRecycler.setAdapter(mNice9Adapter);
            mNice9Adapter.openEditModel(openEditModel);
            mNice9Adapter.bindData(pictures);

            if (canDrag) {
                if (!isShowTip && pictures.size() > 1) {
                    mRecycler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mTip.setVisibility(View.INVISIBLE);
                        }
                    }, 500);
                    isShowTip = true;
                }
                itemTouchHelper = new ItemTouchHelper(new Nice9ItemTouchCallback(mNice9Adapter).setOnDragListener(this));
                itemTouchHelper.attachToRecyclerView(mRecycler);
                mRecycler.addOnItemTouchListener(new OnRecyclerItemClickListener(mRecycler) {
                    @Override
                    public void onItemClick(RecyclerView.ViewHolder vh) {
                    }

                    @Override
                    public void onLongClick(RecyclerView.ViewHolder vh) {
                        itemTouchHelper.startDrag(vh);
                    }
                });
            }
        }

    }

    /**
     * 获取更改后的图片列表
     **/
    public List<String> getAfterPicList() {
        return mNice9Adapter.getPictures();
    }

    @Override
    public void onFinishDrag() {
        mNice9Adapter.notifyDataSetChanged();
    }


    public interface ItemDelegate {
        void onItemRemoveClick(int position);

        //与onAddClick 不同时回调
        void onItemClick(int position);
    }
}
