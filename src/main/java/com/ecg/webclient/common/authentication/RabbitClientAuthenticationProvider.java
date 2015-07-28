package com.ecg.webclient.common.authentication;



public class RabbitClientAuthenticationProvider // implements AuthenticationProvider
{
    // @Autowired
    // private RabbitMqUtil rmqUtil;
    //
    // @Override
    // public Authentication authenticate(Authentication authentication) throws AuthenticationException
    // {
    // String user = authentication.getName();
    // String password = authentication.getCredentials().toString();
    //
    // LoginRequest request = LoginRequest.newBuilder().setUsername(user).setPassword(password).build();
    //
    // LoginResponse response = (LoginResponse) rmqUtil.getWebclientRabbitTemplate().convertSendAndReceive(
    // request);
    //
    // // if (response.getSuccessful())
    // // {
    // List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
    // grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
    // Authentication auth = new UsernamePasswordAuthenticationToken(user, password, grantedAuths);
    // return auth;
    // // }
    // // else
    // // {
    // // return null;
    // // }
    // }
    //
    // @Override
    // public boolean supports(Class<?> authentication)
    // {
    // return authentication.equals(UsernamePasswordAuthenticationToken.class);
    // }

}
