package com.revature.project1.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.project1.persistence.DaoManager;
import com.revature.project1.model.Student;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

public class Service {
 private static DaoManager daoManager = new DaoManager();


    // ********************** find by id **********************************************
    public boolean read_findById(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String id = req.getParameter("id");
                if( daoManager.read(id))
                    return true;
                    return false;
    }
    // ********************** update table **********************************************
    public void update_service(HttpServletRequest req, HttpServletResponse resp) throws java.lang.Exception {
        String collectorBody = req.getReader().lines().collect(Collectors.joining("\n"));
        String json = collectorBody;

        // parse the json string into a java object
        ObjectMapper mapper = new ObjectMapper();
        Student student;
        student = mapper.readValue(json, Student.class);
        System.out.println(student);
        System.out.println("Table updated\n");
        daoManager.update(student);
    }
    // ********************** creating new table or inserting new object **********************************************
    public void create_or_insert_service(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String name = req.getParameter("name");
        String collectorBody = req.getReader().lines().collect(Collectors.joining("\n"));
        String json = collectorBody;

        // parse the json string into a java object
        ObjectMapper mapper = new ObjectMapper();
        Student student;
        student = mapper.readValue(json, Student.class);

        if(name.equals("create")){
            daoManager.create(student);
            System.out.println(student);
            System.out.println("Table created\n");
        }else{
            daoManager.insert(student);
            System.out.println(student);
            System.out.println("Inserted to the table\n");
                      }
    }
    // ********************** delete or drop table **********************************************
    public boolean deleteById_or_dropTable_service(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String id = req.getParameter("id");
        if(id != null && daoManager.delete(id)){
            System.out.println("Deleted by id\n");
            return  true;

        }else{
            daoManager.dropTable();
            System.out.println("Table dropped\n");
            return  true;
        }
    }
}
