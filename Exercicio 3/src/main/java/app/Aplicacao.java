package app;

import static spark.Spark.*;
import service.CamisaService;

public class Aplicacao {

    private static CamisaService camisaService = new CamisaService();

    public static void main(String[] args) {
        port(6789);

        staticFiles.location("/public");

        post("/camisa/insert", (request, response) -> camisaService.insert(request, response));

        get("/camisa/:id", (request, response) -> camisaService.get(request, response));

        get("/camisa/list/:orderby", (request, response) -> camisaService.getAll(request, response));

        get("/camisa/update/:id", (request, response) -> camisaService.getToUpdate(request, response));

        post("/camisa/update/:id", (request, response) -> camisaService.update(request, response));

        get("/camisa/delete/:id", (request, response) -> camisaService.delete(request, response));
    }
}