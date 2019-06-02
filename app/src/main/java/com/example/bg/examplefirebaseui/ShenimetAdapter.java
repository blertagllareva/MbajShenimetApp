package com.example.bg.examplefirebaseui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import org.w3c.dom.Text;

public class ShenimetAdapter extends FirestoreRecyclerAdapter<Shenimet, ShenimetAdapter.ShenimetHolder>
{
     public onItemClickListener listener;
     /**
      * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
      * FirestoreRecyclerOptions} for configuration options.
      *
      * @param options
      */
     public ShenimetAdapter(@NonNull FirestoreRecyclerOptions<Shenimet> options) {
          super(options);
     }

     @Override
     protected void onBindViewHolder(@NonNull ShenimetHolder holder, int position, @NonNull Shenimet model) {

          holder.tv_titulli.setText(model.getTitulli());
          holder.tv_pershkrimi.setText(model.getPershkrimi());
          holder.tv_prioriteti.setText(String.valueOf(model.getPrioriteti()));

     }

     @NonNull
     @Override
     public ShenimetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
     {
          View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shenimet_item, parent, false);
          return new ShenimetHolder(v);
     }

     public void fshijShenimet(int pozita)
     {
          getSnapshots().getSnapshot(pozita).getReference().delete();
     }


     class ShenimetHolder extends RecyclerView.ViewHolder
     {
          TextView tv_titulli;
          TextView tv_pershkrimi;
          TextView tv_prioriteti;
          public ShenimetHolder(@NonNull View itemView) {
               super(itemView);
               tv_titulli = itemView.findViewById(R.id.text_view_title);
               tv_pershkrimi = itemView.findViewById(R.id.text_view_description);
               tv_prioriteti = itemView.findViewById(R.id.text_view_priority);

               itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                         int pozita = getAdapterPosition();
                         if(pozita != RecyclerView.NO_POSITION && listener != null)
                         {
                              listener.onItemClick(getSnapshots().getSnapshot(pozita), pozita);
                         }
                    }
               });
          }
     }

     public interface onItemClickListener
     {
          void onItemClick(DocumentSnapshot documentSnapshot, int pozita);
     }
     public void setOnItemClickListener(onItemClickListener listener)
     {
          this.listener = listener;
     }


}
