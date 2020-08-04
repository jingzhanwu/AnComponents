package com.mti.component.master.view.template

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import com.mcxtzhang.indexlib.suspension.SuspensionDecoration
import com.mti.component.master.R
import com.mti.component.master.adapter.SimpleContactAdapter
import com.mti.component.master.base.BaseActivity
import com.mti.component.master.model.ContactEntry
import kotlinx.android.synthetic.main.activity_simple_contact_template.*
import java.util.*

/**
 * @company 上海道枢信息科技-->
 * @anthor created by 薛兵
 * @date 2020/5/21
 * @change
 * @describe 简易通讯录（即当前组织下人员列表）
 **/
class SimpleContactTemplateActivity : BaseActivity() {

    private var mAdapter: SimpleContactAdapter? = null
    private var mDecoration: SuspensionDecoration? = null
    private var layoutManager: LinearLayoutManager? = null

    /**
     * 数据总数
     */
    private var dataList = mutableListOf<ContactEntry>()

    override fun getLayoutId(): Int = R.layout.activity_simple_contact_template

    override fun initViews(savedInstanceState: Bundle?) {
        setToolbarTitle("简易通讯录模板")
        showToolbarBackView()
        initData()
        initUi()

    }

    private fun initData() {
        //A类数据
        dataList.add(ContactEntry().setName("艾炳龙").setMobilenum("18968925068"))
        //b
        dataList.add(ContactEntry().setName("白洪密").setMobilenum("13868805365"))
        dataList.add(ContactEntry().setName("白永超").setMobilenum("13588296431"))
        dataList.add(ContactEntry().setName("包长安").setMobilenum("15158515959").setJobnumber("133852"))

        //c类数据
        dataList.add(ContactEntry().setName("蔡程阳").setMobilenum("13806885894"))
        dataList.add(ContactEntry().setName("陈胜祥").setMobilenum("13587636629").setJobnumber("033170"))
        //d
        dataList.add(ContactEntry().setName("戴杰客").setMobilenum("15825655388"))
        //f
        dataList.add(ContactEntry().setName("方建青").setMobilenum("18805771101").setJobnumber("033219"))
        //g
        dataList.add(ContactEntry().setName("高炳南").setMobilenum("18066295678"))

        //h
        dataList.add(ContactEntry().setName("韩小春").setMobilenum("13857758897"))
        dataList.add(ContactEntry().setName("何德智").setMobilenum("13968860074").setJobnumber("039924"))
        //j
        dataList.add(ContactEntry().setName("贾磊").setMobilenum("15157763906"))
        dataList.add(ContactEntry().setName("姜蓓蕾").setMobilenum("13705886677").setJobnumber("033631"))
        dataList.add(ContactEntry().setName("姜宏").setMobilenum("15258777899"))
        //k
        dataList.add(ContactEntry().setName("孔浩杰").setMobilenum("15990705875").setJobnumber("133778"))
        //l
        dataList.add(ContactEntry().setName("梁毅").setMobilenum("13506513206"))
        //z
        dataList.add(ContactEntry().setName("张宝林").setMobilenum("13758439289"))


    }


    private fun initUi() {

        ivDelete.setOnClickListener {
            mAdapter?.let {
                if (it.isSearching) {
                    etInput.setText("")
                }
            }
        }

        etInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                onSearchChanged(s!!)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
        layoutManager = LinearLayoutManager(this)
        initIndexBar()
        iniRecycler()

        //indexbar初始化
        //设置HintTextView
        indexBar.setmPressedShowTextView(tvSideBarHint) //设置需要真实的索引
                .setNeedRealIndex(true) //设置RecyclerView的LayoutManager
                .setmLayoutManager(layoutManager!!)
        rendContactList(dataList)
    }


    /**
     * 初始化列表
     */
    private fun iniRecycler() {
        recyclerView.layoutManager = layoutManager
        mAdapter = SimpleContactAdapter(this, listOf())
        recyclerView.adapter = mAdapter
        mDecoration = SuspensionDecoration(this, listOf())
        mDecoration?.setColorTitleBg(Color.parseColor("#f6f6f6"))
        mDecoration?.setColorTitleFont(resources.getColor(R.color.gray))
        recyclerView.addItemDecoration(mDecoration!!)
    }


    /**
     * 侧边栏索引
     */
    private fun initIndexBar() {
        val clazz: Class<*> = indexBar.javaClass
        try {
            val field = clazz.getDeclaredField("mPaint")
            field.isAccessible = true
            val o = field[indexBar]
            val paintClazz: Class<*> = o.javaClass
            val method = paintClazz.getMethod("setColor", Int::class.javaPrimitiveType)
            method?.isAccessible = true
            method?.invoke(o, resources.getColor(R.color.gray_font_dark))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    /**
     * 输入监听变化
     * @param editable
     */
    fun onSearchChanged(editable: Editable) {
        val text = editable.toString()
        if (TextUtils.isEmpty(text)) {
            mAdapter!!.setSearching(false, "")
            rendContactList(dataList)
            ivDelete.setImageResource(R.drawable.ic_search)
            return
        }
        val list: List<ContactEntry> = dataList
                .filter { e -> e.name.contains(text) }.toList()
        mAdapter!!.setSearching(true, text)
        ivDelete.setImageResource(R.drawable.ic_clear)
        rendContactList(list)
    }


    private fun rendContactList(list: List<ContactEntry>) {
        mAdapter?.setNewData(list)
        //设置数据
        indexBar.setmSourceDatas(list)
                .invalidate()
        mDecoration!!.setmDatas(list)
    }
}