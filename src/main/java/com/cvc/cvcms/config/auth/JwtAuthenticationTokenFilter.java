package com.cvc.cvcms.config.auth;

import com.cvc.cvcms.common.ErrorEnum;
import com.cvc.cvcms.common.JsonStandard;
import com.cvc.cvcms.common.JwtTokenUtil;
import com.cvc.cvcms.dao.UserRedisDao;
import com.cvc.cvcms.pojo.User;
import com.cvc.cvcms.service.impl.MyDataUserDetailsServiceImpl;
import com.cvc.cvcms.service.JwtAuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author ZZJ
 * @date 2021/3/14 21:55
 * @desc jwt控制访问过滤器
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private MyDataUserDetailsServiceImpl myDataUserDetailsService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRedisDao userRedisDao;
    @Autowired
    private JwtAuthService jwtAuthService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 拿token
        String token = request.getHeader(jwtTokenUtil.getHeader());
        // 判断token是否为空
        if(StringUtils.hasText(token)){
            // 判断token过期
            if(jwtTokenUtil.isTokenExpired(token)){
                JsonStandard jsonStandard = JsonStandard.error(ErrorEnum.NO_AUTH,"登录已过期");
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(new String(objectMapper.writeValueAsBytes(jsonStandard), StandardCharsets.UTF_8));
                return;
            }
            // 根据token拿用户名
            String username = jwtTokenUtil.getUsernameFromToken(token);
            // 判断用户名为空和是否认证
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                // 从redis里拿到user
                User user = jwtAuthService.loadUserByUsernameFromRedis(username);
                // 从token拿到权限
                List<String> permission = jwtTokenUtil.getPermissionFromToken(token);
                String[] permissionArr = new String[permission.size()];
                permission.toArray(permissionArr);
                // 封装UserDetails
                UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                        .password(user.getPassword())
                        .authorities(permissionArr)
                        .disabled(user.getFrozen())
                        .build();
                // 如果token和redis里的token不一样说明另外登录了
                if(!token.equals(userRedisDao.getUserToken(username))){
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json;charset=UTF-8");
                    JsonStandard jsonStandard = JsonStandard.error(ErrorEnum.NO_AUTH,"已在其他地方登录");
                    response.getWriter().write(new String(objectMapper.writeValueAsBytes(jsonStandard), StandardCharsets.UTF_8));
                    return;
                }
                // 判断token是否可以使用
                if(!jwtTokenUtil.isTokenExpired(token) && jwtTokenUtil.validateToken(token,userDetails)){
                    // 授权
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails,null,userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
