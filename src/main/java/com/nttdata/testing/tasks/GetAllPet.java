package com.nttdata.testing.tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class GetAllPet implements Task {

    private final String endpoint;

    public GetAllPet(String endpoint){
        this.endpoint = endpoint;
    }

    public static Performable fromEndpoint(String endpoint){
        return instrumented(GetAllPet.class, endpoint);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Get.resource(endpoint)
                        .with(req -> req
                                .accept(ContentType.JSON)
                                .log().all()
                        )
        );
    }




}
