package com.montanha.isolada;

import com.montanha.factory.UsuarioDataFactory;
import com.montanha.factory.ViagensDataFactory;
import com.montanha.gerenciador.entities.Viagem;
import com.montanha.pojo.Usuario;
import com.montanha.pojo.Viagens;
import io.restassured.http.ContentType;
import org.junit.Assert;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class ViagensTest {
    @Test
    public void testCadastroDeViagensValidaRetornoSucesso() {
        //Configuração Rest-Assured
        baseURI = "http://localhost";
        port = 8089;
        basePath = "/api";

        Usuario usuarioAdministrador = UsuarioDataFactory.criarUsuarioAdministrador();

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

        Viagens viagemValida = ViagensDataFactory.criarViagemValida();

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

