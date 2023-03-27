package bjfu.BJFU.mall.entity;

public class Address {
    private Integer id;
    private Integer uid;
    private String name;
    private String address;
    private String phone;

    public Address() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getDfault() {
        return dfault;
    }

    public void setDfault(Boolean dfault) {
        this.dfault = dfault;
    }

    public Address(Integer id, Integer uid, String name, String address, String phone, Boolean dfault) {
        this.id = id;
        this.uid = uid;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.dfault = dfault;
    }

    private Boolean dfault;
}
