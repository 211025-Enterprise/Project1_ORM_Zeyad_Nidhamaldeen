package com.revature.project1.controller;

import com.revature.project1.services.Service;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/controller")
public class Controller extends HttpServlet {
    static Service service =  new Service();
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            service.read_findById(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {

        try {
            service.create_or_insert_service(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        try {
            service.update_service(req,resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        try {
            service.deleteById_or_dropTable_service(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

















       /* resp.setHeader("Content-Type", "application/json");
        // resp.setHeader("Content-Type", "text/html");
        resp.setStatus(200);
        resp.getOutputStream().println("{\"id\":id,\"firstname\":\"firstname\",\"lastname\":\"lastname\"}");
*/


      /* Enumeration<String> parameters = req.getParameterNames();
        System.out.println("Parameters: ");
        while (parameters.hasMoreElements()) {
            String parameter = parameters.nextElement();
            System.out.printf("%s : %s,\n", parameter, req.getParameter(parameter));
        }


        // first method of getting a request body
        BufferedReader reader = req.getReader();
        StringBuilder builder = new StringBuilder();
        String temp;
//        while((temp = reader.readLine()) != null){
//            builder.append(temp).append("\n");
//        }
        String bufferedBody = builder.toString();

        String collectorBody = req.getReader().lines().collect(Collectors.joining("\n"));

        String json = collectorBody;

        System.out.println("Buffered Body: ");
        System.out.println(bufferedBody);

        System.out.println("Collector Body: ");
        System.out.println(collectorBody);


        // parse the json string into a java object
        ObjectMapper mapper = new ObjectMapper();
        Student student = null;
        student = mapper.readValue(json, Student.class);
        System.out.println(student);

          resp.setHeader("Content-Type", "application/json");
        String newJson = mapper.writeValueAsString(student);
        resp.getWriter().println(newJson);*/