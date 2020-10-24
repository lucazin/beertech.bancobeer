package br.com.beertech.fusion.controller.dto;

public class RoleDTO {

    private long userId;
    private String roleType;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }


}
