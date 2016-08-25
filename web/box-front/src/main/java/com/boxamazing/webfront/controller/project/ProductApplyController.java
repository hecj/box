package com.boxamazing.webfront.controller.project;

import com.boxamazing.service.product.model.Product;
import com.boxamazing.service.project.model.Project;
import com.boxamazing.service.user.model.User;
import com.boxamazing.webfront.util.UserUtil;
import com.jfinal.core.JfinalxController;

import java.util.List;

/**
 * 回报申请表
 * Created by jhl on 15/8/27.
 */
public class ProductApplyController extends JfinalxController {

    /**
     * 创建回报设置
     * /productapply/createproductt/pid
     */
    public void createproduct() {

        //权限检测
        User user = UserUtil.getUser(getSession());
        if (null == user) {
            renderError(403);
            return;
        }
        setAttr("u", user);

        Project project = Project.dao.findById(getParaToInt(0));
        if (!user.get("username").equals(project.get("uid"))) {
            renderError(403);
            return;
        }
        setAttr("project", project);

        List<Product> list = Product.dao.findByProjectId(getParaToLong(0));
        setAttr("list", list);
        renderFreeMarker("createproduct.ftl");
    }

    /**
     * 保存回报设置
     * /productapply/savecreated
     */
    public void saveproduct() {
        //权限检测
        User user = UserUtil.getUser(getSession());
        if (null == user) {
            renderError(403);
            return;
        }
        setAttr("u", user);

        //权限检测
        Integer pid = getParaToInt("pid");
        Project project = Project.dao.findById(pid);
        if (!user.get("username").equals(project.get("uid"))) {
            renderError(403);
            return;
        }
        setAttr("p", project);

        //创建支持方式
        Integer type = getParaToInt("type");
        Long fund = getParaToLong("fund");
        Long totalNum = getParaToLong("totalnum");
        String desc = getPara("desc");
        Integer sendTime = getParaToInt("sendtime");
        Integer postage = getParaToInt("postage");
        String picurl = getPara("pic0");
        boolean result = false;
        result = new Product()
                .set("pid", pid)
                .set("type", type)
                .set("fund", fund)
                .set("totalnum", totalNum)
                .set("desc", desc)
                .set("sendtime", sendTime)
                .set("postage", postage)
                .set("pic0", picurl)
                .save();
        setAttr("result", result);

        //跳转
        redirect("/productapply/createproduct/" + pid);
    }

    /**
     * 编辑表
     */
    public void editproduct() {

        //权限检测
        User user = UserUtil.getUser(getSession());
        if (null == user) {
            renderError(403);
            return;
        }
        setAttr("u", user);

        Long pid = getParaToLong(0);
        Project project = Project.dao.findById(pid);
        if (!user.get("username").equals(project.get("uid"))) {
            renderError(403);
            return;
        }
        setAttr("project", project);

        Product product = null;
        Integer id = getParaToInt(1, -1);
        if (id <= 0) {
            renderError(403);
            return;
        }
        product = Product.dao.findById(id);
        if (product.getLong("pid") != project.getLong("id")) {
            renderError(403);
            return;
        }
        setAttr("product", product);

        // 配值
        List<Product> list = Product.dao.findByProjectId(pid);
        setAttr("list", list);

        //跳转View
        renderFreeMarker("editproduct.ftl");
    }

    public void updateproduct() {
        //权限检测
        User user = UserUtil.getUser(getSession());
        if (null == user) {
            renderError(403);
            return;
        }
        setAttr("u", user);

        //权限检测
        Integer pid = getParaToInt("pid");
        Project project = Project.dao.findById(pid);
        if (!user.get("username").equals(project.get("uid"))) {
            renderError(403);
            return;
        }
        setAttr("p", project);

        //权限检测
        Integer id = getParaToInt("id");
        if (id <= 0) {
            renderError(403);
            return;
        }
        Product product = new Product().findById(id);
        if (null == product ||
                product.getLong("pid") != project.getLong("id")) {
            renderError(403);
            return;
        }

        //创建支持方式
        Integer type = getParaToInt("type");
        Long fund = getParaToLong("fund");
        Long totalNum = getParaToLong("totalnum");
        String desc = getPara("desc");
        Integer sendTime = getParaToInt("sendtime");
        Integer postage = getParaToInt("postage");
        String pic0 = getPara("pic0");
        boolean result = product.set("type", type)
                .set("fund", fund)
                .set("totalnum", totalNum)
                .set("desc", desc)
                .set("sendtime", sendTime)
                .set("postage", postage)
                .set("pic0", pic0)
                .update();
        setAttr("result", result);

        //跳转
        redirect("/productapply/createproduct/" + pid);

    }

    /**
     * 删除
     */
    public void delete() {

        // 权限检测
        User user = UserUtil.getUser(getSession());
        if (null == user) {
            renderError(403);
            return;
        }
        setAttr("u", user);

        // 权限检测
        Integer pid = getParaToInt(0);
        Project project = Project.dao.findById(pid);
        if (!user.get("username").equals(project.get("uid"))) {
            renderError(403);
            return;
        }
        setAttr("p", project);

        //权限检测
        Integer id = getParaToInt(1, -1);
        Product product = null;
        Boolean result = false;
        if (id <= 0) {
            renderError(403);
            return;
        }

        product = Product.dao.findById(id);
        if (product.getLong("pid") != project.getLong("id")) {
            renderError(403);
            return;
        }

        result = Product.dao.deleteById(id);
        setAttr("result", result);

        // 跳转
        redirect("/productapply/createproduct/" + pid);
    }
}

