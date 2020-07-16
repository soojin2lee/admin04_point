package bookrental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
 public class PointController {
  @Autowired
  PointRepository pointRepo;

 @PostMapping("/points/save")
 public Point saved(@RequestBody Point postPoint) {

   Point point = new Point();
   Integer savedPoint = postPoint.getPoint();
   if(postPoint.getPoint() == null){
       savedPoint = 1;
   }
   point.setUserId(postPoint.getUserId());
   point.setPoint(savedPoint);
   point.setStatus(postPoint.getStatus());

     List<Point> pointList = pointRepo.findByUserIdOrderByChgDateDesc(postPoint.getUserId());
     if(!pointList.isEmpty()){
         if(pointList.get(0).getUserTotalPoint() != null){
             point.setUserTotalPoint(pointList.get(0).getUserTotalPoint()+savedPoint);
         }
         else{
             point.setUserTotalPoint(savedPoint);
         }

     }else{
         point.setUserTotalPoint(1);
     }

   pointRepo.save(point);
   return point;
  }

 @PostMapping("/points/use")
 public Point used(@RequestBody Point postPoint) {

  Point point = new Point();
  point.setUserId(postPoint.getUserId());
  point.setStatus("used");
  point.setPoint(postPoint.getPoint());
     if(postPoint.getUserTotalPoint() == null){
         List<Point> pointList = pointRepo.findByUserIdOrderByChgDateDesc(postPoint.getUserId());
         if(!pointList.isEmpty()){
             if(pointList.get(0).getUserTotalPoint() != null)
             point.setUserTotalPoint(pointList.get(0).getUserTotalPoint()-postPoint.getPoint());
         }else{
             point.setUserTotalPoint(1);
         }
     }else{ //handler 통해서 넘어온 경우
         point.setUserTotalPoint(postPoint.getUserTotalPoint());
     }
  pointRepo.save(point);
  return point;
 }
}
