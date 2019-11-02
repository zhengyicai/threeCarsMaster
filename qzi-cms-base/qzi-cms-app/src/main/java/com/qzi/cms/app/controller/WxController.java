package com.qzi.cms.app.controller;

/**
 * Created by Administrator on 2019/3/7.
 */


import com.alibaba.fastjson.JSONObject;
import com.qzi.cms.common.enums.RespCodeEnum;
import com.qzi.cms.common.po.ResidentAddressPo;
import com.qzi.cms.common.po.UseResidentPo;
import com.qzi.cms.common.resp.RespBody;
import com.qzi.cms.common.util.HttpClientManager;


import com.qzi.cms.common.util.LogUtils;
import com.qzi.cms.common.util.ToolUtils;
import com.qzi.cms.server.mapper.UseCommunityMapper;
import com.qzi.cms.server.mapper.UseResidentAddressMapper;
import com.qzi.cms.server.mapper.UseResidentMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 注册控制器
 * @author qsy
 * @version v1.0
 * @date 2017年7月31日
 */
@RestController
@RequestMapping("/wx")
public class WxController {

    @Resource
    private UseCommunityMapper useCommunityMapper;


    @Resource
    private UseResidentMapper useResidentMapper;

    @Resource
    private UseResidentAddressMapper useResidentAddressMapper;

    private String appid = "wx64a68f7e23549c98";
    private String appsecret = "2767433c1e4d0c888d30c03329ee711e";
    private String url = "http://testthree.umo119.com"; //回调接口
    private String pageUrl = "http://testhome.umo119.com/threeCarsOne/first.html";  //返回页面
    private String pageUrl1 = "http://testhome.umo119.com/threeCarsOne/one.html";  //返回页面
    private String authPageUrl = "http://testhome.umo119.com/threeCarsOne/orderTabs1.html";  //访客授权页面



    private String pageUrl2 = "http://testhome.umo119.com/threeCarsOne/tabs.html";  //设置返回页面
    private String pageUrl22 = "http://testhome.umo119.com/threeCarsOne/setting.html";  //设置返回页面


    @RequestMapping("loginInit.do")
    public void  loginInit(HttpServletRequest request, HttpServletResponse response)  throws  Exception {
        //回调地址,要跟下面的地址能调通(getWechatGZAccessToken.do)
        String backUrl=url+"/app/wx/getWechatGZAccessToken.do";



        /**
         *这儿一定要注意！！首尾不能有多的空格（因为直接复制往往会多出空格），其次就是参数的顺序不能变动
         **/
        //AuthUtil.APPID微信公众号的appId   scope是否需要授权用户信息   snsapi_base  只获取openId ，snsapi_userinfo：获取用户其他信息
        String url ="https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +appid+
                "&redirect_uri=" + URLEncoder.encode(backUrl,"UTF-8")+
                "&response_type=code" +
                "&scope=snsapi_userinfo" +
                "&state=STATE#wechat_redirect";
            response.sendRedirect(url);
       // return  url;
    }



