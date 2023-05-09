package com.lear.router;

/**
 * 路由信息实体封装类
 * @author 天狗
 */
public class RouterInfo {

    private String className;
    private String methodName;

    public RouterInfo(String cls, String mtd) {
        this.className = cls;
        this.methodName = mtd;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public String toString() {
        return "RouterInfo{" +
                "className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                '}';
    }
}
