package com.montanha.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.montanha.pojo.Viagens;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ViagensDataFactory {
    public static Viagens criarViagemValida() {
        Viagens viagemValida = new Viagens();
        viagemValida.setAcompanhante("Lucas Coan Mazzuco");
        viagemValida.setDataPartida("2022-11-09");
        viagemValida.setDataRetorno("2022-11-20");
        viagemValida.setLocalDeDestino("Braco do Norte");
        viagemValida.setRegiao("Sul");

        return viagemValida;
    }

    public static Viagens criarViagem() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Viagens viagens = objectMapper.readValue(new FileInputStream("src/test/resources/requestBody/postV1Viegens.json"), Viagens.class);
        return viagens;
    }
}
