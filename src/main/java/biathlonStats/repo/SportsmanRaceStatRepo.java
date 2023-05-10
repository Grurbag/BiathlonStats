package biathlonStats.repo;

import biathlonStats.entity.SportsmanRace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SportsmanRaceStatRepo extends JpaRepository<SportsmanRace, Long>  {

}
