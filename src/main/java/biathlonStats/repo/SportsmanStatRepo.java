package biathlonStats.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import biathlonStats.entity.Sportsman;

import java.util.List;

@Repository
public interface SportsmanStatRepo extends JpaRepository<Sportsman, Long>  {

}
