package com.example.aubergine_practical.ui.workout

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.aubergine_practical.R
import com.example.aubergine_practical.databinding.ItemProgressBinding
import com.example.aubergine_practical.databinding.ItemWorkoutBinding
import com.example.aubergine_practical.model.ActivitiesModel
import kotlin.coroutines.coroutineContext

/**
 * Activities listing RecyclerView Adapter
 * @param mDataList contains list of Activities
 */
class WorkoutAdapter(private val mDataList: ArrayList<ActivitiesModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        /**
         * Used for storing loading indicator type
         */
        const val TYPE_LOAD_MORE = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_LOAD_MORE -> {
                LoadMoreViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_progress, parent, false
                    )
                )
            }
            else -> {
                WorkoutViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_workout, parent, false
                    )
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int = mDataList[position].doLoadMore

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is WorkoutViewHolder) {
            holder.mBinding.apply {
                with(mDataList[position]) {
                    holder.mBinding.activities = this
                    if (activityName == "Walk"){
                        holder.mBinding.ivType.setImageDrawable(holder.itemView.context.getDrawable(R.drawable.ic_walking))
                    }else if (activityName == "Run"){
                        holder.mBinding.ivType.setImageDrawable(holder.itemView.context.getDrawable(R.drawable.ic_running))
                    }else if (activityName == "Bike"){
                        holder.mBinding.ivType.setImageDrawable(holder.itemView.context.getDrawable(R.drawable.ic_bike))
                    }else{
                        holder.mBinding.ivType.setImageDrawable(holder.itemView.context.getDrawable(R.drawable.ic_swimming))
                    }
                    holder.mBinding.tvCalories.text =
                        String.format(holder.itemView.context.getString(R.string.text_calories), calories)


                }
            }

        }
    }

    override fun getItemCount(): Int = mDataList.size

    /**
     * to clear the all the list data
     */
    fun clearActivitiesData() {
        mDataList.clear()
        notifyDataSetChanged()
    }

    /**
     * To store and display more pagination data
     * @param list list of activities data
     */
    fun updateMoreActivities(list: ArrayList<ActivitiesModel>) {
        val startIndex = mDataList.size
        mDataList.addAll(list)
        notifyItemRangeChanged(startIndex, mDataList.size)
    }

    /**
     * This view holder class is used for activities
     */
    inner class WorkoutViewHolder(itemView: ItemWorkoutBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val mBinding = itemView
    }

    /**
     * This view holder class is used for loading indicator
     */
    class LoadMoreViewHolder(itemView: ItemProgressBinding) :
        RecyclerView.ViewHolder(itemView.root)

}