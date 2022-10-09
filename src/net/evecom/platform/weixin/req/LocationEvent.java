/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.weixin.req;

/**
 * 描述  上报地理位置事件
 * @author Sundy Sun
 * @version v2.0
 *
 */
public class LocationEvent extends BaseEvent{
    /**地理位置纬度 **/
    private String Latitude;
    /**地理位置经度**/
    private String Longitude;
    /**地理位置精度**/
    private String Precision;
    /**
     * @return the latitude
     */
    public String getLatitude() {
        return Latitude;
    }
    /**
     * @param latitude the latitude to set
     */
    public void setLatitude(String latitude) {
        Latitude = latitude;
    }
    /**
     * @return the longitude
     */
    public String getLongitude() {
        return Longitude;
    }
    /**
     * @param longitude the longitude to set
     */
    public void setLongitude(String longitude) {
        Longitude = longitude;
    }
    /**
     * @return the precision
     */
    public String getPrecision() {
        return Precision;
    }
    /**
     * @param precision the precision to set
     */
    public void setPrecision(String precision) {
        Precision = precision;
    }
    
    
}
