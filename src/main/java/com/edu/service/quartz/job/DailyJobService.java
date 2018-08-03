package com.edu.service.quartz.job;

import com.edu.common.EWarnChannel;
import com.edu.dao.mapper.cat.AlertMapper;
import com.edu.dao.mapper.cat.ProjectMapper;
import com.edu.service.quartz.job.Thread.ReqAlarmThread;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Tangzhihao
 * @date 2018/3/19
 */
@Service
public class DailyJobService {

    private final static Logger logger = LoggerFactory.getLogger(DailyJobService.class);
    private static ExecutorService executorService;
    private static List<String> channels = new ArrayList<>();
    @Resource
    private AlertMapper alertMapper;
    @Resource
    private ProjectMapper projectMapper;

    static {
        channels.add("Email");
        channels.add("WeiXin");
        channels.add("SMS");
    }

    public void DailJobExecute(){

        String strDate = DateFormatUtils.format(new Date(),"yyyy-MM-dd");
        strDate = "2018-04-03";
        List<String> domains = alertMapper.selectDomainByAlertTime(strDate);
        logger.info("strDate: "+strDate);
        //cat自身报警没有日报
        if(domains.contains("cat")){
            domains.remove("cat");
        }
        if(null==domains||domains.size()==0){
            return;
        }
        try {
            sendAction(domains, strDate);
        }catch (Exception e){
            logger.info("请求alarmcore异常");
        }
    }

    private void sendAction(List<String> domains,String strDate){
        logger.info("进入sendAction方法，参数: "+domains +"+"+ strDate);
        if(executorService==null){
            executorService = Executors.newFixedThreadPool(10);
        }
        for (String domain:domains){
            executorService.execute(new ReqAlarmThread(domain,strDate, EWarnChannel.E_MAIL.getCode(),alertMapper,projectMapper));
        }
        /*for (String domain:domains){
            executorService.execute(new ReqAlarmThread(domain,strDate,EWarnChannel.E_SMS.getCode(),alertMapper,projectMapper));
        }*/
        /*for (String domain:domains){
            executorService.execute(new ReqAlarmThread(domain,strDate, EWarnChannel.E_WEIXIN.getCode(),alertMapper,projectMapper));
        }*/
    }

}
