package nz.co.tsg.themedapp.ui.foldingtabbar;

import java.util.ArrayList;
import java.util.List;
import nz.co.tsg.themedapp.R;

/**
 * Created by andrewkhristyan on 12/9/16.
 */

public class ChatDataManager {

    private static List<ChatModel> sChatModels = new ArrayList<>();

    static {
        sChatModels.add(new ChatModel("Ann Drewer", "Hello", R.drawable.ic_img_chat_one, "10 min ago"));
        sChatModels.add(new ChatModel("Ann Drewer", "It's me", R.drawable.ic_img_chat_one, "7 min ago"));
        sChatModels.add(new ChatModel("Ann Drewer", "I was wondering ..", R.drawable.ic_img_chat_one, "5 min ago"));
    }

    public static List<ChatModel> getChatModels() {
        return sChatModels;
    }
}
