package com.securityms.repository;

import com.securityms.repository.domain.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Page<Usuario> findAll(Pageable pageable);

    Optional<Usuario> findByUsuario(String usuario);

    List<Usuario> findAllByIdRole(Integer idRole);

    Page<Usuario> findAllByIdRole(Integer idRole, Pageable pageable);

    boolean existsByUsuario(String usuario);
}
