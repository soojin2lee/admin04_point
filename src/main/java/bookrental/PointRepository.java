package bookrental;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PointRepository extends PagingAndSortingRepository<Point, Long>{
    List<Point> findByUserIdOrderByChgDateDesc(Long userId);

}