package ua.nure.kn.muzyka.usermanagement.web;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ua.nure.kn.muzyka.usermanagement.User;
import ua.nure.kn.muzyka.usermanagement.web.BrowseServlet;

public class BrowseServletTest extends ua.nure.kn.muzyka.usermanagement.web.MockServletTestCase {

    @Before
    protected void setUp() throws Exception {
        super.setUp();
        createServlet(BrowseServlet.class);
    }


    @Test
    public void testBrowse() {
        User user = new User(new Long(1000),"Inna", "Dvoinikova", new Date());
        List<User> list = Collections.singletonList(user);
        getMockUserDao().expectAndReturn("findAll", list);
        doGet();
        Collection<User> collection = (Collection<User>) getWebMockObjectFactory().getMockSession().getAttribute("users");
        assertNotNull("Could not find list of users in session", collection);
        assertSame(list, collection);

    }

    public void testEdit() {
        User user = new User(new Long(1000), "Inna", "Dvoinikova", new Date());
        getMockUserDao().expectAndReturn("find", new Long(1000), user);
        addRequestParameter("editButton", "Edit");
        addRequestParameter("id", "1000");
        doPost();
        User userInSession = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
        assertNotNull("Could not find user in session", user);
        assertSame(user, userInSession);
    }


    public void testDetails() {
        User user = new User(new Long(1000), "Inna", "Dvoinikova", new Date());
        getMockUserDao().expectAndReturn("find", new Long(1000), user);
        addRequestParameter("detailsButton", "Details");
        addRequestParameter("id", "1000");
        doPost();
        User userInSession = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
        assertNotNull("Could not find user in session", userInSession);
        assertEquals(user, userInSession);
    }

    public void testDelete() {
        User user = new User(new Long(1000), "Inna", "Dvoinikova", new Date());
        getMockUserDao().expectAndReturn("find", new Long(1000), user);
        getMockUserDao().expect("delete", user);
        addRequestParameter("deleteButton", "Delete");
        addRequestParameter("id", "1000");
        doPost();

        User userInSession = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
        assertNull("No such user in session scope", userInSession);
    }
}