package com.revature.project1.persistence;

import com.revature.project1.exception.Exception;
import com.revature.project1.model.Student;
import com.revature.project1.utils.ConnectionUtil;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DaoManager {
    static final Logger log = Logger.getLogger(DaoManager.class);

    ExecutorService executorService = Executors.newFixedThreadPool(7);
    PreparedStatement preparedStatement;
    private static DaoPreparingStatement daoStatementPreparer = new DaoPreparingStatement();
    private static DaoManager daoManager = new DaoManager();

    //*************************create table ******************************************
    public void create(Object o) throws java.lang.Exception {
        Connection conn = ConnectionUtil.getConnection();

        String sqlStmt = daoStatementPreparer.sql(o, "CREATE");
        preparedStatement = daoStatementPreparer.prepareStatement(o, conn, sqlStmt, "CREATE");
        executorService.execute(() -> {
            try {
                // if a table does not already exist
                preparedStatement.executeUpdate(); // return 0 or -1
            } catch (SQLException e) {
                e.printStackTrace();
                log.error(e);
                log.trace(e);
                log.warn(e);
            }
        });
        //----------------------------------------------------------------------------------------
        // check is the Object exist or not
        if (isTableExist(o)) throw new Exception("This object is exist.");
        daoManager.insert(o);
    }

    //************************* insert ******************************************
    public void insert(Object o) throws java.lang.Exception {
        Connection conn = ConnectionUtil.getConnection();
        String sql;
        PreparedStatement stmt;
        int success = 0;

        conn.setAutoCommit(false);
        Savepoint savepoint = conn.setSavepoint();

        sql = daoStatementPreparer.sql(o, "INSERT");
        stmt = daoStatementPreparer.prepareStatement(o, conn, sql, "INSERT");
        success = stmt.executeUpdate();
        log.info("Executing query: " + stmt + " from insert");
        if (success == 0) {
            conn.rollback(savepoint);
            throw new java.lang.Exception("not inserted: " + o);
        }
        conn.commit();
    }

    //************************* read table ******************************************
    public boolean read(String id) throws SQLException, IllegalAccessException {
        if (Integer.parseInt(id) > 0) {
            Student o = new Student(Integer.parseInt(id));
            Connection conn = ConnectionUtil.getConnection();
            DaoPreparingStatement ps = new DaoPreparingStatement();
            PreparedStatement stmt = ps.prepareStatement(o, conn, ps.sql(o, "SELECT"), "SELECT");
            log.info("Executing query: " + stmt + " from read");
            //return  stmt;
            ResultSet rs = stmt.executeQuery();
            List<Object> result = new ArrayList<>();
            ResultSetMetaData md = rs.getMetaData();
            if (rs != null) {
                while (rs.next()) {
                    for (int i = 1; i <= md.getColumnCount(); i++) {
                        result.add(rs.getObject(i));
                    }
                }
            }
            if (id.equals(result.get(0).toString())) {
                System.out.println(result);
                return true;
            } else {
                System.out.println("Object with id : " + id + " is not exist");
                return false;
            }
        } else {
            System.out.println("Object with id : " + id + " is not exist");
            return false;
        }
    }

    //************************* update table ******************************************
    public Object update(Object o) throws java.lang.Exception {
        Connection conn = ConnectionUtil.getConnection();
        String sql;
        PreparedStatement stmt;
        int success = 0;

        if (!isTableExist(o)) throw new Exception("This object is not exist.");

        conn.setAutoCommit(false);
        Savepoint savepoint = conn.setSavepoint();

        sql = daoStatementPreparer.sql(o, "UPDATE");
        stmt = daoStatementPreparer.prepareStatement(o, conn, sql, "UPDATE");
        success = stmt.executeUpdate();
        log.info("Executing query: " + stmt + " from update");
        if (success == 0) {
            conn.rollback(savepoint);
            throw new Exception("not updated: " + o);
        }
        conn.commit();
        return o;
    }

    //************************* delete by id  ******************************************
    public boolean delete(String id) throws java.lang.Exception {
        Connection conn = ConnectionUtil.getConnection();
        Student o = new Student(Integer.parseInt(id));
        if (daoManager.read(id)) {
            String sql;
            PreparedStatement stmt;
            int success = 0;

            // if a table does not already exist
            // if is !isExist throw new Exception("Table does not exist");
            conn.setAutoCommit(false);
            Savepoint savepoint = conn.setSavepoint();

            sql = daoStatementPreparer.sql(o, "DELETE");
            stmt = daoStatementPreparer.prepareStatement(o, conn, sql, "DELETE");
            success = stmt.executeUpdate();
            log.info("Executing query: " + stmt + " from delete");

            if (success == 0) {
                conn.rollback(savepoint);
                throw new Exception("no deleted the object: " + o);
            }
            conn.commit();
            return true;
        } else {
            System.out.println("Nothing to delete, none existing object");
            return false;
        }
    }

    //************************* delete table  ******************************************
    public boolean dropTable() throws java.lang.Exception {
        Connection conn = ConnectionUtil.getConnection();
        String sql;
        PreparedStatement stmt;
        Student student = new Student();
        sql = " DROP TABLE IF EXISTS p1." + student.getClass().getSimpleName() + " CASCADE ";
        stmt = conn.prepareStatement(sql);
        stmt.executeUpdate();
        return true;
    }

    //************************* isExist table ******************************************
    public boolean isTableExist(Object o) throws java.lang.Exception {
        Connection conn = ConnectionUtil.getConnection();
        String sql;
        PreparedStatement stmt;

        sql = daoStatementPreparer.sql(o, "SELECT");
        stmt = daoStatementPreparer.prepareStatement(o, conn, sql, "SELECT");
        ResultSet rs = stmt.executeQuery();
        log.info("Executing query: " + stmt + " from isExist");
        return rs.next();
    }
}