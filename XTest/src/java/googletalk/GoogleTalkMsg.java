package googletalk;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Message.Type;
import org.jivesoftware.smack.packet.Presence;
 

public class GoogleTalkMsg {
    public static void main(String[] args) throws XMPPException {
        ConnectionConfiguration connConfig = new ConnectionConfiguration("talk.google.com", 5222, "gmail.com");
        XMPPConnection connection = new XMPPConnection(connConfig);
        // 구글 톡 서버에 접속
        connection.connect();
        // 로그인.
        connection.login("fcube.server@gmail.com", "wlsdjr2011");
 
        // 상태를 활성화로 바꾸어 준다.
        Presence presence = new Presence(Presence.Type.available);
        connection.sendPacket(presence);
 
        // 자신이 대화하고 싶은 상대와 채팅창을 연다.
        Chat newChat = connection.getChatManager().createChat("ppjjuu@gmail.com", new MessageListener() {
            public void processMessage(Chat chat, Message message) {
                System.out.println("Received message: " + message);
            }
        });
 
        try {
            Message message = new Message("ppjjuu@gmail.com", Type.chat);
            message.addBody("ko", "Hello. 가나다라..");
            newChat.sendMessage(message);
        } catch (XMPPException e) {
            throw new XMPPException(e);
        }
        connection.disconnect();
    }
}