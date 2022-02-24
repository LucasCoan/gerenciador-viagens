package com.montanha.isolada;

import com.montanha.pojo.Usuario;
import io.restassured.http.ContentType;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;


public class ViagensTestComBody {
    @Test
    public void testCadastroDeViagensValidaRetornoSucesso() {
        //Configuração Rest-Assured
        baseURI = "http://localhost";
        port = 8089;
        basePath = "/api";

        String token = given()
            .contentType(ContentType.JSON)
            .body("{\n" +
                    "  \"email\": \"admin@email.com\",\n" +
                    "  \"senha\": \"654321\"\n" +
                    "}")
        .when()
            .post("/v1/auth")
        .then()
//            .log()
//                .all()
            .extract()
                .path("data.token");

        given()
            .contentType(ContentType.JSON)
            .body("{\n" +
                    "  \"acompanhante\": \"Lucas Coan Mazzuco\",\n" +
                    "  \"dataPartida\": \"2021-11-09\",\n" +
                    "  \"dataRetorno\": \"2021-11-20\",\n" +
                    "  \"localDeDestino\": \"Braco do Norte\",\n" +
                    "  \"regiao\": \"Sul\"\n" +
                    "}")
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

