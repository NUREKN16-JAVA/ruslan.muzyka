package ua.nure.kn.muzyka.usermanagement.web;
import ua.nure.kn.muzyka.usermanagement.User;
import ua.nure.kn.muzyka.usermanagement.web.DetailsServlet;

public class DetailsServletTest extends ua.nure.kn.muzyka.usermanagement.web.MockServletTestCase {

    @Override
    public void setUp() throws Exception {
        super.setUp();
        createServlet(DetailsServlet.class);
    }

    public void testBack() {
        addRequestParameter("backButton", "BACK");
        User nullUser = (User) getWebMockObjectFactory().getMockSession().getAttribute("user");
        assertNull("User must not exist in session scope", nullUser);
    }
}