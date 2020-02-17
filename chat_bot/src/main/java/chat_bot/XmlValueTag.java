package chat_bot;

public class XmlValueTag {

    private String value;
    private int weight = 1;

    public XmlValueTag(String value, int weight) {
        this.value = value;
        this.weight = weight;
    }

    public XmlValueTag(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void incrementWeight(){
        weight++;
    }

    @Override
    public String toString() {
        return "Value{" +
                "value='" + value + '\'' +
                ", weight=" + weight +
                '}';
    }
}
