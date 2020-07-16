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
         point.setUserTotalPoint(savedPoint);
     }

   pointRepo.save(point);
   return point;
  }

 @PostMapping("/points/use")
 public Point used(@RequestBody Point postPoint) {

  Point point = new Point();
     Integer usedPoint = postPoint.getPoint();
     if(postPoint.getPoint() == null){
         usedPoint = 1;
     }
  point.setUserId(postPoint.getUserId());
  point.setStatus("used");
  point.setPoint(usedPoint);

     List<Point> pointList = pointRepo.findByUserIdOrderByChgDateDesc(postPoint.getUserId());
     if(!pointList.isEmpty()) {
         if (pointList.get(0).getUserTotalPoint() != null) {
             if (usedPoint <= pointList.get(0).getUserTotalPoint()) {
                 point.setUserTotalPoint(pointList.get(0).getUserTotalPoint() - usedPoint);
                 pointRepo.save(point); //가용한 포인트가 있는 경우에만 save
             }
         }
     }


  return point;
 }


    @PostMapping("/points/cancel")
    public Point cancelled(@RequestBody Point postPoint) {

        Point point = new Point();
        point.setUserId(postPoint.getUserId());
        point.setStatus("cancelled");
        List<Point> pointList = pointRepo.findByUserIdOrderByChgDateDesc(postPoint.getUserId());
        if(!pointList.isEmpty()) {
            if (pointList.get(0).getPoint() != null) { //마지막에 받았던 포인트
                    point.setUserTotalPoint(pointList.get(0).getUserTotalPoint() - pointList.get(0).getPoint());
                    point.setPoint(0);
                    pointRepo.save(point); //가용한 포인트가 있는 경우에만 save
                }
        }
        return point;
    }

}
