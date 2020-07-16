package bookrental;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PointRepository extends PagingAndSortingRepository<Point, Long>{
    List<Point> findByUserIdOrderByChgDateDesc(Long userId);
    List<Point> findByUserIdOrderByChgDateAsc(Long userId);

}