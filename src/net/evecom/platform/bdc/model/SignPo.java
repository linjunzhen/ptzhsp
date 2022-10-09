/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.bdc.model;

import net.evecom.platform.bdc.enumbdc.SignIdentity;

/**
 * 描述   不动产全流程签章位置信息实体类
 *
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月6日 上午7:19:11
 */
public class SignPo {
    /**
     * 证书id
     */
    private String  certId;
    /**
     * 需要第几页到第几页签章
     */
    private String posPage="1-99";
    /**
     *签章的时候是否添加签署时间
     */
    private boolean addSignTime=false;
    /**
     * 骑缝章位置，1-无，2-
     * 上方，3-底部
     */
    private int edgePosition=2;
    /**
     * 关键字（以关键字左下方作为坐标原点，进行posX（X轴），posY（Y轴）偏移
     */
    private String key;
    /**
     * x轴偏移量
     */
    private int posX=123;
    /**
     * y轴偏移量
     */
    private int posY=10;
    /**
     * 不传代表全部
     * 指定签署第几个关键 字,-1为最后一个,超过关键字总数选中最后一个；当关键字签传了
     * posPage和keyIndex， 表示在指定页里指定第几个关键字进行签署
     */
    private int keyIndex=1;
    
    /**
     * 印章类型：0:手绘印章1,模板印章，多选用','隔开
     */
    private String sealType;

    /**
     * @author Allin Lin
     * @created 2020年12月8日 下午4:59:57
     * @return type
     */
    public String getSealType() {
        return sealType;
    }

    /**
     * @author Allin Lin
     * @created 2020年12月8日 下午4:59:57
     * @param sealType
     */
    public void setSealType(String sealType) {
        this.sealType = sealType;
    }

    public int getKeyIndex() {
        return keyIndex;
    }

    public void setKeyIndex(int keyIndex) {
        this.keyIndex = keyIndex;
    }

    public boolean isAddSignTime() {
        return addSignTime;
    }

    public void setAddSignTime(boolean addSignTime) {
        this.addSignTime = addSignTime;
    }

    /**
     *
     */
    private boolean qrcodeSign=false;
    /**
     *签署位置类型，PERSON-个人,ORGANIZE-企业,LEGAL-法人,NOLIMIT-不限制;多个用','隔开,默认NOLIMIT
     */
    private String signIdentity=SignIdentity.NOLIMIT.getVal();
    /**
     * 签署类型；0-不限(需用户手动拖拽印章完成签署，自动签署不支持)、1-单页签、2-多页签、 3-骑缝章、4关键字签
     */
    private int signType=4;
    /**
     * 签名域宽度，为空则取印章大小
     */
    private int width=90;

    public String getCertId() {
        return certId;
    }

    public void setCertId(String certId) {
        this.certId = certId;
    }

    public String getPosPage() {
        return posPage;
    }

    public void setPosPage(String posPage) {
        this.posPage = posPage;
    }

    public int getEdgePosition() {
        return edgePosition;
    }

    public void setEdgePosition(int edgePosition) {
        this.edgePosition = edgePosition;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public boolean isQrcodeSign() {
        return qrcodeSign;
    }

    public void setQrcodeSign(boolean qrcodeSign) {
        this.qrcodeSign = qrcodeSign;
    }

    public String getSignIdentity() {
        return signIdentity;
    }

    public void setSignIdentity(String signIdentity) {
        this.signIdentity = signIdentity;
    }

    public int getSignType() {
        return signType;
    }

    public void setSignType(int signType) {
        this.signType = signType;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
