package org.jboss.resteasy.jose.jws;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.Serializable;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class JWSHeader implements Serializable{
   private static final ObjectMapper mapper=new ObjectMapper();

   static{
      mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

   }

   @JsonProperty("alg")
   private Algorithm algorithm;
   @JsonProperty("typ")
   private String type;
   @JsonProperty("cty")
   private String contentType;

   public JWSHeader(){
   }

   public JWSHeader(Algorithm algorithm,String type,String contentType){
      this.algorithm=algorithm;
      this.type=type;
      this.contentType=contentType;
   }

   public Algorithm getAlgorithm(){
      return algorithm;
   }

   public String getType(){
      return type;
   }

   public String getContentType(){
      return contentType;
   }

   @JsonIgnore
   public MediaType getMediaType(){
      if(contentType==null) return null;
      return MediaType.valueOf(contentType);
   }

   public String toString(){
      try{
         return mapper.writeValueAsString(this);
      }catch(IOException e){
         throw new RuntimeException(e);
      }


   }

}
