package com.senai.projetonotas.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErroDto {
    private String codigo;
    private String mensagem;
}
