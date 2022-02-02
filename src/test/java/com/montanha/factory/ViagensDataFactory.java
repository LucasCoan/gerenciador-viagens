package com.montanha.factory;

import com.montanha.pojo.Viagens;

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
}
