package bookrental;

import bookrental.config.kafka.KafkaProcessor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PolicyHandler{

    @Autowired
    PointRepository pointRepo;
    @Autowired
    PointController pc;

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
            List<Point> pointList = pointRepo.findByUserIdOrderByChgDateDesc(returned.getUserId());
            if(!pointList.isEmpty()){
                if(pointList.get(0).getUserTotalPoint() != null){
                    point.setUserTotalPoint(pointList.get(0).getUserTotalPoint()+1);
                }
                else{
                    point.setUserTotalPoint(1);
                }

            }else{
                point.setUserTotalPoint(1);
            }
            System.out.println("point:"+point);
            pc.saved(point);

        }
    }

}
