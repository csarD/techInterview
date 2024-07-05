package com.securityms.service;

import com.securityms.repository.domain.TipoDoc;

import java.util.List;

public interface ITipoDocService {

    TipoDoc findById(Integer id);
    List<TipoDoc> findAll();
}
