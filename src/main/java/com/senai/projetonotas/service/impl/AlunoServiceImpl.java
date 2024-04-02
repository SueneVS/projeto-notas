package com.senai.projetonotas.service.impl;

import com.senai.projetonotas.dto.DtoGenericRequest;
import com.senai.projetonotas.dto.DtoGenericResponse;
import com.senai.projetonotas.service.AlunoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoServiceImpl implements AlunoService {
    @Override
    public DtoGenericResponse create(DtoGenericRequest dto) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public DtoGenericResponse update(Long id, DtoGenericRequest dto) {
        return null;
    }

    @Override
    public DtoGenericResponse getEntity(Long id) {
        return null;
    }

    @Override
    public List<DtoGenericResponse> getEntities() {
        return null;
    }

    @Override
    public List<DtoGenericResponse> getEntities(Long id) {
        return null;
    }
}
