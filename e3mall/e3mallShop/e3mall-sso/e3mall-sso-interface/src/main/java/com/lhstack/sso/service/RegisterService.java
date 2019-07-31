package com.lhstack.sso.service;

import com.lhstack.common.pojo.TaotaoResult;
import com.lhstack.pojo.TbUser;

public interface RegisterService {

    TaotaoResult checkData(String param, int type);
    TaotaoResult register(TbUser user);
}
