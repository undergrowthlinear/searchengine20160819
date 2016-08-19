package com.gpdi.searchengine.searchindexservice.conf;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * @description: TODO(这里用一句话描述这个类的作用)
 * @author zhangwu
 * @date 2016年8月8日
 * @version 1.0.0
 */
public class NRTManagerServiceConf {

	// 存放区域节点的代码
	private String[] areaList;
	private String areaCode;
	// 存放不同节点的索引和搜索目录
	private Map<String, String> areaInndexStorePath = new ConcurrentHashMap<String, String>();
	private String indexBaseStorePath;
	private boolean isRamDirectory;
	private double targetMaxScaleSec;
	private double targetMinStaleSec;

	public NRTManagerServiceConf() {
	}

	public String getIndexStorePath(String areaCode) {
		return areaInndexStorePath.get(areaCode);
	}

	public void setIndexBaseStorePath(String indexBaseStorePath) {
		this.indexBaseStorePath = indexBaseStorePath;
	}

	public boolean getIsRamDirectory() {
		return isRamDirectory;
	}

	public void setIsRamDirectory(boolean isRamDirectory) {
		this.isRamDirectory = isRamDirectory;
	}

	public double getTargetMaxScaleSec() {
		return targetMaxScaleSec;
	}

	public void setTargetMaxScaleSec(double targetMaxScaleSec) {
		this.targetMaxScaleSec = targetMaxScaleSec;
	}

	public double getTargetMinStaleSec() {
		return targetMinStaleSec;
	}

	public void setTargetMinStaleSec(double targetMinStaleSec) {
		this.targetMinStaleSec = targetMinStaleSec;
	}

	public String[] getAreaList() {
		return areaList;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
		this.areaList = areaCode.split(",");
	}

	/**
	 * 初始化各节点存储目录
	 */
	private void initAreaInndexStorePath() {
		// TODO Auto-generated method stub
		for (String areaCode : areaList) {
			areaInndexStorePath.put(areaCode,
					indexBaseStorePath + System.getProperty("file.separator")
							+ areaCode);
		}
	}

	@Override
	public String toString() {
		return "NRTManagerServiceConf [areaList=" + Arrays.toString(areaList)
				+ ", areaCode=" + areaCode + ", areaInndexStorePath="
				+ areaInndexStorePath + ", indexBaseStorePath="
				+ indexBaseStorePath + ", isRamDirectory=" + isRamDirectory
				+ ", targetMaxScaleSec=" + targetMaxScaleSec
				+ ", targetMinStaleSec=" + targetMinStaleSec + "]";
	}

}