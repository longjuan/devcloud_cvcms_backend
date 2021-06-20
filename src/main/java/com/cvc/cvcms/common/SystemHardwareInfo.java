package com.cvc.cvcms.common;

import cn.hutool.core.util.NumberUtil;
import lombok.Data;
import lombok.ToString;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.Util;

/**
 * @author ZZJ
 * @date 2021/6/4 22:57
 * @desc
 */
@Data
@ToString
public class SystemHardwareInfo {
    private static final int OSHI_WAIT_SECOND = 500;
    /**
     * cpu使用率
     */
    private double cpuUsage;
    /**
     * cpu核心数
     */
    private int cpuCoreNum;
    /**
     * 内存使用率
     */
    private double memUsage;
    /**
     * 总内存容量
     */
    private String memTotal;
    /**
     * 内存已使用量
     */
    private String memUsed;
    /**
     * 硬盘容量使用率
     */
    private double distUsage;
    /**
     * 总内存容量
     */
    private String distTotal;
    /**
     * 硬盘已使用容量
     */
    private String distUsed;
    /**
     * 负载率
     */
    private double loadRate;
    /**
     * 负载状态
     */
    private String loadStatus;


    public SystemHardwareInfo() {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        setCpuInfo(hal.getProcessor());
        setMemInfo(hal.getMemory());
        setSysFiles(si.getOperatingSystem());
        this.loadRate = Math.sqrt(cpuUsage * memUsage);
        if (loadRate < 0.3) {
            loadStatus = "流畅运行";
        } else if (loadRate < 0.7) {
            loadStatus = "运行良好";
        } else {
            loadStatus = "运行卡顿";
        }
    }

    /**
     * 设置CPU信息
     */
    private void setCpuInfo(CentralProcessor processor) {
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        Util.sleep(OSHI_WAIT_SECOND);
        long[] ticks = processor.getSystemCpuLoadTicks();
        long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        long cSys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;

        this.cpuCoreNum = processor.getLogicalProcessorCount();
        this.cpuUsage = (totalCpu - idle) / (totalCpu * 1.0);
    }

    /**
     * 设置内存信息
     */
    private void setMemInfo(GlobalMemory memory) {
        this.memTotal = convertFileSize(memory.getTotal());
        this.memUsed = convertFileSize(memory.getTotal() - memory.getAvailable());
        this.memUsage = (memory.getTotal() - memory.getAvailable()) / (memory.getTotal() * 1.0);
    }


    /**
     * 设置磁盘信息
     */
    private void setSysFiles(OperatingSystem os) {
        FileSystem fileSystem = os.getFileSystem();
        OSFileStore[] fsArray = fileSystem.getFileStores();
        long free = 0;
        long total = 0;
        for (OSFileStore fs : fsArray) {
            free += fs.getUsableSpace();
            total += fs.getTotalSpace();
        }
        this.distUsage = total == 0 ? 0.0 : (total - free) / (total * 1.0);
        this.distTotal = convertFileSize(total);
        this.distUsed = convertFileSize(total - free);
    }

    /**
     * 字节转换
     *
     * @param size 字节大小
     * @return 转换后值
     */
    public String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else {
            return String.format("%d B", size);
        }
    }
}
