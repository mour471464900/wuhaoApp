package com.anfeng.wuhao.anfengkuaikan.activity

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
import com.anfeng.wuhao.anfengkuaikan.inter.RequestCallback
import com.anfeng.wuhao.anfengkuaikan.net.HttpHelper
import com.anfeng.wuhao.anfengkuaikan.utils.GsonUtils
import com.anfeng.wuhao.anfengkuaikan.utils.ToastUtil
import com.anfeng.wuhao.anfengkuaikan.utils.URLCommon
import com.anfeng.wuhao.anfengkuaikan.view.Tag
import com.anfeng.wuhao.anfengkuaikan.view.TagCloudLinkView
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.activity_search_main.*


/**
 * Created by Administrator on 2017/8/12.
 */
class SearchActivity : BaseKotlinActivity() {

    var since: Int = 0
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
                1 -> ToastUtil.toastLong("网络错误")
            }
        }

        override fun succeedOnResult(response: String?) {
            try {
                var hotWord = GsonUtils.GsonToBean(response, HotWord::class.java)
                if (hotWord.data.since != -1) {
                    since = hotWord.data.since
                    hotWordList.addAll(hotWord.data.hot_word)
                    refreshHotWord()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                errorForCode(0)
            }
        }

    }

    private fun refreshHotWord() {
        tagHotWord.tags.clear()
        var listHot: ArrayList<HotWord.DataBean.HotWordBean> = arrayListOf()

        for (item in hotWordList) {
            tagHotWord.add(Tag(1, item.target_title))
        }
        tagHotWord.drawTags()
    }

}