package com.montanha.isolada;

import com.montanha.pojo.Usuario;
import com.montanha.pojo.Viagens;
import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;

public class ViagensTestComPojo {
    @Test
    public void testCadastroDeViagensValidaRetornoSucesso() {
        //Configuração Rest-Assured
        baseURI = "http://localhost";
        port = 8089;
        basePath = "/api";

        Usuario usuarioAdministrador = new Usuario();
        usuarioAdministrador.setEmail("admin@email.com");
        usuarioAdministrador.setSenha("654321");

        String token = given()
            .contentType(ContentType.JSON)
            .body(usuarioAdministrador)
        .when()
            .post("/v1/auth")
        .then()
//            .log()
//                .all()
            .extract()
                .path("data.token");

        Viagens viagemValida = new Viagens();
        viagemValida.setAcompanhante("Lucas Coan Mazzuco");
        viagemValida.setDataPartida("2022-11-09");
        viagemValida.setDataRetorno("2022-11-20");
        viagemValida.setLocalDeDestino("Braco do Norte");
        viagemValida.setRegiao("Sul");

        given()
            .contentType(ContentType.JSON)
            .body(viagemValida)
            .header("Authorization", token)
        .when()
            .post("/v1/viagens")
        .then()
            .assertThat()
                .statusCode(201)
                .body("data.localDeDestino", equalTo("Braco do Norte"))
                .body("data.acompanhante", equalToIgnoringCase("Lucas Coan Mazzuco"));

        System.out.println(token);
      }
    }

