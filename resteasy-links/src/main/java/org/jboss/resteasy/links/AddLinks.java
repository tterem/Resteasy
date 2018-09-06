package org.jboss.resteasy.links;

import org.jboss.resteasy.annotations.Decorator;
import org.jboss.resteasy.links.impl.LinkDecorator;

import javax.xml.bind.Marshaller;
import java.lang.annotation.*;

/**
 * Use on any JAX-RS method if you want RESTEasy to inject the RESTServiceDiscovery
 * to every entity in the response. This will only inject RESTServiceDiscovery instances
 * on entities that have a field of this type, but it will be done recursively on the response's
 * entity.
 *
 * @author <a href="mailto:stef@epardaud.fr">Stéphane Épardaud</a>
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER,
        ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Decorator(processor = LinkDecorator.class, target = Marshaller.class)
@Documented
public @interface AddLinks {
}
