package org.jboss.resteasy.test.resource.path.resource;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;

public class LocatorWithClassHierarchyPathSegmentImpl implements PathSegment{

   private String id;

   public LocatorWithClassHierarchyPathSegmentImpl(final String id){
      super();
      this.id=id;
   }

   @Override
   public MultivaluedMap<String,String> getMatrixParameters(){
      return null;
   }

   @Override
   public String getPath(){
      return id;
   }

}
