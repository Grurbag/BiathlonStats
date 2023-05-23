package biathlonStats.repo;

import biathlonStats.entity.SportsmanCoach;
import biathlonStats.entity.SportsmanRace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SportsmanCoachStatRepo extends JpaRepository<SportsmanCoach, Long>  {

}
