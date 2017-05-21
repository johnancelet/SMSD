package app.service;

import app.model.*;
import app.util.Msg;
import app.util.ServerResponse;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by winnerawan
 * on 5/18/17.
 */
public class Service {

    private static final Logger logger = Logger.getLogger(Service.class.getCanonicalName());

    public Sql2o mysql = null;

    ServerResponse response = new ServerResponse();
    LoginResult loginResult = new LoginResult();

    public Service(Sql2o mysql) {
        this.mysql = mysql;
    }

    /** ----------------------- GAMMU ----------------------- */

    public ServerResponse api_version() {
        String sql = "SELECT * FROM gammu";
        try(Connection con = mysql.open()) {
            Gammu version = con.createQuery(sql).executeAndFetchFirst(Gammu.class);
            response.setStatus(200);
            response.setMessage(Msg.Congratulations());
            response.setResult(version);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public Gammu web_version() {
        String sql = "SELECT * FROM gammu";
        try(Connection con = mysql.open()) {
            return con.createQuery(sql).executeAndFetchFirst(Gammu.class);

        }
    }

    /** ----------------------- USER ----------------------- */

    public ServerResponse api_register(String username, String email, String password) {
        String sql = "INSERT INTO user(username, email, password) VALUES (:username,:email,:password)";
        try(Connection con = mysql.open()) {
            con.createQuery(sql)
                    //.addParameter("user_id", user_id)
                    .addParameter("username", username)
                    .addParameter("email", email)
                    .addParameter("password", password)
                    .executeUpdate();
            //if (u!=null) {
            User user = new User(username, email);
            response.setStatus(200);
            response.setMessage(Msg.OK());
            response.setResult(user);
            //} else {
            //response.setStatus(404);
            //response.setMessage(Msg.NotFound());
            //response.setResult(null);
            //}
        }catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public ServerResponse api_login(String email, String password) {
        String sql = "SELECT * FROM user WHERE email = :email";
        try(Connection con = mysql.open()) {
            User user = con.createQuery(sql).addParameter("email", email).executeAndFetchFirst(User.class);
            boolean match = BCrypt.checkpw(password, user.getPassword());
            if(match) {
                response.setStatus(200);
                response.setMessage(Msg.OK());
                response.setResult(user);
                return response;
            } else {
                response.setStatus(404);
                response.setMessage(Msg.NotFound());
                response.setResult(null);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public LoginResult web_login(String email, String password) {
        String sql = "SELECT * FROM user WHERE email = :email";
        try(Connection con = mysql.open()) {

            User user = con.createQuery(sql).addParameter("email", email).executeAndFetchFirst(User.class);
            boolean match = BCrypt.checkpw(password, user.getPassword());
            if(match) {
                loginResult.setUser(user);
                return loginResult;
            } else {
                loginResult.setError("Error");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return loginResult;
    }

    public String web_username_by_email(String email) {
        String sql = "SELECT *, NULL as password FROM user WHERE email = :email";
        try(Connection con = mysql.open()) {
            return con.createQuery(sql).addParameter("email", email).throwOnMappingFailure(false).executeAndFetchFirst(User.class).getUsername();
        }
    }



    /**
     * Fetch All Inbox
     * @return response
     */
    public ServerResponse api_inboxs() {
        String sql = "SELECT *, NULL as Class FROM inbox";
        try(Connection con = mysql.open()) {
            List<Inbox> inboxes = con.createQuery(sql)
                    .throwOnMappingFailure(false).executeAndFetch(Inbox.class);
            if (inboxes!=null) {
                response.setStatus(200);
                response.setMessage(Msg.OK());
                response.setResult(inboxes);
            } else {
                response.setStatus(404);
                response.setMessage(Msg.NotFound());
                response.setResult(null);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * Fetch Single Message by Number
     * @param SenderNumber of inbox
     * @return response
     */
    public ServerResponse api_inbox(String SenderNumber) {
        String sql = "SELECT *, NULL as Class FROM inbox WHERE sendernumber= :sendernumber LIMIT 1 ";
        try(Connection con = mysql.open()) {
            Inbox inbox = con.createQuery(sql).addParameter("sendernumber", SenderNumber)
                    .throwOnMappingFailure(false).executeAndFetchFirst(Inbox.class);
            if (inbox!=null) {
                response.setStatus(200);
                response.setMessage(Msg.OK());
                response.setResult(inbox);
            } else {
                response.setStatus(404);
                response.setMessage(Msg.NotFound());
                response.setResult(null);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * Fetch All Inbox by Number
     * @param SenderNumber of inbox
     * @return response
     */
    public ServerResponse api_inboxs(String SenderNumber) {
        String sql = "SELECT *, NULL as Class FROM inbox WHERE sendernumber= :sendernumber ";
        try(Connection con = mysql.open()) {
            List<Inbox> inboxs = con.createQuery(sql).addParameter("sendernumber", SenderNumber).throwOnMappingFailure(false).executeAndFetch(Inbox.class);
            if (inboxs!=null) {
                response.setStatus(200);
                response.setMessage(Msg.OK());
                response.setResult(inboxs);
            } else {
                response.setStatus(404);
                response.setMessage(Msg.NotFound());
                response.setMessage(null);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public List<Inbox> web_inboxes() {
        List<Inbox> inboxes =  new ArrayList<>();
        String sql = "SELECT *, NULL as Class FROM inbox";
        try (Connection con = mysql.open()) {
            inboxes = con.createQuery(sql).throwOnMappingFailure(false).executeAndFetch(Inbox.class);
            return inboxes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inboxes;
    }

    public Inbox web_inbox(String number) {
        Inbox inbox = new Inbox();
        String sql = "SELECT *, NULL as Class FROM inbox WHERE sendernumber= :sendernumber";
        try(Connection con = mysql.open()) {
            return con.createQuery(sql).addParameter("sendernumber", number).throwOnMappingFailure(false).executeAndFetchFirst(Inbox.class);
        }
    }

    public List<Inbox> web_inboxs(String number){
        String sql = "SELECT *, NULL as Class FROM inbox WHERE sendernumber= :sendernumber";
        try(Connection con = mysql.open()) {
            return con.createQuery(sql).addParameter("sendernumber", number).throwOnMappingFailure(false).executeAndFetch(Inbox.class);
        }
    }

    public List<MostSender> helperNumber() {
        String sql = "select sendernumber, count(sendernumber) counts from inbox group by sendernumber order by counts desc LIMIT 3";
        //String sql = "select replace(sendernumber,substring(sendernumber,1,1),'%2B') as number FROM inbox ";
        try(Connection con = mysql.open()) {
            return con.createQuery(sql).throwOnMappingFailure(false).executeAndFetch(MostSender.class);
        }
    }

    public String web_inbox_count(){
        String sql = "SELECT COUNT(*) as count FROM inbox";
        try(Connection con = mysql.open()) {
            return con.createQuery(sql).throwOnMappingFailure(false).executeAndFetchFirst(String.class);
        }
    }

    public List<MostSender> web_most_sender() {
        String sql = "select replace(sendernumber,substring(sendernumber,1,1),'%2B') as sendernumber, COUNT(*) as counts FROM inbox group by sendernumber order by counts desc LIMIT 3";
        //String sql = "select sendernumber, count(sendernumber) counts from inbox group by sendernumber order by counts desc LIMIT 3";
        try(Connection con = mysql.open()) {
            return con.createQuery(sql).executeAndFetch(MostSender.class);
        }
    }

    public List<Inbox> web_most_sender_inbox(String number) {
        String sql = "select *, sendernumber, count(sendernumber) counts from inbox group by sendernumber order by counts desc WHERE sendernumber = :sendernumber ";
        try(Connection con = mysql.open()) {
            return con.createQuery(sql).addParameter("sendernumber", number).throwOnMappingFailure(false).executeAndFetch(Inbox.class);
        }
    }

    public List<String> web_time_receive() {
        String sql = "SELECT receivingdatetime as time FROM inbox";
        try(Connection con = mysql.open()) {
            return con.createQuery(sql).executeAndFetch(String.class);
        }
    }

    public List<String> web_time_delivery_receive() {
        String sql = "SELECT deliverydatetime as time FROM sentitems";
        try(Connection con = mysql.open()) {
            return con.createQuery(sql).executeAndFetch(String.class);
        }
    }

    /** ----------------------- OUTBOX ----------------------- */

    public ServerResponse api_outboxs() {
        String sql = "SELECT * FROM outbox";
        try(Connection con = mysql.open()) {
            List<Outbox> outboxes = con.createQuery(sql).throwOnMappingFailure(false).executeAndFetch(Outbox.class);
            if (outboxes!=null) {
                response.setStatus(200);
                response.setMessage(Msg.OK());
                response.setResult(outboxes);
            } else {
                response.setStatus(404);
                response.setMessage(Msg.NotFound());
                response.setResult(null);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * Fetch Single Message by DestinationNumber
     * @param DestinationNumber of outbox
     * @return response
     */
    public ServerResponse api_outbox(String DestinationNumber) {
        String sql = "SELECT * FROM outbox WHERE destinationnumber= :destinationnumber LIMIT 1 ";
        try(Connection con = mysql.open()) {
            Outbox outbox = con.createQuery(sql).throwOnMappingFailure(false).addParameter("destinationnumber", DestinationNumber).executeAndFetchFirst(Outbox.class);
            if (outbox!=null) {
                response.setStatus(200);
                response.setMessage(Msg.OK());
                response.setResult(outbox);
            } else {
                response.setStatus(404);
                response.setMessage(Msg.NotFound());
                response.setResult(null);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * Fetch All Inbox by DestinationNumber
     * @param DestinationNumber of outbox
     * @return response
     */
    public ServerResponse api_outboxs(String DestinationNumber) {
        String sql = "SELECT * FROM outbox WHERE destinationnumber= :destinationnumber ";
        try(Connection con = mysql.open()) {
            List<Outbox> outboxs = con.createQuery(sql).throwOnMappingFailure(false).addParameter("destinationnumber", DestinationNumber).executeAndFetch(Outbox.class);
            if (outboxs!=null) {
                response.setStatus(200);
                response.setMessage(Msg.OK());
                response.setResult(outboxs);
            } else {
                response.setStatus(404);
                response.setMessage(Msg.NotFound());
                response.setMessage(null);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public List<Outbox> web_outboxs() {
        String sql = "SELECT * FROM outbox";
        try(Connection con = mysql.open()) {
            return con.createQuery(sql).throwOnMappingFailure(false).executeAndFetch(Outbox.class);
        }
    }

    public String web_outbox_count(){
        String sql = "SELECT COUNT(*) as count_outbox FROM outbox";
        try(Connection con = mysql.open()) {
            return con.createQuery(sql).throwOnMappingFailure(false).executeAndFetchFirst(String.class);
        }
    }

    public void sendMessage(String number, String text, String from) {
        String sql = "INSERT INTO outbox (destinationnumber,textdecoded,creatorid) VALUE (:destinationnumber, :textdecoded, :creatorid)";
        try(Connection con = mysql.open()) {
            con.createQuery(sql).addParameter("destinationnumber", number)
                    .addParameter("textdecoded", text).addParameter("creatorid", from).throwOnMappingFailure(false).executeUpdate();
        }
    }

    /** ------------------------- SENT ------------------------*/

    public String web_sent_count(){
        String sql = "SELECT COUNT(*) as sent_count FROM sentitems";
        try(Connection con = mysql.open()) {
            return con.createQuery(sql).throwOnMappingFailure(false).executeAndFetchFirst(String.class);
        }
    }

    public List<SentItem> web_sent_items() {
        String sql = "SELECT * FROM sentitems";
        try(Connection con = mysql.open()) {
            List<SentItem> sentItems = con.createQuery(sql).throwOnMappingFailure(false).executeAndFetch(SentItem.class);
            return sentItems;
        }
    }

}