    @RequestMapping("getWechatGZAccessToken.do")
    public void getWechatGZAccessToken(HttpServletRequest request,HttpServletResponse response) throws Exception{
        //微信公众号的APPID和APPSECRET
        String code=request.getParameter("code");

        String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid+
                "&secret=" +appsecret+
                "&code=" +code+
                "&grant_type=authorization_code";
        String result = HttpClientManager.getUrlData(url);

        Map<String,Object> data = JSONObject.parseObject(result);
        String openid=data.get("openid").toString();
        String token=data.get("access_token").toString();


        //获取信息
        String infoUrl="https://api.weixin.qq.com/sns/userinfo?access_token=" +token+
                "&openid=" +openid+
                "&lang=zh_CN";
        String infoResult = HttpClientManager.getUrlData(infoUrl);


        Map<String,Object> data1 = JSONObject.parseObject(infoResult);
        String nickName = data1.get("nickname").toString();
        String headimgurl = data1.get("headimgurl").toString();


        //判断wxid是否存在
       UseResidentPo po =  useResidentMapper.findWxId(openid);
       if(po !=null){

       }else{

           UseResidentPo useResidentPo = new UseResidentPo();
           useResidentPo.setId(ToolUtils.getUUID());
           useResidentPo.setName(nickName);
           useResidentPo.setImgUrl(headimgurl);
           useResidentPo.setCreateTime(new Date());
           useResidentPo.setState("10");
           useResidentPo.setWxId(openid);
           useResidentPo.setMobile("");
           useResidentPo.setSalt("");
           useResidentPo.setPassword("");
           useResidentPo.setClientNumber("");
           useResidentPo.setClientPwd("");
           useResidentPo.setLoginToken("");
           useResidentPo.setOpenPwd("");
           useResidentPo.setLastTime(new Date());
           useResidentPo.setRemark("");
           useResidentPo.setIdentityId("");
           useResidentPo.setIdentityNo("");
           useResidentPo.setResidentType("10");

           useResidentMapper.insert(useResidentPo);

       }



        List<ResidentAddressPo> lists =  useResidentAddressMapper.findAllWxId(openid);

       System.out.println(""+pageUrl+"?openId="+openid+"&nickname="+nickName+"&headimgurl="+headimgurl);
       if(lists.size()>0){
           response.sendRedirect(pageUrl+"?openId="+openid+"&nickname="+nickName+"&headimgurl="+headimgurl);
       }else{
           response.sendRedirect(pageUrl1+"?openId="+openid+"&nickname="+nickName+"&headimgurl="+headimgurl);
       }





        //回调显示页面
        //response.sendRedirect(pageUrl+"?openId="+openid+"&nickname="+nickName+"&headimgurl="+headimgurl);

        //return  infoResult;

    }



    @RequestMapping("authUserInit.do")
    public void  authUserInit(HttpServletRequest request, HttpServletResponse response)  throws  Exception {
        //回调地址,要跟下面的地址能调通(getWechatGZAccessToken.do)
        String backUrl=url+"/app/wx/getAuthWechatGZAccessToken.do";



        /**
         *这儿一定要注意！！首尾不能有多的空格（因为直接复制往往会多出空格），其次就是参数的顺序不能变动
         **/
        //AuthUtil.APPID微信公众号的appId   scope是否需要授权用户信息   snsapi_base  只获取openId ，snsapi_userinfo：获取用户其他信息
        String url ="https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +appid+
                "&redirect_uri=" + URLEncoder.encode(backUrl,"UTF-8")+
                "&response_type=code" +
                "&scope=snsapi_userinfo" +
                "&state=STATE#wechat_redirect";
        response.sendRedirect(url);
        // return  url;
    }



