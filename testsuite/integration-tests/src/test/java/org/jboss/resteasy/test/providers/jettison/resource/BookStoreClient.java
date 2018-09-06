package org.jboss.resteasy.test.providers.jettison.resource;

import javax.ws.rs.*;
import java.util.Collection;

@Path("/bookstore")
public interface BookStoreClient {

   @GET
   @Path("/books/{isbn}")
   @Produces("text/xml")
   Book getBookByISBN(@PathParam("isbn") String isbn);

   @PUT
   @Path("/books")
   @Consumes("text/xml")
   void addBook(Book book);

   @GET
   @Path("/books")
   @Produces("text/xml")
   Collection<Book> getAllBooks();

   @GET
   @Path("/books/{isbn}")
   @Produces("application/json")
   Book getBookByISBNJson(@PathParam("isbn") String isbn);

   @PUT
   @Path("/books")
   @Consumes("application/json")
   void addBookJson(Book book);

   @GET
   @Path("/books")
   @Produces("application/json")
   Collection<Book> getAllBooksJson();
}
