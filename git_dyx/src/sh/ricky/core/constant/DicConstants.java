package sh.ricky.core.constant;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import sh.ricky.core.dao.DicDAO;
import sh.ricky.dyx.bean.CfgFlow;
import sh.ricky.dyx.bean.CfgMenu;
import sh.ricky.dyx.constant.InstConstants;

public class DicConstants {
    /** 单体实例 */
    private static DicConstants instance;

    /** 字典查询 */
    private DicDAO dicDAO;

    /** 操作菜单配置 */
    private Map<String, CfgMenu> dicMenu;

    /** 角色字典 */
    private Map<String, String> dicRole;

    /** 角色权限映射配置 */
    private Map<String, List<String>> dicRoleAuth;

    /** 流程配置 */
    private Map<String, List<CfgFlow>> dicFlowCfg;

    /** 流程操作字典 */
    private Map<String, String> dicActn;

    /** 流程环节字典 */
    private Map<String, String> dicSeg;

    /** 流程环节状态字典 */
    private Map<String, String> dicSegStat;

    /** 审核状态字典 */
    private Map<String, List<String>> dicAudtStat;

    /** 分期名称字典 */
    private Map<String, String> dicInstName;

    /** 操作员字典 */
    private Map<String, String> dicOpUser;

    /** 分期业务状态字典 */
    private Map<String, String> dicInstStat;

    /** 分期还款方式字典 */
    private Map<String, String> dicRepMethod;

    /** 性别字典 */
    private Map<String, String> dicSex;

    /** 行政区划字典 */
    private Map<String, String> dicRegion;

    /** 银行字典 */
    private Map<String, String> dicBank;

    /** 教育程度字典 */
    private Map<String, String> dicEdu;

    /** 婚姻状况字典 */
    private Map<String, String> dicMari;

    /** 住宅状况字典 */
    private Map<String, String> dicHomeStat;

    /** 受雇类型字典 */
    private Map<String, String> dicEmpType;

    /** 工作性质字典 */
    private Map<String, String> dicJobSpec;

    /** 单位规模字典 */
    private Map<String, String> dicCompSize;

    /** 单位性质字典 */
    private Map<String, String> dicCompNatu;

    /** 职务级别字典 */
    private Map<String, String> dicJobTitle;

    /** 联系人关系字典 */
    private Map<String, String> dicContRel;

    /** 附件类型字典 */
    private Map<String, String> dicAtchType;

    /** 客户端状态字典 */
    private Map<String, String> dicClientStat;

    /**
     * 私有构造器
     */
    private DicConstants() {
        dicDAO = new DicDAO();
    }

    /**
     * @return 返回 instance。
     */
    public static DicConstants getInstance() {
        if (instance == null) {
            instance = new DicConstants();
        }
        return instance;
    }

