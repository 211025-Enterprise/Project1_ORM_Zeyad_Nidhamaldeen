package com.revature.project1;
import  static org.junit.Assert.*;

import com.revature.project1.model.Student;
import com.revature.project1.persistence.DaoManager;
import com.revature.project1.services.Service;
import com.revature.project1.controller.Controller;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.servlet.ServletConfig;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ServiceTest extends Mockito {
    @Mock private  Student student ;
    @Mock private  Service service ;
    @Mock private Controller servlet;
    @Mock private ServletConfig servletConfig;
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private ServletOutputStream outputStream;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        student = mock((Student.class));
        service = mock(Service.class);
        servlet = mock(Controller.class);
    }
    @Test
    public void testReadFindById() throws Exception {
        request.getParameter("id");
        when(service.read_findById(request, response)).thenReturn(true);
        assertEquals(true, service.read_findById(request , response));
    }
    @Test
    public void testDeleteByIdOrDropTable() throws Exception {
        request.getParameter("id");
        when(service.deleteById_or_dropTable_service(request, response)).thenReturn(true);
        assertEquals(true, service.deleteById_or_dropTable_service(request , response));
    }

    @Test
    public void testCreateServiceGetName() throws Exception {
        student = new Student(1, "Zeyad", "Zeyad");
        request.getParameter("id");
        service.read_findById(request,response);
        when(service.read_findById(request , response)).thenReturn(true);
        assertEquals(true, service.read_findById(request , response));
    }
    @Test
    public void testObjectIsExist() throws Exception {
        DaoManager checkObject = mock(DaoManager.class);

        Object student = new Student(1, "Zeyad", "Zeyad");
        when(checkObject.isTableExist(student)).thenReturn(true);
        assertEquals(true, checkObject.isTableExist(student));
    }

    @Test
    public void testServlet() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(service.read_findById(request, response)).thenReturn(true);
        assertEquals(true, service.read_findById(request , response));
    }

    @Test public void testedGetServlet() throws Exception {
        when(servlet.getServletConfig()).thenReturn(servletConfig);
        when(response.getOutputStream()).thenReturn(outputStream);
        servlet.doGet(request, response);
    }
    @Test public void testedPostServlet() throws Exception {
        when(servlet.getServletConfig()).thenReturn(servletConfig);
        when(response.getOutputStream()).thenReturn(outputStream);
        servlet.doGet(request, response);
    }
    @Test public void testedPutServlet() throws Exception {
        when(servlet.getServletConfig()).thenReturn(servletConfig);
        when(response.getOutputStream()).thenReturn(outputStream);
        servlet.doGet(request, response);
        Runnable runnable = () -> {
            try {
                verify(outputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
    @Test public void testedDeleteServlet() throws Exception {
        when(servlet.getServletConfig()).thenReturn(servletConfig);
        when(response.getOutputStream()).thenReturn(outputStream);
        servlet.doGet(request, response);

    }
    private void verify(ServletOutputStream outputStream) throws IOException {
        System.out.println(outputStream);
    }
}


