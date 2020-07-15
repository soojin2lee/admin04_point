package bookrental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

@RestController
 public class PointController {
  @Autowired
  PointRepository pointRepo;

 @PostMapping("/point/save")
 public Point saved(@RequestBody Point postPoint) {

   Point point = new Point();
   point.setUserId(postPoint.getUserId());
   point.setStatus("saved");
   point.setPoint(postPoint.getPoint());
   point.setChgDate(new Date());
   pointRepo.save(point);
   return point;
  }

 @PostMapping("/point/use")
 public Point used(@RequestBody Point postPoint) {

  Point point = new Point();
  point.setUserId(postPoint.getUserId());
  point.setStatus("used");
  point.setPoint(postPoint.getPoint());
  point.setChgDate(new Date());
  pointRepo.save(point);
  return point;
 }
}
