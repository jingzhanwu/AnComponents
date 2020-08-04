# SignatureComponent组件
## 如何使用
#### 1.属性方法说明（这里只列举比较常用的）

* setColor(int mColor)：设置画笔的颜色，默认为黑色
* setBackgroundColor(int mBackgroundColor)：设置背景颜色
* setStyle(Paint.Style style)：设置画笔的样式，默认实线
* setSize(float size)：设置画笔线段的宽度，默认5px
* setInteractionMode(int interactionMode)：设置交互模式，默认是DRAW_MODE模式，还有SELECT_MODE，ROTATE_MODE或LOCKED_MODE
* getInteractionMode()：返回当前的交互模式
* setBackgroundMode()：设置画板背景的模式，默认是纯白色模式
* undo()：撤销一步操作
* redo()：恢复上一步撤销的操作
* cleanPage()：清空当前画布，此操作一旦执行将无法恢复
* getCanvasBitmap()：获得当前画布上的所有对象
* markSaved()：标记当前画布上的对象已经保存
* isSaved()：当前画布的对象是否已经保存
* getUnsavedDrawablesList()：返回在调用markSaved方法之后添加的绘制对象集合
* revertUnsaved()：删除在调用markSaved方法之后添加的绘制对象，不触发DeletionConfirmationListener
* getCroppedCanvasBitmap()：获取目前画布上绘制的bitmap图像，删除绘制对象周围的所有边距
* getDrawablesList()：按插入顺序返回所有的绘制对象集合
* deletionConfirmed(CDrawable drawable)：删除经过确认的drawable对象
* setDeletionListener()：设置删除监听器，绘制对象被删除后回调
* setScaleRotateListener()：设置收拾缩放旋转监听器
* setDeletionConfirmationListener()：设置删除确认监听器，如果设置此监听，则必须手动调用deletionConfirmed()删除

#### 2.布局使用
```
 <com.mti.component.extend.SignatureComponent
                android:id="@+id/signatureView"
                android:layout_width="match_parent"
                android:layout_height="400dp"/>
```
#### 3.代码使用
```
  floatUndo.setOnClickListener(View.OnClickListener {
            println("点击了撤销")
            signatureView.undo()
        })
        floatClean.setOnClickListener(View.OnClickListener {
            println("点击了清空")
            signatureView.cleanPage()
        })
        floatSave.setOnClickListener {
            println("点击了保存")
            var result = signatureView.canvasBitmap
            if (result != null) {
                layoutResult.visibility = View.VISIBLE
                signatureBitmap.setImageBitmap(result)
            } else {
                layoutResult.visibility = View.GONE
            }
            signatureView.markSaved()
            signatureView.cleanPage()
        }

        signatureView.setDeletionListener { println("对象被删除$it") }
        signatureView.setScaleRotateListener(object : SignatureComponent.ScaleRotateListener {
            override fun endRotate() {
                println("结束旋转")
            }

            override fun startRotate(): Boolean {
                println("开始旋转")
                return true
            }
        })
```