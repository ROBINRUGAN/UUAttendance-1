package com.uu.attendance.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.hjq.toast.Toaster
import com.uu.attendance.databinding.FragmentSuperviseCardBinding
import com.uu.attendance.model.bean.StudentBean
import com.uu.attendance.ui.activity.SuperviseViewModel
import com.uu.attendance.ui.adapter.SuperviseCardAdapter
import com.uu.attendance.ui.view.cardswipelayout.CardConfig
import com.uu.attendance.ui.view.cardswipelayout.CardItemTouchHelperCallback
import com.uu.attendance.ui.view.cardswipelayout.CardLayoutManager
import com.uu.attendance.ui.view.cardswipelayout.OnSwipeListener


class SuperviseCardFragment : BaseFragment<FragmentSuperviseCardBinding>() {

    lateinit var viewModel: SuperviseViewModel

//    private lateinit var mCardLayoutHelper : CardLayoutHelper<StudentBean>
//    private lateinit var mConfig : CardLayoutHelper.Config

    companion object {
        val instance: SuperviseCardFragment by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            SuperviseCardFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(SuperviseViewModel::class.java)

        binding.etSearch.setOnClickListener {
            viewModel.isSearch.value = true
        }

        val list = mutableListOf(
            StudentBean(1, "张三", 2019210001, "", 0),
            StudentBean(2, "李四", 2019210002, "", 0),
            StudentBean(3, "王五", 2019210003, "", 0),
            StudentBean(4, "赵六", 2019210004, "", 0),
            StudentBean(5, "孙七", 2019210005, "", 0),
            StudentBean(6, "周八", 2019210006, "", 0),
        )
        val recyclerView = binding.rvSuperviseList

//
//        mCardLayoutHelper = CardLayoutHelper()
//
//        mConfig = CardLayoutHelper.Config()
//            .setCardCount(2)
//            .setMaxRotation(20f)
//            .setOffset(8.dp)
//            .setSwipeThreshold(0.2f)
//            .setDuration(200)
//
//        mCardLayoutHelper.setConfig(mConfig)
//
//        mCardLayoutHelper.attachToRecyclerView(recyclerView)
//
//        mCardLayoutHelper.bindDataSource(object : CardLayoutHelper.BindDataSource<StudentBean> {
//            override fun bind(): List<StudentBean> {
//                return list
//            }
//        })
//
//        mCardLayoutHelper.setOnCardLayoutListener(object : OnCardLayoutListener {
//            override fun onSwipe(dx: Float, dy: Float) {
//                Log.d("onStateChanged","dx:$dx dy:$dy")
//            }
//
//            override fun onStateChanged(state: CardLayoutHelper.State) {
//                Log.d("onStateChanged",state.name)
//
//            }
//
//        })
//        val adapter = SuperviseCardAdapter(list)
//        recyclerView.adapter = adapter


        val adapter = SuperviseCardAdapter(list)
        val cardCallback = CardItemTouchHelperCallback(adapter, list)
        val touchHelper = ItemTouchHelper(cardCallback)
        val cardLayoutManager = CardLayoutManager(recyclerView, touchHelper)
        recyclerView.layoutManager = cardLayoutManager
        touchHelper.attachToRecyclerView(recyclerView)
        cardCallback.setOnSwipedListener(object : OnSwipeListener<StudentBean> {
            override fun onSwiping(
                viewHolder: RecyclerView.ViewHolder,
                ratio: Float,
                direction: Int
            ) {
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, t: StudentBean, direction: Int) {
                Toaster.show(t.name + ": " + when(direction){
                    CardConfig.SWIPED_LEFT -> "未到"
                    else -> "签到"
                })
                adapter.getOneMoreData()
            }

            override fun onSwipedClear() {
                Toaster.show("所有任务已完成")
            }
        })
        recyclerView.adapter = adapter
    }

}