/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fish.payara.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import org.eclipse.microprofile.faulttolerance.Timeout;
import org.eclipse.microprofile.metrics.Counter;
import org.eclipse.microprofile.metrics.Gauge;
import org.eclipse.microprofile.metrics.MetricRegistry;
import static org.eclipse.microprofile.metrics.MetricRegistry.Type.APPLICATION;
import org.eclipse.microprofile.metrics.annotation.RegistryType;

/**
 *
 * http://localhost:8080/hello/register?key=d1&value=d2
 * http://localhost:8080/hello/stats
 */
@Path("/hello")
@ApplicationScoped
public class HelloWorld {

    @Inject
    @RegistryType(type = APPLICATION)
    MetricRegistry registry;

    @GET
    @Path("/register")
    public String register(
            @QueryParam("key") final String key,
            @QueryParam("value") final String value) {

        registry.register(key, new Gauge() {
            @Override
            public Object getValue() {
                return value;
            }
        });
        return "registerd";
    }

    @GET
    @Path("/stats")
    public String getStats(final String param) {
        return registry.getGauges().toString();
    }


}
