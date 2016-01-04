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
        // ���� �� ������ ����
        connection.connect();
        // �α���.
        connection.login("fcube.server@gmail.com", "wlsdjr2011");
 
        // ���¸� Ȱ��ȭ�� �ٲپ� �ش�.
        Presence presence = new Presence(Presence.Type.available);
        connection.sendPacket(presence);
 
        // �ڽ��� ��ȭ�ϰ� ���� ���� ä��â�� ����.
        Chat newChat = connection.getChatManager().createChat("ppjjuu@gmail.com", new MessageListener() {
            public void processMessage(Chat chat, Message message) {
                System.out.println("Received message: " + message);
            }
        });
 
        try {
            Message message = new Message("ppjjuu@gmail.com", Type.chat);
            message.addBody("ko", "Hello. �����ٶ�..");
            newChat.sendMessage(message);
        } catch (XMPPException e) {
            throw new XMPPException(e);
        }
        connection.disconnect();
    }
}