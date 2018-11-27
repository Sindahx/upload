package cn.refine.model;

/**
 * Created by Administrator on 2018/3/19.
 */
public class SysDefaultVO {
    /**
     * 类型
     */
    private String type;

    /**
     * 名称
     */
    private String name;


    private String keyer;

    /**
     * 值属性
     */
    private String val;


    public SysDefaultVO(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public SysDefaultVO(String type, String name,String val,String keyer) {
        this.type = type;
        this.name = name;
        this.val = val;
        this.keyer = keyer;
    }

    public SysDefaultVO() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getKeyer() {
        return keyer;
    }

    public void setKeyer(String keyer) {
        this.keyer = keyer;
    }
}
