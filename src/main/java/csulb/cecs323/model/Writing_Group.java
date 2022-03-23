package csulb.cecs323.model;


public class Writing_Group extends Authoring_Entity
{
    private String head_writer;
    private int year_formed;

    public String getHead_writer() {
        return head_writer;
    }

    public void setHead_writer(String head_writer) {
        this.head_writer = head_writer;
    }

    public int getYear_formed() {
        return year_formed;
    }

    public void setYear_formed(int year_formed) {
        this.year_formed = year_formed;
    }
}
