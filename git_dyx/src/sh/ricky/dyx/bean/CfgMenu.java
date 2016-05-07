package sh.ricky.dyx.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 操作菜单
 * 
 * @author SHI
 */
public class CfgMenu implements java.io.Serializable {

    private static final long serialVersionUID = -2653055015687013547L;

    private String menuId;

    private String menuName;

    private String menuLink;

    private String menuIcon;

    private String parentId;

    private String authId;

    private String valid;

    private List<CfgMenu> subMenuList;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuLink() {
        return menuLink;
    }

    public void setMenuLink(String menuLink) {
        this.menuLink = menuLink;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public List<CfgMenu> getSubMenuList() {
        return subMenuList;
    }

    public void setSubMenuList(List<CfgMenu> subMenuList) {
        this.subMenuList = subMenuList;
    }

    public void addToSubMenuList(CfgMenu menu) {
        if (this.subMenuList == null) {
            this.subMenuList = new ArrayList<CfgMenu>();
        }

        this.subMenuList.add(menu);
    }
}
