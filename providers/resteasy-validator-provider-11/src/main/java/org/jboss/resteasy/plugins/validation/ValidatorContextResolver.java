package org.jboss.resteasy.plugins.validation;

import org.jboss.resteasy.spi.validation.GeneralValidator;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * @author Leandro Ferro Luzia
 * @author <a href="ron.sigal@jboss.com">Ron Sigal</a>
 * @version $Revision: 1.1 $
 *
 * Copyright May 23, 2013
 */
@Provider
public class ValidatorContextResolver extends AbstractValidatorContextResolver implements ContextResolver<GeneralValidator>{
}
