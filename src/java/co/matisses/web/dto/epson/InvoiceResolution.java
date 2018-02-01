package co.matisses.web.dto.epson;

public class InvoiceResolution {

    private String number;
    private String prefix;
    private String date;
    private Integer from;
    private Integer to;

    public InvoiceResolution() {
    }

    public InvoiceResolution(String number, String prefix, String date, Integer from, Integer to) {
        this.number = number;
        this.prefix = prefix;
        this.date = date;
        this.from = from;
        this.to = to;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "InvoiceResolution [number=" + number + ", prefix=" + prefix + ", date=" + date + ", from=" + from
                + ", to=" + to + "]";
    }
}
