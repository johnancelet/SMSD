package app.controller;

import app.model.*;
import app.service.Service;
import app.util.JsonUtil;
import app.util.ServerResponse;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.helper.StringHelpers;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.ocpsoft.pretty.time.PrettyTime;
import org.springframework.security.crypto.bcrypt.BCrypt;
import spark.ModelAndView;
import spark.Request;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static app.util.JsonUtil.json;
import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.post;

/**
 * Created by winnerawan
 * on 5/18/17.
 */
public class ServiceController {

    private static final String USER_SESSION_ID = "user";

    public ServiceController(Service service) {

        before("/admin/index", (request, response) -> {
            User user = getAuthenticatedUser(request);
            if (user == null) {
                response.redirect("/admin");
            }
        });

        before("/admin/outbox", (request, response) -> {
            User user = getAuthenticatedUser(request);
            if (user == null) {
                response.redirect("/admin");
            }
        });

        before("/admin/sender/*", (request, response) -> {
            User user = getAuthenticatedUser(request);
            if (user == null) {
                response.redirect("/admin");
            }
        });

        before("/admin/senditems", (request, response) -> {
            User user = getAuthenticatedUser(request);
            if (user == null) {
                response.redirect("/admin");
            }
        });
        /**
         * JSON
         * Mobile Version
         * REST API
         */
        get("/", (request, response) -> {
            response.type("application/json");
            return JsonUtil.toJson(service.api_version());
        });

        post("/register", (request, response) -> {
            //String user_id = request.queryParams(":user_id");
            response.type("application/json");
            String username = request.queryParams("username");
            String email = request.queryParams("email");
            String password = request.queryParams("password");
            String encodePassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
            return JsonUtil.toJson(service.api_register(username, email, encodePassword));
        });

        post("/login", (request, response) -> {
            response.type("application/json");
            String email = request.queryParams("email");
            String password = request.queryParams("password");
            return JsonUtil.toJson(service.api_login(email, password));
        });

        /**
         * LOGIN ACTION
         */
        post("/admin/login", (request, response) -> {
            response.type("text/html");
            Map<String, Object> map = new HashMap<String, Object>();
            String mail = request.queryParams("email");
            String pass = request.queryParams("password");
            User user = new User(mail, pass);
            LoginResult loginResult = service.web_login(mail, pass);
            if (loginResult.getUser() != null) {
                map.put("user", getAuthenticatedUser(request));
                response.redirect("/admin/index");
                addAuthenticatedUser(request, loginResult.getUser());
            } else {
                response.redirect("/");
            }
            //map.put("email", mail);
            //map.put("password", pass);
            //map.put("u", getAuthenticatedUser(request));
            return new ModelAndView(map, "admin/login.hbs");
        }, new HandlebarsTemplateEngine());

        /**
         * RESERVE LOGIN 
         */
        get("/admin/login", (request, response) -> new ModelAndView(null, "/admin/login.hbs"), new HandlebarsTemplateEngine());

        get("/admin/logout", (request, response) -> {
            removeAuthenticatedUser(request);
            response.redirect("/admin");
            return null;
        });

//        get("/admin/tes", (request, response) -> JsonUtil.toJson(getAuthenticatedUser(request)));

        /**
         * Web Application Version
         */
        get("/admin", (request, response) -> {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("data", service.web_version());
            return new ModelAndView(map, "admin/login.hbs");
        }, new HandlebarsTemplateEngine());

        get("/inboxes", (request, response) -> {
            response.type("application/json");
            return JsonUtil.toJson(service.api_inboxs());
        });

        get("/inbox/:sendernumber", (request, response) -> {
            String number = request.params(":sendernumber");
            ServerResponse result = service.api_inbox(number);
            return JsonUtil.toJson(result);
        });

        get("/webinbox/:sendernumber", (request, response) -> {
            Map map = new HashMap();
            map.put("inbox", service.web_inbox(request.queryParams("sendernumber")));
            return new ModelAndView(map, null);
        }, new HandlebarsTemplateEngine());

        get("/inboxs/:sendernumber", (request, response) -> {
            String number = request.params(":sendernumber");
            ServerResponse result = service.api_inbox(number);
            return JsonUtil.toJson(result);
        });

        get("/admin/index", (request, response) -> {
            //response.type("text/html");

            Map map = new HashMap();
            List<Inbox> inboxes = service.web_inboxes();
            List<String> timers = service.web_time_receive();
            map.put("inboxes", inboxes);
            map.put("inbox_count", service.web_inbox_count());
            map.put("outbox_count", service.web_outbox_count());
            map.put("sent_count", service.web_sent_count());
            map.put("most_sender", service.web_most_sender());
            map.put("helper_sender", service.helperNumber());
            map.put("user", getAuthenticatedUser(request));
            for (int i = 0; i < inboxes.size(); i++) {
                Inbox inbox = inboxes.get(i);
//                map.put("date", humanTime(date));
                map.put("date", humanTime(service.web_time_receive().get(i)));
                map.put("inbox", service.web_inbox(inbox.getSendernumber()));
            }
            return new ModelAndView(map, "admin/index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/admin/inbox", (request, response) -> {
            //response.type("text/html");

            Map map = new HashMap();
            List<Inbox> inboxes = service.web_inboxes();
            List<String> timers = service.web_time_receive();
            map.put("inboxes", inboxes);
            map.put("inbox_count", service.web_inbox_count());
            map.put("outbox_count", service.web_outbox_count());
            map.put("sent_count", service.web_sent_count());
            map.put("most_sender", service.web_most_sender());
            map.put("user", getAuthenticatedUser(request));
            for (int i = 0; i < inboxes.size(); i++) {
                Inbox inbox = inboxes.get(i);
//                map.put("date", humanTime(date));
                map.put("date", humanTime(service.web_time_receive().get(i)));
                map.put("inbox", service.web_inbox(inbox.getSendernumber()));
            }
            return new ModelAndView(map, "admin/index.hbs");
        }, new HandlebarsTemplateEngine());

        get("/admin/count", (request, response) -> service.web_inbox_count(), json());

        get("/outboxes", (request, response) -> service.api_outboxs(), json());

        get("/outbox/:SenderNumber", (request, response) -> {
            String number = request.params(":SenderNumber");
            ServerResponse result = service.api_outbox(number);
            return JsonUtil.toJson(result);
        });

        get("/outboxs/:DestinationNumber", (request, response) -> {
            String number = request.params(":DestinationNumber");
            ServerResponse result = service.api_outbox(number);
            return JsonUtil.toJson(result);
        });

        get("/admin/outbox", (request, response) -> {
            Map map = new HashMap();
            List<Outbox> outboxes = service.web_outboxs();
            map.put("outboxes", outboxes);
            map.put("inbox_count", service.web_inbox_count());
            map.put("outbox_count", service.web_outbox_count());
            map.put("sent_count", service.web_sent_count());
            map.put("most_sender", service.web_most_sender());
            map.put("user", getAuthenticatedUser(request));
            for (int i = 0; i < outboxes.size(); i++) {
                map.put("date", humanTime(service.web_time_receive().get(i)));
            }
                return new ModelAndView(map, "/admin/outbox.hbs");
        }, new HandlebarsTemplateEngine());

//        get("/admin/in/:sendernumber", (request, response) -> JsonUtil.toJson(service.api_inbox(request.params(":sendernumber").replaceFirst("\\+", "%2B"))));
//
        get("/admin/sender/:sendernumber", (request, response) -> {
            Map map = new HashMap();
            Handlebars handlebars = new Handlebars();
            handlebars.registerHelpers(StringHelpers.class);
            map.put("inbox_count", service.web_inbox_count());
            map.put("outbox_count", service.web_outbox_count());
            map.put("sent_count", service.web_sent_count());
            map.put("most_sender", service.web_most_sender());
            map.put("user", getAuthenticatedUser(request));
            map.put("sender", service.web_inboxs(request.params(":sendernumber")));
            return new ModelAndView(map, "/admin/sender.hbs");
        }, new HandlebarsTemplateEngine());


        get("/admin/senditems", (request, response) -> {
            Map map = new HashMap();
            List<SentItem> sentItems = service.web_sent_items();
            map.put("sent_items", sentItems);
            map.put("inbox_count", service.web_inbox_count());
            map.put("outbox_count", service.web_outbox_count());
            map.put("sent_count", service.web_sent_count());
            map.put("most_sender", service.web_most_sender());
            map.put("user", getAuthenticatedUser(request));
            for (int i = 0; i < sentItems.size(); i++) {
                map.put("date", service.web_time_receive().get(i));
            }
            return new ModelAndView(map, "/admin/sentitems.hbs");
        }, new HandlebarsTemplateEngine());


        post("/admin/send_message", (request, response) -> {
            response.type("text/html");
            Map<String, Object> map = new HashMap<String, Object>();
            String number = request.queryParams("number");
            String text = request.queryParams("text_message");
            String from = getAuthenticatedUser(request).getUsername();
            service.sendMessage(number, text, from);
            response.redirect("/admin/index");
            return null;
        });

        post("/admin/send", (request, response) -> {
            response.type("application/json");
            service.sendMessage(request.queryParams("number"), request.queryParams("text_message"), "admin");
            return null;
        });

//        get("/admin/inbox", (request, response) -> inboxModel.webAllInbox(), json());
    }

    public String humanTime(String timestamp) {
        //String humanTime = timestamp;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        PrettyTime p = new PrettyTime();
        String time = p.format(convertedDate);
        return time;
    }

    private void addAuthenticatedUser(Request request, User u) {
        request.session().attribute(USER_SESSION_ID, u);
    }

    private void removeAuthenticatedUser(Request request) {
        request.session().removeAttribute(USER_SESSION_ID);

    }

    private User getAuthenticatedUser(Request request) {
        return request.session().attribute(USER_SESSION_ID);
    }

}
