package com.larinia;

import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("gc-pause")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class TestGCPause {

    @GET
    @Path("/{iterations}")
    public Response longGCPause(@PathParam("iterations") final int iterations) throws InterruptedException {

        Thread thread = new Thread() {
            @Override
            public void run() {

                double sideEffect = 0;

                for (int i = 0; i < 10000; i++) {
                    sideEffect = slowpoke(iterations); //999999999
                }

                System.out.println("result = " + sideEffect);
            }
        };
        thread.start();

        new Thread(){
            @Override
            public void run() {
                long timestamp = System.currentTimeMillis();
                while (true){
                    System.out.println("Delay " + (System.currentTimeMillis() - timestamp));
                    timestamp = System.currentTimeMillis();
                    //trigger stop-the-world
                    System.gc();
                }
            }
        }.start();

        thread.join();

        return Response.ok("{message:ok}").build();

    }

    protected static double slowpoke(int iterations) {
        double d = 0;
        for (int j = 1; j < iterations; j++) {
            d += Math.log(Math.E * j);
        }
        return d;
    }

}