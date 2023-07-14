package com.webuilding.config;

import com.webuilding.common.AjaxResult;
import com.webuilding.exceprion.BusinessException;
import com.webuilding.common.CodeEnum;
import com.webuilding.exceprion.ApiHttpException;
import com.webuilding.exceprion.ParamJsonException;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.exceptions.TemplateInputException;

/**
 * Controller统一异常处理
 */
@ControllerAdvice
public class AllControllerAdvice {
    private static Logger logger = LoggerFactory.getLogger(AllControllerAdvice.class);

    /**
     * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
    }

    /**
     * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
     */
    @ModelAttribute
    public void addAttributes(Model model) {
    }

    /**
     * 捕捉BusinessException自定义抛出的异常
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public AjaxResult handleBusinessException(BusinessException e) {
        String message = e.getMessage();
        if(message.indexOf(":--:") > 0){
            String[] split = message.split(":--:");
            return AjaxResult.error(split[1], split[0],null);
        }
        return AjaxResult.error(CodeEnum.DATA_ERROR.getCode(),message,null);
    }

    /**
     * 全局异常捕捉处理
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public AjaxResult errorHandler(Exception ex) {
        logger.error("接口出现严重异常：{}", ex.getMessage());
        if(ex instanceof ShiroException){
            return AjaxResult.error(CodeEnum.IDENTIFICATION_ERROR.getCode(),CodeEnum.IDENTIFICATION_ERROR.getMsg(),"");
        }else if(ex instanceof AuthenticationException){
            return AjaxResult.error(CodeEnum.IDENTIFICATION_ERROR.getCode(),CodeEnum.IDENTIFICATION_ERROR.getMsg(),"");
        }else if(ex instanceof ApiHttpException){
            return AjaxResult.error(((ApiHttpException)ex).getCode(),ex.getMessage(),"");
        }else if(ex instanceof ParamJsonException){
            return AjaxResult.error(CodeEnum.PARAM_ERROR.getCode(), CodeEnum.PARAM_ERROR.getMsg(),"");
        }else if(ex instanceof HttpMessageNotReadableException){
            return AjaxResult.error(CodeEnum.PARAM_ERROR.getCode(), CodeEnum.PARAM_ERROR.getMsg(),"");
        }

        return AjaxResult.error(CodeEnum.ERROR.getMsg(),"");
    }

}