package com.montanha.isolada;

import com.montanha.config.Configuracoes;
import com.montanha.factory.UsuarioDataFactory;
import com.montanha.factory.ViagensDataFactory;
import com.montanha.gerenciador.entities.Viagem;
import com.montanha.pojo.Usuario;
import com.montanha.pojo.Viagens;
import io.restassured.http.ContentType;
import org.aeonbits.owner.ConfigFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class ViagensTest {

    private String token;
    private String tokenUsuario;

    @Before
    public void setUp() {
        //Configuração Rest-Assured

        Configuracoes configuracoes = ConfigFactory.create(Configuracoes.class);
        baseURI = configuracoes.baseURI();
        port = configuracoes.port();
        basePath = configuracoes.basePath();

        Usuario usuarioAdministrador = UsuarioDataFactory.criarUsuarioAdministrador();

        this.token = given()
            .contentType(ContentType.JSON)
            .body(usuarioAdministrador)
        .when()
            .post("/v1/auth")
        .then()
            .extract()
                .path("data.token");

        Usuario usuarioComum = UsuarioDataFactory.criarUsuarioComum();

        this.tokenUsuario = given()
                .contentType(ContentType.JSON)
                .body(usuarioComum)
            .when()
                .post("/v1/auth")
            .then()
                .extract()
                    .path("data.token");
    }


    @Test
    public void testCadastroDeViagensValidaRetornoSucesso() {

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

    @Test
    public void testCadastroDeViagensValidaContrato() {

        Viagens viagemValida = ViagensDataFactory.criarViagemValida();

        given()
            .contentType(ContentType.JSON)
            .body(viagemValida)
            .header("Authorization", token)
        .when()
            .post("/v1/viagens")
        .then()
            .log()
            .all()
            .assertThat()
                .statusCode(201)
                .body(matchesJsonSchemaInClasspath("schemas/postV1ViagensViagemValida.json"));

        System.out.println(token);
      }

      @Test
      public void testRetornaUmaViagemPossuiStatusCode200MostraLocalDestino() {
            given()
                    .header("Authorization", tokenUsuario)
                .when()
                    .get("/v1/viagens/1")
                .then()
                    .assertThat()
                        .statusCode(200)
                    .body("data.localDeDestino", equalTo("Orleans"));
      }
    }

