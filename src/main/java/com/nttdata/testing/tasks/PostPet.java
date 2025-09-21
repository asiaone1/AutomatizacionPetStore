package com.nttdata.testing.tasks;

import io.restassured.http.ContentType;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.rest.interactions.Post;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class PostPet implements Task {

    private final String id, categoryId, categoryName, name, photoUrls, status;

    public PostPet(String id, String categoryId, String categoryName, String name, String photoUrls, String status) {
        this.id = id;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.name = name;
        this.photoUrls = photoUrls;
        this.status = status;
    }

    public static Performable fromPage(String id, String categoryId, String categoryName, String name, String photoUrls, String status) {
        return instrumented(PostPet.class, id, categoryId, categoryName, name, photoUrls, status);
    }
    @Override
    public <T extends Actor> void performAs(T actor) {

        String body = "{"
                + "\"id\": " + Integer.parseInt(id) + ","
                + "\"category\": {"
                +     "\"id\": " + Integer.parseInt(categoryId) + ","
                +     "\"name\": \"" + categoryName + "\""
                + "},"
                + "\"name\": \"" + name + "\","
                + "\"photoUrls\": [\"" + photoUrls + "\"],"
                + "\"status\": \"" + status + "\""
                + "}";

        actor.attemptsTo(Post.to("/pet")
                .with(requestSpecification -> requestSpecification
                        .contentType(ContentType.JSON)
                        .header("Accept", "application/json")
                        .body(body)
                        .log().all()));

        SerenityRest.lastResponse().body().prettyPrint();

        if (SerenityRest.lastResponse().statusCode() == 200) {
            OnStage.theActorInTheSpotlight().remember("petId", SerenityRest.lastResponse().path("id").toString());
            String valorDelpetId = actor.recall("petId");
            System.out.println("PET ID: " + valorDelpetId);
        }
    }

}
