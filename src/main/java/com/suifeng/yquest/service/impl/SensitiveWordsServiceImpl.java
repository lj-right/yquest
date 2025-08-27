package com.suifeng.yquest.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suifeng.yquest.dao.SensitiveWordsMapper;
import com.suifeng.yquest.entity.SensitiveWords;
import com.suifeng.yquest.service.SensitiveWordsService;
import org.springframework.stereotype.Service;

/**
 *
 * 敏感词表 服务实现类
 */
@Service
public class SensitiveWordsServiceImpl extends ServiceImpl<SensitiveWordsMapper, SensitiveWords> implements SensitiveWordsService {

}
