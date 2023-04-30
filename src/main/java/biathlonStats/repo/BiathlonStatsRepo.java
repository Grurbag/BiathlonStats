package biathlonStats.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import biathlonStats.entity.Sportsman;

@Repository
public interface BiathlonStatsRepo extends JpaRepository<Sportsman, String> {

}
