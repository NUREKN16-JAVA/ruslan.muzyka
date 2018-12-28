package ua.nure.kn.muzyka.usermanagement.web;

import java.text.DateFormat;
import java.util.Date;

import ua.nure.kn.muzyka.usermanagement.User;
import ua.nure.kn.muzyka.usermanagement.web.AddServlet;

public class AddServletTest extends ua.nure.kn.muzyka.usermanagement.web.MockServletTestCase {
    @Override
    public void setUp() throws Exception {
        super.setUp();
        createServlet(AddServlet.class);
    }
    public void testAdd() {
        Date date = new Date();
        User newUser = new User("Inna", "Dvoinikova", date);
        User user = new User(new Long(1000), "Inna", "Dvoinikova", date);
        getMockUserDao().expectAndReturn("create", newUser, user);

        addRequestParameter("firstName", "Inna");
        addRequestParameter("lastName", "Dvoinikova");
        addRequestParameter("date", DateFormat.getDateInstance().format(date));
        addRequestParameter("okButton", "Ok");
        doPost();
    }

    public void testAddEmptyFirstName() {
        Date date = new Date();
        addRequestParameter("lastName", "Dvoinikova");
        addRequestParameter("date", DateFormat.getDateInstance().format(date));
        addRequestParameter("okButton", "Ok");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Could not find error message in session scope", errorMessage);
    }

    public void testAddEmptyLastName() {
        Date date = new Date();
        addRequestParameter("firstName", "Inna");
        addRequestParameter("date", DateFormat.getDateInstance().format(date));
        addRequestParameter("okButton", "Ok");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Could not find error message in session scope", errorMessage);
    }

    public void testAddEmptyDate() {
        Date date = new Date();
        addRequestParameter("firstName", "Inna");
        addRequestParameter("lastName", "Dvoinikova");
        addRequestParameter("okButton", "Ok");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Could not find error message in session scope", errorMessage);
    }

    public void testAddInvalidDate() {
        Date date = new Date();
        addRequestParameter("firstName", "Inna");
        addRequestParameter("lastName", "Dvoinikova");
        addRequestParameter("date", "testtestInnatest1");
        addRequestParameter("okButton", "Ok");
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute("error");
        assertNotNull("Could not find error message in session scope", errorMessage);
    }
}
