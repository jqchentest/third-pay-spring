package com.jon.thirdpay.model;

import com.jon.thirdpay.enums.ThirdPayTypeEnum;
import lombok.Data;

/**
 * 下载对账文件请求
 */
@Data
public class ThirdPayDownloadBillRequest {

    private ThirdPayTypeEnum payTypeEnum;
    //对账日期
    private String billDate;

}
