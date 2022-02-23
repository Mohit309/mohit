package com.example.chatting_app.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatting_app.Models.MessageModels;
import com.example.chatting_app.R;
import com.github.pgreze.reactions.ReactionPopup;
import com.github.pgreze.reactions.ReactionsConfig;
import com.github.pgreze.reactions.ReactionsConfigBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatAdapters extends RecyclerView.Adapter{

    ArrayList<MessageModels> messageModels;
    Context context;
    String recId;


    int SENDER_VIEW_TYPE = 1;
    int RECEIVER_VIEW_TYPE = 2;

    public ChatAdapters(ArrayList<MessageModels> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
    }

    public ChatAdapters(ArrayList<MessageModels> messageModels, Context context, String recId) {
        this.messageModels = messageModels;
        this.context = context;
        this.recId = recId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType ==SENDER_VIEW_TYPE)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender, parent, false);
            return new SenderViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_reciver, parent, false);
            return  new ReceiverViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (messageModels.get(position).getuId().equals(FirebaseAuth.getInstance().getUid())){
            return SENDER_VIEW_TYPE;
        }
        else {
            return RECEIVER_VIEW_TYPE;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MessageModels messageModels1 = messageModels.get(position);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                new  AlertDialog.Builder(context)
                        .setTitle("Delete")
                        .setMessage("Are you sure you want to delete this message")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                String senderRoom = FirebaseAuth.getInstance().getUid() + recId;
                                database.getReference().child("Chats").child(senderRoom)
                                        .child(messageModels1.getMessageId())
                                        .setValue(null);

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setCancelable(false)
                        .show();

                return false;
            }
        });

        int reactions[] = new int[]{
            R.drawable.ic_fb_like,
                    R.drawable.ic_fb_love,
                    R.drawable.ic_fb_laugh,
                    R.drawable.ic_fb_wow,
                    R.drawable.ic_fb_sad,
                    R.drawable.ic_fb_angry
        };
        ReactionsConfig config = new ReactionsConfigBuilder(context)
                .withReactions(reactions)
                .build();

        ReactionPopup popup = new ReactionPopup(context, config, (pos) -> {
            if (holder.getClass() == SenderViewHolder.class) {
                SenderViewHolder viewHolder = (SenderViewHolder)holder;
                viewHolder.senderMsg.setImeOptions(reactions[pos]);
                viewHolder.senderMsg.setVisibility(View.VISIBLE);
            } else {
                ReceiverViewHolder viewHolder = (ReceiverViewHolder)holder;
                viewHolder.receiverMsg.setImeOptions(reactions[pos]);
                viewHolder.receiverMsg.setVisibility(View.VISIBLE);
            }
            return true; // true is closing popup, false is requesting a new selection
        });

        if (holder.getClass() == SenderViewHolder.class){
           /* ((SenderViewHolder)holder).senderMsg.setText(messageModels1.getMessage());
*/          SenderViewHolder viewHolder = (SenderViewHolder)holder;


          viewHolder.senderMsg.setText(messageModels1.getMessage());

          viewHolder.senderMsg.setOnTouchListener(new View.OnTouchListener() {
              @Override
              public boolean onTouch(View v, MotionEvent event) {
                  popup.onTouch(v, event);
                  return false;
              }
          });

        }
        else {
            /*((ReceiverViewHolder)holder).receiverMsg.setText(messageModels1.getMessage());*/

            ReceiverViewHolder viewHolder = (ReceiverViewHolder)holder;
            viewHolder.receiverMsg.setText(messageModels1.getMessage());

            viewHolder.receiverMsg.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    popup.onTouch(v, event);
                    return false;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public class ReceiverViewHolder extends RecyclerView.ViewHolder {

        TextView receiverMsg, receiverTime;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);

            receiverMsg = itemView.findViewById(R.id.receiverText);
            receiverTime = itemView.findViewById(R.id.receiverTime);
        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder {

        TextView senderMsg, senderTime;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);

            senderMsg = itemView.findViewById(R.id.senderText);
            senderTime = itemView.findViewById(R.id.senderTime);
        }
    }

}
