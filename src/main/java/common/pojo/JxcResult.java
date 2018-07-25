package common.pojo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.List;

/**
 * 自定义响应结构
 */
public class JxcResult implements Serializable{



//************************************************************1	
//	{code: "100000", data: {total: 26, limit: "15", page: "1",…}, msg: "请求成功"}
//	code
//	:
//	"100000"
//	data
//	:
//	{total: 26, limit: "15", page: "1",…}
//	limit
//	:
//	"15"
//	list
//	:
//	[{account_id: 155, account_realname: "韦思店", account_phone: "13245784578", store_name: "步行街第三分店",…},…]
//	page
//	:
//	"1"
//	total
//	:
//	26
//	msg
//	:
//	"请求成功"
//************************************************************2
	
//	{code: "100000", data: {days: 20}, msg: "请求成功"}
//	code
//	:
//	"100000"
//	data
//	:
//	{days: 20}
//	days
//	:
//	20
//	msg
//	:
//	"请求成功"
	
	
//**********************************************************3	
//	{code: "100000", data: {days: 20,stauts:"1",name:'测试名称'}, msg: "请求成功"}
	
	
	
	
	
	
	
    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    // 响应业务状态
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    public static JxcResult build(Integer status, String msg, Object data) {
        return new JxcResult(status, msg, data);
    }

    public static JxcResult ok(Object data) {
        return new JxcResult(data);
    }

    public static JxcResult ok() {
        return new JxcResult(null);
    }

    public static JxcResult notFound() {
        return new JxcResult(404,"Not Found",null);
    }

    public static JxcResult badRequest() {
        return new JxcResult(400,"Bad Request",null);
    }

    public static JxcResult forbidden() {
        return new JxcResult(403,"Forbidden",null);
    }

    public static JxcResult unauthorized() {
        return new JxcResult(401,"unauthorized",null);
    }

    public static JxcResult serverInternalError() {
        return new JxcResult(500,"Server Internal Error",null);
    }

    public static JxcResult customerError() {
        return new JxcResult(1001,"customer Error",null);
    }

    public JxcResult() {

    }

    public static JxcResult build(Integer status, String msg) {
        return new JxcResult(status, msg, null);
    }

    public JxcResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public JxcResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

//    public Boolean isOK() {
//        return this.status == 200;
//    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 将json结果集转化为TaotaoResult对象
     * 
     * @param jsonData json数据
     * @param clazz TaotaoResult中的object类型
     * @return
     */
    public static JxcResult formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, JxcResult.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 没有object对象的转化
     * 
     * @param json
     * @return
     */
    public static JxcResult format(String json) {
        try {
            return MAPPER.readValue(json, JxcResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Object是集合转化
     * 
     * @param jsonData json数据
     * @param clazz 集合中的类型
     * @return
     */
    public static JxcResult formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

}
