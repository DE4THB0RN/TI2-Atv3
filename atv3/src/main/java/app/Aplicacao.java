package app;

import static spark.Spark.*;

import service.BeysService;



public class Aplicacao {
	
	private static BeysService beysService = new BeysService();
	
    public static void main(String[] args) {
       port(4567);
        
        staticFiles.location("/public");
        
        post("/produto/insert", (request, response) -> beysService.insert(request, response));

        get("/produto/:id", (request, response) -> beysService.get(request, response));
        
        get("/produto/list/:orderby", (request, response) -> beysService.getAll(request, response));

        get("/produto/update/:id", (request, response) -> beysService.getToUpdate(request, response));
        
        post("/produto/update/:id", (request, response) -> beysService.update(request, response));
           
        get("/produto/delete/:id", (request, response) -> beysService.delete(request, response));

             
    }
}