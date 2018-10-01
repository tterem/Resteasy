package org.jboss.resteasy.test.spring.deployment.resource;

import org.springframework.stereotype.Controller;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.stream.Stream;


/**
 * User: rsearls
 * Date: 2/20/17
 */
@Controller
@Path("numbers")
public class NumbersResource{

   @GET
   @Produces("application/json")
   public JsonArray numbers(){
      JsonArrayBuilder array=Json.createArrayBuilder();
      Stream<String> numberStream=Stream.generate(System::currentTimeMillis)
         .map(String::valueOf)
         .limit(10);
      numberStream.forEach(array::add);
      return array.build();

   }

}

