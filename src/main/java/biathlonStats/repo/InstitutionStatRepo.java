package biathlonStats.repo;

import biathlonStats.entity.Institution;
import biathlonStats.entity.Sportsman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstitutionStatRepo extends JpaRepository<Institution, String>  {

}