    /**
     * 初始化字典
     */
    public void init() throws Exception {
        // 操作菜单配置
        this.dicMenu = dicDAO.getDicMenu();

        // 角色字典 */
        this.dicRole = dicDAO.getDicRole();

        // 角色权限映射配置
        this.dicRoleAuth = dicDAO.getDicRoleAuth();

        // 流程配置
        this.dicFlowCfg = dicDAO.getDicFlowCfg();

        // 流程操作字典
        this.dicActn = dicDAO.getDicActn();

        // 流程环节字典
        this.dicSeg = dicDAO.getDicSeg();

        // 流程环节状态字典
        this.dicSegStat = dicDAO.getDicSegStat();

        // 审核状态字典
        this.dicAudtStat = new LinkedHashMap<String, List<String>>();
        for (String stat : dicSegStat.keySet()) {
            String statName = dicSegStat.get(stat);
            if (!dicAudtStat.containsKey(statName)) {
                dicAudtStat.put(statName, new ArrayList<String>());
            }
            dicAudtStat.get(statName).add(stat);
        }

        // 分期名称字典
        this.dicInstName = dicDAO.getDicInstName();

        // 操作员字典
        this.dicOpUser = dicDAO.getDicOpUser();

        // 分期业务状态字典
        this.dicInstStat = new LinkedHashMap<String, String>();
        dicInstStat.put(InstConstants.INST_STAT_YQD, "已启动");
        dicInstStat.put(InstConstants.INST_STAT_YDJ, "已冻结");

        // 分期还款方式字典
        this.dicRepMethod = new LinkedHashMap<String, String>();
        dicRepMethod.put(InstConstants.REP_METHOD_DEBX, "等额本息");
        dicRepMethod.put(InstConstants.REP_METHOD_DEBJ, "等额本金");

        // 性别字典
        this.dicSex = new LinkedHashMap<String, String>();
        dicSex.put("1", "男");
        dicSex.put("2", "女");

        // 行政区划字典
        this.dicRegion = new LinkedHashMap<String, String>();
        Object json = (new ObjectMapper()).readValue(new FileReader(System.getProperty("webapp.root") + "static/js/cityData.min.json"), List.class);
        this.fromCityDataJson(dicRegion, json);

        // 银行字典
        this.dicBank = new LinkedHashMap<String, String>();
        dicBank.put("中国银行", "中国银行");
        dicBank.put("中国农业银行", "中国农业银行");
        dicBank.put("中国工商银行", "中国工商银行");
        dicBank.put("中国建设银行", "中国建设银行");
        dicBank.put("中国邮政银行", "中国邮政银行");
        dicBank.put("中信银行", "中信银行");
        dicBank.put("兴业银行", "兴业银行");
        dicBank.put("光大银行", "光大银行");

        // 教育程度字典
        this.dicEdu = new LinkedHashMap<String, String>();
        dicEdu.put("硕士及以上", "硕士及以上");
        dicEdu.put("本科", "本科");
        dicEdu.put("大专", "大专");
        dicEdu.put("高中/中专", "高中/中专");
        dicEdu.put("初中及以下", "初中及以下");

        // 婚姻状况字典
        this.dicMari = new LinkedHashMap<String, String>();
        dicMari.put("未婚", "未婚");
        dicMari.put("已婚有子女", "已婚有子女");
        dicMari.put("已婚无子女", "已婚无子女");
        dicMari.put("离婚", "离婚");
        dicMari.put("丧偶", "丧偶");

        // 住宅状况字典
        this.dicHomeStat = new LinkedHashMap<String, String>();
        dicHomeStat.put("自购无按揭", "自购无按揭");
        dicHomeStat.put("按揭", "按揭");
        dicHomeStat.put("租赁", "租赁");
        dicHomeStat.put("与父母同住", "与父母同住");
        dicHomeStat.put("单位宿舍", "单位宿舍");
        dicHomeStat.put("自建", "自建");
        dicHomeStat.put("其他", "其他");

        // 受雇类型字典
        this.dicEmpType = new LinkedHashMap<String, String>();
        dicEmpType.put("授薪", "授薪");
        dicEmpType.put("自雇", "自雇");

        // 工作性质字典
        this.dicJobSpec = new LinkedHashMap<String, String>();
        dicJobSpec.put("公司职员", "公司职员");
        dicJobSpec.put("务工人员", "务工人员");
        dicJobSpec.put("务农人员", "务农人员");
        dicJobSpec.put("学生", "学生");
        dicJobSpec.put("离退人员", "离退人员");
        dicJobSpec.put("其他", "其他");

        // 单位规模字典
        this.dicCompSize = new LinkedHashMap<String, String>();
        dicCompSize.put("10人以下", "10人以下");
        dicCompSize.put("10-100人", "10-100人");
        dicCompSize.put("101-1000人", "101-1000人");
        dicCompSize.put("1001-10000人", "1001-10000人");
        dicCompSize.put("10000人以上", "10000人以上");

        // 单位性质字典
        this.dicCompNatu = new LinkedHashMap<String, String>();
        dicCompNatu.put("国家机关/事业单位", "国家机关/事业单位");
        dicCompNatu.put("教育", "教育");
        dicCompNatu.put("医疗卫生", "医疗卫生");
        dicCompNatu.put("金融/保险/证券", "金融/保险/证券");
        dicCompNatu.put("高新技术", "高新技术");
        dicCompNatu.put("制造业", "制造业");
        dicCompNatu.put("建筑业", "建筑业");
        dicCompNatu.put("商业/贸易", "商业/贸易");
        dicCompNatu.put("传媒/体育/娱乐", "传媒/体育/娱乐");
        dicCompNatu.put("酒店/旅游/餐饮", "酒店/旅游/餐饮");
        dicCompNatu.put("服务业", "服务业");
        dicCompNatu.put("其他", "其他");

        // 职务级别字典
        this.dicJobTitle = new LinkedHashMap<String, String>();
        dicJobTitle.put("负责人/法定代表人", "负责人/法定代表人");
        dicJobTitle.put("高级管理人员", "高级管理人员");
        dicJobTitle.put("一般管理人员", "一般管理人员");
        dicJobTitle.put("一般正式员工", "一般正式员工");
        dicJobTitle.put("销售/中介/业务代表", "销售/中介/业务代表");
        dicJobTitle.put("营业员/服务员", "营业员/服务员");

        // 联系人关系字典
        this.dicContRel = new LinkedHashMap<String, String>();
        dicContRel.put("父母", "父母");
        dicContRel.put("配偶", "配偶");
        dicContRel.put("子女", "子女");
        dicContRel.put("兄弟姐妹", "兄弟姐妹");
        dicContRel.put("同事", "同事");
        dicContRel.put("朋友", "朋友");
        dicContRel.put("其他", "其他");

        // 附件类型字典
        this.dicAtchType = new LinkedHashMap<String, String>();
        dicAtchType.put("0", "分期申请书");
        dicAtchType.put("1", "身份证");
        dicAtchType.put("2", "储蓄卡");
        dicAtchType.put("3", "手持身份证照");
        dicAtchType.put("4", "其他辅助资料");

        // 客户端状态字典
        this.dicClientStat = new LinkedHashMap<String, String>();
        dicClientStat.put("0001", "资料已上传");
        dicClientStat.put("0002", "资料已上传");
        dicClientStat.put("0003", "审核不通过");
        dicClientStat.put("0004", "资料需修改");
        dicClientStat.put("0005", "资料已上传");
        dicClientStat.put("0006", "审核不通过");
        dicClientStat.put("0007", "资料已上传");
        dicClientStat.put("0008", "资料已上传");
        dicClientStat.put("0009", "资料已上传");
    }

