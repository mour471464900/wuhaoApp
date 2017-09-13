package com.anfeng.wuhao.anfengkuaikan.ui.fragment.cartoon

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.anfeng.wuhao.anfengkuaikan.R
import com.anfeng.wuhao.anfengkuaikan.base.BaseKotlinActivity
import com.anfeng.wuhao.anfengkuaikan.bean.HotWord
import com.anfeng.wuhao.anfengkuaikan.event.TagEvent
import com.anfeng.wuhao.anfengkuaikan.inter.RequestCallback
import com.anfeng.wuhao.anfengkuaikan.net.HttpHelper
import com.anfeng.wuhao.anfengkuaikan.utils.GsonUtils
import com.anfeng.wuhao.anfengkuaikan.utils.LogUtil
import com.anfeng.wuhao.anfengkuaikan.utils.ToastUtil
import com.anfeng.wuhao.anfengkuaikan.utils.URLCommon
import com.anfeng.wuhao.anfengkuaikan.ui.view.Tag
import com.anfeng.wuhao.anfengkuaikan.ui.view.TagCloudLinkView
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_search_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 * Created by Administrator on 2017/8/12.
 */
class SearchActivity : BaseKotlinActivity() {

    var since: Int = 0
    var isHotWordNoMore: Boolean = false
    val hotWordList: ArrayList<HotWord.DataBean.HotWordBean> = arrayListOf() //热词搜索
    val historyList: ArrayList<Any> = arrayListOf() // 历史记录
    override fun setMainView(): Int {
        return R.layout.activity_search_main
    }

    override fun initView() {
        RxView.clicks(tvCancel).subscribe {
            onBackPressed()
        }
        RxView.clicks(ivChange).subscribe {
            when (since) {
                -1 -> changeHotWord()
                else -> {
                    requestHotWord()
                }
            }
        }
        RxView.clicks(ivDelete).subscribe {
            clearHistory()
        }
        etSearch.addTextChangedListener(etSearchListener())
        etSearch.setOnEditorActionListener(etSearchActionListener())
        tagHotWord.setOnTagSelectListener(TagSelectListener())
        tagHistory.setOnTagSelectListener(TagSelectListener())
    }

    inner class TagSelectListener : TagCloudLinkView.OnTagSelectListener {
        override fun onTagSelected(tag: Tag?, position: Int) {
            var t: Tag = tag as Tag
            etSearch.setText(t.text)
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    inner class etSearchActionListener : TextView.OnEditorActionListener {
        override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                if (imm.isActive) {
                    imm.hideSoftInputFromWindow(v?.applicationWindowToken, 0)
                }
                if (etSearch.text.toString().isEmpty()) {
                    ToastUtil.toastShort(etSearch.hint.toString())
                } else {
                    searchCartoon()
                }
            }
            return false
        }
    }

    /**
     * 搜索漫画
     */
    private fun searchCartoon() {
        LogUtil.e("search", "搜索漫画")
    }

    inner class etSearchListener : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (s.toString().isEmpty()) {
                ivDelete.visibility = View.GONE
            } else {
                ivDelete.visibility = View.VISIBLE
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            etSearch.setSelection(start + count)
        }

    }

    private fun changeHotWord() {

    }

    private fun clearHistory() {
        tagHistory.tags.clear()
        history.visibility = View.GONE
    }

    override fun requestData() {
        requestHotWord()
    }

    private fun requestHotWord() {
        HttpHelper.getInstance().httpGetString(URLCommon.getNewHotWord(since), this, HotWordRequest())
    }

    inner class HotWordRequest : RequestCallback<String> {
        override fun errorForCode(code: Int) {
            when (code) {
                0 -> ToastUtil.toastLong("服务器返回异常")
                1 -> ToastUtil.toastLong("网络错误")
            }
        }

        override fun succeedOnResult(response: String?) {
            try {
                var hotWord = GsonUtils.GsonToBean(response, HotWord::class.java)
                if (hotWord == null) errorForCode(0)
                when (hotWord.data.since) {
                    -1 -> isHotWordNoMore = true
                    else -> {
                        since = hotWord.data.since
                        hotWordList.addAll(hotWord.data.hot_word)
                        refreshHotWord()
                        EventBus.getDefault().post(TagEvent("测试事件总线"))
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                errorForCode(0)
            }
        }

    }

    private fun refreshHotWord() {
        tagHotWord.tags.clear()
        when (isHotWordNoMore) {
            true ->{      // 表示所有的数据全部加载完毕
                var index=10
                (index-10 until index).
                        map { hotWordList[it] }.forEach{tagHotWord.add(Tag(1,it.target_title))}

            }
            false->{
                (since - 10 until since)  // 表示从该list集合， 包含 since-10 到 since 不包括since
                        .map { hotWordList[it] }
                        .forEach { tagHotWord.add(Tag(1, it.target_title)) }
            }

        }

        tagHotWord.drawTags()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun tagSuccess(tagEvent: TagEvent) {
        ToastUtil.toastShort(tagEvent.tag)
    }


}