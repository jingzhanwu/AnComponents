
## 如何使用
这里提供3d云标签效果，可以是文字标签，可以是图片标签，也可以自定义标签。
### 3d云标签组件

#### 1.布局使用
    引入第三方库
    implementation  'com.moxun:tagcloudlib:1.2.1'
```
<com.moxun.tagcloudlib.view.TagCloudView
                android:id="@+id/tagCloudView"
                style="@style/wrap_content"
                android:layout_margin="@dimen/size_10"
                app:autoScrollMode="uniform"
                app:darkColor="@color/colorPrimary"
                app:lightColor="@color/colorPrimaryDark"
                app:radiusPercent="0.6"
                app:scrollSpeed="3" />
```

#### 2.构造加载数据

构造标签适配器
```
public class TextTagsAdapter extends TagsAdapter {

    private List<String> dataSet = new ArrayList<>();

    public TextTagsAdapter(@NonNull String... data) {
        dataSet.clear();
        Collections.addAll(dataSet, data);
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public View getView(final Context context, final int position, ViewGroup parent) {
        TextView tv = new TextView(context);
        tv.setTextColor(Color.BLACK);
        tv.setText("No." + position);
        tv.setGravity(Gravity.CENTER);
        tv.setOnClickListener(v -> {
            Log.e("Click", "Tag " + position + " clicked.");
            Toast.makeText(context, "Tag " + position + " clicked", Toast.LENGTH_SHORT).show();
        });
        return tv;
    }

    @Override
    public Object getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public int getPopularity(int position) {
        return position % 7;
    }

    @Override
    public void onThemeColorChanged(View view, int themeColor) {
//        view.setBackgroundColor(themeColor);
    }

}

```

渲染数据
```
    override fun initViews(savedInstanceState: Bundle?) {
        //构造数据
        val textTagsAdapter = TextTagsAdapter(*arrayOfNulls(20))
        //绑定数据
        tagCloudView.setAdapter(textTagsAdapter)
    }
```

