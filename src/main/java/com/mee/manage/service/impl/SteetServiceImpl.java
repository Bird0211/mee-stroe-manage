package com.mee.manage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mee.manage.mapper.ISteetMapper;
import com.mee.manage.po.Steet;
import com.mee.manage.service.ISteetService;

import org.springframework.stereotype.Service;

@Service
public class SteetServiceImpl extends ServiceImpl<ISteetMapper, Steet> implements ISteetService{

}