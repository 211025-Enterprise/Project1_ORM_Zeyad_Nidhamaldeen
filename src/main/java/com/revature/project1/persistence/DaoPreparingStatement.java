package com.revature.project1.persistence;
import com.revature.project1.annotation.Id;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;

public class DaoPreparingStatement {
    public PreparedStatement prepareStatement(Object table, Connection connection,
                                              String sql, String query) throws SQLException, IllegalAccessException{
        PreparedStatement statement = connection.prepareStatement(sql);

        Field[] fields = table.getClass().getDeclaredFields();
        Iterator<Field> iterator = Arrays.stream(fields).iterator();
        int column = 1;

        switch(query){
            case "INSERT": while(iterator.hasNext()) {
                    setParameter(table, column, iterator.next(), statement);
                    column++; }
                break;
            case "SELECT":
                for(Field f : fields){
                    if(f.getName().equalsIgnoreCase("id")){
                        f.setAccessible(true);
                        statement.setInt(column, f.getInt(table));
                    }
                }
                break;
            case "UPDATE": while(iterator.hasNext()) {
                setParameter(table, column, iterator.next(), statement);
                column++; }
                for(Field f : fields){
                    if(f.getName().equalsIgnoreCase("id")){
                        f.setAccessible(true);
                        statement.setInt(column, f.getInt(table));
                    }
                }
            case "DELETE":
                for(Field f : fields){
                    if(f.getName().equalsIgnoreCase("id")){
                        f.setAccessible(true);
                        statement.setInt(column, f.getInt(table));
                    }
                }
                break;
        }
        return statement;
    }

    public String sql(Object table, String query){
        StringBuilder sqlStetement = new StringBuilder();
        sqlStetement.append(query);
        switch (query){
            case "INSERT":
                sqlStetement.append(" into ");
                sqlStetement.append(table.getClass().getSimpleName());
                sqlStetement.append(" values ");
                sqlStetement.append(insertParameters(table));
                break;
            case "SELECT":
                sqlStetement.append(" * from ");
                sqlStetement.append(table.getClass().getSimpleName());
                sqlStetement.append(" where \"id\" = ?");
                break;
            case "UPDATE":
                sqlStetement.append(" ").append(table.getClass().getSimpleName());
                sqlStetement.append(" SET ").append(updateParameters(table));
                sqlStetement.append(" where \"id\" = ?");
                break;
            case "DELETE":
                sqlStetement.append(" from ");
                sqlStetement.append(table.getClass().getSimpleName());
                sqlStetement.append(" where \"id\" = ?");
                break;
            case "CREATE":
                sqlStetement.append(" table ")
                        .append(table.getClass().getSimpleName()).append(" (")
                        .append(createParameters(table));
                for (Field field : table.getClass().getDeclaredFields())
                    if (field.getDeclaredAnnotation(Id.class) != null && field.getDeclaredAnnotation(Id.class).isPrimary())
                        sqlStetement.append((", primary key(\"")).append(field.getName()).append("\")");
                sqlStetement.append(");");
                break;
        }
        return sqlStetement.toString();
    }

    public void setParameter(Object table, int column_number, Field field,
                             PreparedStatement stmt) throws IllegalAccessException, SQLException {
        field.setAccessible(true);
        if (field.getType().getSimpleName().equals(("byte")) || field.getType().getSimpleName().equals(("Byte")))
            stmt.setByte(column_number, field.getByte(table));
           else if (field.getType().getSimpleName().equals(("short")) || field.getType().getSimpleName().equals(("Short")))
                stmt.setShort(column_number, field.getShort(table));
                 else if (field.getType().getSimpleName().equals(("int")) || field.getType().getSimpleName().equals(("Integer")))
                stmt.setInt(column_number, field.getInt(table));
                 else if (field.getType().getSimpleName().equals(("long")) || field.getType().getSimpleName().equals(("Long")))
                    stmt.setLong(column_number, field.getLong(table));
                     else if (field.getType().getSimpleName().equals(("float")) || field.getType().getSimpleName().equals(("Float")))
                        stmt.setFloat(column_number, field.getFloat(table));
                        else if (field.getType().getSimpleName().equals(("double")) || field.getType().getSimpleName().equals(("Double")))
                         stmt.setDouble(column_number, field.getDouble(table));
                        else if (field.getType().getSimpleName().equals(("char")) || field.getType().getSimpleName().equals(("Char")))
                        stmt.setString(column_number, String.valueOf(field.getChar(table)));
                      else if (field.getType().getSimpleName().equals(("boolean")) || field.getType().getSimpleName().equals(("Boolean")))
                           stmt.setBoolean(column_number, field.getBoolean(table));
                      else if (field.getType().getSimpleName().equals(("String")))
                      stmt.setString(column_number, (String) field.get(table));
    }

    private String insertParameters(Object table){
        StringBuilder param = new StringBuilder("(");
        Field[] fields = table.getClass().getDeclaredFields();
        Iterator<Field> iterator = Arrays.stream(fields).iterator();
        while(iterator.hasNext()){
            iterator.next();
            param.append("?");
            if(iterator.hasNext()) param.append(",");
        }
        param.append(")");
        return param.toString();
    }

    private String updateParameters(Object o){
        StringBuilder param = new StringBuilder();
        Field[] fields = o.getClass().getDeclaredFields();
        Iterator<Field> iterator = Arrays.stream(fields).iterator();
        while(iterator.hasNext()){
            Field field = iterator.next();
            param.append("\"").append(field.getName()).append("\"").append("=").append("?");
            if(iterator.hasNext()) param.append(",");
        }
        return param.toString();
    }

    private String createParameters(Object table){
        StringBuilder query = new StringBuilder();
        Field[] fields = table.getClass().getDeclaredFields();
        Iterator<Field> iterator = Arrays.stream(fields).iterator();
        while(iterator.hasNext()){
            Field field = iterator.next();
            switch(field.getType().getSimpleName()){
                case "byte":
                case "Byte":
                    query.append("\"").append(field.getName()).append("\"");
                    query.append(" bit");
                    break;
                case "short":
                case "Short":
                    query.append("\"").append(field.getName()).append("\"");
                    query.append(" smallint");
                    break;
                case "int":
                case "Integer":
                    query.append("\"").append(field.getName()).append("\"");
                    query.append(" integer");
                    break;
                case "long":
                case "Long":
                    query.append("\"").append(field.getName()).append("\"");
                    query.append(" bigint");
                    break;
                case "float":
                case "Float":
                    query.append("\"").append(field.getName()).append("\"");
                    query.append(" decimal");
                    break;
                case "double":
                case "Double":
                    query.append("\"").append(field.getName()).append("\"");
                    query.append(" double precision");
                    break;
                case "char":
                case "Char":
                    query.append("\"").append(field.getName()).append("\"");
                    query.append(" char");
                    break;
                case "boolean":
                case "Boolean":
                    query.append("\"").append(field.getName()).append("\"");
                    query.append(" boolean");
                    break;
                case "string":
                case "String":
                    query.append("\"").append(field.getName()).append("\"");
                    query.append(" text");
                    break;
                default:
                    query.append("\"").append(field.getName()).append("id").append("\"");
                    query.append(" integer");
                    break;
            }
            if (iterator.hasNext()) query.append(",");
        }
        return query.toString();
    }
}
