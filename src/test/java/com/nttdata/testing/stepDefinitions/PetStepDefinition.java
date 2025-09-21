package com.nttdata.testing.stepDefinitions;

import com.nttdata.testing.questions.ResponseCode;
import com.nttdata.testing.tasks.*;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.CoreMatchers.equalTo;

public class PetStepDefinition {

    public static Logger LOGGER = LoggerFactory.getLogger(PetStepDefinition.class);

    @Before
    public void setTheStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Given("el {actor} establece el endpoint de pet")
    public void elActorEstableceElEndpointDePet(Actor actor) {
        actor.whoCan(CallAnApi.at("https://petstore.swagger.io/v2"));
    }

    @When("el {actor} realiza una solicitud pet GET")
    public void elActorRealizaUnaSolicitudPetGET(Actor actor) {
        actor.attemptsTo(GetAllPet.fromEndpoint("/pet/findByStatus?status=available"));
    }

    @When("^el actor crea un pet con el \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
    public void elActorCreaUnPetConEl(String id, String categoryId, String categoryName, String name, String photoUrls, String status) {
        theActorInTheSpotlight().attemptsTo(
                PostPet.fromPage(id, categoryId, categoryName, name, photoUrls, status)
        );
    }

    @Then("el codigo de respuesta debe ser {int}")
    public void elCodigoDeRespuestaDebeSer(int responseCode) {
        theActorInTheSpotlight().should(seeThat("El código de respuesta", ResponseCode.getStatus(), equalTo(responseCode)));
    }

    @When("^el actor actualiza un pet con el nombre y categoria nombre \"([^\"]*)\" \"([^\"]*)\"$")
    public void elActorActualizaUnPetConElNombreYCategoriaNombre(String categoryName, String name) {
        theActorInTheSpotlight().attemptsTo(
                PutPet.fromPage(categoryName, name)
        );
    }



}
