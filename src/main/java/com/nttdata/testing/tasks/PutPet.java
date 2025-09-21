package com.nttdata.testing.tasks;


import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.rest.interactions.Patch;
import net.serenitybdd.screenplay.rest.interactions.Put;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class PutPet implements Task {

    private final String categoryName;
    private final String name;
    private final String petId;

    public PutPet(String categoryName, String name) {
        this.categoryName = categoryName;
        this.name = name;
        this.petId = OnStage.theActorInTheSpotlight().recall("petId");
    }

    public static Performable fromPage(String categoryName, String name) {
        return instrumented(PutPet.class, categoryName, name);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Put.to("/pet")
                        .with(request -> request
                                .contentType(ContentType.JSON)
                                .accept(ContentType.JSON)
                                .body("{"
                                        + "\"id\": " + petId + ","
                                        + "\"category\": {\"id\": 1, \"name\": \"" + categoryName + "\"},"
                                        + "\"name\": \"" + name + "\","
                                        + "\"photoUrls\": [],"
                                        + "\"status\": \"available\""
                                        + "}")
                        )
        );
    }

}
