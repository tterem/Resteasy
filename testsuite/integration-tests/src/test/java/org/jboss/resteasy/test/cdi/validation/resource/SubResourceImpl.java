package org.jboss.resteasy.test.cdi.validation.resource;

import org.jboss.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.Response;

@RequestScoped
public class SubResourceImpl implements SubResource {
   private static final Logger LOG = Logger.getLogger(SubResourceImpl.class);
   static boolean methodEntered;

   @Override
   public Response getAll(QueryBeanParamImpl beanParam) {
      LOG.info("beanParam#getParam valid? " + beanParam.getParam());
      methodEntered = true;
      return Response.ok().build();
   }
}
