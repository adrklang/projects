import com.lhstack.listener.ActivemqMessageListener;
import org.junit.Test;
import org.springframework.jms.listener.MessageListenerContainer;

public class TestMessageListener {
    ActivemqMessageListener activemqMessageListener = new ActivemqMessageListener();

    @Test
    public void testActivemqMessageListener() throws InterruptedException {
        MessageListenerContainer messageListener = activemqMessageListener.createMessageListener();
        messageListener.start();
        while(true){
            Thread.sleep(1000);
        }
    }
}