    @RequestMapping("getAuthWechatGZAccessToken.do")
    public void getAuthWechatGZAccessToken(HttpServletRequest request,HttpServletResponse response) throws Exception{
        //微信公众号的APPID和APPSECRET
        String code=request.getParameter("code");

        String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid+
                "&secret=" +appsecret+
                "&code=" +code+
                "&grant_type=authorization_code";
        String result = HttpClientManager.getUrlData(url);

        Map<String,Object> data = JSONObject.parseObject(result);
        String openid=data.get("openid").toString();
        String token=data.get("access_token").toString();

        //获取信息
        String infoUrl="https://api.weixin.qq.com/sns/userinfo?access_token=" +token+
                "&openid=" +openid+
                "&lang=zh_CN";
        String infoResult = HttpClientManager.getUrlData(infoUrl);
        

        Map<String,Object> data1 = JSONObject.parseObject(infoResult);
       String nickName = data1.get("nickname").toString();
       String headimgurl = data1.get("headimgurl").toString();

        //回调显示页面
        //response.sendRedirect(authPageUrl+"?openId="+openid+"&nickname="+nickName+"&headimgurl="+headimgurl);
        //判断wxid是否存在
               UseResidentPo po =  useResidentMapper.findWxId(openid);
               if(po !=null){

               }else{

                   UseResidentPo useResidentPo = new UseResidentPo();
                   useResidentPo.setId(ToolUtils.getUUID());
                   useResidentPo.setName(nickName);
                   useResidentPo.setImgUrl(headimgurl);
                   useResidentPo.setCreateTime(new Date());
                   useResidentPo.setState("10");
                   useResidentPo.setWxId(openid);
                   useResidentPo.setMobile("");
                   useResidentPo.setSalt("");
                   useResidentPo.setPassword("");
                   useResidentPo.setClientNumber("");
                   useResidentPo.setClientPwd("");
                   useResidentPo.setLoginToken("");
                   useResidentPo.setOpenPwd("");
                   useResidentPo.setLastTime(new Date());
                   useResidentPo.setRemark("");
                   useResidentPo.setIdentityId("");
                   useResidentPo.setIdentityNo("");
                   useResidentPo.setResidentType("10");

                   useResidentMapper.insert(useResidentPo);

               }



                List<ResidentAddressPo> lists =  useResidentAddressMapper.findAllWxId(openid);


               if(lists.size()>0){
                   response.sendRedirect(authPageUrl+"?openId="+openid+"&nickname="+nickName+"&headimgurl="+headimgurl);
               }else{
                   response.sendRedirect(authPageUrl+"?openId="+openid+"&nickname="+nickName+"&headimgurl="+headimgurl);
               }


        //return  infoResult;

    }

    @GetMapping("/wxFindAll")
    public RespBody wxFindAll(){
        RespBody respBody = new RespBody();
        try {

            respBody.add(RespCodeEnum.SUCCESS.getCode(), "参数查找成功",useCommunityMapper.wxFindAll());

        } catch (Exception ex) {
            respBody.add(RespCodeEnum.ERROR.getCode(), "查找个人消息记录数失败");
            LogUtils.error("查找个人消息记录数失败！",ex);
        }
        return respBody;
    }





    @RequestMapping("userInit.do")
    public void  userInit(HttpServletRequest request, HttpServletResponse response)  throws  Exception {
        //回调地址,要跟下面的地址能调通(getWechatGZAccessToken.do)
        String backUrl=url+"/app/wx/getWechatGZAccessTokenUser.do";



        /**
         *这儿一定要注意！！首尾不能有多的空格（因为直接复制往往会多出空格），其次就是参数的顺序不能变动
         **/
        //AuthUtil.APPID微信公众号的appId   scope是否需要授权用户信息   snsapi_base  只获取openId ，snsapi_userinfo：获取用户其他信息
        String url ="https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +appid+
                "&redirect_uri=" + URLEncoder.encode(backUrl,"UTF-8")+
                "&response_type=code" +
                "&scope=snsapi_userinfo" +
                "&state=STATE#wechat_redirect";
        response.sendRedirect(url);
        // return  url;
    }



