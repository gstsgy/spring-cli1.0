package com.gstsgy.test.enable;


import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;

import com.gstsgy.sequence.SeqUtils;
import com.gstsgy.sequence.bean.Sequence;
import com.gstsgy.sequence.mapper.SeqMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TestEnable implements CommandLineRunner {



    @Override
    public void run(String... args) throws Exception {


        Sequence.SequenceBuilder builder= Sequence.builder();
        Sequence sequence = Sequence.builder().startnum(1000L).gKey("dept").subkey("code").step(1).dayresetting(false).build();
//        System.out.println(SeqUtils.getSeq(sequence));
//        System.out.println(SeqUtils.getSeq(sequence));
//        System.out.println(SeqUtils.getSeq(sequence));
//        System.out.println(SeqUtils.getSeq(sequence));
//        System.out.println(SeqUtils.getSeq(sequence));
//        System.out.println(SeqUtils.getSeq(sequence));
//        System.out.println(SeqUtils.getSeq(sequence));

    }
}
