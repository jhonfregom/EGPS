package com.example.jhonfregom.egps;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.UbicacionviewHolder> {

    List<GPSActivity>ubicaciones;

    public Adapter(List<GPSActivity> ubicaciones) {
    }


    @Override
    public UbicacionviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler, parent, false);
    UbicacionviewHolder holder = new UbicacionviewHolder(v);
    return holder;

    }

    @Override
    public void onBindViewHolder(UbicacionviewHolder holder, int position) {
        GPSActivity gps = ubicaciones.get(position);
        holder.textViewinicial.setText((CharSequence) gps.getmPosicion());
        holder.textViewfinal.setText((CharSequence) gps.getmPosicionF());
    }

    @Override
    public int getItemCount() {
        return ubicaciones.size();
    }

    public static class UbicacionviewHolder extends RecyclerView.ViewHolder{

        TextView textViewinicial, textViewfinal;



        public UbicacionviewHolder(View itemView) {
            super(itemView);
            textViewinicial = itemView.findViewById(R.id.tvposicioninicial);
            textViewfinal = itemView.findViewById(R.id.tvposicionfinal);
        }
    }
}
