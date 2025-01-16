package com.mmsx.app.personManagement;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mmsx.sqlapp.R;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>
        implements View.OnClickListener {
    private Context mContext;
    private List<Person> personList;
    private OnRecyclerViewItemClickListener mOnItemClickListener ;

    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
    public ListAdapter() {
        personList = new ArrayList<>();
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v,(Person)v.getTag());
        }
    }
    @Override
    public void onViewRecycled(ViewHolder holder){
        holder.itemView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, Person note);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Log.i(TAG, "###onCreateViewHolder: ");
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_person,parent,false);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //Log.i(TAG, "###onBindViewHolder: ");
        final Person note = personList.get(position);
        //The data is saved in the Tag of the itemView so that it can be retrieved when clicked
        holder.itemView.setTag(note);
        //Log.e("adapter", "###record="+record);
        holder.tv_list_title.setText(note.getName());
        StringBuffer buffer = new StringBuffer();
        buffer.append("id:").append(note.getNumber()).append("    ");
        buffer.append("sex:").append(note.getSex()).append("    ");
        buffer.append("age:").append(note.getAge()).append("\n");
        buffer.append("department:").append(note.getDepartment()).append("    ");
        buffer.append("post:").append(note.getJob()).append("\n");
        buffer.append("salary level:").append(note.getGrade()).append("    ");
        buffer.append("salary:").append(note.getSalary()).append("\n");
        buffer.append("phone number:").append(note.getPhone()).append("\n");

        buffer.append("Total working hours:").append(note.getRewards()).append(note.getMoney()).append("\n");
        buffer.append("joining year:").append(note.getFamily()).append("\n");
        buffer.append("joiningdate:").append(note.getFname());
        holder.tv_list_summary.setText(buffer.toString());

    }

    @Override
    public int getItemCount() {
        //Log.i(TAG, "###getItemCount: ");
        if (personList != null && personList.size()>0){
            return personList.size();
        }
        return 0;
    }


    //A custom ViewHolder that holds all interface elements for each Item
    public class ViewHolder extends RecyclerView.ViewHolder   {
        public TextView tv_list_title;//Note heading
        public TextView tv_list_summary;//Note summary

        public ViewHolder(View view){
            super(view);
            tv_list_title = (TextView) view.findViewById(R.id.item_main_note_title_view);
            tv_list_summary = (TextView) view.findViewById(R.id.item_main_note_content_view);
        }


    }
}
