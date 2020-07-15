package bookrental;

import bookrental.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PolicyHandler{

    @Autowired
    PointRepository pointRepo;
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReturned_ChangePoint(@Payload Returned returned){

        if(returned.isMe()){
            Point point = new Point();
            point.setPoint(1);
            point.setStatus("saved");
            point.setUserId(returned.getUserId());
            pointRepo.save(point);
        }
    }

}
