package com.lks.test.demo.dto;

/**
 * @author lks
 * @description todo
 * @e-mail 1056224715@qq.com
 * @date 2023/4/6 18:22
 */
public class TodoItemReq {

    /**
     * 待办创建人账号[是指人员八位编码吗？还是其它字段？]
     */
    private String creatorId;

    /**
     * 待办标题
     */
    private String todoTitle;

    /**
     * 发送时间
     */
    private String sendTime;

    /**
     * 待办类型
     */
    private String todoType;

    /**
     * 系统编码
     */
    private String systemCode;

    /**
     * 模块编码
     */
    private String moduleCode;

    /**
     * 待办归属人账号
     */
    private String ownerId;

    /**
     * 待办紧急程度
     */
    private String urgentLevel;

    /**
     * 待办详情链接地址
     */
    private String todoUrl;

    /**
     * 移动端待办详情地址
     */
    private String mobileUrl;

    /**
     * 待办id
     */
    private String todoNum;

    /**
     * 密级编码[同上，是否考虑编码映射]
     */
    private String secretCode;

    /**
     * 回调地址
     */
    private String callBackUrl;

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getTodoTitle() {
        return todoTitle;
    }

    public void setTodoTitle(String todoTitle) {
        this.todoTitle = todoTitle;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getTodoType() {
        return todoType;
    }

    public void setTodoType(String todoType) {
        this.todoType = todoType;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getUrgentLevel() {
        return urgentLevel;
    }

    public void setUrgentLevel(String urgentLevel) {
        this.urgentLevel = urgentLevel;
    }

    public String getTodoUrl() {
        return todoUrl;
    }

    public void setTodoUrl(String todoUrl) {
        this.todoUrl = todoUrl;
    }

    public String getMobileUrl() {
        return mobileUrl;
    }

    public void setMobileUrl(String mobileUrl) {
        this.mobileUrl = mobileUrl;
    }

    public String getTodoNum() {
        return todoNum;
    }

    public void setTodoNum(String todoNum) {
        this.todoNum = todoNum;
    }

    public String getSecretCode() {
        return secretCode;
    }

    public void setSecretCode(String secretCode) {
        this.secretCode = secretCode;
    }

    public String getCallBackUrl() {
        return callBackUrl;
    }

    public void setCallBackUrl(String callBackUrl) {
        this.callBackUrl = callBackUrl;
    }

}
