package group22.ci;

public class PayLoad {
    String id;
    String date;
    String ref;
    String name;
    String mail;
    String url;

    /**
     * Creavoid te a PushPayload object with the given parameters.
     * @param id: the id that corresponds to the commit
     * @param ref: the reference to the modified branch
     * @param date: commit date
     * @param name: the name of the pusher
     * @param mail: the email to which the server will send the approval
     * @param url: the URL for the repository
     */
    public PayLoad(String id, String ref, String date, String name, String mail, String url) {
        this.id = id;
        this.ref = ref;
        this.date = date;
        this.name = name;
        this.mail = mail;
        this.url = url;
    }

    @Override
    public String toString() {
        return "\nid: " + id + "ref: " + ref + "\ndate: " + date + "\nname: " + name + "\nemail: " + mail + "\nurl: " + url;
    }
    
}
