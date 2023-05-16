package biathlonStats.repo;

import biathlonStats.entity.Institution;
import biathlonStats.entity.Race;
import biathlonStats.entity.Sportsman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RaceStatRepo extends JpaRepository<Race, String>  {

}
