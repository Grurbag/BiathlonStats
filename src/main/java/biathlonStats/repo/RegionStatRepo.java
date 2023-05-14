package biathlonStats.repo;

import biathlonStats.entity.Institution;
import biathlonStats.entity.Region;
import biathlonStats.entity.SportsmanRace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionStatRepo extends JpaRepository<Region, Long> {
}
