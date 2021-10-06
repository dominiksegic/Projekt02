package com.example.projekt02;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class FigureAdapter extends RecyclerView.Adapter <FigureAdapter.FigureHolder>{
    private List<Figure> figureList = new ArrayList<Figure>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public FigureHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.figure_item,parent,false);
        return new FigureHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FigureHolder holder, int position) {
        Figure currentFigure = figureList.get(position);
        holder.textViewId.setText(String.valueOf(currentFigure.getId()));
        holder.textViewName.setText(currentFigure.getName());
        holder.textViewDate.setText(currentFigure.getDate());
        holder.textViewSeries.setText(currentFigure.getSeries());
        holder.textViewRating.setText(String.valueOf(currentFigure.getRating()));
    }

    @Override
    public int getItemCount() {
        return figureList.size();
    }

    public void setFigureList(List<Figure> figureList) {
        this.figureList = figureList;
        notifyDataSetChanged();
    }

    public Figure getFigureAt(int position){
        return figureList.get(position);
    }

    class FigureHolder extends RecyclerView.ViewHolder{
        private TextView textViewId;
        private TextView textViewName;
        private TextView textViewDate;
        private TextView textViewSeries;
        private TextView textViewRating;

        public FigureHolder(@NonNull View itemView) {
            super(itemView);
            textViewId = itemView.findViewById(R.id.text_view_id);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewDate = itemView.findViewById(R.id.text_view_date);
            textViewSeries = itemView.findViewById(R.id.text_view_series);
            textViewRating = itemView.findViewById(R.id.text_view_rating);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(figureList.get(position));
                    }

                }
            });
        }
    }
    public interface OnItemClickListener{
        void onItemClick(Figure figure);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }
}
