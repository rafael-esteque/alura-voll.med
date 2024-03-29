package med.voll.api.paciente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Serializable> {
    Page<Paciente> findAllByAtivoTrue(Pageable paginacao);
}