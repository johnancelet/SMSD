package app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by winnerawan
 * on 5/11/17.
 */
public class Inbox {

    private String updatedindb;
    private String receivingdatetime;
    private String text;
    private String sendernumber;
    private String coding;
    private String udh;
    private String smscnumber;
    @SerializedName("Class")
    @Expose
    private String _class;
    private String textdecoded;
    private String id;
    private String recipientid;
    private String processed;
    private String count;

    public String getUpdatedindb() {
        return updatedindb;
    }

    public void setUpdatedindb(String updatedindb) {
        this.updatedindb = updatedindb;
    }

    public String getReceivingdatetime() {
        return receivingdatetime;
    }

    public void setReceivingdatetime(String receivingdatetime) {
        this.receivingdatetime = receivingdatetime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSendernumber() {
        return sendernumber;
    }

    public void setSendernumber(String sendernumber) {
        this.sendernumber = sendernumber;
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

    public String getSmscnumber() {
        return smscnumber;
    }

    public void setSmscnumber(String smscnumber) {
        this.smscnumber = smscnumber;
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

    public String getRecipientid() {
        return recipientid;
    }

    public void setRecipientid(String recipientid) {
        this.recipientid = recipientid;
    }

    public String getProcessed() {
        return processed;
    }

    public void setProcessed(String processed) {
        this.processed = processed;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
