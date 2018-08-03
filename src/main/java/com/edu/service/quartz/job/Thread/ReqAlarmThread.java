package com.edu.service.quartz.job.Thread;

import com.edu.dao.domain.Alert;
import com.edu.dao.domain.CategoryDto;
import com.edu.dao.domain.Project;
import com.edu.dao.mapper.cat.AlertMapper;
import com.edu.dao.mapper.cat.ProjectMapper;
import com.edu.service.quartz.http.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author Tangzhihao
 * @date 2018/3/22
 */

public class ReqAlarmThread implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(ReqAlarmThread.class);
    private final static String emailUrl = "http://127.0.0.1:8080/alarmcore/SendMsg/sendMail";
    private final static String weixinUrl = "http://127.0.0.1:8080/alarmcore/SendMsg/sendWeixin";
    private final static String smsUrl = "http://127.0.0.1:8080/alarmcore/SendMsg/sendSms";
    private String domain;
    private String strDate;
    private String channel;
    private AlertMapper alertMapper;
    private ProjectMapper projectMapper;

    public ReqAlarmThread(String domain,String strDate,String channel,AlertMapper alertMapper,ProjectMapper projectMapper){
        this.domain = domain;
        this.strDate = strDate;
        this.channel = channel;
        this.alertMapper = alertMapper;
        this.projectMapper = projectMapper;
    }

    @Override
    public void run() {
        logger.info("进入请求alarmcore线程");
        Map<String,String> params = new HashMap();
        Map<String,Integer> dtos = new HashMap();
        String url = "";
        String toAddr = "";
        String sendType = "";
        StringBuffer contents = new StringBuffer();
        //获取本日domain项目的报警记录
        List<Alert> alerts = alertMapper.selectAlertByDomainAndDate(domain, strDate);
        //获取不同告警次数
        List<CategoryDto> categoryDtos = alertMapper.getCategory(domain, strDate);
        for (CategoryDto dto:categoryDtos){
            dtos.put(dto.getCategory(),dto.getCount());
        }

        int total = alertMapper.countAlertByDomainAndDate(domain, strDate);
        //拼接告警指标
        Map metricsDetail = buildMetricsDetail(alerts);
        //拼接告警详情
        String content = buildContentDetail(contents, dtos, total,metricsDetail);

        logger.info("content: "+content);
        Project project = projectMapper.selectEmailByDomain(domain);
        if("Email".equals(channel)){
            toAddr = project.getEmail();
            url = emailUrl;
            sendType = "MAIL";
        }
        if("WeiXin".equals(channel)){
            toAddr = project.getOwner();
            url = weixinUrl;
            sendType = "WECHAT";
        }
        if("SMS".equals(channel)){
            toAddr = project.getPhone();
            url = smsUrl;
            sendType = "SMS";
        }
        logger.info(" 发送人: "+toAddr);

        params.put("appCode","cat");
        params.put("alarmType","1");
        params.put("alarmDesc","监控日报: "+strDate);
        params.put("alarmMessage",content);
        params.put("toAddr",toAddr);
        params.put("sendType",sendType);
        logger.info("http请求参数: " +params + url);
        String response = HttpClientUtil.doPost(url, params);
        logger.info("http响应参数:  "+response);
    }

    private String buildContentDetail(StringBuffer contents,Map map,int total,Map metrics){
        contents.append("告警总计: ").append(total);
        contents.append("\r\n");
        contents.append("Transaction告警次数: ").append(map.get("Transaction"));
        contents.append("\r\n");
        contents.append("Transaction告警指标: ").append(metrics.get("Transaction"));
        contents.append("\r\n");
        contents.append("Event告警次数: ").append(map.get("Event"));
        contents.append("\r\n");
        contents.append("Event告警指标: ").append(metrics.get("Event"));
        contents.append("\r\n");
        contents.append("Metric业务告警次数: ").append(map.get("Business"));
        contents.append("\r\n");
        contents.append("Metric告警指标: ").append(metrics.get("Business"));
        contents.append("\r\n");
        return contents.toString();
    }

    private Map buildMetricsDetail(List<Alert> alerts){
        String Tetrics = "";
        String Eetrics = "";
        String Metrics = "";
        HashMap<String,String> metricMap = new HashMap();
        for (Alert alert:alerts) {
            if("Business".equals(alert.getCategory())){
                Metrics += alert.getMetric()+" ";
            }
            if("Transaction".equals(alert.getCategory())){
                Tetrics += alert.getMetric()+" ";
            }
            if("Event".equals(alert.getCategory())){
                Eetrics += alert.getMetric()+" ";
            }
        }
        metricMap.put("Transaction",Tetrics);
        metricMap.put("Event",Eetrics);
        metricMap.put("Business",Metrics);
        return metricMap;
    }
}
