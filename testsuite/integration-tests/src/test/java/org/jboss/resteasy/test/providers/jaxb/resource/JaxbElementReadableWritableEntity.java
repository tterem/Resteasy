package org.jboss.resteasy.test.providers.jaxb.resource;

public class JaxbElementReadableWritableEntity{
   public static final String NAME="READABLEWRITEABLE";
   private static final String PREFIX="<"+NAME+">";
   private static final String SUFFIX="</"+NAME+">";
   private String entity;

   public JaxbElementReadableWritableEntity(final String entity){
      this.entity=entity;
   }

   public static JaxbElementReadableWritableEntity fromString(String stream){
      String entity=stream.replaceAll(PREFIX,"").replaceAll(SUFFIX,"");
      return new JaxbElementReadableWritableEntity(entity);
   }

   public String toXmlString(){
      StringBuilder sb=new StringBuilder();
      sb.append(PREFIX).append(entity).append(SUFFIX);
      return sb.toString();
   }

   @Override
   public String toString(){
      return entity;
   }
}