    @RequestMapping("getWechatGZAccessTokenUser.do")
    public void getWechatGZAccessTokenUser(HttpServletRequest request,HttpServletResponse response) throws Exception{
        //微信公众号的APPID和APPSECRET
        String code=request.getParameter("code");

        String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid+
                "&secret=" +appsecret+
                "&code=" +code+
                "&grant_type=authorization_code";
        String result = HttpClientManager.getUrlData(url);

        Map<String,Object> data = JSONObject.parseObject(result);
        String openid=data.get("openid").toString();
        String token=data.get("access_token").toString();


        //获取信息
        String infoUrl="https://api.weixin.qq.com/sns/userinfo?access_token=" +token+
                "&openid=" +openid+
                "&lang=zh_CN";
        String infoResult = HttpClientManager.getUrlData(infoUrl);


        Map<String,Object> data1 = JSONObject.parseObject(infoResult);
        String nickName = data1.get("nickname").toString();
        String headimgurl = data1.get("headimgurl").toString();


        //判断wxid是否存在
        UseResidentPo po =  useResidentMapper.findWxId(openid);
        if(po !=null){

        }else{

            UseResidentPo useResidentPo = new UseResidentPo();
            useResidentPo.setId(ToolUtils.getUUID());
            useResidentPo.setName(nickName);
            useResidentPo.setImgUrl(headimgurl);
            useResidentPo.setCreateTime(new Date());
            useResidentPo.setState("10");
            useResidentPo.setWxId(openid);
            useResidentPo.setMobile("");
            useResidentPo.setSalt("");
            useResidentPo.setPassword("");
            useResidentPo.setClientNumber("");
            useResidentPo.setClientPwd("");
            useResidentPo.setLoginToken("");
            useResidentPo.setOpenPwd("");
            useResidentPo.setLastTime(new Date());
            useResidentPo.setRemark("");
            useResidentPo.setIdentityId("");
            useResidentPo.setIdentityNo("");
            useResidentPo.setResidentType("10");

            useResidentMapper.insert(useResidentPo);

        }



        //List<ResidentAddressPo> lists =  useResidentAddressMapper.findAllWxId(openid);
          UseResidentPo useResidentPo =    useResidentMapper.findWxId(openid);

        if(useResidentPo!=null){
            if("20".equals(useResidentPo.getResidentType())){
                response.sendRedirect(pageUrl2+"?openId="+openid+"&nickname="+nickName+"&headimgurl="+headimgurl);
            }else{
                response.sendRedirect(pageUrl22+"?openId="+openid+"&nickname="+nickName+"&headimgurl="+headimgurl);
            }
            //response.sendRedirect(pageUrl2+"?openId="+openid+"&nickname="+nickName+"&headimgurl="+headimgurl);
        }else{
            response.sendRedirect(pageUrl22+"?openId="+openid+"&nickname="+nickName+"&headimgurl="+headimgurl);
        }







    }


    @RequestMapping("settingInit.do")
    public void  settingInit(HttpServletRequest request, HttpServletResponse response)  throws  Exception {
        //回调地址,要跟下面的地址能调通(getWechatGZAccessToken.do)
        String backUrl=url+"/app/wx/getWechatGZAccessTokenSetting.do";



        /**
         *这儿一定要注意！！首尾不能有多的空格（因为直接复制往往会多出空格），其次就是参数的顺序不能变动
         **/
        //AuthUtil.APPID微信公众号的appId   scope是否需要授权用户信息   snsapi_base  只获取openId ，snsapi_userinfo：获取用户其他信息
        String url ="https://open.weixin.qq.com/connect/oauth2/authorize?appid=" +appid+
                "&redirect_uri=" + URLEncoder.encode(backUrl,"UTF-8")+
                "&response_type=code" +
                "&scope=snsapi_userinfo" +
                "&state=STATE#wechat_redirect";
        response.sendRedirect(url);
        // return  url;
    }



    @RequestMapping("getWechatGZAccessTokenSetting.do")
    public void getWechatGZAccessTokenSetting(HttpServletRequest request,HttpServletResponse response) throws Exception{
        //微信公众号的APPID和APPSECRET
        String code=request.getParameter("code");

        String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid+
                "&secret=" +appsecret+
                "&code=" +code+
                "&grant_type=authorization_code";
        String result = HttpClientManager.getUrlData(url);

        Map<String,Object> data = JSONObject.parseObject(result);
        String openid=data.get("openid").toString();
        String token=data.get("access_token").toString();


        //获取信息
        String infoUrl="https://api.weixin.qq.com/sns/userinfo?access_token=" +token+
                "&openid=" +openid+
                "&lang=zh_CN";
        String infoResult = HttpClientManager.getUrlData(infoUrl);


        Map<String,Object> data1 = JSONObject.parseObject(infoResult);
        String nickName = data1.get("nickname").toString();
        String headimgurl = data1.get("headimgurl").toString();



        response.sendRedirect(pageUrl22+"?openId="+openid+"&nickname="+nickName+"&headimgurl="+headimgurl);








    }

}
