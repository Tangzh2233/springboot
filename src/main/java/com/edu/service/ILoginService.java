package com.edu.service;

import com.edu.dao.domain.Dlog;
import com.edu.dao.domain.User;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/9/14.
 */
public interface ILoginService {
    User login(String name);
    <T> T select(T t);
    int addDlog(Dlog dlog);
}
