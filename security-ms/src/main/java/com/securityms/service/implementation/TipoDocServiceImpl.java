package com.securityms.service.implementation;

import com.securityms.repository.TipoDocRepository;
import com.securityms.repository.domain.TipoDoc;
import com.securityms.service.ITipoDocService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoDocServiceImpl implements ITipoDocService {

  private final Logger logger = LogManager.getLogger(TipoDocServiceImpl.class);

  @Autowired
  private TipoDocRepository repository;

  @Override
  public TipoDoc findById(Integer id) {
    try {
      return repository.findById(id)
          .orElseThrow(() -> new EntityNotFoundException("TipoDoc not found"));
    } catch (Exception e) {
      logger.error("An error occurred while trying to find TipoDoc with id {}", id, e);
      throw new RuntimeException("An error occurred while trying to find TipoDoc with id " + id, e);
    }
  }


  @Override
  public List<TipoDoc> findAll() {
    return repository.findAll();
  }
}
