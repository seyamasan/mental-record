package com.example.mentalrecordapplication.record_mood.view

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mentalrecordapplication.R
import com.example.mentalrecordapplication.record_mood.param.Mood
import com.example.mentalrecordapplication.room.MoodEntity

class RecordListAdapter(
    private val context: Context?,
    private val items: List<MoodEntity>,
    private val onItemClick: (MoodEntity) -> Unit // クリックリスナを引数として受け取る
) : RecyclerView.Adapter<RecordListAdapter.RecordListViewHolder>() {

    inner class RecordListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val moodButton: TextView = itemView.findViewById(R.id.moodButton)

        init {
            itemView.setOnClickListener {
                onItemClick(items[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_record_list_view, parent, false)
        return RecordListViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecordListViewHolder, position: Int) {
        holder.dateTextView.text = items[position].date
        setMoodButton(holder, items[position].mood)
    }

    override fun getItemCount(): Int = items.size

    private fun setMoodButton(holder: RecordListViewHolder, mood: String) {
        when (mood) {
            Mood.HAPPY.getMood() -> {
                holder.moodButton.text = Mood.HAPPY.getMood()
                holder.moodButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_happy, 0, 0)
                if (context != null) {
                    holder.moodButton.compoundDrawableTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.salmon_pink))
                }
            }
            Mood.ANGER.getMood() -> {
                holder.moodButton.text = Mood.ANGER.getMood()
                holder.moodButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_anger, 0, 0)
                if (context != null) {
                    holder.moodButton.compoundDrawableTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.dahlia_purple))
                }
            }
            Mood.SAD.getMood() -> {
                holder.moodButton.text = Mood.SAD.getMood()
                holder.moodButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_sad, 0, 0)
                if (context != null) {
                    holder.moodButton.compoundDrawableTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.cerulean_blue))
                }
            }
            Mood.FUN.getMood() -> {
                holder.moodButton.text = Mood.FUN.getMood()
                holder.moodButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.icon_fun, 0, 0)
                if (context != null) {
                    holder.moodButton.compoundDrawableTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.chartreuse_yellow))
                }
            }
        }
    }
}