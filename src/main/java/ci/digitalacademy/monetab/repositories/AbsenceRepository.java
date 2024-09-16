package ci.digitalacademy.monetab.repositories;

import ci.digitalacademy.monetab.models.Absence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbsenceRepository extends JpaRepository<Absence, Long> {
}
