package com.dandanplay.tv2.ui.bangumi

import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.dandanplay.tv2.R
import com.dandanplay.tv2.ui.base.BaseFragment
import com.dandanplay.tv2.ui.base.pop
import com.dandanplay.tv2.ui.dialog.setLoadFragment
import com.dandanplay.tv2.utils.showUrlBlur
import com.seiko.common.ResultData
import com.seiko.common.Status
import com.dandanplay.tv2.vm.BangumiDetailViewModel
import com.seiko.common.utils.getBangumiStatus
import com.seiko.domain.entities.BangumiDetails
import com.seiko.domain.entities.BangumiTag
import kotlinx.android.synthetic.main.fragment_bangumi_detail.*
import org.koin.android.viewmodel.ext.android.viewModel

class BangumiDetailFragment : BaseFragment() {

    private val viewModel by viewModel<BangumiDetailViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.mainState.observe(this::getLifecycle, this::updateUI)
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        val bundle = arguments ?: return
        if (bundle.containsKey(ARGS_ANIME_ID)) {
            val animeId = bundle.getInt(ARGS_ANIME_ID)
            viewModel.getBangumiDetails(animeId)
        }
    }

    private fun updateUI(data: ResultData<BangumiDetails>) {
        when(data.responseType) {
            Status.LOADING -> {
                setLoadFragment(true)
            }
            Status.ERROR -> {
                setLoadFragment(false)
                ToastUtils.showShort(data.error.toString())
            }
            Status.SUCCESSFUL -> {
                setLoadFragment(false)
                updateDetails(data.data)

            }
        }
    }

    private fun updateDetails(details: BangumiDetails?) {
        if (details == null) {
            content_layout.visibility = View.GONE
            return
        }

        content_layout.visibility = View.VISIBLE
        bangumi_blur.showUrlBlur(details.imageUrl, 5,5)
        bangumi_img.setImageURI(details.imageUrl)
        bangumi_title.text = details.animeTitle
        bangumi_episo.text = details.getBangumiStatus()

        bangumi_info_describe.text = details.summary
        setTagInfo(details.tags)
    }

    private fun setTagInfo(tags: List<BangumiTag>) {
        if (tags.isEmpty()) {
            bangumi_tag_title.visibility = View.GONE
            bangumi_tag.visibility = View.GONE
        } else {
            bangumi_tag.text = tags.joinToString { it.name }
        }
    }

    override fun onBackPressedSupport(): Boolean {
        pop()
        return true
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_bangumi_detail
    }

    companion object {
        private const val ARGS_ANIME_ID = "ARGS_ANIME_ID"

        fun newInstance(animeId: Int): BangumiDetailFragment {
            val bundle = Bundle()
            bundle.putInt(ARGS_ANIME_ID, animeId)

            val fragment = BangumiDetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}