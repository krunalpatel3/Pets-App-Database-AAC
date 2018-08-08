package krunal.com.example.petsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import krunal.com.example.petsapp.Database.PetsEntity;

/**
 * Created by acer on 13-03-2018.
 */

/**
 * Recycle View class.
 */
public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {

    private List<PetsModel> mList = new ArrayList<>();
    private LayoutInflater mInflater;
    private OnItemClickListener mOnClickListener;

    RecycleViewAdapter(Context context,OnItemClickListener onItemClickListener){
        this.mOnClickListener = onItemClickListener;
        this.mInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PetsModel current = mList.get(position);
        holder.mPetsName.setText(current.getPet_Name());
        holder.mPetsBreed.setText(current.getPet_Breed());
        // Get current pet at specific position from mlist var
        // And pass in bind method and also send mOnClickListener var.
        holder.Bind(mList.get(position),mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * List of Pet is Added to mlist var.
     * @param list
     */
    void add(List<PetsModel> list){
        this.mList = list;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mPetsName,mPetsBreed;

        public ViewHolder(View itemView) {
            super(itemView);
            mPetsName = itemView.findViewById(R.id.name);
            mPetsBreed = itemView.findViewById(R.id.summary);

        }

        /**
         * Bind to get OnClick current position and ClickListener.
         * @param petsModel
         * @param onItemClickListener
         */
        void Bind(final PetsModel petsModel, OnItemClickListener onItemClickListener){
            itemView.setOnClickListener(v ->
                    // send current position via Onclick method.
                    onItemClickListener.OnClick(petsModel));
        }
    }
}
