package at.htl.iea.rest.auth;

import at.htl.iea.dao.AccountDao;
import at.htl.iea.model.Account;
import org.json.JSONObject;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Base64;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Cookie;

@Path("auth")
@ApplicationScoped
public class AuthentificationEndPoint {

    @Inject
    AccountDao accountDao;

    @POST
    @Path("login")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(String json) {
        JSONObject body = new JSONObject(json);
        try {
            String username = body.getString("username");
            String password = body.getString("password");
            if (!doesUserExist(username, password)) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            Account account = accountDao.getAccountByUsername(username);

            if (account.getPassword().equals(password)) {
                String accessToken = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
                return Response.ok().cookie(buildCookie(accessToken)).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        } catch (Exception ex) {
            System.err.println("[EXCEPTION]: AuthentificationEndPoint, Method: login");
            return Response.status(500).build();
        }
    }

    @POST
    @Path("register")
    @Consumes(MediaType.TEXT_PLAIN)
    @Transactional
    public Response register(String json) {
        JSONObject body = new JSONObject(json);
        try {
            String username = body.getString("username");
            String password = body.getString("password");
            String email = body.getString("email");
            String fullName = body.getString("fullName");

            Account acc = new Account(username, password, fullName, email);
            accountDao.persist(acc);

            return Response.ok().build();
        } catch (Exception ex) {
            System.err.println("[EXCEPTION]: AuthentificationEndPoint, Method: register: " + ex.getMessage());
            return Response.status(500).build();
        }
    }

    @POST
    @Path("logout")
    public Response logout() {
        // delete cookie
        return Response.status(404).header("Set-Cookie", "auth-token=deleted;Path=/;max-age=0;HttpOnly").build();
    }

    @GET
    @Path("authenticate")
    public Response authenticate(@CookieParam("auth-token") Cookie cookie) {
        if (cookie == null) {
            return Response.status(400).build();
        }

        String token = cookie.getValue();
        String decodedToken = new String(Base64.getDecoder().decode(token));
        String[] decodedTokenParts = decodedToken.split(":");
        String username = decodedTokenParts[0];
        String password = decodedTokenParts[1];

        if (!doesUserExist(username, password)) {
            System.out.println("USERNAME/PASSWORD is not correct!");
            // delete cookie
            return Response.status(403).header("Set-Cookie", "auth-token=deleted;Path=/;max-age=0;HttpOnly").build();
        }
        return Response.ok("Authenticated").build();
    }

    @GET
    @Path("/account")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountInfos(@CookieParam("auth-token") Cookie cookie) {
        if (cookie == null) {
            return Response.status(400).build();
        }

        String token = cookie.getValue();
        String decodedToken = new String(Base64.getDecoder().decode(token));
        String[] decodedTokenParts = decodedToken.split(":");
        String username = decodedTokenParts[0];

        try {
            Account tmp = accountDao.getAccountByUsername(username);
            return Response.ok(tmp).build();
        } catch (NoResultException ex) {
            Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().build();
    }

    @POST
    @Path("/account/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Response updateAccount(@PathParam("id") Long id, String json) {
        JSONObject body = new JSONObject(json);
        try {
            Long accountId = Long.parseLong(body.getInt("id")+"");
            Account temp = accountDao.getAccountById(accountId);
            temp.setPrefix(body.getString("prefix"));
            temp.setFullName(body.getString("fullName"));
            temp.setAddress(body.getString("address"));
            temp.setNotes(body.getString("notes"));
            temp.setEmail(body.getString("email"));

            accountDao.merge(temp);
            return Response.ok().build();
        } catch (Exception ex) {
            System.err.println("[EXCEPTION]: AuthentificationEndPoint, Method: updateAccount");
            return Response.status(500).build();
        }
    }

    private NewCookie buildCookie(String token) {
        long maxAge = 60 * 60; //1 hour
        return new NewCookie("auth-token", token, "/", null, 0, null, (int) maxAge,
                null, false, false);
    }

    private boolean doesUserExist(String username, String password) {
        try {
            Account account = accountDao.getAccountByUsername(username);
            return account.getPassword().equals(password);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
}
