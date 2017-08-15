package nz.co.tsg.themedapp.ui.foldingtabbar;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import nz.co.tsg.themedapp.ThemedApplication;
import nz.co.tsg.themedapp.R;
import nz.co.tsg.themedapp.ui.Branding;
import nz.co.tsg.themedapp.utilities.ColorUtil;

import java.util.List;

/**
 * Created by andrewkhristyan on 12/9/16.
 */

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ViewHolder> {

    private List<ChatModel> mChatModels = ChatDataManager.getChatModels();

    ThemedApplication mApp;
    Branding mBranding;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);

        ThemedApplication mApp = (ThemedApplication)((Activity) (view.getContext())).getApplication();
        mBranding = mApp.getmBranding();

        if (ColorUtil.isLuminanceDark(mBranding.getMainBackgroundColor())) {
            view.setBackgroundColor(mBranding.getColorDarkShade());
        } else {
            view.setBackgroundColor(mBranding.getColorLightShade());
        }


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.chatName.setText(mChatModels.get(position).getUserName());
        holder.lastMessage.setText(mChatModels.get(position).getLastMessage());
        holder.time.setText(mChatModels.get(position).getTime());
        holder.userImage.setImageResource(mChatModels.get(position).getChatImageRes());
        holder.divider.setBackgroundColor(mBranding.getMainBackgroundColor());

    }

    @Override
    public int getItemCount() {
        return mChatModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView chatName;
        private TextView lastMessage;
        private ImageView userImage;
        private TextView time;
        private View divider;

        public ViewHolder(View itemView) {
            super(itemView);
            chatName = (TextView) itemView.findViewById(R.id.text_view_chat_name);
            lastMessage = (TextView) itemView.findViewById(R.id.text_view_last_message);
            time = (TextView) itemView.findViewById(R.id.text_view_time);
            userImage = (ImageView) itemView.findViewById(R.id.image_view_chat);
            divider = (View) itemView.findViewById(R.id.chat_divider);

            if (ColorUtil.isLuminanceDark(mBranding.getMainBackgroundColor())) {
                chatName.setTextColor(mBranding.getColorLightShade());
                lastMessage.setTextColor(mBranding.getTextColorOverDarkShade());
                time.setTextColor(mBranding.getTextColorOverDarkShade());
            } else {
                chatName.setTextColor(mBranding.getColorDarkShade());
                lastMessage.setTextColor(mBranding.getTextColorOverLightShade());
                time.setTextColor(mBranding.getTextColorOverLightShade());
            }


        }


    }
}
