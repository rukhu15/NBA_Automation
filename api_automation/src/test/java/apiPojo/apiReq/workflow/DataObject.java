package apiPojo.apiReq.workflow;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Prateek Sethi
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataObject {
    String model_name;
    String brand;
    String channel;
    String updated_date;
    int count;
    float x;

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(String updated_date) {
        this.updated_date = updated_date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }
}
