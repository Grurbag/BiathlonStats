package biathlonStats.repo;

import biathlonStats.entity.Coach;
import biathlonStats.entity.Institution;
import biathlonStats.entity.Sportsman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoachStatRepo extends JpaRepository<Coach, String>  {

}