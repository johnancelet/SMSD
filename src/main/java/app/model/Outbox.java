package app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by winnerawan
 * on 5/15/17.
 */
public class Outbox {

    private String updatedindb;
    private String insertintodb;
    private String sendingdatetime;
    private String sendbefore;
    private String sendafter;
    private String text;
    private String destinationnumber;
    private String coding;
    private String udh;
    @SerializedName("Class")
    @Expose
    private String _class;
    private String textdecoded;
    private String id;
    private String multipart;
    private String relativevalidity;
    private String senderid;
    private String sendingtimeout;
    private String deliveryreport;
    private String creatorid;
    private String retries;
    private String priority;

    public String getUpdatedindb() {
        return updatedindb;
    }

    public void setUpdatedindb(String updatedindb) {
        this.updatedindb = updatedindb;
    }

    public String getInsertintodb() {
        return insertintodb;
    }

    public void setInsertintodb(String insertintodb) {
        this.insertintodb = insertintodb;
    }

    public String getSendingdatetime() {
        return sendingdatetime;
    }

    public void setSendingdatetime(String sendingdatetime) {
        this.sendingdatetime = sendingdatetime;
    }

    public String getSendbefore() {
        return sendbefore;
    }

    public void setSendbefore(String sendbefore) {
        this.sendbefore = sendbefore;
    }

    public String getSendafter() {
        return sendafter;
    }

    public void setSendafter(String sendafter) {
        this.sendafter = sendafter;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDestinationnumber() {
        return destinationnumber;
    }

    public void setDestinationnumber(String destinationnumber) {
        this.destinationnumber = destinationnumber;
    }

    public String getCoding() {
        return coding;
    }

    public void setCoding(String coding) {
        this.coding = coding;
    }

    public String getUdh() {
        return udh;
    }

    public void setUdh(String udh) {
        this.udh = udh;
    }

    public String get_class() {
        return _class;
    }

    public void set_class(String _class) {
        this._class = _class;
    }

    public String getTextdecoded() {
        return textdecoded;
    }

    public void setTextdecoded(String textdecoded) {
        this.textdecoded = textdecoded;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMultipart() {
        return multipart;
    }

    public void setMultipart(String multipart) {
        this.multipart = multipart;
    }

    public String getRelativevalidity() {
        return relativevalidity;
    }

    public void setRelativevalidity(String relativevalidity) {
        this.relativevalidity = relativevalidity;
    }

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public String getSendingtimeout() {
        return sendingtimeout;
    }

    public void setSendingtimeout(String sendingtimeout) {
        this.sendingtimeout = sendingtimeout;
    }

    public String getDeliveryreport() {
        return deliveryreport;
    }

    public void setDeliveryreport(String deliveryreport) {
        this.deliveryreport = deliveryreport;
    }

    public String getCreatorid() {
        return creatorid;
    }

    public void setCreatorid(String creatorid) {
        this.creatorid = creatorid;
    }

    public String getRetries() {
        return retries;
    }

    public void setRetries(String retries) {
        this.retries = retries;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
