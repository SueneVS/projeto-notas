package com.senai.projetonotas.service;

import com.senai.projetonotas.dto.DtoGenericRequest;
import com.senai.projetonotas.dto.DtoGenericResponse;

import java.util.List;

public interface DisciplinaService {
    public DtoGenericResponse create (DtoGenericRequest dto);
    public void delete(Long id);
    public DtoGenericResponse update (Long id,DtoGenericRequest dto);
    public DtoGenericResponse getEntity (Long id);
    public List<DtoGenericResponse> getEntities ();
    public List<DtoGenericResponse> getEntities(Long id);
}