    public void refreshDicInstName() {
        this.dicInstName = dicDAO.getDicInstName();
    }

    @SuppressWarnings("unchecked")
    private void fromCityDataJson(Map<String, String> dic, Object json) {
        if (json != null) {
            if (json instanceof List) {
                for (Object o : (List<Object>) json) {
                    fromCityDataJson(dic, o);
                }
            } else if (json instanceof Map) {
                Map<String, Object> map = (Map<String, Object>) json;
                dic.put((String) map.get("v"), (String) map.get("n"));
                fromCityDataJson(dic, map.get("s"));
            }
        }
    }

    public Map<String, CfgMenu> getDicMenu() {
        return dicMenu;
    }

    public Map<String, String> getDicRole() {
        return dicRole;
    }

    public Map<String, List<String>> getDicRoleAuth() {
        return dicRoleAuth;
    }

    public Map<String, List<CfgFlow>> getDicFlowCfg() {
        return dicFlowCfg;
    }

    public Map<String, String> getDicActn() {
        return dicActn;
    }

    public Map<String, String> getDicSeg() {
        return dicSeg;
    }

    public Map<String, String> getDicSegStat() {
        return dicSegStat;
    }

    public Map<String, List<String>> getDicAudtStat() {
        return dicAudtStat;
    }

    public Map<String, String> getDicInstName() {
        return dicInstName;
    }

    public Map<String, String> getDicOpUser() {
        return dicOpUser;
    }

    public Map<String, String> getDicInstStat() {
        return dicInstStat;
    }

    public Map<String, String> getDicRepMethod() {
        return dicRepMethod;
    }

    public Map<String, String> getDicSex() {
        return dicSex;
    }

    public Map<String, String> getDicRegion() {
        return dicRegion;
    }

    public Map<String, String> getDicBank() {
        return dicBank;
    }

    public Map<String, String> getDicEdu() {
        return dicEdu;
    }

    public Map<String, String> getDicMari() {
        return dicMari;
    }

    public Map<String, String> getDicHomeStat() {
        return dicHomeStat;
    }

    public Map<String, String> getDicEmpType() {
        return dicEmpType;
    }

    public Map<String, String> getDicJobSpec() {
        return dicJobSpec;
    }

    public Map<String, String> getDicCompSize() {
        return dicCompSize;
    }

    public Map<String, String> getDicCompNatu() {
        return dicCompNatu;
    }

    public Map<String, String> getDicJobTitle() {
        return dicJobTitle;
    }

    public Map<String, String> getDicContRel() {
        return dicContRel;
    }

    public Map<String, String> getDicAtchType() {
        return dicAtchType;
    }

    public Map<String, String> getDicClientStat() {
        return dicClientStat;
    }
}